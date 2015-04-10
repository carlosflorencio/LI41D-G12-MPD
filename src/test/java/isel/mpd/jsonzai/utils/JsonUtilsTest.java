package isel.mpd.jsonzai.utils;

import org.junit.Test;

import static org.junit.Assert.*;

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

        assertEquals(JsonUtils.getKey("\"type\":\"User\""), "type");
        assertEquals(JsonUtils.getKey("\"blog\":\"\""), "blog");
        assertEquals(JsonUtils.getKey("\"following\":38"), "following");
        assertEquals(JsonUtils.getKey("\"bio\":null"), "bio");
    }

    @Test
    public void testGetValue() throws Exception {
        String clean = JsonUtils.clean(json);

        assertEquals(JsonUtils.getValue("\"type\":\"User\""), "\"User\"");
        assertEquals(JsonUtils.getValue("\"blog\":\"\""), "\"\"");
        assertEquals(JsonUtils.getValue("\"following\":38"), "38");
        assertEquals(JsonUtils.getValue("\"bio\":null"), "null");
    }
}