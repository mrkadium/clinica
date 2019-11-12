package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Departamento;
import com.clinica.models.Empleado;
import com.clinica.models.Municipio;
import com.clinica.models.Paciente;
import com.clinica.models.Sucursal;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Tabla;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
        
        String req = "views/pacientes/";
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
                    
                    sql = "SELECT\n" +
                    "	a.idpaciente, a.expediente, CONCAT(a.nombres, ' ', a.apellidos) AS paciente,\n" +
                    "    a.fecha_nacimiento, a.genero, a.telefono, a.email,\n" +
                    "    b.municipio, c.departamento, e.codigo, CONCAT(d.nombres, ' ', d.apellidos) AS empleado\n" +
                    "FROM pacientes a, municipios b, departamentos c, empleados d, sucursales e\n" +
                    "WHERE\n" +
                    "	a.idmunicipio = b.idmunicipio\n" +
                    "    AND a.idsucursal = e.idsucursal\n" +
                    "    AND b.iddepartamento = c.iddepartamento\n" +
                    "    AND a.idempleado = d.idempleado\n" +
                    ";";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID","Exp","Paciente","F. nacimiento","Género","Tel","Email","Mun.", "Depto.", "Suc.", "Empl."};
                    
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Pacientes?accion=modificar");
                    t.setPaginaEliminable("/Pacientes?accion=eliminar");
                    
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();                    
                    request.setAttribute("tabla", tabla);
                    req += "pacientes_consulta.jsp";
                    
                    Operaciones.commit();
                }break;
                case "insertar":{
                    request.getSession().setAttribute("op", "Insertar");
                    req += "insertar_modificar.jsp";
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    String id = request.getParameter("id");
                    Paciente v = Operaciones.get(Integer.parseInt(id), new Paciente());
                    Municipio m = Operaciones.get(v.getIdmunicipio(), new Municipio());
                    Empleado e = Operaciones.get(v.getIdempleado(), new Empleado());
                    Sucursal s = Operaciones.get(v.getIdsucursal(), new Sucursal());
                    
                    request.setAttribute("v", v);
                    request.setAttribute("m", m);
                    request.setAttribute("e", e);
                    request.setAttribute("s", s);
                    
                    request.getSession().setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    String id = request.getParameter("id");
                    Paciente p = Operaciones.eliminar(Integer.parseInt(id), new Paciente());
                    
                    int resultado = p.getIdpaciente() != 0 ? 1 : 0;
                    request.getSession().setAttribute("resultado", resultado);                    
                    res = true;
                    
                    Operaciones.commit();
                }break;
                case "sucursales":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT idsucursal, "
                            + "CONCAT(a.direccion,', ', b.municipio,', ', c.departamento) as dir "
                            + "FROM sucursales a, municipios b, departamentos c "
                            + "WHERE "
                            + "a.idmunicipio = b.idmunicipio "
                            + "AND b.iddepartamento = c.iddepartamento";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"COD", "Dirección"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    
                    req += "sucursales.jsp";
                    
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
                case "municipios":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT a.idmunicipio, a.municipio, b.departamento FROM municipios a, departamentos b WHERE a.iddepartamento = b.iddepartamento;";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID", "Municipio", "Departamento"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    
                    sql = "SELECT * FROM departamentos;";
                    rs = Operaciones.consultar(sql, null);
                    List<Departamento> lista2 = new ArrayList();
                    for(int i=0; i<rs[0].length; i++){
                        lista2.add(new Departamento(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]));
                    }
                    request.setAttribute("Departamentos", lista2);
                    
                    req += "municipios.jsp";
                    
                    Operaciones.commit();
                }break;
            }
        }catch(Exception e){
            try {
                Operaciones.rollback();
                res = true;
                request.getSession().setAttribute("resultado", 0);
            } catch (SQLException ex) {
                Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                if(!res)
                    request.getRequestDispatcher(req).forward(request, response);
                else
                    response.sendRedirect(request.getContextPath()+"/Pacientes");
            } catch (SQLException ex) {
                Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("idpaciente") != null ? request.getParameter("idpaciente") : "";
        String expediente = request.getParameter("expediente");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String fecha_nacimiento = request.getParameter("fecha_nacimiento");
        String genero = request.getParameter("genero");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");
        String idmunicipio = request.getParameter("idmunicipio");
        String idempleado = request.getParameter("idempleado");
        String idsucursal = request.getParameter("idsucursal");
        
        int resultado = 1;
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            Paciente p = new Paciente();
            p.setExpediente(Long.parseLong(expediente));
            p.setNombres(nombres);
            p.setApellidos(apellidos);
            p.setFecha_nacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(fecha_nacimiento));
            p.setGenero(genero);
            p.setTelefono(telefono);
            p.setEmail(email);
            p.setIdmunicipio(Integer.parseInt(idmunicipio));
            p.setIdsucursal(Integer.parseInt(idsucursal));
            p.setIdempleado(Integer.parseInt(idempleado));
            if(id != null && !id.equals("")){
                p.setIdpaciente(Integer.parseInt(id));
                p = Operaciones.actualizar(Integer.parseInt(id), p);
            }else{
                p = Operaciones.insertar(p);
            }
            resultado = p.getIdpaciente() != 0 ? 1 : 0;
            
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
                response.sendRedirect(request.getContextPath() + "/Pacientes");
            } catch (SQLException ex) {
                Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
