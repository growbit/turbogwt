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
public class JsArrayTest extends GWTTestCase {

    private JsArray<String> list;

    @Override
    public String getModuleName() {
        return "org.turbogwt.core.collections.CollectionsTest";
    }

    public void gwtSetUp() {
        list = JsArray.create();
        for (int i = 0; i < 3; i++) {
            list.push("" + i);
        }
    }

    public void testInsert() {
        assertEquals(list.length(), 3);
        list.splice(1, "Insert");
        assertEquals(list.length(), 4);
        assertEquals(list.get(1), "Insert");
    }

    public void testReplace() {
        assertEquals(list.length(), 3);
        list.set(1, "Replace");
        assertEquals(list.length(), 3);
        assertEquals(list.get(1), "Replace");
    }

    public void testOrder() {
        compare(list, new String[] { "0", "1", "2" });
    }

    public void testRemove() {
        assertEquals(list.length(), 3);
        list.splice(1, 1);
        assertEquals(list.length(), 2);
        compare(list, new String[] { "0", "2" });
    }

    private void compare(JsArray<String> lst, String[] strs) {
        Object[] array = lst.toArray();
        assertTrue("Arrays not the same length", array.length == strs.length);
        for (int i = 0; i < array.length; i++) {
            assertEquals(strs[i], (String) array[i]);
        }
    }
}
