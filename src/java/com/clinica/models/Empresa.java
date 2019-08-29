package com.clinica.models;

import com.clinica.annotations.*;

@Entity(table="empresa")
public class Empresa {
    @PrimaryKey
    @AutoIncrement
    private int idempresa;
    private String nombre;
    private String facebook;
    private String whatsapp;

    public Empresa() {
    }

    public Empresa(int idempresa, String nombre, String facebook, String whatsapp) {
        this.idempresa = idempresa;
        this.nombre = nombre;
        this.facebook = facebook;
        this.whatsapp = whatsapp;
    }

    public int getIdEmpresa() {
        return idempresa;
    }

    public void setId(int idempresa) {
        this.idempresa = idempresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }
    
}
