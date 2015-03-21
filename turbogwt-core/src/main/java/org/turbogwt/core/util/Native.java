/*
 * Copyright 2015 Grow Bit
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
import com.google.gwt.core.client.JsArrayString;

import javax.annotation.Nullable;

/**
 * Utility methods derived from browser's native JS API.
 *
 * @author Danilo Reinert
 */
public final class Native {

    private Native() {
    }

    public static native boolean isNumeric(@Nullable String text) /*-{
        if (text) return !isNaN(text);
        return false;
    }-*/;

    public static String[] split(String str, String regex) {
        if (GWT.isScript()) {
            return splitNativeToArray(str, regex, -1);
        }
        return Util.toArray(splitNative(str, regex, -1));
    }

    public static String[] split(String str, String regex, int limit) {
        if (GWT.isScript()) {
            return splitNativeToArray(str, regex, limit);
        }
        return Util.toArray(splitNative(str, regex, limit));
    }

    public static native String toFixed(double number) /*-{
        return number.toFixed();
    }-*/;

    public static native String toFixed(double number, int fractionalDigits) /*-{
        return number.toFixed(fractionalDigits);
    }-*/;

    private static native JsArrayString splitNative(String str, String regex, int limit) /*-{
        if (limit == -1) return str.split(regex);
        return str.split(regex, limit);
    }-*/;

    private static native String[] splitNativeToArray(String str, String regex, int limit) /*-{
        if (limit == -1) return str.split(regex);
        return str.split(regex, limit);
    }-*/;
}
