package org.jboss.dmr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

import org.junit.Assert;
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
        new ExpressionValue((String)null);
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

    /**
     * Test that a valid expression to a system property reference which has
     * no definition throws an ISE
     */
    @Test(expected = IllegalStateException.class)
    public void testUnresolvedReference() {
        final ExpressionValue value = new ExpressionValue("${no-such-system-property}");
        String resolved = value.resolve().asString();
        fail("Did not fail with ISE: "+resolved);
    }

    /**
     * Test that a incomplete expression to a system property reference throws an ISE
     */
    @Test(expected = IllegalStateException.class)
    public void testIncompleteReference() {
        System.setProperty("test.property1", "test.property1.value");
        try {
            final ExpressionValue value = new ExpressionValue("${test.property1");
            String resolved = value.resolve().asString();
            fail("Did not fail with ISE: " + resolved);
        } finally {
            System.clearProperty("test.property1");
        }
    }

    /**
     * Test that an incomplete expression is ignored if escaped
     */
    @Test
    public void testEscapedIncompleteReference() {
        final ExpressionValue value = new ExpressionValue("$${test.property1");
        assertEquals("${test.property1", value.resolve().asString());
    }

    /**
     * Test that a incomplete expression to a system property reference throws an ISE
     */
    @Test(expected = IllegalStateException.class)
    public void testIncompleteReferenceFollowingSuccessfulResolve() {
        System.setProperty("test.property1", "test.property1.value");
        try {
            final ExpressionValue value = new ExpressionValue("${test.property1} ${test.property1");
            String resolved = value.resolve().asString();
            fail("Did not fail with ISE: "+resolved);
        } finally {
            System.clearProperty("test.property1");
        }
    }

    /**
     * Validate a single system property expression sees the system property value.
     */
    @Test
    public void testSystemPropertyRef() {
        System.setProperty("test.property1", "test.property1.value");
        try {
            final ExpressionValue value = new ExpressionValue("${test.property1}");
            assertEquals("test.property1.value", value.resolve().asString());
        } finally {
            System.clearProperty("test.property1");
        }
    }

    /**
     * Test an expression that contains more than one system property name to
     * see that the second property value is used when the first property
     * is not defined.
     */
    @Test
    public void testSystemPropertyRefs() {
        System.setProperty("test.property2", "test.property2.value");
        try {
            final ExpressionValue value = new ExpressionValue("${test.property1,test.property2}");
            assertEquals("test.property2.value", value.resolve().asString());
        } finally {
            System.clearProperty("test.property2");
        }
    }

    /**
     * Validate that a system property expression for a property with no value
     * and a default provides sees the default value.
     */
    @Test
    public void testSystemPropertyRefDefault() {
        final ExpressionValue value = new ExpressionValue("${test.property2:test.property2.default.value}");
        assertEquals("test.property2.default.value", value.resolve().asString());
    }

    /**
     * Validate that a environment variable reference is resolved.
     */
    @Test
    public void testSystemEnvVarRef() {
        // Since we cannot set ENV vars from java, grab first one
        String[] envvar = findEnvVar();
        if (envvar[0].length() == 0) {
            System.err.println("No environment variables found, skipping test");
            return;
        }
        final String envvarValue = envvar[1];
        Assert.assertNotNull("Expect non-null env var: "+envvar[0], envvarValue);
        final ExpressionValue value = new ExpressionValue("${"+envvar[0]+"}");
        assertEquals(envvarValue, value.resolve().asString());
    }
    /**
     * Validate that a environment variable reference is overriden by a
     * system property of the same name prefixed with "env.".
     */
    @Test
    public void testSystemEnvVarRefOverride() {
        // Since we cannot set ENV vars from java, grab first one
        String[] envvar = findEnvVar();
        if (envvar[0].length() == 0) {
            System.err.println("No environment variables found, skipping test");
            return;
        }
        // Override the var
        String sysPropName = envvar[0];
        String overrideValue = sysPropName+"-override";
        try {
            System.setProperty(sysPropName, overrideValue);
            final String envvarValue = envvar[1];
            Assert.assertNotNull("Expect non-null env var: "+envvar[0], envvarValue);
            final ExpressionValue value = new ExpressionValue("${"+envvar[0]+"}");
            assertEquals(overrideValue, value.resolve().asString());
        } finally {
            System.clearProperty(sysPropName);
        }
    }

    /**
     * Make sure nesting works right
     */
    @Test
    public void testNesting() {
        assertEquals("{blah}", new ValueExpression("${resolves.to.nothing:{blah}}").resolveString());
        assertEquals("{blah}", new ValueExpression("${resolves.to.nothing,also.resolves.to.nothing:{blah}}").resolveString());
        assertEquals(System.getProperty("os.name"), new ValueExpression("${os.name:{blah}}").resolveString());
        assertEquals("{{fo{o}oo}}", new ValueExpression("${resolves.to.nothing:{{fo{o}oo}}}").resolveString());
        assertEquals("blah{{fo{o}oo}}blah", new ValueExpression("${resolves.to.nothing:blah{{fo{o}oo}}}blah").resolveString());
    }

    @Test
    public void testFileSeparator() {
        assertEquals(File.separator, new ValueExpression("${/}").resolveString());
        assertEquals(File.separator + "a", new ValueExpression("${/}a").resolveString());
        assertEquals("a" + File.separator, new ValueExpression("a${/}").resolveString());
    }

    @Test
    public void testPathSeparator() {
        assertEquals(File.pathSeparator, new ValueExpression("${:}").resolveString());
        assertEquals(File.pathSeparator + "a", new ValueExpression("${:}a").resolveString());
        assertEquals("a" + File.pathSeparator, new ValueExpression("a${:}").resolveString());
    }

    @Test
    public void testRecursiveExpression() {
        System.setProperty("org.jbpm.designer.perspective", "${org.jbpm.designer.perspective:full}");
        try {
        String resolved =  new ValueExpression("${org.jbpm.designer.perspective:full}").resolveString();
        assertEquals("full", resolved);
        } finally {
            System.clearProperty("org.jbpm.designer.perspective");
        }
    }
    /**
     * Find the first defined System.getenv() environment variable with a non-zero length value.
     * @return [0] = env var name prefixed with "env."
     *  [1] = env var value. If [0].length == 0, then there
     *  were no environment variables defined.
     */
    private static String[] findEnvVar() {
        String[] pair = {"", null};
        Collection<String> envvars = System.getenv().keySet();
        if (envvars.isEmpty()) {
            return pair;
        }
        for(final String envvar : envvars) {
            final String envvarValue = System.getenv(envvar);
            if (envvarValue != null && envvarValue.length() > 0) {
                // Change name to env.name
                pair[0] = "env." + envvar;
                pair[1] = envvarValue;
                break;
            }
        }
        return pair;
    }
}
