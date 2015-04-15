package isel.mpd.jsonzai.factory.types;

import isel.mpd.jsonzai.factory.TypeCreator;

/**
 * Created by Nuno on 14/04/2015.
 */
public class FloatTypeCreator extends TypeCreator<String, Float> {
    @Override
    public boolean test(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    @Override
    public Float apply(String s) {
        return Float.parseFloat(s);
    }
}
