package isel.mpd.jsonzai.factory;

import isel.mpd.jsonzai.types.StringTypeCreator;

public class TypeFactoryJson extends TypeFactoryBase<String> {

    private TypeCreatorInterface[] types = {
        new StringTypeCreator(),
    };


    @Override
    public TypeCreatorInterface getCreator(String value) {

        return null;
    }
}
