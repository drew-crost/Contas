package evdata.sqlite.datamodel;

import java.util.Map;

public interface Chave_Estrangeira<E extends Interface_de_Entidade> {
    public String getForeignKeyName();
    public <F extends Interface_de_Entidade> Map<Field<E>, Field<F>> getFieldPairingMap();
    public FieldGroup<E> getFieldGroup();
    public <F extends Interface_de_Entidade> FieldGroup<F> getSourceFieldGroup();
}
