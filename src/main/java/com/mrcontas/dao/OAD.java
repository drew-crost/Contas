package com.mrcontas.dao;
import com.mrcontas.arquivo.OAA;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class OAD {


    public static boolean executar(String comando){
        return OAA.gerenciador_do_arquivo_sqlite().executar(
                comando
        );
    }

    public static long inserir_1_linha(String comando){
        try {
            return OAA.gerenciador_do_arquivo_sqlite().inserir_1_linha(comando);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<Map<String, String>> executar_consulta_select(CharSequence cláusula_tabela){
        if (cláusula_tabela==null){
            return null;
        }
        StringBuilder strb_consulta = new StringBuilder();

        strb_consulta.append("select * from");
        strb_consulta.append(cláusula_tabela);

        return executar_consulta_select(strb_consulta.toString());

    }

    public static List<Map<String, String>> executar_consulta_select(CharSequence  cláusula_campos, CharSequence cláusula_tabela){
        if (cláusula_tabela==null){
            return null;
        }
        if (cláusula_campos==null){
            return executar_consulta_select(cláusula_tabela);
        }
        StringBuilder strb_consulta = new StringBuilder();
        strb_consulta.append("select ");
        strb_consulta.append(cláusula_campos);
        strb_consulta.append("from ");
        strb_consulta.append(cláusula_tabela);
        return executar_consulta_select(strb_consulta.toString());
    }

    public static List<Map<String, String>> executar_consulta_select(CharSequence  cláusula_campos, CharSequence cláusula_tabela, CharSequence cláusula_where){
        if (cláusula_tabela==null){
            return null;
        }
        if (cláusula_campos==null){
            if (cláusula_where==null){
                return executar_consulta_select(cláusula_tabela);
            }else{
                cláusula_campos="*";
            }
        }
        if (cláusula_where==null){
            return executar_consulta_select(cláusula_campos,cláusula_tabela);
        }
        StringBuilder strb_consulta = new StringBuilder();
        strb_consulta.append("select ");
        strb_consulta.append(cláusula_campos);
        strb_consulta.append("from ");
        strb_consulta.append(cláusula_tabela);
        strb_consulta.append("where ");
        strb_consulta.append(cláusula_where);

        return executar_consulta_select(strb_consulta.toString());
    }

    private static List<Map<String, String>> executar_consulta_select(String consulta){


        List<Map<String, String>> resultado = new ArrayList<Map<String, String>>();
        try {
            ResultSet rs = OAA.gerenciador_do_arquivo_sqlite().executar_consulta_select(consulta.toString());
            ResultSetMetaData rsm = rs.getMetaData();
            int nro_de_colunas = rsm.getColumnCount();
            while(rs.next()){
                Map<String, String> linha = new HashMap<>();
                for(int i= 0; i<nro_de_colunas; i++){
                    linha.put(rs.getString(i), rsm.getColumnName(i));
                }
                resultado.add(linha);
            }
            return resultado;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long consultar_último_id_de_linha_inserida() {
        try {
            return OAA.gerenciador_do_arquivo_sqlite().consultar_último_id_de_linha_inserida();
        } catch (SQLException e) {
             e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

//    private static List<Conta> listContas=new ArrayList<>();
//    public static void addConta(Conta conta){
//        listContas.add(conta);
//    }
//    public static List<Conta> getContas(){
//        return listContas;
//    }

}
