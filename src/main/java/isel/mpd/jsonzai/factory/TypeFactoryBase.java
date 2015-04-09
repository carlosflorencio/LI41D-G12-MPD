package isel.mpd.jsonzai.factory;

public abstract class TypeFactoryBase<T> {

    public abstract TypeCreatorInterface getCreator(T value);

}
