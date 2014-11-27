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

import com.google.gwt.junit.client.GWTTestCase;

/**
 * @author Danilo Reinert
 */
public class JsHashTableTest extends GWTTestCase {

    private JsHashTable<Integer> table;

    @Override
    public String getModuleName() {
        return "org.turbogwt.core.collections.CollectionsTest";
    }

    public void gwtSetUp() {
        table = JsHashTable.create();
        for (int i = 0; i < 3; i++) {
            table.put(i);
        }
    }

    public void testClear() {
        table.clear();
        assertNull(table.get(new Integer(0).hashCode()));
        assertNull(table.get(new Integer(1).hashCode()));
        assertNull(table.get(new Integer(2).hashCode()));
    }

    public void testGet() {
        final Integer zero = 0;
        final JsArray<Integer> b0 = table.get(zero.hashCode());
        assertNotNull(b0);
        assertTrue(b0.indexOf(zero) > -1);

        final Integer one = 1;
        final JsArray<Integer> b1 = table.get(one.hashCode());
        assertNotNull(b1);
        assertTrue(b1.indexOf(one) > -1);

        final Integer two = 2;
        final JsArray<Integer> b2 = table.get(two.hashCode());
        assertNotNull(b2);
        assertTrue(b2.indexOf(two) > -1);
    }

    public void testPut() {
        table.put(4);

        final Integer four = 4;
        final JsArray<Integer> b4 = table.get(four.hashCode());
        assertNotNull(b4);
        assertTrue(b4.indexOf(four) > -1);
    }

    public void testContains() {
        assertFalse(table.contains(-1));
        assertTrue(table.contains(0));
        assertTrue(table.contains(1));
        assertTrue(table.contains(2));
        assertFalse(table.contains(3));
    }

    public void testRemove() {
        assertFalse(table.remove(-1));
        assertTrue(table.remove(0));
        assertNull(table.get(0));
        assertTrue(table.remove(1));
        assertNull(table.get(1));
        assertTrue(table.remove(2));
        assertNull(table.get(2));
        assertFalse(table.remove(3));
    }
}
