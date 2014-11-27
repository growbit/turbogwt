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

import com.google.gwt.junit.tools.GWTTestSuite;

import junit.framework.Test;

/**
 * @author Danilo Reinert
 */
public class CollectionsGwtTestSuite {

    public static Test suite() {
        GWTTestSuite suite = new GWTTestSuite("Collections GWT Test Suite");

        suite.addTestSuite(JsArrayTest.class);
        suite.addTestSuite(JsArrayListStringTest.class);
        suite.addTestSuite(JsArrayListObjectTest.class);
        suite.addTestSuite(JsHashTableTest.class);
        suite.addTestSuite(JsMapTest.class);

        return suite;
    }
}
