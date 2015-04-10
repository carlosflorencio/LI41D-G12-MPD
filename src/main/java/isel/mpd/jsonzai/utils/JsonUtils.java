package isel.mpd.jsonzai.utils;

public class JsonUtils {

    /**
     * Remove all whitespaces that are not between quotes
     * @param json
     * @return
     */
    public static String clean(String json) {
        return json.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
    }

    /**
     * Get the key of a json row ex "name": "John Doe" -> name
     * @param row
     * @return
     */
    public static String getKey(String row) {
        if(row.startsWith("\"")) //remove first quote to help the split of the streams
            row = row.substring(1, row.length() -1);
        return row.trim().substring(0, row.indexOf(':') - 1);
    }

    /**
     * Get the value of a json row ex "name": "John Doe" -> "John Doe"
     * @param row
     * @return
     */
    public static String getValue(String row) {
        return row.substring(row.indexOf(':') + 1, row.length()).trim();
    }
}
