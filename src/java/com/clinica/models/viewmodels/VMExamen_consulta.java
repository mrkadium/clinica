package com.clinica.models.viewmodels;

import java.util.Date;

public class VMExamen_consulta {
    private int idexamen_consulta;
    private int idconsulta;
    private int idexamen;
    private String examen;
    private String estado;
    private Date fecha_revision;
    private String resultados;

    public VMExamen_consulta(int idexamen_consulta, int idconsulta, int idexamen, String examen, String estado, Date fecha_revision, String resultados) {
        this.idexamen_consulta = idexamen_consulta;
        this.idconsulta = idconsulta;
        this.idexamen = idexamen;
        this.examen = examen;
        this.estado = estado;
        this.fecha_revision = fecha_revision;
        this.resultados = resultados;
    }

    public VMExamen_consulta() {
    }


    public int getIdexamen_consulta() {
        return idexamen_consulta;
    }

    public void setIdexamen_consulta(int idexamen_consulta) {
        this.idexamen_consulta = idexamen_consulta;
    }

    public int getIdconsulta() {
        return idconsulta;
    }

    public void setIdconsulta(int idconsulta) {
        this.idconsulta = idconsulta;
    }

    public int getIdexamen() {
        return idexamen;
    }

    public void setIdexamen(int idexamen) {
        this.idexamen = idexamen;
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

    public String getExamen() {
        return examen;
    }

    public void setExamen(String examen) {
        this.examen = examen;
    }
    
    
}
