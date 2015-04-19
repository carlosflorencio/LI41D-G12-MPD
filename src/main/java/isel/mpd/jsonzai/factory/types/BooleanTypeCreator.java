package isel.mpd.jsonzai.factory.types;

import isel.mpd.jsonzai.factory.TypeCreatorInterface;

public class BooleanTypeCreator implements TypeCreatorInterface<String, Boolean> {

    @Override
    public Boolean apply(String s) {
        return Boolean.valueOf(s);
    }
}
