package evdata.filemanager;



import evdata.sqlite.Fábrica_de_Conexão_SQLite;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
public class Gerenciador_do_Arquivo_SQLite extends Gerenciador_de_Arquivo {


    private final String[] rotinas_para_criação_de_tabelas;

    public Gerenciador_do_Arquivo_SQLite(String[] rotinas_para_criação_de_tabelas){
        super();
        this.rotinas_para_criação_de_tabelas = rotinas_para_criação_de_tabelas;
    }

    @Override
    protected void configurar_novo_arquivo() {
        try {
            Fábrica_de_Conexão_SQLite.executar_rotinas_sql(
                    acessar_arquivo_temporário().getCanonicalPath()
                    , rotinas_para_criação_de_tabelas
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean executar(String texto_estruturado_para_alteração_de_conteúdo) {
        System.out.println(texto_estruturado_para_alteração_de_conteúdo);
        try {
             Fábrica_de_Conexão_SQLite.executar_rotinas_sql(
                    acessar_arquivo().getCanonicalPath()
                    , new String[]{texto_estruturado_para_alteração_de_conteúdo}
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override public long inserir_1_linha(String roteiro_sql_para_inserção_de_1_linha) throws SQLException, IOException {
        executar(roteiro_sql_para_inserção_de_1_linha);
        return Fábrica_de_Conexão_SQLite.consultar_último_id_de_linha_inserida(acessar_arquivo().getCanonicalPath());
    }

        @Override public int consultar_último_id_de_linha_inserida() throws SQLException, IOException {
        return Fábrica_de_Conexão_SQLite.consultar_último_id_de_linha_inserida(acessar_arquivo().getCanonicalPath());
    }

    @Override public ResultSet executar_consulta_select(String consulta) throws IOException, SQLException {
        return Fábrica_de_Conexão_SQLite.get_selection_resultset(acessar_arquivo().getCanonicalPath(), consulta);
    }

}
