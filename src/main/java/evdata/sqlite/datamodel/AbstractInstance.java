package evdata.sqlite.datamodel;

public abstract class AbstractInstance<E extends Interface_de_Entidade> implements Instance<E>{
    //@Override public abstract E getEntity(); //{return entity;}

    @Override public abstract E getEntity();




}

    /*
    public BasicInstance(E entity, List<FieldValue<E>> fieldValues) {
        this.entity = entity;
        this.fieldValues = fieldValues;
    }*/

    //private E entity;
