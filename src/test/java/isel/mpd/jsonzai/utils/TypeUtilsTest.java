package isel.mpd.jsonzai.utils;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class TypeUtilsTest {

    @Test
    public void testIsPrimitive() throws Exception {
        assertTrue(TypeUtils.isPrimitive(int.class));
        assertTrue(TypeUtils.isPrimitive(Integer.class));
        assertTrue(TypeUtils.isPrimitive(double.class));
        assertTrue(TypeUtils.isPrimitive(Double.class));
        assertTrue(TypeUtils.isPrimitive(char.class));
        assertTrue(TypeUtils.isPrimitive(Character.class));
        assertTrue(TypeUtils.isPrimitive(long.class));
        assertTrue(TypeUtils.isPrimitive(Long.class));
        assertTrue(TypeUtils.isPrimitive(float.class));
        assertTrue(TypeUtils.isPrimitive(Float.class));
    }

    @Test
    public void testIsArray() throws Exception {
        int[] array = {1, 2};
        assertTrue(TypeUtils.isArray(array.getClass()));
    }

    @Test
    public void testIsString() throws Exception {
        assertTrue(TypeUtils.isString(String.class));
    }

    @Test
    public void testIsList() throws Exception {
        assertTrue(TypeUtils.isList(List.class));
    }
}