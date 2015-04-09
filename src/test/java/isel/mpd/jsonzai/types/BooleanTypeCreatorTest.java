package isel.mpd.jsonzai.types;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Nuno on 09/04/2015.
 */
public class BooleanTypeCreatorTest {

    @Test
    public void BooleanCreatorTypeMethodsTest(){
        BooleanTypeCreator btc = new BooleanTypeCreator();

        assertThat(Boolean.TRUE, is(equalTo(btc.apply("true"))));
    }

}