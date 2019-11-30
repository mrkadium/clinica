package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Abono;
import com.clinica.models.Consumible;
import com.clinica.models.Departamento;
import com.clinica.models.Detalle_venta;
import com.clinica.models.Empleado;
import com.clinica.models.Municipio;
import com.clinica.models.Paciente;
import com.clinica.models.Venta;
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

public class Ventas extends HttpServlet {
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String req = "views/ventas/", sql = "", rs[][], cabeceras[], tabla;
        boolean res = false;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            switch(accion){
                case "":{
                    sql = 
                        "SELECT * FROM ventas;";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID","Número","Fecha","ID Pac.","Paciente","Dir.","Tel.","ID Emp.","Subt.","Desc.","Total","Deuda","Estado"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setSeleccionable(true);
                    t.setPaginaSeleccionable("/Ventas?accion=ver_mas");
                    t.setModificable(true);
                    t.setPaginaModificable("/Ventas?accion=modificar");
                    t.setEliminable(true);
                    t.setPaginaEliminable("/Ventas?accion=eliminar");
                    t.setPageContext(request.getContextPath());
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    req += "ventas_consulta.jsp";
                }break;
                case "insertar":{
                    request.setAttribute("op", "Insertar");
                    req += "insertar_modificar.jsp";
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    int id = Integer.parseInt(request.getParameter("id"));
                    Venta v = Operaciones.get(id, new Venta());
                    request.setAttribute("v", v);
                    Paciente p = Operaciones.get(v.getIdpaciente(), new Paciente());
                    request.setAttribute("p", p);
                    Municipio m = Operaciones.get(p.getIdmunicipio(), new Municipio());
                    request.setAttribute("m", m);
                    Departamento d = Operaciones.get(m.getIddepartamento(), new Departamento());
                    request.setAttribute("d", d);
                    
                    sql = "SELECT * FROM detalles_venta WHERE idventa = ?";
                    List<Object> params = new ArrayList<>();
                    List<Detalle_venta> Detalles_venta = new ArrayList();
                    params.add(id);
                    rs = Operaciones.consultar(sql, params);
                    Date fecha = new Date();
                    for(int i=0; i<rs[0].length; i++){
                        Detalle_venta dc = new Detalle_venta(
                            Integer.parseInt(rs[0][i]), Integer.parseInt(rs[1][i]), Integer.parseInt(rs[2][i]), 
                            Integer.parseInt(rs[3][i]), new BigDecimal(rs[4][i]), 
                            new BigDecimal(rs[5][i]), new BigDecimal(rs[6][i]), new BigDecimal(rs[7][i]),
                            new BigDecimal(rs[8][i])
                        );
                        Detalles_venta.add(dc);
                    }
                    request.setAttribute("Detalles_venta", Detalles_venta);
                    
                    sql = "SELECT b.* FROM detalles_venta a, consumibles b WHERE a.idconsumible = b.idconsumible "
                            + "AND a.idventa = ? ORDER BY iddetalle_venta;";
                    rs = Operaciones.consultar(sql, params);
                    List<Consumible> Consumibles = new ArrayList();
                    for(int i=0; i<rs[0].length; i++){
                        Consumible c = new Consumible(Integer.parseInt(rs[0][i]),rs[1][i],
                                rs[2][i],rs[3][i],rs[4][i],Integer.parseInt(rs[5][i]), 
                                new BigDecimal(rs[6][i]), new BigDecimal(rs[7][i]), new BigDecimal(rs[8][i]));
                        Consumibles.add(c);
                    }
                    request.setAttribute("Consumibles", Consumibles);
                    
                    sql = "SELECT * FROM abonos WHERE idventa = ?";
                    List<Abono> Abonos = new ArrayList();
                    rs = Operaciones.consultar(sql, params);
                    for(int i=0; i<rs[0].length; i++){
                        fecha = new SimpleDateFormat("yyyy-MM-dd").parse(rs[2][i]);
                        Abono a = new Abono(Integer.parseInt(rs[0][i]), Integer.parseInt(rs[1][i]), new Timestamp(fecha.getTime()), new BigDecimal(rs[3][0]));
                        Abonos.add(a);
                    }
                    request.setAttribute("Abonos", Abonos);
                    
                    
                    request.setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";                    
                    
                    Operaciones.commit();
                }break;
                case "eliminar":{}break;
                case "consumibles":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = "SELECT idconsumible, nombre, tipo FROM consumibles;";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID Consumible", "Consumible", "Tipo"};
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
                    
                    sql = 
                        "SELECT \n" +
                        "	a.idconsumible, b.nombre, b.tipo,\n" +
                        "    IF(b.tipo = 'Producto', SUM(a.cantidad), 1) as existencias,\n" +
                        "    b.precio_venta\n" +
                        "FROM detalles_inventario a, consumibles b\n" +
                        "WHERE \n" +
                        "	a.idconsumible = b.idconsumible\n" +
                        "--    AND b.tipo = 'Producto'\n" +
                        "GROUP BY a.idconsumible\n" +
                        "ORDER BY b.tipo DESC\n" +
                        ";";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID", "Consumible", "Tipo", "Cantidad", "Precio"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    
                    req += "agregar.jsp";
                    
                    Operaciones.commit();
                }break;
                case "pacientes":{
                    Operaciones.iniciarTransaccion();
                    
                    sql = 
                        "SELECT\n" +
                        "	a.idpaciente,\n" +
                        "	CONCAT(a.nombres,' ', a.apellidos) AS paciente,\n" +
                        "    CONCAT(b.municipio, ', ', c.departamento) AS direccion\n" +
                        "FROM pacientes a, municipios b, departamentos c\n" +
                        "WHERE\n" +
                        "	a.idmunicipio = b.idmunicipio\n" +
                        "    AND b.iddepartamento = c.iddepartamento;";
                    rs = Operaciones.consultar(sql, null);
                    
                    cabeceras = new String[]{"ID", "Paciente", "Dirección"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setFilaSeleccionable(true);
                    t.setMetodoFilaSeleccionable("_Seleccionar_");
                    
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    
                    req += "pacientes.jsp";
                    
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
                if(res) response.sendRedirect("Ventas");
                else request.getRequestDispatcher(req).forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Compras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idventa = request.getParameter("idventa");
        String fecha = request.getParameter("fecha");
        String idpaciente = request.getParameter("idpaciente");
        String paciente = request.getParameter("nombre_paciente");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        
        String[] idconsumibles = request.getParameterValues("idconsumible");
        String[] cantidades = request.getParameterValues("cantidad");
        
        String[] montos = request.getParameterValues("montito");
        String[] fechas = request.getParameterValues("fechita");
        String[] idmontos = request.getParameterValues("idmonto");
        
        int resultado = 1;
        
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();
            
            Empleado e = (Empleado)request.getSession().getAttribute("Empleado");
            
            Venta v = new Venta();
            v.setFecha(fecha.equals("") ? new Date() : new SimpleDateFormat("yyyy-MM-dd").parse(fecha));
            v.setIdpaciente(Integer.parseInt(idpaciente));
            v.setPaciente(paciente);
            v.setDireccion(direccion);
            v.setTelefono(telefono);
            v.setIdempleado(e.getIdempleado());
            if(idventa != null && !idventa.equals("")){
                v.setIdventa(Integer.parseInt(idventa));
//                v = Operaciones.actualizar(v.getIdventa(), v);
                
                List<Abono> lstAbonosI = new ArrayList();
                List<Abono> lstAbonosM = new ArrayList();
                for(int i=0; i<montos.length; i++){
                    Abono a = new Abono();
                    a.setIdventa(v.getIdventa());
                    a.setFecha(new Date());
                    a.setMonto(new BigDecimal(montos[i]));
                    if(idmontos[i].equals("0"))
                        lstAbonosI.add(a);
                    else
                        lstAbonosM.add(a);
                }
                
                for(Abono a: lstAbonosI){
                    a = Operaciones.insertar(a);
                }
            }else{
                v = Operaciones.insertar(v); //COMPRA RECIÉN INSERTADA
                
                List<Detalle_venta> lst = new ArrayList();                
                for(int i=0; i<idconsumibles.length; i++){
                    Detalle_venta dc = new Detalle_venta();
                    dc.setIdventa(v.getIdventa()); //ID DE COMPRA RECIÉN INSERTADA
                    dc.setIdconsumible(Integer.parseInt(idconsumibles[i]));
                    dc.setCantidad(Integer.parseInt(cantidades[i]));
                    lst.add(dc);
                }                
                for(Detalle_venta d: lst){
                    d = Operaciones.insertar(d);
                }
                
                List<Abono> lstAbonosI = new ArrayList();
                List<Abono> lstAbonosM = new ArrayList();
                for(int i=0; i<montos.length; i++){
                    Abono a = new Abono();
                    a.setIdventa(v.getIdventa());
                    a.setFecha(new Date());
                    a.setMonto(new BigDecimal(montos[i]));
                    if(idmontos[i].equals("0"))
                        lstAbonosI.add(a);
                    else
                        lstAbonosM.add(a);
                }
                
                for(Abono a: lstAbonosI){
                    a = Operaciones.insertar(a);
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
                Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex1);
            }            
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                response.sendRedirect("Ventas");
            } catch (SQLException ex) {
                Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
