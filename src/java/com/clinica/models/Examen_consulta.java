package com.clinica.models;
import com.clinica.annotations.*;
import java.util.Date;

@Entity(table="examenes_consulta")
public class Examen_consulta {
    @PrimaryKey
    @AutoIncrement
    private int idexamen_consulta;
    private int idexamen;
    private int idconsulta;
    private String estado;
    private Date fecha_revision;
    private String resultados;

    public Examen_consulta() {
    }

    public Examen_consulta(int idexamen_consulta, int idexamen, int idconsulta, String estado, Date fecha_revision, String resultados) {
        this.idexamen_consulta = idexamen_consulta;
        this.idexamen = idexamen;
        this.idconsulta = idconsulta;
        this.estado = estado;
        this.fecha_revision = fecha_revision;
        this.resultados = resultados;
    }

    public int getIdexamen_consulta() {
        return idexamen_consulta;
    }

    public void setIdexamen_consulta(int idexamen_consulta) {
        this.idexamen_consulta = idexamen_consulta;
    }

    public int getIdexamen() {
        return idexamen;
    }

    public void setIdexamen(int idexamen) {
        this.idexamen = idexamen;
    }

    public int getIdconsulta() {
        return idconsulta;
    }

    public void setIdconsulta(int idconsulta) {
        this.idconsulta = idconsulta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha_revision() {
        return fecha_revision;
    }

    public void setFecha_revision(Date fecha_revision) {
        this.fecha_revision = fecha_revision;
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }

}
