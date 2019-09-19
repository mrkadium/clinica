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
import java.util.ArrayList;
import java.util.List;
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
        String req = "views/especialidades/";
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
                    
                    sql = "SELECT * FROM horarios";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID","Especialidad","Sucursal","Desde","Hasta","Día"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(res);
                    t.setEliminable(res);
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
                    
                    List<Sucursal> su = new ArrayList();
                    sql = "SELECT * FROM sucursales";
                    rs = Operaciones.consultar(sql, null);
                    if(rs != null){
                        for(int i=0; i<rs[0].length; i++){
                            su.add(new Sucursal(Integer.parseInt(rs[0][i]),Integer.parseInt(rs[1][i]),Integer.parseInt(rs[2][i]), rs[3][i], rs[4][i], rs[5][i], rs[6][i]));
                        }
                    }
                    request.setAttribute("Sucursales", su);
                    
                    List<Especialidad> es = new ArrayList();
                    sql = "SELECT * FROM especialidades";
                    rs = Operaciones.consultar(sql, null);
                    if(rs != null){
                        for(int i=0; i<rs[0].length; i++){
                            es.add(new Especialidad(Integer.parseInt(rs[0][i]), rs[1][i]));
                        }
                    }
                    request.setAttribute("Especialidades", es);
                    
                    request.setAttribute("v", h);                    
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
                if(!res)
                    request.getRequestDispatcher(req).forward(request, response);
                else
                    response.sendRedirect(request.getContextPath() + "/Horarios");
            } catch (SQLException ex) {
                Logger.getLogger(Horarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        
    }
}
