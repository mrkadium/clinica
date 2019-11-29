package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Compra;
import com.clinica.models.Consumible;
import com.clinica.models.Detalle_compra;
import com.clinica.models.Laboratorio;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Tabla;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
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

public class Compras extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String req = "views/compras/", sql = "", rs[][], cabeceras[], tabla;
        boolean res = false;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            switch(accion){
                case "":{
                    sql = 
                        "SELECT\n" +
                        "	a.idcompra, a.fecha, b.nombre,\n" +
                        "    a.subtotal, a.descuentos, a.total\n" +
                        "FROM compras a, laboratorios b\n" +
                        "WHERE\n" +
                        "	a.idlaboratorio = b.idlaboratorio\n" +
                        ";";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID","Fecha","Laboratorio","Subtotal","Descuentos","Total"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setPaginaModificable("/Compras?accion=modificar");
                    t.setEliminable(true);
                    t.setPaginaEliminable("/Compras?accion=eliminar");
                    t.setPageContext(request.getContextPath());
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    req += "compras_consulta.jsp";
                }break;
                case "insertar":{
                    request.setAttribute("op", "Insertar");
                    req += "insertar_modificar.jsp";
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Compra v = Operaciones.get(id, new Compra());
                    request.setAttribute("v", v);
                    Laboratorio l = Operaciones.get(v.getIdlaboratorio(), new Laboratorio());
                    request.setAttribute("l", l);
                    
                    sql = "SELECT * FROM detalles_compra WHERE idcompra = ?";
                    List<Object> params = new ArrayList<>();
                    List<Detalle_compra> Detalles_compra = new ArrayList();
                    params.add(id);
                    rs = Operaciones.consultar(sql, params);
                    Date fecha = new Date();
                    for(int i=0; i<rs[0].length; i++){
                        fecha = new SimpleDateFormat("yyyy-MM-dd").parse(rs[3][i]);
                        Detalle_compra dc = new Detalle_compra(
                            Integer.parseInt(rs[0][i]), Integer.parseInt(rs[1][i]), Integer.parseInt(rs[2][i]), 
                            new Timestamp(fecha.getTime()), Integer.parseInt(rs[4][i]), 
                            new BigDecimal(rs[5][i]), new BigDecimal(rs[6][i]), new BigDecimal(rs[7][i])
                        );
                        Detalles_compra.add(dc);
                    }
                    request.setAttribute("Detalles_compra", Detalles_compra);
                    
                    sql = "SELECT b.* FROM detalles_compra a, consumibles b WHERE a.idconsumible = b.idconsumible "
                            + "AND a.idcompra = ? ORDER BY iddetalle_compra;";
                    rs = Operaciones.consultar(sql, params);
                    List<Consumible> Consumibles = new ArrayList();
                    for(int i=0; i<rs[0].length; i++){
                        Consumible c = new Consumible(Integer.parseInt(rs[0][i]),rs[1][i],
                                rs[2][i],rs[3][i],rs[4][i],Integer.parseInt(rs[5][i]), 
                                new BigDecimal(rs[6][i]), new BigDecimal(rs[7][i]), new BigDecimal(rs[8][i]));
                        Consumibles.add(c);
                    }
                    request.setAttribute("Consumibles", Consumibles);
                    
                    request.setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";                    
                    
                    Operaciones.commit();
                }break;
                case "eliminar":{}break;
                case "laboratorios":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT idlaboratorio, nombre FROM laboratorios;";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID Laboratorio", "Laboratorio"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    
                    req += "laboratorios.jsp";
                    
                    Operaciones.commit();
                }break;
                case "consumibles":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT idconsumible, nombre FROM consumibles WHERE tipo = 'Producto';";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID Consumible", "Consumible"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    
                    req += "consumibles.jsp";
                    
                    Operaciones.commit();
                }break;
                case "agregar":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT idconsumible, nombre FROM consumibles WHERE tipo = 'Producto';";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID Consumible", "Consumible"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    
                    req += "agregar.jsp";
                    
                    Operaciones.commit();
                }break;
            }
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                res = true;
            } catch (SQLException ex1) {
                Logger.getLogger(Compras.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                if(res) response.sendRedirect("Compras");
                else request.getRequestDispatcher(req).forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Compras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idcompra = request.getParameter("idcompra");
        String fecha = request.getParameter("fecha");
        String idlaboratorio = request.getParameter("idlaboratorio");
        
        String[] idconsumibles = request.getParameterValues("idconsumible");
        String[] precios_compra = request.getParameterValues("precio_compra");
        String[] precios_sugerido = request.getParameterValues("precio_sugerido");
        String[] cantidades = request.getParameterValues("cantidad");
        String[] fechas_caducidad = request.getParameterValues("fecha_caducidad");
        
        int resultado = 1;
        
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();
            
            Compra c = new Compra();
            c.setFecha(fecha.equals("") ? new Date() : new SimpleDateFormat("yyyy-MM-dd").parse(fecha));
            c.setIdlaboratorio(Integer.parseInt(idlaboratorio));
            if(idcompra != null && !idcompra.equals("")){
                c.setIdcompra(Integer.parseInt(idcompra));
                c = Operaciones.actualizar(c.getIdcompra(), c);
            }else{
                c = Operaciones.insertar(c); //COMPRA RECIÉN INSERTADA
                
                List<Detalle_compra> lst = new ArrayList();                
                for(int i=0; i<idconsumibles.length; i++){
                    Detalle_compra dc = new Detalle_compra();
                    dc.setIdcompra(c.getIdcompra()); //ID DE COMPRA RECIÉN INSERTADA
                    dc.setIdconsumible(Integer.parseInt(idconsumibles[i]));
                    dc.setPrecio_compra(new BigDecimal(precios_compra[i]));
                    dc.setPrecio_sugerido(new BigDecimal(precios_sugerido[i]));
                    dc.setCantidad(Integer.parseInt(cantidades[i]));
                    dc.setFecha_caducidad(new SimpleDateFormat("yyyy-MM-dd").parse(fechas_caducidad[i]));
                    lst.add(dc);
                }
                
                for(Detalle_compra d: lst){
                    d = Operaciones.insertar(d);
                }
            }
            
//            List<Detalle_compra> lst = new ArrayList();
//            for(int i=0; i<idconsumibles.length; i++){
//                Detalle_compra dc = new Detalle_compra();
//                dc.setIdcompra(c.getIdcompra());
//                dc.setIdconsumible(Integer.parseInt(idconsumibles[i]));
//                dc.setPrecio_compra(Double.parseDouble(precios_compra[i]));
//                dc.setPrecio_sugerido(Double.parseDouble(precios_sugerido[i]));
//                dc.setCantidad(Integer.parseInt(cantidades[i]));
//                dc.setFecha_caducidad(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(fechas_caducidad[i]));
//                lst.add(dc);
//            }
            
            //PARA CUANDO ACTUALICE, NECESITO SABER CUÁLES YA ESTABAN
            /*
            String sql = "SELECT iddetalle_compra FROM detalles_compra WHERE idcompra = ?;";
            List<Object> params = new ArrayList();
            params.add(c.getIdcompra());
            String[][] rs = Operaciones.consultar(sql, params);
            */
            
            
            
            Operaciones.commit();
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex1) {
                Logger.getLogger(Compras.class.getName()).log(Level.SEVERE, null, ex1);
            }            
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                response.sendRedirect("Compras");
            } catch (SQLException ex) {
                Logger.getLogger(Compras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
