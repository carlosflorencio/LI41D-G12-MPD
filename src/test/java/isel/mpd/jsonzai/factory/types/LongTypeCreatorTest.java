package isel.mpd.jsonzai.factory.types;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class LongTypeCreatorTest {

    private static LongTypeCreator creator;

    @BeforeClass
    public static void setUp() {
        creator = new LongTypeCreator();
    }

    @Test
    public void LongTypeCreatorMethodApplyTest() {
        assertThat(1897685L, is(equalTo(creator.apply("1897685"))));
    }
}