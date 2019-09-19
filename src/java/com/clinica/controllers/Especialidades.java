package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Especialidad;
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

public class Especialidades extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String req = "views/especialidades/";
        boolean res = false;
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
                    
                    sql = "SELECT * FROM especialidades";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID Especialidad","Especialidad"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Especialidades?accion=modificar");
                    t.setPaginaEliminable("/Especialidades?accion=eliminar");
                    
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    req += "especialidades_consulta.jsp";
                    
                    Operaciones.commit();
                }break;
                case "insertar":{
                    request.setAttribute("op", "Insertar");
                    req += "insertar_modificar.jsp";
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Especialidad e = Operaciones.get(id, new Especialidad());
                    request.setAttribute("v", e);
                    
                    request.setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";
                    Operaciones.commit();
                }break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Especialidad e = Operaciones.eliminar(id, new Especialidad());
                    int resultado = e.getIdespecialidad() != 0 ? 1 : 0;
                    request.getSession().setAttribute("resultado", resultado);
                    
                    res = true;
                    Operaciones.commit();
                }break;
            }
        }catch(Exception e){
            try {
                Operaciones.rollback();
                res = true;
            } catch (SQLException ex) {
                Logger.getLogger(Especialidades.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                if(!res)
                    request.getRequestDispatcher(req).forward(request, response);
                else
                    response.sendRedirect(request.getContextPath() + "/Especialidades");
            } catch (SQLException ex) {
                Logger.getLogger(Especialidades.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idespecialidad = request.getParameter("idespecialidad");
        String especialidad = request.getParameter("especialidad");
        
        int resultado = 1;
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            Especialidad e = new Especialidad();
            e.setEspecialidad(especialidad);
            if(idespecialidad != null && !idespecialidad.equals("")){
                e.setIdespecialidad(Integer.parseInt(idespecialidad));
                e = Operaciones.actualizar(e.getIdespecialidad(), e);
            }else{
                e = Operaciones.insertar(e);
            }
            resultado = e.getIdespecialidad() != 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex) {
                Logger.getLogger(Especialidades.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                response.sendRedirect(request.getContextPath() + "/Especialidades");
            } catch (SQLException ex) {
                Logger.getLogger(Especialidades.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
