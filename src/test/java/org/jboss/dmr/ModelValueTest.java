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

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Test;

public class ModelValueTest {

    @Test
    public void testToString() {
        final ModelValue value = new IntModelValue(5);
        assertEquals("5", value.toString());
    }

    @Test
    public void testOutputDMRString() {
        final ModelValue value = new IntModelValue(5);
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter, true);
        value.writeString(writer, false);
        assertEquals("5", stringWriter.toString());
    }

    @Test
    public void testJsonEscape() {
        assertEquals("\"some string\"", ModelValue.jsonEscape("some string"));
        assertEquals("\"A string with a \\\" in it.\"", ModelValue.jsonEscape("A string with a \" in it."));
        assertEquals("\"A string with a \\b in it.\"", ModelValue.jsonEscape("A string with a \b in it."));
        assertEquals("\"A string with a \\f in it.\"", ModelValue.jsonEscape("A string with a \f in it."));
        assertEquals("\"A string with a \\n in it.\"", ModelValue.jsonEscape("A string with a \n in it."));
        assertEquals("\"A string with a \\r in it.\"", ModelValue.jsonEscape("A string with a \r in it."));
        assertEquals("\"A string with a \\t in it.\"", ModelValue.jsonEscape("A string with a \t in it."));
        assertEquals(
                "\"A string with unicode characters: \\u0000\\u0001\\u001F \u007F\u0080\u009F\u2000\u2001\u20FF\"",
                ModelValue
                        .jsonEscape("A string with unicode characters: \u0000\u0001\u001F\u0020\u007F\u0080\u009F\u2000\u2001\u20FF"));
    }

    @Test
    public void testFormatAsJSON() {
        final ModelValue value = new IntModelValue(5);
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter, true);
        value.formatAsJSON(writer, 0, false);
        assertEquals("5", stringWriter.toString());
    }

    @Test
    public void testToJSONString() {
        final ModelValue value = new IntModelValue(5);
        assertEquals("5", value.toJSONString(false));
        assertEquals("5", value.toJSONString(true));
    }

    @Test
    public void testOutputJSONString() {
        final ModelValue value = new IntModelValue(5);
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter, true);
        value.writeJSONString(writer, true);
        assertEquals("5", stringWriter.toString());
    }
}
