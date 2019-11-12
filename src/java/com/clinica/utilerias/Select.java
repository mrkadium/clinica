package com.clinica.utilerias;

import com.clinica.models.Cargo;
import com.clinica.models.Departamento;
import com.clinica.models.Municipio;
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
    public static List<Departamento> departamentos() throws Exception{
        List<Departamento> lst = new ArrayList();
        String sql = "SELECT * FROM departamentos;";
        String[][] rs = Operaciones.consultar(sql, null);
        for(int i=0; i<rs[0].length; i++){
            lst.add(new Departamento(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]));
        }
        return lst;
    }
    public static List<Municipio> municipios() throws Exception{
        List<Municipio> lst = new ArrayList();
        String sql = "SELECT * FROM departamentos;";
        String[][] rs = Operaciones.consultar(sql, null);
        for(int i=0; i<rs[0].length; i++){
            lst.add(new Municipio(Integer.parseInt(rs[0][i]), rs[1][i], Integer.parseInt(rs[2][i])));
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
