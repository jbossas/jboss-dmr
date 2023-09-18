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

public class TypeModelValueTest {

    @Test
    public void testHashCode() {
        assertEquals(true, TypeModelValue.of(ModelType.BIG_DECIMAL).hashCode() == TypeModelValue.of(ModelType.BIG_DECIMAL)
                .hashCode());
        assertEquals(false, TypeModelValue.of(ModelType.BIG_DECIMAL).hashCode() == TypeModelValue.of(ModelType.BIG_INTEGER)
                .hashCode());
    }

    @Test
    public void testAsBoolean() {
        assertEquals(true, TypeModelValue.of(ModelType.BIG_DECIMAL).asBoolean());
        assertEquals(false, TypeModelValue.of(ModelType.UNDEFINED).asBoolean());
    }

    @Test
    public void testAsBooleanBoolean() {
        assertEquals(true, TypeModelValue.of(ModelType.BIG_DECIMAL).asBoolean(false));
        assertEquals(false, TypeModelValue.of(ModelType.UNDEFINED).asBoolean(true));
    }

    @Test
    public void testAsString() {
        assertEquals("BIG_DECIMAL", TypeModelValue.of(ModelType.BIG_DECIMAL).asString());
    }

    @Test
    public void testAsType() {
        assertEquals(ModelType.BIG_DECIMAL, TypeModelValue.of(ModelType.BIG_DECIMAL).asType());
    }

    @Test
    public void testEqualsObject() {
        final TypeModelValue value1 = TypeModelValue.of(ModelType.BIG_DECIMAL);
        final TypeModelValue value2 = TypeModelValue.of(ModelType.BIG_DECIMAL);
        final TypeModelValue value3 = TypeModelValue.of(ModelType.BIG_INTEGER);
        assertEquals(true, value1.equals((Object) value1));
        assertEquals(true, value1.equals((Object) value2));
        assertEquals(true, value2.equals((Object) value1));
        assertEquals(false, value1.equals((Object) value3));
        assertEquals(false, value1.equals((Object) null));
        assertEquals(false, value1.equals("some string"));
    }

    @Test
    public void testFormatAsJSON() {
        final TypeModelValue value = TypeModelValue.of(ModelType.BIG_DECIMAL);
        final StringWriter stringWriter1 = new StringWriter();
        final PrintWriter writer1 = new PrintWriter(stringWriter1, true);
        value.formatAsJSON(writer1, 0, false);
        assertEquals("{ \"TYPE_MODEL_VALUE\" : \"BIG_DECIMAL\" }", stringWriter1.toString());

        final StringWriter stringWriter2 = new StringWriter();
        final PrintWriter writer2 = new PrintWriter(stringWriter2, true);
        value.formatAsJSON(writer2, 0, true);
        assertEquals("{\n    \"TYPE_MODEL_VALUE\" : \"BIG_DECIMAL\"\n}", stringWriter2.toString());
    }

    @Test
    public void testWriteExternal() {
        // TODO implement
    }

    @Test
    public void testOf() {
        for (ModelType type : ModelType.values()) {
            assertEquals(type, TypeModelValue.of(type).asType());
        }
    }

    @Test
    public void testEqualsTypeModelValue() {
        final TypeModelValue value1 = TypeModelValue.of(ModelType.BIG_DECIMAL);
        final TypeModelValue value2 = TypeModelValue.of(ModelType.BIG_DECIMAL);
        final TypeModelValue value3 = TypeModelValue.of(ModelType.BIG_INTEGER);
        assertEquals(true, value1.equals(value1));
        assertEquals(true, value1.equals(value2));
        assertEquals(true, value2.equals(value1));
        assertEquals(false, value1.equals(value3));
        assertEquals(false, value1.equals((TypeModelValue) null));
    }
}
