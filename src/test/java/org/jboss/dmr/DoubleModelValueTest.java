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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import org.junit.Test;


public class DoubleModelValueTest {

    @Test
    public void testDoubleModelValue() {
        final DoubleModelValue value = new DoubleModelValue(5.0);
        assertNotNull(value);
        assertEquals(ModelType.DOUBLE, value.getType());
        assertEquals(Double.valueOf(5), Double.valueOf(value.asDouble()));
    }

    @Test
    public void testWriteExternal() {
        //TODO implement test
    }

    @Test
    public void testAsLong() {
        final DoubleModelValue value = new DoubleModelValue(5.0);
        assertEquals(5L, value.asLong());
    }

    @Test
    public void testAsLongWithDefault() {
        final DoubleModelValue value = new DoubleModelValue(5.0);
        assertEquals(5L, value.asLong(10L));
    }

    @Test
    public void testAsInt() {
        final DoubleModelValue value = new DoubleModelValue(5.0);
        assertEquals(5, value.asInt());
    }

    @Test
    public void testAsIntWithDefault() {
        final DoubleModelValue value = new DoubleModelValue(5.0);
        assertEquals(5, value.asInt(10));
    }

    @Test
    public void testAsBoolean() {
        final DoubleModelValue value1 = new DoubleModelValue(5.0);
        assertEquals(true, value1.asBoolean());

        final DoubleModelValue value2 = new DoubleModelValue(0.0);
        assertEquals(false, value2.asBoolean());
    }

    @Test
    public void  testAsBooleanWithDefault() {
        final DoubleModelValue value1 = new DoubleModelValue(5.0);
        assertEquals(true, value1.asBoolean(false));

        final DoubleModelValue value2 = new DoubleModelValue(0.0);
        assertEquals(false, value2.asBoolean(true));
    }

    @Test
    public void testAsDouble() {
        final DoubleModelValue value = new DoubleModelValue(5.0);
        assertEquals(Double.valueOf(5), Double.valueOf(value.asDouble()));
    }

    @Test
    public void testAsDoubleWithDefault() {
        final DoubleModelValue value = new DoubleModelValue(5.0);
        assertEquals(Double.valueOf(5), Double.valueOf(value.asDouble(10.0)));
    }

    @Test
    public void testAsBytes() {
        final DoubleModelValue value = new DoubleModelValue(5.0);
        assertEquals(Double.valueOf(5), Double.valueOf(ByteBuffer.wrap(value.asBytes()).asDoubleBuffer().get()));
    }

    @Test
    public void testAsBigDecimal() {
        final DoubleModelValue value = new DoubleModelValue(5.0);
        assertEquals(BigDecimal.valueOf(5.0), value.asBigDecimal());
    }

    @Test
    public void testAsBigInteger() {
        final DoubleModelValue value = new DoubleModelValue(5.0);
        assertEquals(BigInteger.valueOf(5), value.asBigInteger());
    }

    @Test
    public void testAsString() {
        final DoubleModelValue value = new DoubleModelValue(5.0);
        assertEquals("5.0", value.asString());
    }

    @Test
    public void testEqualsObject() {
        final DoubleModelValue value1 = new DoubleModelValue(5.0);
        final DoubleModelValue value2 = new DoubleModelValue(5.0);
        final DoubleModelValue value3 = new DoubleModelValue(10.0);

        assertEquals(true, value1.equals((Object)value1));
        assertEquals(true, value1.equals((Object)value2));
        assertEquals(true, value2.equals((Object)value1));
        assertEquals(false, value1.equals((Object)value3));
        assertEquals(false, value1.equals((Object)null));
        assertEquals(false, value1.equals("some string"));
    }

    @Test
    public void testEqualsDoubleModel() {
        final DoubleModelValue value1 = new DoubleModelValue(5.0);
        final DoubleModelValue value2 = new DoubleModelValue(5.0);
        final DoubleModelValue value3 = new DoubleModelValue(10.0);

        assertEquals(true, value1.equals(value1));
        assertEquals(true, value1.equals(value2));
        assertEquals(true, value2.equals(value1));
        assertEquals(false, value1.equals(value3));
        assertEquals(false, value1.equals((DoubleModelValue)null));
    }

    @Test
    public void testHashCode() {
        final DoubleModelValue value1 = new DoubleModelValue(5.0);
        final DoubleModelValue value2 = new DoubleModelValue(5.0);
        final DoubleModelValue value3 = new DoubleModelValue(10.0);

        assertEquals(true, value1.hashCode() == value1.hashCode());
        assertEquals(true, value1.hashCode() == value2.hashCode());
        assertEquals(false, value1.hashCode() == value3.hashCode());
    }
}
