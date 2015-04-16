package isel.mpd.weathergw;

import isel.mpd.jsonzai.entities.GithubRepo;
import isel.mpd.jsonzai.utils.IOUtils;
import isel.mpd.jsonzai.utils.JsonUtils;
import isel.mpd.weather.data.HttpUrlStreamSupplier;

import java.io.IOException;
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

        String result = IOUtils.getResultInString(httpUrlStreamSupplier.get());

        GithubRepo user = new GithubRepo();
        //GithubUser user = new GithubUser();

        Field[] fields = user.getClass().getDeclaredFields();

        for (Field field : fields) {
            String nameOfField = field.getName().toLowerCase();
            if(JsonUtils.isPrimitive(field.getType())){
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
               // System.out.println(getObjectFromJson(result, nameOfField));
            }
        }
    }
}
