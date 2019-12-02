package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Menu;
import com.clinica.models.Usuario;
import com.clinica.opearaciones.Operaciones;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Filtro implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("Filtro initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            HttpServletRequest req = (HttpServletRequest)request;
            HttpServletResponse res = (HttpServletResponse)response;
            
            String path = req.getServletPath();
            Map params = req.getParameterMap();
            boolean encontrado = false;
            List<Menu> permisos = (List<Menu>)req.getSession().getAttribute("permisos");
            Usuario u = (Usuario)req.getSession().getAttribute("Usuario");
            
            if(permisos != null && u != null){
                for(Menu m: permisos){
                    if(m.getUrl().equals(path)){
                        encontrado = true;
                    }
                }
            }
            if(encontrado)
                chain.doFilter(request, response);
            else if(!encontrado && u != null){
                req.getRequestDispatcher("Home").forward(req, res);
            }else{
                req.getSession().setAttribute("msg", "Debe iniciar sesión");
                res.sendRedirect("Login");
            }
            
            Operaciones.commit();
        }catch(Exception ex){
            out.println("ERROR: "+ex.getMessage());
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Filtro.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Filtro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void destroy() {
        //we can close resources here
    }
}
