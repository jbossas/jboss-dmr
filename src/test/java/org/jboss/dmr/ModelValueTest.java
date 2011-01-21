package org.jboss.dmr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ModelValueTest {

    @Test
    public void testJsonEscape() {
        assertEquals("\"some string\"", ModelValue.jsonEscape("some string"));
        assertEquals("\"A string with a \\\" in it.\"", ModelValue.jsonEscape("A string with a \" in it."));
        assertEquals("\"A string with a \\b in it.\"", ModelValue.jsonEscape("A string with a \b in it."));
        assertEquals("\"A string with a \\f in it.\"", ModelValue.jsonEscape("A string with a \f in it."));
        assertEquals("\"A string with a \\n in it.\"", ModelValue.jsonEscape("A string with a \n in it."));
        assertEquals("\"A string with a \\r in it.\"", ModelValue.jsonEscape("A string with a \r in it."));
        assertEquals("\"A string with a \\t in it.\"", ModelValue.jsonEscape("A string with a \t in it."));
        assertEquals("\"A string with a \\/ in it.\"", ModelValue.jsonEscape("A string with a / in it."));
        assertEquals("\"A string with unicode characters: \\u0000\\u0001\\u001F \\u007F\\u0080\\u009F\\u2000\\u2001\\u20FF\"",
                ModelValue.jsonEscape("A string with unicode characters: \u0000\u0001\u001F\u0020\u007F\u0080\u009F\u2000\u2001\u20FF"));
    }

    @Test
    public void testFormatAsJSON() {
        final ModelValue value = new IntModelValue(5);
        final StringBuilder builder = new StringBuilder();
        value.formatAsJSON(builder, 0, false);
        assertEquals("5", builder.toString());
    }

    @Test
    public void testToJSONString() {
        final ModelValue value = new IntModelValue(5);
        assertEquals("5", value.toJSONString(false));
        assertEquals("5", value.toJSONString(true));
    }
}
