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

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * A more featured extension of {@link com.google.gwt.core.client.JsArray}.
 *
 * @param <T> Type of list values
 *
 * @author Danilo Reinert
 */
public class JsArray<T> extends JavaScriptObject {

    protected JsArray() {
    }

    public static <T> JsArray<T> fromArray(T... values) {
        if (GWT.isScript()) {
            return reinterpretCast(values);
        } else {
            JsArray<T> ret = create();
            for (int i = 0, l = values.length; i < l; i++) {
                ret.set(i, values[i]);
            }
            return ret;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> JsArray<T> cast(JavaScriptObject jso) {
        return (JsArray<T>) jso;
    }

    @SuppressWarnings("unchecked")
    public static <T> JsArray<T> create() {
        return (JsArray<T>) JavaScriptObject.createArray();
    }

    /**
     * Gets the object at a given index.
     *
     * @param index the index to be retrieved
     * @return the object at the given index, or <code>null</code> if none exists
     */
    public final native T get(int index) /*-{
        return this[index];
    }-*/;

    /**
     * Convert each element of the array to a String and join them with a comma
     * separator. The value returned from this method may vary between browsers
     * based on how JavaScript values are converted into strings.
     * @return the string representation of joined array
     */
    public final String join() {
        // As per JS spec
        return join(",");
    }

    /**
     * Convert each element of the array to a String and join them with a comma
     * separator. The value returned from this method may vary between browsers
     * based on how JavaScript values are converted into strings.
     *
     * @param separator the string to place between elments of the array
     * @return the string representation of joined array
     */
    public final native String join(String separator) /*-{
        return this.join(separator);
    }-*/;

    /**
     * Gets the length of the array.
     *
     * @return the array length
     */
    public final native int length() /*-{
        return this.length;
    }-*/;

    /**
     * Pushes the given value onto the end of the array.
     *
     * @param value the element to push into the array
     */
    public final native void push(T value) /*-{
        this[this.length] = value;
    }-*/;

    /**
     * Sets the object value at a given index.
     *
     * If the index is out of bounds, the value will still be set. The array's
     * length will be updated to encompass the bounds implied by the added object.
     *
     * @param index the index to be set
     * @param value the object to be stored
     */
    public final native void set(int index, T value) /*-{
        this[index] = value;
    }-*/;

    /**
     * Reset the length of the array.
     *
     * @param newLength the new length of the array
     */
    public final native void setLength(int newLength) /*-{
        this.length = newLength;
    }-*/;

    /**
     * Shifts the first value off the array.
     *
     * @return the shifted value
     */
    public final native T shift() /*-{
        return this.shift();
    }-*/;

    /**
     * Shifts a value onto the beginning of the array.
     *
     * @param value the value to the stored
     */
    public final native void unshift(T value) /*-{
        this.unshift(value);
    }-*/;

    public final native void splice(int index, T element) /*-{
        this.splice(index, 0, element);
    }-*/;

    public final native JsArray<T> splice(int index) /*-{
        return this.splice(index, 1);
    }-*/;

    public final native JsArray<T> splice(int index, int quantity) /*-{
        return this.splice(index, quantity);
    }-*/;

    public final JsArray<T> splice(int index, int quantity, T... elements) {
        if (elements.length == 0) {
            return splice(index, quantity);
        }
        if (elements.length > 1) {
            // TODO: Check if the JsArray is being considered as one element.
            final JsArray<T> elements1 = JsArray.fromArray(elements);
            return splice(index, quantity, elements1);
        }
        return splice(index, quantity, elements[0]);
    }

    public final native JsArray<T> splice(int index, int quantity, T element) /*-{
        return this.splice(index, quantity, element);
    }-*/;

    // TODO: Check if the JsArray is being considered as one element.
    public final native JsArray<T> splice(int index, int quantity, JsArray<T> elements) /*-{
        return this.splice(index, quantity, elements);
    }-*/;

    public final native int indexOf(Object search) /*-{
        return this.indexOf(search);
    }-*/;

    public final native int lastIndexOf(Object search) /*-{
        return this.lastIndexOf(search);
    }-*/;

    public final native JsArray<T> slice(int begin) /*-{
        return this.slice(begin);
    }-*/;

    public final native JsArray<T> slice(int begin, int end) /*-{
        return this.slice(begin, end);
    }-*/;

    public final native JsArray concat(JsArray<T> a) /*-{
        return this.concat(a);
    }-*/;

    /**
     * Appends an array to current one.
     *
     * @param a Array to append.
     */
    public final native void pushApply(JsArray<T> a) /*-{
        this.push.apply(this, a);
    }-*/;

    public final native T pop() /*-{
        return this.pop()
    }-*/;

    @SuppressWarnings("unchecked")
    public final Object[] toArray() {
        if (GWT.isScript()) {
            return reinterpretCast(slice(0));
        } else {
            int length = length();
            T nonNull = null;
            for (int i = 0; i < length; i++) {
                nonNull = get(i);
                if (nonNull != null) break;
            }
            T[] ret;
            if (nonNull instanceof JavaScriptObject) {
                ret = (T[]) new JavaScriptObject[length];
            } else {
                ret = (T[]) new Object[length];
            }
            for (int i = 0; i < length; i++) {
                ret[i] = get(i);
            }
            return ret;
        }
    }

    private static native <T> T[] reinterpretCast(JsArray<T> value) /*-{
        return value;
    }-*/;

    private static native <T> JsArray<T> reinterpretCast(T[] value) /*-{
        return value;
    }-*/;
}
