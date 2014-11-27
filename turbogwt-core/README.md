Turbo GWT Core [![Build Status](https://travis-ci.org/growbit/turbogwt/turbogwt-core.svg?branch=master)](https://travis-ci.org/growbit/turbogwt/turbogwt-core)
==

**Turbo GWT Core** contains JavaScriptObject extensions for performing common operations faster in production. These classes are reused across other TurboG modules.

## Highlights

### Lightweight Collections (Overlay Compliant)
In order to save memory usage, Turbo GWT Core provides common data structures implemented as lightweight as possible. They are also expected to be fast as js native properties are used when possible.

* [JsArrayList](https://github.com/growbit/turbogwt-core/blob/master/src/main/java/org/turbogwt/core/collections/client/JsArrayList.java) - **List** implementation wrapping native JS array. Take your json array from request and wrap it directly into a List. No iterations. Simple as <code>new JsArrayList(jsArray)</code>. It works with any object type. (not only JavaScriptObjects)
* [JsArrayIterator](http://growbit.github.io/turbogwt-core/javadoc/apidocs/org/turbogwt/core/collections/client/JsArrayIterator.html) - **Iterator** supporting JsArray.
* [LightMap](http://growbit.github.io/turbogwt-core/javadoc/apidocs/org/turbogwt/core/collections/client/LightMap.html) - Lightweight **Map\<String, T\>** implementation on a simple javascript object.
* [JsHashSet](http://growbit.github.io/turbogwt-core/javadoc/apidocs/org/turbogwt/core/collections/client/JsHashSet.html) - **Set** implementation on a [ligthweight hash table](http://growbit.github.io/turbogwt-core/javadoc/apidocs/org/turbogwt/core/collections/client/JsHashTable.html) (simple js object) using object properties to store the hash codes.

### Native Utilities
* [Overlays](http://growbit.github.io/turbogwt-core/javadoc/apidocs/org/turbogwt/core/util/client/Overlays.html) - Easy boxing and unboxing JS native values and more.
 
With Overlays, you can avoid using JSNI for most common operations:
```java
    class Person extends JavaScriptObject {

        protected Person() {
        }

        public Long getId() {
            return Overlays.getBoxedLong(this, "id");
        }

        public String getName() {
            return Overlays.getString(this, "name");
        }

        public Integer getAge() {
            return Overlays.getBoxedInteger(this, "age");
        }

        public Double getWeight() {
            return Overlays.getBoxedDouble(this, "weight");
        }

        public void setId(Long id) {
            Overlays.setBoxedLong(this, "id", id);
        }

        public void setName(String name) {
            Overlays.setString(this, "name", name);
        }

        public void setAge(Integer age) {
            Overlays.setBoxedInteger(this, "age", age);
        }

        public void setWeight(Double weight) {
            Overlays.setBoxedDouble(this, "weight", weight);
        }
    }
```

## Documentation
* [Javadocs](http://growbit.github.io/turbogwt/turbogwt-core/javadoc/apidocs/index.html)

## Community
* [Turbo GWT Google Group](http://groups.google.com/d/forum/turbogwt) - Share ideas and ask for help.

## Downloads
Turbo GWT Core is currently available at maven central.

### Maven
```
<dependency>
    <groupId>org.turbogwt.core</groupId>
    <artifactId>turbogwt-core</artifactId>
    <version>0.3.0</version>
</dependency>
```

## Thanks to
* [Thomas Broyer](https://plus.google.com/u/0/+ThomasBroyer) for contributing with [JsCollections](http://code.google.com/p/gwt-in-the-air/source/browse/#svn%2Ftrunk%2Fsrc%2Fnet%2Fltgt%2Fgwt%2Fjscollections%2Fclient%253Fstate%253Dclosed).

## License
Turbo GWT Core is freely distributable under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html)
