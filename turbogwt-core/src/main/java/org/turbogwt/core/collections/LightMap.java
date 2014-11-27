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
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A Map from String to Object implementation over a Javascript Object.
 *
 * @param <T> The type of the map values
 *
 * @author Danilo Reinert
 */
public class LightMap<T> implements Map<String, T> {

    JsMap<T> innerMap = JsMap.create();

    @Override
    public int size() {
        return innerMap.size();
    }

    @Override
    public boolean isEmpty() {
        return innerMap.size() <= 0;
    }

    @Override
    public boolean containsKey(Object o) {
        checkNotNull(o);
        assert o instanceof String : "Key should be of type String";

        String key = (String) o;
        return innerMap.contains(key);
    }

    @Override
    public boolean containsValue(Object o) {
        checkNotNull(o);

        JsArrayString keys = innerMap.keys();
        for (int i = 0; i < keys.length(); i++) {
            String key = keys.get(i);
            T t = innerMap.get(key);
            if (o.equals(t)) return true;
        }
        return false;
    }

    @Override
    public T get(Object o) {
        checkNotNull(o);
        assert o instanceof String : "Key should be of type String";

        String key = (String) o;
        return innerMap.get(key);
    }

    @Override
    public T put(String s, T t) {
        checkNotNull(s);
        checkNotNull(t);

        T old = innerMap.get(s);
        innerMap.put(s, t);
        return old;
    }

    @Override
    public T remove(Object o) {
        checkNotNull(o);
        assert o instanceof String : "Key should be of type String";

        final String key = (String) o;
        T t = innerMap.get(key);
        innerMap.remove(key);
        return t;
    }

    @Override
    public void putAll(Map<? extends String, ? extends T> map) {
        final Set<? extends String> keySet = map.keySet();
        for (String s : keySet) {
            put(s, map.get(s));
        }
    }

    @Override
    public void clear() {
        innerMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return new KeySet<T>(this);
    }

    @Override
    public Collection<T> values() {
        return new ValueArray<T>(this);
    }

    @Override
    public Set<Entry<String, T>> entrySet() {
        return new EntrySet<T>(this);
    }

    private void checkNotNull(Object o) {
        if (o == null) throw new NullPointerException("This map does not support null values");
    }

    private static class JsEntry<T> implements Entry<String, T> {

        private final LightMap<T> map;
        private final String key;

        private JsEntry(LightMap<T> map, String key) {
            this.map = map;
            this.key = key;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public T getValue() {
            return map.get(key);
        }

        @Override
        public T setValue(T t) {
            return map.put(key, t);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Entry)) {
                return false;
            }

            final Entry<String, T> entry = (Entry<String, T>) o;

            if (!key.equals(entry.getKey())) {
                return false;
            }
            if (!getValue().equals(entry.getValue())) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = getValue().hashCode();
            result = 31 * result + key.hashCode();
            return result;
        }
    }

    private static class KeySet<T> extends JsArraySet<String> {

        private final LightMap<T> map;

        private KeySet(LightMap<T> map) {
            super(map.innerMap.keys());
            this.map = map;
        }

        @Override
        public void clear() {
            super.clear();
            map.clear();
        }

        @Override
        public boolean remove(Object o) {
            return super.remove(o) && map.remove(o) != null;
        }

        @Override
        public boolean add(String s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends String> strings) {
            throw new UnsupportedOperationException();
        }
    }

    private static class ValueArray<T> extends JsArrayList<T> {

        private final LightMap<T> map;

        private ValueArray(LightMap<T> map) {
            super(map.innerMap.values());
            this.map = map;
        }

        @Override
        public void clear() {
            super.clear();
            map.clear();
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean remove(Object o) {
            return super.remove(o) && map.remove(map.innerMap.keyOf((T) o)) != null;
        }

        @Override
        public boolean add(T t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int i, Collection<? extends T> c) {
            throw new UnsupportedOperationException();
        }
    }

    private static class EntrySet<T> extends AbstractSet<Entry<String, T>> {

        private final LightMap<T> map;

        private EntrySet(LightMap<T> map) {
            this.map = map;
        }

        @Override
        public void clear() {
            map.clear();
        }

        @Override
        public int size() {
            return map.size();
        }

        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            Entry<String, T> entry = (Entry<String, T>) o;
            return map.containsKey(entry.getKey());
        }

        @Override
        public Iterator<Entry<String, T>> iterator() {
            return new Itr();
        }

        @Override
        public boolean add(Entry<String, T> tJsEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            Entry<String, T> entry = (Entry<String, T>) o;
            return map.remove(entry.getKey()) != null;
        }

        @Override
        public boolean addAll(Collection<? extends Entry<String, T>> entries) {
            throw new UnsupportedOperationException();
        }

        private class Itr implements Iterator<Entry<String, T>> {

            private JsArrayString keys = map.innerMap.keys();
            private int cursor;       // index of next element to return
            private int lastRet = -1; // index of last element returned; -1 if no such

            public boolean hasNext() {
                return cursor != keys.length();
            }

            @Override
            public Entry<String, T> next() {
                int i = cursor;

                if (i >= keys.length())
                    throw new NoSuchElementException();

                cursor = i + 1;
                return new JsEntry<T>(map, keys.get(lastRet = i));
            }

            @Override
            public void remove() {
                if (lastRet < 0)
                    throw new IllegalStateException();

                map.remove(keys.get(lastRet));
                cursor = lastRet;
                lastRet = -1;
            }
        }
    }
}
