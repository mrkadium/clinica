package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.models.Cargo;
import com.clinica.models.Departamento;
import com.clinica.models.Empleado;
import com.clinica.models.Municipio;
import com.clinica.models.Sucursal;
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

public class Empleados extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        
        String req = "views/empleados/";
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
                    "	a.idempleado, \n" +
                    "    IF(a.jvpm IS NULL,'-',a.jvpm) AS jvpm, \n" +
                    "    a.codigo,\n" +
                    "    CONCAT(a.nombres,' ',a.apellidos) AS empleado,\n" +
                    "    a.genero, a.fecha_nacimiento, a.dui, a.nit, e.municipio, a.direccion, d.cargo,\n" +
                    "    IF(a.idjefe IS NULL, '-', (SELECT CONCAT(x.nombres,' ',x.apellidos) FROM empleados x WHERE x.idempleado = a.idjefe)) AS jefe,\n" +
                    "    c.codigo AS codigo_sucursal, a.fecha_contratacion, \n" +
                    "    IF(a.fecha_salida IS NULL, '-', a.fecha_salida) AS fecha_salida, a.estado\n" +
                    "FROM empleados a, sucursales c, cargos d, municipios e\n" +
                    "WHERE\n" +
                    "	a.idsucursal = c.idsucursal\n" +
                    "    AND a.idcargo = d.idcargo\n" +
                    "    AND a.idmunicipio = e.idmunicipio\n" +
                    ";";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID","JVPM","Código","Empleado","Género","F_Nac","DUI","NIT", "Municipio", "Dirección", "Cargo", "Jefe", "Cód. suc", "F_Contrata.", "F_Salida", "Estado"};
                    
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setModificable(true);
                    t.setEliminable(true);
                    t.setPageContext(request.getContextPath());
                    t.setPaginaModificable("/Empleados?accion=modificar");
                    t.setPaginaEliminable("/Empleados?accion=eliminar");
                    
                    String tabla = rs != null ? t.getTabla() : t.getEmptyTabla();                    
                    request.setAttribute("tabla", tabla);
                    
                    req += "empleados_consulta.jsp";
                    
                    Operaciones.commit();
                }break;
                case "insertar":{
                    Operaciones.iniciarTransaccion();

                    sql = "SELECT * FROM cargos;";
                    rs = Operaciones.consultar(sql, null);
                    List<Cargo> lista2 = new ArrayList();
                    for(int i=0; i<rs[0].length; i++){
                        lista2.add(new Cargo(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]));
                    }
                    request.setAttribute("Cargos", lista2);

                    request.getSession().setAttribute("op", "Insertar");
                    req += "insertar_modificar.jsp";

                    Operaciones.commit();
                }break;
                case "modificar":{
                    Operaciones.iniciarTransaccion();
                    
                    String id = request.getParameter("id");
                    Empleado v = Operaciones.get(Integer.parseInt(id), new Empleado());
                    
                    request.setAttribute("v", v);
                    
                    Sucursal s = Operaciones.get(v.getIdsucursal(), new Sucursal());
                    request.setAttribute("s", s);
                    Municipio m = Operaciones.get(v.getIdmunicipio(), new Municipio());
                    request.setAttribute("m", m);                    
                    Empleado jefe = new Empleado();
                    if(v.getIdjefe() != null){
                        jefe = Operaciones.get(v.getIdjefe(), new Empleado());
                        request.setAttribute("jefe", jefe);
                    }
                    
                    request.setAttribute("Cargos", Select.cargos());
                    
                    request.getSession().setAttribute("op", "Modificar");
                    req += "insertar_modificar.jsp";
                    
                    Operaciones.commit();
                }break;
                case "eliminar":{
                    Operaciones.iniciarTransaccion();
                    
                    String id = request.getParameter("id");
                    Empleado p = Operaciones.eliminar(Integer.parseInt(id), new Empleado());
                    
                    int resultado = p.getIdempleado() != 0 ? 1 : 0;
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
                case "jefes":{
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
                    
                    request.setAttribute("Departamentos", Select.departamentos());
                    
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
                    response.sendRedirect(request.getContextPath()+"/Empleados");
            } catch (SQLException ex) {
                Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idempleado = request.getParameter("idempleado");
        String idjefe = request.getParameter("idjefe");
        String idsucursal = request.getParameter("idsucursal");
        String idcargo = request.getParameter("idcargo");
        String codigo = request.getParameter("codigo");
        String jvpm = request.getParameter("jvpm");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String genero = request.getParameter("genero");
        String fecha_nacimiento = request.getParameter("fecha_nacimiento");
        String dui = request.getParameter("dui");
        String nit = request.getParameter("nit");
        String idmunicipio = request.getParameter("idmunicipio");
        String direccion = request.getParameter("direccion");
        String fecha_contratacion = request.getParameter("fecha_contratacion");
        String fecha_salida = request.getParameter("fecha_salida");
        String estado = request.getParameter("estado");
        
        int resultado = 1;        
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();
            
            Empleado e = new Empleado();
            if(idjefe != null && !idjefe.equals(""))
                e.setIdjefe(Integer.parseInt(idjefe));
            
            e.setIdsucursal(Integer.parseInt(idsucursal));
            e.setIdcargo(Integer.parseInt(idcargo));
            e.setCodigo(codigo);
            if(jvpm != null && !jvpm.equals(""))
                e.setJvpm(Integer.parseInt(jvpm));
            
            e.setNombres(nombres);
            e.setApellidos(apellidos);
            e.setGenero(genero);
            e.setFecha_nacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(fecha_nacimiento));
            e.setDui(dui);
            e.setNit(nit);
            e.setIdmunicipio(Integer.parseInt(idmunicipio));
            e.setDireccion(direccion);
            e.setFecha_contratacion(new SimpleDateFormat("yyyy-MM-dd").parse(fecha_contratacion));
            if(fecha_salida != null && !fecha_salida.equals(""))
                e.setFecha_salida(new SimpleDateFormat("yyyy-MM-dd").parse(fecha_salida));
            
            e.setEstado(estado);
            if(idempleado != null && !idempleado.equals("")){
                e.setIdempleado(Integer.parseInt(idempleado));
                e = Operaciones.actualizar(e.getIdempleado(), e);
            }else{
                e = Operaciones.insertar(e);
            }
            resultado = e.getIdempleado() != 0 ? 1 : 0;
            
            Operaciones.commit();
        }catch(Exception ex){
            try {
                Operaciones.rollback();
                resultado = 0;
            } catch (SQLException ex1) {
                Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
                request.getSession().setAttribute("resultado", resultado);
                response.sendRedirect(request.getContextPath() + "/Empleados");
            } catch (SQLException ex) {
                Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
