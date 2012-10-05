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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
final class ObjectModelValue extends ModelValue {
    private final Map<String, ModelNode> map;

    protected ObjectModelValue() {
        super(ModelType.OBJECT);
        map = new LinkedHashMap<String, ModelNode>();
    }

    private ObjectModelValue(final Map<String, ModelNode> map) {
        super(ModelType.OBJECT);
        this.map = map;
    }

    ObjectModelValue(final DataInput in) throws IOException {
        super(ModelType.OBJECT);
        final int count = in.readInt();
        final LinkedHashMap<String, ModelNode> map = new LinkedHashMap<String, ModelNode>();
        for (int i = 0; i < count; i++) {
            final String key = in.readUTF();
            final ModelNode value = new ModelNode();
            value.readExternal(in);
            map.put(key, value);
        }
        this.map = map;
    }

    @Override
    void writeExternal(final DataOutput out) throws IOException {
        out.write(ModelType.OBJECT.typeChar);
        final Map<String, ModelNode> map = this.map;
        final int size = map.size();
        out.writeInt(size);
        for (final Map.Entry<String, ModelNode> entry : map.entrySet()) {
            out.writeUTF(entry.getKey());
            entry.getValue().writeExternal(out);
        }
    }

    @Override
    ModelValue protect() {
        final Map<String, ModelNode> map = this.map;
        for (final ModelNode node : map.values()) {
            node.protect();
        }
        return map.getClass() == LinkedHashMap.class ? new ObjectModelValue(Collections.unmodifiableMap(map)) : this;
    }

    @Override
    ModelNode asObject() {
        return new ModelNode(copy());
    }

    @Override
    ModelNode getChild(final String name) {
        if (name == null) {
            return null;
        }
        final ModelNode node = map.get(name);
        if (node != null) {
            return node;
        }
        final ModelNode newNode = new ModelNode();
        map.put(name, newNode);
        return newNode;
    }

    @Override
    ModelNode removeChild(final String name) {
        if (name == null) {
            return null;
        }
        return map.remove(name);
    }

    @Override
    int asInt() {
        return map.size();
    }

    @Override
    int asInt(final int defVal) {
        return asInt();
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
    boolean asBoolean() {
        return !map.isEmpty();
    }

    @Override
    boolean asBoolean(final boolean defVal) {
        return !map.isEmpty();
    }

    @Override
    Property asProperty() {
        if (map.size() == 1) {
            final Map.Entry<String, ModelNode> entry = map.entrySet().iterator().next();
            return new Property(entry.getKey(), entry.getValue());
        }
        return super.asProperty();
    }

    @Override
    List<Property> asPropertyList() {
        final List<Property> propertyList = new ArrayList<Property>();
        for (final Map.Entry<String, ModelNode> entry : map.entrySet()) {
            propertyList.add(new Property(entry.getKey(), entry.getValue()));
        }
        return propertyList;
    }

    @Override
    ModelValue copy() {
        return copy(false);
    }

    @Override
    ModelValue resolve() {
        return copy(true);
    }

    ModelValue copy(final boolean resolve) {
        final LinkedHashMap<String, ModelNode> newMap = new LinkedHashMap<String, ModelNode>();
        for (final Map.Entry<String, ModelNode> entry : map.entrySet()) {
            newMap.put(entry.getKey(), resolve ? entry.getValue().resolve() : entry.getValue().clone());
        }
        return new ObjectModelValue(newMap);
    }

    @Override
    List<ModelNode> asList() {
        final ArrayList<ModelNode> nodes = new ArrayList<ModelNode>();
        for (final Map.Entry<String, ModelNode> entry : map.entrySet()) {
            final ModelNode node = new ModelNode();
            node.set(entry.getKey(), entry.getValue());
            nodes.add(node);
        }
        return nodes;
    }

    @Override
    Set<String> getKeys() {
        return map.keySet();
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
        writer.append('{');
        final boolean multiLine = multiLineRequested && map.size() > 1;
        if (multiLine) {
            indent(writer.append('\n'), indent + 1);
        }
        final Iterator<Map.Entry<String, ModelNode>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<String, ModelNode> entry = iterator.next();
            writer.append(quote(entry.getKey()));
            final ModelNode value = entry.getValue();
            writer.append(" => ");
            value.format(writer, multiLine ? indent + 1 : indent, multiLineRequested);
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
        writer.append('}');
    }

    @Override
    void formatAsJSON(final PrintWriter writer, final int indent, final boolean multiLineRequested) {
        writer.append('{');
        final boolean multiLine = multiLineRequested && map.size() > 1;
        if (multiLine) {
            indent(writer.append('\n'), indent + 1);
        }
        final Iterator<Map.Entry<String, ModelNode>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<String, ModelNode> entry = iterator.next();
            writer.append(quote(entry.getKey()));
            writer.append(" : ");
            final ModelNode value = entry.getValue();
            value.formatAsJSON(writer, multiLine ? indent + 1 : indent, multiLineRequested);
            if (iterator.hasNext()) {
                if (multiLine) {
                    indent(writer.append(",\n"), indent + 1);
                } else {
                    writer.append(", ");
                }
            }
        }
        if (multiLine) {
            indent(writer.append('\n'), indent);
        }
        writer.append('}');
    }

    /**
     * Determine whether this object is equal to another.
     * 
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(final Object other) {
        return other instanceof ObjectModelValue && equals((ObjectModelValue) other);
    }

    /**
     * Determine whether this object is equal to another.
     * 
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(final ObjectModelValue other) {
        return this == other || other != null && other.map.equals(map);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    boolean has(final String key) {
        return map.containsKey(key);
    }

    @Override
    ModelNode requireChild(final String name) throws NoSuchElementException {
        final ModelNode node = map.get(name);
        if (node != null) {
            return node;
        }
        return super.requireChild(name);
    }
}
