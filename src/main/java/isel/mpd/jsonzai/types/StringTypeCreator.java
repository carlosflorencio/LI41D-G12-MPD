package isel.mpd.jsonzai.types;

import isel.mpd.jsonzai.factory.TypeCreator;

public class StringTypeCreator extends TypeCreator<String, String> {

    @Override
    public boolean match(String value) {
        return value.startsWith("\"") && value.endsWith("\"");
    }

    @Override
    public String apply(String s) {
        return s.substring(1, s.length()-1);
    }
}
