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

/**
 * DMR parsing events.
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public enum ModelEvent {
    /**
     * Parsing cursor points to DMR <CODE>boolean</CODE>.
     */
    BOOLEAN,
    /**
     * Parsing cursor points to DMR <CODE>bytes</CODE>.
     */
    BYTES,
    /**
     * Parsing cursor points to DMR <CODE>expression</CODE>.
     */
    EXPRESSION,
    /**
     * Parsing cursor points to DMR <CODE>list end</CODE> token.
     */
    LIST_END,
    /**
     * Parsing cursor points to DMR <CODE>list start</CODE> token.
     */
    LIST_START,
    /**
     * Parsing cursor points to DMR <CODE>int</CODE>.
     */
    INT,
    /**
     * Parsing cursor points to DMR <CODE>long</CODE>.
     */
    LONG,
    /**
     * Parsing cursor points to DMR <CODE>double</CODE>.
     */
    DOUBLE,
    /**
     * Parsing cursor points to DMR <CODE>big integer</CODE>.
     */
    BIG_INTEGER,
    /**
     * Parsing cursor points to DMR <CODE>big decimal</CODE>.
     */
    BIG_DECIMAL,
    /**
     * Parsing cursor points to DMR <CODE>object start</CODE> token.
     */
    OBJECT_START,
    /**
     * Parsing cursor points to DMR <CODE>object end</CODE> token.
     */
    OBJECT_END,
    /**
     * Parsing cursor points to DMR <CODE>property end</CODE> token.
     */
    PROPERTY_END,
    /**
     * Parsing cursor points to DMR <CODE>property start</CODE> token.
     */
    PROPERTY_START,
    /**
     * Parsing cursor points to DMR <CODE>string</CODE>.
     */
    STRING,
    /**
     * Parsing cursor points to DMR <CODE>type</CODE>.
     */
    TYPE,
    /**
     * Parsing cursor points to DMR <CODE>undefined</CODE> token.
     */
    UNDEFINED,
}
