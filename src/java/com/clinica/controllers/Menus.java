package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Menu;
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

@WebServlet(name = "Menus", urlPatterns = {"/Menus"})
public class Menus extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String req = "views/menus/";
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
                    
                    sql = "SELECT \n" +
                    "	a.idmenu, a.menu, \n" +
                    "	IF(a.idpadre IS NULL, '-', a.idpadre) as idpadre,\n" +
                    "	IF(a.idpadre IS NULL, '-', b.menu) as padre,\n" +
                    "	a.descripcion, a.url\n" +
                    "FROM menus a LEFT JOIN menus b \n" +
                    "ON a.idpadre = b.idmenu\n" +
                    "ORDER BY a.idmenu\n" +
                    ";";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID Menú","Menú","ID Padre","Padre","Descripción","URL"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Menus?accion=modificar");
                    t.setPaginaEliminable("/Menus?accion=eliminar");
                    
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    req += "menus_consulta.jsp";
                    
                    Operaciones.commit();
                }break;
                case "insertar":{
                    request.setAttribute("op", "Insertar");
                    req += "insertar_modificar.jsp";
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Menu m = Operaciones.get(id, new Menu());
                    request.setAttribute("v", m);
                    
                    request.setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";
                    Operaciones.commit();
                }break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Menu m = Operaciones.eliminar(id, new Menu());
                    int resultado = m.getIdmenu()!= 0 ? 1 : 0;
                    request.getSession().setAttribute("resultado", resultado);
                    
                    res = true;
                    Operaciones.commit();
                }break;
                case "padres":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT \n" +
                    "	a.idmenu, a.menu, \n" +
                    "	IF(a.idpadre IS NULL, '-', a.idpadre) as idpadre,\n" +
                    "	IF(a.idpadre IS NULL, '-', b.menu) as padre,\n" +
                    "	a.descripcion, a.url\n" +
                    "FROM menus a LEFT JOIN menus b \n" +
                    "ON a.idpadre = b.idmenu\n" +
                    "ORDER BY a.idmenu\n" +
                    ";";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID Menú","Menú","ID Padre","Padre","Descripción","URL"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    
                    req += "empleados.jsp";
                    
                    Operaciones.commit();
                }break;
            }
        }catch(Exception e){
            try {
                Operaciones.rollback();
                res = true;
            } catch (SQLException ex) {
                Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{            
            try {
                Operaciones.cerrarConexion();
                if(!res)
                    request.getRequestDispatcher(req).forward(request, response);
                else
                    response.sendRedirect(request.getContextPath() + "/Menus");
            } catch (SQLException ex) {
                Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idmenu = request.getParameter("idmenu");
        String menu = request.getParameter("menu");
        String idpadre = request.getParameter("idpadre");
        String descripcion = request.getParameter("descripcion");
        String url = request.getParameter("url");
        int resultado = 0;
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            Menu m = new Menu();
            m.setMenu(menu);
            if(idpadre != null && !idpadre.equals("")){
                m.setIdpadre(Integer.parseInt(idpadre));
            }
            m.setDescripcion(descripcion);
            m.setUrl(url);
            if(idmenu != null && !idmenu.equals("")){
                m.setIdmenu(Integer.parseInt(idmenu));
                m = Operaciones.actualizar(m.getIdmenu(), m);
            }else{
                m = Operaciones.insertar(m);
            }
            resultado = m.getIdmenu()!= 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex) {
                Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                response.sendRedirect(request.getContextPath() + "/Menus");
            } catch (SQLException ex) {
                Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
