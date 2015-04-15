package isel.mpd.jsonzai.factory.types;

import isel.mpd.jsonzai.factory.TypeCreator;

/**
 * Created by Nuno on 09/04/2015.
 */
public class BooleanTypeCreator extends TypeCreator<String, Boolean> {

    @Override
    public boolean test(String value) {
        // TODO: method
        return false;
    }

    @Override
    public Boolean apply(String s) {
        return Boolean.valueOf(s);
    }
}
