package isel.mpd.jsonzai.factory.types;

import isel.mpd.jsonzai.factory.TypeCreator;

/**
 * Created by Nuno on 14/04/2015.
 */
public class CharacterTypeCreator extends TypeCreator<String, Character> {
    @Override
    public boolean test(String value) {
        return value.startsWith("\"") && value.endsWith("\"") && value.length() == 3;
    }

    @Override
    public Character apply(String s) {
        return s.charAt(1);
    }
}
