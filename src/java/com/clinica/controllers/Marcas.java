package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Marca;
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

public class Marcas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("resultado") != null) {
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String sql, tabla, cabeceras[], rs[][], req = "views/marcas/";
        boolean res = false;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            switch(accion){
                case "":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT * FROM marcas ORDER BY idmarca;";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID Marca", "Marca"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Marcas?accion=modificar");
                    t.setPaginaEliminable("/Marcas?accion=eliminar");
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    req += "marcas_consulta.jsp";
                    
                    Operaciones.commit();
                }break;
                case "insertar":{
                    request.setAttribute("op", "Insertar");
                    req += "insertar_modificar.jsp";
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Marca v = Operaciones.get(id, new Marca());
                    request.setAttribute("v", v);
                    request.setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Marca v = Operaciones.eliminar(id, new Marca());
                    int resultado = v.getIdmarca() != 0 ? 1 : 0;
                    request.getSession().setAttribute("resultado", resultado);
                    res = true;
                    
                    Operaciones.commit();
                }break;
            }
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                res = true;
            } catch (SQLException ex1) {
                Logger.getLogger(Marcas.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                if(res) response.sendRedirect("Marcas");
                else request.getRequestDispatcher(req).forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Marcas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idmarca = request.getParameter("idmarca");
        String marca = request.getParameter("marca");
        int resultado = 1;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();
            
            Marca m = new Marca();
            m.setMarca(marca);
            if(idmarca != null && !idmarca.equals("")){
                m.setIdmarca(Integer.parseInt(idmarca));
                m = Operaciones.actualizar(m.getIdmarca(), m);
            }else
                m = Operaciones.insertar(m);
            
            resultado = m.getIdmarca() != 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex1) {
                Logger.getLogger(Marcas.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                response.sendRedirect("Marcas");
            } catch (SQLException ex) {
                Logger.getLogger(Marcas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
