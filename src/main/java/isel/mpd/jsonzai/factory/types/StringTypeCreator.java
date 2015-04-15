package isel.mpd.jsonzai.factory.types;

import isel.mpd.jsonzai.factory.TypeCreator;

public class StringTypeCreator extends TypeCreator<String, String> {

    @Override
    public boolean test(String value) {
        return value.startsWith("\"") && value.endsWith("\"");
    }

    @Override
    public String apply(String s) {
        return s.substring(1, s.length()-1);
    }
}
