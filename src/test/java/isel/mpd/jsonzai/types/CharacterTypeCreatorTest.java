package isel.mpd.jsonzai.types;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nuno on 14/04/2015.
 */
public class CharacterTypeCreatorTest {

    @Test
    public void matchCharacterTest() {
        String value = "\"o\"";
        String wrongValue = "\"Ola\"";

        CharacterTypeCreator ctc = new CharacterTypeCreator();

        assertTrue(ctc.match(value));
        assertEquals(new Character('o'), ctc.apply(value));
        assertFalse(ctc.match(wrongValue));
    }

}