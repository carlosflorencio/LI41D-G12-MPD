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

public class JsonParser {

    private TypeFactoryInterface factory;

    public JsonParser() {
        factory = new TypeFactoryJson();
    }

    /**
     * Populate an object from a json object.
     * Make sure json is minified first!
     */
    @SuppressWarnings("unchecked")
    public <T> T toObject(String json, Class<T> dest) {
        try {
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
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // if we can't create the object
            return null;
        }
    }


    /**
     * Generate a list from a json array.
     * Make sure json is minified first!
     */
    public <T> List<T> toList(String src, Class<T> dest) {
        List<T> list = new LinkedList<>();

        int i = 1;
        while (i < src.length()) {
            String strObj = JsonUtils.getObject(src, i, '{', '}');
            T obj = toObject(strObj, dest);

            list.add(obj);

            i += strObj.length() + 1;
        }
        return list;
    }

    /**
     * Create the value type or null if we dont know how to make it
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
     */
    private Object getObjectFromJsonValue(String json, Field field, Class<?> type, int initialIndex)
            throws IllegalAccessException, InvocationTargetException {
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
