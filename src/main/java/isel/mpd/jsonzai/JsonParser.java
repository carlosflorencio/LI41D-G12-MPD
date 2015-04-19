package isel.mpd.jsonzai;

import isel.mpd.jsonzai.factory.TypeFactoryInterface;
import isel.mpd.jsonzai.factory.TypeFactoryJson;
import isel.mpd.jsonzai.factory.exceptions.TypeCreatorNotFound;
import isel.mpd.jsonzai.utils.JsonUtils;
import isel.mpd.jsonzai.utils.TypeUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class JsonParser<T> {

    private TypeFactoryInterface factory;

    public JsonParser(){
        factory = new TypeFactoryJson();
    }

    @SuppressWarnings("unchecked")
    public <T> T toObject(String src, Class<T> dest) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        String json = JsonUtils.clean(src); //minify the json


        T obj = (T) dest.getConstructors()[0].newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            String nameOfField = field.getName().toLowerCase();
            Class<?> type = field.getType();
            int initialIndex = JsonUtils.getBeginIndexOfValue(json, nameOfField);

            if(initialIndex == -1) { //no key in json
                continue;
            }

            Object resultValue;
            String value;

            if (TypeUtils.isPrimitive(type)) {
                value = json.substring(initialIndex, json.indexOf(",", initialIndex));
                resultValue = createValue(type, value);
            } else if (TypeUtils.isString(type)) {
                value = json.substring(initialIndex, json.indexOf("\",", initialIndex) + 1);
                resultValue = createValue(type, value);
            } else if (TypeUtils.isArray(type)) {
                value = JsonUtils.getObject(json, initialIndex, '[', ']');
                resultValue = toList(value, field.getClass().getComponentType()).toArray();
            } else {
                value = JsonUtils.getObject(json, initialIndex, '{', '}');
                resultValue = toObject(value, type);
            }

            field.set(obj, resultValue);
        }

        return obj;
    }


    public <T> List<T> toList(String src, Class<T> dest) throws IllegalAccessException, InstantiationException, InvocationTargetException {
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

}
