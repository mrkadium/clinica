package com.clinica.models;

import com.clinica.annotations.*;
import java.util.Date;

@Entity(table = "consultas")
public class Consulta {

    @PrimaryKey
    @AutoIncrement
    private int idconsulta;
    private int idpaciente;
    private int idservicio;
    private Integer iddoctor;
    private Date fecha_hora;
    private boolean programada;
    private String estado;

    public Consulta() {
    }

    public Consulta(int idconsulta, int idpaciente, int idservicio, Integer iddoctor, Date fecha, boolean programada, String estado) {
        this.idconsulta = idconsulta;
        this.idpaciente = idpaciente;
        this.idservicio = idservicio;
        this.iddoctor = iddoctor;
        this.fecha_hora = fecha;
        this.programada = programada;
        this.estado = estado;
    }

    public int getIdconsulta() {
        return idconsulta;
    }

    public void setIdconsulta(int idconsulta) {
        this.idconsulta = idconsulta;
    }

    public int getIdpaciente() {
        return idpaciente;
    }

    public void setIdpaciente(int idpaciente) {
        this.idpaciente = idpaciente;
    }

    public int getIdservicio() {
        return idservicio;
    }

    public void setIdservicio(int idservicio) {
        this.idservicio = idservicio;
    }

    public Integer getIddoctor() {
        return iddoctor;
    }

    public void setIddoctor(Integer iddoctor) {
        this.iddoctor = iddoctor;
    }

    public Date getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Date fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public boolean getProgramada() {
        return programada;
    }

    public void setProgramada(boolean programada) {
        this.programada = programada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
