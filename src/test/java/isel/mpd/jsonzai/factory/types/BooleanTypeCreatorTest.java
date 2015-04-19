package isel.mpd.jsonzai.factory.types;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nuno on 09/04/2015.
 */
public class BooleanTypeCreatorTest {

    private static BooleanTypeCreator creator;

    @BeforeClass
    public static void setUp(){
        creator = new BooleanTypeCreator();
    }

    @Test
    public void booleanCreatorTypeMethodMatchTest(){
        assertTrue(creator.test("\"true\""));
        assertFalse(creator.test("\"truest\""));
        assertFalse(creator.test("true\""));
    }

    @Test
    public void booleanCreatorTypeMethodApplyTest(){
        assertThat(Boolean.TRUE, is(equalTo(creator.apply("true"))));
        assertThat(Boolean.FALSE, is(equalTo(creator.apply("false"))));
    }

}