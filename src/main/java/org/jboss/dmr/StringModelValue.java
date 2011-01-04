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

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
final class StringModelValue extends ModelValue {

    private final String value;

    StringModelValue(final String value) {
        super(ModelType.STRING);
        this.value = value;
    }

    long asLong() {
        return Long.parseLong(value);
    }

    long asLong(final long defVal) {
        return Long.parseLong(value);
    }

    int asInt() {
        return Integer.parseInt(value);
    }

    int asInt(final int defVal) {
        return Integer.parseInt(value);
    }

    boolean asBoolean() {
        return Boolean.parseBoolean(value);
    }

    boolean asBoolean(final boolean defVal) {
        return Boolean.parseBoolean(value);
    }

    double asDouble() {
        return Double.parseDouble(value);
    }

    double asDouble(final double defVal) {
        return Double.parseDouble(value);
    }

    byte[] asBytes() {
        try {
            return value.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return value.getBytes();
        }
    }

    BigDecimal asDecimal() {
        return new BigDecimal(value);
    }

    String asString() {
        return value;
    }

    ModelType asType() {
        return ModelType.valueOf(value);
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(Object other) {
        return other instanceof StringModelValue && equals((StringModelValue)other);
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(StringModelValue other) {
        return this == other || other != null && value.equals(other.value);
    }

    public int hashCode() {
        return value.hashCode();
    }
}
