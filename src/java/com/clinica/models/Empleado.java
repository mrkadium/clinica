package com.clinica.models;

import com.clinica.annotations.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(table="empleados")
public class Empleado {
    @PrimaryKey
    @AutoIncrement
    private int idempleado;
    private Integer idjefe;
    private int idsucursal;
    private int idcargo;
    private String codigo;
    private Integer jvpm;
    private String nombres;
    private String apellidos;
    private String genero;
    private Date fecha_nacimiento;
    private String dui;
    private String nit;
    private int idmunicipio;
    private String direccion;
    private Date fecha_contratacion;
    private Date fecha_salida;
    private String estado;

    public Empleado(int idempleado, Integer idjefe, int idsucursal, int idcargo, String codigo, Integer jvpm, String nombres, String apellidos, String genero, Date fecha_nacimiento, String dui, String nit, int idmunicipio, String direccion, Date fecha_contratacion, Date fecha_salida, String estado) {
        this.idempleado = idempleado;
        this.idjefe = idjefe;
        this.idsucursal = idsucursal;
        this.idcargo = idcargo;
        this.codigo = codigo;
        this.jvpm = jvpm;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.genero = genero;
        this.fecha_nacimiento = fecha_nacimiento;
        this.dui = dui;
        this.nit = nit;
        this.idmunicipio = idmunicipio;
        this.direccion = direccion;
        this.fecha_contratacion = fecha_contratacion;
        this.fecha_salida = fecha_salida;
        this.estado = estado;
    }

    public Empleado() throws ParseException {
        codigo = "";
//        fecha_salida = new SimpleDateFormat("yyyy-MM-dd").parse("1111-11-11");
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public Integer getIdjefe() {
        return idjefe;
    }

    public void setIdjefe(Integer idjefe) {
        this.idjefe = idjefe;
    }

    public int getIdsucursal() {
        return idsucursal;
    }

    public void setIdsucursal(int idsucursal) {
        this.idsucursal = idsucursal;
    }

    public int getIdcargo() {
        return idcargo;
    }

    public void setIdcargo(int idcargo) {
        this.idcargo = idcargo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getJvpm() {
        return jvpm;
    }

    public void setJvpm(Integer jvpm) {
        this.jvpm = jvpm;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public int getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(int idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha_contratacion() {
        return fecha_contratacion;
    }

    public void setFecha_contratacion(Date fecha_contratacion) {
        this.fecha_contratacion = fecha_contratacion;
    }

    public Date getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(Date fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
