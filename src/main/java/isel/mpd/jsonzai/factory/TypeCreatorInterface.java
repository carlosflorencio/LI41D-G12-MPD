package isel.mpd.jsonzai.factory;

import java.util.function.Function;
import java.util.function.Predicate;

public interface TypeCreatorInterface<T, R> extends Predicate<T>, Function<T, R> {
}
