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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

// TODO: [DMR-9] Delete this class when DMR release gets it to the JBoss EAP.
/**
 * This class have been deprecated in JBoss DMR 1.4.0 and will be removed in future releases.
 */
@Deprecated
class ModelNodeParser extends Parser {

    private static final byte[] NO_BYTES = new byte[0];

    // tokenizer

    enum Token {
        OPEN_BRACE,
        CLOSE_BRACE,
        OPEN_BRACKET,
        CLOSE_BRACKET,
        OPEN_PAREN,
        CLOSE_PAREN,

        // symbols and keywords
        ARROW,

        COMMA,

        BIG,
        BYTES,
        INTEGER,
        DECIMAL,
        EXPRESSION,
        UNDEFINED,

        // value types
        TRUE,
        FALSE,

        INT_VAL,
        INT_HEX_VAL,
        LONG_VAL,
        LONG_HEX_VAL,
        DOUBLE_SPECIAL_VAL,
        DEC_VAL,
        STR_VAL,
        TYPE_VAL,
    }

    protected Void parseToken() {
        return null;
    }

    protected String parsePlainValue() {
        return yyText();
    }

    protected String parseStringValue() {
        final String yyText = yyText();
        final int length = yyText.length();
        final StringBuilder b = new StringBuilder(length);
        for (int i = 1; i < length - 1; i = yyText.offsetByCodePoints(i, 1)) {
            int ch = yyText.codePointAt(i);
            switch (ch) {
                case '\\': {
                    i = yyText.offsetByCodePoints(i, 1);
                    ch = yyText.codePointAt(i);
                    switch (ch) {
                        case 'n': b.append('\n'); break;
                        case 'r': b.append('\r'); break;
                        case 'b': b.append('\b'); break;
                        case 'f': b.append('\f'); break;
                        case 'u': b.append((char) Integer.parseInt(yyText.substring(i + 1, i + 5), 16)); i+=4; break;
                        default: b.appendCodePoint(ch); break;
                    }
                    break;
                }
                default: {
                    b.appendCodePoint(ch);
                    break;
                }
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

    protected ModelNode parseBigDecimal(final String arg) {
        return new ModelNode().set(new BigDecimal(arg));
    }

    protected ModelNode parseBigInteger(final String arg) {
        return new ModelNode().set(new BigInteger(arg));
    }

    protected ModelNode parseTrue() {
        return new ModelNode().set(Boolean.TRUE);
    }

    protected ModelNode parseFalse() {
        return new ModelNode().set(Boolean.FALSE);
    }

    protected ByteArrayOutputStream startBytesInt(final String val) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(Integer.parseInt(val));
        return baos;
    }

    protected ByteArrayOutputStream startBytesHex(final String val) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(Integer.parseInt(val.substring(2), 16));
        return baos;
    }

    protected ByteArrayOutputStream nextByteInt(final ByteArrayOutputStream baos, final String val) {
        baos.write(Integer.parseInt(val));
        return baos;
    }

    protected ByteArrayOutputStream nextByteHex(final ByteArrayOutputStream baos, final String val) {
        baos.write(Integer.parseInt(val.substring(2), 16));
        return baos;
    }

    protected ModelNode finishBytes(final ByteArrayOutputStream baos) {
        return new ModelNode().set(baos.toByteArray());
    }

    protected ModelNode emptyBytes() {
        return new ModelNode().set(NO_BYTES);
    }

    protected ModelNode parseDouble(final String arg) {
        return new ModelNode().set(Double.parseDouble(arg));
    }

    protected ModelNode parseExpression(final String arg) {
        return new ModelNode().setExpression(arg);
    }

    protected ModelNode parseInt(final String arg) {
        return new ModelNode().set(Integer.parseInt(arg));
    }

    protected ModelNode parseIntHex(final String arg) {
        return new ModelNode().set(Integer.parseInt(arg.substring(2), 16));
    }

    protected ModelNode parseLong(final String arg) {
        // Can't pass the trailing 'L' to parseLong
        return new ModelNode().set(Long.parseLong(arg.substring(0, arg.length() - 1)));
    }

    protected ModelNode parseLongHex(final String arg) {
        return new ModelNode().set(Long.parseLong(arg.substring(2), 16));
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
        final ModelNode node = new ModelNode();
        node.get(key).setNoCopy(child);
        return node;
    }

    protected ModelNode parseObjectItem(final ModelNode object, final String key, final ModelNode child) {
        object.get(key).setNoCopy(child);
        return object;
    }

    protected ModelNode finishObject(final ModelNode object) {
        return object;
    }

    protected ModelNode parseProperty(final String key, final ModelNode value) {
        return new ModelNode().setNoCopy(key, value);
    }

    protected ModelNode parseString(final String arg) {
        return new ModelNode().set(arg);
    }

    protected ModelNode parseType(final String arg) {
        return new ModelNode().set(ModelType.valueOf(arg));
    }

    protected ModelNode parseUndefined() {
        return new ModelNode();
    }

    public ModelNode getResult() {
        return result;
    }
}
