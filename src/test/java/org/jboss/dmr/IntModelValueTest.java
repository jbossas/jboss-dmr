package org.jboss.dmr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import org.junit.Test;


public class IntModelValueTest {

    @Test
    public void testDoubleModelValue() {
        final IntModelValue value = new IntModelValue(5);
        assertNotNull(value);
        assertEquals(ModelType.INT, value.getType());
        assertEquals(5, value.asInt());
    }

    @Test
    public void testWriteExternal() {
        //TODO implement test
    }

    @Test
    public void testAsLong() {
        final IntModelValue value = new IntModelValue(5);
        assertEquals(5L, value.asLong());
    }

    @Test
    public void testAsLongWithDefault() {
        final IntModelValue value = new IntModelValue(5);
        assertEquals(5L, value.asLong(10L));
    }

    @Test
    public void testAsInt() {
        final IntModelValue value = new IntModelValue(5);
        assertEquals(5, value.asInt());
    }

    @Test
    public void testAsIntWithDefault() {
        final IntModelValue value = new IntModelValue(5);
        assertEquals(5, value.asInt(10));
    }

    @Test
    public void testAsBoolean() {
        final IntModelValue value1 = new IntModelValue(5);
        assertEquals(true, value1.asBoolean());

        final IntModelValue value2 = new IntModelValue(0);
        assertEquals(false, value2.asBoolean());
    }

    @Test
    public void  testAsBooleanWithDefault() {
        final IntModelValue value1 = new IntModelValue(5);
        assertEquals(true, value1.asBoolean(false));

        final IntModelValue value2 = new IntModelValue(0);
        assertEquals(false, value2.asBoolean(true));
    }

    @Test
    public void testAsDouble() {
        final IntModelValue value = new IntModelValue(5);
        assertEquals(Double.valueOf(5), Double.valueOf(value.asDouble()));
    }

    @Test
    public void testAsDoubleWithDefault() {
        final IntModelValue value = new IntModelValue(5);
        assertEquals(Double.valueOf(5), Double.valueOf(value.asDouble(10.0)));
    }

    @Test
    public void testAsBytes() {
        final IntModelValue value = new IntModelValue(5);
        assertEquals(5, ByteBuffer.wrap(value.asBytes()).asIntBuffer().get());
    }

    @Test
    public void testAsBigDecimal() {
        final IntModelValue value = new IntModelValue(5);
        assertEquals(new BigDecimal(5), value.asBigDecimal());
    }

    @Test
    public void testAsBigInteger() {
        final IntModelValue value = new IntModelValue(5);
        assertEquals(BigInteger.valueOf(5), value.asBigInteger());
    }

    @Test
    public void testAsString() {
        final IntModelValue value = new IntModelValue(5);
        assertEquals("5", value.asString());
    }

    @Test
    public void testEqualsObject() {
        final IntModelValue value1 = new IntModelValue(5);
        final IntModelValue value2 = new IntModelValue(5);
        final IntModelValue value3 = new IntModelValue(10);

        assertEquals(true, value1.equals((Object)value1));
        assertEquals(true, value1.equals((Object)value2));
        assertEquals(true, value2.equals((Object)value1));
        assertEquals(false, value1.equals((Object)value3));
        assertEquals(false, value1.equals((Object)null));
        assertEquals(false, value1.equals("some string"));
    }

    @Test
    public void testEqualsDoubleModel() {
        final IntModelValue value1 = new IntModelValue(5);
        final IntModelValue value2 = new IntModelValue(5);
        final IntModelValue value3 = new IntModelValue(10);

        assertEquals(true, value1.equals(value1));
        assertEquals(true, value1.equals(value2));
        assertEquals(true, value2.equals(value1));
        assertEquals(false, value1.equals(value3));
        assertEquals(false, value1.equals((IntModelValue)null));
    }

    @Test
    public void testHashCode() {
        final IntModelValue value1 = new IntModelValue(5);
        final IntModelValue value2 = new IntModelValue(5);
        final IntModelValue value3 = new IntModelValue(10);

        assertEquals(true, value1.hashCode() == value1.hashCode());
        assertEquals(true, value1.hashCode() == value2.hashCode());
        assertEquals(false, value1.hashCode() == value3.hashCode());
    }
}
