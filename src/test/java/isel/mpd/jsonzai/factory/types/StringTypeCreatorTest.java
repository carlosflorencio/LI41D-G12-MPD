package isel.mpd.jsonzai.factory.types;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class StringTypeCreatorTest {

    private static StringTypeCreator creator;

    @BeforeClass
    public static void setUp() {
        creator = new StringTypeCreator();
    }

    @Test
    public void StringCreatorMethodApplyTest() {
        assertThat("Ola", is(equalTo(creator.apply("\"Ola\""))));
        assertThat("2012-12-12", is(equalTo(creator.apply("\"2012-12-12\""))));
    }

}