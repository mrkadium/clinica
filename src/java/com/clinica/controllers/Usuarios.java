package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Empleado;
import com.clinica.models.Rol;
import com.clinica.models.Usuario;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Hash;
import com.clinica.utilerias.Tabla;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Usuarios extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }        
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String sql = "";
        String[][] rs;
        String[] cabeceras;
        String tabla;
        String req = "views/usuarios/";
        boolean res = false;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            switch(accion){
                case "":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT\n" +
                    "	a.idusuario, a.usuario,\n" +
                    "    CONCAT(b.nombres,' ',b.apellidos) as empleado, \n" +
                    "    c.rol, a.estado\n" +
                    "FROM usuarios a, empleados b, roles c\n" +
                    "WHERE\n" +
                    "	a.idempleado = b.idempleado\n" +
                    "    AND a.idrol = c.idrol\n" +
                    "ORDER BY a.idrol\n" +
                    ";";                    
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID","Usuario","Empleado","Rol","Estado"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Usuarios?accion=modificar");
                    t.setPaginaEliminable("/Usuarios?accion=eliminar");
                    
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    req += "usuarios_consulta.jsp";
                    
                    Operaciones.commit();
                }break;
                case "insertar":{
                    Operaciones.iniciarTransaccion();
                    
                    List<Rol> roles = Operaciones.getTodos(new Rol());
                    request.setAttribute("Roles", roles);
                    request.setAttribute("op", "Insertar");
                    req += "insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Usuario v = Operaciones.get(id, new Usuario());
                    Empleado e = Operaciones.get(v.getIdempleado(), new Empleado());
                    request.setAttribute("v", v);
                    request.setAttribute("e", e);
                    
                    List<Rol> roles = Operaciones.getTodos(new Rol());
                    request.setAttribute("Roles", roles);   
                    request.setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";                 
                    
                    Operaciones.commit();
                }break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Usuario v = Operaciones.eliminar(id, new Usuario());
                    
                    int resultado = v.getIdusuario()!= 0 ? 1 : 0;
                    request.getSession().setAttribute("resultado", resultado);
                    res = true;                   
                    
                    Operaciones.commit();
                }break;
                case "empleados":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT idempleado, CONCAT(nombres,' ',apellidos) as empleado FROM empleados WHERE idempleado NOT IN (SELECT idempleado FROM usuarios);";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID Empleado", "Empleado"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    req += "empleados.jsp";
                    
                    Operaciones.commit();
                }break;
            }
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                res = true;
            } catch (SQLException ex1) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                if(res) response.sendRedirect("Usuarios");
                else request.getRequestDispatcher(req).forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idusuario = request.getParameter("idusuario");
        String idempleado = request.getParameter("idempleado");
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        String idrol = request.getParameter("idrol");
        String estado = request.getParameter("estado");
        int resultado = 1;
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            Usuario u = new Usuario();
            u.setIdempleado(Integer.parseInt(idempleado));
            u.setUsuario(usuario);
            u.setClave(Hash.generarHash(clave, Hash.SHA256));
            u.setIdrol(Integer.parseInt(idrol));
            u.setEstado(estado);
            if(idusuario != null && !idusuario.equals("")){
                u.setIdusuario(Integer.parseInt(idusuario));
                u = Operaciones.actualizar(u.getIdusuario(), u);
            }else
                u = Operaciones.insertar(u);
            
            resultado = u.getIdempleado() != 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex1) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                response.sendRedirect("Usuarios");
            } catch (SQLException ex) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
