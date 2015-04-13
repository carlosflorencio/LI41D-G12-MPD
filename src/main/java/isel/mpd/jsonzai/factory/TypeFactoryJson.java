package isel.mpd.jsonzai.factory;

import isel.mpd.jsonzai.types.IntegerTypeCreator;
import isel.mpd.jsonzai.types.StringTypeCreator;

public class TypeFactoryJson extends TypeFactoryBase<String> {

    private TypeCreatorInterface[] types = {
            new StringTypeCreator(),
            new IntegerTypeCreator()
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
