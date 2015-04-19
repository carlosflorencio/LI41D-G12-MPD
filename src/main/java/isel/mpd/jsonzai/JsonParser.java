package isel.mpd.jsonzai;

import isel.mpd.jsonzai.factory.TypeFactoryInterface;
import isel.mpd.jsonzai.factory.TypeFactoryJson;
import isel.mpd.jsonzai.factory.exceptions.TypeCreatorNotFound;
import isel.mpd.jsonzai.utils.JsonUtils;
import isel.mpd.jsonzai.utils.TypeUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;

public class JsonParser<T> {

    private TypeFactoryInterface factory;

    public JsonParser() {
        factory = new TypeFactoryJson();
    }

    /**
     * Populate an object from a json object.
     * Make sure json is minified first!
     *
     * @param json
     * @param dest
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    @SuppressWarnings("unchecked")
    public <T> T toObject(String json,
                          Class<T> dest) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        T obj = (T) dest.getConstructors()[0].newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            String nameOfField = field.getName().toLowerCase();
            Class<?> type = field.getType();
            int initialIndex = JsonUtils.getBeginIndexOfValue(json, nameOfField);

            if (initialIndex == -1) { //no key in json, the field should stay with the default value
                continue;
            }

            Object resultValue = getObjectFromJsonValue(json, field, type, initialIndex);

            field.set(obj, resultValue);
        }

        return obj;
    }


    /**
     * Generate a list from a json array.
     * Make sure json is minified first!
     *
     * @param src
     * @param dest
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public <T> List<T> toList(String src,
                              Class<T> dest) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<T> list = new LinkedList<>();

        int i = 1;
        while (i < src.length()) {
            String strObj = JsonUtils.getObject(src, i, '{', '}');  //ArrayTests fails here
            T obj = toObject(strObj, dest);

            list.add(obj);

            i += strObj.length() + 1;
        }
        return list;
    }

    /**
     * Create the value type or null if we dont know how to make it
     *
     * @param type
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    private Object createValue(Class<?> type, String value) {
        try {
            return this.factory.getCreator(type).apply(value);
        } catch (TypeCreatorNotFound typeCreatorNotFound) {
            return null;
        }
    }

    /**
     * Where the magic happens, translate a String value in json to a Java object
     *
     * @param json
     * @param field
     * @param type
     * @param initialIndex
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    private Object getObjectFromJsonValue(String json, Field field, Class<?> type, int initialIndex)
            throws IllegalAccessException, InstantiationException, InvocationTargetException {
        String value;
        Object resultValue;
        if (TypeUtils.isPrimitive(type)) {
            value = JsonUtils.getValue(json, initialIndex);
            resultValue = createValue(type, value);
        } else if (TypeUtils.isString(type)) {
            value = JsonUtils.getValue(json, initialIndex);
            resultValue = createValue(type, value);
        } else if (TypeUtils.isList(type)) {
            value = JsonUtils.getObject(json, initialIndex, '[', ']');
            ParameterizedType listType = (ParameterizedType) field.getGenericType();
            Class<?> t = (Class<?>) listType.getActualTypeArguments()[0];
            resultValue = toList(value, t);
        } else { //must be an Object
            value = JsonUtils.getObject(json, initialIndex, '{', '}');
            resultValue = toObject(value, type);
        }
        return resultValue;
    }

}
