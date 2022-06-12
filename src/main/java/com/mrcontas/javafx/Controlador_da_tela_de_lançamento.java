package com.mrcontas.javafx;

import com.mrcontas.consultas.Rol_de_Contas;
import com.mrcontas.modelo.Lançamento;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.math.BigDecimal;

import static javafx.geometry.Pos.BASELINE_LEFT;

public class Controlador_da_tela_de_lançamento {

    @FXML javafx.scene.layout.VBox quadro_de_movimentos;
    @FXML javafx.scene.control.TextArea histórico;
    private Lançamento lançamento;

    public Controlador_da_tela_de_lançamento(){
        super();
        this.lançamento=new Lançamento();
    }

    public Controlador_da_tela_de_lançamento(Lançamento lançamento){
        super();
        this.lançamento=lançamento;
    }

    @FXML
    protected void initialize() {
        new Quadro_de_Movimentos().initialize();
    }

    private class Quadro_de_Movimentos{

        private Controle_de_Movimento controle_de_movimento_residual;
        private void setControle_de_movimento_residual(Controle_de_Movimento controle_de_movimento_residual) {
            this.controle_de_movimento_residual = controle_de_movimento_residual;
            controle_de_movimento_residual.getPainel().setBackground(
                    new Background(
                        new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)
                    ));
        }


        private void initialize(){
            quadro_de_movimentos.getChildren().clear();
            setControle_de_movimento_residual(new Controle_de_Movimento());
            new Controle_de_Movimento();
        }


        private class Controle_de_Movimento {

            private HBox painel;
            {
                painel = new HBox();
                painel.setBorder(new Border(new BorderStroke(Color.valueOf("#9E9E9E"),
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));
                painel.setPadding(new Insets(10.0));
                AnchorPane.setLeftAnchor(painel,0.0);
                AnchorPane.setRightAnchor(painel,0.0);
                painel.setAlignment(BASELINE_LEFT);
            }
            private javafx.scene.control.ComboBox conta;
            {
                conta = new ComboBox();
                HBox.setHgrow(conta, Priority.ALWAYS);
                conta.setMinWidth(437);
                conta.setPrefWidth(437);
                conta.setMaxWidth(437);
            }
            private javafx.scene.control.TextField valor;
            {
                valor = new TextField();
                //CurrencyField valor = new CurrencyField(new Locale( "pt", "BR" ));
                //AnchorPane.setRightAnchor(valor, 0.0);
                HBox.setHgrow(valor, Priority.NEVER);
                valor.minWidth(115);
                valor.prefWidth(115);
                valor.setPromptText("R$ 0,00");
                //valor.setTextFormatter();
            }
            private Lançamento.Movimento movimento;

            public Controle_de_Movimento() {
                painel.getChildren().addAll(conta, valor);
                quadro_de_movimentos.getChildren().add(painel);
                ativar_evento();
                movimento = lançamento.addMovimento();
                configurar_controles();
            }
            private void configurar_controles(){
                conta.setOnAction(event -> movimento.setConta(Rol_de_Contas.get(Long.parseLong((String) conta.getValue()))));
                valor.setOnAction(event -> movimento.setValor(valor.getText()));
            }
            private void configurar_controle_conta(){}
            private void configurar_objeto_conta(){}

            public HBox getPainel() {
                return painel;
            }
            public void setPainel(HBox painel) {
                this.painel = painel;
            }
            public ComboBox getConta() {
                return conta;
            }
            public void setConta(ComboBox conta) {
                this.conta = conta;
            }
            public TextField getValor() {
                return valor;
            }
            public void setValor(TextField valor) {
                this.valor = valor;
            }
            public Lançamento.Movimento getMovimento() {
                return movimento;
            }
            public void setMovimento(Lançamento.Movimento movimento) {
                this.movimento = movimento;
            }

            private void ativar_bloqueio_de_edição_do_valor(){
                getValor().setEditable(false);
            }

            protected void lançar_valor_para_soma_zero_no_movimento_residual() {
                String t = controle_de_movimento_residual.getValor().getText();
                System.out.println("valor inicial do movimento residual: " + t);
                BigDecimal v = (
                    (t==null||t.equals(""))
                    ?BigDecimal.ZERO
                    :BigDecimal.valueOf(Float.parseFloat(t))
                ).subtract(lançamento.soma());
                controle_de_movimento_residual
                    .getValor().setText(String.valueOf(
                        v.floatValue()
                    )
                );
            }

            private void ativar_evento(){
                getValor().textProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println("textfield changed from " + oldValue + " to " + newValue);
                    if(!oldValue.equals(newValue)){
                        getMovimento().setValor(newValue);
                        gatilho_de_valor();
                    }
                });
            }

            protected void gatilho_de_valor() {
                lançar_valor_para_soma_zero_no_movimento_residual();
                String valor = getValor().textProperty().get();
                if(
                        (getConta().valueProperty().get()!=null)
                                &&
                                (valor!=null&&Float.parseFloat(valor)!=0.0)
                ){
                    new Controle_de_Movimento();
                }
            }
        }
    }

    @FXML
    protected void comando_de_botão_para_inserir_lançamento() {
        Lançamento l = new Lançamento();
        Lançamento.Movimento m = l.addMovimento();
        m.setValor(BigDecimal.valueOf(150));
        Lançamento.Movimento n = l.addMovimento();
        n.setValor(BigDecimal.valueOf(-150));
        l.inserir();
    }
}