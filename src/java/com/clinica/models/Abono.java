package com.clinica.models;
import com.clinica.annotations.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(table="abonos")
public class Abono {
    @PrimaryKey
    @AutoIncrement
    private int idabono;
    private int idventa;
    private Date fecha;
    private BigDecimal monto;

    public Abono() {
    }

    public Abono(int idabono, int idventa, Date fecha, BigDecimal monto) {
        this.idabono = idabono;
        this.idventa = idventa;
        this.fecha = fecha;
        this.monto = monto;
    }

    public int getIdabono() {
        return idabono;
    }

    public void setIdabono(int idabono) {
        this.idabono = idabono;
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
}
