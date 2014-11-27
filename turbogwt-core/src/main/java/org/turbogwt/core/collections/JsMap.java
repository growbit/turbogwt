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
 * Map of String to Object implemented on a JavaScriptObject.
 *
 * @param <T> Type of mapped values
 *
 * @author Danilo Reinert
 */
public class JsMap<T> extends JavaScriptObject {

    protected JsMap() {
    }

    public static native <T> JsMap<T> create() /*-{
        var o = {};
        Object.defineProperties(o, {__props__: {enumerable: false, writable: true, value: []}});
        return o;
    }-*/;

    public final native void clear() /*-{
        // Although not fast, it's safer than creating new objects
        for (var key in this) delete this[key];
        Object.defineProperties(this, {__props__: {enumerable: false, writable: true, value: []}});
    }-*/;

    public final native T get(String key) /*-{
        return this[key];
    }-*/;

    public final void put(String key, T value) {
        checkNotNull(key);
        checkNotNull(value);
        set0(key, value);
    }

    public final native boolean contains(String key) /*-{
        return (key in this);
    }-*/;

    public final native void remove(String key) /*-{
        if (!this.__props__)
            Object.defineProperties(this, {__props__: {enumerable: false, writable: true, value: Object.keys(this)}});
        var i = this.__props__.indexOf(key);
        if (~i) {
            this.__props__.splice(i, 1);
            delete this[key];
        }
    }-*/;

    public final native int size() /*-{
        if (!this.__props__)
            Object.defineProperties(this, {__props__: {enumerable: false, writable: true, value: Object.keys(this)}});
        return this.__props__.length;
    }-*/;

    public final String keyOf(T t) {
        checkNotNull(t);
        return keyOf0(t);
    }

    public final native JsArrayString keys() /*-{
        return this.__props__;
    }-*/;

    public final native JsArray<T> values() /*-{
        var values = [];
        for (var key in this) values.push(this[key]);
        return values;
    }-*/;

    private void checkNotNull(Object o) {
        if (o == null) throw new NullPointerException("This map does not accept null keys or values.");
    }

    private native String keyOf0(T t) /*-{
        for (var key in this) {
            // CHECKSTYLE:OFF
            if (@org.turbogwt.core.collections.JsMap::equalsBridge(Ljava/lang/Object;Ljava/lang/Object;)(t, this[key]))
                return key;
            // CHECKSTYLE:ON
        }
        return null;
    }-*/;

    private native void set0(String key, T value) /*-{
        if (!this[key]) {
            if (!this.__props__)
                Object.defineProperties(this, {__props__: {enumerable: false, writable: true,
                    value: Object.keys(this)}});
            this.__props__.push(key);
        }
        this[key] = value;
    }-*/;

    /**
     * Bridge method from JSNI that keeps us from having to make polymorphic calls
     * in JSNI. By putting the polymorphism in Java code, the compiler can do a
     * better job of optimizing in most cases. (Copied from GWT source)
     */
    @SuppressWarnings("unused")
    private static boolean equalsBridge(Object o1, Object o2) {
        return o1.equals(o2);
    }
}
