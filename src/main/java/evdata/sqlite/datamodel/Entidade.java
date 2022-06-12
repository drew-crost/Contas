package evdata.sqlite.datamodel;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Entidade <T extends Enum<T>, S extends Enum<S>, E> implements Interface_de_Entidade <T, S, E>{

    private final EnumMap<T, Boolean> mapa_de_atributos;
    private final EnumMap<S, Interface_de_Associação> mapa_de_associações;

    private EnumMap atributo_e_valor; //private java.util.List<FieldValue<Entity>> getFieldValues() {return fieldValues;}

    protected abstract String nome_de_domínio();
    protected abstract String nome_da_entidade();

    //protected abstract EnumSet enumerações_da_chave_primária();
    //protected abstract Class classe_de_enumeração_de_associações();
    //protected abstract Class classe_de_enumerações_de_atributos();

    public Entidade(EnumMap atributos, EnumMap<S, Interface_de_Associação> associações) {
        mapa_de_associações = associações;
        mapa_de_atributos = atributos;
        atributo_e_valor = new EnumMap(new HashMap<T, String>());
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entidade entity = (Entidade) o;
        return nome_de_domínio().equals(entity.nome_de_domínio()) && nome_da_entidade().equals(entity.nome_da_entidade());
    }


    @Override public int hashCode() {
        return Objects.hash(nome_de_domínio(), nome_da_entidade());
    }


    private java.util.Map<EnumSet, Interface_de_Entidade> chaves_estrangeiras = new HashMap<>();


    @Override public EnumMap<T, Boolean> atributos() {
        return mapa_de_atributos;
    }
    /*public EnumSet atributos(Enum associação){
        SimpleEntry<EnumSet, Interface_de_Entidade> second_entry
            = (SimpleEntry<EnumSet, Interface_de_Entidade>)
                mapa_de_associações.get(associação);
        EnumSet resultado = second_entry.getKey();
        return resultado;
    }*/
    @Override public EnumMap chave_primária() {
        mapa_de_atributos.entrySet().stream()
            .filter(entry->entry.getValue().equals(true));
        return null;
    }
    @Override public EnumMap<S, Interface_de_Associação> associações() {
        return mapa_de_associações;
    }
    @Override public String ler(T atributo) {
        return (String) atributo_e_valor.get(atributo);
    }
    @Override public EnumMap<T, String> ler(EnumSet<T> atributos) {
        if(atributos==null){
            return null;
        }
        EnumMap<T, String> resultado = new EnumMap(atributo_e_valor);
        resultado.clear();
        atributos.stream().map(atributo->resultado.put(atributo, ler(atributo)));
        return resultado;
    }
    @Override public Interface_de_Entidade acessar(Enum associação) {
        Interface_de_Fábrica fabrica
            = (
                (Interface_de_Fábrica)
                mapa_de_associações.get(associação)
            );
        return (fabrica==null?null:fabrica.criar()
            .escrever(ler(mapa_de_associações.get(associação).atributos()))
        );
    }
    @Override public <Tipo_Final extends Interface_de_Entidade> Tipo_Final escrever(Enum campo, String valor) {
        atributo_e_valor.put(campo, valor);
        return (Tipo_Final) this;
    }
    @Override public <Tipo_Final extends Interface_de_Entidade> Tipo_Final escrever(EnumMap campos_editados) {
        atributo_e_valor.putAll(campos_editados);
        return (Tipo_Final) this;
    }

    @Override
    public Interface_de_Entidade<T,S,E>  inserir() {
        return this;
    }

    @Override
    public Interface_de_Entidade<T,S,E> atualizar() {
        return this;
    }

    @Override
    public Interface_de_Entidade<T,S,E> salvar() {
        return this;
    }

    @Override
    public Interface_de_Entidade<T,S,E>  deletar() {
        return this;
    }
















    protected String roteiro_para_criar_tabela() {
        return
        "CREATE TABLE "+nome_da_entidade()+"("
            + atributos().entrySet().stream()
                .map(campo -> ((Enum)campo).name())
                .collect(Collectors.joining(", "))
        +")"
        +(chave_primária().entrySet().size()==0 ? ""
            :" PRIMARY KEY("
                + chave_primária().entrySet().stream()
                .map(campo -> ((Enum)campo).name())
                .collect( Collectors.joining(", ") )
            +")"
        );
    }

    protected String roteiro_para_selecionar_registros() {
        return "select * from "+ nome_da_entidade();
    }

    public String roteiro_para_adicionar_registro() {
        Entidade entidade = this;
        return "INSERT INTO "
                + nome_da_entidade()+" ("+
                entidade.atributos().entrySet().stream()
                    .map(campo -> ((Enum)campo).name())
                    .collect(Collectors.joining(", "))
                +")"
                +" values("+
                entidade.atributos().entrySet().stream()
                    .map(campo -> "'"+entidade.ler((Enum)campo)+"'")
                    .collect(Collectors.joining(", "))
                +")";
    }
    protected String roteiro_para_deletar_registro() {
        return null;
    }
    protected String roteiro_para_editar_registro() {
        return null;
    }
    protected String cláusula_SQL_Where() {
        return cláusula_SQL_Where("");
    }
    protected String cláusula_SQL_Where(CharSequence alias) {
        return cláusula_SQL_Where(atributos(), alias);
    }
    protected String cláusula_SQL_Where(EnumSet campos) {
        return cláusula_SQL_Where(campos,"");
    }
    protected String cláusula_SQL_Where(EnumSet campos, CharSequence alias) {
        return (String)
                campos
                .stream()
                .map(campo->((Enum)campo).name()+"="+ ler((T)campo))
                .collect(Collectors.joining(" AND "));
    }
    protected String cláusula_SQL_Where(EnumMap atributos_apelidados) {
        return null;
    }
    protected String cláusula_SQL_Where(EnumMap atributos_apelidados, CharSequence apelido_de_tabela){
        return null;
    }
    public String cláusula_SQL_Where_de_Chave_Primária() {
        return cláusula_SQL_Where_de_Chave_Primária("");
    }
    public String cláusula_SQL_Where_de_Chave_Primária(CharSequence alias) {
        return cláusula_SQL_Where(this.atributos(), alias);
    }

    public class Associação implements Interface_de_Associação{
        private EnumSet atributos;
        private Interface_de_Fábrica fábrica_de_entidade;
        public Associação(EnumSet atributos, Interface_de_Fábrica fábrica_de_entidade){
            this.atributos=atributos;
            this.fábrica_de_entidade=fábrica_de_entidade;
        }
        public EnumSet atributos(){
            return atributos;
        }
        public Interface_de_Fábrica fábrica_de_entidade(){
            return fábrica_de_entidade;
        }
        public Interface_de_Entidade entidade(){
            return (fábrica_de_entidade==null?null:fábrica_de_entidade.criar()
                    .escrever(ler(atributos))
            );
        }
    }


    public AbstractMap.SimpleEntry registro_de_associação(Enum associação, Interface_de_Fábrica fábrica, Enum ... atributos){
        return new AbstractMap.SimpleEntry(associação,
            new Associação(
                null//toEnumSet(atributos[0].getClass(), atributos)
                , fábrica
            )
        );
    }

    private static <T extends Enum<T>> EnumSet<T> toEnumSet(Class<T> clazz,T[] ts) {
        if (ts == null)
            return null;

        EnumSet<T> res = EnumSet.noneOf(clazz);
        for (T t : ts) {
            res.add(t);
        }
        return res;
    }
};
