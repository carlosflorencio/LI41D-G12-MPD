package isel.mpd.jsonzai;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Stream;

public class JsonParser<T> {

    public <T> T toObject(String src, Class<T> dest) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        String result = src
                .replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "")   //WhiteSpaces
                .replaceAll("\":", ":");                               // " between key and :value

        T obj = (T) dest.getConstructors()[0].newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();


        Stream.of(result.split("[{|,]\""))
                .filter(s -> !s.isEmpty())
                .forEach(s -> {
                    int indexOfColon = s.indexOf(":");
                    String key = s.substring(0, indexOfColon);
                    String value = s.substring(indexOfColon + 1);

                    for (int i = 0; i < fields.length; i++) {
                        String nameOfField = fields[i].getName().toLowerCase();
                        Class fieldType = fields[i].getType();
                        if(nameOfField.equals(key)){
                            //TODO: match creator and apply
                            //fields[i].set(obj, ...);
                        }
                    }

                    System.out.printf("%s: %s%n", key, value);
                });

            return obj;
    }


    public <T> List<T> toList(String src, Class<T> dest){

        return null;
    }
}
