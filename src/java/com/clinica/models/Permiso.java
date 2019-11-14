package com.clinica.models;
import com.clinica.annotations.*;

@Entity(table="permisos")
public class Permiso {
    @PrimaryKey
    @AutoIncrement
    private int idpermiso;
    private int idrol;
    private int idmenu;

    public Permiso() {
    }

    public Permiso(int idpermiso, int idrol, int idmenu) {
        this.idpermiso = idpermiso;
        this.idrol = idrol;
        this.idmenu = idmenu;
    }

    public int getIdpermiso() {
        return idpermiso;
    }

    public void setIdpermiso(int idpermiso) {
        this.idpermiso = idpermiso;
    }

    public int getIdrol() {
        return idrol;
    }

    public void setIdrol(int idrol) {
        this.idrol = idrol;
    }

    public int getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(int idmenu) {
        this.idmenu = idmenu;
    }
    
}
