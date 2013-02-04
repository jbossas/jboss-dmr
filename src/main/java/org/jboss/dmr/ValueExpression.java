/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * A value expression.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public final class ValueExpression implements Externalizable {

    private static final long serialVersionUID = -277358532170444708L;
    private static final Field expressionStringField;

    private final String expressionString;

    static {
        expressionStringField = AccessController.doPrivileged(new PrivilegedAction<Field>() {
            public Field run() {
                final Field field;
                try {
                    field = ValueExpression.class.getDeclaredField("expressionString");
                } catch (NoSuchFieldException e) {
                    throw new NoSuchFieldError(e.getMessage());
                }
                field.setAccessible(true);
                return field;
            }
        });
    }

    /**
     * Quote a string so that it can be used in an expression as a literal string, instead of being expanded.
     *
     * @param string the string to quote
     * @return the quoted string
     */
    public static String quote(String string) {
        return string.replace("$", "$$");
    }

    /**
     * Construct a new instance.
     *
     * @param expressionString the expression string
     */
    public ValueExpression(final String expressionString) {
        if (expressionString == null) {
            throw new IllegalArgumentException("expressionString is null");
        }
        this.expressionString = expressionString;
    }

    /**
     * Serialize this instance.
     *
     * @param out the target stream
     * @throws IOException if a serialization error occurs
     */
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeUTF(expressionString);
    }

    /**
     * Deserialize this instance.
     *
     * @param in the source stream
     * @throws IOException if a serialization error occurs
     */
    public void readExternal(final ObjectInput in) throws IOException {
        String str = in.readUTF();
        try {
            expressionStringField.set(this, str);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Get the raw expression string.
     *
     * @return the raw expression string (will not be {@code null})
     */
    public String getExpressionString() {
        return expressionString;
    }

    /**
     * Get the hash code of the expression string.
     *
     * @return the hash code
     */
    public int hashCode() {
        return expressionString.hashCode();
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(Object other) {
        return other instanceof ValueExpression && equals((ValueExpression)other);
    }

    /**
     * Determine whether this object is equal to another.
     *
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(ValueExpression other) {
        return this == other || other != null && expressionString.equals(other.expressionString);
    }

    /**
     * Resolve this expression to a string value.
     *
     * @return the resolved value
     */
    public String resolveString() {
        return resolveString(ValueExpressionResolver.DEFAULT_RESOLVER);
    }

    /**
     * Resolve this expression to a string value.
     *
     * @param resolver the resolver to use
     * @return the resolved value
     */
    public String resolveString(final ValueExpressionResolver resolver) {
        return resolver.resolve(this);
    }

    /**
     * Resolve this expression to a {@code boolean} value.
     *
     * @return the resolved value
     */
    public boolean resolveBoolean() {
        final String value = resolveString();
        if (value.equalsIgnoreCase("true")) {
            return true;
        } else if (value.equalsIgnoreCase("false")) {
            return false;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Resolve this expression to a {@code boolean} value.
     *
     * @param resolver the resolver to use
     * @return the resolved value
     */
    public boolean resolveBoolean(final ValueExpressionResolver resolver) {
        final String value = resolveString(resolver);
        if (value.equalsIgnoreCase("true")) {
            return true;
        } else if (value.equalsIgnoreCase("false")) {
            return false;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Resolve this expression to an {@code int} value.
     *
     * @return the resolved value
     */
    public int resolveInt() {
        return Integer.parseInt(resolveString());
    }

    /**
     * Resolve this expression to an {@code int} value.
     *
     * @param resolver the resolver to use
     * @return the resolved value
     */
    public int resolveInt(final ValueExpressionResolver resolver) {
        return Integer.parseInt(resolveString(resolver));
    }

    /**
     * Resolve this expression to a {@code long} value.
     *
     * @return the resolved value
     */
    public long resolveLong() {
        return Long.parseLong(resolveString());
    }

    /**
     * Resolve this expression to a {@code long} value.
     *
     * @param resolver the resolver to use
     * @return the resolved value
     */
    public long resolveLong(final ValueExpressionResolver resolver) {
        return Long.parseLong(resolveString(resolver));
    }

    /**
     * Resolve this expression to a large integer value.
     *
     * @return the resolved value
     */
    public BigInteger resolveBigInteger() {
        return new BigInteger(resolveString());
    }

    /**
     * Resolve this expression to a large integer value.
     *
     * @param resolver the resolver to use
     * @return the resolved value
     */
    public BigInteger resolveBigInteger(final ValueExpressionResolver resolver) {
        return new BigInteger(resolveString(resolver));
    }

    /**
     * Resolve this expression to a decimal value.
     *
     * @return the resolved value
     */
    public BigDecimal resolveBigDecimal() {
        return new BigDecimal(resolveString());
    }

    /**
     * Resolve this expression to a decimal value.
     *
     * @param resolver the resolver to use
     * @return the resolved value
     */
    public BigDecimal resolveBigDecimal(final ValueExpressionResolver resolver) {
        return new BigDecimal(resolveString(resolver));
    }

    /**
     * Get a printable string representation of this object.
     *
     * @return the string
     */
    public String toString() {
        return "Expression \"" + expressionString + "\"";
    }
}
