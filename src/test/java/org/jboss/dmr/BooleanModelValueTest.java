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

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

public class BooleanModelValueTest {

    @Test
    public void TestWriteExternal() {
        //TODO implement test
    }

    @Test
    public void testAsLong() {
        assertEquals(0L, BooleanModelValue.FALSE.asLong());
        assertEquals(1L, BooleanModelValue.TRUE.asLong());
    }

    @Test
    public void testAsLongWithDefault() {
        assertEquals(0L, BooleanModelValue.FALSE.asLong(5L));
        assertEquals(1L, BooleanModelValue.TRUE.asLong(5L));
    }

    @Test
    public void testAsInt() {
        assertEquals(0, BooleanModelValue.FALSE.asInt());
        assertEquals(1, BooleanModelValue.TRUE.asInt());
    }

    @Test
    public void testAsIntWithDefault() {
        assertEquals(0, BooleanModelValue.FALSE.asInt(5));
        assertEquals(1, BooleanModelValue.TRUE.asInt(5));
    }

    @Test
    public void testAsBoolean() {
        assertEquals(false, BooleanModelValue.FALSE.asBoolean());
        assertEquals(true, BooleanModelValue.TRUE.asBoolean());
    }

    @Test
    public void testAsBooleanWithDefault() {
        assertEquals(false, BooleanModelValue.FALSE.asBoolean(true));
        assertEquals(true, BooleanModelValue.TRUE.asBoolean(false));
    }

    @Test
    public void testAsDouble() {
        assertEquals(Double.valueOf(0), Double.valueOf(BooleanModelValue.FALSE.asDouble()));
        assertEquals(Double.valueOf(1), Double.valueOf(BooleanModelValue.TRUE.asDouble()));
    }

    @Test
    public void testAsDoubleWithDefault() {
        assertEquals(Double.valueOf(0), Double.valueOf(BooleanModelValue.FALSE.asDouble(5.0)));
        assertEquals(Double.valueOf(1), Double.valueOf(BooleanModelValue.TRUE.asDouble(5.0)));
    }

    @Test
    public void testAsBytes() {
        assertEquals(new BigInteger(new byte[] { (byte)0 }), new BigInteger(BooleanModelValue.FALSE.asBytes()));
        assertEquals(new BigInteger(new byte[] { (byte)1 }), new BigInteger(BooleanModelValue.TRUE.asBytes()));
    }

    @Test
    public void testAsBigDecimal() {
        assertEquals(BigDecimal.ZERO, BooleanModelValue.FALSE.asBigDecimal());
        assertEquals(BigDecimal.ONE, BooleanModelValue.TRUE.asBigDecimal());
    }

    @Test
    public void testAsBigInteger() {
        assertEquals(BigInteger.ZERO, BooleanModelValue.FALSE.asBigInteger());
        assertEquals(BigInteger.ONE, BooleanModelValue.TRUE.asBigInteger());
    }

    @Test
    public void testAsString() {
        assertEquals("false", BooleanModelValue.FALSE.asString());
        assertEquals("true", BooleanModelValue.TRUE.asString());
    }

    @Test
    public void valueOf() {
        assertEquals(BooleanModelValue.FALSE, BooleanModelValue.valueOf(false));
        assertEquals(BooleanModelValue.TRUE, BooleanModelValue.valueOf(true));
    }

    @Test
    public void testEquals() {
        assertEquals(true, BooleanModelValue.FALSE.equals(BooleanModelValue.valueOf(false)));
        assertEquals(true, BooleanModelValue.TRUE.equals(BooleanModelValue.valueOf(true)));
        assertEquals(false, BooleanModelValue.FALSE.equals(BooleanModelValue.valueOf(true)));
    }

    @Test
    public void testHashCode() {
        assertEquals(true, BooleanModelValue.FALSE.hashCode() == BooleanModelValue.FALSE.hashCode());
        assertEquals(true, BooleanModelValue.TRUE.hashCode() == BooleanModelValue.TRUE.hashCode());
        assertEquals(false, BooleanModelValue.FALSE.hashCode() == BooleanModelValue.TRUE.hashCode());
    }
}
