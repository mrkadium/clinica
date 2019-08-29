package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Empresa;
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

@WebServlet(name = "Empresas", urlPatterns = {"/Empresas"})
public class Empresas extends HttpServlet {

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

                String sql = "SELECT * FROM empresa;";
                String[][] resultSet  = Operaciones.consultar(sql, null);
                String[] cabeceras = new String[]{"ID", "Nombre", "Facebook", "Whatsapp"};

                Tabla t = new Tabla(resultSet, cabeceras);
                t.setModificable(true);
                t.setPageContext(request.getContextPath());
                t.setPaginaModificable("/Empresas?accion=modificar");

                String tabla = resultSet != null ? t.getTabla() : t.getEmptyTabla();
                
                request.setAttribute("tabla", tabla);
                request.getRequestDispatcher("views/empresa/empresas_consulta.jsp").forward(request, response);

                Operaciones.commit();
            }catch(Exception e){
                response.getWriter().println(e.getMessage());
                try {
                    Operaciones.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(Empresas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }finally{
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Empresas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(accion.equals("modificar")){
            try{
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String id = request.getParameter("id");
                
                Empresa e = Operaciones.get(Integer.parseInt(id), new Empresa());    
                request.setAttribute("v", e);                
                
                request.getRequestDispatcher("views/empresa/insertar_modificar.jsp").forward(request, response);
                
                Operaciones.commit();
            }catch(Exception e){
                try {
                    Operaciones.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(Empresas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }finally{
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Empresas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("idempresa");
        String nombre = request.getParameter("nombre");
        String facebook = request.getParameter("facebook");
        String whatsapp = request.getParameter("whatsapp");
        int resultado = 1;
        Empresa v = new Empresa();
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            if(id != null && !id.equals("")){
                //MODIFICANDO
                v.setId(Integer.parseInt(id));
                v.setNombre(nombre);
                v.setFacebook(facebook);
                v.setWhatsapp(whatsapp);                   
                v = Operaciones.actualizar(v.getIdEmpresa(), v);
            }else{
                //INSERTANDO
                v.setNombre(nombre);
                v.setFacebook(facebook);
                v.setWhatsapp(whatsapp);                
                v = Operaciones.insertar(v);
            }
            resultado = v.getIdEmpresa() != 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex) {
                Logger.getLogger(Empresas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                response.sendRedirect(request.getContextPath()+"/Empresas");
                request.getSession().setAttribute("resultado", resultado);
            } catch (SQLException ex) {
                Logger.getLogger(Empresas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
