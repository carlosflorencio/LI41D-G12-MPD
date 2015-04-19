package isel.mpd.jsonzai.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtils {


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
     * Get the value of a json by the index of the begin of the value
     * @param json
     * @param initialIndex
     * @return
     */
    public static String getValue(String json, int initialIndex) {
        if(json.charAt(initialIndex) == '\"') { //string
            return json.substring(initialIndex, json.indexOf("\",", initialIndex) + 1);
        }

        return json.substring(initialIndex, json.indexOf(",", initialIndex));
    }

    /**
     *
     * @param json
     * @param beginIndex
     * @param initialJsonSpec
     * @param finalJsonSpec
     * @return
     */
    public static String getObject(String json, int beginIndex, char initialJsonSpec, char finalJsonSpec) {
        int i, numberOfBrackets;
        for (i = beginIndex+1, numberOfBrackets = 1; numberOfBrackets > 0; i++) {
            char c = json.charAt(i);
            if(c == initialJsonSpec){
                numberOfBrackets++;
            }
            else if(c == finalJsonSpec){
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
        int idx = json.toLowerCase().indexOf(key);

        return  idx < 0 ? -1 : idx + key.length() + 2; //quotes counts too
    }

    /**
     * Minify Json, removes all whitespaces
     *
     * FROM: https://github.com/getify/JSON.minify
     * @param json
     * @return
     */
    public static String clean(String jsonString) {
        String tokenizer = "\"|(/\\*)|(\\*/)|(//)|\\n|\\r";
        String magic = "(\\\\)*$";
        Boolean in_string = false;
        Boolean in_multiline_comment = false;
        Boolean in_singleline_comment = false;
        String tmp = "";
        String tmp2 = "";
        List<String> new_str = new ArrayList<String>();
        Integer from = 0;
        String lc = "";
        String rc = "";

        Pattern pattern = Pattern.compile(tokenizer);
        Matcher matcher = pattern.matcher(jsonString);

        Pattern magicPattern = Pattern.compile(magic);
        Matcher magicMatcher = null;
        Boolean foundMagic = false;

        if (!matcher.find())
            return jsonString;
        else
            matcher.reset();

        while (matcher.find()) {
            lc = jsonString.substring(0, matcher.start());
            rc = jsonString.substring(matcher.end(), jsonString.length());
            tmp = jsonString.substring(matcher.start(), matcher.end());

            if (!in_multiline_comment && !in_singleline_comment) {
                tmp2 = lc.substring(from);
                if (!in_string)
                    tmp2 = tmp2.replaceAll("(\\n|\\r|\\s)*", "");

                new_str.add(tmp2);
            }
            from = matcher.end();

            if (tmp.charAt(0) == '\"' && !in_multiline_comment && !in_singleline_comment) {
                magicMatcher = magicPattern.matcher(lc);
                foundMagic = magicMatcher.find();
                if (!in_string || !foundMagic || (magicMatcher.end() - magicMatcher.start()) % 2 == 0) {
                    in_string = !in_string;
                }
                from--;
                rc = jsonString.substring(from);
            }
            else
            if (tmp.startsWith("/*") && !in_string && !in_multiline_comment && !in_singleline_comment) {
                in_multiline_comment = true;
            }
            else
            if (tmp.startsWith("*/") && !in_string && in_multiline_comment && !in_singleline_comment) {
                in_multiline_comment = false;
            }
            else
            if (tmp.startsWith("//") && !in_string && !in_multiline_comment && !in_singleline_comment) {
                in_singleline_comment = true;
            }
            else
            if ((tmp.startsWith("\n") || tmp.startsWith("\r")) && !in_string && !in_multiline_comment && in_singleline_comment) {
                in_singleline_comment = false;
            }
            else
            if (!in_multiline_comment && !in_singleline_comment && !tmp.substring(0, 1).matches("\\n|\\r|\\s")) {
                new_str.add(tmp);
            }
        }

        new_str.add(rc);
        StringBuffer sb = new StringBuffer();
        for (String str : new_str)
            sb.append(str);

        return sb.toString();
    }

}
