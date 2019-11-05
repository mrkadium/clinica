package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Usuario;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Hash;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {
    
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        String usuario = request.getParameter("usr");
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
                    if(usuario.equals(i.getUsuario()) && clave.equals(Hash.generarHash(i.getClave(), Hash.SHA256))){
                        u = i;
                        found = true;
                        break;
                    }
                }
                if(found){
                    
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
        request.getRequestDispatcher("home/principal.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        
        switch(accion){
            case "":{
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
    }
}
