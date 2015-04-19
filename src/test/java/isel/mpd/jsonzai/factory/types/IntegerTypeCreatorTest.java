package isel.mpd.jsonzai.factory.types;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Nuno on 09/04/2015.
 */
public class IntegerTypeCreatorTest {

    private static IntegerTypeCreator creator;

    @BeforeClass
    public static void setUp(){
        creator = new IntegerTypeCreator();
    }

    @Test
    public void IntegerTypeCreatorMethodMatchTest(){

        assertThat(false, is(equalTo(creator.test("1.1"))));
        assertThat(false, is(equalTo(creator.test("1,1"))));
        assertThat(false, is(equalTo(creator.test("2015-12-12"))));

        assertThat(false, is(equalTo(creator.test(Long.MAX_VALUE+""))));
        assertThat(true, is(equalTo(creator.test("1897685"))));
    }

    @Test
    public void IntegerTypeCreatorMethodApplyTest(){
        assertThat(1897685, is(equalTo(creator.apply("1897685"))));

    }

}