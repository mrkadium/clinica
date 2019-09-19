package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="especialidades")
public class Especialidad {
    @PrimaryKey
    @AutoIncrement
    private int idespecialidad;
    private String especialidad;

    public Especialidad() {
    }

    public Especialidad(int idespecialidad, String especialidad) {
        this.idespecialidad = idespecialidad;
        this.especialidad = especialidad;
    }

    public int getIdespecialidad() {
        return idespecialidad;
    }

    public void setIdespecialidad(int idespecialidad) {
        this.idespecialidad = idespecialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
}
