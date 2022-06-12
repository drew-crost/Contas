package evdata.sqlite.datamodel;

public interface Campo_e_Valor<E extends Interface_de_Entidade> {

    public String Value();
    public Field<E> getField();
    public String getWhereClause();

    //public void setField(Field<E> field);
    //public Instance<E> getInstance();
    //public void setInstance(Instance<E> instance);
}
