package isel.mpd.jsonzai.factory.types;


import isel.mpd.jsonzai.factory.TypeCreatorInterface;

public class CharacterTypeCreator implements TypeCreatorInterface<String, Character> {

    @Override
    public Character apply(String s) {
        return s.charAt(1);
    }
}
