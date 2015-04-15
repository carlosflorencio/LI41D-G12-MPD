package isel.mpd.jsonzai.factory;

public interface TypeFactoryInterface<T> {

    public TypeCreatorInterface getCreator(T value);
}
