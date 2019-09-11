package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Paciente;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Tabla;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Pacientes extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }        
        
        String req = "views/pacientes/pacientes_consulta.jsp";
        String sql = "";
        String[][] rs;
        String[] cabeceras;
        int resultado = 1;
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            
            switch(accion){
                case "":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT * FROM pacientes;";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID","Exp","Nombres","Apellidos","F. nacimiento","Género","Tel","Email", "ID Mun","Mun","Depto", "Suc.", "Empl."};
                    
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Pacientes?accion=modificar");
                    t.setPaginaEliminable("/Pacientes?accion=eliminar");
                    
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();                    
                    request.setAttribute("tabla", tabla);
                    
                    Operaciones.commit();
                }break;
                case "insertar":{
                    request.getSession().setAttribute("op", "Insertar");
                    req = "views/pacientes/insertar_modificar.jsp";
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    String id = request.getParameter("id");
                    Paciente v = new Paciente();
                    v = Operaciones.get(Integer.parseInt(id), new Paciente());
                    request.setAttribute("v", v);
                    
                    request.getSession().setAttribute("op", "Insertar");
                    req = "views/pacientes/insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    String id = request.getParameter("id");
                    Operaciones.eliminar(Integer.parseInt(id), new Paciente());
                    
                    Operaciones.commit();
                }break;
                case "sucursales":{
                }break;
                case "empleados:":{
                }break;
            }
        }catch(Exception e){
            try {
                Operaciones.rollback();
                req = "Pacientes";
                resultado = 0;
            } catch (SQLException ex) {
                Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.setAttribute("resultado", resultado);
                request.getRequestDispatcher(req).forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id") != null ? request.getParameter("id") : "";
        String expediente = request.getParameter("expediente");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String fecha_nacimiento = request.getParameter("fecha_nacimiento");
        String genero = request.getParameter("genero");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");
        String idmunicipio = request.getParameter("idmunicipio");
        String dui_empleado = request.getParameter("dui_empleado");
        String codigo_sucursal = request.getParameter("codigo_sucursal");
        
        int resultado = 1;
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            
            
            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex) {
                Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                request.getRequestDispatcher("Pacientes").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
