package isel.mpd.jsonzai.utils;

import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.Assert.*;

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
        assertTrue(TypeUtils.isArray(Array.class));
    }

    @Test
    public void testIsString() throws Exception {
        assertTrue(TypeUtils.isString(String.class));
    }
}