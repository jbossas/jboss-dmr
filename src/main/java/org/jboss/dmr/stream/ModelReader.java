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

import java.io.IOException;

/**
 * DMR reader. Instances of this interface are not thread safe.
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 * @see ModelStreamFactory
 */
public interface ModelReader extends AutoCloseable {

    /**
     * Detects if there is next DMR parsing event available.
     * Users should call this method before calling {@link #next()} method.
     * @return <code>true</code> if there are more DMR parsing events, <code>false</code> otherwise
     */
    boolean hasNext();

    /**
     * Returns next DMR parsing event.
     * Users should call {@link #hasNext()} before calling this method.
     * @return ModelEvent next event
     * @throws IOException if some I/O error occurs
     * @throws ModelException if wrong DMR is detected
     */
    ModelEvent next() throws IOException, ModelException;

    /**
     * Returns <code>true</code> if current DMR parsing event is DMR <code>object start</code> token, <code>false</code> otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to DMR object start token, false otherwise
     */
    boolean isObjectStart();

    /**
     * Returns <code>true</code> if current DMR parsing event is DMR <code>object end</code> token, <code>false</code> otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to DMR object end token, false otherwise
     */
    boolean isObjectEnd();

    /**
     * Returns <code>true</code> if current DMR parsing event is DMR <code>property start</code> token, <code>false</code> otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to DMR property start token, false otherwise
     */
    boolean isPropertyStart();

    /**
     * Returns <code>true</code> if current DMR parsing event is DMR <code>property end</code> token, <code>false</code> otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to DMR property end token, false otherwise
     */
    boolean isPropertyEnd();

    /**
     * Returns <code>true</code> if current DMR parsing event is DMR <code>list start</code> token, <code>false</code> otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to DMR list start token, false otherwise
     */
    boolean isListStart();

    /**
     * Returns <code>true</code> if current DMR parsing event is DMR <code>list end</code> token, <code>false</code> otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to DMR list end token, false otherwise
     */
    boolean isListEnd();

    /**
     * Returns <code>true</code> if current DMR parsing event is DMR <code>undefined</code> token, <code>false</code> otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to DMR undefined token, false otherwise
     */
    boolean isUndefined();

    /**
     * Returns <code>true</code> if current DMR parsing event is DMR <code>type</code>, <code>false</code> otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to DMR type, false otherwise
     */
    boolean isType();

    /**
     * Converts available context data to <code>model type</code>.
     * Users have to call {@link #next()} and should call {@link #isType()} before calling this method.
     * @return type the parsing cursor is pointing to
     * @exception IllegalStateException if cursor isn't pointing to DMR type
     */
    ModelType getType();

    /**
     * Returns <code>true</code> if current DMR parsing event is DMR <code>string</code>, <code>false</code> otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to DMR string, false otherwise
     */
    boolean isString();

    /**
     * Converts available context data to <code>String</code>.
     * Users have to call {@link #next()} and should call {@link #isString()} before calling this method.
     * @return string the parsing cursor is pointing to
     * @exception IllegalStateException if cursor isn't pointing to DMR String
     */
    String getString();

    /**
     * Returns <code>true</code> if current DMR parsing event is DMR <code>expression</code>, <code>false</code> otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to DMR expression, false otherwise
     */
    boolean isExpression();

    /**
     * Converts available context data to <code>expression</code>.
     * Users have to call {@link #next()} and should call {@link #isExpression()} before calling this method.
     * @return expression the parsing cursor is pointing to
     * @exception IllegalStateException if cursor isn't pointing to DMR expression
     */
    String getExpression();

    /**
     * Returns <code>true</code> if current DMR parsing event is DMR <code>bytes</code>, <code>false</code> otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to DMR bytes, false otherwise
     */
    boolean isBytes();

    /**
     * Converts available context data to <code>bytes</code>.
     * Users have to call {@link #next()} and should call {@link #isBytes()} before calling this method.
     * @return bytes the parsing cursor is pointing to
     * @exception IllegalStateException if cursor isn't pointing to DMR Bytes
     */
    byte[] getBytes();

    /**
     * Returns <code>true</code> if current DMR parsing event is DMR <code>boolean</code> token, <code>false</code> otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor points to DMR boolean tokens, false otherwise
     */
    boolean isBoolean();

    /**
     * Converts available context data to <code>boolean</code>.
     * Users have to call {@link #next()} and should call {@link #isBoolean()} before calling this method.
     * @return boolean value the parsing cursor is pointing to
     * @exception IllegalStateException if cursor isn't pointing to DMR boolean token
     */
    boolean getBoolean();

    /**
     * Returns <code>true</code> if current DMR parsing event is DMR <code>number</code>, <code>false</code> otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor points to DMR number, false otherwise
     */
    boolean isNumber();

    /**
     * Converts available context data to <code>Number</code> instance.
     * Users have to call {@link #next()} and should call {@link #isNumber()} before calling this method.
     * @return number value the parsing cursor is pointing to
     * @exception NumberFormatException if DMR number is not convertible to <code>number</code>
     */
    Number getNumber();

    /**
     * Free resources associated with this reader. Never closes underlying output stream or reader.
     * @exception ModelException if attempting to close this reader before reaching EOF.
     */
    @Override
    void close() throws ModelException;

}
