/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2016, Red Hat, Inc., and individual contributors
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

import static org.jboss.dmr.stream.ModelEvent.BOOLEAN;
import static org.jboss.dmr.stream.ModelEvent.BYTES;
import static org.jboss.dmr.stream.ModelEvent.EXPRESSION;
import static org.jboss.dmr.stream.ModelEvent.LIST_END;
import static org.jboss.dmr.stream.ModelEvent.LIST_START;
import static org.jboss.dmr.stream.ModelEvent.NUMBER;
import static org.jboss.dmr.stream.ModelEvent.OBJECT_END;
import static org.jboss.dmr.stream.ModelEvent.OBJECT_START;
import static org.jboss.dmr.stream.ModelEvent.PROPERTY_END;
import static org.jboss.dmr.stream.ModelEvent.PROPERTY_START;
import static org.jboss.dmr.stream.ModelEvent.STRING;
import static org.jboss.dmr.stream.ModelEvent.TYPE;
import static org.jboss.dmr.stream.ModelEvent.UNDEFINED;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;

import org.jboss.dmr.stream.ModelEvent;
import org.jboss.dmr.stream.ModelException;
import org.jboss.dmr.stream.ModelReader;
import org.jboss.dmr.stream.ModelStreamFactory;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
final class ModelNodeFactory {

    static final ModelNodeFactory INSTANCE = new ModelNodeFactory();

    private ModelNodeFactory() {
        // forbidden instantiation
    }

    ModelNode readFrom(final Reader input, final boolean jsonCompatible) throws IOException, ModelException {
        final ModelReader reader = ModelStreamFactory.getInstance(jsonCompatible).newModelReader(input);
        return readFrom(reader);
    }

    ModelNode readFrom(final String input, final boolean jsonCompatible) throws IOException, ModelException {
        final Reader reader = new StringReader(input);
        return readFrom(reader, jsonCompatible);
    }

    ModelNode readFrom(final InputStream input, final boolean jsonCompatible) throws IOException, ModelException {
        final ModelReader reader = ModelStreamFactory.getInstance(jsonCompatible).newModelReader(input);
        return readFrom(reader);
    }

    ModelNode readFrom(final InputStream input, final Charset charset, final boolean jsonCompatible) throws IOException, ModelException {
        final ModelReader reader = ModelStreamFactory.getInstance(jsonCompatible).newModelReader(input, charset);
        return readFrom(reader);
    }

    private ModelNode readFrom(final ModelReader modelReader) throws IOException, ModelException {
        final ModelEvent event = modelReader.next();
        if (event == STRING) {
            return readStringFrom(modelReader);
        } else if (event == NUMBER) {
            return readNumberFrom(modelReader);
        } else if (event == BYTES) {
            return readBytesFrom(modelReader);
        } else if (event == EXPRESSION) {
            return readExpressionFrom(modelReader);
        } else if (event == TYPE) {
            return readTypeFrom(modelReader);
        } else if (event == BOOLEAN) {
            return readBooleanFrom(modelReader);
        } else if (event == UNDEFINED) {
            return readUndefinedFrom(modelReader);
        } else if (event == OBJECT_START) {
            return readObjectFrom(modelReader);
        } else if (event == LIST_START) {
            return readListFrom(modelReader);
        } else if (event == PROPERTY_START) {
            return readPropertyFrom(modelReader);
        } else {
            throw new IllegalStateException(); // never happens
        }
    }

    private ModelNode readStringFrom(final ModelReader modelReader) throws IOException, ModelException {
        return new ModelNode().set(modelReader.getString());
    }

    private ModelNode readNumberFrom(final ModelReader modelReader) throws IOException, ModelException {
        final Number number = modelReader.getNumber();
        if (number instanceof Integer) {
            return new ModelNode().set((Integer)number);
        } else if (number instanceof Long) {
            return new ModelNode().set((Long)number);
        } else if (number instanceof Double) {
            return new ModelNode().set((Double)number);
        } else if (number instanceof BigInteger) {
            return new ModelNode().set((BigInteger)number);
        } else if (number instanceof BigDecimal) {
            return new ModelNode().set((BigDecimal)number);
        } else {
            throw new IllegalStateException(); // never happens
        }
    }

    private ModelNode readBytesFrom(final ModelReader modelReader) throws IOException, ModelException {
        return new ModelNode().set(modelReader.getBytes());
    }

    private ModelNode readExpressionFrom(final ModelReader modelReader) throws IOException, ModelException {
        return new ModelNode().setExpression(modelReader.getExpression());
    }

    private ModelNode readTypeFrom(final ModelReader modelReader) throws IOException, ModelException {
        return new ModelNode().set(ModelType.valueOf(modelReader.getType().toString()));
    }

    private ModelNode readBooleanFrom(final ModelReader modelReader) throws IOException, ModelException {
        return new ModelNode().set(modelReader.getBoolean());
    }

    private ModelNode readUndefinedFrom(final ModelReader modelReader) throws IOException, ModelException {
        return new ModelNode();
    }

    private ModelNode readListFrom(final ModelReader modelReader) throws IOException, ModelException {
        final ModelNode listNode = new ModelNode();
        listNode.setEmptyList();
        ModelEvent event = modelReader.next();
        ModelNode value;
        while ( event != LIST_END ) {
            if (event == STRING) {
                value = readStringFrom(modelReader);
            } else if (event == NUMBER) {
                value = readNumberFrom(modelReader);
            } else if (event == BYTES) {
                value = readBytesFrom(modelReader);
            } else if (event == EXPRESSION) {
                value = readExpressionFrom(modelReader);
            } else if (event == TYPE) {
                value = readTypeFrom(modelReader);
            } else if (event == BOOLEAN) {
                value = readBooleanFrom(modelReader);
            } else if (event == UNDEFINED) {
                value = readUndefinedFrom(modelReader);
            } else if (event == OBJECT_START) {
                value = readObjectFrom(modelReader);
            } else if (event == LIST_START) {
                value = readListFrom(modelReader);
            } else if (event == PROPERTY_START) {
                value = readPropertyFrom(modelReader);
            } else {
                throw new IllegalStateException(); // never happens
            }
            listNode.addNoCopy(value);
            event = modelReader.next();
        }
        return listNode;
    }

    private ModelNode readObjectFrom(final ModelReader modelReader) throws IOException, ModelException {
        final ModelNode objectNode = new ModelNode();
        objectNode.setEmptyObject();
        ModelEvent event = modelReader.next();
        String key;
        ModelNode value;
        while (event != OBJECT_END) {
            key = modelReader.getString();
            event = modelReader.next();
            if (event == STRING) {
                value = readStringFrom(modelReader);
            } else if (event == NUMBER) {
                value = readNumberFrom(modelReader);
            } else if (event == BYTES) {
                value = readBytesFrom(modelReader);
            } else if (event == EXPRESSION) {
                value = readExpressionFrom(modelReader);
            } else if (event == TYPE) {
                value = readTypeFrom(modelReader);
            } else if (event == BOOLEAN) {
                value = readBooleanFrom(modelReader);
            } else if (event == UNDEFINED) {
                value = readUndefinedFrom(modelReader);
            } else if (event == OBJECT_START) {
                value = readObjectFrom(modelReader);
            } else if (event == LIST_START) {
                value = readListFrom(modelReader);
            } else if (event == PROPERTY_START) {
                value = readPropertyFrom(modelReader);
            } else {
                throw new IllegalStateException(); // never happens
            }
            objectNode.get(key).setNoCopy(value);
            event = modelReader.next();
        }
        return objectNode;
    }

    private ModelNode readPropertyFrom(final ModelReader modelReader) throws IOException, ModelException {
        final ModelNode propertyNode = new ModelNode();
        ModelEvent event = modelReader.next();
        String key;
        ModelNode value;
        while (event != PROPERTY_END) {
            key = modelReader.getString();
            event = modelReader.next();
            if (event == STRING) {
                value = readStringFrom(modelReader);
            } else if (event == NUMBER) {
                value = readNumberFrom(modelReader);
            } else if (event == BYTES) {
                value = readBytesFrom(modelReader);
            } else if (event == EXPRESSION) {
                value = readExpressionFrom(modelReader);
            } else if (event == TYPE) {
                value = readTypeFrom(modelReader);
            } else if (event == BOOLEAN) {
                value = readBooleanFrom(modelReader);
            } else if (event == UNDEFINED) {
                value = readUndefinedFrom(modelReader);
            } else if (event == OBJECT_START) {
                value = readObjectFrom(modelReader);
            } else if (event == LIST_START) {
                value = readListFrom(modelReader);
            } else if (event == PROPERTY_START) {
                value = readPropertyFrom(modelReader);
            } else {
                throw new IllegalStateException(); // never happens
            }
            propertyNode.setNoCopy(key, value);
            event = modelReader.next();
        }
        return propertyNode;
    }

}
