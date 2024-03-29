package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Noticia;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Tabla;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Noticias", urlPatterns = {"/Noticias"})
public class Noticias extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String req = "views/noticias/";
        String sql = "";
        String[][] rs;
        String[] cabeceras;
        boolean res = false;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            
            switch(accion){
                case "":{                
                    Operaciones.iniciarTransaccion();

                    sql = "SELECT * FROM noticias";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID", "Título", "Resumen", "Contenido"};

                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Noticias?accion=modificar");
                    t.setPaginaEliminable("/Noticias?accion=eliminar");
                    
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);                    
                    req += "noticias_consulta.jsp";

                    Operaciones.commit();
                }break;
                case "insertar":{
                    request.setAttribute("op", "Insertar");
                    req += "insertar_modificar.jsp";
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Noticia n = Operaciones.get(id, new Noticia());
                    
                    request.setAttribute("v", n);
                    request.getSession().setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Noticia n = Operaciones.eliminar(id, new Noticia());
                    
                    int resultado = n.getIdnoticia()!= 0 ? 1 : 0;
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
                Logger.getLogger(Noticias.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                if(!res)
                    request.getRequestDispatcher(req).forward(request, response);
                else
                    response.sendRedirect(request.getContextPath()+"/Noticias");
            } catch (SQLException ex) {
                Logger.getLogger(Noticias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idnoticia = request.getParameter("idnoticia");
        String titulo = request.getParameter("titulo");
        String resumen = request.getParameter("resumen");
        String contenido = request.getParameter("contenido");
        int resultado = 1;
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            Noticia v = new Noticia();
            v.setTitulo(titulo);
            v.setResumen(resumen);
            v.setContenido(contenido);
            if(idnoticia != null && !idnoticia.equals("")){
                v.setIdnoticia(Integer.parseInt(idnoticia));
                v = Operaciones.actualizar(v.getIdnoticia(), v);
            }else{
                v = Operaciones.insertar(v);
            }
            resultado = v.getIdnoticia() != 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex) {
                Logger.getLogger(Noticias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                response.sendRedirect(request.getContextPath()+"/Noticias");
                request.getSession().setAttribute("resultado", resultado);
            } catch (SQLException ex) {
                Logger.getLogger(Noticias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
