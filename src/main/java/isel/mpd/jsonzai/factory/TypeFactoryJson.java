package isel.mpd.jsonzai.factory;

import isel.mpd.jsonzai.types.*;

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
            if(types[i].match(value)){
                return types[i];
            }
        }
        throw new NoClassDefFoundError();
    }
}
