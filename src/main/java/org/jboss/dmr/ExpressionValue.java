/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
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

import org.jboss.dmr.stream.ModelException;
import org.jboss.dmr.stream.ModelWriter;

import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
final class ExpressionValue extends ModelValue {

    /**
     * JSON Key used to identify ExpressionValue.
     */
    public static final String TYPE_KEY = "EXPRESSION_VALUE";

    private final ValueExpression valueExpression;

    ExpressionValue(final String expressionString) {
        this(new ValueExpression(expressionString));
    }

    ExpressionValue(final ValueExpression valueExpression) {
        super(ModelType.EXPRESSION);
        this.valueExpression = valueExpression;
    }

    @Override
    void writeExternal(final DataOutput out) throws IOException {
        out.write(ModelType.EXPRESSION.typeChar);
        out.writeUTF(valueExpression.getExpressionString());
    }

    @Override
    String asString() {
        return valueExpression.getExpressionString();
    }

    @Override
    ValueExpression asExpression() {
        return valueExpression;
    }

    @Override
    void format(final PrintWriter writer, final int indent, final boolean multiLine) {
        writer.append("expression ");
        writer.append(quote(valueExpression.getExpressionString()));
    }

    @Override
    void formatAsJSON(final PrintWriter writer, final int indent, final boolean multiLine) {
        writer.append('{');
        if (multiLine) {
            indent(writer.append('\n'), indent + 1);
        } else {
            writer.append(' ');
        }
        writer.append(jsonEscape(TYPE_KEY));
        writer.append(" : ");
        writer.append(jsonEscape(asString()));
        if (multiLine) {
            indent(writer.append('\n'), indent);
        } else {
            writer.append(' ');
        }
        writer.append('}');
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof ExpressionValue && equals((ExpressionValue) other);
    }

    public boolean equals(final ExpressionValue other) {
        return this == other || other != null && valueExpression.equals(other.valueExpression);
    }

    @Override
    public int hashCode() {
        return valueExpression.hashCode();
    }

    @Override
    ModelValue resolve() {
        return new StringModelValue(valueExpression.resolveString());
    }

    @Override
    void write(final ModelWriter writer) throws IOException, ModelException {
        writer.writeExpression(valueExpression.getExpressionString());
    }

}
