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

package org.jboss.dmr.stream;

import static org.jboss.dmr.stream.ModelEvent.BIG_DECIMAL;
import static org.jboss.dmr.stream.ModelEvent.BIG_INTEGER;
import static org.jboss.dmr.stream.ModelEvent.BOOLEAN;
import static org.jboss.dmr.stream.ModelEvent.BYTES;
import static org.jboss.dmr.stream.ModelEvent.DOUBLE;
import static org.jboss.dmr.stream.ModelEvent.EXPRESSION;
import static org.jboss.dmr.stream.ModelEvent.INT;
import static org.jboss.dmr.stream.ModelEvent.LIST_END;
import static org.jboss.dmr.stream.ModelEvent.LIST_START;
import static org.jboss.dmr.stream.ModelEvent.LONG;
import static org.jboss.dmr.stream.ModelEvent.OBJECT_END;
import static org.jboss.dmr.stream.ModelEvent.OBJECT_START;
import static org.jboss.dmr.stream.ModelEvent.PROPERTY_END;
import static org.jboss.dmr.stream.ModelEvent.PROPERTY_START;
import static org.jboss.dmr.stream.ModelEvent.STRING;
import static org.jboss.dmr.stream.ModelEvent.TYPE;
import static org.jboss.dmr.stream.ModelEvent.UNDEFINED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.jboss.dmr.ModelType;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
abstract class AbstractModelStreamsTestCase {

    static void assertClosedState( final ModelWriter writer ) throws IOException, ModelException {
        try {
            writer.flush();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writeListEnd();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writeListStart();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writeString( "" );
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writeBytes( new byte[] {} );
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writeExpression( "" );
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writeType( ModelType.LIST );
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
        try {
            writer.writeUndefined();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR writer have been closed", e.getMessage() );
        }
    }

    static void assertInitialState( final ModelReader reader ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertNotStringException( reader );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    static void assertObjectStartState( final ModelReader reader ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( OBJECT_START, reader.next() );
        assertTrue( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertNotStringException( reader );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    static void assertObjectEndState( final ModelReader reader ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( OBJECT_END, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertTrue( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertNotStringException( reader );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    static void assertListStartState( final ModelReader reader ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( LIST_START, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertTrue( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertNotStringException( reader );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    static void assertListEndState( final ModelReader reader ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( LIST_END, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertTrue( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertNotStringException( reader );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    static void assertPropertyStartState( final ModelReader reader ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( PROPERTY_START, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertTrue( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertNotStringException( reader );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    static void assertPropertyEndState( final ModelReader reader ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( PROPERTY_END, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertTrue( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertNotStringException( reader );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    static void assertFinalState( final ModelReader reader ) throws IOException, ModelException {
        assertNotStringException( reader );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
        assertFalse( reader.hasNext() );
        try {
            reader.next();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "No more DMR tokens available", e.getMessage() );
        }
    }

    static void assertFinalStringState( final ModelReader reader ) throws IOException, ModelException {
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
        assertFalse( reader.hasNext() );
        try {
            reader.next();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "No more DMR tokens available", e.getMessage() );
        }
    }

    static void assertFinalBytesState( final ModelReader reader ) throws IOException, ModelException {
        assertNotStringException( reader );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
        assertFalse( reader.hasNext() );
        try {
            reader.next();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "No more DMR tokens available", e.getMessage() );
        }
    }

    static void assertFinalExpressionState( final ModelReader reader ) throws IOException, ModelException {
        assertNotStringException( reader );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotTypeException( reader );
        assertFalse( reader.hasNext() );
        try {
            reader.next();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "No more DMR tokens available", e.getMessage() );
        }
    }

    static void assertFinalTypeState( final ModelReader reader ) throws IOException, ModelException {
        assertNotStringException( reader );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertFalse( reader.hasNext() );
        try {
            reader.next();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "No more DMR tokens available", e.getMessage() );
        }
    }

    static void assertFinalBooleanState( final ModelReader reader ) throws IOException, ModelException {
        assertNotStringException( reader );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotTypeException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertFalse( reader.hasNext() );
        try {
            reader.next();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "No more DMR tokens available", e.getMessage() );
        }
    }

    static void assertFinalNumberState( final ModelReader reader ) throws IOException, ModelException {
        assertNotStringException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
        assertFalse( reader.hasNext() );
        try {
            reader.next();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "No more DMR tokens available", e.getMessage() );
        }
    }

    static void assertClosedState( final ModelReader reader ) throws IOException, ModelException {
        try {
            reader.hasNext();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.next();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isListEnd();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isListStart();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isObjectEnd();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isObjectStart();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isPropertyEnd();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isPropertyStart();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isString();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.getString();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isInt();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.getInt();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isLong();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.getLong();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isDouble();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.getDouble();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isBigInteger();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.getBigInteger();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isBigDecimal();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.getBigDecimal();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isBytes();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.getBytes();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isExpression();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.getExpression();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isType();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.getType();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isBoolean();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.getBoolean();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
        try {
            reader.isUndefined();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "DMR reader have been closed", e.getMessage() );
        }
    }

    static void assertStringState( final ModelReader reader, final String expected ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( STRING, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertTrue( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertEquals( expected, reader.getString() );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    static void assertExpressionState( final ModelReader reader, final String expected ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( EXPRESSION, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertTrue( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertEquals( expected, reader.getExpression() );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotStringException( reader );
        assertNotTypeException( reader );
    }

    static void assertBytesState( final ModelReader reader, final byte[] expected ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( BYTES, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertTrue( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        final byte[] current = reader.getBytes();
        assertTrue( current.length == expected.length );
        for ( int i = 0; i < current.length; i++ ) {
            assertTrue( current[ i ] == expected[ i ] );
        }
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotStringException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    static void assertTypeState( final ModelReader reader, final ModelType expected ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( TYPE, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertTrue( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertEquals( expected, reader.getType() );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotStringException( reader );
        assertNotExpressionException( reader );
        assertNotBytesException( reader );
    }

    static void assertModelException( final ModelReader reader, final String expected ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        try {
            reader.next();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( expected, e.getMessage() );
        }
    }

    static void assertNumberState( final ModelReader reader, final Number expected ) throws IOException, ModelException {
        if ( expected instanceof Integer ) {
            assertIntState( reader );
            assertEquals( expected, reader.getInt() );
        } else if ( expected instanceof Long ) {
            assertLongState( reader );
            assertEquals( expected, reader.getLong() );
        } else if ( expected instanceof Double ) {
            assertDoubleState( reader );
            assertEquals( expected, reader.getDouble() );
        } else if ( expected instanceof BigInteger ) {
            assertBigIntegerState( reader );
            assertEquals( expected, reader.getBigInteger() );
        } else if ( expected instanceof BigDecimal ) {
            assertBigDecimalState( reader );
            assertEquals( expected, reader.getBigDecimal() );
        }
    }

    static void assertBooleanState( final ModelReader reader, final boolean expected ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( BOOLEAN, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertTrue( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertEquals( expected, reader.getBoolean() );
        assertNotStringException( reader );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    static void assertUndefinedState( final ModelReader reader ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( UNDEFINED, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertTrue( reader.isUndefined() );
        assertNotStringException( reader );
        assertNotIntException( reader );
        assertNotLongException( reader );
        assertNotDoubleException( reader );
        assertNotBigIntegerException( reader );
        assertNotBigDecimalException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    private static void assertIntState( final ModelReader reader ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( INT, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertTrue( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertNotStringException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    private static void assertLongState( final ModelReader reader ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( LONG, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertTrue( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertNotStringException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    private static void assertDoubleState( final ModelReader reader ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( DOUBLE, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertTrue( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertNotStringException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    private static void assertBigIntegerState( final ModelReader reader ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( BIG_INTEGER, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertTrue( reader.isBigInteger() );
        assertFalse( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertNotStringException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    private static void assertBigDecimalState( final ModelReader reader ) throws IOException, ModelException {
        assertTrue( reader.hasNext() );
        assertEquals( BIG_DECIMAL, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isListStart() );
        assertFalse( reader.isListEnd() );
        assertFalse( reader.isPropertyStart() );
        assertFalse( reader.isPropertyEnd() );
        assertFalse( reader.isString() );
        assertFalse( reader.isInt() );
        assertFalse( reader.isLong() );
        assertFalse( reader.isDouble() );
        assertFalse( reader.isBigInteger() );
        assertTrue( reader.isBigDecimal() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isBytes() );
        assertFalse( reader.isExpression() );
        assertFalse( reader.isType() );
        assertFalse( reader.isUndefined() );
        assertNotStringException( reader );
        assertNotBooleanException( reader );
        assertNotBytesException( reader );
        assertNotExpressionException( reader );
        assertNotTypeException( reader );
    }

    private static void assertNotStringException( final ModelReader reader ) throws IOException {
        try {
            reader.getString();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "Current event isn't string", e.getMessage() );
        }
    }

    private static void assertNotBooleanException( final ModelReader reader ) throws IOException {
        try {
            reader.getBoolean();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "Current event isn't boolean", e.getMessage() );
        }
    }

    private static void assertNotBytesException( final ModelReader reader ) throws IOException {
        try {
            reader.getBytes();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "Current event isn't bytes", e.getMessage() );
        }
    }

    private static void assertNotExpressionException( final ModelReader reader ) throws IOException {
        try {
            reader.getExpression();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "Current event isn't expression", e.getMessage() );
        }
    }

    private static void assertNotTypeException( final ModelReader reader ) throws IOException {
        try {
            reader.getType();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "Current event isn't type", e.getMessage() );
        }
    }

    private static void assertNotIntException( final ModelReader reader ) throws IOException {
        try {
            reader.getInt();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "Current event isn't int", e.getMessage() );
        }
    }

    private static void assertNotLongException( final ModelReader reader ) throws IOException {
        try {
            reader.getLong();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "Current event isn't long", e.getMessage() );
        }
    }

    private static void assertNotDoubleException( final ModelReader reader ) throws IOException {
        try {
            reader.getDouble();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "Current event isn't double", e.getMessage() );
        }
    }

    private static void assertNotBigIntegerException( final ModelReader reader ) throws IOException {
        try {
            reader.getBigInteger();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "Current event isn't big integer", e.getMessage() );
        }
    }

    private static void assertNotBigDecimalException( final ModelReader reader ) throws IOException {
        try {
            reader.getBigDecimal();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "Current event isn't big decimal", e.getMessage() );
        }
    }

    protected ModelReader getModelReader( final String data ) throws IOException {
        throw new UnsupportedOperationException();
    }

    protected ModelWriter getModelWriter() throws IOException {
        throw new UnsupportedOperationException();
    }

}
