package com.mrcontas.arquivo;


import com.mrcontas.modelo.tools.DataModel;
import evdata.filemanager.Gerenciador_do_Arquivo_SQLite;

//Objeto de acesso a arquivo.
public class OAA {

    private final static DataModel dataModel = new DataModel();
    private final static Gerenciador_do_Arquivo_SQLite gerenciador_do_arquivo_sqlite = new Gerenciador_do_Arquivo_SQLite(
            dataModel.getCreateTableScripts()
    );
    public static Gerenciador_do_Arquivo_SQLite gerenciador_do_arquivo_sqlite(){
        return gerenciador_do_arquivo_sqlite;
    }

}
