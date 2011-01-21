package org.jboss.dmr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

public class ModelNodeTest {

    @Test
    public void formatAsJSON() {
        final ModelNode node = new ModelNode(new IntModelValue(5));
        final StringBuilder builder = new StringBuilder();
        node.formatAsJSON(builder, 0, false);
        assertEquals("5", builder.toString());
    }

    @Test
    public void testToJSONString() {
        final ModelNode node = new ModelNode();
        node.get("description").set("A managable resource");
        node.get("type").set(ModelType.OBJECT);
        node.get("tail-comment-allowed").set(false);
        node.get("attributes").get("foo").set("some description of foo");
        node.get("attributes").get("bar").set("some description of bar");
        node.get("attributes").get("list").add("value1");
        node.get("attributes").get("list").add("value2");
        node.get("attributes").get("list").add("value3");
        node.get("value-type").get("size").set(ModelType.INT);
        node.get("value-type").get("color").set(ModelType.STRING);
        node.get("big-decimal-value").set(BigDecimal.TEN);
        node.get("big-integer-value").set(BigInteger.TEN);
        node.get("bytes-value").set(new byte[] {(byte)0,(byte)55});
        node.get("double-value").set(Double.valueOf("55"));
        node.get("int-value").set(Integer.valueOf("12"));
        node.get("long-value").set(Long.valueOf("14"));

        final String json = node.toJSONString(false);
        assertNotNull(json);
        assertEquals(true, json.contains("\n"));
        assertEquals(true, json.contains("    "));
        assertThat(json, containsString("\"description\" : \"A managable resource\","));
        assertThat(json, containsString("\"type\" : {\n        \"TYPE_MODEL_VALUE\" : \"OBJECT\"\n    },"));
        assertThat(json, containsString("\"tail-comment-allowed\" : false,"));
        assertThat(json, containsString("\"attributes\" : {"));
        assertThat(json, containsString("\"foo\" : \"some description of foo\","));
        assertThat(json, containsString("\"bar\" : \"some description of bar\","));
        assertThat(json, containsString("\"list\" : ["));
        assertThat(json, containsString("\"value1\","));
        assertThat(json, containsString("\"value2\","));
        assertThat(json, containsString("\"value3\""));
        assertThat(json, containsString("\"value-type\" : {"));
        assertThat(json, containsString("\"size\" : {\n            \"TYPE_MODEL_VALUE\" : \"INT\"\n        },"));
        assertThat(json, containsString("\"color\" : {\n            \"TYPE_MODEL_VALUE\" : \"STRING\"\n        }"));
        assertThat(json, containsString("\"big-decimal-value\" : 10,"));
        assertThat(json, containsString("\"big-integer-value\" : 10,"));
        assertThat(json, containsString("\"bytes-value\" : {\n        \"BYTES_VALUE\" : \"ADc=\"\n    },"));
        assertThat(json, containsString("\"double-value\" : 55.0,"));
        assertThat(json, containsString("\"int-value\" : 12,"));
        assertThat(json, containsString("\"long-value\" : 14"));

        final String compressedJson = node.toJSONString(true);
        assertNotNull(compressedJson);
        assertEquals(false, compressedJson.contains("\n"));
        assertEquals(false, compressedJson.contains("    "));
    }
}
