package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Contacto;
import com.clinica.models.Empleado;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Select;
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

public class Contactos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String req = "views/contactos/";
        String sql = "";
        String[][] rs;
        String[] cabeceras;
        
        boolean res = false;
        try{            
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            
            switch(accion){
                case "":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT a.*,CONCAT(b.nombres,' ',b.apellidos) as empleado  FROM contactos a, empleados b WHERE a.idempleado = b.idempleado";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID","Tipo","Contacto", "ID Emp.","Empleado"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Contactos?accion=modificar");
                    t.setPaginaEliminable("/Contactos?accion=eliminar");
                    
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);                    
                    req += "contactos_consulta.jsp";
                    
                    Operaciones.commit();
                }break;
                case "insertar":{
                    Operaciones.iniciarTransaccion();
                    
                    List<String> tipos = Select.tipos_contacto();
                    request.setAttribute("Tipos", tipos);
                    
                    request.setAttribute("op", "Insertar");
                    req += "insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Contacto c = Operaciones.get(id, new Contacto());
                    
                    List<String> tipos = Select.tipos_contacto();
                    
                    request.setAttribute("Tipos", tipos);
                    request.setAttribute("v", c);
                    
                    request.getSession().setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Contacto c = Operaciones.eliminar(id, new Contacto());
                    
                    int resultado = c.getIdcontacto()!= 0 ? 1 : 0;
                    request.getSession().setAttribute("resultado", resultado);
                    res = true;
                    
                    Operaciones.commit();
                }break;
                case "empleados":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT idempleado, CONCAT(apellidos,', ',nombres) as empleado FROM empleados;";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"COD", "Empleado"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    
                    req += "empleados.jsp";
                    
                    Operaciones.commit();
                }break;
            }
        }catch(Exception e){
            try {
                Operaciones.rollback();
                res = true;
            } catch (SQLException ex) {
                Logger.getLogger(Contactos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                if(!res)
                    request.getRequestDispatcher(req).forward(request, response);
                else
                    response.sendRedirect(request.getContextPath()+"/Contactos");
            } catch (SQLException ex) {
                Logger.getLogger(Contactos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idcontacto = request.getParameter("idcontacto") != null ? request.getParameter("idcontacto") : "";
        String tipo = request.getParameter("tipo");
        String contacto = request.getParameter("contacto");
        String idempleado = request.getParameter("idempleado");
        int resultado = 1;
        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            Contacto c = new Contacto();
            c.setTipo(tipo);
            c.setContacto(contacto);
            c.setIdempleado(Integer.parseInt(idempleado));
            if(!idcontacto.equals("")){
                c.setIdcontacto(Integer.parseInt(idcontacto));
                c = Operaciones.actualizar(c.getIdcontacto(), c);
            }else{
                c = Operaciones.insertar(c);
            }
            resultado = c.getIdcontacto() != 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex) {
                Logger.getLogger(Contactos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                response.sendRedirect(request.getContextPath() + "/Contactos");
            } catch (SQLException ex) {
                Logger.getLogger(Contactos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
