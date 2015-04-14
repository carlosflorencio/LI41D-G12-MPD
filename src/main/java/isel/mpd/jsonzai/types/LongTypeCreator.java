package isel.mpd.jsonzai.types;

import isel.mpd.jsonzai.factory.TypeCreator;

/**
 * Created by Nuno on 14/04/2015.
 */
public class LongTypeCreator extends TypeCreator<String, Long> {
    @Override
    public boolean match(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    @Override
    public Long apply(String s) {
        return Long.parseLong(s);
    }
}
