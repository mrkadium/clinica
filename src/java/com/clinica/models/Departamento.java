package com.clinica.models;

import com.clinica.annotations.*;

@Entity(table="departamentos")
public class Departamento {
    @PrimaryKey
    @AutoIncrement
    private int iddepartamento;
    private String departamento;
    private String zona;

    public Departamento() {
    }

    public Departamento(int iddepartamento, String departamento, String zona) {
        this.iddepartamento = iddepartamento;
        this.departamento = departamento;
        this.zona = zona;
    }

    public int getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(int iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
    
}