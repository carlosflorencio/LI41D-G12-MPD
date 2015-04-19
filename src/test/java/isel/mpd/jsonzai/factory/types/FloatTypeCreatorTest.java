package isel.mpd.jsonzai.factory.types;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class FloatTypeCreatorTest {

    private static FloatTypeCreator creator;

    @BeforeClass
    public static void setUp() {
        creator = new FloatTypeCreator();
    }

    @Test
    public void floatCreatorTypeMethodApplyTest() {
        assertThat(2.45f, is(equalTo(creator.apply("2.45"))));
    }
}