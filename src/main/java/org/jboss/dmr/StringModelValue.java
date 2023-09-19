/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2023 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.dmr;

import org.jboss.dmr.stream.ModelException;
import org.jboss.dmr.stream.ModelWriter;

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
        if (value.equalsIgnoreCase("true")) {
            return true;
        } else if (value.equalsIgnoreCase("false")) {
            return false;
        } else {
            // TODO i18n
            throw new IllegalArgumentException("Cannot convert the string '" + value + "' to a boolean. Must be either 'true' or 'false', ignoring case");
        }
    }

    @Override
    boolean asBoolean(final boolean defVal) {
        return asBoolean();
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
    ValueExpression asExpression() {
        return new ValueExpression(ValueExpression.quote(value));
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

    @Override
    void write(final ModelWriter writer) throws IOException, ModelException {
        writer.writeString(value);
    }

}
