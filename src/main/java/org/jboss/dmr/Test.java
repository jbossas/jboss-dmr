/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.dmr;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public final class Test {

    private Test() {
    }

    public static void main(String[] args) {
        final ModelNode node = new ModelNode();
        node.get("one").set("StringValue");
        node.get("two").set(BigInteger.TEN);
        node.get("three").set(BigDecimal.ONE);
        node.get("four").add("bean").add("bags").add(123).add(true);
        node.get("five").add("foo");
        node.get("address").add("base", "domain");
        node.get("address").add("profile", "primary");
        node.get("address").add("subsystem", "urn:jboss:subsystem:remoting:1.0");
        node.get("address").add("connector", "ssl");
        node.get("something1").add().get("blah").set("foo");
        node.get("something2").setEmptyObject();
        node.get("something3").get("blah").set("foo");
        node.get("something4").setEmptyList();
        node.get("six").set(ModelType.TYPE);
        System.out.println(node);
    }
}
