package isel.mpd.jsonzai.factory.types;

import isel.mpd.jsonzai.factory.TypeCreatorInterface;

/**
 * Created by Nuno on 14/04/2015.
 */
public class DoubleTypeCreator implements TypeCreatorInterface<String, Double> {

    @Override
    public Double apply(String s) {
        return Double.parseDouble(s);
    }
}
