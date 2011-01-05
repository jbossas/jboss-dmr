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

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
abstract class ModelValue implements Cloneable {
    private final ModelType type;

    protected ModelValue(final ModelType type) {
        this.type = type;
    }

    ModelType getType() {
        return type;
    }

    long asLong() {
        throw new IllegalArgumentException();
    }

    long asLong(long defVal) {
        throw new IllegalArgumentException();
    }

    int asInt() {
        throw new IllegalArgumentException();
    }

    int asInt(int defVal) {
        throw new IllegalArgumentException();
    }

    boolean asBoolean() {
        throw new IllegalArgumentException();
    }

    boolean asBoolean(boolean defVal) {
        throw new IllegalArgumentException();
    }

    double asDouble() {
        throw new IllegalArgumentException();
    }

    double asDouble(double defVal) {
        throw new IllegalArgumentException();
    }

    byte[] asBytes() {
        throw new IllegalArgumentException();
    }

    BigDecimal asBigDecimal() {
        throw new IllegalArgumentException();
    }

    BigInteger asBigInteger() {
        throw new IllegalArgumentException();
    }

    abstract String asString();

    Property asProperty() {
        throw new IllegalArgumentException();
    }

    List<Property> asPropertyList() {
        throw new IllegalArgumentException();
    }

    ModelNode asObject() {
        throw new IllegalArgumentException();
    }

    ModelNode getChild(final String name) {
        throw new IllegalArgumentException();
    }

    ModelNode removeChild(final String name) {
        throw new IllegalArgumentException();
    }

    ModelNode getChild(final int index) {
        throw new IllegalArgumentException();
    }

    ModelNode addChild() {
        throw new IllegalArgumentException();
    }

    boolean exists() {
        return true;
    }

    Set<String> getKeys() {
        throw new IllegalArgumentException();
    }

    List<ModelNode> asList() {
        throw new IllegalArgumentException();
    }

    ModelType asType() {
        throw new IllegalArgumentException();
    }

    ModelValue protect() {
        return this;
    }

    protected final ModelValue clone() {
        try {
            return (ModelValue) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    protected static String quote(String orig) {
        final int length = orig.length();
        final StringBuilder builder = new StringBuilder(length + 32);
        builder.append('"');
        for (int i = 0; i < length; i = orig.offsetByCodePoints(i, 1)) {
            final int cp = orig.codePointAt(i);
            if (cp == '"' || cp == '\\') {
                builder.append('\\').appendCodePoint(cp);
            } else {
                builder.appendCodePoint(cp);
            }
        }
        builder.append('"');
        return builder.toString();
    }

    ModelValue copy() {
        return this;
    }

    static final ModelValue UNDEFINED = new ModelValue(ModelType.UNDEFINED) {

        String asString() {
            return "undefined";
        }

        long asLong(final long defVal) {
            return defVal;
        }

        int asInt(final int defVal) {
            return defVal;
        }

        boolean asBoolean(final boolean defVal) {
            return defVal;
        }

        double asDouble(final double defVal) {
            return defVal;
        }

        boolean exists() {
            return false;
        }

        public boolean equals(final Object other) {
            return other == this;
        }

        public int hashCode() {
            return 7113;
        }
    };

    public abstract boolean equals(Object other);

    public abstract int hashCode();

    protected static void indent(StringBuilder target, int count) {
        for (int i = 0; i < count; i ++) {
            target.append("    ");
        }
    }

    void format(final StringBuilder builder, final int indent, final boolean multiLine) {
        builder.append(asString());
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        format(builder, 0, true);
        return builder.toString();
    }

    ModelValue resolve() {
        return copy();
    }

    void writeExternal(final DataOutput out) throws IOException {
        // nothing by default
    }
}
