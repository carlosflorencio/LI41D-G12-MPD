package isel.mpd.jsonzai.factory.types;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class BooleanTypeCreatorTest {

    private static BooleanTypeCreator creator;

    @BeforeClass
    public static void setUp() {
        creator = new BooleanTypeCreator();
    }

    @Test
    public void booleanCreatorTypeMethodApplyTest() {
        assertThat(Boolean.TRUE, is(equalTo(creator.apply("true"))));
        assertThat(Boolean.FALSE, is(equalTo(creator.apply("false"))));
    }

}