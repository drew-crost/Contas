package evdata.sqlite.datamodel;

public interface Chave_Primária<E extends Interface_de_Entidade>{
    public String getPrimaryKeyName();
    public FieldGroup<E> getFieldGroup();
}
