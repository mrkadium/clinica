package com.clinica.models;
import com.clinica.annotations.*;
import java.math.BigDecimal;

@Entity(table="consumibles")
public class Consumible {
    @PrimaryKey
    @AutoIncrement
    private int idconsumible;
    private String tipo;
    private String nombre;
    private String alias;
    private String presentacion;
    private int idmarca;
    private BigDecimal precio_compra;
    private BigDecimal precio_sugerido;
    private BigDecimal precio_venta;

    public Consumible() {
    }

    public Consumible(int idconsumible, String tipo, String nombre, String alias, String presentacion, int idmarca, BigDecimal precio_compra, BigDecimal precio_sugerido, BigDecimal precio_venta) {
        this.idconsumible = idconsumible;
        this.tipo = tipo;
        this.nombre = nombre;
        this.alias = alias;
        this.presentacion = presentacion;
        this.idmarca = idmarca;
        this.precio_compra = precio_compra;
        this.precio_sugerido = precio_sugerido;
        this.precio_venta = precio_venta;
    }

    public int getIdconsumible() {
        return idconsumible;
    }

    public void setIdconsumible(int idconsumible) {
        this.idconsumible = idconsumible;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public int getIdmarca() {
        return idmarca;
    }

    public void setIdmarca(int idmarca) {
        this.idmarca = idmarca;
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

    public BigDecimal getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(BigDecimal precio_venta) {
        this.precio_venta = precio_venta;
    }
    
}
