package isel.mpd.jsonzai.types;

import isel.mpd.jsonzai.factory.TypeCreator;

/**
 * Created by Nuno on 09/04/2015.
 */
public class ArrayTypeCreator extends TypeCreator<String,Object> {

    @Override
    public boolean match(String value) {
        return value.startsWith("[") && value.endsWith("]");
    }

    @Override
    public Object apply(String s) {
        return null;    //TODO: method
        //return Array.newInstance(int.class, 4);
    }

}
