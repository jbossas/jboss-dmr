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

import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * @author John Bailey
 */
public class PropertyModelValueTest {
        @Test
    public void testCloneNotProtected() {
        final ModelNode model = new ModelNode();
        final ModelNode nested = new ModelNode();
        nested.get("attr").set("test");

        model.set("test", nested);
        model.protect();
        try {
            model.get("test").get("attr2").set("otherValue");
            fail("Should not allow child additions once protected");
        } catch(Exception expected) {
        }

        final ModelNode cloned = model.clone();
        cloned.get("test").set("attr2").set("otherValue"); // Should not fail
    }

}
