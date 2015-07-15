package isel.mpd.jsonzai.factory.types;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class DoubleTypeCreatorTest {

    private static DoubleTypeCreator creator;

    @BeforeClass
    public static void setUp() {
        creator = new DoubleTypeCreator();
    }

    @Test
    public void doubleCreatorTypeMethodApplyTest() {
        assertThat(2.45, is(equalTo(creator.apply("2.45"))));
    }
}