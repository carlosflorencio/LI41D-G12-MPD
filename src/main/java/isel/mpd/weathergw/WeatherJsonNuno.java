package isel.mpd.weathergw;

import isel.mpd.jsonzai.entities.GithubRepo;
import isel.mpd.weather.data.HttpUrlStreamSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.MessageFormat;

/**
 * Created by Nuno on 11/04/2015.
 */
public class WeatherJsonNuno {

    private static final String userUrl = MessageFormat.format("https://api.github.com/users/achiu", "json");

    private static final String repoUrl = MessageFormat.format("https://api.github.com/users/achiu/repos", "json");

    public static void main(String[] args) throws IOException {
        HttpUrlStreamSupplier httpUrlStreamSupplier = new HttpUrlStreamSupplier(repoUrl);

        String result = getResultInString(httpUrlStreamSupplier.get());

        GithubRepo user = new GithubRepo();

        Field[] fields = user.getClass().getDeclaredFields();

        for (Field field : fields) {
            String nameOfField = field.getName().toLowerCase();
            if(isPrimitive(field.getType())){
                int initialIndex = result.indexOf(nameOfField);
                int finalIdx = result.indexOf(",", initialIndex);
                String aux = result.substring(initialIndex-1, finalIdx);

                System.out.println(aux);
            }
            else if(field.getType().isAssignableFrom(String.class)){

            }
            else{
                System.out.println(getObjectFromJson(result, nameOfField));
            }
        }
    }

    private static String getObjectFromJson(String result, String nameOfField) {
        int initialIndex = result.indexOf(nameOfField)+nameOfField.length()+2;
        int numberOfBrackets = 1;
        int i = 0;
        for (i = initialIndex; numberOfBrackets != 0; i++) {
            char c = result.charAt(i);
            if(c == '{'){
                numberOfBrackets++;
            }
            else if(c == '}'){
                numberOfBrackets--;
            }
        }
        return result.substring(initialIndex, i);
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

    private static String getResultInString(InputStream inputStream) throws IOException {
        StringBuffer result = new StringBuffer();
        int read = 0;
        while((read = inputStream.read()) != -1){
            result.append((char) read);
        }
        return result.toString();
    }
}
