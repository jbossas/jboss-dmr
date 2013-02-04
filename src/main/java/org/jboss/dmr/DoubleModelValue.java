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
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
final class DoubleModelValue extends ModelValue {

    private final double value;

    DoubleModelValue(final double value) {
        super(ModelType.DOUBLE);
        this.value = value;
    }

    @Override
    void writeExternal(final DataOutput out) throws IOException {
        out.write(ModelType.DOUBLE.typeChar);
        out.writeDouble(value);
    }

    @Override
    long asLong() {
        return (long) value;
    }

    @Override
    long asLong(final long defVal) {
        return (long) value;
    }

    @Override
    int asInt() {
        return (int) value;
    }

    @Override
    int asInt(final int defVal) {
        return (int) value;
    }

    @Override
    boolean asBoolean() {
        return value != 0;
    }

    @Override
    boolean asBoolean(final boolean defVal) {
        return value != 0;
    }

    @Override
    double asDouble() {
        return value;
    }

    @Override
    double asDouble(final double defVal) {
        return value;
    }

    @Override
    byte[] asBytes() {
        final long value = Double.doubleToLongBits(this.value);
        final byte[] bytes = new byte[8];
        bytes[0] = (byte) (value >>> 56);
        bytes[1] = (byte) (value >>> 48);
        bytes[2] = (byte) (value >>> 40);
        bytes[3] = (byte) (value >>> 32);
        bytes[4] = (byte) (value >>> 24);
        bytes[5] = (byte) (value >>> 16);
        bytes[6] = (byte) (value >>> 8);
        bytes[7] = (byte) (value);
        return bytes;
    }

    @Override
    BigDecimal asBigDecimal() {
        // Use the "pretty" over the "exact"
        return BigDecimal.valueOf(value);
    }

    @Override
    BigInteger asBigInteger() {
        return BigInteger.valueOf((long) value);
    }

    @Override
    String asString() {
        return Double.toString(value);
    }

    @Override
    ValueExpression asExpression() {
        return new ValueExpression(asString());
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(final Object other) {
        return other instanceof DoubleModelValue && equals((DoubleModelValue)other);
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(final DoubleModelValue other) {
        return this == other || other != null && other.value == value;
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }
}
