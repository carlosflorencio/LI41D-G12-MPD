package isel.mpd.jsonzai.utils;

import java.util.Stack;

public class JsonUtils {

    /**
     * Remove all whitespaces that are not between quotes
     *
     * @param json
     * @return
     */
    public static String clean(String json) {
        return json.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
    }

    /**
     * Get the key of a json row ex "name": "John Doe" -> name
     *
     * @param row
     * @return
     */
    public static String getKey(String row) {
        int indexOfColon = row.indexOf(":");
        return row.substring(1, indexOfColon - 1);
    }

    /**
     * Get the value of a json row ex "name": "John Doe" -> "John Doe"
     *
     * @param row
     * @return
     */
    public static String getValue(String row) {
        int indexOfColon = row.indexOf(":");
        String last = row.substring(indexOfColon + 1);

        if (last.charAt(last.length() - 1) == ',') //remove trailing ,
            last = last.substring(0, last.length() - 1);

        return last;
    }

    /**
     * Clean and remove quotes from keys
     *
     * @param json
     * @return
     */
    public static String cleanObject(String json) {
        String res = clean(json).replaceAll("\":", ":"); // quote between key and :value
        return res.substring(1, res.length() - 2);       // Remove first and last { }
    }

    /**
     * Get an nested object from a json string
     *
     * @param json
     * @param field
     * @return
     */
    public static String getObject(String json, int beginIndex) {
        int numberOfBrackets = 1;
        int i;
        for (i = beginIndex+1; numberOfBrackets != 0; i++) {
            char c = json.charAt(i);
            if(c == '{'){
                numberOfBrackets++;
            }
            else if(c == '}'){
                numberOfBrackets--;
            }
        }

        return json.substring(beginIndex, i);
    }

    /**
     * Get the begin index of value by key
     * @param json
     * @param key
     * @return
     */
    public static int getBeginIndexOfValue(String json, String key) {
        return json.indexOf(key) + key.length() + 2; //quotes counts too
    }
}
