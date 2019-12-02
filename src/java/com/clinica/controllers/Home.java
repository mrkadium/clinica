package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Empresa;
import com.clinica.models.Horario;
import com.clinica.models.Sucursal;
import com.clinica.opearaciones.Operaciones;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Home", urlPatterns = {"/Home"})
public class Home extends HttpServlet {
    
    private void getEmpresa(HttpServletRequest request, HttpServletResponse response) throws SQLException{
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();
            
            Empresa e = Operaciones.get(1, new Empresa());
            List<Sucursal> Sucursales = Operaciones.getTodos(new Sucursal());
            List<Horario> Horarios = Operaciones.getTodos(new Horario());
            request.getSession().setAttribute("Empresa", e);
            request.getSession().setAttribute("Sucursales", Sucursales);
            request.getSession().setAttribute("Horarios", Horarios);

            Operaciones.commit();
        }catch(Exception ex){
            Operaciones.rollback();
        }finally{
            Operaciones.cerrarConexion();
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        try{
            switch(accion){
                case "":
                    getEmpresa(request, response);
                    request.getRequestDispatcher("home/principal.jsp").forward(request, response);
                    break;
            }
        }catch(Exception ex){
            
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
