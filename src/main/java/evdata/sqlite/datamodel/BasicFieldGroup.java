package evdata.sqlite.datamodel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BasicFieldGroup implements FieldGroup {
    private java.util.List<Enum> fields;

    public BasicFieldGroup(Enum[] fieldArray){
        this.fields= Arrays.stream(fieldArray).toList();
    }

    /*
    @Override
    public String getWhereClause(Interface_de_Entidade instance) {
        return fields.stream()
        .map(f->instance.ler(f).getWhereClause())
        .collect(Collectors.joining(" AND "));
    }*/

    @Override
    public List<Enum> getFields() {
        return fields;
    }
}
