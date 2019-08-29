package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Departamento;
import com.clinica.opearaciones.Operaciones;
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

@WebServlet(name = "Departamentos", urlPatterns = {"/Departamentos"})
public class Departamentos extends HttpServlet {
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

                String sql = "SELECT * FROM departamentos;";
                String[][] resultSet  = Operaciones.consultar(sql, null);
                String[] cabeceras = new String[]{"ID", "Departamento", "Zona"};

                Tabla t = new Tabla(resultSet, cabeceras);
                t.setModificable(true);
                t.setEliminable(true);
                t.setPageContext(request.getContextPath());
                t.setPaginaModificable("/Departamentos?accion=modificar");
                t.setPaginaEliminable("/Departamentos?accion=eliminar");

                String tabla = resultSet != null ? t.getTabla() : t.getEmptyTabla();
                
                request.setAttribute("tabla", tabla);
                request.getRequestDispatcher("views/departamentos/departamentos_consulta.jsp").forward(request, response);

                Operaciones.commit();
            }catch(Exception e){
                response.getWriter().println(e.getMessage());
                try {
                    Operaciones.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(Departamentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }finally{
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Departamentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(accion.equals("insertar")){
            try{
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sql = "SELECT * FROM departamentos GROUP BY zona;";
                List<Departamento> list = new ArrayList();
                String[][] rs = Operaciones.consultar(sql, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Departamento v = new Departamento(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]);
                    list.add(v);
                }
                request.setAttribute("Zonas", list);                 
                
                request.setAttribute("accion", "insertar");
                request.getRequestDispatcher("views/departamentos/insertar_modificar.jsp").forward(request, response);
                
                Operaciones.commit();
            }catch(Exception e){
                try {
                    Operaciones.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(Departamentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }finally{
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Departamentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(accion.equals("modificar")){
            try{
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String id = request.getParameter("id");
                
                Departamento d = Operaciones.get(id, new Departamento());    
                request.setAttribute("v", d);
                
                String sql = "SELECT * FROM departamentos GROUP BY zona;";
                List<Departamento> list = new ArrayList();
                String[][] rs = Operaciones.consultar(sql, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Departamento v = new Departamento(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]);
                    list.add(v);
                }
                request.setAttribute("Zonas", list);                 
                
                request.getRequestDispatcher("views/departamentos/insertar_modificar.jsp").forward(request, response);
                
                Operaciones.commit();
            }catch(Exception e){
                try {
                    Operaciones.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(Departamentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }finally{
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Departamentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(accion.equals("eliminar")){
            try{
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Departamento d = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Departamento());
                if(d.getIddepartamento() != 0)
                    request.getSession().setAttribute("resultado", 1);
                else
                    request.getSession().setAttribute("resultado", 0);
                
                response.sendRedirect(request.getContextPath()+"/Departamentos");
                
                Operaciones.commit();
            }catch(Exception e){
                try {
                    Operaciones.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(Departamentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }finally{
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Departamentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String id = request.getParameter("iddepartamento");
        String departamento = request.getParameter("departamento");
        String zona = request.getParameter("zona");
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            if(!id.equals("") && id != null){
                Departamento d = new Departamento(Integer.parseInt(id), departamento, zona);
                
                d = Operaciones.actualizar(d.getIddepartamento(), d);
                if(d.getIddepartamento() != 0){
                    request.getSession().setAttribute("resultado", 1);
                }else{
                    request.setAttribute("resultado", 0);
                }
            }else{
                Departamento d = new Departamento();
                d.setDepartamento(departamento);
                d.setZona(zona);
                
                d = Operaciones.insertar(d);
                if(d.getIddepartamento() != 0){
                    request.getSession().setAttribute("resultado", 1);
                }else{
                    request.setAttribute("resultado", 0);
                }
            }
                
            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
                request.getSession().setAttribute("resultado", 0);
            } catch (SQLException ex) {
                Logger.getLogger(Departamentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();            
                response.sendRedirect(request.getContextPath()+"/Departamentos");
            } catch (SQLException ex) {
                Logger.getLogger(Departamentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
