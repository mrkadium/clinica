package com.clinica.utilerias;

import com.clinica.models.Menu;
import java.util.ArrayList;
import java.util.List;

public class Menu_Submenu {
    private Menu menu_principal;
    private List<Menu> submenus;

    public Menu_Submenu() {
        submenus = new ArrayList();
    }

    public Menu_Submenu(Menu menu_principal, List<Menu> submenus) {
        this.menu_principal = menu_principal;
        this.submenus = submenus;
    }

    public Menu getMenu_principal() {
        return menu_principal;
    }

    public void setMenu_principal(Menu menu_principal) {
        this.menu_principal = menu_principal;
    }

    public List<Menu> getSubmenus() {
        return submenus;
    }

    public void setSubmenus(List<Menu> submenus) {
        this.submenus = submenus;
    }
    
}
