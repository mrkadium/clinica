package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="roles")
public class Rol {
    @PrimaryKey
    @AutoIncrement
    private int idrol;
    private String rol;
    private String descripcion;

    public Rol() {
    }

    public Rol(int idrol, String rol, String descripcion) {
        this.idrol = idrol;
        this.rol = rol;
        this.descripcion = descripcion;
    }

    public int getIdrol() {
        return idrol;
    }

    public void setIdrol(int idrol) {
        this.idrol = idrol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
