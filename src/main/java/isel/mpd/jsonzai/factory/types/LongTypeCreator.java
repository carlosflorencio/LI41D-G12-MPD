package isel.mpd.jsonzai.factory.types;


import isel.mpd.jsonzai.factory.TypeCreatorInterface;

public class LongTypeCreator implements TypeCreatorInterface<String, Long> {

    @Override
    public Long apply(String s) {
        return Long.parseLong(s);
    }
}
