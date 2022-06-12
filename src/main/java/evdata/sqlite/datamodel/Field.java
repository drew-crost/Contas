package evdata.sqlite.datamodel;

public interface Field<E extends Interface_de_Entidade> {
    //public String getName();
    //public boolean isPrimaryKeyField();
    //public Entity getEntity();
    //public void setEntity(Entity entity);
    //public void setPrimaryKeyField(boolean pkboolean);
    //public java.util.List<ForeignKey> getForeignKeysOfThisField();
    public String name();
    public Field[] values();
    @Override
    public boolean equals(Object o);

    @Override
    public int hashCode();

}
