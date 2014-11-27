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
public class JsArrayListObjectTest extends GWTTestCase {

    private List<SomeObject> list;

    @Override
    public String getModuleName() {
        return "org.turbogwt.core.collections.CollectionsTest";
    }

    public void gwtSetUp() {
        list = new JsArrayList<SomeObject>();
        for (int i = 0; i < 3; i++) {
            list.add(new SomeObject("Text" + i, i));
        }
    }

    public void testInsert() {
        assertEquals(list.size(), 3);
        final SomeObject inserted = new SomeObject("New", 0);
        list.add(1, inserted);
        assertEquals(list.size(), 4);
        assertEquals(list.get(1), inserted);
    }

    public void testReplace() {
        assertEquals(list.size(), 3);
        final SomeObject replaced = new SomeObject("Replace", 0);
        list.set(1, replaced);
        assertEquals(list.size(), 3);
        assertEquals(list.get(1), replaced);
    }

    public void testOrder() {
        compare(list, new SomeObject[] { new SomeObject("Text0", 0),
                new SomeObject("Text1", 1), new SomeObject("Text2", 2) });
    }

    public void testRemove() {
        assertEquals(list.size(), 3);
        list.remove(1);
        assertEquals(list.size(), 2);
        compare(list, new SomeObject[] { new SomeObject("Text0", 0), new SomeObject("Text2", 2) });
    }

    public void testAddAll() {
        list.addAll(Arrays.asList(new SomeObject("Text3", 3), new SomeObject("Text4", 4)));
        assertEquals(list.size(), 5);
        compare(list, new SomeObject[] { new SomeObject("Text0", 0),
                new SomeObject("Text1", 1), new SomeObject("Text2", 2),
                new SomeObject("Text3", 3), new SomeObject("Text4", 4) });
    }

    public void testAddAllByIndex() {
        list.addAll(1, Arrays.asList(new SomeObject("Text3", 3), new SomeObject("Text4", 4)));
        assertEquals(list.size(), 5);
        compare(list, new SomeObject[] { new SomeObject("Text0", 0),
                new SomeObject("Text3", 3), new SomeObject("Text4", 4),
                new SomeObject("Text1", 1), new SomeObject("Text2", 2) });
    }

    public void testRemoveAll() {
        list.removeAll(Arrays.asList(new SomeObject("Text0", 0), new SomeObject("Text2", 2)));
        assertEquals(list.size(), 1);
        compare(list, new SomeObject[] { new SomeObject("Text1", 1) });
    }

    public void testRetainAll() {
        list.retainAll(Arrays.asList(new SomeObject("Text0", 0), new SomeObject("Text2", 2)));
        assertEquals(list.size(), 2);
        compare(list, new SomeObject[] { new SomeObject("Text0", 0), new SomeObject("Text2", 2) });
    }

    private void compare(List<SomeObject> lst, SomeObject[] strs) {
        Object[] array = lst.toArray();
        assertTrue("Arrays not the same size", array.length == strs.length);
        for (int i = 0; i < array.length; i++) {
            assertEquals(strs[i], array[i]);
        }
    }

    private class SomeObject {

        String text;
        int number;

        SomeObject(String text, int number) {
            this.text = text;
            this.number = number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof SomeObject)) {
                return false;
            }

            final SomeObject that = (SomeObject) o;

            if (number != that.number) {
                return false;
            }
            if (text != null ? !text.equals(that.text) : that.text != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = text != null ? text.hashCode() : 0;
            result = 31 * result + number;
            return result;
        }
    }
}
