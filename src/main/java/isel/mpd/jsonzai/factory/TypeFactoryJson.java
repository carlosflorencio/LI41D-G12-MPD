package isel.mpd.jsonzai.factory;

import isel.mpd.jsonzai.factory.exceptions.TypeCreatorNotFound;
import isel.mpd.jsonzai.factory.types.*;

import java.util.HashMap;

public class TypeFactoryJson<T> implements TypeFactoryInterface<Class<?>> {

    private HashMap<Class<?>, TypeCreatorInterface> map = new HashMap<>();

    public TypeFactoryJson() {
        this.fillMap();
    }

    /**
     * Fill the map with the type creators
     */
    private void fillMap() {
        map.put(boolean.class, new BooleanTypeCreator());
        map.put(char.class, new CharacterTypeCreator());
        map.put(String.class, new StringTypeCreator());
        map.put(int.class, new IntegerTypeCreator());
        map.put(float.class, new FloatTypeCreator());
        map.put(double.class, new DoubleTypeCreator());
        map.put(long.class, new LongTypeCreator());
    }


    @Override
    public TypeCreatorInterface getCreator(Class<?> type) throws TypeCreatorNotFound {
        TypeCreatorInterface res = map.get(type);

        if (res == null) {
            throw new TypeCreatorNotFound();
        }

        return res;
    }

}
