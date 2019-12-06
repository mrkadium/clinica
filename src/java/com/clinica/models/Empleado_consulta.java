package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="empleados_consulta")
public class Empleado_consulta {
    @PrimaryKey
    @AutoIncrement
    private int idempleado_consulta;
    private int idconsulta;
    private int idempleado;

    public Empleado_consulta() {
    }

    public Empleado_consulta(int idempleado_consulta, int idempleado, int idconsulta) {
        this.idempleado_consulta = idempleado_consulta;
        this.idempleado = idempleado;
        this.idconsulta = idconsulta;
    }

    public int getIdempleado_consulta() {
        return idempleado_consulta;
    }

    public void setIdempleado_consulta(int idempleado_consulta) {
        this.idempleado_consulta = idempleado_consulta;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public int getIdconsulta() {
        return idconsulta;
    }

    public void setIdconsulta(int idconsulta) {
        this.idconsulta = idconsulta;
    }
    
}
