package isel.mpd.jsonzai.factory;

import isel.mpd.jsonzai.factory.exceptions.TypeCreatorNotFound;
import isel.mpd.jsonzai.factory.types.*;
import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.Assert.*;

public class TypeFactoryJsonTest {

    @Test
    public void testGetCreatorForIntTypes() throws Exception {
        TypeFactoryJson<Class<?>> factory = new TypeFactoryJson<>();

        assertTrue(factory.getCreator(int.class) instanceof IntegerTypeCreator);
        assertTrue(factory.getCreator(Integer.class) instanceof IntegerTypeCreator);
    }

    @Test
    public void testGetCreatorBooleanTypes() throws Exception {
        TypeFactoryJson<Class<?>> factory = new TypeFactoryJson<>();

        assertTrue(factory.getCreator(boolean.class) instanceof BooleanTypeCreator);
        assertTrue(factory.getCreator(Boolean.class) instanceof BooleanTypeCreator);
    }

    @Test
    public void testGetCreatorFloatTypes() throws Exception {
        TypeFactoryJson<Class<?>> factory = new TypeFactoryJson<>();

        assertTrue(factory.getCreator(float.class) instanceof FloatTypeCreator);
        assertTrue(factory.getCreator(Float.class) instanceof FloatTypeCreator);
    }

    @Test
    public void testGetCreatorDoubleTypes() throws Exception {
        TypeFactoryJson<Class<?>> factory = new TypeFactoryJson<>();

        assertTrue(factory.getCreator(double.class) instanceof DoubleTypeCreator);
        assertTrue(factory.getCreator(Double.class) instanceof DoubleTypeCreator);
    }

    @Test
    public void testGetCreatorStringTypes() throws Exception {
        TypeFactoryJson<Class<?>> factory = new TypeFactoryJson<>();

        assertTrue(factory.getCreator(String.class) instanceof StringTypeCreator);
    }

    @Test
    public void testGetCreatorLongTypes() throws Exception {
        TypeFactoryJson<Class<?>> factory = new TypeFactoryJson<>();

        assertTrue(factory.getCreator(long.class) instanceof LongTypeCreator);
        assertTrue(factory.getCreator(Long.class) instanceof LongTypeCreator);
    }

    @Test
    public void testGetCreatorCharacterTypes() throws Exception {
        TypeFactoryJson<Class<?>> factory = new TypeFactoryJson<>();

        assertTrue(factory.getCreator(char.class) instanceof CharacterTypeCreator);
        assertTrue(factory.getCreator(Character.class) instanceof CharacterTypeCreator);
    }


    @Test(expected=TypeCreatorNotFound.class)
    public void testGetCreatorNotFound() throws TypeCreatorNotFound {
        TypeFactoryJson<Class<?>> factory = new TypeFactoryJson<>();

        factory.getCreator(Array.class);
    }
}