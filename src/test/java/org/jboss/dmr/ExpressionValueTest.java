package org.jboss.dmr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Test;

public class ExpressionValueTest {

    @Test
    public void testExpressionValue() {
        final ExpressionValue value = new ExpressionValue("some expression");
        assertNotNull(value);
        assertEquals(ModelType.EXPRESSION, value.getType());
        assertEquals("some expression", value.asString());
    }

    @Test
    public void testExpressionValueWithBlankExpression() {
        final ExpressionValue value = new ExpressionValue("");
        assertNotNull(value);
        assertEquals(ModelType.EXPRESSION, value.getType());
        assertEquals("", value.asString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpressionValueWithNullExpression() {
        new ExpressionValue(null);
        fail("Expected IllegalArgumentException.");
    }

    @Test
    public void testWriteExternal() {
        // TODO implement test.
    }

    @Test
    public void testAsString() {
        final ExpressionValue value = new ExpressionValue("some expression");
        assertEquals("some expression", value.asString());
    }

    @Test
    public void testFormat() {
        final ExpressionValue value = new ExpressionValue("some expression");
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter, true);
        value.format(writer, 0, false);
        assertEquals("expression \"some expression\"", stringWriter.toString());
    }

    @Test
    public void testFormatAsJSON() {
        final ExpressionValue value = new ExpressionValue("some expression");
        final StringWriter stringWriter1 = new StringWriter();
        final PrintWriter writer1 = new PrintWriter(stringWriter1, true);
        value.formatAsJSON(writer1, 0, false);
        assertEquals("{ \"EXPRESSION_VALUE\" : \"some expression\" }", stringWriter1.toString());

        final StringWriter stringWriter2 = new StringWriter();
        final PrintWriter writer2 = new PrintWriter(stringWriter2, true);
        value.formatAsJSON(writer2, 0, true);
        assertEquals("{\n    \"EXPRESSION_VALUE\" : \"some expression\"\n}", stringWriter2.toString());
    }

    @Test
    public void testEqualsObject() {
        final ExpressionValue value1 = new ExpressionValue("some expression");
        final ExpressionValue value2 = new ExpressionValue("some expression");
        final ExpressionValue value3 = new ExpressionValue("some other expression");

        assertEquals(true, value1.equals((Object) value1));
        assertEquals(true, value1.equals((Object) value2));
        assertEquals(true, value2.equals((Object) value1));
        assertEquals(false, value1.equals((Object) value3));
        assertEquals(false, value3.equals((Object) value1));
        assertEquals(false, value1.equals((Object) null));
        assertEquals(false, value1.equals("some string"));
    }

    @Test
    public void testEqualsExpressionValue() {
        final ExpressionValue value1 = new ExpressionValue("some expression");
        final ExpressionValue value2 = new ExpressionValue("some expression");
        final ExpressionValue value3 = new ExpressionValue("some other expression");

        assertEquals(true, value1.equals(value1));
        assertEquals(true, value1.equals(value2));
        assertEquals(true, value2.equals(value1));
        assertEquals(false, value1.equals(value3));
        assertEquals(false, value3.equals(value1));
        assertEquals(false, value1.equals((ExpressionValue) null));
    }

    @Test
    public void testHashCode() {
        final ExpressionValue value1 = new ExpressionValue("some expression");
        final ExpressionValue value2 = new ExpressionValue("some expression");
        final ExpressionValue value3 = new ExpressionValue("some other expression");

        assertEquals(true, value1.hashCode() == value1.hashCode());
        assertEquals(true, value1.hashCode() == value2.hashCode());
        assertEquals(false, value1.hashCode() == value3.hashCode());
    }

    @Test
    public void testResolve() {
        final ExpressionValue value = new ExpressionValue("some expression");
        assertEquals("some expression", value.resolve().asString());
    }
}
