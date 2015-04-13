package isel.mpd.weathergw;

import isel.mpd.weather.data.HttpUrlStreamSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.stream.Stream;

/**
 * Created by Nuno on 11/04/2015.
 */
public class WeatherJsonNuno {

    private static final String userUrl = MessageFormat.format("https://api.github.com/users/achiu", "json");

    private static final String repoUrl = MessageFormat.format("https://api.github.com/users/achiu/repos", "json");

    public static void main(String[] args) throws IOException {
        HttpUrlStreamSupplier httpUrlStreamSupplier = new HttpUrlStreamSupplier(userUrl);

        String result = getResultInString(httpUrlStreamSupplier.get())
                //.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "")   //WhiteSpaces
                .replaceAll("\":", ":");                               // " between key and :value

        Stream.of(result.split("[{|,]\""))
                .filter(s -> !s.isEmpty())
                .forEach(s -> {
                    int indexOfColon = s.indexOf(":");

                    if(indexOfColon != -1){
                        String key = s.substring(0, indexOfColon);
                        String value = s.substring(indexOfColon + 1);

                        System.out.printf("%s: %s%n", key, value);
                    }
                });

//        JsonParser parser = new JsonParser();
//        GithubUser user = parser.<GithubUser>toObject(getResultInString(httpUrlStreamSupplier.get()), GithubUser.class);
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
