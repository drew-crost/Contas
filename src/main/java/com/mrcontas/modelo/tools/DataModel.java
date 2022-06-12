package com.mrcontas.modelo.tools;

public class DataModel {

    //protected abstract String tabela_por_tipo_de_pesquisa(TP tipo_de_pesquisa);
    public String tabela_por_tipo_de_pesquisa(String tipo_de_pesquisa){
//        Conta.fábrica().criar();
        return switch (tipo_de_pesquisa) {

            case "Tabela de Contas" -> "Contas";
            case "Tabela de Lançamentos" -> "Lançamentos";
            case "Tabela de Movimentos" -> "Movimento";

            default -> null;
        };
    }

    public String[] getCreateTableScripts(){
        return scripts;
    }

    private static final String[] scripts={"PRAGMA foreign_keys = off",
            //"BEGIN TRANSACTION",
        """
        -- Table: Contas
        CREATE TABLE Contas (
            Id   INTEGER PRIMARY KEY AUTOINCREMENT
                             NOT NULL,
            Nome TEXT
        );""", """
        -- Table: Lançamentos
        CREATE TABLE Lançamentos (
            Id   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            DataHora  DATETIME,
            Histórico
        );""", """
        -- Table: Movimentos
        CREATE TABLE Movimentos (
            Id   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            Conta       REFERENCES Conta (ContaId),
            Valor,
            Lançamento  REFERENCES Lançamento (LançamentoId)
        );"""
    };

}
