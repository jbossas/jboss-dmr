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

import static org.jboss.dmr.stream.ModelConstants.BACKSLASH;
import static org.jboss.dmr.stream.ModelConstants.BACKSPACE;
import static org.jboss.dmr.stream.ModelConstants.BYTES_VALUE;
import static org.jboss.dmr.stream.ModelConstants.CR;
import static org.jboss.dmr.stream.ModelConstants.COLON;
import static org.jboss.dmr.stream.ModelConstants.COMMA;
import static org.jboss.dmr.stream.ModelConstants.EQUAL;
import static org.jboss.dmr.stream.ModelConstants.EXPRESSION_VALUE;
import static org.jboss.dmr.stream.ModelConstants.FORMFEED;
import static org.jboss.dmr.stream.ModelConstants.QUOTE;
import static org.jboss.dmr.stream.ModelConstants.LIST_END;
import static org.jboss.dmr.stream.ModelConstants.LIST_START;
import static org.jboss.dmr.stream.ModelConstants.MINUS;
import static org.jboss.dmr.stream.ModelConstants.NL;
import static org.jboss.dmr.stream.ModelConstants.OBJECT_END;
import static org.jboss.dmr.stream.ModelConstants.OBJECT_START;
import static org.jboss.dmr.stream.ModelConstants.PLUS;
import static org.jboss.dmr.stream.ModelConstants.TAB;
import static org.jboss.dmr.stream.ModelConstants.TYPE_MODEL_VALUE;
import static org.jboss.dmr.stream.Utils.BASE64_DEC_TABLE;
import static org.jboss.dmr.stream.Utils.EMPTY_BYTES;
import static org.jboss.dmr.stream.Utils.INCORRECT_DATA;
import static org.jboss.dmr.stream.Utils.isBase64Char;
import static org.jboss.dmr.stream.Utils.isControl;
import static org.jboss.dmr.stream.Utils.isNumberChar;
import static org.jboss.dmr.stream.Utils.isWhitespace;

import java.io.IOException;
import java.io.Reader;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
final class JsonReaderImpl implements ModelReader {

    private static final char[] INFINITY = ModelConstants.INFINITY.toCharArray();
    private static final char[] NAN = ModelConstants.NAN.toCharArray();
    private static final char[] NULL = ModelConstants.NULL.toCharArray();
    private static final char[] TRUE = ModelConstants.TRUE.toCharArray();
    private static final char[] FALSE = ModelConstants.FALSE.toCharArray();
    private final Reader in;
    private final JsonGrammarAnalyzer analyzer;
    private char[] buffer = new char[ 1024 ];
    private int position;
    private int limit;
    private int numberOffset;
    private int numberLength;
    private int stringOffset;
    private int stringLength;
    private byte[] bytesValue;
    private ModelType typeValue;
    private Number numberValue;
    private boolean booleanValue;
    private String stringValue;
    private boolean closed;
    private boolean stringReadInAdvance;

    JsonReaderImpl( final Reader in ) {
        this.in = in;
        analyzer = new JsonGrammarAnalyzer();
    }

    @Override
    public void close() throws ModelException {
        if ( closed ) return; // idempotent
        closed = true;
        if ( !analyzer.finished ) {
            throw analyzer.newModelException( "Uncomplete DMR stream have been read" );
        }
    }

    @Override
    public String getString() {
        if ( !isCurrentEvent( ModelEvent.STRING ) ) {
            throw new IllegalStateException( "Current event isn't string" );
        }
        return stringValue;
    }

    public Number getNumber() {
        if ( !isCurrentEvent( ModelEvent.NUMBER ) ) {
            throw new IllegalStateException( "Current event isn't number" );
        }
        return numberValue;
    }

    @Override
    public byte[] getBytes() {
        if ( !isCurrentEvent( ModelEvent.BYTES ) ) {
            throw new IllegalStateException( "Current event isn't bytes" );
        }
        return bytesValue;
    }

    @Override
    public String getExpression() {
        if ( !isCurrentEvent( ModelEvent.EXPRESSION ) ) {
            throw new IllegalStateException( "Current event isn't expression" );
        }
        return stringValue;
    }

    @Override
    public ModelType getType() {
        if ( !isCurrentEvent( ModelEvent.TYPE ) ) {
            throw new IllegalStateException( "Current event isn't type" );
        }
        return typeValue;
    }

    @Override
    public boolean getBoolean() {
        if ( !isCurrentEvent( ModelEvent.BOOLEAN ) ) {
            throw new IllegalStateException( "Current event isn't boolean" );
        }
        return booleanValue;
    }

    @Override
    public boolean isListEnd() {
        return isCurrentEvent( ModelEvent.LIST_END );
    }

    @Override
    public boolean isListStart() {
        return isCurrentEvent( ModelEvent.LIST_START );
    }

    @Override
    public boolean isObjectEnd() {
        return isCurrentEvent( ModelEvent.OBJECT_END );
    }

    @Override
    public boolean isObjectStart() {
        return isCurrentEvent( ModelEvent.OBJECT_START );
    }

    @Override
    public boolean isPropertyEnd() {
        return isCurrentEvent( ModelEvent.PROPERTY_END );
    }

    @Override
    public boolean isPropertyStart() {
        return isCurrentEvent( ModelEvent.PROPERTY_START );
    }

    @Override
    public boolean isString() {
        return isCurrentEvent( ModelEvent.STRING );
    }

    @Override
    public boolean isNumber() {
        return isCurrentEvent( ModelEvent.NUMBER );
    }

    @Override
    public boolean isBytes() {
        return isCurrentEvent( ModelEvent.BYTES );
    }

    @Override
    public boolean isExpression() {
        return isCurrentEvent( ModelEvent.EXPRESSION );
    }

    @Override
    public boolean isType() {
        return isCurrentEvent( ModelEvent.TYPE );
    }

    @Override
    public boolean isBoolean() {
        return isCurrentEvent( ModelEvent.BOOLEAN );
    }

    @Override
    public boolean isUndefined() {
        return isCurrentEvent( ModelEvent.UNDEFINED );
    }

    private boolean isCurrentEvent( final ModelEvent event ) {
        ensureOpen();
        return analyzer.currentEvent == event;
    }

    @Override
    public boolean hasNext() {
        ensureOpen();
        return !analyzer.finished;
    }

    @Override
    public ModelEvent next() throws IOException, ModelException {
        ensureOpen();
        if ( analyzer.finished ) {
            throw new IllegalStateException( "No more DMR tokens available" );
        }
        // we read object keys in advance to detect bytes, types or expression types
        if ( stringReadInAdvance ) {
            stringReadInAdvance = false;
            analyzer.putString();
            return analyzer.currentEvent;
        }
        BigInteger bigIntegerValue;
        int currentChar;
        while ( true ) {
            currentChar = position < limit ? buffer[ position++ ] : read();
            switch ( currentChar ) {
                case QUOTE: {
                    analyzer.putString();
                    readString();
                    stringValue = new String( buffer, stringOffset, stringLength );
                    return analyzer.currentEvent;
                }
                case COLON: {
                    analyzer.putColon();
                }
                    break;
                case COMMA: {
                    analyzer.putComma();
                }
                    break;
                case '0': case '1': case '2': case '3': case '4':
                case '5': case '6': case '7': case '8': case '9':
                case MINUS: case PLUS: {
                    analyzer.putNumber();
                    if ( currentChar == PLUS || currentChar == MINUS ) {
                        ensureBufferAccess( 1, "Infinity or NaN or number" );
                        if ( buffer[ position ] == 'I' ) {
                            readString( INFINITY );
                            numberValue = currentChar == PLUS ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
                            return analyzer.currentEvent;
                        } else if ( buffer[ position ] == 'N' ) {
                            readString( NAN );
                            numberValue = Double.NaN;
                            return analyzer.currentEvent;
                        } else if ( !isNumberChar( buffer[ position ] ) ) {
                            throw newModelException( "Unexpected first character '" + buffer[ position ]
                                    + "' while reading DMR Infinity or NaN or number token" );
                        }
                    }
                    position--;
                    readNumber();
                    if ( isDecimalString() ) {
                        try {
                            numberValue = new BigDecimal( new String( buffer, numberOffset, numberLength ) );
                        } catch ( final NumberFormatException nfe ) {
                            throw newModelException( "Incorrect decimal value", nfe );
                        }
                    } else {
                        try {
                            bigIntegerValue = new BigInteger( new String( buffer, numberOffset, numberLength ) );
                            if ( bigIntegerValue.bitLength() <= 31 ) {
                                numberValue = bigIntegerValue.intValue();
                            } else if ( bigIntegerValue.bitLength() <= 63 ) {
                                numberValue = bigIntegerValue.longValue();
                            } else {
                                numberValue = bigIntegerValue;
                            }
                        } catch ( final NumberFormatException nfe ) {
                            throw newModelException( "Incorrect integer value", nfe );
                        }
                    }
                    return analyzer.currentEvent;
                }
                case 'I': {
                    position--;
                    analyzer.putNumber();
                    readString( INFINITY );
                    numberValue = Double.POSITIVE_INFINITY;
                    return analyzer.currentEvent;
                }
                case 'N': {
                    position--;
                    analyzer.putNumber();
                    readString( NAN );
                    numberValue = Double.NaN;
                    return analyzer.currentEvent;
                }
                case 'f':
                case 't': {
                    analyzer.putBoolean();
                    position--;
                    booleanValue = currentChar == 't';
                    readString( booleanValue ? TRUE : FALSE );
                    return analyzer.currentEvent;
                }
                case 'n': {
                    analyzer.putUndefined();
                    position--;
                    readString( NULL );
                    return analyzer.currentEvent;
                }
                case OBJECT_START: {
                    processWhitespaces();
                    currentChar = position < limit ? buffer[ position++ ] : read();
                    if ( currentChar == QUOTE ) {
                        readString();
                        stringValue = new String( buffer, stringOffset, stringLength );
                        if ( TYPE_MODEL_VALUE.equals( stringValue ) ) {
                            processWhitespaces();
                            readType();
                            analyzer.putType();
                        } else if ( BYTES_VALUE.equals( stringValue ) ) {
                            processWhitespaces();
                            readBytes();
                            analyzer.putBytes();
                        } else if ( EXPRESSION_VALUE.equals( stringValue ) ) {
                            processWhitespaces();
                            readExpression();
                            analyzer.putExpression();
                        } else {
                            stringReadInAdvance = true;
                            analyzer.putObjectStart();
                        }
                    } else {
                        position--;
                        analyzer.putObjectStart();
                    }
                    return analyzer.currentEvent;
                }
                case LIST_START: {
                    analyzer.putListStart();
                    return analyzer.currentEvent;
                }
                case OBJECT_END: {
                    analyzer.putObjectEnd();
                    return analyzer.currentEvent;
                }
                case LIST_END: {
                    analyzer.putListEnd();
                    return analyzer.currentEvent;
                }
                default: {
                    if ( isWhitespace( currentChar ) ) {
                        continue;
                    }
                    if ( currentChar >= 0 ) {
                        throw newModelException( "Unexpected character '" + ( char ) currentChar + "' while reading DMR stream" );
                    } else {
                        throw newModelException( "Unexpected EOF while reading DMR stream" );
                    }
                }
            }
        }
    }

    private void processWhitespaces() throws IOException {
        int currentChar;
        do {
            currentChar = read();
        } while ( isWhitespace( currentChar ) );
        if ( currentChar != -1 ) position--;
    }

    private void ensureData() throws IOException {
        if ( position == limit ) {
            if ( limit == buffer.length ) {
                limit = 0;
                position = 0;
            }
            fillBuffer();
        }
    }

    private void ensureBufferAccess( final int charsCount, final String expectedTokens ) throws IOException, ModelException {
        if ( position + charsCount <= limit ) return;
        if ( position < limit ) {
            System.arraycopy( buffer, position, buffer, 0, limit - position );
            limit -= position;
            position = 0;
        }
        fillBuffer();
        if ( position + charsCount > limit ) {
            throw newModelException( "Unexpected EOF while reading DMR " + expectedTokens + " token" );
        }
    }

    private void fillBuffer() throws IOException {
        int read;
        do {
            read = in.read( buffer, limit, buffer.length - limit );
            if ( read == -1 ) return;
            limit += read;
        } while ( limit != buffer.length );
    }

    private int read() throws IOException {
        ensureData();
        return position < limit ? buffer[ position++ ] : -1;
    }

    private void readString() throws IOException, ModelException {
        boolean escaped = false;
        char currentChar;
        stringLength = 0;
        boolean copy = false;
        while ( true ) {
            if ( stringLength == 0 ) stringOffset = position;
            while ( position != limit ) {
                currentChar = buffer[ position++ ];
                if ( escaped ) {
                    copy = true;
                    if ( currentChar == 'b' ) {
                        buffer[ stringOffset + stringLength++ ] = BACKSPACE;
                    } else if ( currentChar == 'f' ) {
                        buffer[ stringOffset + stringLength++ ] = FORMFEED;
                    } else if ( currentChar == 'n' ) {
                        buffer[ stringOffset + stringLength++ ] = NL;
                    } else if ( currentChar == 'r' ) {
                        buffer[ stringOffset + stringLength++ ] = CR;
                    } else if ( currentChar == 't' ) {
                        buffer[ stringOffset + stringLength++ ] = TAB;
                    } else if ( currentChar == 'u' ) {
                        if ( limit - position >= 4 ) {
                            try {
                                buffer[ stringOffset + stringLength++ ] = ( char ) Integer.parseInt( new String( buffer, position, 4 ), 16 );
                            } catch ( final NumberFormatException e ) {
                                throw newModelException( "Invalid DMR unicode sequence. Expecting 4 hexadecimal digits but got '" + new String( buffer, position, 4 ) + "'" );
                            }
                            position += 4;
                        } else {
                            if ( stringOffset != 0 ) {
                                if ( stringLength > 0 ) System.arraycopy( buffer, stringOffset, buffer, 0, stringLength );
                                position = stringLength;
                                limit = stringLength;
                                stringOffset = 0;
                            }
                            while ( limit + 4 > buffer.length ) doubleBuffer();
                            fillBuffer();
                            if ( limit - position < 4 ) {
                                throw newModelException( "Unexpected EOF while reading DMR string" );
                            }
                            try {
                                buffer[ stringOffset + stringLength++ ] = ( char ) Integer.parseInt( new String( buffer, position, 4 ), 16 );
                            } catch ( final NumberFormatException e ) {
                                throw newModelException( "Invalid DMR unicode sequence. Expecting 4 hexadecimal digits but got '" + new String( buffer, position, 4 ) + "'" );
                            }
                            position += 4;
                        }
                    } else {
                        buffer[ stringOffset + stringLength++ ] = currentChar;
                    }
                    escaped = false;
                } else {
                    if ( currentChar == QUOTE ) return;
                    if ( currentChar == BACKSLASH ) {
                        escaped = true;
                        continue;
                    }
                    if ( isControl( currentChar ) ) {
                        throw newModelException( "Unexpected control character '" + currentChar + "' while reading DMR string" );
                    }
                    if ( copy ) {
                        buffer[ stringOffset + stringLength ] = currentChar;
                    }
                    stringLength++;
                }
            }
            if ( stringOffset != 0 && stringLength > 0 ) {
                System.arraycopy( buffer, stringOffset, buffer, 0, stringLength );
                position = stringLength;
                limit = stringLength;
                stringOffset = 0;
            } else if ( stringOffset == 0 && limit == buffer.length ) doubleBuffer();
            ensureData();
            if ( position == limit ) {
                throw newModelException( "Unexpected EOF while reading DMR string" );
            }
        }
    }

    private void readString( final char[] expected ) throws IOException, ModelException {
        int i = 0;
        if ( position < limit - expected.length + 1 ) {
            // fast path
            for ( ; i < expected.length; i++ ) {
                if ( buffer[ position++ ] != expected[ i ] ) {
                    throw newModelException( "Unexpected character '" + buffer[ position - 1 ]
                            + "' while reading DMR " + new String( expected ) + " token" );
                }
            }
        } else {
            // slow path
            while ( true ) {
                while ( position < limit && i != expected.length ) {
                    if ( buffer[ position++ ] != expected[ i++ ] ) {
                        throw newModelException( "Unexpected character '" + buffer[ position - 1 ]
                                + "' while reading DMR " + new String( expected ) + " token" );
                    }
                }
                if ( i == expected.length ) return;
                ensureData();
                if ( position == limit ) {
                    throw newModelException( "Unexpected EOF while reading DMR " + new String( expected ) + " token" );
                }
            }
        }
    }

    private void readNumber() throws IOException, ModelException {
        numberOffset = position;
        while ( true ) {
            while ( position < limit ) {
                if ( isNumberChar( buffer[ position++ ] ) ) continue;
                position--;
                break;
            }
            numberLength = position - numberOffset;
            if ( position < limit ) break;
            if ( numberOffset != 0 ) {
                System.arraycopy( buffer, numberOffset, buffer, 0, numberLength );
                position = numberLength;
                limit = numberLength;
                numberOffset = 0;
            } else if ( limit == buffer.length ) doubleBuffer();
            ensureData();
            if ( position == limit ) {
                break;
            }
        }
    }

    private boolean isDecimalString() {
        final int numberLimit = numberOffset + numberLength;
        for ( int i = numberOffset; i < numberLimit; i++ ) {
            if ( buffer[ i ] == '.' ) return true;
        }
        return false;
    }

    private void doubleBuffer() {
        final char[] oldData = buffer;
        buffer = new char[ oldData.length * 2 ];
        System.arraycopy( oldData, 0, buffer, 0, oldData.length );
    }

    private ModelException newModelException( final String message ) throws ModelException {
        throw analyzer.newModelException( message );
    }

    private ModelException newModelException( final String message, final Throwable t ) throws ModelException {
        throw analyzer.newModelException( message, t );
    }

    private void ensureOpen() {
        if ( closed ) {
            throw new IllegalStateException( "DMR reader have been closed" );
        }
    }

    private void readType() throws IOException, ModelException {
        int currentChar = position < limit ? buffer[ position++ ] : read();
        if ( currentChar != COLON ) {
            if ( currentChar == -1 ) {
                throw newModelException( "Unexpected EOF while reading DMR type value" );
            } else {
                throw newModelException( "Unexpected character '" + ( char ) currentChar + "' while reading DMR type value" );
            }
        }
        processWhitespaces();
        currentChar = position < limit ? buffer[ position++ ] : read();
        if ( currentChar != QUOTE ) {
            if ( currentChar == -1 ) {
                throw newModelException( "Unexpected EOF while reading DMR type value" );
            } else {
                throw newModelException( "Unexpected character '" + ( char ) currentChar + "' while reading DMR type value" );
            }
        }
        readString();
        try {
            typeValue = ModelType.valueOf( new String( buffer, stringOffset, stringLength ) );
        } catch ( final IllegalArgumentException e ) {
            throw newModelException( e.getMessage(), e );
        }
        processWhitespaces();
        currentChar = position < limit ? buffer[ position++ ] : read();
        if ( currentChar != OBJECT_END ) {
            if ( currentChar == -1 ) {
                throw newModelException( "Unexpected EOF while reading DMR type value" );
            } else {
                throw newModelException( "Unexpected character '" + ( char ) currentChar + "' while reading DMR type value" );
            }
        }
    }

    private void readBytes() throws IOException, ModelException {
        int currentChar = position < limit ? buffer[ position++ ] : read();
        if ( currentChar != COLON ) {
            if ( currentChar == -1 ) {
                throw newModelException( "Unexpected EOF while reading DMR bytes value" );
            } else {
                throw newModelException( "Unexpected character '" + ( char ) currentChar + "' while reading DMR bytes value" );
            }
        }
        processWhitespaces();
        currentChar = position < limit ? buffer[ position++ ] : read();
        if ( currentChar != QUOTE ) {
            if ( currentChar == -1 ) {
                throw newModelException( "Unexpected EOF while reading DMR bytes value" );
            } else {
                throw newModelException( "Unexpected character '" + ( char ) currentChar + "' while reading DMR bytes value" );
            }
        }
        base64Canonicalize();
        base64Decode();
        processWhitespaces();
        currentChar = position < limit ? buffer[ position++ ] : read();
        if ( currentChar != OBJECT_END ) {
            if ( currentChar == -1 ) {
                throw newModelException( "Unexpected EOF while reading DMR bytes value" );
            } else {
                throw newModelException( "Unexpected character '" + ( char ) currentChar + "' while reading DMR bytes value" );
            }
        }
    }

    private void base64Canonicalize() throws IOException, ModelException {
        boolean escaped = false;
        char currentChar;
        stringLength = 0;
        boolean copy = false;
        while ( true ) {
            if ( stringLength == 0 ) stringOffset = position;
            while ( position != limit ) {
                currentChar = buffer[ position++ ];
                if ( escaped ) {
                    copy = true;
                    if ( currentChar != 'n' && currentChar != 'r' ) {
                        throw newModelException( "Unexpected character '" + currentChar + "' after escape character while reading DMR base64 string" );
                    }
                    escaped = false;
                } else {
                    if ( currentChar == QUOTE ) return;
                    if ( currentChar == BACKSLASH ) {
                        escaped = true;
                        continue;
                    }
                    if ( !isBase64Char( currentChar ) ) {
                        throw newModelException( "Unexpected character '" + currentChar + "' while reading DMR base64 string" );
                    }
                    if ( copy ) {
                        buffer[ stringOffset + stringLength ] = currentChar;
                    }
                    stringLength++;
                }
            }
            if ( stringOffset != 0 && stringLength > 0 ) {
                System.arraycopy( buffer, stringOffset, buffer, 0, stringLength );
                position = stringLength;
                limit = stringLength;
                stringOffset = 0;
            } else if ( stringOffset == 0 && limit == buffer.length ) doubleBuffer();
            ensureData();
            if ( position == limit ) {
                throw newModelException( "Unexpected EOF while reading DMR base64 string" );
            }
        }
    }

    private void base64Decode() throws IOException, ModelException {
        if ( stringLength == 0 ) {
            bytesValue = EMPTY_BYTES;
            return;
        }
        if ( stringLength % 4 != 0 )    {
            throw newModelException( "Encoded base64 value is not dividable by 4" );
        }
        int paddingSize = 0;
        for ( int i = 1; i <= 2; i++ ) {
            if ( buffer[ stringOffset + stringLength - i ] == EQUAL ) paddingSize = i;
        }
        bytesValue = new byte[ ( stringLength / 4 * 3 ) - paddingSize ];
        int j = 0;
        final int[] b = new int[ 4 ];
        for ( int i = 0; i < stringLength; i += 4 ) {
            b[ 0 ] = BASE64_DEC_TABLE[ buffer[ stringOffset + i ] ];
            b[ 1 ] = BASE64_DEC_TABLE[ buffer[ stringOffset + i + 1 ] ];
            b[ 2 ] = BASE64_DEC_TABLE[ buffer[ stringOffset + i + 2 ] ];
            b[ 3 ] = BASE64_DEC_TABLE[ buffer[ stringOffset + i + 3 ] ];
            bytesValue[ j++ ] = ( byte ) ( ( b[ 0 ] << 2 ) | ( b[ 1 ] >> 4 ) );
            if ( b[ 2 ] != INCORRECT_DATA ) {
                bytesValue[ j++ ] = ( byte ) ( ( b[ 1 ] << 4 ) | ( b[ 2 ] >> 2 ) );
                if ( b[ 3 ] != INCORRECT_DATA )  {
                    bytesValue[ j++ ] = ( byte ) ( ( b[ 2 ] << 6 ) | b[ 3 ] );
                }
            }
        }
    }

    private void readExpression() throws IOException, ModelException {
        int currentChar = position < limit ? buffer[ position++ ] : read();
        if ( currentChar != COLON ) {
            if ( currentChar == -1 ) {
                throw newModelException( "Unexpected EOF while reading DMR expression value" );
            } else {
                throw newModelException( "Unexpected character '" + ( char ) currentChar + "' while reading DMR expression value" );
            }
        }
        processWhitespaces();
        currentChar = position < limit ? buffer[ position++ ] : read();
        if ( currentChar != QUOTE ) {
            if ( currentChar == -1 ) {
                throw newModelException( "Unexpected EOF while reading DMR expression value" );
            } else {
                throw newModelException( "Unexpected character '" + ( char ) currentChar + "' while reading DMR expression value" );
            }
        }
        readString();
        stringValue = new String( buffer, stringOffset, stringLength );
        processWhitespaces();
        currentChar = position < limit ? buffer[ position++ ] : read();
        if ( currentChar != OBJECT_END ) {
            if ( currentChar == -1 ) {
                throw newModelException( "Unexpected EOF while reading DMR expression value" );
            } else {
                throw newModelException( "Unexpected character '" + ( char ) currentChar + "' while reading DMR expression value" );
            }
        }
    }

}
