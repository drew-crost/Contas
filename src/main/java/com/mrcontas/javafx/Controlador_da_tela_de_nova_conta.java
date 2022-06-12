package com.mrcontas.javafx;

import com.mrcontas.modelo.Lançamento;
import com.mrcontas.modelo.Lançamento.Movimento;
import javafx.fxml.FXML;

import java.math.BigDecimal;

public class Controlador_da_tela_de_nova_conta {

    @FXML javafx.scene.control.TextField caixa_de_texto_de_id;
    @FXML javafx.scene.control.TextField caixa_de_texto_de_nome;
    @FXML protected void comando_de_botão_para_inserir_conta() {
        Aplicativo.adicionar_conta(caixa_de_texto_de_nome.getText());
    }

    protected void comando_de_botão_para_inserir_lançamento() {
        Lançamento l = new Lançamento();
        Movimento m = l.addMovimento();
        m.setValor(BigDecimal.valueOf(150));
        Movimento n = l.addMovimento();
        n.setValor(BigDecimal.valueOf(-150));
        l.inserir();
    }
}