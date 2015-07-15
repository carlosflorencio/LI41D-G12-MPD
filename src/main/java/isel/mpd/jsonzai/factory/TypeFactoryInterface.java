package isel.mpd.jsonzai.factory;

import isel.mpd.jsonzai.factory.exceptions.TypeCreatorNotFound;

public interface TypeFactoryInterface<T> {

    /**
     * Get the TypeCreator for the given field Type
     *
     * @param field
     * @return
     */
    public TypeCreatorInterface getCreator(T field) throws TypeCreatorNotFound;
}
