package com.clinica.models;
import com.clinica.annotations.*;
import java.util.Date;

@Entity(table="Pacientes")
public class Paciente {
    @PrimaryKey
    @AutoIncrement
    private int idpaciente;
    private long expediente;
    private String nombres;
    private String apellidos;
    private Date fecha_nacimiento;
    private String genero;
    private String telefono;
    private String email;
    private int idmunicipio;
    private String codigo_sucursal;
    private String dui_empleado;

    public Paciente() {
    }

    public Paciente(int idpaciente, long expediente, String nombres, String apellidos, Date fecha_nacimiento, String genero, String telefono, String email, int idmunicipio, String codigo_sucursal, String dui_empleado) {
        this.idpaciente = idpaciente;
        this.expediente = expediente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fecha_nacimiento = fecha_nacimiento;
        this.genero = genero;
        this.telefono = telefono;
        this.email = email;
        this.idmunicipio = idmunicipio;
        this.codigo_sucursal = codigo_sucursal;
        this.dui_empleado = dui_empleado;
    }
    
    public int getIdpaciente(){
        return idpaciente;
    }

    public void setIdpaciente(int idpaciente) {
        this.idpaciente = idpaciente;
    }

    public long getExpediente() {
        return expediente;
    }

    public void setExpediente(long expediente) {
        this.expediente = expediente;
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

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(int idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    public String getCodigo_sucursal() {
        return codigo_sucursal;
    }

    public void setCodigo_sucursal(String codigo_sucursal) {
        this.codigo_sucursal = codigo_sucursal;
    }

    public String getDui_empleado() {
        return dui_empleado;
    }

    public void setDui_empleado(String dui_empleado) {
        this.dui_empleado = dui_empleado;
    }

    
    
}
