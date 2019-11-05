package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="contactos")
public class Contacto {
    @PrimaryKey
    @AutoIncrement
    private int idcontacto;
    private String tipo;
    private String contacto;
    private int idempleado;

    public Contacto(int idcontacto, String tipo, String contacto, int idempleado) {
        this.idcontacto = idcontacto;
        this.tipo = tipo;
        this.contacto = contacto;
        this.idempleado = idempleado;
    }

    public Contacto() {
    }

    public int getIdcontacto() {
        return idcontacto;
    }

    public void setIdcontacto(int idcontacto) {
        this.idcontacto = idcontacto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }
}
