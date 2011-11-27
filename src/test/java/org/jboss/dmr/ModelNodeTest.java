package org.jboss.dmr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.matchers.JUnitMatchers.containsString;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

public class ModelNodeTest {

    private ModelNode node;

    @Before
    public void setUp() {
        node = new ModelNode();
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
        node.get("big-decimal-value").set(BigDecimal.valueOf(10.0));
        node.get("big-integer-value").set(BigInteger.TEN);
        node.get("bytes-value").set(new byte[] { (byte) 0, (byte) 55 });
        node.get("double-value").set(Double.valueOf("55"));
        node.get("max-double-value").set(Double.MAX_VALUE);
        node.get("int-value").set(Integer.valueOf("12"));
        node.get("max-int-value").set(Integer.MAX_VALUE);
        node.get("long-value").set(Long.valueOf("14"));
        node.get("max-long-value").set(Long.MAX_VALUE);
        node.get("property-value").set("property", ModelType.PROPERTY);
        node.get("expression-value").setExpression("$expression");
        node.get("true-value").set(true);
        node.get("false-value").set(false);
    }

    @Test
    public void testToString() {
        final String dmrString = node.toString();
        assertThat(dmrString, containsString("\"description\" => \"A managable resource\","));
        assertThat(dmrString, containsString("\"type\" => OBJECT,"));
        assertThat(dmrString, containsString("\"tail-comment-allowed\" => false,"));
        assertThat(
                dmrString,
                containsString("\"attributes\" => {\n        \"foo\" => \"some description of foo\",\n        \"bar\" => \"some description of bar\",\n"));
        assertThat(
                dmrString,
                containsString("\n        \"list\" => [\n            \"value1\",\n            \"value2\",\n            \"value3\"\n        ]\n    },"));
        assertThat(dmrString,
                containsString("\n    \"value-type\" => {\n        \"size\" => INT,\n        \"color\" => STRING\n    },"));
        assertThat(dmrString, containsString("\"big-decimal-value\" => big decimal 10.0,"));
        assertThat(dmrString, containsString("\"big-integer-value\" => big integer 10,"));
        assertThat(dmrString, containsString("\"bytes-value\" => bytes {\n        0x00, 0x37\n    },"));
        assertThat(dmrString, containsString("\"double-value\" => 55.0,"));
        assertThat(dmrString, containsString("\"max-double-value\" => 1.7976931348623157E308,"));
        assertThat(dmrString, containsString("\"int-value\" => 12,"));
        assertThat(dmrString, containsString("\"max-int-value\" => 2147483647,"));
        assertThat(dmrString, containsString("\"long-value\" => 14L,"));
        assertThat(dmrString, containsString("\"max-long-value\" => 9223372036854775807L,"));
        assertThat(dmrString, containsString("\"property-value\" => (\"property\" => PROPERTY),"));
        assertThat(dmrString, containsString("\"expression-value\" => expression \"$expression\","));
        assertThat(dmrString, containsString("\"true-value\" => true,"));
        assertThat(dmrString, containsString("\"false-value\" => false"));
    }

    @Test
    public void testOutputDMRString() {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter, true);
        node.writeString(writer, false);
        final String dmrString = stringWriter.toString();
        assertThat(dmrString, containsString("\"description\" => \"A managable resource\","));
        assertThat(dmrString, containsString("\"type\" => OBJECT,"));
        assertThat(dmrString, containsString("\"tail-comment-allowed\" => false,"));
        assertThat(
                dmrString,
                containsString("\"attributes\" => {\n        \"foo\" => \"some description of foo\",\n        \"bar\" => \"some description of bar\",\n"));
        assertThat(
                dmrString,
                containsString("\n        \"list\" => [\n            \"value1\",\n            \"value2\",\n            \"value3\"\n        ]\n    },"));
        assertThat(dmrString,
                containsString("\n    \"value-type\" => {\n        \"size\" => INT,\n        \"color\" => STRING\n    },"));
        assertThat(dmrString, containsString("\"big-decimal-value\" => big decimal 10.0,"));
        assertThat(dmrString, containsString("\"big-integer-value\" => big integer 10,"));
        assertThat(dmrString, containsString("\"bytes-value\" => bytes {\n        0x00, 0x37\n    },"));
        assertThat(dmrString, containsString("\"double-value\" => 55.0,"));
        assertThat(dmrString, containsString("\"max-double-value\" => 1.7976931348623157E308,"));
        assertThat(dmrString, containsString("\"int-value\" => 12,"));
        assertThat(dmrString, containsString("\"max-int-value\" => 2147483647,"));
        assertThat(dmrString, containsString("\"long-value\" => 14L,"));
        assertThat(dmrString, containsString("\"max-long-value\" => 9223372036854775807L,"));
        assertThat(dmrString, containsString("\"property-value\" => (\"property\" => PROPERTY),"));
        assertThat(dmrString, containsString("\"expression-value\" => expression \"$expression\","));
        assertThat(dmrString, containsString("\"true-value\" => true,"));
        assertThat(dmrString, containsString("\"false-value\" => false"));
    }

    @Test
    public void testFormatAsJSON() {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter, true);
        node.formatAsJSON(writer, 0, true);
        final String json = stringWriter.toString();
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
        assertThat(json, containsString("\"big-decimal-value\" : 10.0,"));
        assertThat(json, containsString("\"big-integer-value\" : 10,"));
        assertThat(json, containsString("\"bytes-value\" : {\n        \"BYTES_VALUE\" : \"ADc=\"\n    },"));
        assertThat(json, containsString("\"double-value\" : 55.0,"));
        assertThat(json, containsString("\"max-double-value\" : 1.7976931348623157E308,"));
        assertThat(json, containsString("\"int-value\" : 12,"));
        assertThat(json, containsString("\"max-int-value\" : 2147483647,"));
        assertThat(json, containsString("\"long-value\" : 14"));
        assertThat(json, containsString("\"max-long-value\" : 9223372036854775807"));
        assertThat(
                json,
                containsString("\n    \"property-value\" : {\n        \"property\" : {\n            \"TYPE_MODEL_VALUE\" : \"PROPERTY\"\n        }\n    },"));
        assertThat(json, containsString("\"expression-value\" : {\n        \"EXPRESSION_VALUE\" : \"$expression\"\n    }"));
        assertThat(json, containsString("\"true-value\" : true"));
        assertThat(json, containsString("\"false-value\" : false"));
        assertThat(json, containsString("\n}"));

        final String compressedJson = node.toJSONString(true);
        assertNotNull(compressedJson);
        assertEquals(false, compressedJson.contains("\n"));
        assertEquals(false, compressedJson.contains("    "));
    }

    @Test
    public void testOutputJSONString() {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter, true);
        node.writeJSONString(writer, false);
        final String json = stringWriter.toString();
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
        assertThat(json, containsString("\"big-decimal-value\" : 10.0,"));
        assertThat(json, containsString("\"big-integer-value\" : 10,"));
        assertThat(json, containsString("\"bytes-value\" : {\n        \"BYTES_VALUE\" : \"ADc=\"\n    },"));
        assertThat(json, containsString("\"double-value\" : 55.0,"));
        assertThat(json, containsString("\"max-double-value\" : 1.7976931348623157E308,"));
        assertThat(json, containsString("\"int-value\" : 12,"));
        assertThat(json, containsString("\"max-int-value\" : 2147483647,"));
        assertThat(json, containsString("\"long-value\" : 14"));
        assertThat(json, containsString("\"max-long-value\" : 9223372036854775807"));
        assertThat(
                json,
                containsString("\n    \"property-value\" : {\n        \"property\" : {\n            \"TYPE_MODEL_VALUE\" : \"PROPERTY\"\n        }\n    },"));
        assertThat(json, containsString("\"expression-value\" : {\n        \"EXPRESSION_VALUE\" : \"$expression\"\n    }"));
        assertThat(json, containsString("\"true-value\" : true"));
        assertThat(json, containsString("\"false-value\" : false"));
        assertThat(json, containsString("\n}"));

        final String compressedJson = node.toJSONString(true);
        assertNotNull(compressedJson);
        assertEquals(false, compressedJson.contains("\n"));
        assertEquals(false, compressedJson.contains("    "));
    }

    @Test
    public void testToJSONString() {
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
        assertThat(json, containsString("\"big-decimal-value\" : 10.0,"));
        assertThat(json, containsString("\"big-integer-value\" : 10,"));
        assertThat(json, containsString("\"bytes-value\" : {\n        \"BYTES_VALUE\" : \"ADc=\"\n    },"));
        assertThat(json, containsString("\"double-value\" : 55.0,"));
        assertThat(json, containsString("\"max-double-value\" : 1.7976931348623157E308,"));
        assertThat(json, containsString("\"int-value\" : 12,"));
        assertThat(json, containsString("\"max-int-value\" : 2147483647,"));
        assertThat(json, containsString("\"long-value\" : 14"));
        assertThat(json, containsString("\"max-long-value\" : 9223372036854775807"));
        assertThat(
                json,
                containsString("\n    \"property-value\" : {\n        \"property\" : {\n            \"TYPE_MODEL_VALUE\" : \"PROPERTY\"\n        }\n    },"));
        assertThat(json, containsString("\"expression-value\" : {\n        \"EXPRESSION_VALUE\" : \"$expression\"\n    }"));
        assertThat(json, containsString("\"true-value\" : true"));
        assertThat(json, containsString("\"false-value\" : false"));
        assertThat(json, containsString("\n}"));

        final String compressedJson = node.toJSONString(true);
        assertNotNull(compressedJson);
        assertEquals(false, compressedJson.contains("\n"));
        assertEquals(false, compressedJson.contains("    "));
    }

    @Test
    public void testFromJSONString() {
        final ModelNode parsedNode = ModelNode.fromJSONString(node.toJSONString(true));
        // TODO why not a simple equality check?
        assertThat(parsedNode.toString(), containsString("\"description\" => \"A managable resource\","));
        assertThat(parsedNode.toString(), containsString("\"type\" => OBJECT,"));
        assertThat(parsedNode.toString(), containsString("\"tail-comment-allowed\" => false,"));
        assertThat(
                parsedNode.toString(),
                containsString("\"attributes\" => {\n        \"foo\" => \"some description of foo\",\n        \"bar\" => \"some description of bar\",\n"));
        assertThat(
                parsedNode.toString(),
                containsString("\n        \"list\" => [\n            \"value1\",\n            \"value2\",\n            \"value3\"\n        ]\n    },"));
        assertThat(parsedNode.toString(),
                containsString("\n    \"value-type\" => {\n        \"size\" => INT,\n        \"color\" => STRING\n    },"));
        assertThat(parsedNode.toString(), containsString("\"big-decimal-value\" => big decimal 10.0,"));
        assertThat(parsedNode.toString(), containsString("\"big-integer-value\" => big integer 10,"));
        assertThat(parsedNode.toString(), containsString("\"bytes-value\" => bytes {\n        0x00, 0x37\n    },"));
        assertThat(parsedNode.toString(), containsString("\"double-value\" => big decimal 55.0,"));
        assertThat(parsedNode.toString(), containsString("\"max-double-value\" => big decimal 1.7976931348623157E+308,"));
        assertThat(parsedNode.toString(), containsString("\"int-value\" => big integer 12,"));
        assertThat(parsedNode.toString(), containsString("\"max-int-value\" => big integer 2147483647,"));
        assertThat(parsedNode.toString(), containsString("\"long-value\" => big integer 14,"));
        assertThat(parsedNode.toString(), containsString("\"max-long-value\" => big integer 9223372036854775807,"));
        assertThat(parsedNode.toString(), containsString("\"property-value\" => {\"property\" => PROPERTY},"));
        assertThat(parsedNode.toString(), containsString("\"expression-value\" => expression \"$expression\","));
        assertThat(parsedNode.toString(), containsString("\"true-value\" => true,"));
        assertThat(parsedNode.toString(), containsString("\"false-value\" => false"));
    }

    @Test
    public void testFromString() {
        final ModelNode parsedNode = ModelNode.fromString(node.toString());
        assertEquals(node, parsedNode);
    }

    @Test
    public void testWriteBase64() {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            node.writeBase64(os);
            assertEquals(701, os.toByteArray().length);
        } catch (final IOException e) {
            fail("IOException not expected: " + e.getMessage());
        }
    }

    @Test
    public void testFromBase64() {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            node.writeBase64(os);
            ModelNode newNode = ModelNode.fromBase64(new ByteArrayInputStream(os.toByteArray()));
            assertNotNull(newNode);
            assertEquals(node, newNode);
        } catch (final IOException e) {
            fail("IOException not expected: " + e.getMessage());
        }
    }
}
