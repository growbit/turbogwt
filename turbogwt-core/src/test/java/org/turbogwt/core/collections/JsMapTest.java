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
import com.google.gwt.junit.client.GWTTestCase;

/**
 * @author Danilo Reinert
 */
public class JsMapTest extends GWTTestCase {

    private JsMap<Integer> map;

    @Override
    public String getModuleName() {
        return "org.turbogwt.core.collections.CollectionsTest";
    }

    public void gwtSetUp() {
        map = JsMap.create();
        for (int i = 0; i < 3; i++) {
            map.put("" + i, i);
        }
    }

    public void testInsert() {
        assertEquals(map.size(), 3);
        map.put("4", 4);
        assertEquals(map.size(), 4);
        assertEquals(map.get("1"), new Integer(1));
    }

    public void testReplace() {
        assertEquals(map.size(), 3);
        map.put("1", 20);
        assertEquals(map.size(), 3);
        assertEquals(map.get("1"), new Integer(20));
    }

    public void testClear() {
        assertEquals(map.size(), 3);
        map.clear();
        assertEquals(map.size(), 0);
        JsArrayString keys = map.keys();
        assertEquals(keys.length(), 0);
    }

    public void testRemove() {
        assertEquals(map.size(), 3);
        map.remove("1");
        map.remove("5"); // Should not affect size
        assertEquals(map.size(), 2);
    }

    public void testKeys() {
        assertEquals(map.size(), 3);
        JsArrayString keys = map.keys();
        assertEquals(keys.length(), 3);
        assertEquals(keys.get(0), "0");
        assertEquals(keys.get(1), "1");
        assertEquals(keys.get(2), "2");
    }

    public void testValues() {
        assertEquals(map.size(), 3);
        JsArray<Integer> values = map.values();
        assertEquals(values.length(), 3);
        assertEquals(values.get(0), new Integer(0));
        assertEquals(values.get(1), new Integer(1));
        assertEquals(values.get(2), new Integer(2));
    }

    public void testKeyOf() {
        assertEquals(map.size(), 3);
        assertNull(map.keyOf(-1));
        assertEquals(map.keyOf(0), "0");
        assertEquals(map.keyOf(1), "1");
        assertEquals(map.keyOf(2), "2");
        assertNull(map.keyOf(3));
    }
}
