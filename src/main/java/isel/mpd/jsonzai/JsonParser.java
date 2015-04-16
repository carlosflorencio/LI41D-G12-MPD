package isel.mpd.jsonzai;

import isel.mpd.jsonzai.factory.TypeFactoryJson;
import isel.mpd.jsonzai.utils.JsonUtils;

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
        src = JsonUtils.clean(src);

        T obj = (T) dest.getConstructors()[0].newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            String nameOfField = field.getName().toLowerCase();
            int initialIndex = JsonUtils.getBeginIndexOfValue(src, nameOfField);

            if(initialIndex == -1) { //no key in json
                continue;
            }

            int finalIdx;

            if(JsonUtils.isPrimitive(field.getType())){
                finalIdx = src.indexOf(",", initialIndex);
                String value = src.substring(initialIndex, finalIdx);

                field.set(obj, typeFactoryJson.getCreator(value).apply(value));
            }
            else if(field.getType().isAssignableFrom(String.class)){
                finalIdx = src.indexOf("\",", initialIndex) + 1;
                String value = src.substring(initialIndex, finalIdx);

                field.set(obj, typeFactoryJson.getCreator(value).apply(value));
            } else {
                String value = JsonUtils.getObject(src, initialIndex);
                field.set(obj, toObject(value, field.getType()));
            }
        }

        return obj;
    }


    public <T> List<T> toList(String src, Class<T> dest) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<T> list = new LinkedList<>();

        int i = 1;
        while (i < src.length()) {
            String strObj = JsonUtils.getObject(src, i);
            T obj = toObject(strObj, dest);

            list.add(obj);

            i += strObj.length() + 1;
        }
        return list;
    }
}
