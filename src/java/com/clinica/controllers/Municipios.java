package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Departamento;
import com.clinica.models.Municipio;
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

@WebServlet(name = "Municipios", urlPatterns = {"/Municipios"})
public class Municipios extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        
        if(accion.equals("")){
            try{
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sql = 
                        "SELECT "
                        + "a.idmunicipio, "
                        + "a.municipio, "
                        + "b.departamento "
                        + "FROM municipios a, departamentos b "
                        + "WHERE a.iddepartamento = b.iddepartamento;";
                String[][] rs = Operaciones.consultar(sql,null);
                String[] cabeceras = new String[]{"ID", "Municipio", "Departamento"};
                
                Tabla t = new Tabla(rs, cabeceras);
                t.setModificable(true);
                t.setEliminable(true);
                t.setPageContext(request.getContextPath());
                t.setPaginaModificable("/Municipios?accion=modificar");
                t.setPaginaEliminable("/Municipios?accion=eliminar");
                
                String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();                
                request.setAttribute("tabla", tabla);
                
                sql = "SELECT * FROM departamentos";
                rs = Operaciones.consultar(sql, null);
                List<Departamento> lista = new ArrayList();
                for(int i=0; i<rs[0].length; i++){
                    Departamento m = new Departamento(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]);
                    lista.add(m);
                }
                request.setAttribute("Departamentos", lista);
                
                request.getRequestDispatcher("views/municipios/municipios_consulta.jsp").forward(request, response);
                
                Operaciones.commit();
            }catch(Exception e){
                try {
                    Operaciones.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(Municipios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }finally{
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Municipios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(accion.equals("insertar")){
            try{
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sql = "SELECT * FROM departamentos";
                String[][] rs = Operaciones.consultar(sql, null);
                List<Departamento> lista = new ArrayList();
                for(int i=0; i<rs[0].length; i++){
                    Departamento m = new Departamento(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]);
                    lista.add(m);
                }
                request.setAttribute("Departamentos", lista);
                
                request.getRequestDispatcher("views/municipios/insertar_modificar.jsp").forward(request, response);
                
                Operaciones.commit();
            }catch(Exception e){
                try {
                    Operaciones.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(Municipios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }finally{
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Municipios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(accion.equals("modificar")){
            try{
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Municipio v = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Municipio());
                request.setAttribute("v", v);
                
                String sql = "SELECT * FROM departamentos";
                String[][] rs = Operaciones.consultar(sql, null);
                List<Departamento> lista = new ArrayList();
                for(int i=0; i<rs[0].length; i++){
                    Departamento m = new Departamento(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]);
                    lista.add(m);
                }
                request.setAttribute("Departamentos", lista);
                
                request.getRequestDispatcher("views/municipios/insertar_modificar.jsp").forward(request, response);
                
                Operaciones.commit();
            }catch(Exception e){
                try {
                    Operaciones.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(Municipios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }finally{
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Municipios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("idmunicipio");
        String municipio = request.getParameter("municipio");
        String iddepartamento = request.getParameter("iddepartamento");
        int resultado = 1;
        Municipio m = new Municipio();
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            if(id != null && !id.equals("")){
                //MODIFICAR
                m = new Municipio(Integer.parseInt(id), municipio, Integer.parseInt(iddepartamento));
                m = Operaciones.actualizar(m.getIdmunicipio(), m);
            }else{
                //INSERTAR
                m.setMunicipio(municipio);
                m.setIddepartamento(Integer.parseInt(iddepartamento));
                m = Operaciones.insertar(m);
            }
            resultado = m.getIdmunicipio() != 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Municipios.class.getName()).log(Level.SEVERE, null, ex);
            }
            resultado = 0;
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Municipios.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect(request.getContextPath()+"/Municipios");
            request.getSession().setAttribute("resultado", resultado);
        }
    }
}
