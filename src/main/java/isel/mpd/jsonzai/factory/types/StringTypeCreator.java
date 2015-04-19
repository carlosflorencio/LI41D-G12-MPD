package isel.mpd.jsonzai.factory.types;


import isel.mpd.jsonzai.factory.TypeCreatorInterface;

public class StringTypeCreator implements TypeCreatorInterface<String, String> {

    @Override
    public String apply(String s) {
        return s.substring(1, s.length() - 1);
    }
}
