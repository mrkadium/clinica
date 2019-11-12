package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Laboratorio;
import com.clinica.models.Municipio;
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

public class Laboratorios extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String req = "views/laboratorios/";
        boolean res = false;
        String sql;
        String[][] rs;
        String tabla;
        String[] cabeceras;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            switch(accion){
                case "":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT\n" +
                    "	a.idlaboratorio, a.nombre,\n" +
                    "    c.departamento, b.municipio, a.direccion,\n" +
                    "    IF(a.descripcion IS NULL, '-', a.descripcion) AS descripcion\n" +
                    "FROM laboratorios a, municipios b, departamentos c\n" +
                    "WHERE \n" +
                    "	a.idmunicipio = b.idmunicipio\n" +
                    "	AND b.iddepartamento = c.iddepartamento\n" +
                    ";";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID","Nombre","Depto.","Munic.","Dirección","Descripción"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Laboratorios?accion=modificar");
                    t.setPaginaEliminable("/Laboratorios?accion=eliminar");
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    req += "laboratorios_consulta.jsp";
                    
                    Operaciones.commit();
                }break;
                case "insertar":{
                    request.setAttribute("op", "Insertar");
                    req += "insertar_modificar.jsp";
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Laboratorio v = Operaciones.get(id, new Laboratorio());
                    Municipio m = Operaciones.get(v.getIdmunicipio(), new Municipio());
                    request.setAttribute("v", v);
                    request.setAttribute("m", m);
                    request.setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Laboratorio v = Operaciones.eliminar(id, new Laboratorio());
                    int resultado = v.getIdlaboratorio() != 0 ? 1 : 0;
                    request.setAttribute("resultado", resultado);
                    res = true;
                    
                    Operaciones.commit();
                }break;
                case "municipios":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT idmunicipio, municipio FROM municipios;";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID Municipio", "Municipio"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    req += "municipios.jsp";
                    
                    Operaciones.commit();
                }break;
            }
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                res = true;
            } catch (SQLException ex1) {
                Logger.getLogger(Laboratorios.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                if(res) response.sendRedirect("Laboratorios");
                else request.getRequestDispatcher(req).forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Laboratorios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idlaboratorio = request.getParameter("idlaboratorio");
        String nombre = request.getParameter("nombre");
        String idmunicipio = request.getParameter("idmunicipio");
        String direccion = request.getParameter("direccion");
        String descripcion = request.getParameter("descripcion");
        int resultado = 1;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();
            
            Laboratorio l = new Laboratorio();
            l.setNombre(nombre);
            l.setIdmunicipio(Integer.parseInt(idmunicipio));
            l.setDireccion(direccion);
            l.setDescripcion(descripcion);
            if(idlaboratorio != null && !idlaboratorio.equals("")){
                l.setIdlaboratorio(Integer.parseInt(idlaboratorio));
                l = Operaciones.actualizar(l.getIdlaboratorio(), l);
            }else
                l = Operaciones.insertar(l);
            
            resultado = l.getIdlaboratorio() != 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex1) {
                Logger.getLogger(Laboratorios.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                response.sendRedirect("Laboratorios");
            } catch (SQLException ex) {
                Logger.getLogger(Laboratorios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
