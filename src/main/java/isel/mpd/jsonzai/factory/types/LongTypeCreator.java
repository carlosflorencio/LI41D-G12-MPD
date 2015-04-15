package isel.mpd.jsonzai.factory.types;

import isel.mpd.jsonzai.factory.TypeCreator;

/**
 * Created by Nuno on 14/04/2015.
 */
public class LongTypeCreator extends TypeCreator<String, Long> {
    @Override
    public boolean test(String value) {
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
