package com.clinica.utilerias;

import com.clinica.models.Cargo;
import com.clinica.opearaciones.Operaciones;
import java.util.ArrayList;
import java.util.List;

public class Select {
    public static List<Cargo> cargos() throws Exception{
        List<Cargo> lst = new ArrayList();
        String sql = "SELECT * FROM cargos;";
        String[][] rs = Operaciones.consultar(sql, null);
        for(int i=0; i<rs[0].length; i++){
            lst.add(new Cargo(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]));
        }
        return lst;
    }
    public static List<String> tipos_contacto() throws Exception{
        List<String> lst = new ArrayList();
        lst.add("Email");
        lst.add("Telefono");
        return lst;
    }
}
