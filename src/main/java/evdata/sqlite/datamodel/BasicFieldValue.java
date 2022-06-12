package evdata.sqlite.datamodel;

public class BasicFieldValue implements Campo_e_Valor {

    private final String value;
    private Instance instance;
    private Field field;

    public BasicFieldValue (Field field, String value){
        this.field=field;
        this.value=value;
    }

    @Override
    public String Value() {
        return value;
    }

    @Override
    public Field getField() {
        return null;
    }


    public void setField(Field field) {
        this.field=field;
    }

    @Override
    public String getWhereClause() {
        return field.name() +"="+ value;
    }

    //public Instance getInstance() { return instance; }
    //public void setInstance(Instance instance) { this.instance=instance; }

    /*public static FieldValue get(Field field, Instance instance){
        BasicFieldValue fv = new BasicFieldValue();
        fv.setField(field);
        fv.setInstance(instance);
        return fv;
    }*/
}
