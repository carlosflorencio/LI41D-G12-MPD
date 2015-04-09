package isel.mpd.jsonzai.factory;

import java.util.function.Function;

public interface TypeCreatorInterface<T, R> extends Function<T, R> {
    /**
     * Match the value with the object
     * @param value
     * @return
     */
    public boolean match(T value);
}
