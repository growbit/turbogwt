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
import com.google.gwt.core.client.JsArrayString;

/**
 * A hash table implemented on a javascript object.
 * The object's properties are used to index the hash codes.
 *
 * @param <T> The type of the values
 *
 * @author Danilo Reinert
 */
public class JsHashTable<T> extends JavaScriptObject {

    protected JsHashTable() {
    }

    public static native <T> JsHashTable<T> create() /*-{
        return {};
    }-*/;

    public final native void clear() /*-{
        for (var key in this) delete this[key];
    }-*/;

    public final boolean contains(T value) {
        JsArray<T> bucket = get(value.hashCode());
        return (bucket != null) && (bucket.indexOf(value) > -1);
    }

    public final native JsArray<T> get(int hashCode) /*-{
        return this[hashCode];
    }-*/;

    public final native JsArrayString hashCodes() /*-{
        return Object.keys(this);
    }-*/;

    public final void put(T value) {
        checkNotNull(value);
        put(value.hashCode(), value);
    }

    public final boolean remove(T value) {
        final int hashCode = value.hashCode();
        JsArray<T> bucket = get(hashCode);
        if (bucket != null) {
            final int i = bucket.indexOf(value);
            if (i > -1) {
                bucket.splice(i);
                if (bucket.length() == 0) {
                    // Save memory by disposing empty buckets
                    deleteBucket(hashCode);
                }
                return true;
            }
        }
        return false;
    }

    public final native JsArray<T> values() /*-{
        var v = [];
        for (var key in this) v.push.apply(v, this[key]);
        return v;
    }-*/;

    final native JsArray<T> get(String hashCode) /*-{
        return this[hashCode];
    }-*/;

    private void checkNotNull(Object o) {
        if (o == null)
            throw new NullPointerException("This HashTable does not support null values.");
    }

    private native void deleteBucket(int hashCode) /*-{
        delete this[hashCode];
    }-*/;

    private native void put(int hashCode, T value) /*-{
        if (!this[hashCode]) this[hashCode] = [];
        this[hashCode].push(value);
    }-*/;
}
