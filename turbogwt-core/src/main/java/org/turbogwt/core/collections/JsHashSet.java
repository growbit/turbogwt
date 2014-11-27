/*
 * Copyright 2014 Grow Bit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.turbogwt.core.collections;

import com.google.gwt.core.client.JsArrayString;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of Set based on {@link JsHashTable}.
 * <p>
 *
 * This class indexes the objects by resorting to their hashCode method.<br>
 * In order to use it, the values must implement hashCode consistently.
 *
 * @param <T> Type of set values
 *
 * @author Danilo Reinert
 */
public class JsHashSet<T> extends AbstractSet<T> {

    private final JsHashTable<T> hashTable = JsHashTable.create();
    private int size;

    public JsHashSet() {
    }

    public JsHashSet(Iterable<T> iterable) {
        for (T t : iterable) {
            add(t);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        checkNotNull(o);
        return hashTable.contains((T) o);
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    @Override
    public Object[] toArray() {
        return hashTable.values().toArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> E[] toArray(E[] a) {
        JsArray<T> jsArray = hashTable.values();
        if (a != null && a.length >= jsArray.length()) {
            for (int i = 0; i < jsArray.length(); i++) {
                E e = (E) jsArray.get(i);
                a[i] = e;
            }
            return a;
        }
        return (E[]) jsArray.toArray();
    }

    @Override
    public boolean add(T t) {
        checkNotNull(t);

        if (contains(t)) {
            return false;
        }

        ++size;
        hashTable.put(t);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        checkNotNull(o);
        --size;
        return hashTable.remove((T) o);
    }

    @Override
    public void clear() {
        hashTable.clear();
    }

    private void checkNotNull(Object o) {
        if (o == null) throw new NullPointerException("This Set does not support null values");
    }

    private class Itr implements Iterator<T> {

        private JsArrayString keys = hashTable.hashCodes();
        private int cursor;       // index of next element to return
        private T lastRet;
        private Iterator<T> bucketItr = new JsArrayIterator<T>(hashTable.get(keys.get(cursor)));

        public boolean hasNext() {
            return cursor != keys.length() && bucketItr.hasNext();
        }

        @Override
        public T next() {
            int i = cursor;

            if (i >= keys.length())
                throw new NoSuchElementException();

            if (!bucketItr.hasNext()) {
                bucketItr = new JsArrayIterator<T>(hashTable.get(keys.get(cursor)));
                cursor = i + 1;
            }

            lastRet = bucketItr.next();
            return lastRet;
        }

        @Override
        public void remove() {
            if (lastRet == null)
                throw new IllegalStateException();

            bucketItr.remove();
            JsHashSet.this.remove(lastRet);
            lastRet = null;
        }
    }
}
