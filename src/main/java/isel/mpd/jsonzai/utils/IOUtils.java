package isel.mpd.jsonzai.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Nuno on 16/04/2015.
 */
public class IOUtils {

    public static String getResultInString(InputStream inputStream) throws IOException {
        StringBuffer result = new StringBuffer();
        int read = 0;
        while((read = inputStream.read()) != -1){
            result.append((char) read);
        }
        return result.toString();
    }

}
