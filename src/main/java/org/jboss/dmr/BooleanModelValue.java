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

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
final class BooleanModelValue extends ModelValue {

    private final boolean value;

    static final BooleanModelValue TRUE = new BooleanModelValue(true);
    static final BooleanModelValue FALSE = new BooleanModelValue(false);

    private static final byte[] TRUE_BYTES = new byte[] { 1 };
    private static final byte[] FALSE_BYTES = new byte[] { 0 };

    private BooleanModelValue(final boolean value) {
        super(ModelType.BOOLEAN);
        this.value = value;
    }

    long asLong() {
        return value ? 1 : 0;
    }

    long asLong(final long defVal) {
        return value ? 1 : 0;
    }

    int asInt() {
        return value ? 1 : 0;
    }

    int asInt(final int defVal) {
        return value ? 1 : 0;
    }

    boolean asBoolean() {
        return value;
    }

    boolean asBoolean(final boolean defVal) {
        return value;
    }

    double asDouble() {
        return value ? 1.0 : 0.0;
    }

    double asDouble(final double defVal) {
        return value ? 1.0 : 0.0;
    }

    byte[] asBytes() {
        return value ? TRUE_BYTES.clone() : FALSE_BYTES.clone();
    }

    BigDecimal asBigDecimal() {
        return value ? BigDecimal.ONE : BigDecimal.ZERO;
    }

    BigInteger asBigInteger() {
        return value ? BigInteger.ONE : BigInteger.ZERO;
    }

    String asString() {
        return Boolean.toString(value);
    }

    static BooleanModelValue valueOf(boolean value) {
        return value ? TRUE : FALSE;
    }

    public boolean equals(final Object other) {
        return other == this;
    }

    public int hashCode() {
        return Boolean.valueOf(value).hashCode();
    }
}
