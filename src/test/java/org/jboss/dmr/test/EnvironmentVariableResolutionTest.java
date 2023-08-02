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

    @Test(expected = IllegalStateException.class)
    public void testLegacyEnviromentVariableCaseSensitiveNotFound() throws Exception {
        ModelNode node = new ModelNode(new ValueExpression("${env.test_environment_variable}"));
        ModelNode result = node.resolve();
    }

    @Test
    public void testLegacyEnviromentVariableCaseSensitive() throws Exception {
        ModelNode node = new ModelNode(new ValueExpression("${env.lower_case_environment_variable}"));
        ModelNode result = node.resolve();
        Assert.assertEquals("Hola mundo", result.asString());
    }
}
