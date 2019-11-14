package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Permiso;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Tabla;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Permisos extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String rs[][], cabeceras[], tabla, sql, req = "views/permisos/";
        List<Object> params = new ArrayList();
        boolean res = false;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            switch(accion){
                case "":{
                    Operaciones.iniciarTransaccion();
                    
                    int idrol = 0;
                    try{idrol = Integer.parseInt(request.getParameter("idrol"));}
                    catch(Exception ex){idrol = 0;}
                    String rol = request.getParameter("rol");
                    
                    sql = "SELECT \n" +
                    "    a.idmenu, a.menu,\n" +
                    "    IF(a.idpadre IS NULL,'-', a.idpadre) AS idpadre, a.descripcion, a.url,\n" +
                    "	(SELECT COUNT(*) FROM permisos p WHERE p.idmenu = a.idmenu AND p.idrol = ?) as checkbox\n" +
                    "FROM menus a;";
                    params.add(idrol);
                    rs = Operaciones.consultar(sql, params);
                    cabeceras = new String[]{"ID","Menú","Padre","Descripción","URL"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setChequeable(true);
                    t.setNameChequeable("idmenu");
                    t.setPageContext(request.getContextPath());
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    if(idrol != 0)
                        request.setAttribute("idrol", idrol);
                    if(rol != null)
                        request.setAttribute("rol", rol);
                    req += "permisos_consulta.jsp";
                    
                    Operaciones.commit();
                }break;
                case "roles":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT idrol, rol FROM roles;";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID Rol","Rol"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    req += "roles.jsp";
                    
                    Operaciones.commit();
                }break;
            }
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                res = true;
            } catch (SQLException ex1) {
                Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                if(res) response.sendRedirect("Permisos");
                else request.getRequestDispatcher(req).forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idrol = request.getParameter("idrol");
        String[] idmenu = request.getParameterValues("idmenu");
        
        String sql = "SELECT * FROM permisos WHERE idrol = ?;";
        List<Object> params = new ArrayList();
        List<Permiso> lst = new ArrayList();
        params.add(Integer.parseInt(idrol));
        boolean encontrado = false;
        int resultado = 1;
        
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();
            
            String[][] rs = Operaciones.consultar(sql, params);
            if(rs != null){
                for(int i=0; i<rs[0].length; i++){
                    Permiso p = new Permiso(Integer.parseInt(rs[0][i]),Integer.parseInt(rs[1][i]),Integer.parseInt(rs[2][i]));
                    lst.add(p);
                }
            }
            
            //INSERTAR
            for(String i: idmenu){
                for(Permiso p: lst){
                    if(i.equals(p.getIdmenu()+""))
                        encontrado = true;
                }
                if(lst.isEmpty()) encontrado = false;
                if(!encontrado){
                    Permiso p = new Permiso();
                    p.setIdrol(Integer.parseInt(idrol));
                    p.setIdmenu(Integer.parseInt(i));
                    p = Operaciones.insertar(p);
                }
                encontrado = false;
            }
            
            //ELIMINAR
            for(Permiso p: lst){
                for(String i: idmenu){
                    if(i.equals(p.getIdmenu()+""))
                        encontrado = true;
                }
                if(!encontrado){
                    p = Operaciones.eliminar(p.getIdpermiso(), p);
                }
                encontrado = false;
            }
            Operaciones.commit();
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex1) {
                Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.setAttribute("resultado", resultado);
                response.sendRedirect("Permisos");
            } catch (SQLException ex) {
                Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
