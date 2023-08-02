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

import java.io.DataOutput;
import java.io.IOException;
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

    @Override
    void writeExternal(final DataOutput out) throws IOException {
        out.write(ModelType.BOOLEAN.typeChar);
        out.writeBoolean(value);
    }

    @Override
    long asLong() {
        return value ? 1 : 0;
    }

    @Override
    long asLong(final long defVal) {
        return value ? 1 : 0;
    }

    @Override
    int asInt() {
        return value ? 1 : 0;
    }

    @Override
    int asInt(final int defVal) {
        return value ? 1 : 0;
    }

    @Override
    boolean asBoolean() {
        return value;
    }

    @Override
    boolean asBoolean(final boolean defVal) {
        return value;
    }

    @Override
    double asDouble() {
        return value ? 1.0 : 0.0;
    }

    @Override
    double asDouble(final double defVal) {
        return value ? 1.0 : 0.0;
    }

    @Override
    byte[] asBytes() {
        return value ? TRUE_BYTES.clone() : FALSE_BYTES.clone();
    }

    @Override
    BigDecimal asBigDecimal() {
        return value ? BigDecimal.ONE : BigDecimal.ZERO;
    }

    @Override
    BigInteger asBigInteger() {
        return value ? BigInteger.ONE : BigInteger.ZERO;
    }

    @Override
    String asString() {
        return Boolean.toString(value);
    }

    @Override
    ValueExpression asExpression() {
        return new ValueExpression(asString());
    }

    static BooleanModelValue valueOf(final boolean value) {
        return value ? TRUE : FALSE;
    }

    @Override
    public boolean equals(final Object other) {
        return other == this;
    }

    @Override
    public int hashCode() {
        return Boolean.valueOf(value).hashCode();
    }

    @Override
    void write(final ModelWriter writer) throws IOException, ModelException {
        writer.writeBoolean(value);
    }

}
