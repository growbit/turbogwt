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

import com.google.gwt.core.client.JavaScriptObject;

import java.util.AbstractSet;
import java.util.Iterator;

/**
 * An implementation of Set underpinned by an array.
 * <p>
 *
 * This is not the fastest implementation of Set at all,
 * but if you have an array and want to wrap in a set mainly for iteration purposes
 * it is a good alternative.
 *
 * @param <T> Type of set values
 *
 * @author Danilo Reinert
 */
public class JsArraySet<T> extends AbstractSet<T> {

    private final JsArray<T> innerArray;

    public JsArraySet() {
        innerArray = JsArray.create();
    }

    public JsArraySet(T[] array) {
        innerArray = JsArray.fromArray(array);
    }

    public <E extends JavaScriptObject> JsArraySet(com.google.gwt.core.client.JsArray<E> jsArray) {
        this.innerArray = JsArray.cast(jsArray);
    }

    JsArraySet(JavaScriptObject jsArray) {
        this.innerArray = JsArray.cast(jsArray);
    }

    @Override
    public int size() {
        return innerArray.length();
    }

    @Override
    public boolean contains(Object o) {
        return innerArray.indexOf(o) > -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new JsArrayIterator<T>(innerArray);
    }

    @Override
    public Object[] toArray() {
        return innerArray.toArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> E[] toArray(E[] a) {
        if (a != null && a.length >= innerArray.length()) {
            for (int i = 0; i < innerArray.length(); i++) {
                E e = (E) innerArray.get(i);
                a[i] = e;
            }
            return a;
        }
        return (E[]) innerArray.toArray();
    }

    @Override
    public boolean add(T t) {
        if (contains(t)) return false;

        innerArray.push(t);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int i = innerArray.indexOf(o);

        if (i == -1) return false;

        innerArray.splice(i, 1);
        return true;
    }

    @Override
    public void clear() {
        innerArray.setLength(0);
    }
}
