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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public class ModelNode implements Serializable, Cloneable {

    private static final long serialVersionUID = 2030456323088551487L;

    ModelNode() {
    }

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

    public void set(String newValue) {
        value = new StringModelValue(newValue);
    }

    public void set(BigDecimal newValue) {
        value = new DecimalModelValue(newValue);
    }

    public void set(ModelNode newValue) {
        value = newValue.value.copy();
    }

    public void set(byte[] newValue) {
        value = new BytesModelValue(newValue);
    }

    public void set(ModelType type) {
        value = TypeModelValue.of(type);
    }

    public void set(String propertyName, ModelNode propertyValue) {
        value = new PropertyModelValue(propertyName, propertyValue);
    }

    public void set(List<ModelNode> newValue) {
        value = new ListModelValue(new ArrayList<ModelNode>(newValue));
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

    public ModelNode add(String newValue) {
        add().set(newValue);
        return this;
    }

    public ModelNode add(BigDecimal newValue) {
        add().set(newValue);
        return this;
    }

    public ModelNode add(ModelNode newValue) {
        add().set(newValue);
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

    public Set<String> keys() {
        return value.getKeys();
    }

    public List<ModelNode> asList() {
        return value.getList();
    }

    public ModelNode get(String... name) {
        ModelNode current = this;
        for (String part : name) {
            current = current.get(part);
        }
        return current;
    }

    public String toString() {
        return asString();
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
}
