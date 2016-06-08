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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class InvalidModelWriterTestCase extends AbstractModelStreamsTestCase {

    @Override
    public ModelWriter getModelWriter() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        return ModelStreamFactory.getInstance( false ).newModelWriter( baos );
    }

    @Test
    public void emptyState() throws IOException, ModelException {
        write_objectEnd();
        write_listEnd();
        write_propertyEnd();
    }

    @Test
    public void emptyObjectStartState() throws IOException, ModelException {
        write_objectStart_listEnd();
        write_objectStart_propertyEnd();
        write_objectStart_int();
        write_objectStart_long();
        write_objectStart_double();
        write_objectStart_bigInteger();
        write_objectStart_bigDecimal();
        write_objectStart_boolean();
        write_objectStart_undefined();
        write_objectStart_bytes();
        write_objectStart_expression();
        write_objectStart_type();
    }

    @Test
    public void emptylistStartState() throws IOException, ModelException {
        write_listStart_objectEnd();
        write_listStart_propertyEnd();
    }

    @Test
    public void emptyPropertyStartState() throws IOException, ModelException {
        write_propertyStart_listEnd();
        write_propertyStart_objectEnd();
        write_propertyStart_propertyEnd();
        write_propertyStart_int();
        write_propertyStart_long();
        write_propertyStart_double();
        write_propertyStart_bigInteger();
        write_propertyStart_bigDecimal();
        write_propertyStart_boolean();
        write_propertyStart_undefined();
        write_propertyStart_bytes();
        write_propertyStart_expression();
        write_propertyStart_type();
    }

    @Test
    public void notEmptyPropertyState() throws IOException, ModelException {
        write_propertyStart_string_listStart_listEnd_listStart();
        write_propertyStart_string_listStart_listEnd_listEnd();
        write_propertyStart_string_listStart_listEnd_objectStart();
        write_propertyStart_string_listStart_listEnd_objectEnd();
        write_propertyStart_string_listStart_listEnd_propertyStart();
        write_propertyStart_string_listStart_listEnd_string();
        write_propertyStart_string_listStart_listEnd_int();
        write_propertyStart_string_listStart_listEnd_long();
        write_propertyStart_string_listStart_listEnd_double();
        write_propertyStart_string_listStart_listEnd_bigInteger();
        write_propertyStart_string_listStart_listEnd_bigDecimal();
        write_propertyStart_string_listStart_listEnd_boolean();
        write_propertyStart_string_listStart_listEnd_undefined();
        write_propertyStart_string_listStart_listEnd_bytes();
        write_propertyStart_string_listStart_listEnd_expression();
        write_propertyStart_string_listStart_listEnd_type();

        write_propertyStart_string_objectStart_objectEnd_listStart();
        write_propertyStart_string_objectStart_objectEnd_listEnd();
        write_propertyStart_string_objectStart_objectEnd_objectStart();
        write_propertyStart_string_objectStart_objectEnd_objectEnd();
        write_propertyStart_string_objectStart_objectEnd_propertyStart();
        write_propertyStart_string_objectStart_objectEnd_string();
        write_propertyStart_string_objectStart_objectEnd_int();
        write_propertyStart_string_objectStart_objectEnd_long();
        write_propertyStart_string_objectStart_objectEnd_double();
        write_propertyStart_string_objectStart_objectEnd_bigInteger();
        write_propertyStart_string_objectStart_objectEnd_bigDecimal();
        write_propertyStart_string_objectStart_objectEnd_boolean();
        write_propertyStart_string_objectStart_objectEnd_undefined();
        write_propertyStart_string_objectStart_objectEnd_bytes();
        write_propertyStart_string_objectStart_objectEnd_expression();
        write_propertyStart_string_objectStart_objectEnd_type();

        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_listStart();
        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_listEnd();
        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_objectStart();
        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_objectEnd();
        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_propertyStart();
        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_string();
        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_int();
        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_long();
        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_double();
        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_bigInteger();
        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_bigDecimal();
        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_boolean();
        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_undefined();
        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_bytes();
        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_expression();
        write_propertyStart_string_propertyStart_string_undefined_propertyEnd_type();

        write_propertyStart_string_string_listStart();
        write_propertyStart_string_string_listEnd();
        write_propertyStart_string_string_objectStart();
        write_propertyStart_string_string_objectEnd();
        write_propertyStart_string_string_propertyStart();
        write_propertyStart_string_string_string();
        write_propertyStart_string_string_int();
        write_propertyStart_string_string_long();
        write_propertyStart_string_string_double();
        write_propertyStart_string_string_bigInteger();
        write_propertyStart_string_string_bigDecimal();
        write_propertyStart_string_string_boolean();
        write_propertyStart_string_string_undefined();
        write_propertyStart_string_string_bytes();
        write_propertyStart_string_string_expression();
        write_propertyStart_string_string_type();

        write_propertyStart_string_int_listStart();
        write_propertyStart_string_int_listEnd();
        write_propertyStart_string_int_objectStart();
        write_propertyStart_string_int_objectEnd();
        write_propertyStart_string_int_propertyStart();
        write_propertyStart_string_int_string();
        write_propertyStart_string_int_int();
        write_propertyStart_string_int_long();
        write_propertyStart_string_int_double();
        write_propertyStart_string_int_bigInteger();
        write_propertyStart_string_int_bigDecimal();
        write_propertyStart_string_int_boolean();
        write_propertyStart_string_int_undefined();
        write_propertyStart_string_int_bytes();
        write_propertyStart_string_int_expression();
        write_propertyStart_string_int_type();

        write_propertyStart_string_long_listStart();
        write_propertyStart_string_long_listEnd();
        write_propertyStart_string_long_objectStart();
        write_propertyStart_string_long_objectEnd();
        write_propertyStart_string_long_propertyStart();
        write_propertyStart_string_long_string();
        write_propertyStart_string_long_int();
        write_propertyStart_string_long_long();
        write_propertyStart_string_long_double();
        write_propertyStart_string_long_bigInteger();
        write_propertyStart_string_long_bigDecimal();
        write_propertyStart_string_long_boolean();
        write_propertyStart_string_long_undefined();
        write_propertyStart_string_long_bytes();
        write_propertyStart_string_long_expression();
        write_propertyStart_string_long_type();

        write_propertyStart_string_double_listStart();
        write_propertyStart_string_double_listEnd();
        write_propertyStart_string_double_objectStart();
        write_propertyStart_string_double_objectEnd();
        write_propertyStart_string_double_propertyStart();
        write_propertyStart_string_double_string();
        write_propertyStart_string_double_int();
        write_propertyStart_string_double_long();
        write_propertyStart_string_double_double();
        write_propertyStart_string_double_bigInteger();
        write_propertyStart_string_double_bigDecimal();
        write_propertyStart_string_double_boolean();
        write_propertyStart_string_double_undefined();
        write_propertyStart_string_double_bytes();
        write_propertyStart_string_double_expression();
        write_propertyStart_string_double_type();

        write_propertyStart_string_bigInteger_listStart();
        write_propertyStart_string_bigInteger_listEnd();
        write_propertyStart_string_bigInteger_objectStart();
        write_propertyStart_string_bigInteger_objectEnd();
        write_propertyStart_string_bigInteger_propertyStart();
        write_propertyStart_string_bigInteger_string();
        write_propertyStart_string_bigInteger_int();
        write_propertyStart_string_bigInteger_long();
        write_propertyStart_string_bigInteger_double();
        write_propertyStart_string_bigInteger_bigInteger();
        write_propertyStart_string_bigInteger_bigDecimal();
        write_propertyStart_string_bigInteger_boolean();
        write_propertyStart_string_bigInteger_undefined();
        write_propertyStart_string_bigInteger_bytes();
        write_propertyStart_string_bigInteger_expression();
        write_propertyStart_string_bigInteger_type();

        write_propertyStart_string_bigDecimal_listStart();
        write_propertyStart_string_bigDecimal_listEnd();
        write_propertyStart_string_bigDecimal_objectStart();
        write_propertyStart_string_bigDecimal_objectEnd();
        write_propertyStart_string_bigDecimal_propertyStart();
        write_propertyStart_string_bigDecimal_string();
        write_propertyStart_string_bigDecimal_int();
        write_propertyStart_string_bigDecimal_long();
        write_propertyStart_string_bigDecimal_double();
        write_propertyStart_string_bigDecimal_bigInteger();
        write_propertyStart_string_bigDecimal_bigDecimal();
        write_propertyStart_string_bigDecimal_boolean();
        write_propertyStart_string_bigDecimal_undefined();
        write_propertyStart_string_bigDecimal_bytes();
        write_propertyStart_string_bigDecimal_expression();
        write_propertyStart_string_bigDecimal_type();

        write_propertyStart_string_boolean_listStart();
        write_propertyStart_string_boolean_listEnd();
        write_propertyStart_string_boolean_objectStart();
        write_propertyStart_string_boolean_objectEnd();
        write_propertyStart_string_boolean_propertyStart();
        write_propertyStart_string_boolean_string();
        write_propertyStart_string_boolean_int();
        write_propertyStart_string_boolean_long();
        write_propertyStart_string_boolean_double();
        write_propertyStart_string_boolean_bigInteger();
        write_propertyStart_string_boolean_bigDecimal();
        write_propertyStart_string_boolean_boolean();
        write_propertyStart_string_boolean_undefined();
        write_propertyStart_string_boolean_bytes();
        write_propertyStart_string_boolean_expression();
        write_propertyStart_string_boolean_type();

        write_propertyStart_string_undefined_listStart();
        write_propertyStart_string_undefined_listEnd();
        write_propertyStart_string_undefined_objectStart();
        write_propertyStart_string_undefined_objectEnd();
        write_propertyStart_string_undefined_propertyStart();
        write_propertyStart_string_undefined_string();
        write_propertyStart_string_undefined_int();
        write_propertyStart_string_undefined_long();
        write_propertyStart_string_undefined_double();
        write_propertyStart_string_undefined_bigInteger();
        write_propertyStart_string_undefined_bigDecimal();
        write_propertyStart_string_undefined_boolean();
        write_propertyStart_string_undefined_undefined();
        write_propertyStart_string_undefined_bytes();
        write_propertyStart_string_undefined_expression();
        write_propertyStart_string_undefined_type();

        write_propertyStart_string_bytes_listStart();
        write_propertyStart_string_bytes_listEnd();
        write_propertyStart_string_bytes_objectStart();
        write_propertyStart_string_bytes_objectEnd();
        write_propertyStart_string_bytes_propertyStart();
        write_propertyStart_string_bytes_string();
        write_propertyStart_string_bytes_int();
        write_propertyStart_string_bytes_long();
        write_propertyStart_string_bytes_double();
        write_propertyStart_string_bytes_bigInteger();
        write_propertyStart_string_bytes_bigDecimal();
        write_propertyStart_string_bytes_boolean();
        write_propertyStart_string_bytes_undefined();
        write_propertyStart_string_bytes_bytes();
        write_propertyStart_string_bytes_expression();
        write_propertyStart_string_bytes_type();

        write_propertyStart_string_expression_listStart();
        write_propertyStart_string_expression_listEnd();
        write_propertyStart_string_expression_objectStart();
        write_propertyStart_string_expression_objectEnd();
        write_propertyStart_string_expression_propertyStart();
        write_propertyStart_string_expression_string();
        write_propertyStart_string_expression_int();
        write_propertyStart_string_expression_long();
        write_propertyStart_string_expression_double();
        write_propertyStart_string_expression_bigInteger();
        write_propertyStart_string_expression_bigDecimal();
        write_propertyStart_string_expression_boolean();
        write_propertyStart_string_expression_undefined();
        write_propertyStart_string_expression_bytes();
        write_propertyStart_string_expression_expression();
        write_propertyStart_string_expression_type();

        write_propertyStart_string_type_listStart();
        write_propertyStart_string_type_listEnd();
        write_propertyStart_string_type_objectStart();
        write_propertyStart_string_type_objectEnd();
        write_propertyStart_string_type_propertyStart();
        write_propertyStart_string_type_string();
        write_propertyStart_string_type_int();
        write_propertyStart_string_type_long();
        write_propertyStart_string_type_double();
        write_propertyStart_string_type_bigInteger();
        write_propertyStart_string_type_bigDecimal();
        write_propertyStart_string_type_boolean();
        write_propertyStart_string_type_undefined();
        write_propertyStart_string_type_bytes();
        write_propertyStart_string_type_expression();
        write_propertyStart_string_type_type();
    }

    @Test
    public void notEmptylistStartState() throws IOException, ModelException {
        write_listStart_objectStart_objectEnd_objectEnd();
        write_listStart_listStart_listEnd_objectEnd();
        write_listStart_propertyStart_string_undefined_propertyEnd_objectEnd();
        write_listStart_string_objectEnd();
        write_listStart_int_objectEnd();
        write_listStart_long_objectEnd();
        write_listStart_double_objectEnd();
        write_listStart_bigInteger_objectEnd();
        write_listStart_bigDecimal_objectEnd();
        write_listStart_bytes_objectEnd();
        write_listStart_expression_objectEnd();
        write_listStart_type_objectEnd();
        write_listStart_boolean_objectEnd();
        write_listStart_undefined_objectEnd();
        write_listStart_objectStart_objectEnd_propertyEnd();
        write_listStart_listStart_listEnd_propertyEnd();
        write_listStart_propertyStart_string_undefined_propertyEnd_propertyEnd();
        write_listStart_string_propertyEnd();
        write_listStart_int_propertyEnd();
        write_listStart_long_propertyEnd();
        write_listStart_double_propertyEnd();
        write_listStart_bigInteger_propertyEnd();
        write_listStart_bigDecimal_propertyEnd();
        write_listStart_bytes_propertyEnd();
        write_listStart_expression_propertyEnd();
        write_listStart_type_propertyEnd();
        write_listStart_boolean_propertyEnd();
        write_listStart_undefined_propertyEnd();
    }

    @Test
    public void objectStartObjectEndState() throws IOException, ModelException {
        write_objectStart_objectEnd_objectStart();
        write_objectStart_objectEnd_objectEnd();
        write_objectStart_objectEnd_listStart();
        write_objectStart_objectEnd_listEnd();
        write_objectStart_objectEnd_propertyStart();
        write_objectStart_objectEnd_propertyEnd();
        write_objectStart_objectEnd_string();
        write_objectStart_objectEnd_int();
        write_objectStart_objectEnd_long();
        write_objectStart_objectEnd_double();
        write_objectStart_objectEnd_bigInteger();
        write_objectStart_objectEnd_bigDecimal();
        write_objectStart_objectEnd_bytes();
        write_objectStart_objectEnd_expression();
        write_objectStart_objectEnd_type();
        write_objectStart_objectEnd_boolean();
        write_objectStart_objectEnd_undefined();
    }

    @Test
    public void listStartListEndState() throws IOException, ModelException {
        write_listStart_listEnd_objectStart();
        write_listStart_listEnd_objectEnd();
        write_listStart_listEnd_listStart();
        write_listStart_listEnd_listEnd();
        write_listStart_listEnd_propertyStart();
        write_listStart_listEnd_propertyEnd();
        write_listStart_listEnd_string();
        write_listStart_listEnd_int();
        write_listStart_listEnd_long();
        write_listStart_listEnd_double();
        write_listStart_listEnd_bigInteger();
        write_listStart_listEnd_bigDecimal();
        write_listStart_listEnd_bytes();
        write_listStart_listEnd_expression();
        write_listStart_listEnd_type();
        write_listStart_listEnd_boolean();
        write_listStart_listEnd_undefined();
    }

    @Test
    public void propertyStartPropertyEndState() throws IOException, ModelException {
        write_propertyStart_propertyEnd_objectStart();
        write_propertyStart_propertyEnd_objectEnd();
        write_propertyStart_propertyEnd_listStart();
        write_propertyStart_propertyEnd_listEnd();
        write_propertyStart_propertyEnd_propertyStart();
        write_propertyStart_propertyEnd_propertyEnd();
        write_propertyStart_propertyEnd_string();
        write_propertyStart_propertyEnd_int();
        write_propertyStart_propertyEnd_long();
        write_propertyStart_propertyEnd_double();
        write_propertyStart_propertyEnd_bigInteger();
        write_propertyStart_propertyEnd_bigDecimal();
        write_propertyStart_propertyEnd_bytes();
        write_propertyStart_propertyEnd_expression();
        write_propertyStart_propertyEnd_type();
        write_propertyStart_propertyEnd_boolean();
        write_propertyStart_propertyEnd_undefined();
    }

    @Test
    public void stringState() throws IOException, ModelException {
        write_string_objectStart();
        write_string_objectEnd();
        write_string_listStart();
        write_string_listEnd();
        write_string_propertyStart();
        write_string_propertyEnd();
        write_string_string();
        write_string_int();
        write_string_long();
        write_string_double();
        write_string_bigInteger();
        write_string_bigDecimal();
        write_string_bytes();
        write_string_expression();
        write_string_type();
        write_string_boolean();
        write_string_undefined();
    }

    @Test
    public void intState() throws IOException, ModelException {
        write_int_objectStart();
        write_int_objectEnd();
        write_int_listStart();
        write_int_listEnd();
        write_int_propertyStart();
        write_int_propertyEnd();
        write_int_string();
        write_int_int();
        write_int_long();
        write_int_double();
        write_int_bigInteger();
        write_int_bigDecimal();
        write_int_bytes();
        write_int_expression();
        write_int_type();
        write_int_boolean();
        write_int_undefined();
    }

    @Test
    public void longState() throws IOException, ModelException {
        write_long_objectStart();
        write_long_objectEnd();
        write_long_listStart();
        write_long_listEnd();
        write_long_propertyStart();
        write_long_propertyEnd();
        write_long_string();
        write_long_int();
        write_long_long();
        write_long_double();
        write_long_bigInteger();
        write_long_bigDecimal();
        write_long_bytes();
        write_long_expression();
        write_long_type();
        write_long_boolean();
        write_long_undefined();
    }

    @Test
    public void doubleState() throws IOException, ModelException {
        write_double_objectStart();
        write_double_objectEnd();
        write_double_listStart();
        write_double_listEnd();
        write_double_propertyStart();
        write_double_propertyEnd();
        write_double_string();
        write_double_int();
        write_double_long();
        write_double_double();
        write_double_bigInteger();
        write_double_bigDecimal();
        write_double_bytes();
        write_double_expression();
        write_double_type();
        write_double_boolean();
        write_double_undefined();
    }

    @Test
    public void bigIntegerState() throws IOException, ModelException {
        write_bigInteger_objectStart();
        write_bigInteger_objectEnd();
        write_bigInteger_listStart();
        write_bigInteger_listEnd();
        write_bigInteger_propertyStart();
        write_bigInteger_propertyEnd();
        write_bigInteger_string();
        write_bigInteger_int();
        write_bigInteger_long();
        write_bigInteger_double();
        write_bigInteger_bigInteger();
        write_bigInteger_bigDecimal();
        write_bigInteger_bytes();
        write_bigInteger_expression();
        write_bigInteger_type();
        write_bigInteger_boolean();
        write_bigInteger_undefined();
    }

    @Test
    public void bigDecimalState() throws IOException, ModelException {
        write_bigDecimal_objectStart();
        write_bigDecimal_objectEnd();
        write_bigDecimal_listStart();
        write_bigDecimal_listEnd();
        write_bigDecimal_propertyStart();
        write_bigDecimal_propertyEnd();
        write_bigDecimal_string();
        write_bigDecimal_int();
        write_bigDecimal_long();
        write_bigDecimal_double();
        write_bigDecimal_bigInteger();
        write_bigDecimal_bigDecimal();
        write_bigDecimal_bytes();
        write_bigDecimal_expression();
        write_bigDecimal_type();
        write_bigDecimal_boolean();
        write_bigDecimal_undefined();
    }

    @Test
    public void bytesState() throws IOException, ModelException {
        write_bytes_objectStart();
        write_bytes_objectEnd();
        write_bytes_listStart();
        write_bytes_listEnd();
        write_bytes_propertyStart();
        write_bytes_propertyEnd();
        write_bytes_string();
        write_bytes_int();
        write_bytes_long();
        write_bytes_double();
        write_bytes_bigInteger();
        write_bytes_bigDecimal();
        write_bytes_bytes();
        write_bytes_expression();
        write_bytes_type();
        write_bytes_boolean();
        write_bytes_undefined();
    }

    @Test
    public void expressionState() throws IOException, ModelException {
        write_expression_objectStart();
        write_expression_objectEnd();
        write_expression_listStart();
        write_expression_listEnd();
        write_expression_propertyStart();
        write_expression_propertyEnd();
        write_expression_string();
        write_expression_int();
        write_expression_long();
        write_expression_double();
        write_expression_bigInteger();
        write_expression_bigDecimal();
        write_expression_bytes();
        write_expression_expression();
        write_expression_type();
        write_expression_boolean();
        write_expression_undefined();
    }

    @Test
    public void typeState() throws IOException, ModelException {
        write_type_objectStart();
        write_type_objectEnd();
        write_type_listStart();
        write_type_listEnd();
        write_type_propertyStart();
        write_type_propertyEnd();
        write_type_string();
        write_type_int();
        write_type_long();
        write_type_double();
        write_type_bigInteger();
        write_type_bigDecimal();
        write_type_bytes();
        write_type_expression();
        write_type_type();
        write_type_boolean();
        write_type_undefined();
    }

    @Test
    public void booleanState() throws IOException, ModelException {
        write_boolean_objectStart();
        write_boolean_objectEnd();
        write_boolean_listStart();
        write_boolean_listEnd();
        write_boolean_propertyStart();
        write_boolean_propertyEnd();
        write_boolean_string();
        write_boolean_int();
        write_boolean_long();
        write_boolean_double();
        write_boolean_bigInteger();
        write_boolean_bigDecimal();
        write_boolean_bytes();
        write_boolean_expression();
        write_boolean_type();
        write_boolean_boolean();
        write_boolean_undefined();
    }

    @Test
    public void undefinedState() throws IOException, ModelException {
        write_undefined_objectStart();
        write_undefined_objectEnd();
        write_undefined_listStart();
        write_undefined_listEnd();
        write_undefined_propertyStart();
        write_undefined_propertyEnd();
        write_undefined_string();
        write_undefined_int();
        write_undefined_long();
        write_undefined_double();
        write_undefined_bigInteger();
        write_undefined_bigDecimal();
        write_undefined_bytes();
        write_undefined_expression();
        write_undefined_type();
        write_undefined_boolean();
        write_undefined_undefined();
    }

    @Test
    public void notEmptyObjectStartState() throws IOException, ModelException {
        write_objectStart_string_objectEnd();
        write_objectStart_string_listEnd();
        write_objectStart_string_propertyEnd();

        write_objectStart_string_string_listEnd();
        write_objectStart_string_string_propertyEnd();
        write_objectStart_string_string_int();
        write_objectStart_string_string_long();
        write_objectStart_string_string_double();
        write_objectStart_string_string_bigInteger();
        write_objectStart_string_string_bigDecimal();
        write_objectStart_string_string_bytes();
        write_objectStart_string_string_expression();
        write_objectStart_string_string_type();
        write_objectStart_string_string_boolean();
        write_objectStart_string_string_undefined();

        write_objectStart_string_objectStart_objectEnd_string_objectEnd();
        write_objectStart_string_listStart_listEnd_string_objectEnd();
        write_objectStart_string_propertyStart_string_undefined_propertyEnd_string_objectEnd();
        write_objectStart_string_string_string_objectEnd();
        write_objectStart_string_int_string_objectEnd();
        write_objectStart_string_long_string_objectEnd();
        write_objectStart_string_double_string_objectEnd();
        write_objectStart_string_bigInteger_string_objectEnd();
        write_objectStart_string_bigDecimal_string_objectEnd();
        write_objectStart_string_bytes_string_objectEnd();
        write_objectStart_string_expression_string_objectEnd();
        write_objectStart_string_type_string_objectEnd();
        write_objectStart_string_boolean_string_objectEnd();
        write_objectStart_string_undefined_string_objectEnd();

        write_objectStart_string_objectStart_objectEnd_string_listEnd();
        write_objectStart_string_listStart_listEnd_string_listEnd();
        write_objectStart_string_propertyStart_string_undefined_propertyEnd_string_listEnd();
        write_objectStart_string_string_string_listEnd();
        write_objectStart_string_int_string_listEnd();
        write_objectStart_string_long_string_listEnd();
        write_objectStart_string_double_string_listEnd();
        write_objectStart_string_bigInteger_string_listEnd();
        write_objectStart_string_bigDecimal_string_listEnd();
        write_objectStart_string_bytes_string_listEnd();
        write_objectStart_string_expression_string_listEnd();
        write_objectStart_string_type_string_listEnd();
        write_objectStart_string_boolean_string_listEnd();
        write_objectStart_string_undefined_string_listEnd();

        write_objectStart_string_objectStart_objectEnd_string_propertyEnd();
        write_objectStart_string_listStart_listEnd_string_propertyEnd();
        write_objectStart_string_propertyStart_string_undefined_propertyEnd_string_propertyEnd();
        write_objectStart_string_string_string_propertyEnd();
        write_objectStart_string_int_string_propertyEnd();
        write_objectStart_string_long_string_propertyEnd();
        write_objectStart_string_double_string_propertyEnd();
        write_objectStart_string_bigInteger_string_propertyEnd();
        write_objectStart_string_bigDecimal_string_propertyEnd();
        write_objectStart_string_bytes_string_propertyEnd();
        write_objectStart_string_expression_string_propertyEnd();
        write_objectStart_string_type_string_propertyEnd();
        write_objectStart_string_boolean_string_propertyEnd();
        write_objectStart_string_undefined_string_propertyEnd();
    }

    private void write_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_START or LIST_START or PROPERTY_START or STRING or EXPRESSION or BYTES or NUMBER or BOOLEAN or TYPE or UNDEFINED", e.getMessage() );
        }
    }

    private void write_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_START or LIST_START or PROPERTY_START or STRING or EXPRESSION or BYTES or NUMBER or BOOLEAN or TYPE or UNDEFINED", e.getMessage() );
        }
    }

    private void write_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_START or LIST_START or PROPERTY_START or STRING or EXPRESSION or BYTES or NUMBER or BOOLEAN or TYPE or UNDEFINED", e.getMessage() );
        }
    }

    private void write_objectStart_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        try {
            writer.writeExpression("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_propertyStart_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting STRING", e.getMessage() );
        }
    }

    private void write_propertyStart_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting STRING", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting STRING", e.getMessage() );
        }
    }

    private void write_propertyStart_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting STRING", e.getMessage() );
        }
    }

    private void write_propertyStart_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting STRING", e.getMessage() );
        }
    }

    private void write_propertyStart_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting STRING", e.getMessage() );
        }
    }

    private void write_propertyStart_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting STRING", e.getMessage() );
        }
    }

    private void write_propertyStart_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting STRING", e.getMessage() );
        }
    }

    private void write_propertyStart_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting STRING", e.getMessage() );
        }
    }

    private void write_propertyStart_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting STRING", e.getMessage() );
        }
    }

    private void write_propertyStart_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting STRING", e.getMessage() );
        }
    }

    private void write_propertyStart_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        try {
            writer.writeExpression("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting STRING", e.getMessage() );
        }
    }

    private void write_propertyStart_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting STRING", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writeString("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writeInt(0);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writeLong(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writeDouble(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writeBigInteger(BigInteger.TEN);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writeBigDecimal(BigDecimal.ONE);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writeBoolean(false);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_string_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeString("Foo");
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writeString("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writeInt(0);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writeLong(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writeDouble(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writeBigInteger(BigInteger.TEN);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writeBigDecimal(BigDecimal.ONE);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writeBoolean(false);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_int_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeInt(0);
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writeString("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writeInt(0);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writeLong(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writeDouble(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writeBigInteger(BigInteger.TEN);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writeBigDecimal(BigDecimal.ONE);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writeBoolean(false);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_long_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeLong(1L);
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writeString("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writeInt(0);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writeLong(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writeDouble(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writeBigInteger(BigInteger.TEN);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writeBigDecimal(BigDecimal.ONE);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writeBoolean(false);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_double_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeDouble(3.0);
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeString("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeInt(0);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeLong(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeDouble(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeBigInteger(BigInteger.TEN);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeBigDecimal(BigDecimal.ONE);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeBoolean(false);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigInteger_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writeString("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writeInt(0);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writeLong(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writeDouble(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writeBigInteger(BigInteger.TEN);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writeBigDecimal(BigDecimal.ONE);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writeBoolean(false);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bigDecimal_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBigDecimal(BigDecimal.TEN);
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writeString("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writeInt(0);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writeLong(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writeDouble(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writeBigInteger(BigInteger.TEN);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writeBigDecimal(BigDecimal.ONE);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writeBoolean(false);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_boolean_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBoolean(true);
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writeString("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writeInt(0);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writeLong(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writeDouble(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writeBigInteger(BigInteger.TEN);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writeBigDecimal(BigDecimal.ONE);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writeBoolean(false);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_undefined_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writeString("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writeInt(0);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writeLong(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writeDouble(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writeBigInteger(BigInteger.TEN);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writeBigDecimal(BigDecimal.ONE);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writeBoolean(false);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_bytes_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeBytes(new byte[] {});
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writeString("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writeInt(0);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writeLong(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writeDouble(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writeBigInteger(BigInteger.TEN);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writeBigDecimal(BigDecimal.ONE);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writeBoolean(false);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_expression_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeExpression("env.JAVA_HOME");
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writeString("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writeInt(0);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writeLong(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writeDouble(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writeBigInteger(BigInteger.TEN);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writeBigDecimal(BigDecimal.ONE);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writeBoolean(false);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_type_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeType(ModelType.BOOLEAN);
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeString("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeInt(0);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeLong(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeDouble(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeBigInteger(BigInteger.TEN);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeBigDecimal(BigDecimal.ONE);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeBoolean(false);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_listStart_listEnd_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeString("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeInt(0);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeLong(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeDouble(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeBigInteger(BigInteger.TEN);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeBigDecimal(BigDecimal.ONE);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeBoolean(false);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_objectStart_objectEnd_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeString("");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeInt(0);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeLong(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeDouble(0L);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeBigInteger(BigInteger.TEN);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeBigDecimal(BigDecimal.ONE);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeBoolean(false);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_propertyStart_string_propertyStart_string_undefined_propertyEnd_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writePropertyStart();
        writer.writeString("nestedPropertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting PROPERTY_END", e.getMessage() );
        }
    }

    private void write_listStart_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting LIST_END or OBJECT_START or LIST_START or PROPERTY_START or STRING or EXPRESSION or BYTES or NUMBER or BOOLEAN or TYPE or UNDEFINED", e.getMessage() );
        }
    }

    private void write_listStart_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting LIST_END or OBJECT_START or LIST_START or PROPERTY_START or STRING or EXPRESSION or BYTES or NUMBER or BOOLEAN or TYPE or UNDEFINED", e.getMessage() );
        }
    }

    private void write_listStart_objectStart_objectEnd_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_listStart_listEnd_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }

    }

    private void write_listStart_propertyStart_string_undefined_propertyEnd_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }

    }

    private void write_listStart_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_int_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeInt( 0 );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_long_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeLong( 0L );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_bigInteger_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeBigInteger( BigInteger.ZERO );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_bigDecimal_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeBigDecimal( BigDecimal.ZERO );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_bytes_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_expression_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeExpression("foo");
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_type_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_double_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeDouble( 0.0 );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_boolean_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeBoolean( true );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_undefined_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeUndefined();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_objectStart_objectEnd_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_listStart_listEnd_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }

    }

    private void write_listStart_propertyStart_string_undefined_propertyEnd_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }

    }

    private void write_listStart_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeString( "" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_int_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeInt( 0 );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_long_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeLong( 0L );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_bigInteger_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeBigInteger( BigInteger.ZERO );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_bigDecimal_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeBigDecimal( BigDecimal.ZERO );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_bytes_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeBytes(new byte[] {});
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_expression_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeExpression("foo");
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_type_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_double_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeDouble( 0.0 );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_boolean_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeBoolean( true );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_listStart_undefined_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeUndefined();
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or LIST_END", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeString( "" );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeString( "" );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_listStart_listEnd_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeListStart();
        writer.writeListEnd();
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeString( "" );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_propertyStart_propertyEnd_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writeString( "" );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_string_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeString("");
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writeString( "" );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_int_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeInt(1);
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writeString( "" );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_long_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeLong(2L);
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writeString( "" );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_double_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeDouble(3.0);
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeString( "" );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigInteger_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigInteger(BigInteger.TEN);
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writeString( "" );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bigDecimal_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBigDecimal(BigDecimal.ONE);
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeString( "" );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_bytes_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBytes(new byte[] {});
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writeString( "" );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_expression_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeExpression("someEpression");
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeString( "" );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_type_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeType(ModelType.UNDEFINED);
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writeString( "" );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_boolean_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeBoolean(true);
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_objectStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_listStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writeListStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_propertyStart() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writePropertyStart();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_string() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writeString( "" );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_undefined_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeUndefined();
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or OBJECT_END", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting ',' or OBJECT_END", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_int() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_long() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_bigInteger() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_bigDecimal() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_bytes() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeBytes(new byte[] {});
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_expression() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeExpression("someExpression");
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_type() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeType(ModelType.UNDEFINED);
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_double() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_boolean() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_undefined() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeUndefined();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting OBJECT_END or STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_objectStart_objectEnd_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeObjectStart();
        writer.writeObjectEnd();
        writer.writeString( "2" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_listStart_listEnd_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeListStart();
        writer.writeListEnd();
        writer.writeString( "2" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_propertyStart_string_undefined_propertyEnd_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        writer.writeString( "2" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeString( "" );
        writer.writeString( "2" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_int_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeInt( 0 );
        writer.writeString( "2" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_long_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeLong( 0L );
        writer.writeString( "2" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_bigInteger_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeBigInteger( BigInteger.ZERO );
        writer.writeString( "2" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_bigDecimal_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeBigDecimal( BigDecimal.ZERO );
        writer.writeString( "2" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_bytes_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeBytes(new byte[] {});
        writer.writeString( "2" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_expression_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeExpression("someExpression");
        writer.writeString( "2" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_type_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeType(ModelType.UNDEFINED);
        writer.writeString( "2" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_double_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeDouble( 0.0 );
        writer.writeString( "2" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_boolean_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeBoolean( true );
        writer.writeString( "2" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_undefined_string_objectEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeUndefined();
        writer.writeString( "2" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_objectStart_objectEnd_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeObjectStart();
        writer.writeObjectEnd();
        writer.writeString( "2" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_listStart_listEnd_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeListStart();
        writer.writeListEnd();
        writer.writeString( "2" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_propertyStart_string_undefined_propertyEnd_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        writer.writeString( "2" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeString( "" );
        writer.writeString( "2" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_int_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeInt( 0 );
        writer.writeString( "2" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_long_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeLong( 0L );
        writer.writeString( "2" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_bigInteger_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeBigInteger( BigInteger.ZERO );
        writer.writeString( "2" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_bigDecimal_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeBigDecimal( BigDecimal.ZERO );
        writer.writeString( "2" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_bytes_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeBytes(new byte[] {});
        writer.writeString( "2" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_expression_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeExpression("someExpression");
        writer.writeString( "2" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_type_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeType(ModelType.UNDEFINED);
        writer.writeString( "2" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_double_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeDouble( 0.0 );
        writer.writeString( "2" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_boolean_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeBoolean( true );
        writer.writeString( "2" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_undefined_string_listEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeUndefined();
        writer.writeString( "2" );
        try {
            writer.writeListEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_objectStart_objectEnd_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeObjectStart();
        writer.writeObjectEnd();
        writer.writeString( "2" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_listStart_listEnd_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeListStart();
        writer.writeListEnd();
        writer.writeString( "2" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_propertyStart_string_undefined_propertyEnd_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writePropertyStart();
        writer.writeString("propertyKey");
        writer.writeUndefined();
        writer.writePropertyEnd();
        writer.writeString( "2" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeString( "" );
        writer.writeString( "2" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_int_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeInt( 0 );
        writer.writeString( "2" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_long_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeLong( 0L );
        writer.writeString( "2" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_bigInteger_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeBigInteger( BigInteger.ZERO );
        writer.writeString( "2" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_bigDecimal_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeBigDecimal( BigDecimal.ZERO );
        writer.writeString( "2" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_bytes_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeBytes(new byte[] {});
        writer.writeString( "2" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_expression_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeExpression("someExpression");
        writer.writeString( "2" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_type_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeType(ModelType.UNDEFINED);
        writer.writeString( "2" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_double_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeDouble( 0.0 );
        writer.writeString( "2" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_boolean_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeBoolean( true );
        writer.writeString( "2" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

    private void write_objectStart_string_undefined_string_propertyEnd() throws IOException, ModelException {
        final ModelWriter writer = getModelWriter();
        writer.writeObjectStart();
        writer.writeString( "1" );
        writer.writeUndefined();
        writer.writeString( "2" );
        try {
            writer.writePropertyEnd();
            fail();
        } catch ( final ModelException e ) {
            assertEquals( "Expecting '=>'", e.getMessage() );
        }
    }

}
