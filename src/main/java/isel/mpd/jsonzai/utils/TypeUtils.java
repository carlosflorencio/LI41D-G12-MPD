package isel.mpd.jsonzai.utils;

public class TypeUtils {

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

    public static boolean isArray(Class<?> type) {
        //return type.isAssignableFrom(Array.class);
        return type.getName().charAt(0) == '[';
    }

    public static boolean isString(Class<?> type) {
        return type.isAssignableFrom(String.class);
    }
}
