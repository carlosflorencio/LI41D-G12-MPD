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
        map.put(boolean.class, new BooleanTypeCreator()); //primitive
        map.put(Boolean.class, new BooleanTypeCreator()); //wrapper

        map.put(char.class, new CharacterTypeCreator());
        map.put(Character.class, new CharacterTypeCreator());

        map.put(int.class, new IntegerTypeCreator());
        map.put(Integer.class, new IntegerTypeCreator());

        map.put(float.class, new FloatTypeCreator());
        map.put(Float.class, new FloatTypeCreator());

        map.put(double.class, new DoubleTypeCreator());
        map.put(Double.class, new DoubleTypeCreator());

        map.put(long.class, new LongTypeCreator());
        map.put(Long.class, new LongTypeCreator());

        map.put(String.class, new StringTypeCreator());
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
