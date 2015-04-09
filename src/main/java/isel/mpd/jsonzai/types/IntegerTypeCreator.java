package isel.mpd.jsonzai.types;

import isel.mpd.jsonzai.factory.TypeCreator;

/**
 * Created by Nuno on 09/04/2015.
 */
public class IntegerTypeCreator extends TypeCreator<String, Integer> {

    @Override
    public boolean match(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    @Override
    public Integer apply(String s) {
        return Integer.parseInt(s);
    }
}
