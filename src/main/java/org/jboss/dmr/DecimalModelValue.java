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

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
final class DecimalModelValue extends ModelValue {

    private final BigDecimal value;

    public DecimalModelValue(final BigDecimal value) {
        super(ModelType.DECIMAL);
        this.value = value;
    }

    long asLong() {
        return value.longValue();
    }

    long asLong(final long defVal) {
        return value.longValue();
    }

    int asInt() {
        return value.intValue();
    }

    int asInt(final int defVal) {
        return value.intValue();
    }

    boolean asBoolean() {
        return !value.equals(BigDecimal.ZERO);
    }

    boolean asBoolean(final boolean defVal) {
        return !value.equals(BigDecimal.ZERO);
    }

    double asDouble() {
        return value.doubleValue();
    }

    double asDouble(final double defVal) {
        return value.doubleValue();
    }

    BigDecimal asDecimal() {
        return value;
    }

    String asString() {
        return value.toString();
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(Object other) {
        return other instanceof DecimalModelValue && equals((DecimalModelValue)other);
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(DecimalModelValue other) {
        return this == other || other != null && value.equals(other.value);
    }

    public int hashCode() {
        return value.hashCode();
    }
}
