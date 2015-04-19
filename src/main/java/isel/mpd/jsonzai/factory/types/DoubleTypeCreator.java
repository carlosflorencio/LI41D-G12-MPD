package isel.mpd.jsonzai.factory.types;

import isel.mpd.jsonzai.factory.TypeCreatorInterface;

public class DoubleTypeCreator implements TypeCreatorInterface<String, Double> {

    @Override
    public Double apply(String s) {
        return Double.parseDouble(s);
    }
}
