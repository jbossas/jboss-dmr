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

package org.jboss.dmr.stream;

import static org.jboss.dmr.ModelType.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class ValidModelWriterTestCase extends AbstractModelStreamsTestCase {

    private ByteArrayOutputStream baos;

    private ModelWriter writer;

    @Override
    public ModelWriter getModelWriter() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        return ModelStreamFactory.getInstance( false ).newModelWriter( baos );
    }

    @Before
    public void init() {
        baos = new ByteArrayOutputStream();
        writer = ModelStreamFactory.getInstance( false ).newModelWriter( new OutputStreamWriter( baos ) );
    }

    @After
    public void destroy() {
        baos = null;
        writer = null;
    }

    @Test
    public void emptyList() throws IOException, ModelException {
        writer.writeListStart();
        writer.writeListEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "[]", getWriterOutput() );
    }

    @Test
    public void moreComplexList() throws IOException, ModelException {
        writer.writeListStart();
        writer.writeString( "0" );
        writer.writeInt( Integer.MIN_VALUE );
        writer.writeLong( Long.MIN_VALUE );
        writer.writeDouble( 6.0 );
        writer.writeDouble(Double.NaN);
        writer.writeDouble(Double.NEGATIVE_INFINITY);
        writer.writeDouble(Double.POSITIVE_INFINITY);
        writer.writeBigInteger( new BigInteger( "700000000000000000000000000000000000000" ) );
        writer.writeBigDecimal( new BigDecimal( "800000000000000000000000000000000000000.000000000000000000000000000009" ) );
        writer.writeBoolean( true );
        writer.writeUndefined();
        writer.writeListEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        final String expected = "[\"0\"," + Integer.MIN_VALUE + "," + Long.MIN_VALUE + "L,6.0,NaN,-Infinity,Infinity,"
            + "big integer 700000000000000000000000000000000000000,big decimal 800000000000000000000000000000000000000.000000000000000000000000000009,true,undefined]";
        Assert.assertEquals( expected, getWriterOutput() );
    }

    @Test
    public void theMostComplexList() throws IOException, ModelException {
        writer.writeListStart();
        writer.writeString( "0" );
        writer.writeObjectStart();
        writer.writeString( "String" );
        writer.writeString( "s" );
        writer.writeString( "boolean" );
        writer.writeBoolean( false );
        writer.writeObjectEnd();
        writer.writeString( "1" );
        writer.writeListStart();
        writer.writeUndefined();
        writer.writeBoolean( true );
        writer.writeInt( 7 );
        writer.writeObjectStart();
        writer.writeObjectEnd();
        writer.writeListStart();
        writer.writeListStart();
        writer.writeType(OBJECT).writeType(LIST).writeType(PROPERTY).writeType(STRING).writeType(INT).writeType(LONG).writeType(DOUBLE);
        writer.writeType(BIG_INTEGER).writeType(BIG_DECIMAL).writeType(BYTES).writeType(EXPRESSION).writeType(TYPE).writeType(BOOLEAN).writeType(UNDEFINED);
        writer.writeListEnd();
        writer.writeListEnd();
        writer.writeListEnd();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeObjectStart();
        writer.writeString("nestedPropertyObject");
        writer.writeBytes(new byte[] {' ', '\n'});
        writer.writeObjectEnd();
        writer.writePropertyStart();
        writer.writeString("nestedPropertyProperty");
        writer.writeBytes(new byte[] {'0', '1'});
        writer.writePropertyEnd();
        writer.writeListEnd();
        writer.writePropertyEnd();
        writer.writeBytes(new byte[] {});
        writer.writeExpression("env.JAVA_HOME");
        writer.writeUndefined();
        writer.writeListEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "[\"0\",{\"String\"=>\"s\",\"boolean\"=>false},\"1\",[undefined,true,7,{},[[" +
                "OBJECT,LIST,PROPERTY,STRING,INT,LONG,DOUBLE,BIG_INTEGER,BIG_DECIMAL,BYTES,EXPRESSION,TYPE,BOOLEAN,UNDEFINED]]]," +
                "(\"propertyKey\"=>[{\"nestedPropertyObject\"=>bytes {0x20,0x0a}},(\"nestedPropertyProperty\"=>bytes {0x30,0x31})])," +
                "bytes {},expression \"env.JAVA_HOME\",undefined]", getWriterOutput() );
    }

    @Test
    public void emptyObject() throws IOException, ModelException {
        writer.writeObjectStart();
        writer.writeObjectEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "{}", getWriterOutput() );
    }

    @Test
    public void moreComplexObject() throws IOException, ModelException {
        writer.writeObjectStart();
        writer.writeString( "0" );
        writer.writeString( "0" );
        writer.writeString( "3" );
        writer.writeInt( 3 );
        writer.writeString( "4" );
        writer.writeLong( 4L );
        writer.writeString( "6" );
        writer.writeDouble( 6.0 );
        writer.writeString( "7" );
        writer.writeBoolean( true );
        writer.writeString( "8" );
        writer.writeUndefined();
        writer.writeString( "9" );
        writer.writeBigInteger( new BigInteger( "900000000000000000000000000000000000000" ) );
        writer.writeString( "10" );
        writer.writeBigDecimal( new BigDecimal( "100000000000000000000000000000000000000.000000000000000000000000000001" ) );
        writer.writeObjectEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        final String expected = "{\"0\"=>\"0\",\"3\"=>3,\"4\"=>4L,\"6\"=>6.0,\"7\"=>true,\"8\"=>undefined,\"9\"=>"
            + "big integer 900000000000000000000000000000000000000,\"10\"=>big decimal 100000000000000000000000000000000000000.000000000000000000000000000001}";
        Assert.assertEquals( expected, getWriterOutput() );
    }

    @Test
    public void theMostComplexObject() throws IOException, ModelException {
        writer.writeObjectStart();
        writer.writeString( "0" );
        writer.writeObjectStart();
        writer.writeString( "String" );
        writer.writeString( "s" );
        writer.writeString( "boolean" );
        writer.writeBoolean( false );
        writer.writeObjectEnd();
        writer.writeString( "1" );
        writer.writeListStart();
        writer.writeUndefined();
        writer.writeBoolean( true );
        writer.writeInt( 7 );
        writer.writeObjectStart();
        writer.writeObjectEnd();
        writer.writeListEnd();
        writer.writeString( "11" );
        writer.writeListStart();
        writer.writeString( "0" );
        writer.writeObjectStart();
        writer.writeString( "String" );
        writer.writeString( "s" );
        writer.writeString( "boolean" );
        writer.writeBoolean( false );
        writer.writeObjectEnd();
        writer.writeString( "1" );
        writer.writeListStart();
        writer.writeUndefined();
        writer.writeBoolean( true );
        writer.writeInt( 7 );
        writer.writeObjectStart();
        writer.writeObjectEnd();
        writer.writeListStart();
        writer.writeListStart();
        writer.writeType(OBJECT).writeType(LIST).writeType(PROPERTY).writeType(STRING).writeType(INT).writeType(LONG).writeType(DOUBLE);
        writer.writeType(BIG_INTEGER).writeType(BIG_DECIMAL).writeType(BYTES).writeType(EXPRESSION).writeType(TYPE).writeType(BOOLEAN).writeType(UNDEFINED);
        writer.writeListEnd();
        writer.writeListEnd();
        writer.writeListEnd();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeObjectStart();
        writer.writeString("nestedPropertyObject");
        writer.writeBytes(new byte[] {' ', '\n'});
        writer.writeObjectEnd();
        writer.writePropertyStart();
        writer.writeString("nestedPropertyProperty");
        writer.writeBytes(new byte[] {'0', '1'});
        writer.writePropertyEnd();
        writer.writeListEnd();
        writer.writePropertyEnd();
        writer.writeBytes(new byte[] {});
        writer.writeExpression("env.JAVA_HOME");
        writer.writeUndefined();
        writer.writeListEnd();
        writer.writeObjectEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "{\"0\"=>{\"String\"=>\"s\",\"boolean\"=>false},\"1\"=>[undefined,true,7,{}],\"11\"=>" +
                "[\"0\",{\"String\"=>\"s\",\"boolean\"=>false},\"1\",[undefined,true,7,{},[[" +
                "OBJECT,LIST,PROPERTY,STRING,INT,LONG,DOUBLE,BIG_INTEGER,BIG_DECIMAL,BYTES,EXPRESSION,TYPE,BOOLEAN,UNDEFINED]]]," +
                "(\"propertyKey\"=>[{\"nestedPropertyObject\"=>bytes {0x20,0x0a}},(\"nestedPropertyProperty\"=>bytes {0x30,0x31})])," +
                "bytes {},expression \"env.JAVA_HOME\",undefined]}", getWriterOutput() );
    }

    @Test
    public void emptyProperty() throws IOException, ModelException {
        writer.writePropertyStart();
        writer.writeString("");
        writer.writeUndefined();
        writer.writePropertyEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "(\"\"=>undefined)", getWriterOutput() );
    }

    @Test
    public void moreComplexProperty() throws IOException, ModelException {
        writer.writePropertyStart();
        writer.writeString( "0" );
        writer.writeListStart();
        writer.writeInt( Integer.MIN_VALUE );
        writer.writeLong( Long.MIN_VALUE );
        writer.writeDouble( 6.0 );
        writer.writeDouble(Double.NaN);
        writer.writeDouble(Double.NEGATIVE_INFINITY);
        writer.writeDouble(Double.POSITIVE_INFINITY);
        writer.writeBigInteger( new BigInteger( "700000000000000000000000000000000000000" ) );
        writer.writeBigDecimal( new BigDecimal( "800000000000000000000000000000000000000.000000000000000000000000000009" ) );
        writer.writeBoolean( true );
        writer.writeUndefined();
        writer.writeListEnd();
        writer.writePropertyEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        final String expected = "(\"0\"=>[" + Integer.MIN_VALUE + "," + Long.MIN_VALUE + "L,6.0,NaN,-Infinity,Infinity,"
                + "big integer 700000000000000000000000000000000000000,big decimal 800000000000000000000000000000000000000.000000000000000000000000000009,true,undefined])";
        Assert.assertEquals( expected, getWriterOutput() );
    }

    @Test
    public void theMostComplexProperty() throws IOException, ModelException {
        writer.writePropertyStart();
        writer.writeString("theMostComplexProperty");
        writer.writeListStart();
        writer.writeString( "0" );
        writer.writeObjectStart();
        writer.writeString( "String" );
        writer.writeString( "s" );
        writer.writeString( "boolean" );
        writer.writeBoolean( false );
        writer.writeObjectEnd();
        writer.writeString( "1" );
        writer.writeListStart();
        writer.writeUndefined();
        writer.writeBoolean( true );
        writer.writeInt( 7 );
        writer.writeObjectStart();
        writer.writeObjectEnd();
        writer.writeListStart();
        writer.writeListStart();
        writer.writeType(OBJECT).writeType(LIST).writeType(PROPERTY).writeType(STRING).writeType(INT).writeType(LONG).writeType(DOUBLE);
        writer.writeType(BIG_INTEGER).writeType(BIG_DECIMAL).writeType(BYTES).writeType(EXPRESSION).writeType(TYPE).writeType(BOOLEAN).writeType(UNDEFINED);
        writer.writeListEnd();
        writer.writeListEnd();
        writer.writeListEnd();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeObjectStart();
        writer.writeString("nestedPropertyObject");
        writer.writeBytes(new byte[] {' ', '\n'});
        writer.writeObjectEnd();
        writer.writePropertyStart();
        writer.writeString("nestedPropertyProperty");
        writer.writeBytes(new byte[] {'0', '1'});
        writer.writePropertyEnd();
        writer.writeListEnd();
        writer.writePropertyEnd();
        writer.writeBytes(new byte[] {});
        writer.writeExpression("env.JAVA_HOME");
        writer.writeUndefined();
        writer.writeListEnd();
        writer.writePropertyEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "(\"theMostComplexProperty\"=>[\"0\",{\"String\"=>\"s\",\"boolean\"=>false},\"1\",[undefined,true,7,{},[[" +
                "OBJECT,LIST,PROPERTY,STRING,INT,LONG,DOUBLE,BIG_INTEGER,BIG_DECIMAL,BYTES,EXPRESSION,TYPE,BOOLEAN,UNDEFINED]]]," +
                "(\"propertyKey\"=>[{\"nestedPropertyObject\"=>bytes {0x20,0x0a}},(\"nestedPropertyProperty\"=>bytes {0x30,0x31})])," +
                "bytes {},expression \"env.JAVA_HOME\",undefined])", getWriterOutput() );
    }
    @Test
    public void escapesEncoding() throws IOException, ModelException {
        writer.writeListStart();
        writer.writeString( "\"\\" );
        writer.writeListEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "[\"\\\"\\\\\"]", getWriterOutput() );
    }

    private String getWriterOutput() {
        return baos.toString();
    }
}
