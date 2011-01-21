package org.jboss.dmr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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

    @Test(expected=IllegalArgumentException.class)
    public void testExpressionValueWithNullExpression() {
        new ExpressionValue(null);
        fail("Expected IllegalArgumentException.");
    }

    @Test
    public void testWriteExternal() {
        //TODO implement test.
    }

    @Test
    public void testAsString() {
        final ExpressionValue value = new ExpressionValue("some expression");
        assertEquals("some expression", value.asString());
    }

    @Test
    public void testFormat() {
        final ExpressionValue value = new ExpressionValue("some expression");
        final StringBuilder builder = new StringBuilder();
        value.format(builder, 0, false);
        assertEquals("expression \"some expression\"", builder.toString());
    }

    @Test
    public void testFormatAsJSON() {
        final ExpressionValue value = new ExpressionValue("some expression");
        final StringBuilder builder1 = new StringBuilder();
        value.formatAsJSON(builder1, 0, false);
        assertEquals("{ \"EXPRESSION_VALUE\" : \"some expression\" }",builder1.toString());

        final StringBuilder builder2 = new StringBuilder();
        value.formatAsJSON(builder2, 0, true);
        assertEquals("{\n    \"EXPRESSION_VALUE\" : \"some expression\"\n}",builder2.toString());
    }

    @Test
    public void testEqualsObject() {
        final ExpressionValue value1 = new ExpressionValue("some expression");
        final ExpressionValue value2 = new ExpressionValue("some expression");
        final ExpressionValue value3 = new ExpressionValue("some other expression");

        assertEquals(true, value1.equals((Object)value1));
        assertEquals(true, value1.equals((Object)value2));
        assertEquals(true, value2.equals((Object)value1));
        assertEquals(false, value1.equals((Object)value3));
        assertEquals(false, value3.equals((Object)value1));
        assertEquals(false, value1.equals((Object)null));
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
        assertEquals(false, value1.equals((ExpressionValue)null));
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
