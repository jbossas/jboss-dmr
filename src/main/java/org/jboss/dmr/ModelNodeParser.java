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

import org.yuanheng.cookcc.CookCCOption;
import org.yuanheng.cookcc.CookCCToken;
import org.yuanheng.cookcc.Lex;
import org.yuanheng.cookcc.Lexs;
import org.yuanheng.cookcc.Rule;
import org.yuanheng.cookcc.Rules;
import org.yuanheng.cookcc.TokenGroup;
import org.yuanheng.cookcc.TokenType;

@CookCCOption()
class ModelNodeParser extends Parser {

    private static final byte[] NO_BYTES = new byte[0];

    // tokenizer

    @CookCCToken
    enum Token {
        @TokenGroup
        OPEN_BRACE,
        CLOSE_BRACE,
        OPEN_BRACKET,
        CLOSE_BRACKET,
        OPEN_PAREN,
        CLOSE_PAREN,

        // symbols and keywords
        @TokenGroup(type = TokenType.LEFT)
        ARROW,

        @TokenGroup(type = TokenType.LEFT)
        COMMA,

        @TokenGroup(type = TokenType.RIGHT)
        BIG,
        BYTES,
        INTEGER,
        DECIMAL,
        EXPRESSION,
        @TokenGroup
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

    @Lexs(patterns = {
        @Lex(pattern = "\\{", token = "OPEN_BRACE"),
        @Lex(pattern = "\\}", token = "CLOSE_BRACE"),

        @Lex(pattern = "\\[", token = "OPEN_BRACKET"),
        @Lex(pattern = "\\]", token = "CLOSE_BRACKET"),

        @Lex(pattern = "\\(", token = "OPEN_PAREN"),
        @Lex(pattern = "\\)", token = "CLOSE_PAREN"),

        @Lex(pattern = ",", token = "COMMA"),
        @Lex(pattern = "=>", token = "ARROW"),

        @Lex(pattern = "true", token = "TRUE"),
        @Lex(pattern = "false", token = "FALSE"),

        @Lex(pattern = "big", token = "BIG"),
        @Lex(pattern = "bytes", token = "BYTES"),
        @Lex(pattern = "decimal", token = "DECIMAL"),
        @Lex(pattern = "integer", token = "INTEGER"),
        @Lex(pattern = "expression", token = "EXPRESSION"),
        @Lex(pattern = "undefined", token = "UNDEFINED")
    })
    protected Void parseToken() {
        return null;
    }

    @Lexs(patterns = {
        @Lex(pattern = "[+-]?[0-9]+L", token = "LONG_VAL"),
        @Lex(pattern = "[+-]?0x[0-9a-fA-F]+L", token = "LONG_HEX_VAL"),
        @Lex(pattern = "[+-]?[0-9]+", token = "INT_VAL"),
        @Lex(pattern = "[+-]?0x[0-9a-fA-F]+", token = "INT_HEX_VAL"),
        @Lex(pattern = "[+-]?(NaN|Infinity)", token = "DOUBLE_SPECIAL_VAL"),
        @Lex(pattern = "[+-]?([0-9]+\\.[0-9]+([eE][+-]?[0-9]+)?)", token = "DEC_VAL"),
        @Lex(pattern = "BIG_DECIMAL|BIG_INTEGER|BOOLEAN|BYTES|DOUBLE|EXPRESSION|INT|LIST|LONG|OBJECT|PROPERTY|STRING|TYPE|UNDEFINED", token = "TYPE_VAL")
    })
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
                        case 'n': b.append('\n'); break;
                        case 'r': b.append('\r'); break;
                        case 'b': b.append('\b'); break;
                        case 'f': b.append('\f'); break;
                        case 'u': b.append((char) Integer.parseInt(yyText.substring(i + 1, i + 5))); i+=4; break;
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

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "BIG DECIMAL DEC_VAL", args = "3"),
        @Rule(lhs = "node", rhs = "BIG DECIMAL INT_VAL", args = "3")
    })
    protected ModelNode parseBigDecimal(final String arg) {
        return new ModelNode().set(new BigDecimal(arg));
    }

    @Rule(lhs = "node", rhs = "BIG INTEGER INT_VAL", args = "3")
    protected ModelNode parseBigInteger(final String arg) {
        return new ModelNode().set(new BigInteger(arg));
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "TRUE"),
        @Rule(lhs = "node", rhs = "FALSE")
    })
    protected ModelNode parseBoolean() {
        return new ModelNode().set(Boolean.parseBoolean(yyText()));
    }

    @Rules(rules = {
        @Rule(lhs = "bytes", rhs = "BYTES OPEN_BRACE INT_VAL", args = "3")
    })
    protected ByteArrayOutputStream startBytesInt(final String val) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(Integer.parseInt(val));
        return baos;
    }

    @Rules(rules = {
        @Rule(lhs = "bytes", rhs = "BYTES OPEN_BRACE INT_HEX_VAL", args = "3")
    })
    protected ByteArrayOutputStream startBytesHex(final String val) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(Integer.parseInt(val.substring(2), 16));
        return baos;
    }

    @Rules(rules = {
        @Rule(lhs = "bytes", rhs = "bytes COMMA INT_VAL", args = "1 3")
    })
    protected ByteArrayOutputStream nextByteInt(final ByteArrayOutputStream baos, final String val) {
        baos.write(Integer.parseInt(val));
        return baos;
    }

    @Rules(rules = {
        @Rule(lhs = "bytes", rhs = "bytes COMMA INT_HEX_VAL", args = "1 3")
    })
    protected ByteArrayOutputStream nextByteHex(final ByteArrayOutputStream baos, final String val) {
        baos.write(Integer.parseInt(val.substring(2), 16));
        return baos;
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "bytes CLOSE_BRACE", args = "1"),
        // allow a trailing ,
        @Rule(lhs = "node", rhs = "bytes COMMA CLOSE_BRACE", args = "1")
    })
    protected ModelNode finishBytes(final ByteArrayOutputStream baos) {
        return new ModelNode().set(baos.toByteArray());
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "BYTES OPEN_BRACE CLOSE_BRACE")
    })
    protected ModelNode emptyBytes() {
        return new ModelNode().set(NO_BYTES);
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "DEC_VAL", args = "1")
    })
    protected ModelNode parseDouble(final String arg) {
        return new ModelNode().set(Double.parseDouble(arg));
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "EXPRESSION STR_VAL", args = "2")
    })
    protected ModelNode parseExpression(final String arg) {
        return new ModelNode().setExpression(arg);
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "INT_VAL", args = "1")
    })
    protected ModelNode parseInt(final String arg) {
        return new ModelNode().set(Integer.parseInt(arg));
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "INT_HEX_VAL", args = "1")
    })
    protected ModelNode parseIntHex(final String arg) {
        return new ModelNode().set(Integer.parseInt(arg.substring(2), 16));
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "LONG_VAL", args = "1")
    })
    protected ModelNode parseLong(final String arg) {
        return new ModelNode().set(Long.parseLong(arg));
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "LONG_HEX_VAL", args = "1")
    })
    protected ModelNode parseLongHex(final String arg) {
        return new ModelNode().set(Long.parseLong(arg.substring(2), 16));
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "OPEN_BRACKET CLOSE_BRACKET")
    })
    protected ModelNode parseEmptyList() {
        return new ModelNode().setEmptyList();
    }

    @Rules(rules = {
        @Rule(lhs = "list", rhs = "OPEN_BRACKET node", args = "2")
    })
    protected ModelNode parseStartList(final ModelNode child) {
        return new ModelNode().addNoCopy(child);
    }

    @Rules(rules = {
        @Rule(lhs = "list", rhs = "list COMMA node", args = "1 3")
    })
    protected ModelNode parseListItem(final ModelNode list, final ModelNode child) {
        return list.addNoCopy(child);
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "list CLOSE_BRACKET", args = "1"),
        @Rule(lhs = "node", rhs = "list COMMA CLOSE_BRACKET", args = "1")
    })
    protected ModelNode finishList(final ModelNode list) {
        return list;
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "OPEN_BRACE CLOSE_BRACE")
    })
    protected ModelNode parseEmptyObject() {
        return new ModelNode().setEmptyObject();
    }

    @Rules(rules = {
        @Rule(lhs = "object", rhs = "OPEN_BRACE STR_VAL ARROW node", args = "2 4")
    })
    protected ModelNode parseStartObject(final String key, final ModelNode child) {
        final ModelNode node = new ModelNode();
        node.get(key).setNoCopy(child);
        return node;
    }

    @Rules(rules = {
        @Rule(lhs = "object", rhs = "object COMMA STR_VAL ARROW node", args = "1 3 5")
    })
    protected ModelNode parseObjectItem(final ModelNode object, final String key, final ModelNode child) {
        object.get(key).setNoCopy(child);
        return object;
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "object CLOSE_BRACE", args = "1"),
        @Rule(lhs = "node", rhs = "object COMMA CLOSE_BRACE", args = "1")
    })
    protected ModelNode finishObject(final ModelNode object) {
        return object;
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "OPEN_PAREN STR_VAL ARROW node CLOSE_PAREN", args = "2 4")
    })
    protected ModelNode parseProperty(final String key, final ModelNode value) {
        return new ModelNode().setNoCopy(key, value);
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "STR_VAL", args = "1")
    })
    protected ModelNode parseString(final String arg) {
        return new ModelNode().set(arg);
    }

    @Rules(rules = {
        @Rule(lhs = "node", rhs = "TYPE_VAL", args = "1")
    })
    protected ModelNode parseType(final String arg) {
        return new ModelNode().set(ModelType.valueOf(arg));
    }

    @Rule(lhs = "node", rhs = "UNDEFINED")
    protected ModelNode parseUndefined() {
        return new ModelNode();
    }

    public ModelNode getResult() {
        return result;
    }
}
