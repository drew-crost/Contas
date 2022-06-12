package com.mrcontas.modelo;

import com.mrcontas.dao.OAD;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Conta {



    private long id=-1;
    public long getId() {
        return id;
    }
    public Conta setId(long id) {
        this.id = id;
        return this;
    }

    public Conta setId(String id) {
        this.id = Long.parseLong(id);
        return this;
    }

    private String nome;
    public String getNome() { return nome; }
    public Conta setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Conta inserir(){
        long id = OAD.inserir_1_linha(roteiro_para_inserir_1_linha(EnumSet.of(Entidade.atributos.Nome)));
        this.setId(id);
        return this;
    }
    public Conta atualizar(){
        List<Map<String, String>> resultado_select = OAD.executar_consulta_select("*","Contas", "id = "+getId());
        this.setNome(resultado_select.get(0).get("Nome"));
        return this;
    }
    public Conta salvar(){
        OAD.executar("update Contas set nome='"+getNome()+"' where Id="+getId()+";");
        return this;
    }
    public Conta deletar(){
        OAD.executar("delete from Contas where Id="+getId()+";");
        return this;
    }


    public String roteiro_para_inserir_1_linha(EnumSet atributos) {
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
        private enum atributos {Id, Nome}
        String nome_da_entidade(){
            return "Contas";
        }
        EnumSet atributos(){
            return EnumSet.allOf(atributos.class);
        }
        String ler(Enum atributo){
            String resultado = null;
            switch ((atributos)atributo){
                case Id:
                    resultado = String.valueOf(getId());
                    break;
                case Nome:
                    resultado = getNome();
                    break;
            }
            return resultado;
        }

    }
}

/*
public class Conta extends Entidade<Conta.ATRIBUTO,Conta.ASSOCIAÇÃO> {


    public static enum ATRIBUTO {ID, NOME}
    private static EnumMap<Movimento.ATRIBUTO, Boolean> mapa_de_campos = new EnumMap(
            Map.of(
                    Movimento.ATRIBUTO.ID, true,
                    Movimento.ATRIBUTO.CONTA, false,
                    Movimento.ATRIBUTO.VALOR, false,
                    Movimento.ATRIBUTO.LANÇAMENTO, false
            )
    );

    public static enum ASSOCIAÇÃO {VAZIA};

    private static class Fábrica implements Interface_de_Fábrica {
        @Override
        public Conta criar() {
            return new Conta();
        }
    }
    private static Interface_de_Fábrica fábrica = new Fábrica();
    public static Interface_de_Fábrica a_fábrica(){
        return fábrica;
    }
    @Override public Interface_de_Fábrica fábrica(){
        return fábrica;
    }
    Conta(){
        super(mapa_de_campos, null);
    }

    @Override public String nome_de_domínio() {return null;}
    @Override public String nome_da_entidade() {return "Contas";}

}
*/