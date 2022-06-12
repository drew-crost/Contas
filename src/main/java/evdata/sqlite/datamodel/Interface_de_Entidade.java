package evdata.sqlite.datamodel;

import java.util.EnumMap;
import java.util.EnumSet;

public interface Interface_de_Entidade<T extends Enum<T>, S extends Enum<S>, E> {

    EnumMap chave_primária();

    EnumMap<T, Boolean> atributos();
    EnumMap<S, Interface_de_Associação> associações();


    String ler(T atributo);
    EnumMap<T,String> ler(EnumSet<T> atributos);
    public abstract <E extends Interface_de_Entidade> E escrever(Enum atributo, String valor);
    public abstract <E extends Interface_de_Entidade> E escrever(EnumMap atributos_editados);
    Interface_de_Entidade acessar(S associação);
    EnumMap<S, Interface_de_Associação> acessar(EnumSet<S> associações);

    public abstract Interface_de_Entidade<T,S, E> inserir();
    public abstract Interface_de_Entidade<T,S, E> atualizar();
    public abstract Interface_de_Entidade<T,S, E> salvar();
    public abstract Interface_de_Entidade<T,S, E> deletar();

    //protected abstract String roteiro_para_criar_tabela();
    //protected abstract String roteiro_para_selecionar_registros();
    //protected abstract String roteiro_para_adicionar_registro();
    //protected abstract String roteiro_para_deletar_registro();
    //protected abstract String roteiro_para_editar_registro();
    //protected abstract String cláusula_SQL_Where();
    //protected abstract String cláusula_SQL_Where(CharSequence apelido_de_tabela);
    //protected abstract String cláusula_SQL_Where(EnumSet atributos);
    //protected abstract String cláusula_SQL_Where(EnumSet atributos, CharSequence apelido_de_tabela);
    //protected abstract String cláusula_SQL_Where(EnumMap atributos_apelidados);
    //protected abstract String cláusula_SQL_Where(EnumMap atributos_apelidados, CharSequence apelido_de_tabela);
    //protected abstract String cláusula_SQL_Where_de_Chave_Primária();
    //protected abstract String cláusula_SQL_Where_de_Chave_Primária(CharSequence apelido_de_tabela);
}

