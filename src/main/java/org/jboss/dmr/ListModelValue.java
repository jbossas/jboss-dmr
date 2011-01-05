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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
final class ListModelValue extends ModelValue {

    public static final ModelNode[] NO_NODES = new ModelNode[0];
    private final List<ModelNode> list;

    ListModelValue() {
        super(ModelType.LIST);
        list = new ArrayList<ModelNode>();
    }

    private ListModelValue(ListModelValue orig) {
        super(ModelType.LIST);
        list = new ArrayList<ModelNode>(orig.list);
    }

    ListModelValue(final List<ModelNode> list) {
        super(ModelType.LIST);
        this.list = list;
    }

    ModelValue protect() {
        List<ModelNode> list = this.list;
        return list.getClass() == ArrayList.class ? new ListModelValue(Collections.unmodifiableList(list)) : this;
    }

    long asLong() {
        return asInt();
    }

    long asLong(final long defVal) {
        return asInt();
    }

    int asInt() {
        return list.size();
    }

    int asInt(final int defVal) {
        return asInt();
    }

    boolean asBoolean() {
        return ! list.isEmpty();
    }

    boolean asBoolean(final boolean defVal) {
        return asBoolean();
    }

    Property asProperty() {
        if (list.size() == 2) {
            return new Property(list.get(0).asString(), list.get(1));
        } else {
            return super.asProperty();
        }
    }

    List<Property> asPropertyList() {
        final List<Property> propertyList = new ArrayList<Property>();
        Iterator<ModelNode> i = list.iterator();
        while (i.hasNext()) {
            final ModelNode name = i.next();
            if (i.hasNext()) {
                final ModelNode value = i.next();
                propertyList.add(new Property(name.asString(), value));
            }
        }
        return propertyList;
    }

    ModelNode asObject() {
        final ModelNode node = new ModelNode();
        Iterator<ModelNode> i = list.iterator();
        while (i.hasNext()) {
            final ModelNode name = i.next();
            if (name.getType() == ModelType.PROPERTY) {
                final Property property = name.asProperty();
                node.get(property.getName()).set(property.getValue());
            } else if (i.hasNext()) {
                final ModelNode value = i.next();
                node.get(name.asString()).set(value);
            }
        }
        return node;
    }

    ModelNode getChild(final int index) {
        return list.get(index);
    }

    ModelNode addChild() {
        ModelNode node = new ModelNode();
        list.add(node);
        return node;
    }

    List<ModelNode> asList() {
        //noinspection ToArrayCallWithZeroLengthArrayArgument
        return Collections.unmodifiableList(Arrays.asList(list.toArray(NO_NODES)));
    }

    ModelValue copy() {
        return new ListModelValue(this);
    }

    ModelValue resolve() {
        final ArrayList<ModelNode> copy = new ArrayList<ModelNode>(list.size());
        for (ModelNode node : list) {
            copy.add(node.resolve());
        }
        return new ListModelValue(copy);
    }

    String asString() {
        StringBuilder builder = new StringBuilder();
        format(builder, 0, false);
        return builder.toString();
    }

    void format(final StringBuilder builder, final int indent, final boolean multiLineRequested) {
        final boolean multiLine = multiLineRequested && list.size() > 1;
        final List<ModelNode> list = asList();
        final Iterator<ModelNode> iterator = list.iterator();
        builder.append('[');
        if (multiLine) {
            indent(builder.append('\n'), indent + 1);
        }
        while (iterator.hasNext()) {
            final ModelNode entry = iterator.next();
            entry.format(builder, indent + 1, multiLineRequested);
            if (iterator.hasNext()) {
                if (multiLine) {
                    indent(builder.append(",\n"), indent + 1);
                } else {
                    builder.append(',');
                }
            }
        }
        if (multiLine) {
            indent(builder.append('\n'), indent);
        }
        builder.append(']');
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(Object other) {
        return other instanceof ListModelValue && equals((ListModelValue)other);
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(ListModelValue other) {
        return this == other || other != null && list.equals(other.list);
    }

    public int hashCode() {
        return list.hashCode();
    }
}
