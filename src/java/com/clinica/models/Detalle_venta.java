package com.clinica.models;
import com.clinica.annotations.*;
import java.math.BigDecimal;

@Entity(table="detalles_venta")
public class Detalle_venta {
    @PrimaryKey
    @AutoIncrement
    private int iddetalle_venta;
    private int idventa;
    private int idconsumible;
    private int cantidad;
    private BigDecimal precio;
    private BigDecimal excento;
    private BigDecimal total;
    private BigDecimal no_sujeto;
    private BigDecimal monto_iva;

    public Detalle_venta() {        
        precio = new BigDecimal(0);
        excento = new BigDecimal(0);
        total = new BigDecimal(0);
        no_sujeto = new BigDecimal(0);
        monto_iva = new BigDecimal(0);
    }

    public Detalle_venta(int iddetalle_venta, int idventa, int idconsumible, int cantidad, BigDecimal precio, BigDecimal excento, BigDecimal total, BigDecimal no_sujeto, BigDecimal monto_iva) {
        this.iddetalle_venta = iddetalle_venta;
        this.idventa = idventa;
        this.idconsumible = idconsumible;
        this.cantidad = cantidad;
        this.precio = precio;
        this.excento = excento;
        this.total = total;
        this.no_sujeto = no_sujeto;
        this.monto_iva = monto_iva;
    }

    public int getIddetalle_venta() {
        return iddetalle_venta;
    }

    public void setIddetalle_venta(int iddetalle_venta) {
        this.iddetalle_venta = iddetalle_venta;
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public int getIdconsumible() {
        return idconsumible;
    }

    public void setIdconsumible(int idconsumible) {
        this.idconsumible = idconsumible;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getExcento() {
        return excento;
    }

    public void setExcento(BigDecimal excento) {
        this.excento = excento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getNo_sujeto() {
        return no_sujeto;
    }

    public void setNo_sujeto(BigDecimal no_sujeto) {
        this.no_sujeto = no_sujeto;
    }

    public BigDecimal getMonto_iva() {
        return monto_iva;
    }

    public void setMonto_iva(BigDecimal monto_iva) {
        this.monto_iva = monto_iva;
    }
    
}
