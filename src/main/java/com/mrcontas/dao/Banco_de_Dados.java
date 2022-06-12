package com.mrcontas.dao;

import evdata.sqlite.datamodel.Entidade;

public class Banco_de_Dados {
    public static boolean adicionar(Entidade entidade){
        entidade.roteiro_para_adicionar_registro();
        return false;
    }
}
