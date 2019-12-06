package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="examenes")
public class Examen {
    @PrimaryKey
    @AutoIncrement
    private int idexamen;
    private String examen;
    private String descripcion;

    public Examen() {
    }

    public Examen(int idexamen, String examen, String descripcion) {
        this.idexamen = idexamen;
        this.examen = examen;
        this.descripcion = descripcion;
    }

    public int getIdexamen() {
        return idexamen;
    }

    public void setIdexamen(int idexamen) {
        this.idexamen = idexamen;
    }

    public String getExamen() {
        return examen;
    }

    public void setExamen(String examen) {
        this.examen = examen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
