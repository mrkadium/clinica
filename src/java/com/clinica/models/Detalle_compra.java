package com.clinica.models;
import com.clinica.annotations.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(table="detalles_compra")
public class Detalle_compra {
    @PrimaryKey
    @AutoIncrement
    private int iddetalle_compra;
    private int idcompra;
    private int idconsumible;
    private Date fecha_caducidad;
    private int cantidad;
    private BigDecimal precio_compra;
    private BigDecimal precio_sugerido;
    private BigDecimal total;

    public Detalle_compra() {
        precio_compra = new BigDecimal("0");
        precio_sugerido = new BigDecimal("0");
        total = new BigDecimal("0");
    }

    public Detalle_compra(int iddetalle_compra, int idcompra, int idconsumible, Date fecha_caducidad, int cantidad, BigDecimal precio_compra, BigDecimal precio_sugerido, BigDecimal total) {
        this.iddetalle_compra = iddetalle_compra;
        this.idcompra = idcompra;
        this.idconsumible = idconsumible;
        this.fecha_caducidad = fecha_caducidad;
        this.cantidad = cantidad;
        this.precio_compra = precio_compra;
        this.precio_sugerido = precio_sugerido;
        this.total = total;
    }

    public int getIddetalle_compra() {
        return iddetalle_compra;
    }

    public void setIddetalle_compra(int iddetalle_compra) {
        this.iddetalle_compra = iddetalle_compra;
    }

    public int getIdcompra() {
        return idcompra;
    }

    public void setIdcompra(int idcompra) {
        this.idcompra = idcompra;
    }

    public int getIdconsumible() {
        return idconsumible;
    }

    public void setIdconsumible(int idconsumible) {
        this.idconsumible = idconsumible;
    }

    public Date getFecha_caducidad() {
        return fecha_caducidad;
    }

    public void setFecha_caducidad(Date fecha_caducidad) {
        this.fecha_caducidad = fecha_caducidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(BigDecimal precio_compra) {
        this.precio_compra = precio_compra;
    }

    public BigDecimal getPrecio_sugerido() {
        return precio_sugerido;
    }

    public void setPrecio_sugerido(BigDecimal precio_sugerido) {
        this.precio_sugerido = precio_sugerido;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    
    
}
