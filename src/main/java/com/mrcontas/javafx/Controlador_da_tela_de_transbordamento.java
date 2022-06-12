package com.mrcontas.javafx;


import javafx.fxml.FXML;

public class Controlador_da_tela_de_transbordamento {

    @FXML
    protected void onNovoButtonClick() {
        Aplicativo.novoArquivo();
    }

    @FXML
    protected void onAbrirButtonClick() {
        Aplicativo.abrirArquivo();
    }

    @FXML
    protected void initialize(){

    }

}