package evdata.sqlite.datamodel;

import java.util.EnumMap;

public class Mapa_Atributivo <K extends Enum<K>,V> extends EnumMap<K,V> {

    public Mapa_Atributivo(Class<K> keyType) {
        super(keyType);
    }
}
