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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

public class BigIntegerModelValueTest {

    @Test
    public void testBigIntegerModelValue() {
        final BigIntegerModelValue value = new BigIntegerModelValue(new BigInteger("5"));
        assertNotNull(value);
        assertEquals(ModelType.BIG_INTEGER, value.getType());
        assertEquals(BigInteger.valueOf(5), value.asBigInteger());
    }

    @Test
    public void testWriteExternal() {
        // TODO implement test
    }

    @Test
    public void testAsLong() {
        final BigIntegerModelValue value = new BigIntegerModelValue(new BigInteger("5"));
        assertEquals(5L, value.asLong());
    }

    @Test
    public void testAsLongWithDefault() {
        final BigIntegerModelValue value = new BigIntegerModelValue(new BigInteger("5"));
        assertEquals(5L, value.asLong(10L));
    }

    @Test
    public void testAsInt() {
        final BigIntegerModelValue value = new BigIntegerModelValue(new BigInteger("5"));
        assertEquals(5, value.asInt());
    }

    @Test
    public void testAsIntWithDefault() {
        final BigIntegerModelValue value = new BigIntegerModelValue(new BigInteger("5"));
        assertEquals(5, value.asInt(10));
    }

    @Test
    public void testAsBoolean() {
        final BigIntegerModelValue value1 = new BigIntegerModelValue(new BigInteger("5"));
        assertEquals(true, value1.asBoolean());

        final BigIntegerModelValue value2 = new BigIntegerModelValue(BigInteger.ZERO);
        assertEquals(false, value2.asBoolean());
    }

    @Test
    public void testAsBooleanWithDefault() {
        final BigIntegerModelValue value1 = new BigIntegerModelValue(new BigInteger("5"));
        assertEquals(true, value1.asBoolean(false));

        final BigIntegerModelValue value2 = new BigIntegerModelValue(BigInteger.ZERO);
        assertEquals(false, value2.asBoolean(true));
    }

    @Test
    public void testAsDouble() {
        final BigIntegerModelValue value = new BigIntegerModelValue(new BigInteger("5"));
        assertEquals(Double.valueOf(5), Double.valueOf(value.asDouble()));
    }

    @Test
    public void testAsDoubleWithDefault() {
        final BigIntegerModelValue value = new BigIntegerModelValue(new BigInteger("5"));
        assertEquals(Double.valueOf(5), Double.valueOf(value.asDouble(10)));
    }

    @Test
    public void testAsBigDecimal() {
        final BigIntegerModelValue value = new BigIntegerModelValue(new BigInteger("5"));
        assertEquals(BigDecimal.valueOf(5), value.asBigDecimal());
    }

    @Test
    public void testAsBigInteger() {
        final BigIntegerModelValue value = new BigIntegerModelValue(new BigInteger("5"));
        assertEquals(BigInteger.valueOf(5), value.asBigInteger());
    }

    @Test
    public void testAsString() {
        final BigIntegerModelValue value = new BigIntegerModelValue(new BigInteger("5"));
        assertEquals("5", value.asString());
    }

    @Test
    public void testFormat() {
        final BigIntegerModelValue value = new BigIntegerModelValue(new BigInteger("5"));
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter, true);
        value.format(writer, 0, false);
        assertEquals("big integer 5", stringWriter.toString());
    }

    @Test
    public void testHashCode() {
        final BigIntegerModelValue value1 = new BigIntegerModelValue(BigInteger.ONE);
        final BigIntegerModelValue value2 = new BigIntegerModelValue(BigInteger.ONE);
        final BigIntegerModelValue value3 = new BigIntegerModelValue(BigInteger.TEN);
        assertEquals(true, value1.hashCode() == value2.hashCode());
        assertEquals(false, value1.hashCode() == value3.hashCode());
    }

    @Test
    public void testEqualsObject() {
        final BigIntegerModelValue value1 = new BigIntegerModelValue(BigInteger.ONE);
        final BigIntegerModelValue value2 = new BigIntegerModelValue(BigInteger.ONE);
        final BigIntegerModelValue value3 = new BigIntegerModelValue(BigInteger.TEN);
        assertEquals(true, value1.equals((Object) value1));
        assertEquals(true, value1.equals((Object) value2));
        assertEquals(true, value2.equals((Object) value1));
        assertEquals(false, value1.equals((Object) value3));
        assertEquals(false, value1.equals((Object) null));
        assertEquals(false, value1.equals("Some String"));
    }

    @Test
    public void testEqualsBigIntegerModelValue() {
        final BigIntegerModelValue value1 = new BigIntegerModelValue(BigInteger.ONE);
        final BigIntegerModelValue value2 = new BigIntegerModelValue(BigInteger.ONE);
        final BigIntegerModelValue value3 = new BigIntegerModelValue(BigInteger.TEN);
        assertEquals(true, value1.equals(value1));
        assertEquals(true, value1.equals(value2));
        assertEquals(true, value2.equals(value1));
        assertEquals(false, value1.equals(value3));
        assertEquals(false, value1.equals(null));
    }

    @Test
    public void testToString() {
        final BigIntegerModelValue value = new BigIntegerModelValue(BigInteger.ONE);
        assertEquals("big integer 1", value.toString());
    }

    @Test
    public void testToJSONString() {
        final BigIntegerModelValue value = new BigIntegerModelValue(BigInteger.ONE);
        assertEquals("1", value.toJSONString(false));
        assertEquals("1", value.toJSONString(true));
    }
}
