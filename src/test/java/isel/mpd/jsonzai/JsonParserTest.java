package isel.mpd.jsonzai;

import isel.mpd.jsonzai.entities.GithubRepo;
import isel.mpd.jsonzai.entities.GithubUser;
import isel.mpd.jsonzai.utils.IOUtils;
import isel.mpd.weather.data.HttpUrlStreamSupplier;
import isel.mpd.weather.data.stringsuppliers.SimpleStringSupplierFromStream;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JsonParserTest {

    private static String userJson = null;
    private static String repoJson = null;

    @BeforeClass
    public static void setUp() throws Exception {
        File userFile = new File("src/test/resources/user.json");
        File repoFile = new File("src/test/resources/repos.json");
        InputStream userStream = new FileInputStream(userFile);
        InputStream repoStream = new FileInputStream(repoFile);

        userJson = new SimpleStringSupplierFromStream(() -> userStream).get();
        repoJson = new SimpleStringSupplierFromStream(() -> repoStream).get();
    }

    @Test
    public void testToObject() throws Exception {
        JsonParser<GithubUser> parser = new JsonParser<>();

        GithubUser user = parser.toObject(userJson, GithubUser.class);

        assertNotNull(user);
        assertEquals(user.login, "achiu");
        assertEquals(user.email, "achiu@github.com");
        assertEquals(user.location, "San Francisco, CA");
        assertEquals(user.id, 24772);
    }

    @Test
    public void testToList() throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException {
        JsonParser<GithubRepo> parser = new JsonParser<>();

        List<GithubRepo> repos = parser.<GithubRepo>toList(repoJson, GithubRepo.class);

        assertEquals(30, repos.size());
    }
}