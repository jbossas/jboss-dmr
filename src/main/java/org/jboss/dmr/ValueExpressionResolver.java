/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
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

package org.jboss.dmr;

import java.io.File;

/**
 * A resolver for value expressions.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public class ValueExpressionResolver {

    /**
     * The default value expression resolver.
     */
    public static final ValueExpressionResolver DEFAULT_RESOLVER = new ValueExpressionResolver();

    /**
     * Construct a new instance.
     */
    public ValueExpressionResolver() {
    }

    /**
     * Perform expression resolution.
     *
     * @param expression the expression to resolve
     * @return the resolved string
     */
    public String resolve(ValueExpression expression) {
        final String value = expression.getExpressionString();
        final StringBuilder builder = new StringBuilder();
        final int len = value.length();
        int state = INITIAL;
        int start = -1;
        int nest = 0;
        int nameStart = -1;
        for (int i = 0; i < len; i = value.offsetByCodePoints(i, 1)) {
            final int ch = value.codePointAt(i);
            switch (state) {
                case INITIAL: {
                    switch (ch) {
                        case '$': {
                            state = GOT_DOLLAR;
                            continue;
                        }
                        default: {
                            builder.appendCodePoint(ch);
                            continue;
                        }
                    }
                    // not reachable
                }
                case GOT_DOLLAR: {
                    switch (ch) {
                        case '$': {
                            builder.appendCodePoint(ch);
                            state = INITIAL;
                            continue;
                        }
                        case '{': {
                            start = i + 1;
                            nameStart = start;
                            state = GOT_OPEN_BRACE;
                            continue;
                        }
                        default: {
                            // invalid; emit and resume
                            builder.append('$').appendCodePoint(ch);
                            state = INITIAL;
                            continue;
                        }
                    }
                    // not reachable
                }
                case GOT_OPEN_BRACE: {
                    switch (ch) {
                        case '{': {
                            nest++;
                            continue;
                        }
                        case ':':
                            if (nameStart == i) {
                                // not a default delimiter; same as default case
                                continue;
                            }
                            // else fall into the logic for 'end of key to resolve cases' "," and "}"
                        case '}':
                        case ',': {
                            if (nest > 0) {
                                if (ch == '}') nest--;
                                continue;
                            }
                            final String val = resolvePart(value.substring(nameStart, i).trim());
                            if (val != null && !val.equals(value)) {
                                builder.append(val);
                                state = ch == '}' ? INITIAL : RESOLVED;
                                continue;
                            } else if (ch == ',') {
                                nameStart = i + 1;
                                continue;
                            } else if (ch == ':') {
                                start = i + 1;
                                state = DEFAULT;
                                continue;
                            } else {
                                throw new IllegalStateException("Failed to resolve expression: "+ value.substring(start - 2, i + 1));
                            }
                        }
                        default: {
                            continue;
                        }
                    }
                    // not reachable
                }
                case RESOLVED: {
                    if (ch == '{') {
                        nest ++;
                    } else if (ch == '}') {
                        if (nest > 0) {
                            nest--;
                        } else {
                            state = INITIAL;
                        }
                    }
                    continue;
                }
                case DEFAULT: {
                    if (ch == '{') {
                        nest ++;
                    } else if (ch == '}') {
                        if (nest > 0) {
                            nest --;
                        } else {
                            state = INITIAL;
                            builder.append(value.substring(start, i));
                        }
                    }
                    continue;
                }
                default:
                    throw new IllegalStateException("Unexpected char seen: "+ch);
            }
        }
        switch (state) {
            case GOT_DOLLAR: {
                builder.append('$');
                break;
            }
            case DEFAULT: {
                builder.append(value.substring(start - 2));
                break;
            }
            case GOT_OPEN_BRACE: {
                // We had a reference that was not resolved, throw ISE
                throw new IllegalStateException("Incomplete expression: "+builder.toString());
           }
        }
        return builder.toString();
    }

    /**
     * Resolve a single name in the expression.  Return {@code null} if no resolution is possible.  The default
     * implementation (which may be delegated to) checks system properties, environment variables, and a small set of
     * special strings.
     *
     * @param name the name to resolve
     * @return the resolved value, or {@code null} for none
     */
    protected String resolvePart(String name) {
        if ("/".equals(name)) {
            return File.separator;
        } else if (":".equals(name)) {
            return File.pathSeparator;
        }
        // First check for system property, then env variables
        String val = System.getProperty(name);

        if (val == null) {
            // See if an env var is defined
            String envVar = replaceNonAlphanumericByUnderscoresAndMakeUpperCase(name);
            val = System.getenv(envVar);
        }

        if (val == null && name.startsWith("env."))
            val = System.getenv(name.substring(4));

        return val;
    }


    private String replaceNonAlphanumericByUnderscoresAndMakeUpperCase(final String name) {
        int length = name.length();
        StringBuilder sb = new StringBuilder();
        int c;
        for (int i = 0; i < length; i += Character.charCount(c)) {
            c = Character.toUpperCase(name.codePointAt(i));
            if ('A' <= c && c <= 'Z' ||
                    '0' <= c && c <= '9') {
                sb.appendCodePoint(c);
            } else {
                sb.append('_');
            }
        }
        return sb.toString();
    }

    private static final int INITIAL = 0;
    private static final int GOT_DOLLAR = 1;
    private static final int GOT_OPEN_BRACE = 2;
    private static final int RESOLVED = 3;
    private static final int DEFAULT = 4;
}
