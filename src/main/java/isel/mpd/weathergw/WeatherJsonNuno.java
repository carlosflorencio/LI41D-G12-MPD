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
        //GithubUser user = new GithubUser();

        Field[] fields = user.getClass().getDeclaredFields();

        for (Field field : fields) {
            String nameOfField = field.getName().toLowerCase();
            if(isPrimitive(field.getType())){
                int initialIndex = result.indexOf(nameOfField)+nameOfField.length()+2;
                int finalIdx = result.indexOf(",", initialIndex);
                String aux = result.substring(initialIndex, finalIdx);

                System.out.println(aux);
            }
            else if(field.getType().isAssignableFrom(String.class)){
                int initialIndex = result.indexOf(nameOfField)+nameOfField.length()+2;
                int finalIdx = result.indexOf("\",", initialIndex)+1;
                System.out.println(result.substring(initialIndex, finalIdx));
            }
            else{
                System.out.println(getObjectFromJson(result, nameOfField));
            }
        }
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
