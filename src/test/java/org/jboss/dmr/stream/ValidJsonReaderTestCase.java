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

import org.jboss.dmr.ModelType;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class ValidJsonReaderTestCase extends AbstractModelStreamsTestCase {

    @Override
    public ModelReader getModelReader( final String data ) throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream( data.getBytes() );
        return ModelStreamFactory.getInstance( true ).newModelReader( bais );
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
    public void simpleObjectWithWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{ \t\r\n\"a\" \t\r\n: \t\r\n\"b\" \t\r\n}" );
        assertObjectStartState( reader );
        assertStringState( reader, "a" );
        assertStringState( reader, "b" );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void simpleObjectWithoutWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{\"a\":\"b\","
                + "\"hexedPositiveIntNoSign\":0xabcdef,\"hexedPositiveIntWithSign\":+0xabcdef,\"hexedNegativeIntWithSign\":-0xFEDCBA,"
                + "\"hexedPositiveLongNoSign\":0xabcdefabcdef,\"hexedPositiveLongWithSign\":+0xabcdefabcdef,\"hexedNegativeLongWithSign\":-0xFEDCBAFEDCBA,"
                + "\"octedPositiveIntNoSign\":012345670,\"octedPositiveIntWithSign\":+012345670,\"octedNegativeIntWithSign\":-076543210,"
                + "\"octedPositiveLongNoSign\":01234567012345670,\"octedPositiveLongWithSign\":+01234567012345670,\"octedNegativeLongWithSign\":-07654321076543210}" );
        assertObjectStartState( reader );
        assertStringState( reader, "a" );
        assertStringState( reader, "b" );
        assertStringState( reader, "hexedPositiveIntNoSign" );
        assertNumberState( reader, 11259375 );
        assertStringState( reader, "hexedPositiveIntWithSign" );
        assertNumberState( reader, 11259375 );
        assertStringState( reader, "hexedNegativeIntWithSign" );
        assertNumberState( reader, -16702650 );
        assertStringState( reader, "hexedPositiveLongNoSign" );
        assertNumberState( reader, 188900977659375L );
        assertStringState( reader, "hexedPositiveLongWithSign" );
        assertNumberState( reader, 188900977659375L );
        assertStringState( reader, "hexedNegativeLongWithSign" );
        assertNumberState( reader, -280223983525050L );
        assertStringState( reader, "octedPositiveIntNoSign" );
        assertNumberState( reader, 2739128 );
        assertStringState( reader, "octedPositiveIntWithSign" );
        assertNumberState( reader, 2739128 );
        assertStringState( reader, "octedNegativeIntWithSign" );
        assertNumberState( reader, -16434824 );
        assertStringState( reader, "octedPositiveLongNoSign" );
        assertNumberState( reader, 45954944846776L );
        assertStringState( reader, "octedPositiveLongWithSign" );
        assertNumberState( reader, 45954944846776L );
        assertStringState( reader, "octedNegativeLongWithSign" );
        assertNumberState( reader, -275730608604808L );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void moreComplexObjectWithoutWhitespaces() throws IOException, ModelException {
        final String data = "{\"0\":\"0\",\"1\":1,\"2\":2,\"3\":3.0,\"4\":true,\"5\":null,\"6\":"
            + "60000000000000000000000000000,\"7\":70000000000000000000000000000.000000000000001,"
            + "\"8\":{\"EXPRESSION_VALUE\":\"env.JAVA_HOME\"},\"9\":{\"BYTES_VALUE\":\"AAECAw==\"},\"10\":{\"\":false},\"11\":"
            + "[{\"TYPE_MODEL_VALUE\":\"OBJECT\"}],\"12\":{\"propKey\":{\"TYPE_MODEL_VALUE\":\"PROPERTY\"}}}";
        final ModelReader reader = getModelReader( data );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertStringState( reader, "0" );
        assertStringState( reader, "1" );
        assertNumberState( reader, 1 );
        assertStringState( reader, "2" );
        assertNumberState( reader, 2 );
        assertStringState( reader, "3" );
        assertNumberState( reader, new BigDecimal( "3.0" ) );
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
        assertObjectStartState( reader );
        assertStringState( reader, "propKey" );
        assertTypeState( reader, ModelType.PROPERTY );
        assertObjectEndState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void moreComplexObjectWithWhitespaces() throws IOException, ModelException {
        final String data = "{ \"0\" : \"0\" , \t\"1\" : \t1 \t, \r\"2\" \r: \r2 \r, \n\"3\" \n: \n 3.0 \n, \"4\" : true , \"5\" : null ,\"6\":"
                + "60000000000000000000000000000, \"7\" : \t\r\n70000000000000000000000000000.000000000000001 \t\r\n,"
                + "\"8\" : { \t\r\n\"EXPRESSION_VALUE\" \t\r\n: \t\r\n\"env.JAVA_HOME\" \t\r\n} \t\r\n, \"9\" : \t\r\n{ \t\r\n\"BYTES_VALUE\" \t\r\n: \t\r\n\"AAECAw==\" \t\r\n} \t\r\n, \t\r\n"
                + "\"10\" : { \t\r\n\"\":false \t\r\n},\"11\" \t\r\n: \t\r\n[ \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"OBJECT\" \t\r\n} ] , \"12\" : { \t\r\n\"propKey\" : { \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"PROPERTY\" \t\r\n} \t\r\n} \t\r\n}";
        final ModelReader reader = getModelReader( data );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertStringState( reader, "0" );
        assertStringState( reader, "1" );
        assertNumberState( reader, 1 );
        assertStringState( reader, "2" );
        assertNumberState( reader, 2 );
        assertStringState( reader, "3" );
        assertNumberState( reader, new BigDecimal( "3.0" ) );
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
        assertObjectStartState( reader );
        assertStringState( reader, "propKey" );
        assertTypeState( reader, ModelType.PROPERTY );
        assertObjectEndState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostComplexObject() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{\"0\":{\"String\":\"s\",\"boolean\":false},\"1\":[null,true,7,{}]}" );
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
    public void simpleListWithWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "[ { \t\r\n\"a\" \t\r\n: \t\r\n\"b\" \t\r\n} \t\r\n]" );
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
    public void simpleListWithoutWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "[{\"a\":\"b\"}]" );
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
        final String data = "[ \"0\" \r\n, 1 \r\n, 2 \r\n, 3.0 \t\r\n, \t\r\n{ \t\r\n} \t\r\n, \t\r\n[ \t\r\n] \t\r\n, \t\r\n "
                + "{ \t\r\n\"\" \t\r\n: \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"EXPRESSION\" \t\r\n} \t\r\n} \t\r\n, \t\r\n"
                + "{ \t\r\n\"BYTES_VALUE\" \t\r\n: \t\r\n\"\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"EXPRESSION_VALUE\" \t\r\n: \t\r\n\"\" \t\r\n} \t\r\n,"
                + "400000000000000000000000000000000000000, 500000000000000000000000000000000000000.000000000000000000000000000009 \r\n, true \r\n, null \r\n]";
        final ModelReader reader = getModelReader( data );
        assertListStartState( reader );
        assertStringState( reader, "0" );
        assertNumberState( reader, 1 );
        assertNumberState( reader, 2 );
        assertNumberState( reader, new BigDecimal( "3.0" ) );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListEndState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertObjectEndState( reader );
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
        final String data = "[\"0\",1,2,3.0,{},[],{\"\":{\"TYPE_MODEL_VALUE\":\"EXPRESSION\"}},{\"BYTES_VALUE\":\"\"},{\"EXPRESSION_VALUE\":\"\"},"
                + "40000000000000000000000000000,5000000000000000000000000000000000.0000000000000000000000009,true,null]";
        final ModelReader reader = getModelReader( data );
        assertListStartState( reader );
        assertStringState( reader, "0" );
        assertNumberState( reader, 1 );
        assertNumberState( reader, 2 );
        assertNumberState( reader, new BigDecimal( "3.0" ) );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListEndState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertObjectEndState( reader );
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
    public void moreComplexPropertyWithWhitespaces() throws IOException, ModelException {
        final String data = "{\"moreComplexPropertyKey\":[ \"0\" \r\n, 1 \r\n, 2 \r\n, 3.0 \r\n, \r\n{ \r\n} \r\n, \r\n[ \r\n] \r\n, \r\n "
                + "{ \"\" \r\n: \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"EXPRESSION\" \t\r\n} \t\r\n} \t\r\n, \t\r\n"
                + "{ \t\r\n\"BYTES_VALUE\" \t\r\n: \t\r\n\"\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"EXPRESSION_VALUE\" \t\r\n: \t\r\n\"\" \t\r\n},"
                + "400000000000000000000000000000000000000, 500000000000000000000000000000000000000.000000000000000000000000000009 \r\n, true \r\n, null \r\n]}";
        final ModelReader reader = getModelReader( data );
        assertObjectStartState( reader );
        assertStringState( reader, "moreComplexPropertyKey" );
        assertListStartState( reader );
        assertStringState( reader, "0" );
        assertNumberState( reader, 1 );
        assertNumberState( reader, 2 );
        assertNumberState( reader, new BigDecimal( "3.0" ) );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListEndState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertObjectEndState( reader );
        assertBytesState( reader, new byte[] {} );
        assertExpressionState( reader, "" );
        assertNumberState( reader, new BigInteger( "400000000000000000000000000000000000000" ) );
        assertNumberState( reader, new BigDecimal( "500000000000000000000000000000000000000.000000000000000000000000000009" ) );
        assertBooleanState( reader, true );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void moreComplexPropertyWithoutWhitespaces() throws IOException, ModelException {
        final String data = "{\"moreComplexPropertyKey\":[\"0\",1,2,3.0,{},[],{\"\":{\"TYPE_MODEL_VALUE\":\"EXPRESSION\"}},{\"BYTES_VALUE\":\"\"},{\"EXPRESSION_VALUE\":\"\"},"
                + "40000000000000000000000000000,5000000000000000000000000000000000.0000000000000000000000009,true,null]}";
        final ModelReader reader = getModelReader( data );
        assertObjectStartState( reader );
        assertStringState( reader, "moreComplexPropertyKey" );
        assertListStartState( reader );
        assertStringState( reader, "0" );
        assertNumberState( reader, 1 );
        assertNumberState( reader, 2 );
        assertNumberState( reader, new BigDecimal( "3.0" ) );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertListStartState( reader );
        assertListEndState( reader );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertTypeState( reader, ModelType.EXPRESSION );
        assertObjectEndState( reader );
        assertBytesState( reader, new byte[] {} );
        assertExpressionState( reader, "" );
        assertNumberState( reader, new BigInteger( "40000000000000000000000000000" ) );
        assertNumberState( reader, new BigDecimal( "5000000000000000000000000000000000.0000000000000000000000009" ) );
        assertBooleanState( reader, true );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostComplexListWithoutWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "[{\"list\":[{\"expr\":{\"EXPRESSION_VALUE\":\"\"},\"BTS\":{\"BYTES_VALUE\":\"AAECAw==\"}}]},\"0\",{\"String\":\"s\",\"boolean\":false},{\"booleans\":[true,false,null]},\"1\",[null,true,7,{},[["
                + "{\"TYPE_MODEL_VALUE\":\"OBJECT\"},{\"TYPE_MODEL_VALUE\":\"LIST\"},{\"TYPE_MODEL_VALUE\":\"PROPERTY\"},{\"TYPE_MODEL_VALUE\":\"STRING\"},"
                + "{\"TYPE_MODEL_VALUE\":\"INT\"},{\"TYPE_MODEL_VALUE\":\"LONG\"},{\"TYPE_MODEL_VALUE\":\"DOUBLE\"},{\"TYPE_MODEL_VALUE\":\"BIG_INTEGER\"},"
                + "{\"TYPE_MODEL_VALUE\":\"BIG_DECIMAL\"},{\"TYPE_MODEL_VALUE\":\"BYTES\"},{\"TYPE_MODEL_VALUE\":\"EXPRESSION\"},{\"TYPE_MODEL_VALUE\":\"TYPE\"},"
                + "{\"TYPE_MODEL_VALUE\":\"BOOLEAN\"},{\"TYPE_MODEL_VALUE\":\"UNDEFINED\"}]]]]" );
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
        assertObjectStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
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
        final ModelReader reader = getModelReader( "[ { \r\"list\"\t: [\r{\t\"expr\" \t\r\n: \t\r\n{ \t\r\n\"EXPRESSION_VALUE\" \t\r\n: \t\r\n\"\" \t\r\n} \t\r\n , \"BTS\" : {\"BYTES_VALUE\":\"AAECAw==\"} } ] } , \"0\" \t\r\n, \t\r\n{ \"String\" : \"s\" , \"boolean\" \r: false } , {\"booleans\" : [ true , false , null]} , \"1\", [ null , true,7,{},[["
                + " \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"OBJECT\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"LIST\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"PROPERTY\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n:\"STRING\" \t\r\n},"
                + " \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"INT\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"LONG\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"DOUBLE\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n:\"BIG_INTEGER\" \t\r\n},"
                + " \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"BIG_DECIMAL\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"BYTES\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"EXPRESSION\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n:\"TYPE\" \t\r\n},"
                + " \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"BOOLEAN\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"UNDEFINED\"} \t\r\n] \t\r\n] \t\r\n] \t\r\n]" );
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
        assertObjectStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
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
        final ModelReader reader = getModelReader( "{\"the Most Complex Object\":[{\"list\":[{\"expr\":{\"EXPRESSION_VALUE\":\"\"},\"BTS\":{\"BYTES_VALUE\":\"AAECAw==\"}}]},\"0\",{\"String\":\"s\",\"boolean\":false},{\"booleans\":[true,false,null]},\"1\",[null,true,7,{},[["
                + "{\"TYPE_MODEL_VALUE\":\"OBJECT\"},{\"TYPE_MODEL_VALUE\":\"LIST\"},{\"TYPE_MODEL_VALUE\":\"PROPERTY\"},{\"TYPE_MODEL_VALUE\":\"STRING\"},"
                + "{\"TYPE_MODEL_VALUE\":\"INT\"},{\"TYPE_MODEL_VALUE\":\"LONG\"},{\"TYPE_MODEL_VALUE\":\"DOUBLE\"},{\"TYPE_MODEL_VALUE\":\"BIG_INTEGER\"},"
                + "{\"TYPE_MODEL_VALUE\":\"BIG_DECIMAL\"},{\"TYPE_MODEL_VALUE\":\"BYTES\"},{\"TYPE_MODEL_VALUE\":\"EXPRESSION\"},{\"TYPE_MODEL_VALUE\":\"TYPE\"},"
                + "{\"TYPE_MODEL_VALUE\":\"BOOLEAN\"},{\"TYPE_MODEL_VALUE\":\"UNDEFINED\"}]]]]}" );
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
        assertObjectStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
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
        final ModelReader reader = getModelReader( "{ \"the Most Complex Object\" : [ { \r\"list\"\t: [\r{\t\"expr\" : \t\r\n{ \t\r\n\"EXPRESSION_VALUE\" \t\r\n: \t\r\n\"\" \t\r\n} , \"BTS\" : {\"BYTES_VALUE\":\"AAECAw==\"} } ] } , \"0\" \t\r\n, \t\r\n{ \"String\" : \"s\" , \"boolean\" \r: false } , {\"booleans\" : [ true , false , null]} , \"1\", [ null , true,7,{},[["
                + " \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"OBJECT\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"LIST\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"PROPERTY\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n:\"STRING\" \t\r\n},"
                + " \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"INT\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"LONG\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"DOUBLE\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n:\"BIG_INTEGER\" \t\r\n},"
                + " \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"BIG_DECIMAL\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"BYTES\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"EXPRESSION\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n:\"TYPE\" \t\r\n},"
                + " \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"BOOLEAN\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"UNDEFINED\"} \t\r\n] \t\r\n] \t\r\n] \t\r\n] \t\r\n}" );
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
        assertObjectStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
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
        final ModelReader reader = getModelReader( "{\"the Most Complex Property\":{\"bar\":[{\"list\":[{\"expr\":{\"EXPRESSION_VALUE\":\"\"}" +
                ",\"BTS\":{\"BYTES_VALUE\":\"AAECAw==\"}}]},\"0\",{\"String\":\"s\",\"boolean\":false},{\"booleans\":[true,false,null]},\"1\",[null,true,7,{},[["
                + "{\"TYPE_MODEL_VALUE\":\"OBJECT\"},{\"TYPE_MODEL_VALUE\":\"LIST\"},{\"TYPE_MODEL_VALUE\":\"PROPERTY\"},{\"TYPE_MODEL_VALUE\":\"STRING\"},"
                + "{\"TYPE_MODEL_VALUE\":\"INT\"},{\"TYPE_MODEL_VALUE\":\"LONG\"},{\"TYPE_MODEL_VALUE\":\"DOUBLE\"},{\"TYPE_MODEL_VALUE\":\"BIG_INTEGER\"},"
                + "{\"TYPE_MODEL_VALUE\":\"BIG_DECIMAL\"},{\"TYPE_MODEL_VALUE\":\"BYTES\"},{\"TYPE_MODEL_VALUE\":\"EXPRESSION\"},{\"TYPE_MODEL_VALUE\":\"TYPE\"},"
                + "{\"TYPE_MODEL_VALUE\":\"BOOLEAN\"},{\"TYPE_MODEL_VALUE\":\"UNDEFINED\"}]]]]}}" );
        assertObjectStartState( reader );
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
        assertObjectStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
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
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void theMostComplexPropertyWithWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{ \"the Most Complex Property\" : { \"bar\" : [ { \r\"list\"\t: [\r{\t\"expr\" : \t\r\n{ \t\r\n\"EXPRESSION_VALUE\" \t\r\n: \t\r\n\"\" \t\r\n} \t\r\n, \"BTS\" : {\"BYTES_VALUE\":\"AAECAw==\"} } ] } , \"0\" \t\r\n, \t\r\n{ \"String\" : \"s\" , \"boolean\" \r: false } , {\"booleans\" : [ true , false , null]} , \"1\", [ null , true,7,{},[["
                + " \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"OBJECT\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"LIST\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"PROPERTY\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n:\"STRING\" \t\r\n},"
                + " \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"INT\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"LONG\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"DOUBLE\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n:\"BIG_INTEGER\" \t\r\n},"
                + " \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"BIG_DECIMAL\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"BYTES\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"EXPRESSION\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n:\"TYPE\" \t\r\n},"
                + " \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"BOOLEAN\" \t\r\n} \t\r\n, \t\r\n{ \t\r\n\"TYPE_MODEL_VALUE\" \t\r\n: \t\r\n\"UNDEFINED\"} \t\r\n] \t\r\n] \t\r\n] \t\r\n] \t\r\n} \t\r\n}" );
        assertObjectStartState( reader );
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
        assertObjectStartState( reader );
        assertStringState( reader, "booleans" );
        assertListStartState( reader );
        assertBooleanState( reader, true );
        assertBooleanState( reader, false );
        assertUndefinedState( reader );
        assertListEndState( reader );
        assertObjectEndState( reader );
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
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void simplePropertyWithWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{\r\n\t\"a\" : \"b\"\r\n}" );
        assertObjectStartState( reader );
        assertStringState( reader, "a" );
        assertStringState( reader, "b" );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

    @Test
    public void simplePropertyWithoutWhitespaces() throws IOException, ModelException {
        final ModelReader reader = getModelReader( "{\"a\":\"b\"}" );
        assertObjectStartState( reader );
        assertStringState( reader, "a" );
        assertStringState( reader, "b" );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
        assertClosedState( reader );
    }

}
