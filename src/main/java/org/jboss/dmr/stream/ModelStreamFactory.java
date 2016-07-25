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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * DMR streams factory. Defines an abstract implementation of a factory for getting DMR readers and
 * writers. All readers and writers returned by this factory are not thread safe.
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 * @see ModelReader
 * @see ModelWriter
 */
public final class ModelStreamFactory {

    private static final Charset DEFAULT_CHARSET = Charset.forName( "UTF-8" );
    private static final ModelStreamFactory DMR_FACTORY = new ModelStreamFactory( false );
    private static final ModelStreamFactory JSON_FACTORY = new ModelStreamFactory( true );
    private final boolean jsonCompatible;

    /**
     * Forbidden instantiation.
     */
    private ModelStreamFactory( final boolean jsonCompatible ) {
        this.jsonCompatible = jsonCompatible;
    }

    /**
     * Returns DMR stream factory instance.
     * @param jsonCompatible whether stream factories should read/write JSON
     * @return DMR stream factory instance
     */
    public static ModelStreamFactory getInstance( final boolean jsonCompatible ) {
        return jsonCompatible ? JSON_FACTORY : DMR_FACTORY;
    }

    /**
     * Creates new DMR reader.
     * @param reader input
     * @return DMR reader instance
     */
    public ModelReader newModelReader( final Reader reader ) {
        assertNotNullParameter( reader );
        return jsonCompatible ? new JsonReaderImpl( reader ) : new ModelReaderImpl( reader );
    }

    /**
     * Creates new DMR writer.
     * @param writer output
     * @return DMR writer instance
     */
    public ModelWriter newModelWriter( final Writer writer ) {
        assertNotNullParameter( writer );
        return jsonCompatible ? new JsonWriterImpl( writer ) : new ModelWriterImpl( writer );
    }

    /**
     * Creates new DMR reader with <code>UTF-8</code> character set.
     * @param stream input
     * @return DMR reader instance
     */
    public ModelReader newModelReader( final InputStream stream ) {
        return newModelReader( stream, DEFAULT_CHARSET );
    }

    /**
     * Creates new DMR writer with <code>UTF-8</code> character set.
     * @param stream output
     * @return DMR writer instance
     */
    public ModelWriter newModelWriter( final OutputStream stream ) {
        return newModelWriter( stream, DEFAULT_CHARSET );
    }

    /**
     * Creates new DMR reader with specified character set.
     * @param stream input
     * @param charset character set
     * @return DMR reader instance
     */
    public ModelReader newModelReader( final InputStream stream, final Charset charset ) {
        assertNotNullParameter( stream );
        assertNotNullParameter( charset );
        return newModelReader( new InputStreamReader( stream, charset ) );
    }

    /**
     * Creates new DMR writer with specified character set.
     * @param stream output
     * @param charset character set
     * @return DMR writer instance
     */
    public ModelWriter newModelWriter( final OutputStream stream, final Charset charset ) {
        assertNotNullParameter( stream );
        assertNotNullParameter( charset );
        return newModelWriter( new OutputStreamWriter( stream, charset ) );
    }

    private static void assertNotNullParameter( final Object o ) {
        if ( o == null ) {
            throw new NullPointerException( "Parameter cannot be null" );
        }
    }

}
