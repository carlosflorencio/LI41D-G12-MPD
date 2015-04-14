package isel.mpd.jsonzai.types;

import isel.mpd.jsonzai.factory.TypeCreator;

/**
 * Created by Nuno on 14/04/2015.
 */
public class DoubleTypeCreator extends TypeCreator<String, Double> {
    @Override
    public boolean match(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    @Override
    public Double apply(String s) {
        return Double.parseDouble(s);
    }
}
