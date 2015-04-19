package isel.mpd.jsonzai.factory.types;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class IntegerTypeCreatorTest {

    private static IntegerTypeCreator creator;

    @BeforeClass
    public static void setUp(){
        creator = new IntegerTypeCreator();
    }

    @Test
    public void IntegerTypeCreatorMethodApplyTest(){
        assertThat(1897685, is(equalTo(creator.apply("1897685"))));
    }

}