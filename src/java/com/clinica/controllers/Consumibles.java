package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Consumible;
import com.clinica.models.Marca;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Tabla;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Consumibles extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado",request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String sql, rs[][], cabeceras[], tabla, req = "views/consumibles/";
        boolean res = false;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            switch(accion){
                case "":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT\n" +
                    "	a.idconsumible, a.tipo, a.nombre, a.alias,\n" +
                    "    a.presentacion, b.marca, \n" +
                    "    CONCAT('$ ',a.precio_compra) AS precio_compra,\n" +
                    "    CONCAT('$ ',a.precio_sugerido) AS precio_sugerido,\n" +
                    "    CONCAT('$ ',a.precio_venta) AS precio_venta\n" +
                    "FROM consumibles a, marcas b\n" +
                    "WHERE a.idmarca = b.idmarca;";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID", "Tipo", "Nombre", "Alias", "Present.", "Marca", "$ Compra", "$ Sugerido", "$ Venta"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Consumibles?accion=modificar");
                    t.setPaginaEliminable("/Consumibles?accion=eliminar");
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    req += "consumibles_consulta.jsp";
                    
                    Operaciones.commit();
                }break;
                case "insertar":{
                    request.setAttribute("op", "Insertar");
                    req += "insertar_modificar.jsp";
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Consumible v = Operaciones.get(id, new Consumible());
                    Marca m = Operaciones.get(v.getIdmarca(), new Marca());
                    request.setAttribute("v", v);
                    request.setAttribute("m", m);
                    request.setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Consumible v = Operaciones.eliminar(id, new Consumible());
                    int resultado = v.getIdconsumible() != 0 ? 1 : 0;
                    request.getSession().setAttribute("resultado", resultado);
                    res = true;
                    
                    Operaciones.commit();
                }break;
                case "marcas":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT * FROM marcas;";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID Marca", "Marca"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    req += "marcas.jsp";
                    
                    Operaciones.commit();
                }break;
            }
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                res = true;
            } catch (SQLException ex1) {
                Logger.getLogger(Consumibles.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                if(res) response.sendRedirect("Consumibles");
                else request.getRequestDispatcher(req).forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Consumibles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idconsumible = request.getParameter("idconsumible");
        String tipo = request.getParameter("tipo");
        String nombre = request.getParameter("nombre");
        String alias = request.getParameter("alias");
        String presentacion = request.getParameter("presentacion");
        String idmarca = request.getParameter("idmarca");
        String precio_compra = request.getParameter("precio_compra");
        String precio_sugerido = request.getParameter("precio_sugerido");
        String precio_venta = request.getParameter("precio_venta");
        int resultado = 1;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();
            
            Consumible c = new Consumible();
            c.setTipo(tipo);
            c.setNombre(nombre);
            c.setAlias(alias);
            c.setPresentacion(presentacion);
            c.setIdmarca(Integer.parseInt(idmarca));
            c.setPrecio_compra(new BigDecimal(precio_compra));
            c.setPrecio_sugerido(new BigDecimal(precio_sugerido));
            c.setPrecio_venta(new BigDecimal(precio_venta));
            if(idconsumible != null && !idconsumible.equals("")){
                c.setIdconsumible(Integer.parseInt(idconsumible));
                c = Operaciones.actualizar(c.getIdconsumible(), c);
            }else
                c = Operaciones.insertar(c);
            
            resultado = c.getIdconsumible() != 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex1) {
                Logger.getLogger(Consumibles.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                response.sendRedirect("Consumibles");
            } catch (SQLException ex) {
                Logger.getLogger(Consumibles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
