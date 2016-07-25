package org.jboss.dmr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import org.junit.Test;

public class LongModelValueTest {

    @Test
    public void testLongModelValue() {
        final LongModelValue value = new LongModelValue(5L);
        assertNotNull(value);
        assertEquals(ModelType.LONG, value.getType());
        assertEquals(5L, value.asLong());
    }

    @Test
    public void testWriteExternal() {
        //TODO implement test
    }

    @Test
    public void testAsLong() {
        final LongModelValue value = new LongModelValue(5L);
        assertEquals(5L, value.asLong());
    }

    @Test
    public void testAsLongWithDefault() {
        final LongModelValue value = new LongModelValue(5L);
        assertEquals(5L, value.asLong(10L));
    }

    @Test
    public void testAsInt() {
        final LongModelValue value = new LongModelValue(5L);
        assertEquals(5, value.asInt());
    }

    @Test
    public void testAsIntWithDefault() {
        final LongModelValue value = new LongModelValue(5L);
        assertEquals(5, value.asInt(10));
    }

    @Test
    public void testAsBoolean() {
        final LongModelValue value1 = new LongModelValue(5L);
        assertEquals(true, value1.asBoolean());

        final LongModelValue value2 = new LongModelValue(0L);
        assertEquals(false, value2.asBoolean());
    }

    @Test
    public void  testAsBooleanWithDefault() {
        final LongModelValue value1 = new LongModelValue(5L);
        assertEquals(true, value1.asBoolean(false));

        final LongModelValue value2 = new LongModelValue(0L);
        assertEquals(false, value2.asBoolean(true));
    }

    @Test
    public void testAsDouble() {
        final LongModelValue value = new LongModelValue(5L);
        assertEquals(Double.valueOf(5), Double.valueOf(value.asDouble()));
    }

    @Test
    public void testAsDoubleWithDefault() {
        final LongModelValue value = new LongModelValue(5L);
        assertEquals(Double.valueOf(5), Double.valueOf(value.asDouble(10.0)));
    }

    @Test
    public void testAsBytes() {
        final LongModelValue value = new LongModelValue(5L);
        assertEquals(5L, ByteBuffer.wrap(value.asBytes()).asLongBuffer().get());
    }

    @Test
    public void testAsBigDecimal() {
        final LongModelValue value = new LongModelValue(5L);
        assertEquals(new BigDecimal(5), value.asBigDecimal());
    }

    @Test
    public void testAsBigInteger() {
        final LongModelValue value = new LongModelValue(5L);
        assertEquals(BigInteger.valueOf(5), value.asBigInteger());
    }

    @Test
    public void testAsString() {
        final LongModelValue value = new LongModelValue(5L);
        assertEquals("5", value.asString());
    }

    @Test
    public void testEqualsObject() {
        final LongModelValue value1 = new LongModelValue(5L);
        final LongModelValue value2 = new LongModelValue(5L);
        final LongModelValue value3 = new LongModelValue(10L);

        assertEquals(true, value1.equals((Object)value1));
        assertEquals(true, value1.equals((Object)value2));
        assertEquals(true, value2.equals((Object)value1));
        assertEquals(false, value1.equals((Object)value3));
        assertEquals(false, value1.equals((Object)null));
        assertEquals(false, value1.equals("some string"));
    }

    @Test
    public void testEqualsDoubleModel() {
        final LongModelValue value1 = new LongModelValue(5L);
        final LongModelValue value2 = new LongModelValue(5L);
        final LongModelValue value3 = new LongModelValue(10L);

        assertEquals(true, value1.equals(value1));
        assertEquals(true, value1.equals(value2));
        assertEquals(true, value2.equals(value1));
        assertEquals(false, value1.equals(value3));
        assertEquals(false, value1.equals((LongModelValue)null));
    }

    @Test
    public void testHashCode() {
        final LongModelValue value1 = new LongModelValue(5L);
        final LongModelValue value2 = new LongModelValue(5L);
        final LongModelValue value3 = new LongModelValue(10L);

        assertEquals(true, value1.hashCode() == value1.hashCode());
        assertEquals(true, value1.hashCode() == value2.hashCode());
        assertEquals(false, value1.hashCode() == value3.hashCode());
    }
}
