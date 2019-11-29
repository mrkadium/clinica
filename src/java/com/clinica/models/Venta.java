package com.clinica.models;
import com.clinica.annotations.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(table="ventas")
public class Venta {
    @PrimaryKey
    @AutoIncrement
    private int idventa;
    private int numero;
    private Date fecha;
    private int idpaciente;
    private String paciente;
    private String direccion;
    private String telefono;
    private int idempleado;
    private BigDecimal subtotal;
    private BigDecimal descuentos;
    private BigDecimal total;
    private BigDecimal deuda;
    private String estado;

    public Venta() {
        numero = 0;
        subtotal = new BigDecimal(0);
        descuentos = new BigDecimal(0);
        total = new BigDecimal(0);
        deuda = new BigDecimal(0);
    }

    public Venta(int idventa, int numero, Date fecha, int idpaciente, String paciente, String direccion, String telefono, int idempleado, BigDecimal subtotal, BigDecimal descuentos, BigDecimal total, BigDecimal deuda, String estado) {
        this.idventa = idventa;
        this.numero = numero;
        this.fecha = fecha;
        this.idpaciente = idpaciente;
        this.paciente = paciente;
        this.direccion = direccion;
        this.telefono = telefono;
        this.idempleado = idempleado;
        this.subtotal = subtotal;
        this.descuentos = descuentos;
        this.total = total;
        this.deuda = deuda;
        this.estado = estado;
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdpaciente() {
        return idpaciente;
    }

    public void setIdpaciente(int idpaciente) {
        this.idpaciente = idpaciente;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(BigDecimal descuentos) {
        this.descuentos = descuentos;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDeuda() {
        return deuda;
    }

    public void setDeuda(BigDecimal deuda) {
        this.deuda = deuda;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
