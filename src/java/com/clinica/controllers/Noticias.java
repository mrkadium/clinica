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
        
        if(accion.equals("")){
            try{
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sql = "SELECT * FROM noticias";
                String[][] rs = Operaciones.consultar(sql, null);
                String[] cabeceras = new String[]{"ID", "Título", "Resumen", "Contenido"};
                
                Tabla t = new Tabla(rs, cabeceras);
                t.setModificable(true);
                t.setEliminable(true);
                t.setPageContext(request.getContextPath());
                t.setPaginaModificable("/Noticias?accion=modificar");
                t.setPaginaEliminable("/Noticias?accion=eliminar");
                
                String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                
                request.setAttribute("tabla", tabla);
                request.getRequestDispatcher("views/noticias/noticias_consulta.jsp").forward(request, response);
                
                Operaciones.commit();
            }catch(Exception e){
                try {
                    Operaciones.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(Noticias.class.getName()).log(Level.SEVERE, null, ex);
                }
            }finally{
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Noticias.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(accion.equals("insertar")){
                request.getRequestDispatcher("views/noticias/insertar_modificar.jsp").forward(request, response);
        }else if(accion.equals("modificar")){
            try{
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String id = request.getParameter("id");
                
                Noticia n = Operaciones.get(Integer.parseInt(id), new Noticia());
                request.setAttribute("v", n);
                
                request.getRequestDispatcher("views/noticias/insertar_modificar.jsp").forward(request, response);
                
                Operaciones.commit();
            }catch(Exception e){
                try {
                    Operaciones.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(Noticias.class.getName()).log(Level.SEVERE, null, ex);
                }
            }finally{
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Noticias.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(accion.equals("eliminar")){                
            int resultado = 1;
            String id = request.getParameter("id");
            
            try{
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Noticia n = Operaciones.eliminar(Integer.parseInt(id), new Noticia());
                resultado = n.getIdnoticia() != 0 ? 1 : 0;
                request.getSession().setAttribute("resultado", resultado);
                
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
                    request.getSession().setAttribute("resultado", resultado);                
                    request.getRequestDispatcher("views/noticias/noticias_consulta.jsp").forward(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(Noticias.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String titulo = request.getParameter("titulo");
        String resumen = request.getParameter("resumen");
        String contenido = request.getParameter("contenido");
        int resultado = 1;
        Noticia v = new Noticia();
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            if(id != null && id.equals("")){
                v = new Noticia(Integer.parseInt(id), titulo, resumen, contenido);
                v = Operaciones.actualizar(v.getIdnoticia(), v);
            }else{
                v.setTitulo(titulo);
                v.setResumen(resumen);
                v.setContenido(contenido);
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
