/*
Copyright 2017 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.jboss.dmr.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.jboss.dmr.Property;
import org.jboss.dmr.ValueExpression;
import org.junit.Test;

/**
 * Tests the public contracts of {@link ModelNode}.
 *
 * @author Brian Stansberry
 */
public class ModelNodeTest {

    @Test
    public void testProtect() {
        ModelNode testee = new ModelNode();
        testee.protect();
        try {
            testee.set(true);
            fail();
        } catch (UnsupportedOperationException ok) {
            // ignore
        }

    }

    @Test
    public void testAsType() {
        ModelNode testee = testUndefinedConversion(ModelNode::asType);
        testee.set(ModelType.UNDEFINED);
        assertEquals(ModelType.UNDEFINED, testee.asType());
    }

    @Test
    public void testAsProperty() {

        ModelNode testee = testUndefinedConversion(ModelNode::asProperty);

        testee.set("a", new ModelNode(true));
        Property prop = testee.asProperty();
        assertEquals("a", prop.getName());
        assertTrue(prop.getValue().asBoolean());
    }

    @Test
    public void testAsPropertyList() {

        ModelNode testee = testUndefinedConversion(ModelNode::asPropertyList);

        testee.set("a", new ModelNode(true));
        List<Property> list = testee.asPropertyList();
        assertEquals(1, list.size());
        Property prop = list.get(0);
        assertEquals("a", prop.getName());
        assertTrue(prop.getValue().asBoolean());

        testee.setEmptyObject();
        list = testee.asPropertyList();
        assertEquals(0, list.size());

        testee.get("a").set(true);
        testee.get("b").set(1);
        list = testee.asPropertyList();
        assertEquals(2, list.size());
        prop = list.get(0);
        assertEquals("a", prop.getName());
        assertTrue(prop.getValue().asBoolean());
        prop = list.get(1);
        assertEquals("b", prop.getName());
        assertEquals(1, prop.getValue().asInt());
    }

    @Test
    public void testClear() {
        ModelNode input = new ModelNode();
        input.get("a").set(true);
        ModelNode testee = new ModelNode();
        testee.set(input);
        assertTrue(testee.isDefined());
        testee.clear();
        assertFalse(testee.isDefined());
        assertTrue(input.get("a").asBoolean());
    }

    @Test
    public void testRequire() {
        ModelNode testee = new ModelNode();
        try {
            testee.require("a");
            fail();
        } catch (NoSuchElementException expected) {
            //good
        }
        testee.get("a");
        testee.require("a");
        try {
            testee.require("b");
            fail();
        } catch (NoSuchElementException expected) {
            //good
        }
        testee.setEmptyList();
        try {
            testee.require(0);
            fail();
        } catch (NoSuchElementException expected) {
            //good
        }
        testee.add();
        testee.require(0);
        try {
            testee.require(1);
            fail();
        } catch (NoSuchElementException expected) {
            //good
        }

    }

    @Test
    public void testRemove() {
        ModelNode testee = new ModelNode();
        // TODO the following fails with IAE, not NSEE, which doesn't quite seem right
//        try {
//            testee.remove("a");
//            fail();
//        } catch (NoSuchElementException expected) {
//            //good
//        }
        testee.get("a").set(1);
        ModelNode a = testee.remove("a");
        assertTrue(a.asBoolean());
        // TODO DMR-31 this doesn't fail it returns null
//        try {
//            testee.remove("b");
//            fail();
//        } catch (NoSuchElementException expected) {
//            //good
//        }
        testee.setEmptyList();
        // TODO DMR-32 fails with wrong exception
//        try {
//            testee.remove(0);
//            fail();
//        } catch (NoSuchElementException expected) {
//            //good
//        }
        testee.add(true);
        a = testee.remove(0);
        assertTrue(a.asBoolean());
        // TODO DMR-32 fails with wrong exception
//        try {
//            testee.remove(1);
//            fail();
//        } catch (NoSuchElementException expected) {
//            //good
//        }
    }

    @Test
    public void testAdd() {
        ModelNode testee = basicAddTest(input -> input.add(1));
        assertEquals(1, testee.get(0).asInt());
        testee = basicAddTest(input -> input.add(Long.MAX_VALUE));
        assertEquals(Long.MAX_VALUE, testee.get(0).asLong());
        testee = basicAddTest(input -> input.add((double) 3));
        assertEquals((double) 3, testee.get(0).asDouble(), 0);
        testee = basicAddTest(input -> input.add(true));
        assertTrue(testee.get(0).asBoolean());
        testee = basicAddTest(input -> input.add("a"));
        assertEquals("a", testee.get(0).asString());
        testee = basicAddTest(input -> input.add(new ValueExpression("a")));
        assertEquals(new ValueExpression("a"), testee.get(0).asExpression());
        BigDecimal bd = new BigDecimal(10);
        testee = basicAddTest(input -> input.add(bd));
        assertEquals(bd, testee.get(0).asBigDecimal());
        BigInteger bigInt = new BigInteger(0, new byte[0]);
        testee = basicAddTest(input -> input.add(bigInt));
        assertEquals(bigInt, testee.get(0).asBigInteger());
        ModelNode obj = new ModelNode();
        obj.get("a").set("b");
        testee = basicAddTest(input -> input.add(obj));
        assertEquals(obj, testee.get(0).asObject());
        byte[] bar = new byte[5];
        testee = basicAddTest(input -> input.add(bar));
        assertTrue(Arrays.equals(bar, testee.get(0).asBytes()));
        Property prop = new Property("a", new ModelNode(bar));
        testee = basicAddTest(input -> input.add(prop));
        assertEquals(prop.getName(), testee.get(0).asProperty().getName());
        assertTrue(Arrays.equals(bar, testee.get(0).asProperty().getValue().asBytes()));
        testee = basicAddTest(input -> input.add("a", 4));
        assertEquals("a", testee.get(0).asProperty().getName());
        assertEquals(4, testee.get(0).asProperty().getValue().asInt());
        testee = basicAddTest(input -> input.add("a", Long.MIN_VALUE));
        assertEquals("a", testee.get(0).asProperty().getName());
        assertEquals(Long.MIN_VALUE, testee.get(0).asProperty().getValue().asLong());
        testee = basicAddTest(input -> input.add("a", (double) 1));
        assertEquals("a", testee.get(0).asProperty().getName());
        assertEquals((double) 1, testee.get(0).asProperty().getValue().asDouble(), 0);
        testee = basicAddTest(input -> input.add("a", true));
        assertEquals("a", testee.get(0).asProperty().getName());
        assertTrue(testee.get(0).asProperty().getValue().asBoolean());
        testee = basicAddTest(input -> input.add("a", new ValueExpression("a")));
        assertEquals("a", testee.get(0).asProperty().getName());
        assertEquals(new ValueExpression("a"), testee.get(0).asProperty().getValue().asExpression());
        testee = basicAddTest(input -> input.add("a", "b"));
        assertEquals("a", testee.get(0).asProperty().getName());
        assertEquals("b", testee.get(0).asProperty().getValue().asString());
        testee = basicAddTest(input -> input.add("a", bd));
        assertEquals("a", testee.get(0).asProperty().getName());
        assertEquals(bd, testee.get(0).asProperty().getValue().asBigDecimal());
        testee = basicAddTest(input -> input.add("a", bigInt));
        assertEquals("a", testee.get(0).asProperty().getName());
        assertEquals(bigInt, testee.get(0).asProperty().getValue().asBigInteger());
        testee = basicAddTest(input -> input.add("a", obj));
        assertEquals("a", testee.get(0).asProperty().getName());
        assertEquals(obj, testee.get(0).asProperty().getValue().asObject());
        testee = basicAddTest(input -> input.add("a", bar));
        assertEquals("a", testee.get(0).asProperty().getName());
        assertTrue(Arrays.equals(bar, testee.get(0).asProperty().getValue().asBytes()));
    }

    @Test
    public void testInsert() {
        // Initial add equivalent
        insertTest(input -> input.insert(0), 0, 0, new ModelNode());
        // Insert last
        insertTest(input -> input.insert(1), 1, 1, new ModelNode());
        // Insert first
        insertTest(input -> input.insert(0), 1, 0, new ModelNode());
        // Insert middle
        insertTest(input -> input.insert(1), 2, 1, new ModelNode());
        // Skipping fails
        try {
            insertTest(input -> input.insert(1), 0, 1, new ModelNode());
        } catch (IndexOutOfBoundsException expected) {
            // good
        }
        try {
            insertTest(input -> input.insert(2), 1, 2, new ModelNode());
        } catch (IndexOutOfBoundsException expected) {
            // good
        }

        // Now the 2 arg variant
        ModelNode obj = new ModelNode();
        obj.get("a").set("b");
        // Initial add equivalent
        insertTest(input -> input.insert(obj, 0), 0, 0, obj);
        // Insert last
        insertTest(input -> input.insert(obj, 1), 1, 1, obj);
        // Insert first
        insertTest(input -> input.insert(obj, 0), 1, 0, obj);
        // Insert middle
        insertTest(input -> input.insert(obj, 1), 2, 1, obj);
        // Skipping fails
        try {
            insertTest(input -> input.insert(obj, 1), 0, 1, obj);
        } catch (IndexOutOfBoundsException expected) {
            // good
        }
        try {
            insertTest(input -> input.insert(obj, 2), 1, 2, obj);
        } catch (IndexOutOfBoundsException expected) {
            // good
        }
    }

    @Test
    public void testAddEmptyList() {
        ModelNode testee = new ModelNode();
        ModelNode added = testee.addEmptyList();
        assertEquals(ModelType.LIST, testee.getType());
        assertEquals(1, testee.asList().size());
        assertEquals(ModelType.LIST, added.getType());
        assertEquals(0, added.asList().size());

        added.add(1);

        added = testee.addEmptyList();
        assertEquals(ModelType.LIST, testee.getType());
        assertEquals(2, testee.asList().size());
        assertEquals(ModelType.LIST, added.getType());
        assertEquals(0, added.asList().size());

        assertEquals(1, testee.get(0).asList().size());

        testee = new ModelNode("a");
        try {
            testee.addEmptyList();
        } catch (IllegalArgumentException good) {
            //
        }
    }

    @Test
    public void testAddEmptyObject() {
        ModelNode testee = new ModelNode();
        ModelNode added = testee.addEmptyObject();
        assertEquals(ModelType.LIST, testee.getType());
        assertEquals(1, testee.asList().size());
        assertEquals(ModelType.OBJECT, added.getType());
        assertEquals(0, added.keys().size());

        added.get("a").set(1);

        added = testee.addEmptyObject();
        assertEquals(ModelType.LIST, testee.getType());
        assertEquals(2, testee.asList().size());
        assertEquals(ModelType.OBJECT, added.getType());
        assertEquals(0, added.keys().size());

        assertEquals(1, testee.get(0).keys().size());
        assertEquals(1, testee.get(0).get("a").asInt());

        testee = new ModelNode("a");
        try {
            testee.addEmptyObject();
        } catch (IllegalArgumentException good) {
            //
        }
    }

    @Test
    public void testHas() {
        ModelNode testee = new ModelNode();
        assertFalse(testee.has(0));
        assertFalse(testee.has("a"));

        testee.setEmptyList();
        assertFalse(testee.has(0));
        assertFalse(testee.has("a"));

        testee.add(0);
        assertTrue(testee.has(0));
        assertFalse(testee.has(1));
        assertFalse(testee.has("a"));

        testee.setEmptyObject();
        assertFalse(testee.has(0));
        assertFalse(testee.has("a"));

        testee.get("a");
        assertFalse(testee.has(0));
        assertTrue(testee.has("a"));
        assertFalse(testee.has("b"));
    }

    @Test
    public void testNoExpressionResolve() {
        ModelNode testee = new ModelNode();
        ModelNode resolved = testee.resolve();
        assertEquals(testee, resolved);

        testee = new ModelNode(1);
        resolved = testee.resolve();
        assertEquals(testee, resolved);

        testee = new ModelNode(new ValueExpression("a"));
        resolved = testee.resolve();
        assertEquals("a", resolved.asString());
    }

    @Test
    public void testRecursiveHas() {
        String[] names = {"a", "b", "c"};

        ModelNode testee = new ModelNode();
        assertFalse(testee.toString(), testee.has(names));

        testee.setEmptyList();
        assertFalse(testee.toString(), testee.has(names));

        testee.set(1);
        assertFalse(testee.toString(), testee.has(names));

        testee.setEmptyObject();
        assertFalse(testee.toString(), testee.has(names));

        testee.get("a", "b", "d");
        assertFalse(testee.toString(), testee.has(names));

        testee.get("a", "b", "c", "d");
        assertTrue(testee.toString(), testee.has(names));

        testee.get("a").set("b", "d");
        assertFalse(testee.toString(), testee.has(names));

        testee.get("a").set("b", "c");
        assertFalse(testee.toString(), testee.has(names));

        ModelNode propVal = new ModelNode().setEmptyObject();
        propVal.get("c");
        testee.get("a").set("b", propVal);
        assertTrue(testee.toString(), testee.has(names));
    }

    @Test
    public void testRecursiveHasDefined() {
        String[] names = {"a", "b", "c"};

        ModelNode testee = new ModelNode();
        assertFalse(testee.toString(), testee.hasDefined(names));

        testee.setEmptyList();
        assertFalse(testee.toString(), testee.hasDefined(names));

        testee.set(1);
        assertFalse(testee.toString(), testee.hasDefined(names));

        testee.setEmptyObject();
        assertFalse(testee.toString(), testee.hasDefined(names));

        testee.get("a", "b", "d");
        assertFalse(testee.toString(), testee.hasDefined(names));

        testee.get("a", "b", "c");
        assertFalse(testee.toString(), testee.hasDefined(names));

        testee.get("a", "b", "c", "d");
        assertTrue(testee.toString(), testee.hasDefined(names));

        testee.get("a").set("b", "d");
        assertFalse(testee.toString(), testee.hasDefined(names));

        testee.get("a").set("b", "c");
        assertFalse(testee.toString(), testee.hasDefined(names));

        ModelNode propVal = new ModelNode().setEmptyObject();
        propVal.get("c");
        testee.get("a").set("b", propVal);
        assertTrue("a " + testee.toString(), testee.hasDefined("a"));
        assertTrue("a, b " + testee.toString(), testee.hasDefined("a", "b"));
        assertFalse("a, b, c " + testee.toString(), testee.hasDefined(names));

        propVal.get("c").set(1);
        testee.get("a").set("b", propVal);
        assertTrue(testee.toString(), testee.hasDefined(names));
    }

    private static ModelNode testUndefinedConversion(Function<ModelNode, ?> function) {
        ModelNode testee = new ModelNode();
        try {
            function.apply(testee);
            fail();
        } catch (IllegalArgumentException expected) {
            // good
        }
        return testee;
    }

    private static ModelNode basicAddTest(Function<ModelNode, ?> function) {
        ModelNode testee = new ModelNode("a");
        try {
            function.apply(testee);
            fail();
        } catch (IllegalArgumentException expected) {
            // good
        }
        testee = new ModelNode();
        function.apply(testee);
        assertEquals(ModelType.LIST, testee.getType());
        assertEquals(1, testee.asInt());
        assertEquals(1, testee.asList().size());
        return testee;
    }

    private static void insertTest(Function<ModelNode, ?> function, int initialSize, int insertPos, ModelNode toCheck)  {
        ModelNode testee = new ModelNode("a");
        try {
            function.apply(testee);
            fail();
        } catch (IllegalArgumentException expected) {
            // good
        }
        testee = new ModelNode();
        for (int i = 0; i < initialSize; i++) {
            testee.add();
        }
        function.apply(testee);
        assertEquals(ModelType.LIST, testee.getType());
        assertEquals(initialSize + 1, testee.asInt());
        assertEquals(initialSize + 1, testee.asList().size());
        for (int i = 0; i <= initialSize; i++) {
            assertTrue(testee.has(i));
            if (i == insertPos) {
                assertEquals(toCheck, testee.get(i));
            } else {
                assertFalse(testee.hasDefined(i));
            }
        }
    }
}
