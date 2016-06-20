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

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

// TODO: [DMR-9] Delete this class when DMR release gets it to the JBoss EAP.
/**
 * This class have been deprecated in JBoss DMR 1.4.0 and will be removed in future releases.
 */
@Deprecated
public class JSONParserImpl extends JSONParser {

    // tokenizer
    enum Token {
        OPEN_BRACE, CLOSE_BRACE, OPEN_BRACKET, CLOSE_BRACKET,

        // symbols and keywords
        COLON,

        COMMA,

        BIG, INTEGER, DECIMAL,

        UNDEFINED,

        // value types
        TRUE, FALSE,

        ZERO_VAL, OCTAL_INT_VAL, HEX_INT_VAL, SIGNED_HEX_INT_VAL, DEC_INT_VAL, NAN_VAL, INF_VAL, DEC_VAL, STR_VAL
    }

    protected Void parseToken() {
        return null;
    }

    protected String parsePlainValue() {
        return yyText();
    }

    protected String parseStringValue() throws IOException {
        final String yyText = yyText();
        final int length = yyText.length();
        final StringBuilder b = new StringBuilder(length);
        for (int i = 1; i < length - 1; i = yyText.offsetByCodePoints(i, 1)) {
            int ch = yyText.codePointAt(i);
            switch (ch) {
                case '\\':
                    i = yyText.offsetByCodePoints(i, 1);
                    ch = yyText.codePointAt(i);
                    switch (ch) {
                        case 'n':
                            b.append('\n');
                            break;
                        case 'r':
                            b.append('\r');
                            break;
                        case 'b':
                            b.append('\b');
                            break;
                        case 'f':
                            b.append('\f');
                            break;
                        case 't':
                            b.append('\t');
                            break;
                        case 'u':
                            b.append((char) Integer.parseInt(yyText.substring(i + 1, i + 5), 16));
                            i += 4;
                            break;
                        default:
                            b.appendCodePoint(ch);
                    }
                    break;
                default:
                    b.appendCodePoint(ch);
            }
        }
        return b.toString();
    }

    protected void ignored() {
    }

    protected void invalid() throws IOException {
        throw new IOException("Invalid character: " + yyText());
    }

    protected void parseEOF() {
    }

    private ModelNode result;

    protected int parse(final ModelNode node) {
        result = node;
        return 0;
    }

    protected ModelNode parseZero() {
        return new ModelNode().set(0);
    }

    protected ModelNode parseBigDecimal(final String arg) {
        return new ModelNode().set(new BigDecimal(arg));
    }

    protected ModelNode parseNaN() {
        return new ModelNode().set(Double.NaN);
    }

    protected ModelNode parseInf(String arg) {
        return new ModelNode().set(arg.charAt(0) == '-' ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
    }

    protected ModelNode parseDecInt(final String arg) {
        return parseInteger0(arg, 10);
    }

    protected ModelNode parseOctal(final String arg) {
        return parseInteger0(arg, 8);
    }

    protected ModelNode parseHex(final String arg) {
        return parseInteger0(arg.substring(2), 16);
    }

    protected ModelNode parseHexSigned(final String arg) {
        return parseInteger0("" + arg.charAt(0) + arg.substring(3), 16);
    }

    private static ModelNode parseInteger0(final String arg, final int radix) {
        final BigInteger val = new BigInteger(arg, radix);
        if (val.bitLength() <= 31) {
            return new ModelNode().set(val.intValue());
        } else if (val.bitLength() <= 63) {
            return new ModelNode().set(val.longValue());
        } else {
            return new ModelNode().set(val);
        }
    }

    protected ModelNode parseTrue() {
        return new ModelNode().set(true);
    }

    protected ModelNode parseFalse() {
        return new ModelNode().set(false);
    }

    protected ModelNode parseEmptyList() {
        return new ModelNode().setEmptyList();
    }

    protected ModelNode parseStartList(final ModelNode child) {
        return new ModelNode().addNoCopy(child);
    }

    protected ModelNode parseListItem(final ModelNode list, final ModelNode child) {
        return list.addNoCopy(child);
    }

    protected ModelNode finishList(final ModelNode list) {
        return list;
    }

    protected ModelNode parseEmptyObject() {
        return new ModelNode().setEmptyObject();
    }

    protected ModelNode parseStartObject(final String key, final ModelNode child) {
        if (TypeModelValue.TYPE_KEY.equals(key)) {
            return parseType(child.toString().replaceAll("\"", ""));
        } else if (BytesModelValue.TYPE_KEY.equals(key)) {
            return parseBase64(child.toString().replaceAll("\"", ""));
        } else if (ExpressionValue.TYPE_KEY.equals(key)) {
            return parseExpression(child.toString().replaceAll("\"", ""));
        } else if (PropertyModelValue.TYPE_KEY.equals(key)) {
            return parseProperty(child);
        } else {
            final ModelNode node = new ModelNode();
            node.get(key).setNoCopy(child);
            return node;
        }
    }

    protected ModelNode parseObjectItem(final ModelNode object, final String key, final ModelNode child) {
        object.get(key).setNoCopy(child);
        return object;
    }

    protected ModelNode finishObject(final ModelNode object) {
        return object;
    }

    protected ModelNode parseString(final String arg) {
        return new ModelNode().set(arg);
    }

    protected ModelNode parseUndefined() {
        return new ModelNode();
    }

    private ModelNode parseType(final String arg) {
        return new ModelNode().set(ModelType.valueOf(arg));
    }

    private ModelNode parseBase64(final String arg) {
        final ModelNode node = new ModelNode();
        node.set(Base64.decode(arg));
        return node;
    }

    private ModelNode parseExpression(final String arg) {
        return new ModelNode().setExpression(arg);
    }

    private ModelNode parseProperty(final ModelNode value) {
        return new ModelNode().set(value.asProperty());
    }

    public ModelNode getResult() {
        return result;
    }
}