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

import java.io.ByteArrayInputStream;
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
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;



/**
 * A dynamic model representation node object.
 * <p>
 * A node can be of any type specified in the {@link ModelType} enumeration.  The type can
 * be queried via {@link #getType()} and updated via any of the {@code set*()} methods.  The
 * value of the node can be acquired via the {@code as<type>()} methods, where {@code <type>} is
 * the desired value type.  If the type is not the same as the node type, a conversion is attempted between
 * the types.
 * <p>A node can be made read-only by way of its {@link #protect()} method, which will prevent
 * any further changes to the node or its sub-nodes.
 * <p>Instances of this class are <b>not</b> thread-safe and need to be synchronized externally.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public class ModelNode implements Externalizable, Cloneable {

    private static final long serialVersionUID = 2030456323088551487L;

    private boolean protect = false;
    private ModelValue value = ModelValue.UNDEFINED;

    public ModelNode() {
    }

    ModelNode(final ModelValue value) {
        this.value = value;
    }

    /**
     * Prevent further modifications to this node and its sub-nodes.  Note that copies
     * of this node made after this method call will not be protected.
     */
    public void protect() {
        if (! protect) {
            protect = true;
            value = value.protect();
        }
    }

    /**
     * Get the value of this node as a {@code long}.  Collection types will return the size
     * of the collection for this value.  Other types may attempt a string conversion.
     *
     * @return the long value
     * @throws IllegalArgumentException if no conversion is possible
     */
    public long asLong() throws IllegalArgumentException {
        return value.asLong();
    }

    /**
     * Get the value of this node as a {@code long}.  Collection types will return the size
     * of the collection for this value.  Other types may attempt a string conversion.
     *
     * @param defVal the default value if no conversion is possible
     * @return the long value
     */
    public long asLong(final long defVal) {
        return value.asLong(defVal);
    }

    /**
     * Get the value of this node as an {@code int}.  Collection types will return the size
     * of the collection for this value.  Other types may attempt a string conversion.
     *
     * @return the int value
     * @throws IllegalArgumentException if no conversion is possible
     */
    public int asInt() throws IllegalArgumentException {
        return value.asInt();
    }

    /**
     * Get the value of this node as an {@code int}.  Collection types will return the size
     * of the collection for this value.  Other types may attempt a string conversion.
     *
     * @param defVal the default value if no conversion is possible
     * @return the int value
     */
    public int asInt(final int defVal) {
        return value.asInt(defVal);
    }

    /**
     * Get the value of this node as a {@code boolean}.  Collection types return {@code true} for non-empty
     * collections.  Numerical types return {@code true} for non-zero values.
     *
     * @return the boolean value
     * @throws IllegalArgumentException if no conversion is possible
     */
    public boolean asBoolean() throws IllegalArgumentException {
        return value.asBoolean();
    }

    /**
     * Get the value of this node as a {@code boolean}.  Collection types return {@code true} for non-empty
     * collections.  Numerical types return {@code true} for non-zero values.
     *
     * @param defVal the default value if no conversion is possible
     * @return the boolean value
     */
    public boolean asBoolean(final boolean defVal) {
        return value.asBoolean(defVal);
    }

    /**
     * Get the value as a string.  This is the literal value of this model node.  More than one node type may
     * yield the same value for this method.
     *
     * @return the string value
     */
    public String asString() {
        return value.asString();
    }

    /**
     * Get the value of this node as a {@code double}.  Collection types will return the size
     * of the collection for this value.  Other types may attempt a string conversion.
     *
     * @throws IllegalArgumentException if no conversion is possible
     * @return the double value
     */
    public double asDouble() throws IllegalArgumentException {
        return value.asDouble();
    }

    /**
     * Get the value of this node as an {@code double}.  Collection types will return the size
     * of the collection for this value.  Other types may attempt a string conversion.
     *
     * @param defVal the default value if no conversion is possible
     * @return the int value
     */
    public double asDouble(final double defVal) {
        return value.asDouble(defVal);
    }

    /**
     * Get the value of this node as a type, expressed using the {@code ModelType} enum.  The string
     * value of this node must be convertible to a type.
     *
     * @return the {@code ModelType} value
     * @throws IllegalArgumentException if no conversion is possible
     */
    public ModelType asType() throws IllegalArgumentException {
        return value.asType();
    }

    /**
     * Get the value of this node as a {@code BigDecimal}.  Collection types will return the size
     * of the collection for this value.  Other types may attempt a string conversion.
     *
     * @throws IllegalArgumentException if no conversion is possible
     * @return the {@code BigDecimal} value
     */
    public BigDecimal asBigDecimal() throws IllegalArgumentException {
        return value.asBigDecimal();
    }

    /**
     * Get the value of this node as a {@code BigInteger}.  Collection types will return the size
     * of the collection for this value.  Other types may attempt a string conversion.
     *
     * @return the {@code BigInteger} value
     * @throws IllegalArgumentException if no conversion is possible
     */
    public BigInteger asBigInteger() throws IllegalArgumentException {
        return value.asBigInteger();
    }

    /**
     * Get the value of this node as a byte array.  Strings and string-like values will return
     * the UTF-8 encoding of the string.  Numerical values will return the byte representation of the
     * number.
     *
     * @return the bytes
     * @throws IllegalArgumentException if no conversion is possible
     */
    public byte[] asBytes() throws IllegalArgumentException {
        return value.asBytes();
    }

    /**
     * Get the value of this node as a property.  Object values will return a property if there is exactly one
     * property in the object.  List values will return a property if there are exactly two items in the list,
     * and if the first is convertible to a string.
     *
     * @return the property value
     * @throws IllegalArgumentException if no conversion is possible
     */
    public Property asProperty() throws IllegalArgumentException {
        return value.asProperty();
    }

    /**
     * Get the value of this node as a property list.  Object values will return a list of properties representing
     * each key-value pair in the object.  List values will return all the values of the list, failing if any of the
     * values are not convertible to a property value.
     *
     * @return the property list value
     * @throws IllegalArgumentException if no conversion is possible
     */
    public List<Property> asPropertyList() throws IllegalArgumentException {
        return value.asPropertyList();
    }

    /**
     * Get a copy of this value as an object.  Object values will simply copy themselves as by the {@link #clone()} method.
     * Property values will return a single-entry object whose key and value are copied from the property key and value.
     * List values will attempt to interpolate the list into an object by iterating each item, mapping each property
     * into an object entry and otherwise taking pairs of list entries, converting the first to a string, and using the
     * pair of entries as a single object entry.  If an object key appears more than once in the source object, the last
     * key takes precedence.
     *
     * @return the object value
     * @throws IllegalArgumentException if no conversion is possible
     */
    public ModelNode asObject() throws IllegalArgumentException {
        return value.asObject();
    }

    /**
     * Determine whether this node is defined.  Equivalent to the expression: {@code getType() != ModelType.UNDEFINED}.
     *
     * @return {@code true} if this node's value is defined
     */
    public boolean isDefined() {
        return getType() != ModelType.UNDEFINED;
    }

    /**
     * Change this node's value to the given value.
     *
     * @param newValue the new value
     * @return this node
     */
    public ModelNode set(final int newValue) {
        checkProtect();
        value = new IntModelValue(newValue);
        return this;
    }

    /**
     * Change this node's value to the given value.
     *
     * @param newValue the new value
     * @return this node
     */
    public ModelNode set(final long newValue) {
        checkProtect();
        value = new LongModelValue(newValue);
        return this;
    }

    /**
     * Change this node's value to the given value.
     *
     * @param newValue the new value
     * @return this node
     */
    public ModelNode set(final double newValue) {
        checkProtect();
        value = new DoubleModelValue(newValue);
        return this;
    }

    /**
     * Change this node's value to the given value.
     *
     * @param newValue the new value
     * @return this node
     */
    public ModelNode set(final boolean newValue) {
        checkProtect();
        value = BooleanModelValue.valueOf(newValue);
        return this;
    }

    /**
     * Change this node's value to the given expression value.
     *
     * @param newValue the new value
     * @return this node
     */
    public ModelNode setExpression(final String newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        checkProtect();
        value = new ExpressionValue(newValue);
        return this;
    }

    /**
     * Change this node's value to the given value.
     *
     * @param newValue the new value
     * @return this node
     */
    public ModelNode set(final String newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        checkProtect();
        value = new StringModelValue(newValue);
        return this;
    }

    /**
     * Change this node's value to the given value.
     *
     * @param newValue the new value
     * @return this node
     */
    public ModelNode set(final BigDecimal newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        checkProtect();
        value = new BigDecimalModelValue(newValue);
        return this;
    }

    /**
     * Change this node's value to the given value.
     *
     * @param newValue the new value
     * @return this node
     */
    public ModelNode set(final BigInteger newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        checkProtect();
        value = new BigIntegerModelValue(newValue);
        return this;
    }

    /**
     * Change this node's value to the given value.  The value is copied from the parameter.
     *
     * @param newValue the new value
     * @return this node
     */
    public ModelNode set(final ModelNode newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        checkProtect();
        value = newValue.value.copy();
        return this;
    }

    void setNoCopy(final ModelNode child) {
        value = child.value;
    }

    /**
     * Change this node's value to the given value.
     *
     * @param newValue the new value
     * @return this node
     */
    public ModelNode set(final byte[] newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        checkProtect();
        value = new BytesModelValue(newValue.length == 0 ? newValue : newValue.clone());
        return this;
    }

    /**
     * Change this node's value to the given value.
     *
     * @param newValue the new value
     * @return this node
     */
    public ModelNode set(final ModelType newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        checkProtect();
        value = TypeModelValue.of(newValue);
        return this;
    }

    /**
     * Change this node's value to the given value.
     *
     * @param newValue the new value
     * @return this node
     */
    public ModelNode set(final Property newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        set(newValue.getName(), newValue.getValue());
        return this;
    }

    /**
     * Change this node's value to a property with the given name and value.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode set(final String propertyName, final ModelNode propertyValue) {
        checkProtect();
        value = new PropertyModelValue(propertyName, propertyValue, true);
        return this;
    }

    ModelNode setNoCopy(final String propertyName, final ModelNode propertyValue) {
        value = new PropertyModelValue(propertyName, propertyValue, false);
        return this;
    }

    /**
     * Change this node's value to a property with the given name and value.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode set(final String propertyName, final int propertyValue) {
        checkProtect();
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
        return this;
    }

    /**
     * Change this node's value to a property with the given name and value.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode set(final String propertyName, final long propertyValue) {
        checkProtect();
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
        return this;
    }

    /**
     * Change this node's value to a property with the given name and value.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode set(final String propertyName, final double propertyValue) {
        checkProtect();
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
        return this;
    }

    /**
     * Change this node's value to a property with the given name and value.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode set(final String propertyName, final boolean propertyValue) {
        checkProtect();
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
        return this;
    }

    /**
     * Change this node's value to a property with the given name and value.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode set(final String propertyName, final String propertyValue) {
        checkProtect();
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
        return this;
    }

    /**
     * Change this node's value to a property with the given name and expression value.
     *
     * @param propertyName the property name
     * @param propertyValue the property expression value
     * @return this node
     */
    public ModelNode setExpression(final String propertyName, final String propertyValue) {
        checkProtect();
        final ModelNode node = new ModelNode();
        node.setExpression(propertyValue);
        value = new PropertyModelValue(propertyName, node);
        return this;
    }

    /**
     * Change this node's value to a property with the given name and value.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode set(final String propertyName, final BigDecimal propertyValue) {
        checkProtect();
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
        return this;
    }

    /**
     * Change this node's value to a property with the given name and value.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode set(final String propertyName, final BigInteger propertyValue) {
        checkProtect();
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
        return this;
    }

    /**
     * Change this node's value to a property with the given name and value.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode set(final String propertyName, final byte[] propertyValue) {
        checkProtect();
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
        return this;
    }

    /**
     * Change this node's value to a property with the given name and value.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode set(final String propertyName, final ModelType propertyValue) {
        checkProtect();
        final ModelNode node = new ModelNode();
        node.set(propertyValue);
        value = new PropertyModelValue(propertyName, node);
        return this;
    }

    /**
     * Change this node's value to a list whose values are copied from the given collection.
     *
     * @param newValue the list value
     * @return this node
     */
    public ModelNode set(final Collection<ModelNode> newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("newValue is null");
        }
        checkProtect();
        final ArrayList<ModelNode> list = new ArrayList<ModelNode>(newValue.size());
        for (final ModelNode node : newValue) {
            if (node == null) {
                list.add(new ModelNode());
            } else {
                list.add(node.clone());
            }
        }
        value = new ListModelValue(list);
        return this;
    }

    /**
     * Change this node's value to an empty list.
     *
     * @return this node
     */
    public ModelNode setEmptyList() {
        checkProtect();
        value = new ListModelValue();
        return this;
    }

    /**
     * Change this node's value to an empty object.
     *
     * @return this node
     */
    public ModelNode setEmptyObject() {
        checkProtect();
        value = new ObjectModelValue();
        return this;
    }

    /**
     * Clear this node's value and change its type to {@link ModelType#UNDEFINED}.
     *
     * @return this node
     */
    public ModelNode clear() {
        checkProtect();
        value = ModelValue.UNDEFINED;
        return this;
    }

    /**
     * Get the child of this node with the given name.  If no such child exists, create it.  If the node is undefined,
     * it will be initialized to be of type {@link ModelType#OBJECT}.
     * <p>
     * When called on property values, the name must match the property name.
     *
     * @param name the child name
     * @return the child
     * @throws IllegalArgumentException if this node does not support getting a child with the given name
     */
    public ModelNode get(final String name) {
        ModelValue value = this.value;
        if ((value = this.value) == ModelValue.UNDEFINED) {
            checkProtect();
            return (this.value = new ObjectModelValue()).getChild(name);
        }
        return value.getChild(name);
    }

    /**
     * Require the existence of a child of this node with the given name, returning the child.  If no such child exists,
     * an exception is thrown.
     * <p>
     * When called on property values, the name must match the property name.
     *
     * @param name the child name
     * @return the child
     * @throws NoSuchElementException if the element does not exist
     */
    public ModelNode require(final String name) throws NoSuchElementException {
        return value.requireChild(name);
    }

    /**
     * Remove a child of this node, returning the child.  If no such child exists,
     * an exception is thrown.
     * <p>
     * When called on property values, the name must match the property name.
     *
     * @param name the child name
     * @return the child
     * @throws NoSuchElementException if the element does not exist
     */
    public ModelNode remove(final String name) throws NoSuchElementException {
        return value.removeChild(name);
    }

    /**
     * Get the child of this node with the given index.  If no such child exists, create it (adding list entries as needed).
     * If the node is undefined, it will be initialized to be of type {@link ModelType#LIST}.
     * <p>
     * When called on property values, the index must be zero.
     *
     * @param index the child index
     * @return the child
     * @throws IllegalArgumentException if this node does not support getting a child with the given index
     */
    public ModelNode get(final int index) {
        final ModelValue value = this.value;
        if (value == ModelValue.UNDEFINED) {
            checkProtect();
            return (this.value = new ListModelValue()).getChild(index);
        }
        return value.getChild(index);
    }

    /**
     * Require the existence of a child of this node with the given index, returning the child.  If no such child exists,
     * an exception is thrown.
     * <p>
     * When called on property values, the index must be zero.
     *
     * @param index the child index
     * @return the child
     * @throws NoSuchElementException if the element does not exist
     */
    public ModelNode require(final int index) {
        return value.requireChild(index);
    }

    /**
     * Add the given value to the end of this node's value list.  If the node is undefined, it will be initialized to be
     * of type {@link ModelType#LIST}.
     *
     * @param newValue the new value to add
     * @return this node
     */
    public ModelNode add(final int newValue) {
        add().set(newValue);
        return this;
    }

    /**
     * Add the given value to the end of this node's value list.  If the node is undefined, it will be initialized to be
     * of type {@link ModelType#LIST}.
     *
     * @param newValue the new value to add
     * @return this node
     */
    public ModelNode add(final long newValue) {
        add().set(newValue);
        return this;
    }

    /**
     * Add the given value to the end of this node's value list.  If the node is undefined, it will be initialized to be
     * of type {@link ModelType#LIST}.
     *
     * @param newValue the new value to add
     * @return this node
     */
    public ModelNode add(final double newValue) {
        add().set(newValue);
        return this;
    }

    /**
     * Add the given value to the end of this node's value list.  If the node is undefined, it will be initialized to be
     * of type {@link ModelType#LIST}.
     *
     * @param newValue the new value to add
     * @return this node
     */
    public ModelNode add(final boolean newValue) {
        add().set(newValue);
        return this;
    }

    /**
     * Add the given expression to the end of this node's value list.  If the node is undefined, it will be initialized to be
     * of type {@link ModelType#LIST}.
     *
     * @param newValue the new value to add
     * @return this node
     */
    public ModelNode addExpression(final String newValue) {
        add().setExpression(newValue);
        return this;
    }

    /**
     * Add the given value to the end of this node's value list.  If the node is undefined, it will be initialized to be
     * of type {@link ModelType#LIST}.
     *
     * @param newValue the new value to add
     * @return this node
     */
    public ModelNode add(final String newValue) {
        add().set(newValue);
        return this;
    }

    /**
     * Add the given value to the end of this node's value list.  If the node is undefined, it will be initialized to be
     * of type {@link ModelType#LIST}.
     *
     * @param newValue the new value to add
     * @return this node
     */
    public ModelNode add(final BigDecimal newValue) {
        add().set(newValue);
        return this;
    }

    /**
     * Add the given value to the end of this node's value list.  If the node is undefined, it will be initialized to be
     * of type {@link ModelType#LIST}.
     *
     * @param newValue the new value to add
     * @return this node
     */
    public ModelNode add(final BigInteger newValue) {
        add().set(newValue);
        return this;
    }

    /**
     * Add a copy of the given value to the end of this node's value list.  If the node is undefined, it will be initialized to be
     * of type {@link ModelType#LIST}.
     *
     * @param newValue the new value to add
     * @return this node
     */
    public ModelNode add(final ModelNode newValue) {
        add().set(newValue);
        return this;
    }

    ModelNode addNoCopy(final ModelNode child) {
        add().value = child.value;
        return this;
    }

    /**
     * Add the given value to the end of this node's value list.  If the node is undefined, it will be initialized to be
     * of type {@link ModelType#LIST}.
     *
     * @param newValue the new value to add
     * @return this node
     */
    public ModelNode add(final byte[] newValue) {
        add().set(newValue);
        return this;
    }

    /**
     * Add a property with the given name and value to the end of this node's value list.  If the node is undefined, it
     * will be initialized to be of type {@link ModelType#LIST}.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode add(final String propertyName, final int propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    /**
     * Add a property with the given name and value to the end of this node's value list.  If the node is undefined, it
     * will be initialized to be of type {@link ModelType#LIST}.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode add(final String propertyName, final long propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    /**
     * Add a property with the given name and value to the end of this node's value list.  If the node is undefined, it
     * will be initialized to be of type {@link ModelType#LIST}.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode add(final String propertyName, final double propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    /**
     * Add a property with the given name and value to the end of this node's value list.  If the node is undefined, it
     * will be initialized to be of type {@link ModelType#LIST}.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode add(final String propertyName, final boolean propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    /**
     * Add a property with the given name and value to the end of this node's value list.  If the node is undefined, it
     * will be initialized to be of type {@link ModelType#LIST}.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode add(final String propertyName, final String propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    /**
     * Add a property with the given name and value to the end of this node's value list.  If the node is undefined, it
     * will be initialized to be of type {@link ModelType#LIST}.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode add(final String propertyName, final BigDecimal propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    /**
     * Add a property with the given name and value to the end of this node's value list.  If the node is undefined, it
     * will be initialized to be of type {@link ModelType#LIST}.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode add(final String propertyName, final BigInteger propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    /**
     * Add a property with the given name and value to the end of this node's value list.  If the node is undefined, it
     * will be initialized to be of type {@link ModelType#LIST}.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode add(final String propertyName, final ModelNode propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    /**
     * Add a property with the given name and value to the end of this node's value list.  If the node is undefined, it
     * will be initialized to be of type {@link ModelType#LIST}.
     *
     * @param propertyName the property name
     * @param propertyValue the property value
     * @return this node
     */
    public ModelNode add(final String propertyName, final byte[] propertyValue) {
        add().set(propertyName, propertyValue);
        return this;
    }

    /**
     * Add a node to the end of this node's value list and return it.  If the node is undefined, it
     * will be initialized to be of type {@link ModelType#LIST}.
     *
     * @return the new node
     */
    public ModelNode add() {
        checkProtect();
        ModelValue value = this.value;
        if ((value = this.value) == ModelValue.UNDEFINED) {
            return (this.value = new ListModelValue()).addChild();
        }
        return value.addChild();
    }

    /**
     * Add a node of type {@link ModelType#LIST} to the end of this node's value list and return it.  If this node is
     * undefined, it will be initialized to be of type {@link ModelType#LIST}.
     *
     * @return the new node
     */
    public ModelNode addEmptyList() {
        final ModelNode node = add();
        node.setEmptyList();
        return node;
    }

    /**
     * Add a node of type {@link ModelType#OBJECT} to the end of this node's value list and return it.  If this node is
     * undefined, it will be initialized to be of type {@link ModelType#LIST}.
     *
     * @return the new node
     */
    public ModelNode addEmptyObject() {
        final ModelNode node = add();
        node.setEmptyObject();
        return node;
    }

    /**
     * Determine whether this node has a child with the given index.  Property node types always contain exactly one
     * value.
     *
     * @param index the index
     * @return {@code true} if there is a (possibly undefined) node at the given index
     */
    public boolean has(final int index) {
        return value.has(index);
    }

    /**
     * Determine whether this node has a child with the given name.  Property node types always contain exactly one
     * value with a key equal to the property name.
     *
     * @param key the name
     * @return {@code true} if there is a (possibly undefined) node at the given key
     */
    public boolean has(final String key) {
        return value.has(key);
    }

    /**
     * Determine whether this node has a defined child with the given index.  Property node types always contain exactly one
     * value.
     *
     * @param index the index
     * @return {@code true} if there is a node at the given index and its {@link #getType() type} is not {@link ModelType.UNDEFINED}
     */
    public boolean hasDefined(int index) {
        return value.has(index) && get(index).isDefined();
    }

    /**
     * Determine whether this node has a defined child with the given name.  Property node types always contain exactly one
     * value with a key equal to the property name.
     *
     * @param key the name
     * @return {@code true} if there is a node at the given index and its {@link #getType() type} is not {@link ModelType.UNDEFINED}
     */
    public boolean hasDefined(String key) {
        return value.has(key) && get(key).isDefined();
    }

    /**
     * Get the set of keys contained in this object.  Property node types always contain exactly one value with a key
     * equal to the property name.  Other non-object types will return an empty set.
     *
     * @return the key set
     */
    public Set<String> keys() {
        return value.getKeys();
    }

    /**
     * Get the list of entries contained in this object.  Property node types always contain exactly one entry (itself).
     * Lists will return an unmodifiable view of their contained list.  Objects will return a list of properties corresponding
     * to the mappings within the object.  Other types will return an empty list.
     *
     * @return the entry list
     */
    public List<ModelNode> asList() {
        return value.asList();
    }

    /**
     * Recursively get the children of this node with the given names.  If any child along the path does not exist,
     * create it.  If any node is the path is undefined, it will be initialized to be of type {@link ModelType#OBJECT}.
     *
     * @param names the child names
     * @return the child
     * @throws IllegalArgumentException if a node does not support getting a child with the given name path
     */
    public ModelNode get(final String... names) {
        ModelNode current = this;
        for (final String part : names) {
            current = current.get(part);
        }
        return current;
    }

    /**
     * Get a human-readable string representation of this model node, formatted nicely (possibly on multiple lines).
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Output the DMR string representation of this model node, formatted nicely, if requested to the supplied PrintWriter
     * instance.
     * 
     * @param writer A PrintWriter instance used to output the DMR string.
     * @param compact Flag that indicates whether or not the string should be all on one line (i.e. {@code true}) or should be
     *        printed on multiple lines ({@code false}).
     */
    public void writeString(final PrintWriter writer, final boolean compact) {
        value.writeString(writer, compact);
    }

    /**
     * Get a JSON string representation of this model node, formatted nicely, if requested.
     * @param compact Flag that indicates whether or not the string should be all on
     * 	one line (i.e. {@code true}) or should be printed on multiple lines ({@code false}).
     * @return The JSON string.
     */
    public String toJSONString(final boolean compact) {
        return value.toJSONString(compact);
    }

    /**
     * Output the JSON string representation of this model node, formatted nicely, if requested to the supplied PrintWriter
     * instance.
     * 
     * @param writer A PrintWriter instance used to output the JSON string.
     * @param compact Flag that indicates whether or not the string should be all on one line (i.e. {@code true}) or should be
     *        printed on multiple lines ({@code false}).
     */
    public void writeJSONString(final PrintWriter writer, final boolean compact) {
        value.writeJSONString(writer, compact);
    }

    /**
     * Get a model node from a string representation of the model node.
     *
     * @param input the input string
     * @return the model node
     */
    public static ModelNode fromString(final String input) {
        final ModelNodeParser parser = new ModelNodeParser();
        try {
            parser.setInput(new ByteArrayInputStream(input.getBytes("US-ASCII")));
            if (parser.yyParse() > 0) {
                throw new IllegalArgumentException("DMR parser error");
            }
            return parser.getResult();
        } catch (final IOException e) {
            final IllegalArgumentException n = new IllegalArgumentException(e.getMessage());
            n.setStackTrace(e.getStackTrace());
            throw n;
        }
    }

    public static ModelNode fromJSONString(final String input) {
        final JSONParserImpl parser = new JSONParserImpl();
        try {
            parser.setInput(new ByteArrayInputStream(input.getBytes("US-ASCII")));
            if(parser.yyParse() > 0) {
                throw new IllegalArgumentException("JSON parser error");
            }
            return parser.getResult();
        } catch (final IOException e) {
            final IllegalArgumentException n = new IllegalArgumentException(e.getMessage());
            n.setStackTrace(e.getStackTrace());
            throw n;
        }
    }

    /**
     * Get a model node from a text representation of the model node.  The stream must be encoded in
     * US-ASCII encoding.
     *
     * @param stream the source stream
     * @return the model node
     */
    public static ModelNode fromStream(final InputStream stream) throws IOException {
        final ModelNodeParser parser = new ModelNodeParser();
        parser.setInput(stream);
        if (parser.yyParse() > 0) {
            throw new IOException("Parser error");
        }
        return parser.getResult();
    }
    
    /**
     * Get a model node from a JSON text representation of the model node. The stream must be encoded in US-ASCII encoding.
     * 
     * @param stream the source stream
     * @return the model node
     */
    public static ModelNode fromJSONStream(final InputStream stream) throws IOException {
        final JSONParserImpl parser = new JSONParserImpl();
        parser.setInput(stream);
        if (parser.yyParse() > 0) {
            throw new IOException("Parser error");
        }
        return parser.getResult();
    }

    /**
     * Reads base64 data from the passed stream,
     * and deserializes the decoded result.
     *
     * @see #writeBase64(OutputStream)
     * @return the decoded model node
     * @throws IOException if the passed stream has an issue
     */
    public static ModelNode fromBase64(InputStream stream) throws IOException {
        Base64.InputStream bstream = new Base64.InputStream(stream);
        ModelNode node = new ModelNode();
        node.readExternal(bstream);
        bstream.close();
        return node;
    }

    /**
     * Return a copy of this model node, with all system property expressions locally resolved.  The caller must have
     * permission to access all of the system properties named in the node tree.
     *
     * @return the resolved copy
     */
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
    @Override
    public boolean equals(final Object other) {
        return other instanceof ModelNode && equals((ModelNode)other);
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(final ModelNode other) {
        return this == other || other != null && other.value.equals(value);
    }

    /**
     * Get the hash code of this node object.  Note that unless the value is {@link #protect()}ed, the hash code may
     * change over time, thus making unprotected nodes unsuitable for use as hash table keys.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        //noinspection NonFinalFieldReferencedInHashCode
        return value.hashCode();
    }

    /**
     * Clone this model node.
     *
     * @return the clone
     */
    @Override
    public ModelNode clone() {
        final ModelNode clone = new ModelNode();
        clone.value = value.copy();
        return clone;
    }

    void format(final PrintWriter writer, final int indent, final boolean multiLine) {
        value.format(writer, indent, multiLine);
    }

    void formatAsJSON(final PrintWriter writer, final int indent, final boolean multiLine) {
        value.formatAsJSON(writer, indent, multiLine);
    }

    /**
     * Get the current type of this node.
     *
     * @return the node type
     */
    public ModelType getType() {
        return value.getType();
    }

    /**
     * Write this node's content in binary format to the given target.
     *
     * @param out the target to which the content should be written
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        writeExternal((DataOutput) out);
    }

    /**
     * Write this node's content in binary format to the given target.
     *
     * @param out the target to which the content should be written
     * @throws IOException if an I/O error occurs
     */
    public void writeExternal(final OutputStream out) throws IOException {
        writeExternal((DataOutput) new DataOutputStream(out));
    }

    /**
     * Write this node's content in binary format to the given target.
     *
     * @param out the target to which the content should be written
     * @throws IOException if an I/O error occurs
     */
    public void writeExternal(final DataOutputStream out) throws IOException {
        writeExternal((DataOutput) out);
    }

    /**
     * Write this node's content in binary format to the given target.
     *
     * @param out the target to which the content should be written
     * @throws IOException if an I/O error occurs
     */
    public void writeExternal(final DataOutput out) throws IOException {
        final ModelValue value = this.value;
        final ModelType type = value.getType();
        out.write(type.getTypeChar());
        value.writeExternal(out);
    }

    /**
     * Read this node's content in binary format from the given source.
     *
     * @param in the source from which the content should be read
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void readExternal(final ObjectInput in) throws IOException {
        readExternal((DataInput) in);
    }

    /**
     * Read this node's content in binary format from the given source.
     *
     * @param in the source from which the content should be read
     * @throws IOException if an I/O error occurs
     */
    public void readExternal(final DataInputStream in) throws IOException {
        readExternal((DataInput) in);
    }

    /**
     * Read this node's content in binary format from the given source.
     *
     * @param in the source from which the content should be read
     * @throws IOException if an I/O error occurs
     */
    public void readExternal(final InputStream in) throws IOException {
        readExternal((DataInput) new DataInputStream(in));
    }

    /**
     * Read this node's content in binary format from the given source.
     *
     * @param in the source from which the content should be read
     * @throws IOException if an I/O error occurs
     */
    public void readExternal(final DataInput in) throws IOException {
        checkProtect();
        try {
            final ModelType type = ModelType.forChar((char) (in.readByte() & 0xff));
            switch (type) {
                case UNDEFINED: value = ModelValue.UNDEFINED; return;
                case BIG_DECIMAL: value = new BigDecimalModelValue(in); return;
                case BIG_INTEGER: value = new BigIntegerModelValue(in); return;
                case BOOLEAN: value = BooleanModelValue.valueOf(in.readBoolean()); return;
                case BYTES: value = new BytesModelValue(in); return;
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
        } catch (final IllegalArgumentException e) {
            final InvalidObjectException ne = new InvalidObjectException(e.getMessage());
            ne.initCause(e.getCause());
            throw ne;
        }
    }


    /**
     * Encodes the serialized representation in base64 form
     * and writes it to the specified output stream.
     *
     * @param stream the stream to write to
     * @throws IOException if the specified stream has an issue
     */
    public void writeBase64(OutputStream stream) throws IOException {
        OutputStream bstream = new Base64.OutputStream(stream);
        writeExternal(bstream);
        bstream.close(); // Required to ensure last block is written to stream.
    }

    private void checkProtect() {
        if (protect) {
            throw new UnsupportedOperationException();
        }
    }
}
