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
    public static List<String> HORAS() throws Exception{
        List<String> lst = new ArrayList();
        lst.add("06");
        lst.add("07");
        lst.add("08");
        lst.add("09");
        lst.add("10");
        lst.add("11");
        lst.add("12");
        lst.add("13");
        lst.add("14");
        lst.add("15");
        lst.add("16");
        lst.add("17");
        lst.add("18");
        lst.add("19");
        lst.add("20");
        lst.add("21");
        lst.add("22");
        return lst;
    }
    public static List<String> MINUTOS() throws Exception{
        List<String> lst = new ArrayList();
        lst.add("00");
        lst.add("05");
        lst.add("10");
        lst.add("15");
        lst.add("20");
        lst.add("25");
        lst.add("30");
        lst.add("35");
        lst.add("40");
        lst.add("45");
        lst.add("50");
        lst.add("55");
        return lst;
    }
}
