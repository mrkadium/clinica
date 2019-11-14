package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="horarios")
public class Horario {
    @PrimaryKey
    @AutoIncrement
    private int idhorario;
    private int idsucursal;
    private int idespecialidad;
    private String hora_inicio;
    private String hora_fin;
    private String dias;

    public Horario() {
    }

    public Horario(int idhorario, int idsucursal, int idespecialidad, String hora_inicio, String hora_fin, String dias) {
        this.idhorario = idhorario;
        this.idsucursal = idsucursal;
        this.idespecialidad = idespecialidad;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.dias = dias;
    }

    public int getIdhorario() {
        return idhorario;
    }

    public void setIdhorario(int idhorario) {
        this.idhorario = idhorario;
    }

    public int getIdsucursal() {
        return idsucursal;
    }

    public void setIdsucursal(int idsucursal) {
        this.idsucursal = idsucursal;
    }

    public int getIdespecialidad() {
        return idespecialidad;
    }

    public void setIdespecialidad(int idespecialidad) {
        this.idespecialidad = idespecialidad;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }    
}
