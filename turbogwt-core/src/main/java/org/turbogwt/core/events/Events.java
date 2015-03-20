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
package org.turbogwt.core.events;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Utility class for dealing with native events.
 *
 * @author Danilo Reinert
 */
public final class Events {

    private static HandlerRegistration nullHandlerReg = new HandlerRegistration() {
        @Override
        public void removeHandler() {
        }
    };

    private Events() {
    }

    public static HandlerRegistration addEventListener(String elementId, String type, NativeHandler listener) {
        return addEventListener(Document.get().getElementById(elementId), type, listener);
    }

    public static HandlerRegistration addEventListener(final Element element, final String type,
                                                       final NativeHandler listener) {
        if (element != null) {
            final JavaScriptObject nativeListener = addEventListenerNative(element, type, listener);
            return new HandlerRegistration() {
                @Override
                public void removeHandler() {
                    removeEventListenerNative(element, type, nativeListener);
                }
            };
        }
        return nullHandlerReg;
    }

    private static native JavaScriptObject addEventListenerNative(Element el, String type, NativeHandler handler) /*-{
        var listener = function(e){
            handler.@org.turbogwt.core.events.NativeHandler::onEvent(Lcom/google/gwt/dom/client/NativeEvent;)(e);
        };
        el.addEventListener(type, listener);
        return listener;
    }-*/;

    private static native void removeEventListenerNative(Element el, String type, JavaScriptObject listener) /*-{
        el.removeEventListener(type, listener);
    }-*/;
}
