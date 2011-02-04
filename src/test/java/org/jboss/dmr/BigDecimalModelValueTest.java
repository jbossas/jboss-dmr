package org.jboss.dmr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

public class BigDecimalModelValueTest {

    @Test
    public void testBigDecimalModelValue() {
        final BigDecimalModelValue value1 = new BigDecimalModelValue(new BigDecimal(5));
        assertNotNull(value1);
        assertEquals(ModelType.BIG_DECIMAL, value1.getType());
        assertEquals(BigDecimal.valueOf(5), value1.asBigDecimal());
    }

    @Test
    public void testWriteExternal() {
        // TODO implement test
    }

    @Test
    public void testAsLong() {
        final BigDecimalModelValue value = new BigDecimalModelValue(new BigDecimal(5));
        assertEquals(5l, value.asLong());
    }

    @Test
    public void testAsLongWithDefault() {
        final BigDecimalModelValue value = new BigDecimalModelValue(new BigDecimal(5));
        assertEquals(5l, value.asLong(10l));
    }

    @Test
    public void testAsInt() {
        final BigDecimalModelValue value = new BigDecimalModelValue(new BigDecimal(5));
        assertEquals(5, value.asInt());
    }

    @Test
    public void testAsIntWithDefault() {
        final BigDecimalModelValue value = new BigDecimalModelValue(new BigDecimal(5));
        assertEquals(5, value.asInt(10));
    }

    @Test
    public void testAsBoolean() {
        final BigDecimalModelValue value1 = new BigDecimalModelValue(new BigDecimal(5));
        assertEquals(true, value1.asBoolean());

        final BigDecimalModelValue value2 = new BigDecimalModelValue(BigDecimal.ZERO);
        assertEquals(false, value2.asBoolean());
    }

    @Test
    public void testAsBooleanWithDefault() {
        final BigDecimalModelValue value1 = new BigDecimalModelValue(new BigDecimal(5));
        assertEquals(true, value1.asBoolean(false));

        final BigDecimalModelValue value2 = new BigDecimalModelValue(BigDecimal.ZERO);
        assertEquals(false, value2.asBoolean(true));
    }

    @Test
    public void testAsDouble() {
        final BigDecimalModelValue value = new BigDecimalModelValue(new BigDecimal(5));
        assertEquals(Double.valueOf(5), Double.valueOf(value.asDouble()));
    }

    @Test
    public void testAsDoubleWithDefault() {
        final BigDecimalModelValue value = new BigDecimalModelValue(new BigDecimal(5));
        assertEquals(Double.valueOf(5), Double.valueOf(value.asDouble(10)));
    }

    @Test
    public void testAsBigDecimal() {
        final BigDecimalModelValue value = new BigDecimalModelValue(new BigDecimal(5));
        assertEquals(BigDecimal.valueOf(5), value.asBigDecimal());
    }

    @Test
    public void testAsBigInteger() {
        final BigDecimalModelValue value = new BigDecimalModelValue(new BigDecimal(5));
        assertEquals(BigInteger.valueOf(5), value.asBigInteger());
    }

    @Test
    public void testAsString() {
        final BigDecimalModelValue value = new BigDecimalModelValue(new BigDecimal(5));
        assertEquals("5", value.asString());
    }

    @Test
    public void testFormat() {
        final BigDecimalModelValue value = new BigDecimalModelValue(new BigDecimal(5));
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter, true);
        value.format(writer, 0, false);
        assertEquals("big decimal 5", stringWriter.toString());
    }

    @Test
    public void testHashCode() {
        final BigDecimalModelValue value1 = new BigDecimalModelValue(BigDecimal.ONE);
        final BigDecimalModelValue value2 = new BigDecimalModelValue(BigDecimal.ONE);
        final BigDecimalModelValue value3 = new BigDecimalModelValue(BigDecimal.TEN);
        assertEquals(true, value1.hashCode() == value2.hashCode());
        assertEquals(false, value1.hashCode() == value3.hashCode());
    }

    @Test
    public void testEqualsObject() {
        final BigDecimalModelValue value1 = new BigDecimalModelValue(BigDecimal.ONE);
        final BigDecimalModelValue value2 = new BigDecimalModelValue(BigDecimal.ONE);
        final BigDecimalModelValue value3 = new BigDecimalModelValue(BigDecimal.TEN);
        assertEquals(true, value1.equals((Object) value1));
        assertEquals(true, value1.equals((Object) value2));
        assertEquals(true, value2.equals((Object) value1));
        assertEquals(false, value1.equals((Object) value3));
        assertEquals(false, value1.equals((Object) null));
        assertEquals(false, value1.equals("Some String"));
    }

    @Test
    public void testEqualsBigDecimalModelValue() {
        final BigDecimalModelValue value1 = new BigDecimalModelValue(BigDecimal.ONE);
        final BigDecimalModelValue value2 = new BigDecimalModelValue(BigDecimal.ONE);
        final BigDecimalModelValue value3 = new BigDecimalModelValue(BigDecimal.TEN);
        assertEquals(true, value1.equals(value1));
        assertEquals(true, value1.equals(value2));
        assertEquals(true, value2.equals(value1));
        assertEquals(false, value1.equals(value3));
        assertEquals(false, value1.equals(null));
    }

    @Test
    public void testToString() {
        final BigDecimalModelValue value = new BigDecimalModelValue(BigDecimal.ONE);
        assertEquals("big decimal 1", value.toString());
    }

    @Test
    public void testToJSONString() {
        final BigDecimalModelValue value = new BigDecimalModelValue(BigDecimal.ONE);
        assertEquals("1", value.toJSONString(false));
        assertEquals("1", value.toJSONString(true));
    }
}
