package evdata.sqlite.datamodel;

public class BasicPrimaryKey implements Chave_Primária {

    private String PK_NAME;
    private FieldGroup fieldGroup;

    public BasicPrimaryKey(String pkName,FieldGroup fieldGroup)
    {
        this.PK_NAME = pkName;
        this.fieldGroup=fieldGroup;
    }

    @Override
    public String getPrimaryKeyName() {
        return PK_NAME;
    }

    public FieldGroup getFieldGroup() {
        return fieldGroup;
    }
}
