package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Tabla;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
        
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        
        if(accion.equals("")){
            try{
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();

                String sql = "SELECT * FROM departamentos;";

                String[][] resultSet  = Operaciones.consultar(sql, null);

                String[] cabeceras = new String[]{"ID","Departamento"};

                Tabla t = new Tabla(resultSet, cabeceras);

                String tabla = resultSet != null ? t.getTabla() : "No hay datos";
                
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
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
