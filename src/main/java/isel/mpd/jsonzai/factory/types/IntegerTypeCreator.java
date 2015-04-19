package isel.mpd.jsonzai.factory.types;


import isel.mpd.jsonzai.factory.TypeCreatorInterface;

public class IntegerTypeCreator implements TypeCreatorInterface<String, Integer> {


    @Override
    public Integer apply(String s) {
        return Integer.parseInt(s);
    }
}
