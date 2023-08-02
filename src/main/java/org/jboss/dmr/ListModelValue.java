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

import org.jboss.dmr.stream.ModelException;
import org.jboss.dmr.stream.ModelWriter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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

    private ListModelValue(final ListModelValue orig) {
        super(ModelType.LIST);
        list = new ArrayList<ModelNode>(orig.list);
    }

    ListModelValue(final List<ModelNode> list) {
        super(ModelType.LIST);
        this.list = list;
    }

    ListModelValue(final DataInput in) throws IOException {
        super(ModelType.LIST);
        final int count = in.readInt();
        final ArrayList<ModelNode> list = new ArrayList<ModelNode>();
        for (int i = 0; i < count; i++) {
            final ModelNode value = new ModelNode();
            value.readExternal(in);
            list.add(value);
        }
        this.list = list;
    }

    @Override
    void writeExternal(final DataOutput out) throws IOException {
        out.write(ModelType.LIST.typeChar);
        final List<ModelNode> list = this.list;
        final int size = list.size();
        out.writeInt(size);
        for (final ModelNode node : list) {
            node.writeExternal(out);
        }
    }

    @Override
    ModelValue protect() {
        final List<ModelNode> list = this.list;
        for (final ModelNode node : list) {
            node.protect();
        }
        return list.getClass() == ArrayList.class ? new ListModelValue(Collections.unmodifiableList(list)) : this;
    }

    @Override
    long asLong() {
        return asInt();
    }

    @Override
    long asLong(final long defVal) {
        return asInt();
    }

    @Override
    int asInt() {
        return list.size();
    }

    @Override
    int asInt(final int defVal) {
        return asInt();
    }

    @Override
    boolean asBoolean() {
        return !list.isEmpty();
    }

    @Override
    boolean asBoolean(final boolean defVal) {
        return asBoolean();
    }

    @Override
    Property asProperty() {
        if (list.size() == 2) {
            return new Property(list.get(0).asString(), list.get(1));
        } else {
            return super.asProperty();
        }
    }

    @Override
    List<Property> asPropertyList() {
        final List<Property> propertyList = new ArrayList<Property>();
        final Iterator<ModelNode> i = list.iterator();
        while (i.hasNext()) {
            final ModelNode node = i.next();
            if (node.getType() == ModelType.PROPERTY || node.getType() == ModelType.OBJECT) {
                propertyList.add(node.asProperty());
            } else if (i.hasNext()) {
                final ModelNode value = i.next();
                propertyList.add(new Property(node.asString(), value));
            } else {
                return super.asPropertyList();
            }
        }
        return propertyList;
    }

    @Override
    ModelNode asObject() {
        final ModelNode node = new ModelNode();
        final Iterator<ModelNode> i = list.iterator();
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

    @Override
    ModelNode getChild(final int index) {
        final List<ModelNode> list = this.list;
        final int size = list.size();
        if (size <= index) {
            for (int i = 0; i < index - size + 1; i++) {
                list.add(new ModelNode());
            }
        }
        return list.get(index);
    }

    @Override
    ModelNode addChild() {
        final ModelNode node = new ModelNode();
        list.add(node);
        return node;
    }

    @Override
    ModelNode insertChild(int index) {
        final ModelNode node = new ModelNode();
        list.add(index, node);
        return node;
    }

    @Override
    ModelNode removeChild(int index) {
        requireChild(index);
        return list.remove(index);
    }

    @Override
    List<ModelNode> asList() {
        return Collections.unmodifiableList(list);
    }

    @Override
    ModelValue copy() {
        final List<ModelNode> list = this.list;
        final List<ModelNode> clonedValues = new ArrayList<ModelNode>(list.size());
        for(ModelNode node : list) {
            clonedValues.add(node.clone());
        }
        return new ListModelValue(clonedValues);
    }

    @Override
    ModelValue resolve() {
        final ArrayList<ModelNode> copy = new ArrayList<ModelNode>(list.size());
        for (final ModelNode node : list) {
            copy.add(node.resolve());
        }
        return new ListModelValue(copy);
    }

    @Override
    String asString() {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter, true);
        format(writer, 0, false);
        return stringWriter.toString();
    }

    @Override
    void format(final PrintWriter writer, final int indent, final boolean multiLineRequested) {
        final boolean multiLine = multiLineRequested && list.size() > 1;
        final List<ModelNode> list = asList();
        final Iterator<ModelNode> iterator = list.iterator();
        writer.append('[');
        if (multiLine) {
            indent(writer.append('\n'), indent + 1);
        }
        while (iterator.hasNext()) {
            final ModelNode entry = iterator.next();
            entry.format(writer, multiLine ? indent + 1 : indent, multiLineRequested);
            if (iterator.hasNext()) {
                if (multiLine) {
                    indent(writer.append(",\n"), indent + 1);
                } else {
                    writer.append(',');
                }
            }
        }
        if (multiLine) {
            indent(writer.append('\n'), indent);
        }
        writer.append(']');
    }

    @Override
    void formatAsJSON(final PrintWriter writer, final int indent, final boolean multiLineRequested) {
        final boolean multiLine = multiLineRequested && list.size() > 1;
        final List<ModelNode> list = asList();
        final Iterator<ModelNode> iterator = list.iterator();
        writer.append('[');
        if (multiLine) {
            indent(writer.append('\n'), indent + 1);
        }
        while (iterator.hasNext()) {
            final ModelNode entry = iterator.next();
            entry.formatAsJSON(writer, multiLine ? indent + 1 : indent, multiLineRequested);
            if (iterator.hasNext()) {
                if (multiLine) {
                    indent(writer.append(",\n"), indent + 1);
                } else {
                    writer.append(',');
                }
            }
        }
        if (multiLine) {
            indent(writer.append('\n'), indent);
        }
        writer.append(']');
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(final Object other) {
        return other instanceof ListModelValue && equals((ListModelValue) other);
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(final ListModelValue other) {
        return this == other || other != null && list.equals(other.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    boolean has(final int index) {
        return 0 <= index && index < list.size();
    }

    @Override
    ModelNode requireChild(final int index) throws NoSuchElementException {
        try {
            return list.get(index);
        } catch (final IndexOutOfBoundsException e) {
            return super.requireChild(index);
        }
    }

    @Override
    void write(final ModelWriter writer) throws IOException, ModelException {
        final Iterator<ModelNode> iterator = list.iterator();
        writer.writeListStart();
        ModelNode value;
        while (iterator.hasNext()) {
            value = iterator.next();
            value.write(writer);
        }
        writer.writeListEnd();
    }

}
