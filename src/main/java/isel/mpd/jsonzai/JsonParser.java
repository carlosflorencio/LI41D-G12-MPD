package isel.mpd.jsonzai;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class JsonParser<T> {

    public <T> T toObject(String src, Class<T> dest){

        try {
            T obj = (T) dest.getConstructors()[0].newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                String nameOfField = fields[i].getName().toLowerCase();
                Class fieldType = fields[i].getType();


                System.out.printf("%s -> Type: %s%n",nameOfField, fieldType);
            }

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    public <T> List<T> toList(String src, Class<T> dest){

        return null;
    }
}
