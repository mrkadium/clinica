package com.clinica.models.viewmodels;

public class VMEmpleado_consulta {
    private int idempleado_consulta;
    private int idempleado;
    private String empleado;
    private String cargo;

    public VMEmpleado_consulta() {
    }

    public VMEmpleado_consulta(int idempleado_consulta, int idempleado, String empleado, String cargo) {
        this.idempleado_consulta = idempleado_consulta;
        this.idempleado = idempleado;
        this.empleado = empleado;
        this.cargo = cargo;
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

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
}
