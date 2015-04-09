package isel.mpd.jsonzai.types;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Nuno on 09/04/2015.
 */
public class StringTypeCreatorTest {

    @Test
    public void StringCreatorMethodsTest(){
        StringTypeCreator srt = new StringTypeCreator();

        assertThat("Ola", is(equalTo(srt.apply("\"Ola\""))));
    }

}