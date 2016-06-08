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

import java.io.Flushable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * DMR writer. Instances of this interface are not thread safe.
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 * @see ModelStreamFactory
 */
public interface ModelWriter extends Flushable, AutoCloseable {

    /**
     * Writes DMR <code>object start</code> token.
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writeObjectStart() throws IOException, ModelException;

    /**
     * Writes DMR <code>object end</code> token.
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writeObjectEnd() throws IOException, ModelException;

    /**
     * Writes DMR <code>property start</code> token.
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writePropertyStart() throws IOException, ModelException;

    /**
     * Writes DMR <code>property end</code> token.
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writePropertyEnd() throws IOException, ModelException;

    /**
     * Writes DMR <code>list start</code> token.
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writeListStart() throws IOException, ModelException;

    /**
     * Writes DMR <code>list end</code> token.
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writeListEnd() throws IOException, ModelException;

    /**
     * Writes DMR <code>undefined</code> token.
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writeUndefined() throws IOException, ModelException;

    /**
     * Writes DMR <code>string</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writeString( String data ) throws IOException, ModelException;

    /**
     * Writes DMR <code>expression</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writeExpression( String data ) throws IOException, ModelException;

    /**
     * Writes DMR <code>bytes</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writeBytes( byte[] data ) throws IOException, ModelException;

    /**
     * Writes DMR <code>true</code> or <code>false</code> token.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writeBoolean( boolean data ) throws IOException, ModelException;

    /**
     * Writes DMR <code>number</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writeInt( int data ) throws IOException, ModelException;

    /**
     * Writes DMR <code>number</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writeLong( long data ) throws IOException, ModelException;

    /**
     * Writes DMR <code>number</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writeBigInteger( BigInteger data ) throws IOException, ModelException;

    /**
     * Writes DMR <code>number</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writeBigDecimal( BigDecimal data ) throws IOException, ModelException;

    /**
     * Writes DMR <code>number</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writeDouble( double data ) throws IOException, ModelException;

    /**
     * Writes DMR <code>type</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    ModelWriter writeType( ModelType data ) throws IOException, ModelException;

    /**
     * Writes all cached data.
     * @throws IOException if some I/O error occurs
     */
    @Override
    void flush() throws IOException;

    /**
     * Free resources associated with this writer. Never closes underlying input stream or writer.
     * @throws IOException if some I/O error occurs
     * @throws ModelException if invalid DMR write attempt is detected
     */
    @Override
    void close() throws IOException, ModelException;

}
