package isel.mpd.jsonzai.factory.types;

import isel.mpd.jsonzai.factory.TypeCreator;

/**
 * Created by Nuno on 09/04/2015.
 */
public class ObjectTypeCreator extends TypeCreator<String, Object> {
    @Override
    public boolean test(String value) {
        return value.startsWith("{") && value.endsWith("}");
    }

    @Override
    public Object apply(String s) {
        //TODO: method
        return null;
    }
}
