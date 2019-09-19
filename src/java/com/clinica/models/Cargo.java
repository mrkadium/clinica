package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="cargos")
public class Cargo {
    @PrimaryKey
    @AutoIncrement
    private int idcargo;
    private String cargo;
    private String descripcion;

    public Cargo() {
    }

    public Cargo(int idcargo, String cargo, String descripcion) {
        this.idcargo = idcargo;
        this.cargo = cargo;
        this.descripcion = descripcion;
    }

    public int getIdcargo() {
        return idcargo;
    }

    public void setIdcargo(int idcargo) {
        this.idcargo = idcargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
