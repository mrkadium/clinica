package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="usuarios")
public class Usuario {
    @PrimaryKey
    @AutoIncrement
    private int idusuario;
    private int idempleado;
    private String usuario;
    private String clave;
    private int idrol;
    private String estado;

    public Usuario() {
    }

    public Usuario(int idusuario, int idempleado, String usuario, String clave, int idrol, String estado) {
        this.idusuario = idusuario;
        this.idempleado = idempleado;
        this.usuario = usuario;
        this.clave = clave;
        this.idrol = idrol;
        this.estado = estado;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIdrol() {
        return idrol;
    }

    public void setIdrol(int idrol) {
        this.idrol = idrol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
