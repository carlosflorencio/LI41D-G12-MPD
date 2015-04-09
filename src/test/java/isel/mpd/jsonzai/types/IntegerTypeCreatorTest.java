package isel.mpd.jsonzai.types;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Nuno on 09/04/2015.
 */
public class IntegerTypeCreatorTest {

    @Test
    public void IntegerTypeCreatorMethodsTest(){
        IntegerTypeCreator itc = new IntegerTypeCreator();

        assertThat(false, is(equalTo(itc.match("1.1"))));
        assertThat(false, is(equalTo(itc.match("1,1"))));
        assertThat(false, is(equalTo(itc.match("2015-12-12"))));

        assertThat(true, is(equalTo(itc.match("1897685"))));
    }

}