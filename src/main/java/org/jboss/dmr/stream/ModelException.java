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

/**
 * DMR encoding exception.
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class ModelException extends Exception {

    /**
     * Serialization version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     */
    public ModelException() {
    }

    /**
     * Constructor.
     * @param msg message
     */
    public ModelException( final String msg ) {
        super( msg );
    }

    /**
     * Constructor.
     * @param msg message
     * @param t reason
     */
    public ModelException( final String msg, final Throwable t ) {
        super( msg, t );
    }

    /**
     * Constructor.
     * @param t reason
     */
    public ModelException( final Throwable t ) {
        super( t );
    }

}
