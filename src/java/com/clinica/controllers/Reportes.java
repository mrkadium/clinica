package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.opearaciones.Operaciones;
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
            String accion = request.getParameter("accion");
            
            try{
                String path = null;
                reportFile = new File(path); 
                if(accion.equals("buenaSalud")){
                    path = context.getRealPath("/")+"reports/ConstanciaBuenaSalud.jasper";
                    parameters.put("ciclo", "");
                }else if(accion.equals("incapacidad")){
                    path = context.getRealPath("/")+"reports/ConstanciaIncapacidad.jasper";
                    parameters.put("ciclo", "");
                }
                
                byte[] bytes = null;
                try {
                    bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),parameters,conexion);
                } catch (JRException ex) {
                    request.getSession().setAttribute("error","No se puede compilar");
                    response.sendRedirect(request.getContextPath()+"/Home");
                    Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename="+reportName);
                if(bytes != null){
                    response.setContentLength(bytes.length);
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.flush();
                    }
                }else{
                    request.getSession().setAttribute("error","bytes = null");
                    response.sendRedirect(request.getContextPath()+"/Home");
                }
                
            }catch(Exception ex){
                
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
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    

}
