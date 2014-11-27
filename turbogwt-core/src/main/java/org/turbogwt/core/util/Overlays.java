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
package org.turbogwt.core.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

/**
 * Utility methods for Overlay Types manipulation.
 *
 * @author Danilo Reinert
 * @author Javier Ferrero (#deepCopy)
 */
public final class Overlays {

    private Overlays() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends JavaScriptObject> T deepCopy(T obj) {
        return (T) deepCopyNative(obj);
    }

    public static native boolean getBoolean(JavaScriptObject jso, String property) /*-{
        return jso[property];
    }-*/;

    public static native Boolean getBoxedBoolean(JavaScriptObject jso, String property) /*-{
        return jso[property] != null ? @java.lang.Boolean::valueOf(Z)(jso[property]) : null;
    }-*/;

    public static native Double getBoxedDouble(JavaScriptObject jso, String property) /*-{
        return jso[property] != null ? @java.lang.Double::valueOf(D)(jso[property]) : null;
    }-*/;

    public static native Integer getBoxedInteger(JavaScriptObject jso, String property) /*-{
        return jso[property] != null ? @java.lang.Integer::valueOf(I)(jso[property]) : null;
    }-*/;

    public static native Long getBoxedLong(JavaScriptObject jso, String property) /*-{
        return jso[property] != null ? @java.lang.Long::valueOf(Ljava/lang/String;)(jso[property] + '') : null;
    }-*/;

    public static native double getDouble(JavaScriptObject jso, String property) /*-{
        return jso[property];
    }-*/;

    public static native int getInt(JavaScriptObject jso, String property) /*-{
        return jso[property];
    }-*/;

    @SuppressWarnings("unchecked")
    public static <T> T getObject(JavaScriptObject jso, String property) {
        return (T) getObjectNative(jso, property);
    }

    public static String[] getPropertyNames(JavaScriptObject jso) {
        return getPropertyNames(jso, false);
    }

    public static String[] getPropertyNames(JavaScriptObject jso, boolean sorted) {
        return toArray(getPropertyNamesNative(jso, sorted));
    }

    public static native String getString(JavaScriptObject jso, String property) /*-{
        return jso[property];
    }-*/;

    public static native boolean isPropertyNullOrUndefined(JavaScriptObject jso, String property) /*-{
        return jso[property] == null;
    }-*/;

    public static native void setBoolean(JavaScriptObject jso, String property, boolean value) /*-{
        jso[property] = value;
    }-*/;

    public static void setBoxedBoolean(JavaScriptObject jso, String property, Boolean value) {
        if (value != null) {
            setBoolean(jso, property, value.booleanValue());
        } else {
            setNull(jso, property);
        }
    }

    public static void setBoxedDouble(JavaScriptObject jso, String property, Double value) {
        if (value != null) {
            setDouble(jso, property, value.doubleValue());
        } else {
            setNull(jso, property);
        }
    }

    public static void setBoxedInteger(JavaScriptObject jso, String property, Integer value) {
        if (value != null) {
            setInt(jso, property, value.intValue());
        } else {
            setNull(jso, property);
        }
    }

    public static void setBoxedLong(JavaScriptObject jso, String property, Long value) {
        if (value != null) {
            setDouble(jso, property, value.doubleValue());
        } else {
            setNull(jso, property);
        }
    }

    public static native void setDouble(JavaScriptObject jso, String property, double value) /*-{
        jso[property] = value;
    }-*/;

    public static native void setInt(JavaScriptObject jso, String property, int value) /*-{
        jso[property] = value;
    }-*/;

    public static native void setNull(JavaScriptObject jso, String property) /*-{
        jso[property] = null;
    }-*/;

    public static native void setString(JavaScriptObject jso, String property, String value) /*-{
        jso[property] = value;
    }-*/;

    public static native String stringify(JavaScriptObject jso) /*-{
        return JSON.stringify(jso);
    }-*/;

    private static native JavaScriptObject deepCopyNative(JavaScriptObject obj) /*-{
        if (obj == null) return obj;

        var copy;

        if (obj instanceof Date) {
            // Handle Date
            copy = new Date();
            copy.setTime(obj.getTime());
        } else if (obj instanceof Array) {
            // Handle Array
            copy = [];
            for (var i = 0, len = obj.length; i < len; i++) {
                if (obj[i] == null || typeof obj[i] != "object") copy[i] = obj[i];
                // CHECKSTYLE:OFF
                else copy[i] = @org.turbogwt.core.util.Overlays::deepCopyNative(Lcom/google/gwt/core/client/JavaScriptObject;)(obj[i]);
                // CHECKSTYLE:ON
            }
        } else {
            // Handle Object
            copy = {};
            for (var attr in obj) {
                if (obj.hasOwnProperty(attr)) {
                    if (obj[attr] == null || typeof obj[attr] != "object") copy[attr] = obj[attr];
                    // CHECKSTYLE:OFF
                    else copy[attr] = @org.turbogwt.core.util.Overlays::deepCopyNative(Lcom/google/gwt/core/client/JavaScriptObject;)(obj[attr]);
                    // CHECKSTYLE:ON
                }
            }
        }
        return copy;
    }-*/;

    private static native Object getObjectNative(JavaScriptObject jso, String property) /*-{
        return jso[property];
    }-*/;

    private static native JsArrayString getPropertyNamesNative(JavaScriptObject jso, boolean sorted) /*-{
        if (sorted) return Object.keys(jso).sort();
        return Object.keys(jso);
    }-*/;

    private static native String[] reinterpretCast(JsArrayString value) /*-{
        return value;
    }-*/;

    private static String[] toArray(JsArrayString values) {
        if (GWT.isScript()) {
            return reinterpretCast(values);
        } else {
            int length = values.length();
            String[] ret = new String[length];
            for (int i = 0, l = length; i < l; i++) {
                ret[i] = values.get(i);
            }
            return ret;
        }
    }
}
