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

public class BytesModelValueTest {

    @Test
    public void testBytesModelValue() {
        final BytesModelValue value = new BytesModelValue(new byte[] { (byte) 0 });
        assertNotNull(value);
        assertEquals(ModelType.BYTES, value.getType());
        assertEquals(BigInteger.ZERO, value.asBigInteger());
    }

    @Test
    public void testWriteExternal() {
        // TODO implement test
    }

    @Test
    public void testAsLong() {
        final BytesModelValue value = new BytesModelValue(new byte[] { (byte) 0 });
        assertEquals(0L, value.asLong());
    }

    @Test
    public void testAsLongWithDefault() {
        final BytesModelValue value = new BytesModelValue(new byte[] { (byte) 0 });
        assertEquals(0L, value.asLong(5L));
    }

    @Test
    public void testAsInt() {
        final BytesModelValue value = new BytesModelValue(new byte[] { (byte) 0 });
        assertEquals(0, value.asInt());
    }

    @Test
    public void testAsIntWithDefault() {
        final BytesModelValue value = new BytesModelValue(new byte[] { (byte) 0 });
        assertEquals(0, value.asInt(5));
    }

    @Test
    public void testAsDouble() {
        final BytesModelValue value = new BytesModelValue(new byte[] { (byte) 0 });
        assertEquals(Double.valueOf(0), Double.valueOf(value.asDouble()));
    }

    @Test
    public void testAsDoubleWithDefault() {
        final BytesModelValue value = new BytesModelValue(new byte[] { (byte) 0 });
        assertEquals(Double.valueOf(0), Double.valueOf(value.asDouble(5.0)));
    }

    @Test
    public void testAsBigDecimal() {
        final BytesModelValue value = new BytesModelValue(new byte[] { (byte) 0 });
        assertEquals(BigDecimal.ZERO, value.asBigDecimal());
    }

    @Test
    public void testAsBigInteger() {
        final BytesModelValue value = new BytesModelValue(new byte[] { (byte) 0 });
        assertEquals(BigInteger.ZERO, value.asBigInteger());
    }

    @Test
    public void testAsBytes() {
        final BytesModelValue value = new BytesModelValue(new byte[] { (byte) 0 });
        assertEquals(BigInteger.ZERO, new BigInteger(value.asBytes()));
    }

    @Test
    public void testAsString() {
        final BytesModelValue value = new BytesModelValue(new byte[] { (byte) 0 });
        assertEquals("bytes { 0x00 }", value.asString());
    }

    @Test
    public void testToJSONString() {
        final BytesModelValue value = new BytesModelValue(new byte[] { (byte) 0 });
        assertEquals("{\n    \"BYTES_VALUE\" : \"AA==\"\n}", value.toJSONString(false));
        assertEquals("{ \"BYTES_VALUE\" : \"AA==\" }", value.toJSONString(true));
    }

    @Test
    public void testFormatAsJSON() {
        final BytesModelValue value = new BytesModelValue(new byte[] { (byte) 0, (byte) 0x5, (byte) 0x10, (byte) 0x12,
                (byte) 0x123, (byte) 0x1001, (byte) 0x99, (byte) 0x24, (byte) 0x08, (byte) 0x09 });
        final StringWriter stringWriter1 = new StringWriter();
        final PrintWriter writer1 = new PrintWriter(stringWriter1, true);
        value.formatAsJSON(writer1, 0, false);
        assertEquals("{ \"BYTES_VALUE\" : \"AAUQEiMBmSQICQ==\" }", stringWriter1.toString());

        final StringWriter stringWriter2 = new StringWriter();
        final PrintWriter writer2 = new PrintWriter(stringWriter2, true);
        value.formatAsJSON(writer2, 0, true);
        assertEquals("{\n    \"BYTES_VALUE\" : \"AAUQEiMBmSQICQ==\"\n}", stringWriter2.toString());
    }

    @Test
    public void testFormat() {
        final BytesModelValue value = new BytesModelValue(new byte[] { (byte) 100, (byte) 1 });
        final StringWriter stringWriter1 = new StringWriter();
        final PrintWriter writer1 = new PrintWriter(stringWriter1, true);
        value.format(writer1, 0, false);
        assertEquals("bytes { 0x64, 0x01 }", stringWriter1.toString());

        final StringWriter stringWriter2 = new StringWriter();
        final PrintWriter writer2 = new PrintWriter(stringWriter2, true);
        value.format(writer2, 0, true);
        assertEquals("bytes {\n    0x64, 0x01\n}", stringWriter2.toString());
    }

    @Test
    public void testFormatMultiLine() {
        final BytesModelValue value1 = new BytesModelValue(new byte[] { (byte) 0, (byte) 1 });
        final StringWriter stringWriter1 = new StringWriter();
        final PrintWriter writer1 = new PrintWriter(stringWriter1, true);
        value1.formatMultiLine(writer1, 0);
        assertEquals("bytes { 0x00, 0x01 }", stringWriter1.toString());

        final BytesModelValue value2 = new BytesModelValue(new byte[] { (byte) 0, (byte) 1, (byte) 0, (byte) 1, (byte) 0,
                (byte) 1, (byte) 0, (byte) 1, (byte) 0, (byte) 1 });
        final StringWriter stringWriter2 = new StringWriter();
        final PrintWriter writer2 = new PrintWriter(stringWriter2, true);
        value2.formatMultiLine(writer2, 0);
        assertEquals("bytes {\n    0x00, 0x01, 0x00, 0x01, 0x00, 0x01, 0x00, 0x01,\n    0x00, 0x01\n}",
                stringWriter2.toString());
    }

    @Test
    public void testEqualsObject() {
        final BytesModelValue value1 = new BytesModelValue(new byte[] { (byte) 0, (byte) 1 });
        final BytesModelValue value2 = new BytesModelValue(new byte[] { (byte) 0, (byte) 1 });
        final BytesModelValue value3 = new BytesModelValue(new byte[] { (byte) 0 });

        assertEquals(true, value1.equals((Object) value1));
        assertEquals(true, value1.equals((Object) value2));
        assertEquals(true, value2.equals((Object) value1));
        assertEquals(false, value1.equals((Object) value3));
        assertEquals(false, value3.equals((Object) value1));
        assertEquals(false, value1.equals((Object) null));
        assertEquals(false, value1.equals("some string"));
    }

    @Test
    public void testEqualsBytesModelValue() {
        final BytesModelValue value1 = new BytesModelValue(new byte[] { (byte) 0, (byte) 1 });
        final BytesModelValue value2 = new BytesModelValue(new byte[] { (byte) 0, (byte) 1 });
        final BytesModelValue value3 = new BytesModelValue(new byte[] { (byte) 0 });

        assertEquals(true, value1.equals(value1));
        assertEquals(true, value1.equals(value2));
        assertEquals(true, value2.equals(value1));
        assertEquals(false, value1.equals(value3));
        assertEquals(false, value3.equals(value1));
        assertEquals(false, value1.equals(null));
    }

    @Test
    public void testHashCode() {
        final BytesModelValue value1 = new BytesModelValue(new byte[] { (byte) 0, (byte) 1 });
        final BytesModelValue value2 = new BytesModelValue(new byte[] { (byte) 0, (byte) 1 });
        final BytesModelValue value3 = new BytesModelValue(new byte[] { (byte) 0 });

        assertEquals(true, value1.hashCode() == value1.hashCode());
        assertEquals(true, value1.hashCode() == value2.hashCode());
        assertEquals(false, value1.hashCode() == value3.hashCode());
    }
}
