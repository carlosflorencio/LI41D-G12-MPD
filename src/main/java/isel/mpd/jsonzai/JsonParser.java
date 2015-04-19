package isel.mpd.jsonzai;

import isel.mpd.jsonzai.factory.TypeFactoryJson;
import isel.mpd.jsonzai.utils.JsonUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class JsonParser<T> {

    private static TypeFactoryJson typeFactoryJson;

    public JsonParser(){
        typeFactoryJson = new TypeFactoryJson();
    }

    public <T> T toObject(String src, Class<T> dest) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        String cleanedSrc = JsonUtils.clean(src);

        T obj = (T) dest.getConstructors()[0].newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            String nameOfField = field.getName().toLowerCase();
            int initialIndex = JsonUtils.getBeginIndexOfValue(cleanedSrc, nameOfField);

            if(initialIndex == -1) { //no key in json
                continue;
            }

            int finalIdx;

            if(isPrimitive(field.getType())){
                finalIdx = cleanedSrc.indexOf(",", initialIndex);
                String value = cleanedSrc.substring(initialIndex, finalIdx);

                field.set(obj, typeFactoryJson.getCreator(value).apply(value));
            }
            else if(field.getType().isAssignableFrom(String.class)) {
                finalIdx = cleanedSrc.indexOf("\",", initialIndex) + 1;
                String value = cleanedSrc.substring(initialIndex, finalIdx);

                field.set(obj, typeFactoryJson.getCreator(value).apply(value));

            } else if(field.getType().isAssignableFrom(Array.class)){
                //TODO: ...
                String value = JsonUtils.getObject(cleanedSrc, initialIndex, '[', ']');
                field.set(obj, toList(value, field.getClass().getComponentType()));

            } else {
                String value = JsonUtils.getObject(cleanedSrc, initialIndex, '{', '}');
                field.set(obj, toObject(value, field.getType()));

            }
        }

        return obj;
    }


    public <T> List<T> toList(String src, Class<T> dest) throws IllegalAccessException, InstantiationException, InvocationTargetException {
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

    private static boolean isPrimitive(Class<?> type) {
        return type.isAssignableFrom(Integer.class) ||
                type.isAssignableFrom(int.class) ||
                type.isAssignableFrom(Double.class) ||
                type.isAssignableFrom(double.class) ||
                type.isAssignableFrom(Boolean.class) ||
                type.isAssignableFrom(boolean.class) ||
                type.isAssignableFrom(Float.class) ||
                type.isAssignableFrom(float.class) ||
                type.isAssignableFrom(Long.class) ||
                type.isAssignableFrom(long.class) ||
                type.isAssignableFrom(Character.class) ||
                type.isAssignableFrom(char.class);
    }
}
