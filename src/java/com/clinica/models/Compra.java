package com.clinica.models;
import com.clinica.annotations.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(table="compras")
public class Compra {
    @PrimaryKey
    @AutoIncrement
    private int idcompra;
    private Date fecha;
    private int idlaboratorio;
    private BigDecimal subtotal;
    private BigDecimal descuentos;
    private BigDecimal total;

    public Compra() {
        subtotal = new BigDecimal("0");
        descuentos = new BigDecimal("0");
        total = new BigDecimal("0");
    }

    public Compra(int idcompra, Date fecha, int idlaboratorio, BigDecimal subtotal, BigDecimal descuentos, BigDecimal total) {
        this.idcompra = idcompra;
        this.fecha = fecha;
        this.idlaboratorio = idlaboratorio;
        this.subtotal = subtotal;
        this.descuentos = descuentos;
        this.total = total;
    }

    public int getIdcompra() {
        return idcompra;
    }

    public void setIdcompra(int idcompra) {
        this.idcompra = idcompra;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdlaboratorio() {
        return idlaboratorio;
    }

    public void setIdlaboratorio(int idlaboratorio) {
        this.idlaboratorio = idlaboratorio;
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
    
}
