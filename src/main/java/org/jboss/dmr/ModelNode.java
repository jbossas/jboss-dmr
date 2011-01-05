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
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public class ModelNode implements Externalizable, Cloneable {

    private static final long serialVersionUID = 2030456323088551487L;

    private volatile ModelValue value = ModelValue.UNDEFINED;

    private static final AtomicReferenceFieldUpdater<ModelNode, ModelValue> valueUpdater = AtomicReferenceFieldUpdater.newUpdater(ModelNode.class, ModelValue.class, "value");

    public void protect() {
        value = value.protect();
    }

    public long asLong() throws NoSuchElementException {
        return value.asLong();
    }

    public long asLong(long defVal) {
        return value.asLong(defVal);
    }

    public int asInt() throws NoSuchElementException {
        return value.asInt();
    }

    public int asInt(int defVal) {
        return value.asInt(defVal);
    }

    public boolean asBoolean() throws NoSuchElementException {
        return value.asBoolean();
    }

    public boolean asBoolean(boolean defVal) {
        return value.asBoolean(defVal);
    }

    public String asString() {
        return value.asString();
    }

    public double asDouble() {
        return value.asDouble();
    }

    public double asDouble(double defVal) {
        return value.asDouble(defVal);
    }

    public ModelType asType() {
        return value.asType();
    }

    public BigDecimal asBigDecimal() {
        return value.asBigDecimal();
    }

    public BigInteger asBigInteger() {
        return value.asBigInteger();
    }

    public byte[] asBytes() {
        return value.asBytes();
    }

    public Property asProperty() {
        return value.asProperty();
    }

    public List<Property> asPropertyList() {
        return value.asPropertyList();
    }

    public ModelNode asObject() {
        return value.asObject();
    }

    public boolean exists() {
        return value.exists();
    }

    public void set(int newValue) {
        value = new IntModelValue(newValue);
    }

    public void set(long newValue) {
        value = new LongModelValue(newValue);
    }

    public void set(double newValue) {
        value = new DoubleModelValue(newValue);
    }

    public void set(boolean newValue) {
        value = BooleanModelValue.valueOf(newValue);
    }

    public void setExpression(String newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        value = new ExpressionValue(newValue);
    }

    public void set(String newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        value = new StringModelValue(newValue);
    }

    public void set(BigDecimal newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        value = new BigDecimalModelValue(newValue);
    }

    public void set(BigInteger newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        value = new BigIntegerModelValue(newValue);
    }

    public void set(ModelNode newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        value = newValue.value.copy();
    }

    public void set(byte[] newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        value = new BytesModelValue(newValue.clone());
    }

    public void set(ModelType newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        value = TypeModelValue.of(newValue);
    }

    public void set(Property property) {
        set(property.getName(), property.getValue());
    }

    public void set(String propertyName, ModelNode propertyValue) {
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
    }

    public void set(String propertyName, int propertyValue) {
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
    }

    public void set(String propertyName, long propertyValue) {
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
    }

    public void set(String propertyName, double propertyValue) {
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
    }

    public void set(String propertyName, boolean propertyValue) {
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
    }

    public void set(String propertyName, String propertyValue) {
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
    }

    public void set(String propertyName, BigDecimal propertyValue) {
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
    }

    public void set(String propertyName, BigInteger propertyValue) {
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
    }

    public void set(String propertyName, byte[] propertyValue) {
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
    }

    public void set(String propertyName, ModelType propertyValue) {
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
    }

    public void set(List<ModelNode> newValue) {
        value = new ListModelValue(new ArrayList<ModelNode>(newValue));
    }

    public void setEmptyList() {
        value = new ListModelValue();
    }

    public void setEmptyObject() {
        value = new ObjectModelValue();
    }

    public void clear() {
        value = ModelValue.UNDEFINED;
    }

    public ModelNode get(String name) {
        ModelValue value = this.value;
        while ((value = this.value) == ModelValue.UNDEFINED) {
            final ObjectModelValue newVal = new ObjectModelValue();
            if (valueUpdater.compareAndSet(this, ModelValue.UNDEFINED, newVal)) {
                return newVal.getChild(name);
            }
        }
        return value.getChild(name);
    }

    public ModelNode get(int index) {
        ModelValue value = this.value;
        while ((value = this.value) == ModelValue.UNDEFINED) {
            final ListModelValue newVal = new ListModelValue();
            if (valueUpdater.compareAndSet(this, ModelValue.UNDEFINED, newVal)) {
                return newVal.getChild(index);
            }
        }
        return value.getChild(index);
    }

    public ModelNode add(int newValue) {
        add().set(newValue);
        return this;
    }

    public ModelNode add(long newValue) {
        add().set(newValue);
        return this;
    }

    public ModelNode add(double newValue) {
        add().set(newValue);
        return this;
    }

    public ModelNode add(boolean newValue) {
        add().set(newValue);
        return this;
    }

    public ModelNode addExpression(String expression) {
        add().setExpression(expression);
        return this;
    }

    public ModelNode add(String newValue) {
        add().set(newValue);
        return this;
    }

    public ModelNode add(BigDecimal newValue) {
        add().set(newValue);
        return this;
    }

    public ModelNode add(BigInteger newValue) {
        add().set(newValue);
        return this;
    }

    public ModelNode add(ModelNode newValue) {
        add().set(newValue);
        return this;
    }

    public ModelNode add(byte[] newValue) {
        add().set(newValue);
        return this;
    }

    public ModelNode add(String propertyName, int propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    public ModelNode add(String propertyName, long propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    public ModelNode add(String propertyName, double propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    public ModelNode add(String propertyName, boolean propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    public ModelNode add(String propertyName, String propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    public ModelNode add(String propertyName, BigDecimal propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    public ModelNode add(String propertyName, BigInteger propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    public ModelNode add(String propertyName, ModelNode propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    public ModelNode add(String propertyName, byte[] propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    public ModelNode add() {
        ModelValue value = this.value;
        while ((value = this.value) == ModelValue.UNDEFINED) {
            final ListModelValue newVal = new ListModelValue();
            if (valueUpdater.compareAndSet(this, ModelValue.UNDEFINED, newVal)) {
                return newVal.addChild();
            }
        }
        return value.addChild();
    }

    public ModelNode addEmptyList() {
        add().setEmptyList();
        return this;
    }

    public ModelNode addEmptyObject() {
        add().setEmptyObject();
        return this;
    }

    public Set<String> keys() {
        return value.getKeys();
    }

    public List<ModelNode> asList() {
        return value.asList();
    }

    public ModelNode get(String... name) {
        ModelNode current = this;
        for (String part : name) {
            current = current.get(part);
        }
        return current;
    }

    public String toString() {
        return value.toString();
    }

    public ModelNode resolve() {
        final ModelNode newNode = new ModelNode();
        newNode.value = value.resolve();
        return newNode;
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(Object other) {
        return other instanceof ModelNode && equals((ModelNode)other);
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(ModelNode other) {
        return this == other || other != null && other.value.equals(value);
    }

    public int hashCode() {
        //noinspection NonFinalFieldReferencedInHashCode
        return value.hashCode();
    }

    /**
     * Clone this model node.
     *
     * @return the clone
     */
    public ModelNode clone() {
        try {
            final ModelNode clone = (ModelNode) super.clone();
            clone.value = value.copy();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }

    void format(final StringBuilder builder, final int indent, boolean multiLine) {
        value.format(builder, indent, multiLine);
    }

    public ModelType getType() {
        return value.getType();
    }


    public void writeExternal(final ObjectOutput out) throws IOException {
        writeExternal((DataOutput) out);
    }

    public void writeExternal(final OutputStream out) throws IOException {
        writeExternal((DataOutput) new DataOutputStream(out));
    }

    public void writeExternal(final DataOutputStream out) throws IOException {
        writeExternal((DataOutput) out);
    }

    public void writeExternal(final DataOutput out) throws IOException {
        final ModelValue value = this.value;
        final ModelType type = value.getType();
        out.write(type.getTypeChar());
        value.writeExternal(out);
    }

    public void readExternal(final ObjectInput in) throws IOException {
        readExternal((DataInput) in);
    }

    public void readExternal(final DataInputStream in) throws IOException {
        readExternal((DataInput) in);
    }

    public void readExternal(final InputStream in) throws IOException {
        readExternal((DataInput) new DataInputStream(in));
    }

    public void readExternal(final DataInput in) throws IOException {
        byte[] b; // used by some of these
        try {
            ModelType type = ModelType.forChar((char) (in.readByte() & 0xff));
            switch (type) {
                case UNDEFINED: value = ModelValue.UNDEFINED; return;
                case BIG_DECIMAL: value = new BigDecimalModelValue(in); return;
                case BIG_INTEGER: b = new byte[in.readInt()]; in.readFully(b); value = new BigIntegerModelValue(new BigInteger(b)); return;
                case BOOLEAN: value = BooleanModelValue.valueOf(in.readBoolean()); return;
                case BYTES: b = new byte[in.readInt()]; in.readFully(b); new BytesModelValue(b); return;
                case DOUBLE: value = new DoubleModelValue(in.readDouble()); return;
                case EXPRESSION: value = new ExpressionValue(in.readUTF()); return;
                case INT: value = new IntModelValue(in.readInt()); return;
                case LIST: value = new ListModelValue(in); return;
                case LONG: value = new LongModelValue(in.readLong()); return;
                case OBJECT: value = new ObjectModelValue(in); return;
                case PROPERTY: value = new PropertyModelValue(in); return;
                case STRING: value = new StringModelValue(in.readUTF()); return;
                case TYPE: value = TypeModelValue.of(ModelType.forChar((char) (in.readByte() & 0xff))); return;
                default: throw new InvalidObjectException("Invalid type read: " + type);
            }
        } catch (IllegalArgumentException e) {
            final InvalidObjectException ne = new InvalidObjectException(e.getMessage());
            ne.initCause(e.getCause());
            throw ne;
        }
    }
}
