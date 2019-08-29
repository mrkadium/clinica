package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="noticias")
public class Noticia {
    @PrimaryKey
    @AutoIncrement
    private int idnoticia;
    private String titulo;
    private String resumen;
    private String contenido;

    public Noticia() {
    }

    public Noticia(int idnoticia, String titulo, String resumen, String contenido) {
        this.idnoticia = idnoticia;
        this.titulo = titulo;
        this.resumen = resumen;
        this.contenido = contenido;
    }

    public int getIdnoticia() {
        return idnoticia;
    }

    public void setIdnoticia(int idnoticia) {
        this.idnoticia = idnoticia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    
}