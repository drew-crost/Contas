package com.mrcontas.consultas;

import com.mrcontas.dao.OAD;
import com.mrcontas.modelo.Conta;

import java.util.ArrayList;
import java.util.List;

public class Rol_de_Contas {
    private static List<Conta> rol_de_contas;
    static{
        rol_de_contas = new ArrayList<>();
        atualizar();
    }

    public static void atualizar(){
        rol_de_contas.clear();
        rol_de_contas.addAll(
            OAD.executar_consulta_select("Contas").stream().map(
                mapa_da_conta -> new Conta()
                .setId(mapa_da_conta.get("Id"))
                .setNome(mapa_da_conta.get("Nome"))
            ).toList()
        );
    }
    public static Conta get(long id){
        return rol_de_contas().stream().filter(conta->(conta.getId()==id)).findAny().get();
    }

    public static List<Conta> rol_de_contas(){
        return rol_de_contas;
    }
}
