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
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
final class StringModelValue extends ModelValue {

    private static final int THRESHOLD = 65535 / 3;

    private final String value;

    StringModelValue(final String value) {
        super(ModelType.STRING);
        this.value = value;
    }

    StringModelValue(final char typeChar, final DataInput in) throws IOException {
        super(ModelType.STRING);
        if (typeChar == 's') {
            value = in.readUTF();
        } else {
            // long string
            assert typeChar == 'S';
            final int length = in.readInt();
            final char[] chars = new char[length];
            int a, b, c;
            for (int i = 0; i < length; i ++) {
                a = in.readUnsignedByte();
                if (a < 0x80) {
                    chars[i] = (char) a;
                } else if (a < 0xc0) {
                    throw new UTFDataFormatException();
                } else if (a < 0xe0) {
                    b = in.readUnsignedByte();
                    if ((b & 0xc0) != 0x80) {
                        throw new UTFDataFormatException();
                    }
                    chars[i] = (char) ((a & 0x1f) << 6 | b & 0x3f);
                } else if (a < 0xf0) {
                    b = in.readUnsignedByte();
                    if ((b & 0xc0) != 0x80) {
                        throw new UTFDataFormatException();
                    }
                    c = in.readUnsignedByte();
                    if ((c & 0xc0) != 0x80) {
                        throw new UTFDataFormatException();
                    }
                    chars[i] = (char) ((a & 0x0f) << 12 | (b & 0x3f) << 6 | c & 0x3f);
                } else {
                    throw new UTFDataFormatException();
                }
            }
            value = new String(chars);
        }
    }

    @Override
    void writeExternal(final DataOutput out) throws IOException {
        final int length = value.length();
        if (length > THRESHOLD) {
            int l = 0;
            for (int i = 0; i < length; i ++) {
                final char c = value.charAt(i);
                if (c > 0 && c <= 0x7f) {
                    l ++;
                } else if (c <= 0x07ff) {
                    l += 2;
                } else {
                    l += 3;
                }
            }
            if (l > 65535) {
                out.write('S');
                out.writeInt(length);
                // Modified, modified UTF-8 (keep 0 as 0)
                for (int i = 0; i < length; i ++) {
                    final char c = value.charAt(i);
                    if (c > 0 && c <= 0x7f) {
                        out.writeByte(c);
                    } else if (c <= 0x07ff) {
                        out.writeByte(0xc0 | 0x1f & c >> 6);
                        out.writeByte(0x80 | 0x3f & c);
                    } else {
                        out.writeByte(0xe0 | 0x0f & c >> 12);
                        out.writeByte(0x80 | 0x3f & c >> 6);
                        out.writeByte(0x80 | 0x3f & c);
                    }
                }
                return;
            }
        }
        out.write(ModelType.STRING.typeChar);
        out.writeUTF(value);
    }

    @Override
    long asLong() {
        return Long.parseLong(value);
    }

    @Override
    long asLong(final long defVal) {
        return Long.parseLong(value);
    }

    @Override
    int asInt() {
        return Integer.parseInt(value);
    }

    @Override
    int asInt(final int defVal) {
        return Integer.parseInt(value);
    }

    @Override
    boolean asBoolean() {
        return Boolean.parseBoolean(value);
    }

    @Override
    boolean asBoolean(final boolean defVal) {
        return Boolean.parseBoolean(value);
    }

    @Override
    double asDouble() {
        return Double.parseDouble(value);
    }

    @Override
    double asDouble(final double defVal) {
        return Double.parseDouble(value);
    }

    @Override
    byte[] asBytes() {
        try {
            return value.getBytes("UTF-8");
        } catch (final UnsupportedEncodingException e) {
            return value.getBytes();
        }
    }

    @Override
    BigDecimal asBigDecimal() {
        return new BigDecimal(value);
    }

    @Override
    BigInteger asBigInteger() {
        return new BigInteger(value);
    }

    @Override
    String asString() {
        return value;
    }

    @Override
    ModelType asType() {
        return ModelType.valueOf(value);
    }

    @Override
    void format(final PrintWriter writer, final int indent, final boolean multiLine) {
        writer.append(quote(value));
    }

    @Override
    void formatAsJSON(final PrintWriter writer, final int indent, final boolean multiLine) {
        writer.append(jsonEscape(asString()));
    }

    /**
     * Determine whether this object is equal to another.
     * 
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(final Object other) {
        return other instanceof StringModelValue && equals((StringModelValue) other);
    }

    /**
     * Determine whether this object is equal to another.
     * 
     * @param other the other object
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    public boolean equals(final StringModelValue other) {
        return this == other || other != null && value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
