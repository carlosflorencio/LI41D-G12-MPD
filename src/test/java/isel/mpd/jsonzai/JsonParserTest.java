package isel.mpd.jsonzai;

import isel.mpd.jsonzai.entities.ArrayTest;
import isel.mpd.jsonzai.entities.GithubRepo;
import isel.mpd.jsonzai.entities.GithubUser;
import isel.mpd.weather.data.stringsuppliers.SimpleStringSupplierFromStream;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

        GithubRepo repo = repos.get(0);

        assertEquals(363183, repo.id);
        assertEquals("achiu.github.com", repo.name);
        assertEquals("achiu/achiu.github.com", repo.full_name);

        assertEquals("achiu", repo.owner.login);
        assertEquals(24772, repo.owner.id);
        assertEquals(null, repo.owner.email);
        assertEquals(null, repo.owner.location);
    }

    @Test
    public void testToObjectArray() throws Exception {
        String src = "\"list\":[1,2,3,4,5,6]";

        JsonParser<ArrayTest> parser = new JsonParser<>();

        ArrayTest arr = parser.toObject(src, ArrayTest.class);
    }
}