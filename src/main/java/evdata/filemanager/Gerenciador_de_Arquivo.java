package evdata.filemanager;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Gerenciador_de_Arquivo implements Interface_de_Gerenciador_de_Arquivo {

    private static final String PREFIXO_DE_ARQUIVO_TEMPORÁRIO = "cdb";
    private static final String SUFIXO_DE_ARQUIVO_TEMPORÁRIO = "dbtmp";
    private static final String EXTENSÃO_DE_ARQUIVO_PADRÃO_DO_APLICATIVO = "contas";
    @Override public String extensão_de_arquivo() {
        return EXTENSÃO_DE_ARQUIVO_PADRÃO_DO_APLICATIVO;
    }
    protected String prefixo_de_arquivo_temporário() {
        return PREFIXO_DE_ARQUIVO_TEMPORÁRIO;
    }
    protected String sufixo_de_arquivo_temporário() { return SUFIXO_DE_ARQUIVO_TEMPORÁRIO; }
    private java.io.File arquivo_temporário;
    private java.io.File arquivo_aberto;

    protected File acessar_arquivo_temporário() {
        return arquivo_temporário;
    }

    @Override
    public File acessar_arquivo_aberto() {
        return arquivo_aberto;
    }

    @Override
    public File acessar_arquivo() {
        return arquivo_aberto!=null?arquivo_aberto:arquivo_temporário;
    }

    private java.io.File novo_arquivo_temporário_vazio() throws java.io.IOException{
        java.io.File file = java.io.File.createTempFile(prefixo_de_arquivo_temporário(), sufixo_de_arquivo_temporário());
        arquivo_temporário = file;
        return file;
    }

    @Override
    public java.io.File criar_e_configurar_novo_arquivo_inicial_vazio() throws java.io.IOException{
        java.io.File file = novo_arquivo_temporário_vazio();
        arquivo_aberto = null;
        configurar_novo_arquivo();
        return file;
    }

    protected java.io.File copiar_arquivo(java.io.File originalFile, java.io.File targetFile) throws java.io.IOException{
        java.nio.file.Files.copy(
                originalFile.toPath(), targetFile.toPath()
                ,java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        return targetFile;
    }


    @Override
    public java.io.File abrir_arquivo() throws java.io.IOException{ return abrir_arquivo(null); }
    @Override
    public java.io.File abrir_arquivo(String título_da_janela_de_diálogo) throws java.io.IOException{
        javafx.stage.FileChooser janela_de_diálogo = obter_nova_janela_de_diálogo();
        janela_de_diálogo.setTitle((título_da_janela_de_diálogo==null?"Abrir arquivo":título_da_janela_de_diálogo));
        arquivo_aberto = janela_de_diálogo.showOpenDialog(null);
        if(arquivo_aberto !=null){
            inserir_todo_o_conteúdo_do_arquivo_aberto_em_um_novo_arquivo_temporário_inicialmente_vazio();
        }
        return arquivo_aberto;
    }

    private void inserir_todo_o_conteúdo_do_arquivo_aberto_em_um_novo_arquivo_temporário_inicialmente_vazio() throws IOException {
        copiar_arquivo(arquivo_aberto, novo_arquivo_temporário_vazio());
    }

    private void copiar_todo_o_conteúdo_do_arquivo_temporário_para_o_arquivo_aberto() throws IOException {
        if ((arquivo_temporário !=null) && (arquivo_aberto != null)) {
            copiar_arquivo(arquivo_temporário, arquivo_aberto);
        }
    }

    @Override
    public java.io.File salvar_conteúdo(String título_da_janela_de_diálogo) throws java.io.IOException{
        if(arquivo_aberto ==null){
            salvar_arquivo_como(título_da_janela_de_diálogo);
        } else {
            copiar_todo_o_conteúdo_do_arquivo_temporário_para_o_arquivo_aberto();
        }
        return arquivo_aberto;
    } @Override
    public java.io.File salvar_conteúdo() throws java.io.IOException {
        return salvar_conteúdo(null);
    }

    @Override
    public java.io.File salvar_arquivo_como(String título_da_janela_de_diálogo) throws java.io.IOException{
        javafx.stage.FileChooser janela_de_diálogo = obter_nova_janela_de_diálogo();
        janela_de_diálogo.setTitle((título_da_janela_de_diálogo==null?"Salvar arquivo":título_da_janela_de_diálogo));
        arquivo_aberto = janela_de_diálogo.showSaveDialog(null);

        if(arquivo_aberto !=null){
            copiar_todo_o_conteúdo_do_arquivo_temporário_para_o_arquivo_aberto();
        }
        return arquivo_aberto;
    } @Override
    public java.io.File salvar_arquivo_como() throws java.io.IOException {
        return salvar_conteúdo(null);
    }

    private javafx.stage.FileChooser obter_nova_janela_de_diálogo() {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        javafx.stage.FileChooser.ExtensionFilter extFilter = new javafx.stage.FileChooser.ExtensionFilter(
                "Arquivos "+ extensão_de_arquivo() +" (*."+ extensão_de_arquivo() +")", "*."+ extensão_de_arquivo());
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser;
    }

    protected abstract void configurar_novo_arquivo();

    public abstract long inserir_1_linha(String roteiro_sql_parainserção_de_1_linha) throws SQLException, IOException;

    public abstract int consultar_último_id_de_linha_inserida() throws SQLException, IOException;

    public abstract ResultSet executar_consulta_select(String script) throws IOException, SQLException;
}
