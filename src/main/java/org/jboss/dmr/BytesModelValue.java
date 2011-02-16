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
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
final class BytesModelValue extends ModelValue {

    /**
     * JSON Key used to identify BytesModelValue.
     */
    public static final String TYPE_KEY = "BYTES_VALUE";

    private final byte[] bytes;

    BytesModelValue(final byte[] bytes) {
        super(ModelType.BYTES);
        this.bytes = bytes;
    }

    BytesModelValue(final DataInput in) throws IOException {
        super(ModelType.BYTES);
        byte[] b = new byte[in.readInt()];
        in.readFully(b);
        this.bytes = b;
    }

    @Override
    void writeExternal(final DataOutput out) throws IOException {
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    @Override
    long asLong() {
        final byte[] bytes = this.bytes;
        final int length = bytes.length;
        final int cnt = Math.min(8, length);
        long v = 0L;
        for (int i = 0; i < cnt; i++) {
            v <<= 8;
            v |= bytes[length - cnt + i] & 0xff;
        }
        return v;
    }

    @Override
    long asLong(final long defVal) {
        return asLong();
    }

    @Override
    int asInt() {
        final byte[] bytes = this.bytes;
        final int length = bytes.length;
        final int cnt = Math.min(4, length);
        int v = 0;
        for (int i = 0; i < cnt; i++) {
            v <<= 8;
            v |= bytes[length - cnt + i] & 0xff;
        }
        return v;
    }

    @Override
    int asInt(final int defVal) {
        return asInt();
    }

    @Override
    double asDouble() {
        return Double.longBitsToDouble(asLong());
    }

    @Override
    double asDouble(final double defVal) {
        return Double.longBitsToDouble(asLong());
    }

    @Override
    BigDecimal asBigDecimal() {
        return new BigDecimal(new BigInteger(bytes));
    }

    @Override
    BigInteger asBigInteger() {
        return new BigInteger(bytes);
    }

    @Override
    byte[] asBytes() {
        return bytes.clone();
    }

    @Override
    String asString() {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter, true);
        format(writer, 0, false);
        return stringWriter.toString();
    }

    @Override
    void formatAsJSON(final PrintWriter writer, final int indent, final boolean multiLine) {
        writer.append('{');
        if (multiLine) {
            indent(writer.append('\n'), indent + 1);
        } else {
            writer.append(' ');
        }
        writer.append(jsonEscape(TYPE_KEY));
        writer.append(" : ");
        try {
            writer.append(jsonEscape(Base64.encode(bytes)));
        } catch (final IOException e) {
            final IllegalArgumentException n = new IllegalArgumentException(e.getMessage());
            n.setStackTrace(e.getStackTrace());
            throw n;
        }
        if (multiLine) {
            indent(writer.append('\n'), indent);
        } else {
            writer.append(' ');
        }
        writer.append('}');
    }

    @Override
    void format(final PrintWriter writer, final int indent, final boolean multiLine) {
        writer.append("bytes {");
        if (multiLine) {
            writer.append('\n');
            indent(writer, indent + 1);
        } else {
            writer.append(' ');
        }
        for (int i = 0, length = bytes.length; i < length; i++) {
            final byte b = bytes[i];
            if (b >= 0 && b < 0x10) {
                writer.append("0x0").append(Integer.toHexString(b & 0xff));
            } else {
                writer.append("0x").append(Integer.toHexString(b & 0xff));
            }
            if (i != length - 1) {
                if (multiLine && (i & 7) == 7) {
                    indent(writer.append(",\n"), indent + 1);
                } else {
                    writer.append(", ");
                }
            }
        }
        if (multiLine) {
            indent(writer.append('\n'), indent);
        } else {
            writer.append(' ');
        }
        writer.append('}');
    }

    void formatMultiLine(final PrintWriter writer, final int indent) {
        final int length = bytes.length;
        format(writer, indent, length > 8);
    }

    /**
     * Determine whether this object is equal to another.
     * 
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(final Object other) {
        return other instanceof BytesModelValue && equals((BytesModelValue) other);
    }

    /**
     * Determine whether this object is equal to another.
     * 
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(final BytesModelValue other) {
        return this == other || other != null && Arrays.equals(bytes, other.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes) + 71;
    }
}
