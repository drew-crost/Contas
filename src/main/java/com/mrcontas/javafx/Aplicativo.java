package com.mrcontas.javafx;

import com.mrcontas.arquivo.OAA;
import com.mrcontas.dao.Banco_de_Dados;
import com.mrcontas.modelo.Conta;
import com.mrcontas.modelo.Lançamento;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Aplicativo extends Application {

    public static java.util.List<Conta> contas(){
        return contas;
    }
    private static java.util.List<Conta> contas = new java.util.ArrayList<>();

    private static Banco_de_Dados banco_de_dados_do_aplicativo;

    public static final String MAINSCREEN_FXML = "tela_principal.fxml";
    public static final String INSERIR_CONTA_FXML = "tela_de_nova_conta.fxml";

    public static File getArquivo(){
        return OAA.gerenciador_do_arquivo_sqlite().acessar_arquivo();
    }


    public static Conta adicionar_conta(String nome){
        return new Conta()
                .setNome(nome)
                .inserir()
                .atualizar();
    }
    /*public static boolean adicionar(Entidade conteúdo){
        OAD.gerenciador_do_arquivo_sqlite().alterar_conteúdo(
            conteúdo.roteiro_para_adicionar_registro()
        );
        return true;
    }*/

    public static File abrirArquivo() {
        File resultado;
        try {
            resultado = OAA.gerenciador_do_arquivo_sqlite().abrir_arquivo();
        } catch (IOException e) {
            e.printStackTrace();
            resultado = null;
        }
        configurar_tela(MAINSCREEN_FXML);
        return resultado;
    }


    @FXML
    public static void novoArquivo() {

        try {
            OAA.gerenciador_do_arquivo_sqlite().criar_e_configurar_novo_arquivo_inicial_vazio();
            //refreshWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
        configurar_tela(MAINSCREEN_FXML);
    }
    /*
    @FXML
    public static void abrirArquivo() {
        try {
            sqLiteFileManager.abrir_Arquivo();
            //refreshWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @FXML
    public static void salvarArquivo() {
        try {
            OAA.gerenciador_do_arquivo_sqlite().salvar_conteúdo();
            //refreshWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void novaConta() {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicativo.class.getResource("tela_de_nova_conta.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            getStage().setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPopupStage(INSERIR_CONTA_FXML);
    }

    private static String INSERIR_LANÇAMENTO_FXML = "tela_de_lançamento.fxml";
    public static void novoLançamento() {
        setPopupStage(INSERIR_LANÇAMENTO_FXML, new Controlador_da_tela_de_lançamento(new Lançamento()));
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Mr Contas!");
        Aplicativo.stage = stage;
        Aplicativo.popupStage = new Stage();
        configurar_tela("tela_de_transbordamento.fxml");
    }

    private static Stage stage;
    private static Stage popupStage;
    private static Stage getStage() { return stage;}
    private static Stage getPopupStage() { return popupStage;}

    public static void configurar_tela(String fxmlFile) {

        FXMLLoader fxmlLoader = new FXMLLoader(Aplicativo.class.getResource(fxmlFile));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        getStage().setScene(scene);
        getStage().show();
    }

    private static FXMLLoader getNewFXMLLoader(String fxmlFile, Object controlador){
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicativo.class.getResource(fxmlFile));
        fxmlLoader.setController(controlador);
        return fxmlLoader;
    }

    public static void setPopupStage(String fxmlFile, Object controlador) {
        setStageSceneFromLoader(
                getNewFXMLLoader(fxmlFile, controlador)
                ,getPopupStage()
        );
    }

    public static void setPopupStage(String fxmlFile) {
        setStageSceneFromLoader(
            new FXMLLoader(Aplicativo.class.getResource(fxmlFile))
            ,getPopupStage()
        );
    }

    public static void setStageSceneFromLoader(FXMLLoader fxmlLoader, Stage stage) {
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}