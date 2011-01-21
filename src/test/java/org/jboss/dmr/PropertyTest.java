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
