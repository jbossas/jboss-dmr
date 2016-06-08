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
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
final class ModelConstants {

    static final char LIST_END = ']';

    static final char LIST_START = '[';

    static final char BACKSLASH = '\\';

    static final char BACKSPACE = '\b';

    static final char CR = '\r';

    static final char COLON = ':';

    static final char COMMA = ',';

    static final char FORMFEED = '\f';

    static final char MINUS = '-';

    static final char NL = '\n';

    static final char OBJECT_END = '}';

    static final char OBJECT_START = '{';

    static final char PLUS = '+';

    static final char BYTES_END = '}';

    static final char BYTES_START = '{';

    static final char PROPERTY_END = ')';

    static final char PROPERTY_START = '(';

    static final char QUOTE = '\"';

    static final char SPACE = ' ';

    static final char EQUAL = '=';

    static final char GREATER_THAN = '>';

    static final char TAB = '\t';

    static final String ARROW = "=>";

    static final String BIG = "big";

    static final String BOOLEAN = "boolean";

    static final String BYTES = "bytes";

    static final String DECIMAL = "decimal";

    static final String EXPRESSION = "expression";

    static final String FALSE = "false";

    static final String INFINITY = "Infinity";

    static final String INTEGER = "integer";

    static final String NAN = "NaN";

    static final String NULL = "null";

    static final String NUMBER = "number";

    static final String STRING = "string";

    static final String TRUE = "true";

    static final String TYPE_MODEL_VALUE = "TYPE_MODEL_VALUE";

    static final String BYTES_VALUE = "BYTES_VALUE";

    static final String EXPRESSION_VALUE = "EXPRESSION_VALUE";

    static final String UNDEFINED = "undefined";

    private ModelConstants() {
        // forbidden instantiation
    }

}
