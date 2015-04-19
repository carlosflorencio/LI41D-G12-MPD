package isel.mpd.jsonzai.factory.types;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Nuno on 09/04/2015.
 */
public class StringTypeCreatorTest {

    private static StringTypeCreator creator;

    @BeforeClass
    public static void setUp(){
        creator = new StringTypeCreator();
    }

    @Test
    public void StringCreatorMethodApplyTest(){
        assertThat("Ola", is(equalTo(creator.apply("\"Ola\""))));
        assertThat("2012-12-12", is(equalTo(creator.apply("\"2012-12-12\""))));
    }

    @Test
    public void StringCreatorMethodMatchTest(){
        assertThat(true, is(equalTo(creator.test("\"Ola\""))));

        assertThat(false, is(equalTo(creator.test("12"))));
        assertThat(false, is(equalTo(creator.test("1.2"))));

        assertThat(false, is(equalTo(creator.test("Ola"))));
        assertThat(false, is(equalTo(creator.test("\"Ola"))));
        assertThat(false, is(equalTo(creator.test("Ola\""))));
    }

}