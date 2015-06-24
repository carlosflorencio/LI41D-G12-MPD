package isel.mpd.githubgw.model.async;

import java.util.Iterator;

public class PageIterable<T> implements Iterable<T> {

    private final Iterable<T> i;

    public PageIterable(Iterable<T> i) {
        this.i = i;
    }

    @Override
    public Iterator<T> iterator() {
        return new PageIterator();
    }

    private class PageIterator implements Iterator<T> {


        final Iterator<T> iit = i.iterator();


        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public T next() {
            return null;
        }
    }
}
