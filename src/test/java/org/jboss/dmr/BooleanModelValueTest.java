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
        assertEquals(0l, BooleanModelValue.FALSE.asLong());
        assertEquals(1l, BooleanModelValue.TRUE.asLong());
    }

    @Test
    public void testAsLongWithDefault() {
        assertEquals(0l, BooleanModelValue.FALSE.asLong(5l));
        assertEquals(1l, BooleanModelValue.TRUE.asLong(5l));
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
