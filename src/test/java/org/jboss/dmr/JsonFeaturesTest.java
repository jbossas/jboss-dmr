/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2023 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
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

package org.jboss.dmr;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
public class JsonFeaturesTest {

    // Some tests taken from http://code.google.com/p/json-smart/wiki/FeaturesTests
    private static final String empty_object        =   "{}";
    private static final String string              =   "{\"v\":\"1\"}";
    private static final String space_tester        =   "{\t\"v\":\"1\"\r\n}";
    private static final String integer             =   "{\"v\":1}";
    private static final String quote_in_string     =   "{\"v\":\"ab'c\"}";
    private static final String float_              =   "{\"PI\":3.141E-10}";
    private static final String float_lowercase     =   "{\"PI\":3.141e-10}";
    private static final String long_               =   "{\"v\":12345123456789}";
    private static final String big_integer         =   "{\"v\":123456789123456789123456789}";
    private static final String int_array           =   "[1,2,3,4]";
    private static final String string_array        =   "[\"1\",\"2\",\"3\",\"4\"]";
    private static final String array_empty_obj     =   "[{ }, { },[]]";
    private static final String unicode_lowercase   =   "{\"v\":\"\\u2000\\u20ff\"}";
    private static final String unicode_uppercase   =   "{\"v\":\"\\u2000\\u20FF\"}";
    private static final String forward_slash       =   "{\"a\":\"hp://foo\"}";
    private static final String boolean_true        =   "{\"a\":true}";
    private static final String boolean_false       =   "{\"a\":false}";
    private static final String double_precision    =   "{\"v\":1.7976931348623157E308}";

    @Test
    public void testEmptyObject() {
        ModelNode json = ModelNode.fromJSONString(empty_object);
        assertNotNull(json);
        assertEquals(ModelType.OBJECT, json.getType());
        assertEquals(0, json.keys().size());
    }

    @Test
    public void testString() {
        ModelNode json = ModelNode.fromJSONString(string);
        assertNotNull(json);
        assertEquals(ModelType.OBJECT, json.getType());

        ModelNode value = json.get("v");
        assertEquals(ModelType.STRING, value.getType());
        assertEquals("1", value.asString());
    }

    @Test
    public void testSpaceTester() {
        ModelNode json = ModelNode.fromJSONString(space_tester);
        assertNotNull(json);
        assertEquals(ModelType.OBJECT, json.getType());

        ModelNode value = json.get("v");
        assertEquals(ModelType.STRING, value.getType());
        assertEquals("1", value.asString());
    }

    @Test
    public void testInteger() {
        ModelNode json = ModelNode.fromJSONString(integer);
        assertNotNull(json);
        assertEquals(ModelType.OBJECT, json.getType());

        ModelNode value = json.get("v");
        assertEquals(ModelType.INT, value.getType());
        assertEquals(1, value.asInt());
    }

    @Test
    public void testQuoteInString() {
        ModelNode json = ModelNode.fromJSONString(quote_in_string);
        assertNotNull(json);
        assertEquals(ModelType.OBJECT, json.getType());

        ModelNode value = json.get("v");
        assertEquals(ModelType.STRING, value.getType());
        assertEquals("ab'c", value.asString());
    }

    @Test
    public void testFloat() {
        ModelNode json = ModelNode.fromJSONString(float_);
        assertNotNull(json);
        assertEquals(ModelType.OBJECT, json.getType());

        ModelNode value = json.get("PI");
        assertEquals(ModelType.BIG_DECIMAL, value.getType());
        assertEquals(BigDecimal.valueOf(3.141e-10), value.asBigDecimal());
    }

    @Test
    public void testFloatLowercase() {
        ModelNode json = ModelNode.fromJSONString(float_lowercase);
        assertNotNull(json);
        assertEquals(ModelType.OBJECT, json.getType());

        ModelNode value = json.get("PI");
        assertEquals(ModelType.BIG_DECIMAL, value.getType());
        assertEquals(BigDecimal.valueOf(3.141e-10), value.asBigDecimal());
    }

    @Test
    public void testLong() {
        ModelNode json = ModelNode.fromJSONString(long_);
        assertNotNull(json);
        assertEquals(ModelType.OBJECT, json.getType());

        ModelNode value = json.get("v");
        assertEquals(ModelType.LONG, value.getType());
        assertEquals(12345123456789L, value.asLong());
    }

    @Test
    public void testBigInteger() {
        ModelNode json = ModelNode.fromJSONString(big_integer);
        assertNotNull(json);
        assertEquals(ModelType.OBJECT, json.getType());

        ModelNode value = json.get("v");
        assertEquals(ModelType.BIG_INTEGER, value.getType());
        assertEquals(new BigInteger("123456789123456789123456789"), value.asBigInteger());
    }

    @Test
    public void testIntArray() {
        ModelNode json = ModelNode.fromJSONString(int_array);
        assertNotNull(json);
        assertEquals(ModelType.LIST, json.getType());

        List<ModelNode> list = json.asList();
        assertEquals(4, list.size());
        for (int i=0; i<4; i++) {
            ModelNode item = list.get(i);
            assertEquals(ModelType.INT, item.getType());
            assertEquals(i+1, list.get(i).asInt());
        }
    }

    @Test
    public void testStringArray() {
        ModelNode json = ModelNode.fromJSONString(string_array);
        assertNotNull(json);
        assertEquals(ModelType.LIST, json.getType());

        List<ModelNode> list = json.asList();
        assertEquals(4, list.size());
        for (int i=0; i<4; i++) {
            ModelNode item = list.get(i);
            assertEquals(ModelType.STRING, item.getType());
            assertEquals(i+1 + "", list.get(i).asString());
        }
    }

    @Test
    public void testArrayEmptyObjects() {
        ModelNode json = ModelNode.fromJSONString(array_empty_obj);
        assertNotNull(json);
        assertEquals(ModelType.LIST, json.getType());

        List<ModelNode> list = json.asList();
        assertEquals(3, list.size());
        for (int i=0; i<3; i++) {
            ModelNode item = list.get(i);
            if (i == 2) {
                assertEquals(ModelType.LIST, item.getType());
            } else {
                assertEquals(ModelType.OBJECT, item.getType());
                assertEquals(0, item.keys().size());
            }
        }
    }

    @Test
    public void testUnicodeLowercase() {
        ModelNode json = ModelNode.fromJSONString(unicode_lowercase);
        assertNotNull(json);
        assertEquals(ModelType.OBJECT, json.getType());

        ModelNode value = json.get("v");
        assertEquals(ModelType.STRING, value.getType());
        assertEquals("\u2000\u20ff", value.asString());
    }

    @Test
    public void testUnicodeUppercase() {
        ModelNode json = ModelNode.fromJSONString(unicode_uppercase);
        assertNotNull(json);
        assertEquals(ModelType.OBJECT, json.getType());

        ModelNode value = json.get("v");
        assertEquals(ModelType.STRING, value.getType());
        assertEquals("\u2000\u20ff", value.asString());
    }

    @Test
    public void testNonProtectedForwardSlash() {
        ModelNode json = ModelNode.fromJSONString(forward_slash);
        assertNotNull(json);
        assertEquals(ModelType.OBJECT, json.getType());

        ModelNode value = json.get("a");
        assertEquals(ModelType.STRING, value.getType());
        assertEquals("hp://foo", value.asString());
    }

    @Test
    public void testBooleanTrue() {
        ModelNode json = ModelNode.fromJSONString(boolean_true);
        assertNotNull(json);
        assertEquals(ModelType.OBJECT, json.getType());

        ModelNode value = json.get("a");
        assertEquals(ModelType.BOOLEAN, value.getType());
        assertTrue(value.asBoolean());
    }

    @Test
    public void testBooleanFalse() {
        ModelNode json = ModelNode.fromJSONString(boolean_false);
        assertNotNull(json);
        assertEquals(ModelType.OBJECT, json.getType());

        ModelNode value = json.get("a");
        assertEquals(ModelType.BOOLEAN, value.getType());
        assertFalse(value.asBoolean());
    }

    @Test
    public void testDoublePrecision() {
        ModelNode json = ModelNode.fromJSONString(double_precision);
        assertNotNull(json);
        assertEquals(ModelType.OBJECT, json.getType());

        ModelNode value = json.get("v");
        assertEquals(ModelType.BIG_DECIMAL, value.getType());
        assertEquals(BigDecimal.valueOf(1.7976931348623157E308), value.asBigDecimal());
    }
}
