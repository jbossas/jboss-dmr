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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
final class PropertyModelValue extends ModelValue {

    private final Property property;

    PropertyModelValue(final String name, final ModelNode value) {
        this(new Property(name, value));
    }

    PropertyModelValue(final Property property) {
        super(ModelType.PROPERTY);
        this.property = property;
    }

    PropertyModelValue(final DataInput in) throws IOException {
        super(ModelType.PROPERTY);
        final ModelNode node = new ModelNode();
        final String name = in.readUTF();
        node.readExternal(in);
        property = new Property(name, node);
    }

    void writeExternal(final DataOutput out) throws IOException {
        out.writeUTF(property.getName());
        property.getValue().writeExternal(out);
    }

    String asString() {
        return String.format("(%s => %s)", quote(property.getName()), property.getValue());
    }

    Property asProperty() {
        return property;
    }

    List<Property> asPropertyList() {
        return Collections.singletonList(property);
    }

    ModelNode asObject() {
        final ModelNode node = new ModelNode();
        node.get(property.getName()).set(property.getValue());
        return node;
    }

    Set<String> getKeys() {
        return Collections.singleton(property.getName());
    }

    List<ModelNode> asList() {
        return Collections.singletonList(property.getValue());
    }

    ModelNode getChild(final String name) {
        return property.getName().equals(property.getName()) ? property.getValue() : super.getChild(name);
    }

    ModelNode getChild(final int index) {
        return index == 0 ? property.getValue() : super.getChild(index);
    }

    ModelValue copy() {
        return new PropertyModelValue(property.getName(), property.getValue());
    }

    ModelValue resolve() {
        return new PropertyModelValue(property.getName(), property.getValue().resolve());
    }

    public boolean equals(Object other) {
        return other instanceof PropertyModelValue && equals((PropertyModelValue)other);
    }

    public boolean equals(PropertyModelValue other) {
        return this == other || other != null && other.property.getName().equals(property.getName()) && other.property.getValue().equals(property.getValue());
    }

    public int hashCode() {
        return property.getName().hashCode() * 31 + property.getValue().hashCode();
    }
}
