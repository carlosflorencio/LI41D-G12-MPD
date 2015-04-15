package isel.mpd.jsonzai.factory;

import isel.mpd.jsonzai.factory.types.*;

public class TypeFactoryJson extends TypeFactoryBase<String> {

    // ORDER MATTERS!
    private TypeCreatorInterface[] types = {
            new BooleanTypeCreator(),
            new CharacterTypeCreator(),
            new StringTypeCreator(),
            new IntegerTypeCreator(),
            new FloatTypeCreator(),
            new DoubleTypeCreator(),
            new LongTypeCreator()
    };


    @Override
    public TypeCreatorInterface getCreator(String value) {
        for (int i = 0; i < types.length; i++) {
            if(types[i].test(value)){
                return types[i];
            }
        }
        throw new NoClassDefFoundError();
    }
}
