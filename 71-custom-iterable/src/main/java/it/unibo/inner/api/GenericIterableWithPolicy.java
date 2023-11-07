package it.unibo.inner.api;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;

public class GenericIterableWithPolicy<T> implements IterableWithPolicy<T>{
    private final List<T> elements;
    private Predicate<T> filter;

    public GenericIterableWithPolicy(final T[] parameterArray){
        this(parameterArray, new Predicate<T>() {
            public boolean test(T elem){
                return true;
            }
        });
    }

    public GenericIterableWithPolicy(final T[] parameterArray,final Predicate<T> filter){
        elements = List.of(parameterArray);
        this.filter = filter;
    }

    @Override
    public Iterator<T> iterator() {
        return new InnerGenericIterableWithPolicy();
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;
    }

    private class InnerGenericIterableWithPolicy implements Iterator<T> {
        private int currentIndex = 0;
        @Override
        public boolean hasNext() {
            while(currentIndex < elements.size()){
                T elem = elements.get(currentIndex);
                if(filter.test(elem)){
                    return true;
                }
                currentIndex++;
            }
            return false;
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new java.util.NoSuchElementException();
            }
            return elements.get(currentIndex++);
        }
    }
}

