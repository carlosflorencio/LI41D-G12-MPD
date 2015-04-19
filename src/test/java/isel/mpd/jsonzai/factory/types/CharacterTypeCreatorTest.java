package isel.mpd.jsonzai.factory.types;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nuno on 14/04/2015.
 */
public class CharacterTypeCreatorTest {

    private static String value;
    private static String wrongValue;
    private static CharacterTypeCreator creator;

    @BeforeClass
    public static void setUp(){
        value = "\"o\"";
        wrongValue = "\"Ola\"";

        creator = new CharacterTypeCreator();
    }

    @Test
    public void characterTypeCreatorMethodMatchTest() {
        assertTrue(creator.test(value));
        assertFalse(creator.test(wrongValue));
    }

    @Test
    public void characterTypeCreatorMethodApplyTest() {
        assertEquals(new Character('o'), creator.apply(value));
    }

}