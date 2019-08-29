package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Departamento;
import com.clinica.models.Municipio;
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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Sucursales", urlPatterns = {"/Sucursales"})
public class Sucursales extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        
        String req = "views/sucursales/sucursales_consulta.jsp";
        String sql = "";
        String[][] rs;
        String[] cabeceras;
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            
            switch(accion){
                case "":
                {
                    Operaciones.iniciarTransaccion();
                    sql = "SELECT "
                        + "a.idsucursal, "
                        + "a.direccion, "
                        + "b.municipio, "
                        + "c.departamento, "
                        + "a.telefono1, "
                        + "a.telefono2, "
                        + "a.email "
                        + "FROM sucursales a, municipios b, departamentos c "
                        + "WHERE a.idmunicipio = b.idmunicipio "
                        + "AND b.iddepartamento = c.iddepartamento;";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{
                        "ID",
                        "Dirección",
                        "Municipio",
                        "Departamento",
                        "Teléfono 1",
                        "Teléfono 2",
                        "Email"
                    };
                    
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Sucursales?accion=modificar");
                    t.setPaginaEliminable("/Sucursales?accion=eliminar");
                    
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();                    
                    request.setAttribute("tabla", tabla);
                    
                    Operaciones.commit();
                }
                break;
                case "insertar":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT * FROM departamentos;";
                    rs = Operaciones.consultar(sql, null);
                    List<Departamento> lista = new ArrayList();
                    for(int i=0; i<rs[0].length; i++){
                        lista.add(new Departamento(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]));
                    }
                    request.setAttribute("Departamentos", lista);
                    
                    sql = "SELECT * FROM municipios;";
                    rs = Operaciones.consultar(sql, null);
                    List<Municipio> lista2 = new ArrayList();
                    for(int i=0; i<rs[0].length; i++){
                        lista2.add(new Municipio(Integer.parseInt(rs[0][i]), rs[1][i], Integer.parseInt(rs[2][i])));
                    }
                    request.setAttribute("Municipios", lista2);
                    
                    req = "views/sucursales/insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }
                break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                                       
                    sql = "SELECT * FROM municipios;";
                    rs = Operaciones.consultar(sql, null);
                    List<Municipio> lista2 = new ArrayList();
                    for(int i=0; i<rs[0].length; i++){
                        lista2.add(new Municipio(Integer.parseInt(rs[0][i]), rs[1][i], Integer.parseInt(rs[2][i])));
                    }
                    request.setAttribute("Municipios", lista2);
                    
                    Sucursal v = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Sucursal());
                    request.setAttribute("v", v);
                    
                    req = "views/sucursales/insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }
                break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    Sucursal s = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Sucursal());
                    int resultado = s.getIdsucursal() != 0 ? 1 : 0;
                    request.getSession().setAttribute("resultado", resultado);
                    
                    Operaciones.commit();
                }
                break;
            }
        }catch(Exception e){
            try {
                Operaciones.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Sucursales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getRequestDispatcher(req).forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Sucursales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id") != null ? request.getParameter("id") : "";
        String idmunicipio = request.getParameter("idmunicipio");
        String direccion = request.getParameter("direccion");
        String telefono1 = request.getParameter("telefono1");
        String telefono2 = request.getParameter("telefono2");
        String email = request.getParameter("email");
        
        int resultado = 1;
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            Sucursal s = new Sucursal();
            s.setIdmunicipio(Integer.parseInt(idmunicipio));
            s.setDireccion(direccion);
            s.setTelefono1(telefono1);
            s.setTelefono2(telefono2);
            s.setEmail(email);
            if(!id.equals("")){
                s.setIdsucursal(Integer.parseInt(id));
                s = Operaciones.actualizar(s.getIdsucursal(), s);
            }else{
                s = Operaciones.insertar(s);
            }
            resultado = s.getIdsucursal() != 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex) {
                Logger.getLogger(Sucursales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                response.sendRedirect(request.getContextPath()+"/Sucursales");
                request.getSession().setAttribute("resultado", resultado);
            } catch (SQLException ex) {
                Logger.getLogger(Sucursales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
