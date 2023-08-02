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
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class PropertyTest {

    @Test
    public void testPropertyStringModelNode() {
        final Property property = new Property("test", new ModelNode());
        assertNotNull(property);
        assertEquals("test", property.getName());
    }

    @Test
    public void testPropertyStringModelNodeBoolean() {
        final Property property1 = new Property("test", new ModelNode(), false);
        assertNotNull(property1);
        assertEquals("test", property1.getName());

        final Property property2 = new Property("test", new ModelNode(), true);
        assertNotNull(property2);
        assertEquals("test", property2.getName());
    }

    @Test
    public void testGetName() {
        final Property property = new Property("test", new ModelNode());
        assertNotNull(property);
        assertEquals("test", property.getName());
    }

    @Test
    public void testGetValue() {
        final Property property = new Property("test", new ModelNode(new IntModelValue(5)));
        assertNotNull(property);
        assertEquals(5, property.getValue().asInt());
    }

    @Test
    public void testClone() {
        final Property property = new Property("test", new ModelNode(new IntModelValue(5)));
        assertNotNull(property);
        final Property clone = property.clone();
        assertEquals(false, clone == property);
        assertEquals(clone.getName(), property.getName());
        assertEquals(clone.getValue().asInt(), property.getValue().asInt());
    }
}
