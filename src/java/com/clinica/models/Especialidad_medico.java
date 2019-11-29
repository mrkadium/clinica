package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="especialidades_medico")
public class Especialidad_medico {
    @PrimaryKey
    @AutoIncrement
    private int idespecialidad_medico;
    private int idespecialidad;
    private int idmedico;

    public Especialidad_medico() {
    }

    public Especialidad_medico(int idespecialidad_medico, int idespecialidad, int idmedico) {
        this.idespecialidad_medico = idespecialidad_medico;
        this.idespecialidad = idespecialidad;
        this.idmedico = idmedico;
    }

    public int getIdespecialidad_medico() {
        return idespecialidad_medico;
    }

    public void setIdespecialidad_medico(int idespecialidad_medico) {
        this.idespecialidad_medico = idespecialidad_medico;
    }

    public int getIdespecialidad() {
        return idespecialidad;
    }

    public void setIdespecialidad(int idespecialidad) {
        this.idespecialidad = idespecialidad;
    }

    public int getIdmedico() {
        return idmedico;
    }

    public void setIdmedico(int idmedico) {
        this.idmedico = idmedico;
    }
    
}
