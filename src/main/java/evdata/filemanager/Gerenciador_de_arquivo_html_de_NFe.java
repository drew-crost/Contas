package evdata.filemanager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Gerenciador_de_arquivo_html_de_NFe extends Gerenciador_de_Arquivo {

    public void saveHtmlFileWithContent(String content){
        saveHtmlFileWithContent( content);
    }

    @Override
    public boolean executar(String texto_estruturado_para_alteração_de_conteúdo) {
        try {
        FileOutputStream outputStream = new FileOutputStream(salvar_arquivo_como().getCanonicalPath());
        outputStream.write(texto_estruturado_para_alteração_de_conteúdo.getBytes());
        outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void configurar_novo_arquivo() { }

    @Override
    public long inserir_1_linha(String roteiro_sql_parainserção_de_1_linha) throws SQLException, IOException {
        return -1;
    }

    @Override
    public int consultar_último_id_de_linha_inserida() throws SQLException, IOException {
        return -1;
    }

    @Override
    public ResultSet executar_consulta_select(String script) throws IOException, SQLException {
        return null;
    }

    @Override
    public String extensão_de_arquivo() {
        return "html";
    }

}
