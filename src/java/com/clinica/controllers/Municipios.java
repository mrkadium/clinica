package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Cargo;
import com.clinica.models.Departamento;
import com.clinica.models.Municipio;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Select;
import com.clinica.utilerias.Tabla;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Municipios", urlPatterns = {"/Municipios"})
public class Municipios extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String req = "views/municipios/";
        String sql = "";
        String[][] rs;
        String[] cabeceras;
        boolean res = false;
        try{            
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            
            switch(accion){
                case "":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT a.idmunicipio, a.municipio, b.departamento FROM municipios a, departamentos b WHERE a.iddepartamento = b.iddepartamento;";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID Mun.","Municipio","Departamento"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Municipios?accion=modificar");
                    t.setPaginaEliminable("/Municipios?accion=eliminar");
                    
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);                    
                    req += "municipios_consulta.jsp";
                    
                    Operaciones.commit();
                }break;
                case "insertar":{
                    Operaciones.iniciarTransaccion();

                    request.setAttribute("Departamentos", Select.departamentos());

                    request.setAttribute("op", "Insertar");
                    req += "insertar_modificar.jsp";

                    Operaciones.commit();
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Municipio m = Operaciones.get(id, new Municipio());
                    request.setAttribute("v", m);

                    request.setAttribute("Departamentos", Select.departamentos());
                    
                    request.getSession().setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Municipio m = Operaciones.eliminar(id, new Municipio());
                    
                    int resultado = m.getIdmunicipio()!= 0 ? 1 : 0;
                    request.getSession().setAttribute("resultado", resultado);
                    res = true;
                    
                    Operaciones.commit();
                }break;
            }
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                res = true;
                request.getSession().setAttribute("resultado", 0);
            } catch (SQLException ex1) {
                Logger.getLogger(Municipios.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                if(!res)
                    request.getRequestDispatcher(req).forward(request, response);
                else
                    response.sendRedirect(request.getContextPath()+"/Municipios");
            } catch (SQLException ex) {
                Logger.getLogger(Municipios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idmunicipio = request.getParameter("idmunicipio");
        String municipio = request.getParameter("municipio");
        String iddepartamento = request.getParameter("iddepartamento");
        int resultado = 1;
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            Municipio m = new Municipio();
            m.setMunicipio(municipio);
            m.setIddepartamento(Integer.parseInt(iddepartamento));
            if(idmunicipio != null && !idmunicipio.equals("")){
                m.setIdmunicipio(Integer.parseInt(idmunicipio));
                m = Operaciones.actualizar(m.getIdmunicipio(), m);
            }else{
                m = Operaciones.insertar(m);
            }
            resultado = m.getIdmunicipio() != 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex) {
                Logger.getLogger(Municipios.class.getName()).log(Level.SEVERE, null, ex);
            }
            resultado = 0;
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                response.sendRedirect(request.getContextPath() + "/Municipios");
            } catch (SQLException ex) {
                Logger.getLogger(Municipios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
