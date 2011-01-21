package org.jboss.dmr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class ListModelValueTest {

    @Test
    public void testListModelValue() {
        final ListModelValue value = new ListModelValue();
        assertNotNull(value);
        assertEquals(ModelType.LIST, value.getType());
    }

    @Test
    public void testListModelValueList() {
        final List<ModelNode> list = new ArrayList<ModelNode>();
        final ListModelValue value = new ListModelValue(list);
        assertNotNull(value);
        assertEquals(ModelType.LIST, value.getType());
    }

    @Test
    public void testListModelValueDataInput() {
        //TODO implement test
    }

    @Test
    public void testWriteExternal() {
        //TODO implement test
    }

    @Test
    public void testFormatAsJSON() {
        final List<ModelNode> list = new ArrayList<ModelNode>();
        list.add(new ModelNode(new IntModelValue(5)));
        list.add(new ModelNode(new IntModelValue(6)));
        list.add(new ModelNode(new IntModelValue(7)));
        final ListModelValue value = new ListModelValue(list);

        final StringBuilder builder1 = new StringBuilder();
        value.formatAsJSON(builder1, 0, false);
        assertEquals("[5,6,7]", builder1.toString());

        final StringBuilder builder2 = new StringBuilder();
        value.formatAsJSON(builder2, 0, true);
        assertEquals("[\n    5,\n    6,\n    7\n]", builder2.toString());
    }

}
