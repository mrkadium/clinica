package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Empleado;
import com.clinica.models.Menu;
import com.clinica.models.Rol;
import com.clinica.models.Usuario;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Hash;
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

public class Login extends HttpServlet {
    
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        String usuario = request.getParameter("user");
        String clave = request.getParameter("pass");
        
        if(!usuario.equals("") && !clave.equals("")){ //Si no están vacíos, entonces consultará la bd
            try{
                Conexion con = new ConexionPool();
                con.conectar();
                Operaciones.abrirConexion(con);
                Operaciones.iniciarTransaccion();
                
                String sql = "SELECT * FROM usuarios;";
                String[][] rs = Operaciones.consultar(sql, null);
                List<Usuario> lst = new ArrayList();
                Usuario u = new Usuario();
                for(int i=0; i<rs[0].length; i++){
                    u = new Usuario(Integer.parseInt(rs[0][i]), Integer.parseInt(rs[1][i]), rs[2][i], rs[3][i],Integer.parseInt(rs[4][i]), rs[5][i]);
                    lst.add(u);
                }
                boolean found = false;
                for(Usuario i: lst){
                    if(usuario.equals(i.getUsuario()) && i.getClave().equals(Hash.generarHash(clave, Hash.SHA256))){
                        u = i;
                        found = true;
                        break;
                    }
                }
                if(found){
                    Empleado e = Operaciones.get(u.getIdempleado(), new Empleado());
                    Rol r = Operaciones.get(u.getIdrol(), new Rol());
                    request.getSession().setAttribute("Usuario",u);
                    request.getSession().setAttribute("Empleado",e);
                    request.getSession().setAttribute("Rol", r);
                    
                    sql = "SELECT * FROM menus WHERE idmenu IN (SELECT x.idmenu FROM permisos x WHERE x.idrol = ?)";
                    List<Object> params = new ArrayList();
                    List<Menu> permisos = new ArrayList();
                    params.add(u.getIdrol());
                    rs = Operaciones.consultar(sql, params);
                    for(int i=0; i < rs[0].length; i++){
                        rs[2][i] = rs[2][i] != null ? rs[2][i] : "0";
                        permisos.add(new Menu(Integer.parseInt(rs[0][i]), rs[1][i], Integer.parseInt(rs[2][i]), rs[3][i], rs[4][i]));
                    }
                    
                    List<Menu> MenuPrincipal = new ArrayList();
                    for(Menu m: permisos){
                        if(m.getIdpadre() == null || m.getIdpadre() == 0)
                            MenuPrincipal.add(m);
                    }
                    
                    request.getSession().setAttribute("permisos",permisos);
                    request.getSession().setAttribute("MenuPrincipal",MenuPrincipal);
                    response.sendRedirect("Home");
                }else{
                    request.setAttribute("msg", "Credenciales erróneas");
                    request.getRequestDispatcher("home/login.jsp").forward(request, response);
                }
                
                Operaciones.commit();
            }catch(Exception e){
                Operaciones.rollback();
            }finally{
                Operaciones.cerrarConexion();
            }
        }
    }
    
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("msg", "Sesión finalizada");
        request.getSession().invalidate();
        request.getRequestDispatcher("home/login.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = getServletConfig().getInitParameter("accion") != null ? getServletConfig().getInitParameter("accion") : request.getParameter("accion");
        accion = accion != null ? accion : "";
        
        switch(accion){
            case "":{
                if(request.getSession().getAttribute("Usuario") != null){
                    request.setAttribute("msg", "Ya hay una sesión activa");
                    request.setAttribute("status", 3);
                }
                request.getRequestDispatcher("home/login.jsp").forward(request, response);
            }break;
            case "logout":{
                logout(request, response);
            }break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            login(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
