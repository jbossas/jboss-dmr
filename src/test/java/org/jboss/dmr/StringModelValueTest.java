package org.jboss.dmr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.Random;
import org.junit.Test;

public class StringModelValueTest {

    @Test
    public void testStringModelValue() {
        final StringModelValue value = new StringModelValue("5");
        assertNotNull(value);
        assertEquals(ModelType.STRING, value.getType());
        assertEquals(5, value.asInt());
    }

    @Test
    public void testWriteExternal() throws IOException {
        final Random r = new Random(1234L);
        final StringBuilder b = new StringBuilder(80000);
        for (int i = 0; i < 4000; i ++) {
            b.append((char) r.nextInt());
        }
        final String smallString = b.toString();
        final StringModelValue small = new StringModelValue(smallString);
        for (int i = 0; i < 76000; i ++) {
            b.append((char) r.nextInt());
        }
        final String largeString = b.toString();
        final StringModelValue large = new StringModelValue(largeString);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(200000);
        final DataOutputStream dos = new DataOutputStream(baos);
        small.writeExternal(dos);
        large.writeExternal(dos);
        dos.flush();
        baos.flush();
        final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        final DataInputStream dis = new DataInputStream(bais);
        assertEquals(dis.readUnsignedByte(), 's');
        final StringModelValue smallIn = new StringModelValue('s', dis);
        assertEquals(smallString, smallIn.asString());
        assertEquals(dis.readUnsignedByte(), 'S');
        final StringModelValue largeIn = new StringModelValue('S', dis);
        assertEquals(largeString, largeIn.asString());
    }

    @Test
    public void testAsLong() {
        final StringModelValue value = new StringModelValue("5");
        assertEquals(5l, value.asLong());
    }

    @Test(expected = NumberFormatException.class)
    public void testAsLongWithNumberFormatException() {
        final StringModelValue value = new StringModelValue("hello");
        assertEquals(5l, value.asLong());
    }

    @Test
    public void testAsLongWithDefault() {
        final StringModelValue value = new StringModelValue("5");
        assertEquals(5l, value.asLong(10l));
    }

    @Test(expected = NumberFormatException.class)
    public void testAsLongWithDefaultWithNumberFormatException() {
        final StringModelValue value = new StringModelValue("hello");
        assertEquals(5l, value.asLong(10l));
    }

    @Test
    public void testAsInt() {
        final StringModelValue value = new StringModelValue("5");
        assertEquals(5, value.asInt());
    }

    @Test(expected = NumberFormatException.class)
    public void testAsIntWithNumberFormatException() {
        final StringModelValue value = new StringModelValue("hello");
        assertEquals(5, value.asInt());
    }

    @Test
    public void testAsIntWithDefault() {
        final StringModelValue value = new StringModelValue("5");
        assertEquals(5, value.asInt(10));
    }

    @Test(expected = NumberFormatException.class)
    public void testAsIntWithDefaultWithNumberFormatException() {
        final StringModelValue value = new StringModelValue("hello");
        assertEquals(5, value.asInt(10));
    }

    @Test
    public void testAsBoolean() {
        final StringModelValue value1 = new StringModelValue("5");
        assertEquals(false, value1.asBoolean());

        final StringModelValue value2 = new StringModelValue("true");
        assertEquals(true, value2.asBoolean());

        final StringModelValue value3 = new StringModelValue("false");
        assertEquals(false, value3.asBoolean());
    }

    @Test
    public void testAsBooleanWithDefault() {
        final StringModelValue value1 = new StringModelValue("5");
        assertEquals(false, value1.asBoolean(true));

        final StringModelValue value2 = new StringModelValue("true");
        assertEquals(true, value2.asBoolean(false));

        final StringModelValue value3 = new StringModelValue("false");
        assertEquals(false, value3.asBoolean(true));
    }

    @Test
    public void testAsDouble() {
        final StringModelValue value = new StringModelValue("5");
        assertEquals(Double.valueOf(5), Double.valueOf(value.asDouble()));
    }

    @Test(expected = NumberFormatException.class)
    public void testAsDoubleWithNumberFormatException() {
        final StringModelValue value = new StringModelValue("hello");
        assertEquals(Double.valueOf(5), Double.valueOf(value.asDouble()));
    }

    @Test
    public void testAsDoubleWithDefault() {
        final StringModelValue value = new StringModelValue("5");
        assertEquals(Double.valueOf(5), Double.valueOf(value.asDouble(10.0)));
    }

    @Test(expected = NumberFormatException.class)
    public void testAsDoubleWithDefaultWithNumberFormatException() {
        final StringModelValue value = new StringModelValue("hello");
        assertEquals(Double.valueOf(5), Double.valueOf(value.asDouble(10.0)));
    }

    @Test
    public void testAsBytes() {
        final StringModelValue value = new StringModelValue("5");
        assertEquals("5", new String(value.asBytes()));
    }

    @Test
    public void testAsBigDecimal() {
        final StringModelValue value = new StringModelValue("5");
        assertEquals(new BigDecimal(5), value.asBigDecimal());
    }

    @Test
    public void testAsBigInteger() {
        final StringModelValue value = new StringModelValue("5");
        assertEquals(BigInteger.valueOf(5), value.asBigInteger());
    }

    @Test
    public void testAsString() {
        final StringModelValue value = new StringModelValue("5");
        assertEquals("5", value.asString());
    }

    @Test
    public void testAsType() {
        final StringModelValue bigDecimal = new StringModelValue("BIG_DECIMAL");
        assertEquals(ModelType.BIG_DECIMAL, bigDecimal.asType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAsTypeWithUnknownType() {
        final StringModelValue value = new StringModelValue("hello");
        value.asType();
        fail("Expected IllegalArgumentException");
    }

    @Test
    public void testFormat() {
        final StringModelValue value = new StringModelValue("5");
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter, true);
        value.format(writer, 0, false);
        assertEquals("\"5\"", stringWriter.toString());
    }

    @Test
    public void testFormatAsJSON() {
        final StringModelValue value = new StringModelValue("5");
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter, true);
        value.formatAsJSON(writer, 0, false);
        assertEquals("\"5\"", stringWriter.toString());
    }

    @Test
    public void testEqualsObject() {
        final StringModelValue value1 = new StringModelValue("5");
        final StringModelValue value2 = new StringModelValue("5");
        final StringModelValue value3 = new StringModelValue("10");

        assertEquals(true, value1.equals((Object) value1));
        assertEquals(true, value1.equals((Object) value2));
        assertEquals(true, value2.equals((Object) value1));
        assertEquals(false, value1.equals((Object) value3));
        assertEquals(false, value1.equals((Object) null));
        assertEquals(false, value1.equals("some string"));
    }

    @Test
    public void testEqualsDoubleModel() {
        final StringModelValue value1 = new StringModelValue("5");
        final StringModelValue value2 = new StringModelValue("5");
        final StringModelValue value3 = new StringModelValue("10");

        assertEquals(true, value1.equals(value1));
        assertEquals(true, value1.equals(value2));
        assertEquals(true, value2.equals(value1));
        assertEquals(false, value1.equals(value3));
        assertEquals(false, value1.equals((StringModelValue) null));
    }

    @Test
    public void testHashCode() {
        final StringModelValue value1 = new StringModelValue("5");
        final StringModelValue value2 = new StringModelValue("5");
        final StringModelValue value3 = new StringModelValue("10");

        assertEquals(true, value1.hashCode() == value1.hashCode());
        assertEquals(true, value1.hashCode() == value2.hashCode());
        assertEquals(false, value1.hashCode() == value3.hashCode());
    }

}
