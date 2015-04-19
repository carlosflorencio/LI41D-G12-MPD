package isel.mpd.jsonzai.factory.types;

import isel.mpd.jsonzai.factory.TypeCreatorInterface;

public class FloatTypeCreator implements TypeCreatorInterface<String, Float> {

    @Override
    public Float apply(String s) {
        return Float.parseFloat(s);
    }
}
