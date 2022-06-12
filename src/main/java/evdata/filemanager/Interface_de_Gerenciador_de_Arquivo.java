package evdata.filemanager;

import java.io.File;

public interface Interface_de_Gerenciador_de_Arquivo {

    String extensão_de_arquivo();
    File acessar_arquivo_aberto();

    File acessar_arquivo();

    File criar_e_configurar_novo_arquivo_inicial_vazio() throws java.io.IOException;
    File abrir_arquivo(String tituloJanelaDialogo) throws java.io.IOException;
    File abrir_arquivo() throws java.io.IOException;
    boolean executar(String texto_estruturado_para_alteração_de_conteúdo);
    File salvar_conteúdo(String tituloJanelaDialogo) throws java.io.IOException;
    File salvar_conteúdo() throws java.io.IOException;
    File salvar_arquivo_como(String tituloJanelaDialogo) throws java.io.IOException;
    File salvar_arquivo_como() throws java.io.IOException;

}
