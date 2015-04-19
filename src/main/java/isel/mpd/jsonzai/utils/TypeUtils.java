package isel.mpd.jsonzai.utils;

import java.util.List;

public class TypeUtils {

    private TypeUtils() {
    }

    public static boolean isPrimitive(Class<?> type) {
        return type.isAssignableFrom(Integer.class) ||
                type.isAssignableFrom(Double.class) ||
                type.isAssignableFrom(Boolean.class) ||
                type.isAssignableFrom(Float.class) ||
                type.isAssignableFrom(Long.class) ||
                type.isAssignableFrom(Character.class) ||
                type.isPrimitive();
    }

    public static boolean isArray(Class<?> type) {
        return type.isArray();
    }

    public static boolean isString(Class<?> type) {
        return type.isAssignableFrom(String.class);
    }

    public static boolean isList(Class<?> type) {
        return type.isAssignableFrom(List.class);
    }
}
