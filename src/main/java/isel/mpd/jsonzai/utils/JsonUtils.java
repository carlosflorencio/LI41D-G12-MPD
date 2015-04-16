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
        int indexOfColon = row.indexOf(":");
        return row.substring(0, indexOfColon);
    }

    /**
     * Get the value of a json row ex "name": "John Doe" -> "John Doe"
     * @param row
     * @return
     */
    public static String getValue(String row) {
        int indexOfColon = row.indexOf(":");
        return row.substring(indexOfColon + 1);
    }

    public static String cleanObject(String json) {
        String res = clean(json).replaceAll("\":", ":"); // quote between key and :value
        return  res.substring(1, res.length()-2);       // Remove first and last { }
    }
}
