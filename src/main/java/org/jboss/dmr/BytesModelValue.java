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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
final class BytesModelValue extends ModelValue {

    private final byte[] bytes;

    BytesModelValue(final byte[] bytes) {
        super(ModelType.BYTES);
        this.bytes = bytes;
    }

    long asLong() {
        final byte[] bytes = this.bytes;
        final int length = bytes.length;
        final int cnt = Math.min(8, length);
        long v = 0L;
        for (int i = 0; i < cnt; i ++) {
            v <<= 8;
            v |= bytes[length - cnt + i] & 0xff;
        }
        return v;
    }

    long asLong(final long defVal) {
        return asLong();
    }

    int asInt() {
        final byte[] bytes = this.bytes;
        final int length = bytes.length;
        final int cnt = Math.min(4, length);
        int v = 0;
        for (int i = 0; i < cnt; i ++) {
            v <<= 8;
            v |= bytes[length - cnt + i] & 0xff;
        }
        return v;
    }

    int asInt(final int defVal) {
        return asInt();
    }

    double asDouble() {
        return Double.longBitsToDouble(asLong());
    }

    double asDouble(final double defVal) {
        return Double.longBitsToDouble(asLong());
    }

    BigDecimal asDecimal() {
        return new BigDecimal(new BigInteger(bytes));
    }

    String asString() {
        StringBuilder builder = new StringBuilder(bytes.length * 4 + 4);
        builder.append("{ ");
        for (int i = 0, length = bytes.length; i < length; i++) {
            final byte b = bytes[i];
            if (b >= 0 && b < 0x10) {
                builder.append('0').append('0' + b);
            } else {
                builder.append(Integer.toHexString(b & 0xff));
            }
            if (i != length - 1) {
                builder.append(", ");
            }
        }
        builder.append(" }");
        return builder.toString();
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(Object other) {
        return other instanceof BytesModelValue && equals((BytesModelValue)other);
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(BytesModelValue other) {
        return this == other || other != null && Arrays.equals(bytes, other.bytes);
    }

    public int hashCode() {
        return Arrays.hashCode(bytes) + 71;
    }
}
