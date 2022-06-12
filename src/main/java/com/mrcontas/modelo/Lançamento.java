package com.mrcontas.modelo;

import com.mrcontas.dao.OAD;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Lançamento {

    private long id=-1;
    private LocalDateTime datahora;
    private String histórico;
    private List<Movimento> movimentos = new ArrayList<>();


    public long getId() {
        return id;
    }
    private Lançamento setId(long id) {
        this.id = id;
        return this;
    }
    public LocalDateTime getDatahora() {
        return datahora;
    }
    public Lançamento setDatahora(LocalDateTime datahora) {
        this.datahora = datahora;
        return this;
    }
    public String getHistórico() {
        return histórico;
    }
    public Lançamento setHistórico(String histórico) {
        this.histórico = histórico;
        return this;
    }

    public List<Movimento> getMovimentos(){
        return movimentos;
    }
    public BigDecimal soma(){
        return movimentos.stream()
            .map(movimento -> movimento.getValor())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Movimento addMovimento(){
        Movimento resultado = new Movimento();
        movimentos.add(resultado);
        return resultado;
    }

    public Lançamento inserir(){
        if(!soma().equals(BigDecimal.ZERO)){
            return null;
        }
        long id = OAD.inserir_1_linha(
                roteiro_para_inserir_1_registro_de_cabeçalho_de_lançamento(
                    EnumSet.of(Entidade.atributos.Histórico)
                ));
        this.setId(id);
        movimentos.stream().map(
            movimento -> movimento.inserir()
        );
        return this;
    }

    public Lançamento atualizar(){
        List<Map<String, String>> resultado_select = OAD.executar_consulta_select("*", "Lançamentos", "Id = "+getId());
        if(resultado_select ==null||resultado_select.size()==0){
            return null;
        }
        Map<String, String> registro_de_lançamento = resultado_select.get(0);
        List<Map<String, String>> registros_de_movimentos = OAD.executar_consulta_select("*", "Movimentos","Lançamento = "+getId());
        if(registros_de_movimentos==null){
            return null;
        }
        return atualizar(registro_de_lançamento, registros_de_movimentos);
    }

    public Lançamento atualizar(Map<String, String> registro_de_lançamento, List<Map<String, String>> registros_de_movimentos){
        this
            .setDatahora(LocalDateTime.parse(registro_de_lançamento.get("DataHora")))
            .setHistórico(registro_de_lançamento.get("Histórico"));
        movimentos.clear();
        registros_de_movimentos.stream().map(
            registro_de_movimento -> movimentos.add(
                new Movimento().atualizar(registro_de_movimento)
            )
        );
        return this;
    }
    public Lançamento salvar(){
        deletar();
        inserir();
        //OAD.executar("update Lançamentos set Histórico='"+getHistórico()+"' where Id="+getId()+";");
        return this;
    }
    public Lançamento deletar(){
        if(getId()==-1){
            return null;
        }
        OAD.executar("delete from Lançamentos where Id="+getId()+";");
        OAD.executar("delete from Movimentos where Lançamento="+getId()+";");
        return this;
    }

    public String roteiro_para_inserir_1_registro_de_cabeçalho_de_lançamento(EnumSet atributos) {
        return "INSERT INTO "
                + entidade.nome_da_entidade()+" ("+
                atributos.stream()
                        .map(atributo -> ((Enum)atributo).name())
                        .collect(Collectors.joining(", "))
                +")"
                +" values("+
                atributos.stream()
                        .map(atributo -> "'"+entidade.ler((Enum)atributo)+"'")
                        .collect(Collectors.joining(", "))
                +")";
    }

    private Entidade entidade = new Entidade();
    private class Entidade {
        private enum atributos {Id, DataHora, Histórico}
        String nome_da_entidade(){
            return "Lançamentos";
        }
        EnumSet atributos(){
            return EnumSet.allOf(Entidade.atributos.class);
        }
        String ler(Enum atributo){
            String resultado = null;
            switch ((Entidade.atributos)atributo){
                case Id:
                    resultado = String.valueOf(getId());
                    break;
                case DataHora:
                    resultado = getDatahora().toString();
                    break;
                case Histórico:
                    resultado = getHistórico();
                    break;
            }
            return resultado;
        }
    }

    public class Movimento {

        private long id=-1;
        private Conta conta;
        private BigDecimal valor;
        private Lançamento lançamento;

        public Movimento(){
            lançamento=Lançamento.this;
        }

        private Movimento atualizar(Map<String, String> registro_de_movimento){
            return setId(registro_de_movimento.get("Id"))
                    .setConta(new Conta().setId(registro_de_movimento.get("Conta")).atualizar())
                    .setValor(registro_de_movimento.get("Valor"));
        }

        public long getId() {
            return id;
        }
        private Movimento setId(long id) {
            this.id = id;
            return this;
        }

        private Movimento setId(String id) {
            this.id = Long.parseLong(id);
            return this;
        }

        public Conta getConta() {
            return conta;
        }
        public Movimento setConta(Conta conta) {
            this.conta = conta;
            return this;
        }

        public BigDecimal getValor(){
            return (valor==null?BigDecimal.ZERO:valor);
        }
        public Movimento setValor(BigDecimal valor) {
            this.valor = valor;
            return this;
        }

        public Movimento setValor(String valor) {
            if(valor==null||"".equals(valor)){
                this.valor=BigDecimal.ZERO;
            }else{
                this.valor = new BigDecimal(valor);
            }
            return this;
        }

        public Lançamento getLançamento() {
            return lançamento;
        }

        private Movimento inserir(){
            long id = OAD.inserir_1_linha(
                    roteiro_para_inserir_1_registro_de_movimento(
                            EnumSet.of(
                                Entidade.atributos.Conta
                                , Entidade.atributos.Lançamento
                                , Entidade.atributos.Valor
                            )
                    ));
            this.setId(id);
            return this;
        }
        private Movimento atualizar(){
            List<Map<String, String>> resultado_select = OAD.executar_consulta_select("*","Movimentos", "id = "+getId());
            if(resultado_select ==null){
                return null;
            }
            this
                .setConta(new Conta().setId(resultado_select.get(0).get("Conta")).atualizar())
                .setValor(resultado_select.get(0).get("Valor"));
            return this;
        }
        private Movimento salvar(){
            OAD.executar("update Movimentos set Conta='"+getConta().getId()+" Valor='"+getValor()+"' where Id="+getId()+";");
            return this;
        }
        private Movimento deletar(){
            OAD.executar("delete from Movimentos where Id="+getId()+";");
            return this;
        }



        private String roteiro_para_inserir_1_registro_de_movimento(EnumSet atributos) {
            return "INSERT INTO "
                    + entidade.nome_da_entidade()+" ("+
                    atributos.stream()
                            .map(atributo -> ((Enum)atributo).name())
                            .collect(Collectors.joining(", "))
                    +")"
                    +" values("+
                    atributos.stream()
                            .map(atributo -> "'"+entidade.ler((Enum)atributo)+"'")
                            .collect(Collectors.joining(", "))
                    +")";
        }


        private Entidade entidade = new Entidade();
        private class Entidade {
            private enum atributos {Id, Conta, Lançamento, Valor}
            String nome_da_entidade(){
                return "Movimentos";
            }
            EnumSet atributos(){
                return EnumSet.allOf(Entidade.atributos.class);
            }
            String ler(Enum atributo){
                String resultado = null;
                switch ((Entidade.atributos)atributo){
                    case Id:
                        resultado = String.valueOf(getId());
                        break;
                    case Conta:
                        resultado = String.valueOf(getConta().getId());
                        break;
                    case Lançamento:
                        resultado = String.valueOf(getLançamento().getId());
                        break;
                    case Valor:
                        resultado = String.valueOf(getValor());
                        break;
                }
                return resultado;
            }
        }
    }
}

/*public static enum ATRIBUTO {ID, DATAHORA, HISTÓRICO, FOTO}
    private static EnumMap<Lançamento.ATRIBUTO, Boolean> mapa_de_campos = new EnumMap(
            Map.of(
                    ATRIBUTO.ID, true,
                    ATRIBUTO.DATAHORA, false,
                    ATRIBUTO.HISTÓRICO, false,
            )
    );*/

    /*public static enum ASSOCIAÇÃO {IMAGEM}
    private static EnumMap<Lançamento.ASSOCIAÇÃO, Interface_de_Associação> mapa_de_associações = new EnumMap(
            Map.ofEntries(
                Associação.registrar(ASSOCIAÇÃO.IMAGEM, Conta.a_fábrica(), ATRIBUTO.FOTO )
            )
    );*/


    /*public Lançamento(EnumMap atributos, EnumMap<ASSOCIAÇÃO, Interface_de_Associação> associações) {
            super(atributos, associações);
        }*/
    /*
    private static class Fábrica implements Interface_de_Fábrica<Lançamento> {
        @Override
        public Lançamento criar() {
            return new Lançamento(mapa_de_campos, mapa_de_associações);
        }
    }
    public Interface_de_Fábrica<Lançamento> fábrica(){
        return new Fábrica();
    }
    @Override public String nome_de_domínio() {return null;}
    @Override public String nome_da_entidade() {return "Lançamentos";}
    */