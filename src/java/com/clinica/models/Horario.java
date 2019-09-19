package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="horarios")
public class Horario {
    @PrimaryKey
    @AutoIncrement
    private int idhorario;
    private int idsucursal;
    private int idespecialidad;
    private String horainicio;
    private String horafinal;
    private String dias;

    public Horario() {
    }

    public Horario(int idhorario, int idsucursal, int idespecialidad, String horainicio, String horafinal, String dias) {
        this.idhorario = idhorario;
        this.idsucursal = idsucursal;
        this.idespecialidad = idespecialidad;
        this.horainicio = horainicio;
        this.horafinal = horafinal;
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

    public String getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(String horainicio) {
        this.horainicio = horainicio;
    }

    public String getHorafinal() {
        return horafinal;
    }

    public void setHorafinal(String horafinal) {
        this.horafinal = horafinal;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }
    
}
