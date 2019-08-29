package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="sucursales")
public class Sucursal {
    @PrimaryKey
    @AutoIncrement
    private int idsucursal;
    private int idempresa;
    private int idmunicipio;
    private String direccion;
    private String telefono1;
    private String telefono2;
    private String email;

    public Sucursal() {
    }

    public Sucursal(int idsucursal, int idempresa, int idmunicipio, String direccion, String telefono1, String telefono2, String email) {
        this.idsucursal = idsucursal;
        this.idempresa = idempresa;
        this.idmunicipio = idmunicipio;
        this.direccion = direccion;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.email = email;
    }

    public int getIdsucursal() {
        return idsucursal;
    }

    public void setIdsucursal(int idsucursal) {
        this.idsucursal = idsucursal;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
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

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
