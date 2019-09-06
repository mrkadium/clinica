package com.clinica.models;
import com.clinica.annotations.*;
import java.sql.Date;

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
    private int idsucursal;
    private int idempleado;

    public Paciente() {
    }

    public int getIdpaciente() {
        return idpaciente;
    }

    public Paciente(int idpaciente, long expediente, String nombres, String apellidos, Date fecha_nacimiento, String genero, String telefono, String email, int idmunicipio, int idsucursal, int idempleado) {
        this.idpaciente = idpaciente;
        this.expediente = expediente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fecha_nacimiento = fecha_nacimiento;
        this.genero = genero;
        this.telefono = telefono;
        this.email = email;
        this.idmunicipio = idmunicipio;
        this.idsucursal = idsucursal;
        this.idempleado = idempleado;
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

    public int getIdsucursal() {
        return idsucursal;
    }

    public void setIdsucursal(int idsucursal) {
        this.idsucursal = idsucursal;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }
    
}
