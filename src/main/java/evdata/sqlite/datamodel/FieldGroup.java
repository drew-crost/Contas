package evdata.sqlite.datamodel;

public interface FieldGroup<E extends Interface_de_Entidade> {

    //public String getWhereClause(Interface_de_Entidade instance);

    public java.util.List<Field> getFields();
}
