package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="laboratorios")
public class Laboratorio {
    @PrimaryKey
    @AutoIncrement
    private int idlaboratorio;
    private String nombre;
    private int idmunicipio;
    private String direccion;
    private String descripcion;

    public Laboratorio() {
    }

    public Laboratorio(int idlaboratorio, String nombre, int idmunicipio, String direccion, String descripcion) {
        this.idlaboratorio = idlaboratorio;
        this.nombre = nombre;
        this.idmunicipio = idmunicipio;
        this.direccion = direccion;
        this.descripcion = descripcion;
    }

    public int getIdlaboratorio() {
        return idlaboratorio;
    }

    public void setIdlaboratorio(int idlaboratorio) {
        this.idlaboratorio = idlaboratorio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
