package evdata.sqlite.datamodel;

import java.util.AbstractMap;
import java.util.EnumSet;

public class Atributo<T extends Enum<T>> implements Interface_de_Atributo<T>{

    public static <T extends Enum<T>> Atributo<T> criar(T atributo, Boolean chave_primária){
        return new Atributo<T>(atributo, chave_primária);
    }
    public static <T extends Enum<T>> AbstractMap.SimpleEntry registrar(T atributo,Boolean chave_primária){
        return new AbstractMap.SimpleEntry(atributo,chave_primária);
    }

    private T atributo;
    public T atributo(){ return atributo; }
    private Boolean chave_primária;
    public Boolean chave_primária() { return chave_primária; }

    public Atributo(T atributo, Boolean chave_primária){
        this.atributo=atributo;
        this.chave_primária=chave_primária;
    }

}
