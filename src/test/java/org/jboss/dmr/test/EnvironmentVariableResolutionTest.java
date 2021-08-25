package org.jboss.dmr.test;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ValueExpression;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:kabir.khan@jboss.com">Kabir Khan</a>
 */
public class EnvironmentVariableResolutionTest {

    private ValueExpression expr = new ValueExpression("${test.environment-variable}");

    @Test
    public void testEnviromentVariable() throws Exception {
        ModelNode node = new ModelNode(expr);
        ModelNode result = node.resolve();
        Assert.assertEquals("Hello world", result.asString());
    }

    @Test
    public void testEnviromentVariableOverriddenBySystemProperty() throws Exception {
        try {
            System.setProperty("test.environment-variable", "Hola mundo");
            ModelNode node = new ModelNode(expr);
            ModelNode result = node.resolve();
            Assert.assertEquals("Hola mundo", result.asString());
        } finally {
            System.clearProperty("test.environment-variable");
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testNoEnvironmentVariableOrSystemProperty() throws Exception {
        ModelNode node = new ModelNode(new ValueExpression("${test.environment-variable.non-existent}"));
        ModelNode result = node.resolve();
    }

    @Test
    public void testLegacyEnviromentVariable() throws Exception {
        ModelNode node = new ModelNode(new ValueExpression("${env.TEST_ENVIRONMENT_VARIABLE}"));
        ModelNode result = node.resolve();
        Assert.assertEquals("Hello world", result.asString());
    }
}
