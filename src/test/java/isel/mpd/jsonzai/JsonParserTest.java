package isel.mpd.jsonzai;

import isel.mpd.jsonzai.entities.GithubRepo;
import isel.mpd.jsonzai.entities.GithubUser;
import isel.mpd.jsonzai.helperTests.JsonObjectsEntity;
import isel.mpd.jsonzai.helperTests.SimpleJsonEntity;
import isel.mpd.jsonzai.helperTests.UserEntity;
import isel.mpd.jsonzai.utils.JsonUtils;
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
import static org.junit.Assert.assertTrue;

public class JsonParserTest {

    private static String userJson = null;
    private static String repoJson = null;
    private static String simpleJsonPrimitiveTypes = null;
    private static String objectsJson = null;

    @BeforeClass
    public static void setUp() throws Exception {
        InputStream userStream = new FileInputStream(new File("src/test/resources/user.json"));
        InputStream repoStream = new FileInputStream(new File("src/test/resources/repos.json"));
        InputStream simpleJsonStream = new FileInputStream(new File("src/test/resources/simpleJson.json"));
        InputStream ObjectsJsonStream = new FileInputStream(new File("src/test/resources/jsonWithObjects.json"));

        userJson = new SimpleStringSupplierFromStream(() -> userStream).get();
        repoJson = new SimpleStringSupplierFromStream(() -> repoStream).get();
        simpleJsonPrimitiveTypes = new SimpleStringSupplierFromStream(() -> simpleJsonStream).get();
        objectsJson = new SimpleStringSupplierFromStream(() -> ObjectsJsonStream).get();
    }

    @Test
    public void testToObjectWithGivenUserExample() throws Exception {
        JsonParser parser = new JsonParser();

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
        JsonParser parser = new JsonParser();

        //we do not clean json because this json is huge and this process is slow, instead we have a minified json file
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
    public void testSimpleJsonWithWrapperPrimitiveTypes() throws Exception {
        JsonParser parser = new JsonParser();

        String json = JsonUtils.clean(simpleJsonPrimitiveTypes);
        SimpleJsonEntity obj = parser.<SimpleJsonEntity>toObject(json, SimpleJsonEntity.class);

        assertNotNull(obj);
        assertEquals("vString with \\\"inside quotes\\\", test", obj.kString);
        assertEquals(2125, obj.kInt);
        assertTrue(123145.523 == obj.kDouble);
        assertTrue(215.31f == obj.kFloat);
        assertEquals('C', obj.kChar);
        assertTrue(12345678910L == obj.kLong);
        assertTrue(15 == obj.kInteger);
        assertTrue(234.21 == obj.kDoubleWrapper);
        assertTrue(2145.312f == obj.kFloatWrapper);
        assertTrue('D' == obj.kCharWrapper);
        assertTrue(34643453123L == obj.kLongWrapper);
    }

    @Test
    public void testJsonWithObjectsArraysAndListsOfObjects() throws Exception {
        JsonParser parser = new JsonParser();

        String json = JsonUtils.clean(objectsJson);
        JsonObjectsEntity obj = parser.<JsonObjectsEntity>toObject(json, JsonObjectsEntity.class);

        assertNotNull(obj);
        assertEquals(2, obj.users.size());
        assertUserEntity("Carlos", 21, obj.users.get(0));
        assertUserEntity("Nuno", 65, obj.users.get(1));

        assertEquals(1, obj.oneUser.size());
        assertUserEntity("Paulo", 99, obj.oneUser.get(0));

        assertUserEntity("Ricardo", 150, obj.single);

        assertEquals("Not Gays", obj.status);
    }



    private void assertUserEntity(String name, int age, UserEntity should) {
        assertEquals(name, should.name);
        assertEquals(age, should.age);
    }
}