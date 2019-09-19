package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Cargo;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Tabla;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Cargos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String req = "views/cargos/";
        String sql = "";
        String[][] rs;
        String[] cabeceras;
        try{            
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            
            switch(accion){
                case "":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT * FROM cargos";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID Cargo","Cargo","Descripción"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Cargos?accion=modificar");
                    t.setPaginaEliminable("/Cargos?accion=eliminar");
                    
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);                    
                    req += "cargos_consulta.jsp";
                    
                    Operaciones.commit();
                }break;
                case "insertar":{
                    request.setAttribute("op", "Insertar");
                    req += "insertar_modificar.jsp";
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Cargo c = Operaciones.get(id, new Cargo());
                    request.setAttribute("v", c);
                    
                    request.getSession().setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Cargo c = Operaciones.eliminar(id, new Cargo());
                    
                    int resultado = c.getIdcargo()!= 0 ? 1 : 0;
                    request.getSession().setAttribute("resultado", resultado);
                    req = "Cargos";
                    
                    Operaciones.commit();
                }break;
            }
        }catch(Exception e){
            try {
                Operaciones.rollback();
                req = "Cargos";
            } catch (SQLException ex) {
                Logger.getLogger(Cargos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getRequestDispatcher(req).forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Cargos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idcargo = request.getParameter("idcargo") != null ? request.getParameter("idcargo") : "";
        String cargo = request.getParameter("cargo");
        String descripcion = request.getParameter("descripcion");
        int resultado = 1;
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            Cargo c = new Cargo();
            c.setCargo(cargo);
            c.setDescripcion(descripcion);
            if(!idcargo.equals("")){
                c.setIdcargo(Integer.parseInt(idcargo));
                c = Operaciones.actualizar(c.getIdcargo(), c);
            }else{
                c = Operaciones.insertar(c);
            }
            resultado = c.getIdcargo() != 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex) {
                Logger.getLogger(Cargos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                response.sendRedirect(request.getContextPath() + "/Cargos");
            } catch (SQLException ex) {
                Logger.getLogger(Cargos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
