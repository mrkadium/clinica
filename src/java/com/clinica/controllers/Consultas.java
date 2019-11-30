package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Consulta;
import com.clinica.models.Consumible;
import com.clinica.models.Detalle_consulta;
import com.clinica.models.Empleado;
import com.clinica.models.Paciente;
import com.clinica.models.viewmodels.VMEmpleado_consulta;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Select;
import com.clinica.utilerias.Tabla;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Consultas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("resultado") != null) {
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }

        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String req = "views/consultas/";
        String sql = "";
        String[][] rs;
        String[] cabeceras;
        String tabla = "";
        boolean res = false;
        List<Object> params = new ArrayList();
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);

            switch (accion) {
                case "": {
                    Operaciones.iniciarTransaccion();

                    sql = "SELECT\n" +
                            "    a.idconsulta, a.fecha_hora, \n" +
                            "    CONCAT(b.nombres,' ',b.apellidos) as paciente,\n" +
                            "    c.nombre, IF(a.iddoctor IS NULL,'-',CONCAT(d.nombres,' ',d.apellidos)) as doctor, a.programada, a.estado\n" +
                            "FROM consultas a\n" +
                            "INNER JOIN pacientes b ON a.idpaciente = b.idpaciente\n" +
                            "INNER JOIN consumibles c ON a.idservicio = c.idconsumible\n" +
                            "LEFT JOIN empleados d ON a.iddoctor = d.idempleado\n" +
                            "ORDER BY a.fecha_hora\n" +
                            ";";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID", "Fecha/Hora", "Paciente", "Servicio", "Doctor", "Programada", "Estado"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setSeleccionable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Consultas?accion=modificar");
                    t.setPaginaEliminable("/Consultas?accion=eliminar");
                    t.setIconoSeleccionable(Tabla.ICON.ACEPTAR);
                    t.setCabeceraSeleccionable(Tabla.ICON.ACEPTAR);
                    t.setPaginaSeleccionable("/Consultas?accion=aceptar");
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);

                    req += "consultas_consulta.jsp";

                    Operaciones.commit();
                }
                break;
                case "insertar": {
                    request.setAttribute("op", "Insertar");
                    request.setAttribute("Horas", Select.HORAS());
                    request.setAttribute("Minutos", Select.MINUTOS());
                    
                    req += "insertar_modificar.jsp";
                }
                break;
                case "modificar": {
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Consulta v = Operaciones.get(id, new Consulta());
                    Paciente p = Operaciones.get(v.getIdpaciente(), new Paciente());
                    Consumible c = Operaciones.get(v.getIdservicio(), new Consumible());
                    if(v.getIddoctor() != null){
                        Empleado e = Operaciones.get(v.getIddoctor(), new Empleado());
                        request.setAttribute("e", e);}
                    request.setAttribute("v", v);
                    request.setAttribute("p", p);
                    request.setAttribute("c", c);
                    
                    request.setAttribute("Horas", Select.HORAS());
                    request.setAttribute("Minutos", Select.MINUTOS());
                    
                    
                    
                    sql = 
                        "SELECT \n" +
                        "	a.idempleado_consulta, a.idempleado, \n" +
                        "    CONCAT(b.nombres,' ',b.apellidos) AS empleado,\n" +
                        "    c.cargo\n" +
                        "FROM empleados_consulta a, empleados b, cargos c\n" +
                        "WHERE\n" +
                        "	a.idempleado = b.idempleado\n" +
                        "    AND b.idcargo = c.idcargo\n" +
                        "    AND a.idconsulta = ?\n" +
                        ";";
                    params.add(v.getIdconsulta());
                    rs = Operaciones.consultar(sql, params);
                    if(rs != null){
                        List<VMEmpleado_consulta> Empleados = new ArrayList();
                        for(int i=0; i<rs[0].length; i++){
                            VMEmpleado_consulta em = new VMEmpleado_consulta(Integer.parseInt(rs[0][i]), Integer.parseInt(rs[1][i]), rs[2][i], rs[3][i]);
                            Empleados.add(em);
                        }
                        request.setAttribute("Empleados_consulta",Empleados);
                    }
                    
                    
                    request.setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }
                break;
                case "eliminar": {
                }
                break;
                case "aceptar": {
                    Operaciones.iniciarTransaccion();

                    int id = Integer.parseInt(request.getParameter("id"));
                    sql = "SELECT iddetalle_consulta FROM detalles_consulta WHERE idconsulta = ?";
                    params.add(id);
                    rs = Operaciones.consultar(sql, params);
                    if (rs != null) {
                        Detalle_consulta d = Operaciones.get(Integer.parseInt(rs[0][0]), new Detalle_consulta());
                        request.setAttribute("v", d);

                        request.setAttribute("op", "Modificar");
                    } else {
                        request.setAttribute("op", "Insertar");
                        request.setAttribute("idconsulta", id);
                    }
                    req += "detalles_consulta.jsp";

                    Operaciones.commit();
                }
                break;
                case "pacientes": {
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT idpaciente, CONCAT(nombres, apellidos) AS paciente FROM pacientes;";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID Paciente", "Paciente"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    
                    req += "pacientes.jsp";
                    
                    Operaciones.commit();
                }break;
                case "servicios": {
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT idconsumible, nombre FROM consumibles WHERE tipo = 'Servicio';";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID Servicio", "Servicio"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    
                    req += "servicios.jsp";
                    
                    Operaciones.commit();
                }break;
                case "doctores": {
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT idempleado, CONCAT(nombres,' ',apellidos) AS empleado FROM empleados WHERE jvpm IS NOT NULL;";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID Doctor", "Doctor"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    
                    req += "doctores.jsp";
                    
                    Operaciones.commit();
                }break;
                case "agregarEmpleado":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = 
                        "SELECT \n" +
                        "	b.idempleado, \n" +
                        "    CONCAT(b.nombres,' ',b.apellidos) AS empleado,\n" +
                        "    c.cargo\n" +
                        "FROM  empleados b, cargos c\n" +
                        "WHERE\n" +
                        "	b.idcargo = c.idcargo\n" +
                        ";";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID Empleado", "Empleado", "Cargo"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    
                    req += "empleadosConsulta.jsp";
                    
                    Operaciones.commit();
                }break;
            }
        } catch (Exception e) {
            try {
                Operaciones.rollback();
                res = true;
            } catch (SQLException ex) {
                Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                Operaciones.cerrarConexion();
                if (!res) {
                    request.getRequestDispatcher(req).forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/Consultas");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = getServletConfig().getInitParameter("accion") != null ? getServletConfig().getInitParameter("accion") : "";
        int resultado = 1;

        try {
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);

            switch (accion) {
                case "": {
                    Operaciones.iniciarTransaccion();
                    
                    String idconsulta = request.getParameter("idconsulta");
                    String idpaciente = request.getParameter("idpaciente");
                    String idservicio = request.getParameter("idservicio");
                    String iddoctor = request.getParameter("iddoctor");
                    String fecha = request.getParameter("fecha");
                    String hora = request.getParameter("hora");
                    String min = request.getParameter("min");
                    boolean programada = fecha.equals("");
                    String estado = request.getParameter("estado");
                    String fecha_hora = fecha+" "+hora+":"+min;
                    
                    Consulta c = new Consulta();
                    c.setIdpaciente(Integer.parseInt(idpaciente));
                    c.setIdservicio(Integer.parseInt(idservicio));
                    if(iddoctor != null && !iddoctor.equals(""))
                        c.setIddoctor(Integer.parseInt(iddoctor));
                    c.setFecha_hora(fecha.equals("") ? new Date() : new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(fecha_hora));
                    c.setProgramada(programada);
                    c.setEstado(estado);
                    if(idconsulta != null && !idconsulta.equals("")){
                        c.setIdconsulta(Integer.parseInt(idconsulta));
                        c = Operaciones.actualizar(c.getIdconsulta(), c);
                    }else{
                        c = Operaciones.insertar(c);
                    }
                    resultado = c.getIdconsulta() != 0 ? 1 : 0;
                    
                    Operaciones.commit();
                }
                break;
                case "detalle_consulta": {
                    Operaciones.iniciarTransaccion();
                    
                    String iddetalle_consulta = request.getParameter("iddetalle_consulta");
                    String idventa = request.getParameter("idventa");
                    String idconsulta = request.getParameter("idconsulta");
                    String razon_consulta = request.getParameter("razon_consulta");
                    String temperatura = request.getParameter("temperatura");
                    String frecuencia_cardiaca = request.getParameter("frecuencia_cardiaca");
                    String frecuencia_respiratoria = request.getParameter("frecuencia_respiratoria");
                    String presion_arterial = request.getParameter("presion_arterial");
                    String saturacion_oxigeno = request.getParameter("saturacion_oxigeno");
                    String diagnostico = request.getParameter("diagnostico");
                    String tratamiento = request.getParameter("tratamiento");
                    String observaciones = request.getParameter("observaciones");
                    
                    Detalle_consulta c = new Detalle_consulta();
                    if(!idventa.equals(""))
                        c.setIdventa(Integer.parseInt(idventa));
                    c.setIdconsulta(Integer.parseInt(idconsulta));
                    c.setRazon_consulta(razon_consulta);
                    c.setTemperatura(temperatura);
                    c.setFrecuencia_cardiaca(frecuencia_cardiaca);
                    c.setFrecuencia_respiratoria(frecuencia_respiratoria);
                    c.setPresion_arterial(presion_arterial);
                    c.setSaturacion_oxigeno(saturacion_oxigeno);
                    c.setDiagnostico(diagnostico);
                    c.setTratamiento(tratamiento);
                    c.setObservaciones(observaciones);
                    
                    if(iddetalle_consulta != null && !iddetalle_consulta.equals("")){
                        c.setIddetalle_consulta(Integer.parseInt(iddetalle_consulta));
                        c = Operaciones.actualizar(c.getIddetalle_consulta(), c);
                    }else{
                        c = Operaciones.insertar(c);
                    }
                    resultado = c.getIddetalle_consulta() != 0 ? 1 : 0;
                    
                    Operaciones.commit();
                }
                break;
            }
        } catch (Exception ex) {
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex1) {
                Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                response.sendRedirect("Consultas");
            } catch (SQLException ex) {
                Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
