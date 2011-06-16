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
