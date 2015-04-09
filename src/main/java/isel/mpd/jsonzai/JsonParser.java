package isel.mpd.jsonzai;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Nuno on 05/04/2015.
 */
public class JsonParser<T> {

    public <T> T toObject(String src, Class<T> dest){

        try {
            T obj = (T) dest.getConstructors()[0].newInstance();
            Field[] fields = obj.getClass().getFields();
            Method[] methods = obj.getClass().getMethods();

            Scanner sc = new Scanner(src);

            while(sc.hasNext()) {
                //TODO: read string
                for (int i = 0; i < fields.length; i++) {
                    String nameOfField = fields[i].getName().toLowerCase();
                    Class fieldType = fields[i].getType();
                    for (int j = 0; j < methods.length; j++) {
                        String nameOfMethod = methods[j].getName().toLowerCase();
                        if (nameOfMethod.equals("set" + nameOfField)) {
                            if (nameOfField.equals("object")){
                                //methods[j].invoke(obj, toObject(..., fields[i].getClass()));
                            }
                            //methods[j].invoke(obj, transformPrimitiveType(fields[i], ... ));
                        }
                    }
                }
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
