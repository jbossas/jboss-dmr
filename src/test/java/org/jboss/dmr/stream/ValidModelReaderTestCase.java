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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class ValidModelReaderTestCase extends AbstractModelStreamsTestCase {

    @Override
    public ModelReader getModelReader( final String data ) throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream( data.getBytes() );
        return ModelStreamFactory.getInstance( false ).newModelReader( bais );
    }

    @Test
    public void escapesEncoding() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "[\"\\\"\\\\\"]" );
        assertListStartState( reader );
        assertStringState( reader, "\"\\" );
        assertListEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void emptyObjectWithWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{ \t\r\n}" );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void emptyObjectWithoutWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{}" );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void simpleObjectWithWhitespacesWithoutComma() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{ \t\r\n\"a\" \t\r\n=> \t\r\n\"b\" \t\r\n}" );
        assertObjectStartState( reader );
        assertStringState( reader, "a" );
        assertStringState( reader, "b" );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void simpleObjectWithoutWhitespacesWithoutComma() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{\"a\"=>\"b\"}" );
        assertObjectStartState( reader );
        assertStringState( reader, "a" );
        assertStringState( reader, "b" );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void simpleObjectWithWhitespacesWithComma() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{ \t\r\n\"a\" \t\r\n=> \t\r\n\"b\" \t\r\n, \t\r\n}" );
        assertObjectStartState( reader );
        assertStringState( reader, "a" );
        assertStringState( reader, "b" );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void simpleObjectWithoutWhitespacesWithComma() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{\"a\"=>\"b\",}" );
        assertObjectStartState( reader );
        assertStringState( reader, "a" );
        assertStringState( reader, "b" );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void moreComplexObjectWithoutWhitespaces() throws IOException, ModelException {
        final String data = "{\"0\"=>\"0\",\"1\"=>1,\"2\"=>2L,\"3\"=>3.0,\"4\"=>true,\"5\"=>undefined,\"6\"=>"
            + "biginteger60000000000000000000000000000,\"7\"=>bigdecimal70000000000000000000000000000.000000000000001,"
            + "\"8\"=>expression\"env.JAVA_HOME\",\"9\"=>bytes{0x00,0x01,0x02,0x03},\"10\"=>{\"\"=>false},\"11\"=>"
            + "[OBJECT],\"12\"=>(\"propKey\"=>PROPERTY)}";
        final ModelReader reader = getModelReader( data );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertStringState( reader, "0" );
        assertStringState( reader, "1" );
        assertNumberState( reader, 1 );
        assertStringState( reader, "2" );
        assertNumberState( reader, 2L );
        assertStringState( reader, "3" );
        assertNumberState( reader, 3. );
        assertStringState( reader, "4" );
        assertBooleanState( reader, true );
        assertStringState( reader, "5" );
        assertUndefinedState( reader );
        assertStringState( reader, "6" );
        assertNumberState( reader, new BigInteger( "60000000000000000000000000000" ) );
        assertStringState( reader, "7" );
        assertNumberState( reader, new BigDecimal( "70000000000000000000000000000.000000000000001" ) );
        assertStringState( reader, "8" );
        assertExpressionState( reader, "env.JAVA_HOME" );
        assertStringState( reader, "9" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertStringState( reader, "10" );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertStringState( reader, "11" );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertListEndState( reader );
        assertStringState( reader, "12" );
        assertPropertyStartState( reader );
        assertStringState( reader, "propKey" );
        assertTypeState( reader, ModelType.PROPERTY );
        assertPropertyEndState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void moreComplexObjectWithoutWhitespacesWithComma() throws IOException, ModelException {
        final String data = "{\"0\"=>\"0\",\"1\"=>1,\"2\"=>2L,\"3\"=>3.0,\"4\"=>true,\"5\"=>undefined,\"6\"=>"
                + "biginteger60000000000000000000000000000,\"7\"=>bigdecimal70000000000000000000000000000.000000000000001,"
                + "\"8\"=>expression\"env.JAVA_HOME\",\"9\"=>bytes{0x00,0x01,0x02,0x03,},\"10\"=>{\"\"=>false,},\"11\"=>"
                + "[OBJECT,],\"12\"=>(\"propKey\"=>PROPERTY)}";
        final ModelReader reader = getModelReader( data );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertStringState( reader, "0" );
        assertStringState( reader, "1" );
        assertNumberState( reader, 1 );
        assertStringState( reader, "2" );
        assertNumberState( reader, 2L );
        assertStringState( reader, "3" );
        assertNumberState( reader, 3. );
        assertStringState( reader, "4" );
        assertBooleanState( reader, true );
        assertStringState( reader, "5" );
        assertUndefinedState( reader );
        assertStringState( reader, "6" );
        assertNumberState( reader, new BigInteger( "60000000000000000000000000000" ) );
        assertStringState( reader, "7" );
        assertNumberState( reader, new BigDecimal( "70000000000000000000000000000.000000000000001" ) );
        assertStringState( reader, "8" );
        assertExpressionState( reader, "env.JAVA_HOME" );
        assertStringState( reader, "9" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertStringState( reader, "10" );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertStringState( reader, "11" );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertListEndState( reader );
        assertStringState( reader, "12" );
        assertPropertyStartState( reader );
        assertStringState( reader, "propKey" );
        assertTypeState( reader, ModelType.PROPERTY );
        assertPropertyEndState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void moreComplexObjectWithWhitespaces() throws IOException, ModelException {
        final String data = "{ \"0\" => \"0\" , \t\"1\" => \t1 \t, \r\"2\" \r=> \r2L \r, \n\"3\" \n=> \n 3.0 \n, \"4\" => true , \"5\" => undefined ,\"6\"=>"
                + "big integer 60000000000000000000000000000, \"7\" => \t\r\nbig \t\r\ndecimal \t\r\n70000000000000000000000000000.000000000000001 \t\r\n,"
                + "\"8\" => expression \t\r\n\"env.JAVA_HOME\", \"9\" => \t\r\nbytes \t\r\n{ \t\r\n0x00 \t\r\n, \t\r\n0x01 \t\r\n, \t\r\n0x02"
                + " \t\r\n, \t\r\n0x03},\"10\" => {\"\"=>false \t\r\n},\"11\" \t\r\n=> \t\r\n[ \t\r\nOBJECT ] , \"12\" => ( \t\r\n\"propKey\" => PROPERTY ) \t\r\n}";
        final ModelReader reader = getModelReader( data );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertStringState( reader, "0" );
        assertStringState( reader, "1" );
        assertNumberState( reader, 1 );
        assertStringState( reader, "2" );
        assertNumberState( reader, 2L );
        assertStringState( reader, "3" );
        assertNumberState( reader, 3. );
        assertStringState( reader, "4" );
        assertBooleanState( reader, true );
        assertStringState( reader, "5" );
        assertUndefinedState( reader );
        assertStringState( reader, "6" );
        assertNumberState( reader, new BigInteger( "60000000000000000000000000000" ) );
        assertStringState( reader, "7" );
        assertNumberState( reader, new BigDecimal( "70000000000000000000000000000.000000000000001" ) );
        assertStringState( reader, "8" );
        assertExpressionState( reader, "env.JAVA_HOME" );
        assertStringState( reader, "9" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertStringState( reader, "10" );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertStringState( reader, "11" );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertListEndState( reader );
        assertStringState( reader, "12" );
        assertPropertyStartState( reader );
        assertStringState( reader, "propKey" );
        assertTypeState( reader, ModelType.PROPERTY );
        assertPropertyEndState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void moreComplexObjectWithWhitespacesWithComma() throws IOException, ModelException {
        final String data = "{ \"0\" => \"0\" , \t\"1\" => \t1 \t, \r\"2\" \r=> \r2L \r, \n\"3\" \n=> \n 3.0 \n, \"4\" => true , \"5\" => undefined ,\"6\"=>"
                + "big integer 60000000000000000000000000000, \"7\" => \t\r\nbig \t\r\ndecimal \t\r\n70000000000000000000000000000.000000000000001 \t\r\n,"
                + "\"8\" => expression \t\r\n\"env.JAVA_HOME\", \"9\" => \t\r\nbytes \t\r\n{ \t\r\n0x00 \t\r\n, \t\r\n0x01 \t\r\n, \t\r\n0x02"
                + " \t\r\n, \t\r\n0x03 \t\r\n, \t\r\n},\"10\" => {\"\"=>false \t\r\n, \t\r\n},\"11\" \t\r\n=> \t\r\n[ \t\r\nOBJECT, ] , \"12\" => ( \t\r\n\"propKey\" => PROPERTY ) \t\r\n}";
        final ModelReader reader = getModelReader( data );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertStringState( reader, "0" );
        assertStringState( reader, "1" );
        assertNumberState( reader, 1 );
        assertStringState( reader, "2" );
        assertNumberState( reader, 2L );
        assertStringState( reader, "3" );
        assertNumberState( reader, 3. );
        assertStringState( reader, "4" );
        assertBooleanState( reader, true );
        assertStringState( reader, "5" );
        assertUndefinedState( reader );
        assertStringState( reader, "6" );
        assertNumberState( reader, new BigInteger( "60000000000000000000000000000" ) );
        assertStringState( reader, "7" );
        assertNumberState( reader, new BigDecimal( "70000000000000000000000000000.000000000000001" ) );
        assertStringState( reader, "8" );
        assertExpressionState( reader, "env.JAVA_HOME" );
        assertStringState( reader, "9" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertStringState( reader, "10" );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertStringState( reader, "11" );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertListEndState( reader );
        assertStringState( reader, "12" );
        assertPropertyStartState( reader );
        assertStringState( reader, "propKey" );
        assertTypeState( reader, ModelType.PROPERTY );
        assertPropertyEndState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostComplexObject() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{\"0\"=>{\"String\"=>\"s\",\"boolean\"=>false},\"1\"=>[undefined,true,7,{}]}" );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertObjectStartState( reader );
        assertStringState( reader, "String" );
        assertStringState( reader, "s" );
        assertStringState( reader, "boolean" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertStringState( reader, "1" );
        assertListStartState( reader );
        assertUndefinedState( reader );
        assertBooleanState( reader, true );
        assertNumberState( reader, 7 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void emptyListWithWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "[ \t\r\n]" );
        assertListStartState( reader );
        assertListEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void emptyListWithoutWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "[]" );
        assertListStartState( reader );
        assertListEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void simpleListWithWhitespacesWithoutComma() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "[ { \t\r\n\"a\" \t\r\n=> \t\r\n\"b\" \t\r\n} \t\r\n]" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "a" );
        assertStringState( reader, "b" );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void simpleListWithoutWhitespacesWithoutComma() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "[{\"a\"=>\"b\"}]" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "a" );
        assertStringState( reader, "b" );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void simpleListWithWhitespacesWithComma() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "[ { \t\r\n\"a\" \t\r\n=> \t\r\n\"b\" \t\r\n} \t\r\n, \t\r\n]" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "a" );
        assertStringState( reader, "b" );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void simpleListWithoutWhitespacesWithComma() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "[{\"a\"=>\"b\"},]" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "a" );
        assertStringState( reader, "b" );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void moreComplexListWithWhitespaces() throws IOException, ModelException {
        final String data = "[ \"0\" \r\n, 1 \r\n, 2L \r\n, 3.0 \r\n, \r\n{ \r\n} \r\n, \r\n[ \r\n] \r\n, \r\n ( \"\" \r\n=> \t\r\nEXPRESSION \t\r\n) , bytes { \t\r\n} , expression \t\r\n\"\","
            + "big integer 400000000000000000000000000000000000000, big decimal 500000000000000000000000000000000000000.000000000000000000000000000009 \r\n, true \r\n, undefined \r\n]";
        final ModelReader reader = getModelReader( data );
        assertListStartState( reader );
        assertStringState( reader, "0" );
        assertNumberState( reader, 1 );
        assertNumberState( reader, 2L );
        assertNumberState( reader, 3.0 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "" );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertPropertyEndState( reader );
        assertBytesState( reader, new byte[] {} );
        assertExpressionState( reader, "" );
        assertNumberState( reader, new BigInteger( "400000000000000000000000000000000000000" ) );
        assertNumberState( reader, new BigDecimal( "500000000000000000000000000000000000000.000000000000000000000000000009" ) );
        assertBooleanState( reader, true );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void moreComplexListWithoutWhitespaces() throws IOException, ModelException {
        final String data = "[\"0\",1,2L,3.0,{},[],(\"\"=>EXPRESSION),bytes{},expression\"\","
                + "biginteger40000000000000000000000000000,bigdecimal5000000000000000000000000000000000.0000000000000000000000009,true,undefined]";
        final ModelReader reader = getModelReader( data );
        assertListStartState( reader );
        assertStringState( reader, "0" );
        assertNumberState( reader, 1 );
        assertNumberState( reader, 2L );
        assertNumberState( reader, 3.0 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "" );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertPropertyEndState( reader );
        assertBytesState( reader, new byte[] {} );
        assertExpressionState( reader, "" );
        assertNumberState( reader, new BigInteger( "40000000000000000000000000000" ) );
        assertNumberState( reader, new BigDecimal( "5000000000000000000000000000000000.0000000000000000000000009" ) );
        assertBooleanState( reader, true );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void moreComplexListWithWhitespacesWithComma() throws IOException, ModelException {
        final String data = "[ \"0\" \r\n, 1 \r\n, 2L \r\n, 3.0 \r\n, \r\n{ \"BOOLEAN\" => undefined, } \r\n, \r\n[ LIST , \r\n] \r\n, \r\n ( \"\" \r\n=> \t\r\nEXPRESSION \t\r\n) , bytes { 0x00,\t\r\n} , expression \t\r\n\"\","
                + "big integer 400000000000000000000000000000000000000, big decimal 500000000000000000000000000000000000000.000000000000000000000000000009 \r\n, true \r\n, undefined \r\n,]";
        final ModelReader reader = getModelReader( data );
        assertListStartState( reader );
        assertStringState( reader, "0" );
        assertNumberState( reader, 1 );
        assertNumberState( reader, 2L );
        assertNumberState( reader, 3.0 );
        assertObjectStartState( reader );
        assertStringState( reader, "BOOLEAN" );
        assertUndefinedState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.LIST );
        assertListEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "" );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertPropertyEndState( reader );
        assertBytesState( reader, new byte[] { 0 } );
        assertExpressionState( reader, "" );
        assertNumberState( reader, new BigInteger( "400000000000000000000000000000000000000" ) );
        assertNumberState( reader, new BigDecimal( "500000000000000000000000000000000000000.000000000000000000000000000009" ) );
        assertBooleanState( reader, true );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void moreComplexListWithoutWhitespacesWithComma() throws IOException, ModelException {
        final String data = "[\"0\",1,2L,3.0,{\"BOOLEAN\"=>undefined,},[LIST,],(\"\"=>EXPRESSION),bytes{0x00,},expression\"\","
                + "biginteger40000000000000000000000000000,bigdecimal5000000000000000000000000000000000.0000000000000000000000009,true,undefined,]";
        final ModelReader reader = getModelReader( data );
        assertListStartState( reader );
        assertStringState( reader, "0" );
        assertNumberState( reader, 1 );
        assertNumberState( reader, 2L );
        assertNumberState( reader, 3.0 );
        assertObjectStartState( reader );
        assertStringState( reader, "BOOLEAN" );
        assertUndefinedState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.LIST );
        assertListEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "" );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertPropertyEndState( reader );
        assertBytesState( reader, new byte[] { 0 } );
        assertExpressionState( reader, "" );
        assertNumberState( reader, new BigInteger( "40000000000000000000000000000" ) );
        assertNumberState( reader, new BigDecimal( "5000000000000000000000000000000000.0000000000000000000000009" ) );
        assertBooleanState( reader, true );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void moreComplexPropertyWithWhitespaces() throws IOException, ModelException {
        final String data = "(\"moreComplexPropertyKey\"=>[ \"0\" \r\n, 1 \r\n, 2L \r\n, 3.0 \r\n, \r\n{ \r\n} \r\n, \r\n[ \r\n] \r\n, \r\n ( \"\" \r\n=> \t\r\nEXPRESSION \t\r\n) , bytes { \t\r\n} , expression \t\r\n\"\","
                + "big integer 400000000000000000000000000000000000000, big decimal 500000000000000000000000000000000000000.000000000000000000000000000009 \r\n, true \r\n, undefined \r\n])";
        final ModelReader reader = getModelReader( data );
        assertPropertyStartState( reader );
        assertStringState( reader, "moreComplexPropertyKey" );
        assertListStartState( reader );
        assertStringState( reader, "0" );
        assertNumberState( reader, 1 );
        assertNumberState( reader, 2L );
        assertNumberState( reader, 3.0 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "" );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertPropertyEndState( reader );
        assertBytesState( reader, new byte[] {} );
        assertExpressionState( reader, "" );
        assertNumberState( reader, new BigInteger( "400000000000000000000000000000000000000" ) );
        assertNumberState( reader, new BigDecimal( "500000000000000000000000000000000000000.000000000000000000000000000009" ) );
        assertBooleanState( reader, true );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void moreComplexPropertyWithoutWhitespaces() throws IOException, ModelException {
        final String data = "(\"moreComplexPropertyKey\"=>[\"0\",1,2L,3.0,{},[],(\"\"=>EXPRESSION),bytes{},expression\"\","
                + "biginteger40000000000000000000000000000,bigdecimal5000000000000000000000000000000000.0000000000000000000000009,true,undefined])";
        final ModelReader reader = getModelReader( data );
        assertPropertyStartState( reader );
        assertStringState( reader, "moreComplexPropertyKey" );
        assertListStartState( reader );
        assertStringState( reader, "0" );
        assertNumberState( reader, 1 );
        assertNumberState( reader, 2L );
        assertNumberState( reader, 3.0 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "" );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertPropertyEndState( reader );
        assertBytesState( reader, new byte[] {} );
        assertExpressionState( reader, "" );
        assertNumberState( reader, new BigInteger( "40000000000000000000000000000" ) );
        assertNumberState( reader, new BigDecimal( "5000000000000000000000000000000000.0000000000000000000000009" ) );
        assertBooleanState( reader, true );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void moreComplexPropertyWithWhitespacesWithComma() throws IOException, ModelException {
        final String data = "(\"moreComplexPropertyKey\"=>[ \"0\" \r\n, 1 \r\n, 2L \r\n, 3.0 \r\n, \r\n{ \"BOOLEAN\" => undefined, } \r\n, \r\n[ LIST , \r\n] \r\n, \r\n ( \"\" \r\n=> \t\r\nEXPRESSION \t\r\n) , bytes { 0x00,\t\r\n} , expression \t\r\n\"\","
                + "big integer 400000000000000000000000000000000000000, big decimal 500000000000000000000000000000000000000.000000000000000000000000000009 \r\n, true \r\n, undefined \r\n,])";
        final ModelReader reader = getModelReader( data );
        assertPropertyStartState( reader );
        assertStringState( reader, "moreComplexPropertyKey" );
        assertListStartState( reader );
        assertStringState( reader, "0" );
        assertNumberState( reader, 1 );
        assertNumberState( reader, 2L );
        assertNumberState( reader, 3.0 );
        assertObjectStartState( reader );
        assertStringState( reader, "BOOLEAN" );
        assertUndefinedState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.LIST );
        assertListEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "" );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertPropertyEndState( reader );
        assertBytesState( reader, new byte[] { 0 } );
        assertExpressionState( reader, "" );
        assertNumberState( reader, new BigInteger( "400000000000000000000000000000000000000" ) );
        assertNumberState( reader, new BigDecimal( "500000000000000000000000000000000000000.000000000000000000000000000009" ) );
        assertBooleanState( reader, true );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void moreComplexPropertyWithoutWhitespacesWithComma() throws IOException, ModelException {
        final String data = "(\"moreComplexPropertyKey\"=>[\"0\",1,2L,3.0,{\"BOOLEAN\"=>undefined,},[LIST,],(\"\"=>EXPRESSION),bytes{0x00,},expression\"\","
                + "biginteger40000000000000000000000000000,bigdecimal5000000000000000000000000000000000.0000000000000000000000009,true,undefined,])";
        final ModelReader reader = getModelReader( data );
        assertPropertyStartState( reader );
        assertStringState( reader, "moreComplexPropertyKey" );
        assertListStartState( reader );
        assertStringState( reader, "0" );
        assertNumberState( reader, 1 );
        assertNumberState( reader, 2L );
        assertNumberState( reader, 3.0 );
        assertObjectStartState( reader );
        assertStringState( reader, "BOOLEAN" );
        assertUndefinedState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.LIST );
        assertListEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "" );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertPropertyEndState( reader );
        assertBytesState( reader, new byte[] { 0 } );
        assertExpressionState( reader, "" );
        assertNumberState( reader, new BigInteger( "40000000000000000000000000000" ) );
        assertNumberState( reader, new BigDecimal( "5000000000000000000000000000000000.0000000000000000000000009" ) );
        assertBooleanState( reader, true );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostComplexListWithoutWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "[{\"list\"=>[{\"expr\"=>expression\"\",\"BTS\"=>bytes{0x00,0x01,0x02,0x03}}]},\"0\",{\"String\"=>\"s\",\"boolean\"=>false},(\"booleans\"=>[true,false,undefined]),\"1\",[undefined,true,7,{},[["
                + "OBJECT,LIST,PROPERTY,STRING,INT,LONG,DOUBLE,BIG_INTEGER,BIG_DECIMAL,BYTES,EXPRESSION,TYPE,BOOLEAN,UNDEFINED]]]]" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "list" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "expr" );
        assertExpressionState( reader, "" );
        assertStringState( reader, "BTS" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertStringState( reader, "0" );
        assertObjectStartState( reader );
        assertStringState( reader, "String" );
        assertStringState( reader, "s" );
        assertStringState( reader, "boolean" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertStringState( reader, "1" );
        assertListStartState( reader );
        assertUndefinedState( reader );
        assertBooleanState( reader, true );
        assertNumberState( reader, 7 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertTypeState( reader, ModelType.LIST );
        assertTypeState( reader, ModelType.PROPERTY );
        assertTypeState( reader, ModelType.STRING );
        assertTypeState( reader, ModelType.INT );
        assertTypeState( reader, ModelType.LONG );
        assertTypeState( reader, ModelType.DOUBLE );
        assertTypeState( reader, ModelType.BIG_INTEGER );
        assertTypeState( reader, ModelType.BIG_DECIMAL );
        assertTypeState( reader, ModelType.BYTES );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertTypeState( reader, ModelType.TYPE );
        assertTypeState( reader, ModelType.BOOLEAN );
        assertTypeState( reader, ModelType.UNDEFINED );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostComplexListWithWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "[ { \r\"list\"\t=> [\r{\t\"expr\" => expression \t\r\n\"\" , \"BTS\" => bytes{ 0x00 , 0x01 , 0x02 , 0x03 } } ] } , \"0\" \t\r\n, \t\r\n{ \"String\" => \"s\" , \"boolean\" \r=> false } , (\"booleans\" => [ true , false , undefined]) , \"1\", [ undefined , true,7,{},[["
                + "OBJECT \t\r\n, \t\nLIST \t\n, \t\nPROPERTY \t\n, \t\nSTRING \t\n, \t\nINT \t\n, \t\nLONG \t\n, \t\nDOUBLE \t\n, \t\nBIG_INTEGER \t\n, \t\nBIG_DECIMAL \t\n, \t\nBYTES \t\n, \t\nEXPRESSION \t\n, \t\nTYPE \t\n, \t\nBOOLEAN \t\n, \t\nUNDEFINED ] ] ] ]" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "list" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "expr" );
        assertExpressionState( reader, "" );
        assertStringState( reader, "BTS" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertStringState( reader, "0" );
        assertObjectStartState( reader );
        assertStringState( reader, "String" );
        assertStringState( reader, "s" );
        assertStringState( reader, "boolean" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertStringState( reader, "1" );
        assertListStartState( reader );
        assertUndefinedState( reader );
        assertBooleanState( reader, true );
        assertNumberState( reader, 7 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertTypeState( reader, ModelType.LIST );
        assertTypeState( reader, ModelType.PROPERTY );
        assertTypeState( reader, ModelType.STRING );
        assertTypeState( reader, ModelType.INT );
        assertTypeState( reader, ModelType.LONG );
        assertTypeState( reader, ModelType.DOUBLE );
        assertTypeState( reader, ModelType.BIG_INTEGER );
        assertTypeState( reader, ModelType.BIG_DECIMAL );
        assertTypeState( reader, ModelType.BYTES );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertTypeState( reader, ModelType.TYPE );
        assertTypeState( reader, ModelType.BOOLEAN );
        assertTypeState( reader, ModelType.UNDEFINED );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostComplexListWithoutWhitespacesWithComma() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "[{\"list\"=>[{\"expr\"=>expression\"\",\"BTS\"=>bytes{0x00,0x01,0x02,0x03,},},]},\"0\",{\"String\"=>\"s\",\"boolean\"=>false},(\"booleans\"=>[true,false,undefined,]),\"1\",[undefined,true,7,{},[["
                + "OBJECT,LIST,PROPERTY,STRING,INT,LONG,DOUBLE,BIG_INTEGER,BIG_DECIMAL,BYTES,EXPRESSION,TYPE,BOOLEAN,UNDEFINED,]]],]" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "list" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "expr" );
        assertExpressionState( reader, "" );
        assertStringState( reader, "BTS" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertStringState( reader, "0" );
        assertObjectStartState( reader );
        assertStringState( reader, "String" );
        assertStringState( reader, "s" );
        assertStringState( reader, "boolean" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertStringState( reader, "1" );
        assertListStartState( reader );
        assertUndefinedState( reader );
        assertBooleanState( reader, true );
        assertNumberState( reader, 7 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertTypeState( reader, ModelType.LIST );
        assertTypeState( reader, ModelType.PROPERTY );
        assertTypeState( reader, ModelType.STRING );
        assertTypeState( reader, ModelType.INT );
        assertTypeState( reader, ModelType.LONG );
        assertTypeState( reader, ModelType.DOUBLE );
        assertTypeState( reader, ModelType.BIG_INTEGER );
        assertTypeState( reader, ModelType.BIG_DECIMAL );
        assertTypeState( reader, ModelType.BYTES );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertTypeState( reader, ModelType.TYPE );
        assertTypeState( reader, ModelType.BOOLEAN );
        assertTypeState( reader, ModelType.UNDEFINED );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostComplexListWithWhitespacesWithComma() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "[ { \r\"list\"\t=> [\r{\t\"expr\" => expression \t\r\n\"\" , \"BTS\" => bytes{ 0x00 , 0x01 , 0x02 , 0x03 , } } , ] , } , \"0\" \t\r\n, \t\r\n{ \"String\" => \"s\" , \"boolean\" \r=> false , } , (\"booleans\" => [ true , false , undefined, ]) , \"1\", [ undefined , true,7,{},[["
                + "OBJECT \t\r\n, \t\nLIST \t\n, \t\nPROPERTY \t\n, \t\nSTRING \t\n, \t\nINT \t\n, \t\nLONG \t\n, \t\nDOUBLE \t\n, \t\nBIG_INTEGER \t\n, \t\nBIG_DECIMAL \t\n, \t\nBYTES \t\n, \t\nEXPRESSION \t\n, \t\nTYPE \t\n, \t\nBOOLEAN \t\n, \t\nUNDEFINED , ] ] ], ]" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "list" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "expr" );
        assertExpressionState( reader, "" );
        assertStringState( reader, "BTS" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertStringState( reader, "0" );
        assertObjectStartState( reader );
        assertStringState( reader, "String" );
        assertStringState( reader, "s" );
        assertStringState( reader, "boolean" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertStringState( reader, "1" );
        assertListStartState( reader );
        assertUndefinedState( reader );
        assertBooleanState( reader, true );
        assertNumberState( reader, 7 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertTypeState( reader, ModelType.LIST );
        assertTypeState( reader, ModelType.PROPERTY );
        assertTypeState( reader, ModelType.STRING );
        assertTypeState( reader, ModelType.INT );
        assertTypeState( reader, ModelType.LONG );
        assertTypeState( reader, ModelType.DOUBLE );
        assertTypeState( reader, ModelType.BIG_INTEGER );
        assertTypeState( reader, ModelType.BIG_DECIMAL );
        assertTypeState( reader, ModelType.BYTES );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertTypeState( reader, ModelType.TYPE );
        assertTypeState( reader, ModelType.BOOLEAN );
        assertTypeState( reader, ModelType.UNDEFINED );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostComplexObjectWithoutWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{\"the Most Complex Object\"=>[{\"list\"=>[{\"expr\"=>expression\"\",\"BTS\"=>bytes{0x00,0x01,0x02,0x03}}]},\"0\",{\"String\"=>\"s\",\"boolean\"=>false},(\"booleans\"=>[true,false,undefined]),\"1\",[undefined,true,7,{},[["
                + "OBJECT,LIST,PROPERTY,STRING,INT,LONG,DOUBLE,BIG_INTEGER,BIG_DECIMAL,BYTES,EXPRESSION,TYPE,BOOLEAN,UNDEFINED]]]]}" );
        assertObjectStartState( reader );
        assertStringState( reader, "the Most Complex Object" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "list" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "expr" );
        assertExpressionState( reader, "" );
        assertStringState( reader, "BTS" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertStringState( reader, "0" );
        assertObjectStartState( reader );
        assertStringState( reader, "String" );
        assertStringState( reader, "s" );
        assertStringState( reader, "boolean" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertStringState( reader, "1" );
        assertListStartState( reader );
        assertUndefinedState( reader );
        assertBooleanState( reader, true );
        assertNumberState( reader, 7 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertTypeState( reader, ModelType.LIST );
        assertTypeState( reader, ModelType.PROPERTY );
        assertTypeState( reader, ModelType.STRING );
        assertTypeState( reader, ModelType.INT );
        assertTypeState( reader, ModelType.LONG );
        assertTypeState( reader, ModelType.DOUBLE );
        assertTypeState( reader, ModelType.BIG_INTEGER );
        assertTypeState( reader, ModelType.BIG_DECIMAL );
        assertTypeState( reader, ModelType.BYTES );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertTypeState( reader, ModelType.TYPE );
        assertTypeState( reader, ModelType.BOOLEAN );
        assertTypeState( reader, ModelType.UNDEFINED );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostComplexObjectWithWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{ \"the Most Complex Object\" => [ { \r\"list\"\t=> [\r{\t\"expr\" => expression \t\r\n\"\" , \"BTS\" => bytes{ 0x00 , 0x01 , 0x02 , 0x03 } } ] } , \"0\" \t\r\n, \t\r\n{ \"String\" => \"s\" , \"boolean\" \r=> false } , (\"booleans\" => [ true , false , undefined]) , \"1\", [ undefined , true,7,{},[["
                + "OBJECT \t\r\n, \t\nLIST \t\n, \t\nPROPERTY \t\n, \t\nSTRING \t\n, \t\nINT \t\n, \t\nLONG \t\n, \t\nDOUBLE \t\n, \t\nBIG_INTEGER \t\n, \t\nBIG_DECIMAL \t\n, \t\nBYTES \t\n, \t\nEXPRESSION \t\n, \t\nTYPE \t\n, \t\nBOOLEAN \t\n, \t\nUNDEFINED ] ] ] ] }" );
        assertObjectStartState( reader );
        assertStringState( reader, "the Most Complex Object" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "list" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "expr" );
        assertExpressionState( reader, "" );
        assertStringState( reader, "BTS" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertStringState( reader, "0" );
        assertObjectStartState( reader );
        assertStringState( reader, "String" );
        assertStringState( reader, "s" );
        assertStringState( reader, "boolean" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertStringState( reader, "1" );
        assertListStartState( reader );
        assertUndefinedState( reader );
        assertBooleanState( reader, true );
        assertNumberState( reader, 7 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertTypeState( reader, ModelType.LIST );
        assertTypeState( reader, ModelType.PROPERTY );
        assertTypeState( reader, ModelType.STRING );
        assertTypeState( reader, ModelType.INT );
        assertTypeState( reader, ModelType.LONG );
        assertTypeState( reader, ModelType.DOUBLE );
        assertTypeState( reader, ModelType.BIG_INTEGER );
        assertTypeState( reader, ModelType.BIG_DECIMAL );
        assertTypeState( reader, ModelType.BYTES );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertTypeState( reader, ModelType.TYPE );
        assertTypeState( reader, ModelType.BOOLEAN );
        assertTypeState( reader, ModelType.UNDEFINED );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostComplexObjectWithoutWhitespacesWithComma() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{\"the Most Complex Object\"=>[{\"list\"=>[{\"expr\"=>expression\"\",\"BTS\"=>bytes{0x00,0x01,0x02,0x03,},},]},\"0\",{\"String\"=>\"s\",\"boolean\"=>false},(\"booleans\"=>[true,false,undefined,]),\"1\",[undefined,true,7,{},[["
                + "OBJECT,LIST,PROPERTY,STRING,INT,LONG,DOUBLE,BIG_INTEGER,BIG_DECIMAL,BYTES,EXPRESSION,TYPE,BOOLEAN,UNDEFINED,]]],]}" );
        assertObjectStartState( reader );
        assertStringState( reader, "the Most Complex Object" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "list" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "expr" );
        assertExpressionState( reader, "" );
        assertStringState( reader, "BTS" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertStringState( reader, "0" );
        assertObjectStartState( reader );
        assertStringState( reader, "String" );
        assertStringState( reader, "s" );
        assertStringState( reader, "boolean" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertStringState( reader, "1" );
        assertListStartState( reader );
        assertUndefinedState( reader );
        assertBooleanState( reader, true );
        assertNumberState( reader, 7 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertTypeState( reader, ModelType.LIST );
        assertTypeState( reader, ModelType.PROPERTY );
        assertTypeState( reader, ModelType.STRING );
        assertTypeState( reader, ModelType.INT );
        assertTypeState( reader, ModelType.LONG );
        assertTypeState( reader, ModelType.DOUBLE );
        assertTypeState( reader, ModelType.BIG_INTEGER );
        assertTypeState( reader, ModelType.BIG_DECIMAL );
        assertTypeState( reader, ModelType.BYTES );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertTypeState( reader, ModelType.TYPE );
        assertTypeState( reader, ModelType.BOOLEAN );
        assertTypeState( reader, ModelType.UNDEFINED );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostObjectListWithWhitespacesWithComma() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{ \"the Most Complex Object\" => [ { \r\"list\"\t=> [\r{\t\"expr\" => expression \t\r\n\"\" , \"BTS\" => bytes{ 0x00 , 0x01 , 0x02 , 0x03 , } } , ] , } , \"0\" \t\r\n, \t\r\n{ \"String\" => \"s\" , \"boolean\" \r=> false , } , (\"booleans\" => [ true , false , undefined, ]) , \"1\", [ undefined , true,7,{},[["
                + "OBJECT \t\r\n, \t\nLIST \t\n, \t\nPROPERTY \t\n, \t\nSTRING \t\n, \t\nINT \t\n, \t\nLONG \t\n, \t\nDOUBLE \t\n, \t\nBIG_INTEGER \t\n, \t\nBIG_DECIMAL \t\n, \t\nBYTES \t\n, \t\nEXPRESSION \t\n, \t\nTYPE \t\n, \t\nBOOLEAN \t\n, \t\nUNDEFINED , ] ] ], ], }" );
        assertObjectStartState( reader );
        assertStringState( reader, "the Most Complex Object" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "list" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "expr" );
        assertExpressionState( reader, "" );
        assertStringState( reader, "BTS" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertStringState( reader, "0" );
        assertObjectStartState( reader );
        assertStringState( reader, "String" );
        assertStringState( reader, "s" );
        assertStringState( reader, "boolean" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertStringState( reader, "1" );
        assertListStartState( reader );
        assertUndefinedState( reader );
        assertBooleanState( reader, true );
        assertNumberState( reader, 7 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertTypeState( reader, ModelType.LIST );
        assertTypeState( reader, ModelType.PROPERTY );
        assertTypeState( reader, ModelType.STRING );
        assertTypeState( reader, ModelType.INT );
        assertTypeState( reader, ModelType.LONG );
        assertTypeState( reader, ModelType.DOUBLE );
        assertTypeState( reader, ModelType.BIG_INTEGER );
        assertTypeState( reader, ModelType.BIG_DECIMAL );
        assertTypeState( reader, ModelType.BYTES );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertTypeState( reader, ModelType.TYPE );
        assertTypeState( reader, ModelType.BOOLEAN );
        assertTypeState( reader, ModelType.UNDEFINED );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostComplexPropertyWithoutWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "(\"the Most Complex Property\"=>{\"bar\"=>[{\"list\"=>[{\"expr\"=>expression\"\",\"BTS\"=>bytes{0x00,0x01,0x02,0x03}}]},\"0\",{\"String\"=>\"s\",\"boolean\"=>false},(\"booleans\"=>[true,false,undefined]),\"1\",[undefined,true,7,{},[["
                + "OBJECT,LIST,PROPERTY,STRING,INT,LONG,DOUBLE,BIG_INTEGER,BIG_DECIMAL,BYTES,EXPRESSION,TYPE,BOOLEAN,UNDEFINED]]]]})" );
        assertPropertyStartState( reader );
        assertStringState( reader, "the Most Complex Property" );
        assertObjectStartState( reader );
        assertStringState( reader, "bar" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "list" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "expr" );
        assertExpressionState( reader, "" );
        assertStringState( reader, "BTS" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertStringState( reader, "0" );
        assertObjectStartState( reader );
        assertStringState( reader, "String" );
        assertStringState( reader, "s" );
        assertStringState( reader, "boolean" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertStringState( reader, "1" );
        assertListStartState( reader );
        assertUndefinedState( reader );
        assertBooleanState( reader, true );
        assertNumberState( reader, 7 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertTypeState( reader, ModelType.LIST );
        assertTypeState( reader, ModelType.PROPERTY );
        assertTypeState( reader, ModelType.STRING );
        assertTypeState( reader, ModelType.INT );
        assertTypeState( reader, ModelType.LONG );
        assertTypeState( reader, ModelType.DOUBLE );
        assertTypeState( reader, ModelType.BIG_INTEGER );
        assertTypeState( reader, ModelType.BIG_DECIMAL );
        assertTypeState( reader, ModelType.BYTES );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertTypeState( reader, ModelType.TYPE );
        assertTypeState( reader, ModelType.BOOLEAN );
        assertTypeState( reader, ModelType.UNDEFINED );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertPropertyEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostComplexPropertyWithWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "( \"the Most Complex Property\" => { \"bar\" => [ { \r\"list\"\t=> [\r{\t\"expr\" => expression \t\r\n\"\" , \"BTS\" => bytes{ 0x00 , 0x01 , 0x02 , 0x03 } } ] } , \"0\" \t\r\n, \t\r\n{ \"String\" => \"s\" , \"boolean\" \r=> false } , (\"booleans\" => [ true , false , undefined]) , \"1\", [ undefined , true,7,{},[["
                + "OBJECT \t\r\n, \t\nLIST \t\n, \t\nPROPERTY \t\n, \t\nSTRING \t\n, \t\nINT \t\n, \t\nLONG \t\n, \t\nDOUBLE \t\n, \t\nBIG_INTEGER \t\n, \t\nBIG_DECIMAL \t\n, \t\nBYTES \t\n, \t\nEXPRESSION \t\n, \t\nTYPE \t\n, \t\nBOOLEAN \t\n, \t\nUNDEFINED ] ] ] ] } )" );
        assertPropertyStartState( reader );
        assertStringState( reader, "the Most Complex Property" );
        assertObjectStartState( reader );
        assertStringState( reader, "bar" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "list" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "expr" );
        assertExpressionState( reader, "" );
        assertStringState( reader, "BTS" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertStringState( reader, "0" );
        assertObjectStartState( reader );
        assertStringState( reader, "String" );
        assertStringState( reader, "s" );
        assertStringState( reader, "boolean" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertStringState( reader, "1" );
        assertListStartState( reader );
        assertUndefinedState( reader );
        assertBooleanState( reader, true );
        assertNumberState( reader, 7 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertTypeState( reader, ModelType.LIST );
        assertTypeState( reader, ModelType.PROPERTY );
        assertTypeState( reader, ModelType.STRING );
        assertTypeState( reader, ModelType.INT );
        assertTypeState( reader, ModelType.LONG );
        assertTypeState( reader, ModelType.DOUBLE );
        assertTypeState( reader, ModelType.BIG_INTEGER );
        assertTypeState( reader, ModelType.BIG_DECIMAL );
        assertTypeState( reader, ModelType.BYTES );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertTypeState( reader, ModelType.TYPE );
        assertTypeState( reader, ModelType.BOOLEAN );
        assertTypeState( reader, ModelType.UNDEFINED );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertPropertyEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostComplexPropertyWithoutWhitespacesWithComma() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "(\"the Most Complex Property\"=>{\"bar\"=>[{\"list\"=>[{\"expr\"=>expression\"\",\"BTS\"=>bytes{0x00,0x01,0x02,0x03,},},]},\"0\",{\"String\"=>\"s\",\"boolean\"=>false},(\"booleans\"=>[true,false,undefined,]),\"1\",[undefined,true,7,{},[["
                + "OBJECT,LIST,PROPERTY,STRING,INT,LONG,DOUBLE,BIG_INTEGER,BIG_DECIMAL,BYTES,EXPRESSION,TYPE,BOOLEAN,UNDEFINED,]]],]})" );
        assertPropertyStartState( reader );
        assertStringState( reader, "the Most Complex Property" );
        assertObjectStartState( reader );
        assertStringState( reader, "bar" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "list" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "expr" );
        assertExpressionState( reader, "" );
        assertStringState( reader, "BTS" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertStringState( reader, "0" );
        assertObjectStartState( reader );
        assertStringState( reader, "String" );
        assertStringState( reader, "s" );
        assertStringState( reader, "boolean" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertStringState( reader, "1" );
        assertListStartState( reader );
        assertUndefinedState( reader );
        assertBooleanState( reader, true );
        assertNumberState( reader, 7 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertTypeState( reader, ModelType.LIST );
        assertTypeState( reader, ModelType.PROPERTY );
        assertTypeState( reader, ModelType.STRING );
        assertTypeState( reader, ModelType.INT );
        assertTypeState( reader, ModelType.LONG );
        assertTypeState( reader, ModelType.DOUBLE );
        assertTypeState( reader, ModelType.BIG_INTEGER );
        assertTypeState( reader, ModelType.BIG_DECIMAL );
        assertTypeState( reader, ModelType.BYTES );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertTypeState( reader, ModelType.TYPE );
        assertTypeState( reader, ModelType.BOOLEAN );
        assertTypeState( reader, ModelType.UNDEFINED );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertPropertyEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostComplexPropertyListWithWhitespacesWithComma() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "( \"the Most Complex Property\" => { \"bar\" => [ { \r\"list\"\t=> [\r{\t\"expr\" => expression \t\r\n\"\" , \"BTS\" => bytes{ 0x00 , 0x01 , 0x02 , 0x03 , } } , ] , } , \"0\" \t\r\n, \t\r\n{ \"String\" => \"s\" , \"boolean\" \r=> false , } , (\"booleans\" => [ true , false , undefined, ]) , \"1\", [ undefined , true,7,{},[["
                + "OBJECT \t\r\n, \t\nLIST \t\n, \t\nPROPERTY \t\n, \t\nSTRING \t\n, \t\nINT \t\n, \t\nLONG \t\n, \t\nDOUBLE \t\n, \t\nBIG_INTEGER \t\n, \t\nBIG_DECIMAL \t\n, \t\nBYTES \t\n, \t\nEXPRESSION \t\n, \t\nTYPE \t\n, \t\nBOOLEAN \t\n, \t\nUNDEFINED , ] ] ], ], })" );
        assertPropertyStartState( reader );
        assertStringState( reader, "the Most Complex Property" );
        assertObjectStartState( reader );
        assertStringState( reader, "bar" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "list" );
        assertListStartState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "expr" );
        assertExpressionState( reader, "" );
        assertStringState( reader, "BTS" );
        assertBytesState( reader, new byte[] { 0, 1, 2, 3 } );
        assertObjectEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertStringState( reader, "0" );
        assertObjectStartState( reader );
        assertStringState( reader, "String" );
        assertStringState( reader, "s" );
        assertStringState( reader, "boolean" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertPropertyStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertPropertyEndState( reader );
        assertStringState( reader, "1" );
        assertListStartState( reader );
        assertUndefinedState( reader );
        assertBooleanState( reader, true );
        assertNumberState( reader, 7 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListStartState( reader );
        assertTypeState( reader, ModelType.OBJECT );
        assertTypeState( reader, ModelType.LIST );
        assertTypeState( reader, ModelType.PROPERTY );
        assertTypeState( reader, ModelType.STRING );
        assertTypeState( reader, ModelType.INT );
        assertTypeState( reader, ModelType.LONG );
        assertTypeState( reader, ModelType.DOUBLE );
        assertTypeState( reader, ModelType.BIG_INTEGER );
        assertTypeState( reader, ModelType.BIG_DECIMAL );
        assertTypeState( reader, ModelType.BYTES );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertTypeState( reader, ModelType.TYPE );
        assertTypeState( reader, ModelType.BOOLEAN );
        assertTypeState( reader, ModelType.UNDEFINED );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertPropertyEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void simplePropertyWithWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "(\r\n\t\"a\" => \"b\"\r\n)" );
        assertPropertyStartState( reader );
        assertStringState( reader, "a" );
        assertStringState( reader, "b" );
        assertPropertyEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void simplePropertyWithoutWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "(\"a\"=>\"b\")" );
        assertPropertyStartState( reader );
        assertStringState( reader, "a" );
        assertStringState( reader, "b" );
        assertPropertyEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

}
