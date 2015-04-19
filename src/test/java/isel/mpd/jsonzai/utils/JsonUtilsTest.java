package isel.mpd.jsonzai.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class JsonUtilsTest {

    private String json = "{\n" +
            "  \"type\": \"User\",\n" +
            "  \"site_admin\": true,\n" +
            "  \"name\": \"Arthur Chiu\",\n" +
            "  \"company\": \"GitHub\",\n" +
            "  \"blog\": \"\",\n" +
            "  \"location\": \"San Francisco, CA\",\n" +
            "  \"email\": \"achiu@github.com\",\n" +
            "  \"hireable\": false,\n" +
            "  \"bio\": null,\n" +
            "  \"public_repos\": 51,\n" +
            "  \"following\": 38,\n" +
            "  \"created_at\": \"2008-09-16T03:24:44Z\"\n" +
            "}";

    private String jsonObjects = "{\n" +
            "    \"id\": 363183,\n" +
            "    \"name\": \"achiu.github.com\",\n" +
            "    \"full_name\": \"achiu/achiu.github.com\",\n" +
            "    \"owner\": {\n" +
            "        \"login\": \"achiu\",\n" +
            "        \"site_admin\": true,\n" +
            "        \"object\": {\n" +
            "            \"test\": \"oi\"\n" +
            "        }\n" +
            "    },\n" +
            "    \"private\": false\n" +
            "}";

    @Test
    public void testClean() throws Exception {
       String clean = JsonUtils.clean(json);

        assertFalse(clean.contains("\n"));
        assertEquals(countSpaces(clean), 3); //only 3 space in json values
    }

    private int countSpaces(String clean) {
        int count = 0;
        for (int i = 0; i < clean.length(); i++) {
            if(clean.charAt(i) == ' ')
                count++;
        }

        return count;
    }

    @Test
    public void testGetKey() throws Exception {
        String clean = JsonUtils.clean(json);

        assertEquals("type", JsonUtils.getKey("\"type\":\"User\""));
        assertEquals("blog", JsonUtils.getKey("\"blog\":\"\""));
        assertEquals("following", JsonUtils.getKey("\"following\":38"));
        assertEquals("bio", JsonUtils.getKey("\"bio\":null"));
    }

    @Test
    public void testGetValue() throws Exception {
        String clean = JsonUtils.clean(json);

        assertEquals("\"User\"", JsonUtils.getValue("\"type\":\"User\""));
        assertEquals("\"\"", JsonUtils.getValue("\"blog\":\"\""));
        assertEquals("38", JsonUtils.getValue("\"following\":38"));
        assertEquals("null", JsonUtils.getValue("\"bio\":null"));
        assertEquals("\"test\"", JsonUtils.getValue("\"t\":\"test\","));
    }

    @Test
    public void testGetObject() throws Exception {
        String json = JsonUtils.clean(jsonObjects);
        String should = "{\"login\":\"achiu\",\"site_admin\":true,\"object\":{\"test\":\"oi\"}}";
        int index = JsonUtils.getBeginIndexOfValue(json, "owner");

        assertEquals(should, JsonUtils.getObject(json, index, '{', '}'));
    }

    @Test
    public void testGetBeginIndexOfValue() throws Exception {
        String json = "{\"id\":12}";

        assertEquals(6, JsonUtils.getBeginIndexOfValue(json, "id"));
        assertEquals('1', json.charAt(6));
    }

    @Test
    public void testGetValueFromIndexString() throws Exception {
        String json = "{\"kString\":\"vString with \\\"inside quotes\\\", test\",\"num\":2}";

        assertEquals("\"vString with \\\"inside quotes\\\", test\"", JsonUtils.getValue(json, 11));
        assertEquals("2", JsonUtils.getValue(json, json.length() - 2));
    }

    @Test
    public void testGetValueFromIndexStringLastValue() throws Exception {
        String json = "{\"num\":2,\"kString\":\"vString with \\\"inside quotes\\\", test\"}";

        assertEquals("\"vString with \\\"inside quotes\\\", test\"", JsonUtils.getValue(json, 19));
        assertEquals("2", JsonUtils.getValue(json, 7));
    }
}