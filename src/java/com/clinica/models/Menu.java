package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="menus")
public class Menu {
    @PrimaryKey
    @AutoIncrement
    private int idmenu;
    private String menu;
    private Integer idpadre;
    private String descripcion;
    private String url;

    public Menu() {
    }

    public Menu(int idmenu, String menu, Integer idpadre, String descripcion, String url) {
        this.idmenu = idmenu;
        this.menu = menu;
        this.idpadre = idpadre;
        this.descripcion = descripcion;
        this.url = url;
    }

    public int getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(int idmenu) {
        this.idmenu = idmenu;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public Integer getIdpadre() {
        return idpadre;
    }

    public void setIdpadre(Integer idpadre) {
        this.idpadre = idpadre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
