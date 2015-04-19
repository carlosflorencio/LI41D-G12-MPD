package isel.mpd.jsonzai;

import isel.mpd.jsonzai.entities.GithubRepo;
import isel.mpd.jsonzai.entities.GithubUser;
import isel.mpd.jsonzai.helperTests.SimpleJsonEntity;
import isel.mpd.jsonzai.utils.JsonUtils;
import isel.mpd.weather.data.stringsuppliers.SimpleStringSupplierFromStream;
import jdk.nashorn.internal.parser.JSONParser;
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
import static org.junit.Assert.assertTrue;

public class JsonParserTest {

    private static String userJson = null;
    private static String repoJson = null;
    private static String simpleJsonPrimitiveTypes = null;

    @BeforeClass
    public static void setUp() throws Exception {
        InputStream userStream = new FileInputStream(new File("src/test/resources/user.json"));
        InputStream repoStream = new FileInputStream(new File("src/test/resources/repos.json"));
        InputStream simpleJsonStream = new FileInputStream(new File("src/test/resources/simpleJson.json"));

        userJson = new SimpleStringSupplierFromStream(() -> userStream).get();
        repoJson = new SimpleStringSupplierFromStream(() -> repoStream).get();
        simpleJsonPrimitiveTypes = new SimpleStringSupplierFromStream(() -> simpleJsonStream).get();
    }

    @Test
    public void testToObjectWithGivenUserExample() throws Exception {
        JsonParser<GithubUser> parser = new JsonParser<>();

        String json = JsonUtils.clean(userJson);
        GithubUser user = parser.<GithubUser>toObject(json, GithubUser.class);

        assertNotNull(user);
        assertEquals(user.login, "achiu");
        assertEquals(user.email, "achiu@github.com");
        assertEquals(user.location, "San Francisco, CA");
        assertEquals(user.id, 24772);
    }

    @Test
    public void testToListWithGivenReposExample() throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException {
        JsonParser<GithubRepo> parser = new JsonParser<>();

        String json = JsonUtils.clean(repoJson); //sloooooowwwwww because the json is huge
        List<GithubRepo> repos = parser.<GithubRepo>toList(json, GithubRepo.class);

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
    public void testSimpleJsonWithWrapperPrimitiveTypes() throws Exception {
        JsonParser<SimpleJsonEntity> parser = new JsonParser<>();

        String json = JsonUtils.clean(simpleJsonPrimitiveTypes);
        SimpleJsonEntity obj = parser.<SimpleJsonEntity>toObject(json, SimpleJsonEntity.class);

        assertNotNull(obj);
        assertEquals("vString with \"inside quotes\", test", obj.kString);
        assertEquals(2125, obj.kInt);
        assertTrue(123145.523 == obj.kDouble);
        assertTrue(215.31 == obj.kFloat);
        assertEquals('C', obj.kChar);
        assertTrue(12345678910L == obj.kLong);
        assertTrue(15 == obj.kInteger);
        assertTrue(234.21 == obj.kDoubleWrapper);
        assertTrue(2145.312 == obj.kFloatWrapper);
        assertTrue('D' == obj.kCharWrapper);
        assertTrue(34643453123L == obj.kLongWrapper);
    }

    @Test
    public void testToObjectArray() throws Exception {
//        String src = "\"list\":[1,2,3,4,5,6]";
//
//        JsonParser<ArrayTest> parser = new JsonParser<>();
//
//        ArrayTest arr = parser.toObject(src, ArrayTest.class);
    }
}