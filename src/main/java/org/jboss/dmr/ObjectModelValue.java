/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
final class ObjectModelValue extends ModelValue {
    private final Map<String, ModelNode> map;

    protected ObjectModelValue() {
        super(ModelType.OBJECT);
        map = new LinkedHashMap<String, ModelNode>();
    }

    private ObjectModelValue(final ObjectModelValue value) {
        super(ModelType.OBJECT);
        map = new LinkedHashMap<String, ModelNode>(value.map);
    }

    private ObjectModelValue(final Map<String, ModelNode> map) {
        super(ModelType.OBJECT);
        this.map = map;
    }

    ModelValue protect() {
        Map<String, ModelNode> map = this.map;
        return map.getClass() == LinkedHashMap.class ? new ObjectModelValue(Collections.unmodifiableMap(map)) : this;
    }

    ModelNode getChild(final String name) {
        if (name == null) {
            return null;
        }
        final ModelNode node = map.get(name);
        if (node != null) return node;
        final ModelNode newNode = new ModelNode();
        map.put(name, newNode);
        return newNode;
    }

    ModelNode removeChild(final String name) {
        if (name == null) {
            return null;
        }
        return map.remove(name);
    }

    int asInt() {
        return map.size();
    }

    int asInt(final int defVal) {
        return asInt();
    }

    long asLong() {
        return asInt();
    }

    long asLong(final long defVal) {
        return asInt();
    }

    boolean asBoolean() {
        return ! map.isEmpty();
    }

    boolean asBoolean(final boolean defVal) {
        return ! map.isEmpty();
    }

    ModelValue copy() {
        return new ObjectModelValue(this);
    }

    String asString() {
        StringBuilder builder = new StringBuilder();
        builder.append('{');
        final Iterator<Map.Entry<String, ModelNode>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<String, ModelNode> entry = iterator.next();
            builder.append(entry.getKey());
            builder.append("->");
            builder.append(entry.getValue().asString());
            if (iterator.hasNext()) {
                builder.append(',');
            }
        }
        builder.append('}');
        return builder.toString();
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(Object other) {
        return other instanceof ObjectModelValue && equals((ObjectModelValue)other);
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(ObjectModelValue other) {
        return this == other || other != null && other.map.equals(map);
    }

    public int hashCode() {
        return map.hashCode();
    }
}
