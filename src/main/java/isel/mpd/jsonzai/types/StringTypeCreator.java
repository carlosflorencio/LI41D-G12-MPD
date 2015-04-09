package isel.mpd.jsonzai.types;

import isel.mpd.jsonzai.factory.TypeCreator;

public class StringTypeCreator extends TypeCreator<String, String> {
    @Override
    public boolean match(String value) {
        return value.startsWith("\"") && value.endsWith("\"");
    }

    @Override
    public String apply(String s) {
        //remove last "
        String res = s.substring(0, s.length()-1);

        //remove first "
        return res.substring(1);
    }
}
