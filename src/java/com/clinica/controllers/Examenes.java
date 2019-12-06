package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Examen;
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

public class Examenes extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String req = "views/examenes/";
        String sql, tabla, cabeceras[], rs[][];
        boolean res = false;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            switch(accion){
                case "":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT * FROM examenes;";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID Examen","Examen","Descripción"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Examenes?accion=modificar");
                    t.setPaginaEliminable("/Examenes?accion=eliminar");
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    
                    request.setAttribute("tabla", tabla);
                    req += "examenes_consulta.jsp";
                    
                    Operaciones.commit();
                }break;
                case "insertar":{
                    request.setAttribute("op","Insertar");
                    req += "insertar_modificar.jsp";
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Examen e = Operaciones.get(id, new Examen());
                    request.setAttribute("v",e);
                    request.setAttribute("op","Modificar");
                    req += "insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Examen e = Operaciones.eliminar(id, new Examen());
                    int resultado = e.getIdexamen() != 0 ? 1 : 0;
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
                Logger.getLogger(Examenes.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                if(res) response.sendRedirect("Examenes");
                else request.getRequestDispatcher(req).forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Examenes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idexamen = request.getParameter("idexamen");
        String examen = request.getParameter("examen");
        String descripcion = request.getParameter("descripcion");
        int resultado = 1;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();
            
            Examen e = new Examen();
            e.setExamen(examen);
            e.setDescripcion(descripcion);
            if(idexamen != null && !idexamen.equals("")){
                e.setIdexamen(Integer.parseInt(idexamen));
                e = Operaciones.actualizar(e.getIdexamen(), e);
            }else
                e = Operaciones.insertar(e);
            
            resultado = e.getIdexamen() != 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex1) {
                Logger.getLogger(Examenes.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                response.sendRedirect("Examenes");
            } catch (SQLException ex) {
                Logger.getLogger(Examenes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
