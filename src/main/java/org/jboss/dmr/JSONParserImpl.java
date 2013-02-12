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

import org.yuanheng.cookcc.CookCCOption;
import org.yuanheng.cookcc.CookCCToken;
import org.yuanheng.cookcc.Lex;
import org.yuanheng.cookcc.Lexs;
import org.yuanheng.cookcc.Rule;
import org.yuanheng.cookcc.Rules;
import org.yuanheng.cookcc.TokenGroup;
import org.yuanheng.cookcc.TokenType;

@CookCCOption()
public class JSONParserImpl extends JSONParser {

    // tokenizer
    @CookCCToken
    enum Token {
        @TokenGroup
        OPEN_BRACE, CLOSE_BRACE, OPEN_BRACKET, CLOSE_BRACKET,

        // symbols and keywords
        @TokenGroup(type = TokenType.LEFT)
        COLON,

        @TokenGroup(type = TokenType.LEFT)
        COMMA,

        @TokenGroup(type = TokenType.RIGHT)
        BIG, INTEGER, DECIMAL,

        @TokenGroup
        UNDEFINED,

        // value types
        TRUE, FALSE,

        ZERO_VAL, OCTAL_INT_VAL, HEX_INT_VAL, SIGNED_HEX_INT_VAL, DEC_INT_VAL, NAN_VAL, INF_VAL, DEC_VAL, STR_VAL
    }

    @Lexs(patterns = { @Lex(pattern = "\\{", token = "OPEN_BRACE"), @Lex(pattern = "\\}", token = "CLOSE_BRACE"),
            @Lex(pattern = "\\[", token = "OPEN_BRACKET"), @Lex(pattern = "\\]", token = "CLOSE_BRACKET"),
            @Lex(pattern = ",", token = "COMMA"), @Lex(pattern = ":", token = "COLON"), @Lex(pattern = "true", token = "TRUE"),
            @Lex(pattern = "false", token = "FALSE"), @Lex(pattern = "big", token = "BIG"),
            @Lex(pattern = "decimal", token = "DECIMAL"), @Lex(pattern = "integer", token = "INTEGER"),
            @Lex(pattern = "null", token = "UNDEFINED") })
    protected Void parseToken() {
        return null;
    }

    @Lexs(patterns = {
            @Lex(pattern = "[+-]?0+", token = "ZERO_VAL"),
            @Lex(pattern = "[+-]?0[0-9]+", token = "OCTAL_INT_VAL"),
            @Lex(pattern = "0x[0-9a-fA-F]+", token = "HEX_INT_VAL"),
            @Lex(pattern = "[+-]0x[0-9a-fA-F]+", token = "SIGNED_HEX_INT_VAL"),
            @Lex(pattern = "[+-]?[1-9][0-9]*", token = "DEC_INT_VAL"),
            @Lex(pattern = "[+-]?NaN", token = "NAN_VAL"),
            @Lex(pattern = "[+-]?Infinity", token = "INF_VAL"),
            @Lex(pattern = "[+-]?([0-9]+\\.[0-9]+([eE][+-]?[0-9]+)?)", token = "DEC_VAL") })
    protected String parsePlainValue() {
        return yyText();
    }

    @Lex(pattern = "\\\"([^\"\\\\]+|\\\\.)*\\\"", token = "STR_VAL")
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
                        case 'u':
                            b.append((char) Integer.parseInt(yyText.substring(i + 1, i + 5), 16));
                            i += 4;
                            break;
                        default:
                            b.appendCodePoint(ch);
                            break;
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

    @Lex(pattern = "[ \\t\\r\\n]+")
    protected void ignored() {
    }

    @Lex(pattern = ".")
    protected void invalid() throws IOException {
        throw new IOException("Invalid character: " + yyText());
    }

    @Lex(pattern = "<<EOF>>", token = "$")
    protected void parseEOF() {
    }

    private ModelNode result;

    @Rule(lhs = "complete", rhs = "node", args = "1")
    protected int parse(final ModelNode node) {
        result = node;
        return 0;
    }

    @Rules(rules = { @Rule(lhs = "node", rhs = "ZERO_VAL") })
    protected ModelNode parseZero() {
        return new ModelNode().set(0);
    }

    @Rules(rules = { @Rule(lhs = "node", rhs = "DEC_VAL", args = "1") })
    protected ModelNode parseBigDecimal(final String arg) {
        return new ModelNode().set(new BigDecimal(arg));
    }

    @Rules(rules = { @Rule(lhs = "node", rhs = "NAN_VAL") })
    protected ModelNode parseNaN() {
        return new ModelNode().set(Double.NaN);
    }

    @Rules(rules = { @Rule(lhs = "node", rhs = "INF_VAL", args = "1") })
    protected ModelNode parseInf(String arg) {
        return new ModelNode().set(arg.charAt(0) == '-' ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
    }

    @Rules(rules = { @Rule(lhs = "node", rhs = "DEC_INT_VAL", args = "1") })
    protected ModelNode parseDecInt(final String arg) {
        return parseInteger0(arg, 10);
    }

    @Rules(rules = { @Rule(lhs = "node", rhs = "OCTAL_INT_VAL", args = "1") })
    protected ModelNode parseOctal(final String arg) {
        return parseInteger0(arg, 8);
    }

    @Rules(rules = { @Rule(lhs = "node", rhs = "HEX_INT_VAL", args = "1") })
    protected ModelNode parseHex(final String arg) {
        return parseInteger0(arg.substring(2), 16);
    }

    @Rules(rules = { @Rule(lhs = "node", rhs = "SIGNED_HEX_INT_VAL", args = "1") })
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

    @Rule(lhs = "node", rhs = "TRUE")
    protected ModelNode parseTrue() {
        return new ModelNode().set(true);
    }

    @Rule(lhs = "node", rhs = "FALSE")
    protected ModelNode parseFalse() {
        return new ModelNode().set(false);
    }

    @Rules(rules = { @Rule(lhs = "node", rhs = "OPEN_BRACKET CLOSE_BRACKET") })
    protected ModelNode parseEmptyList() {
        return new ModelNode().setEmptyList();
    }

    @Rules(rules = { @Rule(lhs = "list", rhs = "OPEN_BRACKET node", args = "2") })
    protected ModelNode parseStartList(final ModelNode child) {
        return new ModelNode().addNoCopy(child);
    }

    @Rules(rules = { @Rule(lhs = "list", rhs = "list COMMA node", args = "1 3") })
    protected ModelNode parseListItem(final ModelNode list, final ModelNode child) {
        return list.addNoCopy(child);
    }

    @Rules(rules = { @Rule(lhs = "node", rhs = "list CLOSE_BRACKET", args = "1"),
            @Rule(lhs = "node", rhs = "list COMMA CLOSE_BRACKET", args = "1") })
    protected ModelNode finishList(final ModelNode list) {
        return list;
    }

    @Rules(rules = { @Rule(lhs = "node", rhs = "OPEN_BRACE CLOSE_BRACE") })
    protected ModelNode parseEmptyObject() {
        return new ModelNode().setEmptyObject();
    }

    @Rules(rules = { @Rule(lhs = "object", rhs = "OPEN_BRACE STR_VAL COLON node", args = "2 4") })
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

    @Rules(rules = { @Rule(lhs = "object", rhs = "object COMMA STR_VAL COLON node", args = "1 3 5") })
    protected ModelNode parseObjectItem(final ModelNode object, final String key, final ModelNode child) {
        object.get(key).setNoCopy(child);
        return object;
    }

    @Rules(rules = { @Rule(lhs = "node", rhs = "object CLOSE_BRACE", args = "1"),
            @Rule(lhs = "node", rhs = "object COMMA CLOSE_BRACE", args = "1") })
    protected ModelNode finishObject(final ModelNode object) {
        return object;
    }

    @Rules(rules = { @Rule(lhs = "node", rhs = "STR_VAL", args = "1") })
    protected ModelNode parseString(final String arg) {
        return new ModelNode().set(arg);
    }

    @Rule(lhs = "node", rhs = "UNDEFINED")
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