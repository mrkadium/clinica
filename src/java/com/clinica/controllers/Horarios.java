package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Especialidad;
import com.clinica.models.Horario;
import com.clinica.models.Sucursal;
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

public class Horarios extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String req = "views/horarios/";
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
                    
                    sql = "SELECT\n" +
                    "	a.idhorario, b.especialidad, c.codigo AS sucursal,\n" +
                    "    a.hora_inicio, a.hora_fin, a.dias\n" +
                    "FROM horarios a, especialidades b, sucursales c\n" +
                    "WHERE\n" +
                    "	a.idespecialidad = b.idespecialidad\n" +
                    "    AND a.idsucursal = c.idsucursal\n" +
                    "ORDER BY a.idespecialidad\n" +
                    ";";
                    rs = Operaciones.consultar(sql, null);                    
                    cabeceras = new String[]{"ID","Especialidad","Sucursal","Desde","Hasta","Día"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Horarios?accion=modificar");
                    t.setPaginaEliminable("/Horarios?accion=eliminar");
                    
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);                    
                    req += "horarios_consulta.jsp";
                    Operaciones.commit();
                }break;
                case "insertar":{
                    request.setAttribute("op", "Insertar");
                    req += "insertar_modificar.jsp";
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Horario h = Operaciones.get(id, new Horario());
                    Sucursal s = Operaciones.get(h.getIdsucursal(), new Sucursal());
                    Especialidad e = Operaciones.get(h.getIdespecialidad(), new Especialidad());
                    request.setAttribute("v", h);
                    request.setAttribute("s", s);
                    request.setAttribute("e", e);
                    
                    request.setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";
                    Operaciones.commit();                    
                }break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Horario h = Operaciones.eliminar(id, new Horario());
                    int resultado = h.getIdhorario() != 0 ? 1 : 0;
                    
                    request.getSession().setAttribute("resultado", resultado);
                    res = true;
                    Operaciones.commit();                    
                }break;
                case "sucursales":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT idsucursal, codigo FROM sucursales ORDER BY idsucursal;";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID Sucursal", "Código Sucur."};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    req += "sucursales.jsp";
                    
                    Operaciones.commit();
                }break;
                case "especialidades":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT idespecialidad, especialidad FROM especialidades ORDER BY idespecialidad;";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID Especialidad", "Especialidad"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    req += "especialidades.jsp";
                    
                    Operaciones.commit();
                }break;
            }
        }catch(Exception e){
            try {
                Operaciones.rollback();
                res = true;
            } catch (SQLException ex) {
                Logger.getLogger(Horarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                if(res) response.sendRedirect("Horarios");
                else request.getRequestDispatcher(req).forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Horarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        String idhorario = request.getParameter("idhorario");
        String idespecialidad = request.getParameter("idespecialidad");
        String idsucursal = request.getParameter("idsucursal");
        String hora_inicio = request.getParameter("hora_inicio");
        String hora_fin = request.getParameter("hora_fin");
        String dias = request.getParameter("dias");
        int resultado = 1;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();
            
            Horario h = new Horario();
            h.setIdespecialidad(Integer.parseInt(idespecialidad));
            h.setIdsucursal(Integer.parseInt(idsucursal));
            h.setHora_inicio(hora_inicio);
            h.setHora_fin(hora_fin);
            h.setDias(dias);
            if(idhorario != null && !idhorario.equals("")){
                h.setIdhorario(Integer.parseInt(idhorario));
                h = Operaciones.actualizar(h.getIdhorario(), h);
            }else
                h = Operaciones.insertar(h);
            
            resultado = h.getIdhorario() != 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex1) {
                Logger.getLogger(Horarios.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                response.sendRedirect("Horarios");
            } catch (SQLException ex) {
                Logger.getLogger(Horarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
