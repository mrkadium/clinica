package com.clinica.controllers;

import com.clinica.conexion.Conexion;
import com.clinica.conexion.ConexionPool;
import com.clinica.opearaciones.Operaciones;
import com.clinica.utilerias.Tabla;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Inventarios extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("resultado") != null){
            request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
            request.getSession().removeAttribute("resultado");
        }
        
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
        String req = "views/inventarios/", sql = "", rs[][], cabeceras[], tabla;
        boolean res = false;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            switch(accion){
                case "":{
                    sql = 
                        "SELECT \n" +
                        "	a.iddetalle_inventario, b.nombre, b.tipo,\n" +
                        "    IF(b.tipo = 'Producto', SUM(a.cantidad), 0) as existencias\n" +
                        "FROM detalles_inventario a, consumibles b\n" +
                        "WHERE \n" +
                        "	a.idconsumible = b.idconsumible\n" +
                        "--    AND b.tipo = 'Producto'\n" +
                        "GROUP BY a.idconsumible\n" +
                        "ORDER BY b.tipo DESC\n" +
                        ";";
                    rs = Operaciones.consultar(sql, null);
                    cabeceras = new String[]{"ID","Consumible","Tipo","Existencias"};
                    Tabla t = new Tabla(rs, cabeceras);
                    t.setSeleccionable(true);
                    t.setPaginaSeleccionable("/Inventarios?accion=ver_mas");
                    t.setPageContext(request.getContextPath());
                    tabla = rs != null ? t.getTabla() : t.getEmptyTabla();
                    request.setAttribute("tabla", tabla);
                    req += "inventarios_consulta.jsp";
                }break;
                case "insertar":{
                    req += "insertar_modificar.jsp";
                }break;
                case "modificar":{}break;
                case "eliminar":{}break;
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
        
    }
}
