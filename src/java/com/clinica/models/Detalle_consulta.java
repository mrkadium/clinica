package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="detalles_consulta")
public class Detalle_consulta {
    @PrimaryKey
    @AutoIncrement
    private int iddetalle_consulta;
    private int idconsulta;
    private String razon_consulta;
    private String temperatura;
    private String frecuencia_cardiaca;
    private String frecuencia_respiratoria;
    private String presion_arterial;
    private String saturacion_oxigeno;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;

    public Detalle_consulta() {
    }

    public Detalle_consulta(int iddetalle_consulta, int idconsulta, String razon_consulta, String temperatura, String frecuencia_cardiaca, String frecuencia_respiratoria, String presion_arterial, String saturacion_oxigeno, String diagnostico, String tratamiento, String observaciones) {
        this.iddetalle_consulta = iddetalle_consulta;
        this.idconsulta = idconsulta;
        this.razon_consulta = razon_consulta;
        this.temperatura = temperatura;
        this.frecuencia_cardiaca = frecuencia_cardiaca;
        this.frecuencia_respiratoria = frecuencia_respiratoria;
        this.presion_arterial = presion_arterial;
        this.saturacion_oxigeno = saturacion_oxigeno;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
    }


    public int getIddetalle_consulta() {
        return iddetalle_consulta;
    }

    public void setIddetalle_consulta(int iddetalle_consulta) {
        this.iddetalle_consulta = iddetalle_consulta;
    }

    public int getIdconsulta() {
        return idconsulta;
    }

    public void setIdconsulta(int idconsulta) {
        this.idconsulta = idconsulta;
    }

    public String getRazon_consulta() {
        return razon_consulta;
    }

    public void setRazon_consulta(String razon_consulta) {
        this.razon_consulta = razon_consulta;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getFrecuencia_cardiaca() {
        return frecuencia_cardiaca;
    }

    public void setFrecuencia_cardiaca(String frecuencia_cardiaca) {
        this.frecuencia_cardiaca = frecuencia_cardiaca;
    }

    public String getFrecuencia_respiratoria() {
        return frecuencia_respiratoria;
    }

    public void setFrecuencia_respiratoria(String frecuencia_respiratoria) {
        this.frecuencia_respiratoria = frecuencia_respiratoria;
    }

    public String getPresion_arterial() {
        return presion_arterial;
    }

    public void setPresion_arterial(String presion_arterial) {
        this.presion_arterial = presion_arterial;
    }

    public String getSaturacion_oxigeno() {
        return saturacion_oxigeno;
    }

    public void setSaturacion_oxigeno(String saturacion_oxigeno) {
        this.saturacion_oxigeno = saturacion_oxigeno;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
}
