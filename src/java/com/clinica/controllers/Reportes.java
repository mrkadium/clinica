package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Paciente;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Tabla;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

public class Reportes extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = getServletConfig().getInitParameter("accion") != null ? getServletConfig().getInitParameter("accion") : request.getParameter("accion");
        if(accion != null && request.getParameter("idpaciente") == null){
            if(accion.equals("buenaSalud")){
                request.setAttribute("op", "buena_salud");
            }else if(accion.equals("incapacidad")){
                request.setAttribute("op", "incapacidad");
            }else if(accion.equals("medica")){
                request.setAttribute("op", "medica");
            }
            request.getRequestDispatcher("views/reportes/insertar_modificar.jsp").forward(request, response);
        }else if(accion != null && request.getParameter("idpaciente") != null){
            try{            
                Conexion cn = new ConexionPool();
                cn.conectar();
                Connection conexion=cn.getConexion();
                Operaciones.abrirConexion(cn);
                Operaciones.iniciarTransaccion();

                ServletContext context = request.getServletContext();
                File reportFile = null;
                String reportName = "";
                Map parameters = new HashMap();

                int idpaciente = Integer.parseInt(request.getParameter("idpaciente"));
                int iddoctor = Integer.parseInt(request.getParameter("iddoctor"));
                String dias = request.getParameter("dias");
                String enfermedad = request.getParameter("diagnostico");
                Paciente p = Operaciones.get(idpaciente, new Paciente());

                try{
                    String path = null;
                    if(accion.equals("buenaSalud")){
                        path = context.getRealPath("/")+"reports/ConstanciaBuenaSalud.jasper";
                        parameters.put("iddoctor", iddoctor);
                        parameters.put("idpaciente", p.getIdpaciente());
                        reportName = "ConsBuenaSalud.pdf";
                    }else if(accion.equals("incapacidad")){
                        path = context.getRealPath("/")+"reports/ConstanciaIncapacidad.jasper";
                        parameters.put("iddoctor", 1);
                        parameters.put("idpaciente", p.getIdpaciente());
                        parameters.put("dias", Integer.parseInt(dias));
                        parameters.put("enfermedad", enfermedad);
                        reportName = "ConsIncapacidad.pdf";
                    }else if(accion.equals("medica")){
                        path = context.getRealPath("/")+"reports/ConstanciaMedica.jasper";
                        parameters.put("iddoctor", 1);
                        parameters.put("idpaciente", p.getIdpaciente());
                        parameters.put("enfermedad", enfermedad);
                        reportName = "ConsMedica.pdf";
                    }else if(accion.equals("ventas")){

                    }
                    reportFile = new File(path); 

                    byte[] bytes = null;
                    try {
                        bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),parameters,conexion);
                    } catch (JRException ex) {
                        request.getSession().setAttribute("error","No se puede compilar");
                        response.sendRedirect(request.getContextPath()+"/Home");
                        Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachment; filename="+reportName);
                    if(bytes != null){
                        response.setContentLength(bytes.length);
                        try (ServletOutputStream outputStream = response.getOutputStream()) {
                            outputStream.write(bytes, 0, bytes.length);
                            outputStream.flush();
                        }catch(Exception ex){
                            ex.getMessage();
                        }
                    }else{
                        request.getSession().setAttribute("error","bytes = null");
                        response.sendRedirect(request.getContextPath()+"/Home");
                    }

                }catch(Exception ex){
                    ex.getMessage();
                }

                Operaciones.commit();
            }catch(Exception ex){
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }finally{
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(accion == null){
            request.getRequestDispatcher("views/reportes/insertar_modificar.jsp").forward(request,response);
        }else if(accion.equals("consultas")){
        
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    

}
