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
        String result = JsonUtils.cleanObject(src);

        T obj = (T) dest.getConstructors()[0].newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            String nameOfField = field.getName().toLowerCase();
            int initialIndex = result.indexOf(nameOfField)+nameOfField.length()+1;
            int finalIdx;
            if(JsonUtils.isPrimitive(field.getType())){
                finalIdx = result.indexOf(",", initialIndex);

                field.set(obj, typeFactoryJson.getCreator(result.substring(initialIndex, finalIdx))
                                .apply(result.substring(initialIndex, finalIdx)));
            }
            else if(field.getType().isAssignableFrom(String.class)){
                finalIdx = result.indexOf("\",", initialIndex) + 1;

                field.set(obj, typeFactoryJson.getCreator(result.substring(initialIndex, finalIdx))
                        .apply(result.substring(initialIndex, finalIdx)));
            } else {
                field.set(obj, toObject(JsonUtils.getObject(result, initialIndex), field.getType()));
            }
        }

        return obj;
    }


    public <T> List<T> toList(String src, Class<T> dest) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<T> list = new LinkedList<>();

        int i = 1;
        while (i < src.length()) {
            String strObj = JsonUtils.getObject(src, i);
            System.out.println(strObj);
            T obj = toObject(strObj, dest);

            list.add(obj);

            i += strObj.length() + 1;
        }
        return list;
    }
}
