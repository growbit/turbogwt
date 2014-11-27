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
import com.google.gwt.core.client.JsArrayBoolean;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator implemented under {@link JsArray}.
 *
 * @param <T> Type of iterators values
 *
 * @author Danilo Reinert
 */
public class JsArrayIterator<T> implements Iterator<T> {

    private final JsArray<T> array;
    private int cursor;       // index of next element to return
    private int lastRet = -1; // index of last element returned; -1 if no such

    public JsArrayIterator(T[] array) {
        this.array = JsArray.fromArray(array);
    }

    public JsArrayIterator(JsArray<T> array) {
        this.array = array;
    }

    public <E extends JavaScriptObject> JsArrayIterator(com.google.gwt.core.client.JsArray<E> array) {
        this((JavaScriptObject) array);
    }

    public JsArrayIterator(JsArrayString array) {
        this((JavaScriptObject) array);
    }

    public JsArrayIterator(JsArrayInteger array) {
        this((JavaScriptObject) array);
    }

    public JsArrayIterator(JsArrayNumber array) {
        this((JavaScriptObject) array);
    }

    public JsArrayIterator(JsArrayBoolean array) {
        this((JavaScriptObject) array);
    }

    private JsArrayIterator(JavaScriptObject array) {
        this.array = JsArray.cast(array);
    }

    public boolean hasNext() {
        return cursor != array.length();
    }

    public T next() {
        int i = cursor;
        if (i >= array.length()) {
            throw new NoSuchElementException();
        }
        cursor = i + 1;
        return array.get(lastRet = i);
    }

    public void remove() {
        if (lastRet < 0) {
            throw new IllegalStateException();
        }
        try {
            array.splice(lastRet);
            cursor = lastRet;
            lastRet = -1;
        } catch (IndexOutOfBoundsException ex) {
            throw new ConcurrentModificationException();
        }
    }
}
