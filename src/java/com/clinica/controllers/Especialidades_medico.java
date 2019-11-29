package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Especialidad_medico;
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

public class Especialidades_medico extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String rs[][], cabeceras[], tabla, sql, req = "views/especialidades_medico/";
        List<Object> params = new ArrayList();
        boolean res = false;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            switch(accion){
                case "":{
                    Operaciones.iniciarTransaccion();

                    int idmedico = 0;
                    try{idmedico = Integer.parseInt(request.getParameter("idmedico"));}
                    catch(Exception ex){idmedico = 0;}
                    String medico = request.getParameter("medico");
                    sql = "SELECT\n" +
                    "	*,\n" +
                    "	(SELECT IF(COUNT(*) > 0, 1, 0) FROM especialidades_medico e WHERE e.idespecialidad = a.idespecialidad AND e.idmedico = ?) as checkbox\n" +
                    "FROM especialidades a;";
                    params.add(idmedico);
                    rs = Operaciones.consultar(sql, params);
                    cabeceras = new String[]{"ID Especialidad", "Especialidad"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setChequeable(true);
                    t.setNameChequeable("idespecialidad");
                    t.setPageContext(request.getContextPath());
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    if(idmedico != 0)
                        request.setAttribute("idmedico", idmedico);
                    if(medico != null)
                        request.setAttribute("medico", medico);
                    request.setAttribute("tabla", tabla);
                    req += "consulta.jsp";            

                    Operaciones.commit();
                }break;
                case "medicos":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT\n" +
                    "	idempleado,\n" +
                    "    CONCAT(nombres,' ',apellidos) as empleado, jvpm\n" +
                    "FROM empleados\n" +
                    "WHERE jvpm IS NOT NULL;";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID Médico", "Médico", "JVPM"};
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
                Logger.getLogger(Especialidades_medico.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                if(res) response.sendRedirect("Especialidades_medico");
                else request.getRequestDispatcher(req).forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Especialidades_medico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idmedico = request.getParameter("idmedico");
        String[] idespecialidad = request.getParameterValues("idespecialidad");
        
        String sql = "SELECT * FROM especialidades_medico WHERE idmedico = ?;";
        List<Object> params = new ArrayList();
        List<Especialidad_medico> lst = new ArrayList();
        params.add(Integer.parseInt(idmedico));
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
                    Especialidad_medico p = new Especialidad_medico(Integer.parseInt(rs[0][i]),Integer.parseInt(rs[1][i]),Integer.parseInt(rs[2][i]));
                    lst.add(p);
                }
            }
            
            //INSERTAR
            for(String i: idespecialidad){
                for(Especialidad_medico p: lst){
                    if(i.equals(p.getIdespecialidad()+""))
                        encontrado = true;
                }
                if(lst.isEmpty()) encontrado = false;
                if(!encontrado){
                    Especialidad_medico p = new Especialidad_medico();
                    p.setIdmedico(Integer.parseInt(idmedico));
                    p.setIdespecialidad(Integer.parseInt(i));
                    p = Operaciones.insertar(p);
                }
                encontrado = false;
            }
            
            //ELIMINAR
            for(Especialidad_medico p: lst){
                for(String i: idespecialidad){
                    if(i.equals(p.getIdespecialidad()+""))
                        encontrado = true;
                }
                if(!encontrado){
                    p = Operaciones.eliminar(p.getIdespecialidad_medico(), p);
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
                response.sendRedirect("Especialidades_medico");
            } catch (SQLException ex) {
                Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
