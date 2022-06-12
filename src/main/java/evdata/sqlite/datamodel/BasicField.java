package evdata.sqlite.datamodel;

import java.util.ArrayList;
import java.util.Objects;

public abstract class BasicField implements Field{

    private String FIELD_NAME;
    private Interface_de_Entidade entity;
    private boolean booleaIsPrimaryKeyField;
    private java.util.List<Chave_Estrangeira> foreignKeysOfThisField=new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicField field = (BasicField) o;
        return FIELD_NAME.equals(field.FIELD_NAME) && entity.equals(field.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FIELD_NAME, entity);
    }

    public BasicField(String fieldName
                      //, evdata.sqlite.datamodel.Entity entity
            //, boolean isPrimaryKeyField
            //, ForeignKey[] foreignKeysOfThisField
    ){
        //this.entity = entity;
        this.FIELD_NAME = fieldName;
        //this.booleaIsPrimaryKeyField = isPrimaryKeyField;
        //this.foreignKeysOfThisField = foreignKeysOfThisField;
    }

    //@Override
    public String getName() {
        return FIELD_NAME;
    }

    //@Override
    public Interface_de_Entidade getEntity() {
        return entity;
    }

    //@Override
    public void setEntity(Interface_de_Entidade entity) {
        this.entity=entity;
    }

    //@Override
    public boolean isPrimaryKeyField() {
        return booleaIsPrimaryKeyField;
    }

    //@Override
    public void setPrimaryKeyField(boolean pkboolean) {
        this.booleaIsPrimaryKeyField=pkboolean;
    }

    //@Override
    public java.util.List<Chave_Estrangeira> getForeignKeysOfThisField() {
        return foreignKeysOfThisField;
    }
}
