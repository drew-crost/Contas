package evdata.sqlite.datamodel;

public interface Instance<E extends Interface_de_Entidade> {
    public E getEntity();

    //public java.util.List<FieldValue<E>> getFieldValues();
}
