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
        return  res.substring(1, res.length() - 2);       // Remove first and last { }
    }

    public static boolean isPrimitive(Class<?> type) {
        return type.isAssignableFrom(Integer.class) ||
                type.isAssignableFrom(int.class) ||
                type.isAssignableFrom(Double.class) ||
                type.isAssignableFrom(double.class) ||
                type.isAssignableFrom(Boolean.class) ||
                type.isAssignableFrom(boolean.class) ||
                type.isAssignableFrom(Float.class) ||
                type.isAssignableFrom(float.class) ||
                type.isAssignableFrom(Long.class) ||
                type.isAssignableFrom(long.class) ||
                type.isAssignableFrom(Character.class) ||
                type.isAssignableFrom(char.class);
    }

    public static String getObject(String result, String nameOfField) {
        int initialIndex = result.indexOf(nameOfField)+nameOfField.length()+2;
        int numberOfBrackets = 1;
        int i = 0;
        for (i = initialIndex; numberOfBrackets != 0; i++) {
            char c = result.charAt(i);
            if(c == '{'){
                numberOfBrackets++;
            }
            else if(c == '}'){
                numberOfBrackets--;
            }
        }
        return result.substring(initialIndex, i);
    }
}
