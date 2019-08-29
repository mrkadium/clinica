package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="municipios")
public class Municipio {
    @PrimaryKey
    @AutoIncrement
    private int idmunicipio;
    private String municipio;
    private int iddepartamento;

    public Municipio() {
    }

    public Municipio(int idmunicipio, String municipio, int iddepartamento) {
        this.idmunicipio = idmunicipio;
        this.municipio = municipio;
        this.iddepartamento = iddepartamento;
    }

    public int getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(int idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public int getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(int iddepartamento) {
        this.iddepartamento = iddepartamento;
    }
    
    
}
