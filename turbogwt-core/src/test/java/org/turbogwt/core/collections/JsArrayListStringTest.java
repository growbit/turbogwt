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

import java.util.Arrays;
import java.util.List;

/**
 * @author Danilo Reinert
 */
public class JsArrayListStringTest extends GWTTestCase {

    private List<String> list;

    @Override
    public String getModuleName() {
        return "org.turbogwt.core.collections.CollectionsTest";
    }

    public void gwtSetUp() {
        list = new JsArrayList<String>();
        for (int i = 0; i < 3; i++) {
            list.add("" + i);
        }
    }

    public void testGet() {
        assertNull(list.get(-1));
        assertEquals(list.get(0), "0");
        assertEquals(list.get(1), "1");
        assertEquals(list.get(2), "2");
        assertNull(list.get(3));
    }

    public void testInsert() {
        assertEquals(list.size(), 3);
        list.add(1, "Insert");
        assertEquals(list.size(), 4);
        assertEquals(list.get(1), "Insert");
    }

    public void testReplace() {
        assertEquals(list.size(), 3);
        list.set(1, "Replace");
        assertEquals(list.size(), 3);
        assertEquals(list.get(1), "Replace");
    }

    public void testOrder() {
        compare(list, new String[] { "0", "1", "2" });
    }

    public void testRemove() {
        assertEquals(list.size(), 3);
        list.remove(1);
        assertEquals(list.size(), 2);
        compare(list, new String[] { "0", "2" });
    }

    public void testAddAll() {
        list.addAll(Arrays.asList("An", "African", "Swallow"));
        assertEquals(list.size(), 6);
        compare(list, new String[] { "0", "1", "2", "An", "African", "Swallow" });
    }

    public void testAddAllByIndex() {
        list.addAll(1, Arrays.asList("An", "African", "Swallow"));
        assertEquals(list.size(), 6);
        compare(list, new String[] { "0", "An", "African", "Swallow", "1", "2" });
    }

    public void testRemoveAll() {
        list.removeAll(Arrays.asList("0", "2"));
        assertEquals(list.size(), 1);
        compare(list, new String[] { "1" });
    }

    public void testRetainAll() {
        list.retainAll(Arrays.asList("0", "2"));
        assertEquals(list.size(), 2);
        compare(list, new String[] { "0", "2" });
    }

    private void compare(List<String> lst, String[] strs) {
        Object[] array = lst.toArray();
        assertTrue("Arrays not the same size", array.length == strs.length);
        for (int i = 0; i < array.length; i++) {
            assertEquals(strs[i], (String) array[i]);
        }
    }
}
