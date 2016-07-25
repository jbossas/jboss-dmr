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
import java.io.InputStream;

import java.util.LinkedList;
import java.util.Vector;
import java.util.Stack;

// TODO: [DMR-9] Delete this class when DMR release gets it to the JBoss EAP.
/**
 * This class have been deprecated in JBoss DMR 1.4.0 and will be removed in future releases.
 */
@Deprecated
public abstract class Parser
{
	protected final static int OPEN_BRACE = 256;
	protected final static int CLOSE_BRACE = 257;
	protected final static int OPEN_BRACKET = 258;
	protected final static int CLOSE_BRACKET = 259;
	protected final static int OPEN_PAREN = 260;
	protected final static int CLOSE_PAREN = 261;
	protected final static int ARROW = 262;
	protected final static int COMMA = 263;
	protected final static int BIG = 264;
	protected final static int BYTES = 265;
	protected final static int INTEGER = 266;
	protected final static int DECIMAL = 267;
	protected final static int EXPRESSION = 268;
	protected final static int UNDEFINED = 269;
	protected final static int TRUE = 270;
	protected final static int FALSE = 271;
	protected final static int INT_VAL = 272;
	protected final static int INT_HEX_VAL = 273;
	protected final static int LONG_VAL = 274;
	protected final static int LONG_HEX_VAL = 275;
	protected final static int DOUBLE_SPECIAL_VAL = 276;
	protected final static int DEC_VAL = 277;
	protected final static int STR_VAL = 278;
	protected final static int TYPE_VAL = 279;

	protected final static int INITIAL = 0;

	// an internal class for lazy initiation
	private final static class cc_lexer
	{
		private static char[] accept = ("\000\032\031\032\005\006\007\032\023\023\032\032\032\032\032\032\032\032\032\032\032\032\003\004\032\032\032\032\032\032\032\001\002\033\000\030\000\000\000\000\021\000\010\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\026\024\000\000\000\000\000\027\000\000\000\025\000\000\000\000\000\013\000\000\000\000\000\000\000\000\022\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\011\000\000\026\000\000\000\000\000\000\000\000\000\000\014\000\000\012\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\015\000\016\000\000\000\000\000\000\000\000\000\000\000\020\000\000\017").toCharArray ();
		private static char[] ecs = ("\000\000\000\000\000\000\000\000\000\001\002\000\000\001\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\001\000\003\000\000\000\000\000\004\005\000\000\006\007\010\000\011\012\012\012\012\012\012\012\012\012\000\000\000\013\014\000\000\015\016\017\020\021\022\023\000\024\025\000\026\027\030\031\032\000\033\034\035\036\000\000\037\040\000\041\042\043\000\044\000\045\046\047\050\051\052\053\000\054\000\000\055\056\057\060\061\000\062\063\064\065\000\000\066\067\000\070\000\071\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\072").toCharArray ();
		private static char[] base = ("\116\000\000\000\000\000\000\003\000\001\002\000\003\000\000\001\000\001\003\006\006\017\000\000\002\001\002\003\013\011\015\000\000\000\000\000\002\016\000\065\000\043\000\030\047\044\044\051\047\033\052\057\161\165\162\161\163\176\144\134\152\141\146\140\140\156\000\u0081\164\u0083\u0089\u008d\u0081\000\161\000\u008b\000\u008e\u0086\u008d\u0091\u0092\000\173\171\164\164\177\u0080\u0081\011\000\015\u009a\u0090\u0097\u009d\u0080\u00a1\u00a0\u009a\u00a1\u0081\u0087\u008d\u008e\u008d\000\u008f\000\000\u00a9\u00a3\u00af\000\u00a1\u0092\000\u00a4\000\u00ac\000\u009c\u008f\000\u009a\u0098\u00b6\u00a9\u00af\u00ac\u0095\u00ad\u00b3\u009f\u009a\u009c\u00a0\u00bc\u00c0\u00be\u009c\u00b4\u00c4\000\u00aa\000\u00ae\u00c1\u00c6\u00c1\u00cb\u00ac\u00b5\u00d1\u00ce\000\u00b1\000\u00cb\u00c7\000\000\u00e3").toCharArray ();
		private static char[] next = ("\042\002\002\043\000\042\000\000\047\011\011\000\010\011\052\065\156\133\157\157\053\062\050\045\060\054\063\046\056\160\066\057\055\161\044\067\042\064\070\071\076\133\074\104\103\103\072\061\103\103\103\103\103\103\051\000\075\073\077\100\101\061\102\102\105\106\107\110\111\112\113\114\103\103\103\103\103\103\001\002\002\003\004\005\006\007\001\010\011\012\001\001\013\001\014\015\001\001\016\001\017\001\020\021\022\001\023\024\025\001\001\026\001\027\001\001\030\001\031\032\033\001\034\001\001\001\001\001\001\001\035\036\001\001\037\040\041\115\116\117\120\121\122\123\124\125\126\127\130\131\132\134\135\136\137\140\141\142\111\143\144\145\111\146\147\150\151\152\153\154\155\162\111\163\164\165\166\167\170\171\172\173\174\175\176\177\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008a\u008b\u008c\111\u008d\u008e\u008f\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\115\111\u0098\u0099\u009a\u009b\u009c\u009d\111\u009e\u009f\u00a0\u00a1\u00a2\111\111\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000").toCharArray ();
		private static char[] check = ("\u00a3\002\002\003\044\044\u00a4\156\010\010\010\u00a4\007\007\012\021\133\102\133\133\013\017\010\007\016\013\017\007\014\135\022\015\013\135\003\023\044\020\024\025\033\102\031\053\051\051\030\016\051\051\051\051\051\051\010\011\032\030\034\035\036\045\047\047\054\055\056\057\060\061\062\063\051\051\051\051\051\051\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\064\065\066\067\070\071\072\073\074\075\076\077\100\101\103\104\105\106\107\110\112\114\116\117\120\121\122\124\125\126\127\130\131\132\136\137\140\141\142\143\144\145\146\147\150\151\152\153\155\160\161\162\164\165\167\171\173\174\176\177\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008a\u008b\u008c\u008d\u008e\u008f\u0090\u0092\u0094\u0095\u0096\u0097\u0098\u0099\u009a\u009b\u009c\u009e\u00a0\u00a1\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4\u00a4").toCharArray ();
		private static char[] defaults = ("\u00a4\u00a4\001\u00a3\u00a4\u00a4\u00a4\001\001\010\001\001\001\001\001\001\001\001\001\001\001\001\u00a4\u00a4\001\001\001\001\001\001\001\u00a4\u00a4\u00a4\003\u00a4\003\001\020\001\u00a4\001\u00a4\001\001\001\001\001\001\001\001\001\001\001\001\001\001\001\001\001\001\001\001\001\001\001\047\051\001\001\001\001\001\u00a4\001\060\001\u00a4\001\001\001\001\001\u00a4\001\001\001\001\001\001\001\001\u00a4\001\001\001\001\001\001\001\001\001\001\001\001\001\001\001\u00a4\001\133\156\001\001\001\121\001\001\060\001\114\001\u00a4\001\001\u00a4\001\001\001\001\001\001\001\001\001\001\001\001\001\001\001\001\001\001\001\u00a4\001\u00a4\001\001\001\001\001\001\001\001\001\u0082\001\u00a4\001\001\u00a4\u00a4\u00a4").toCharArray ();
		private static char[] meta = ("\000\000\000\001\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\001\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\001").toCharArray ();
	}

	// an internal class for lazy initiation
	private final static class cc_parser
	{
		private static char[] rule = ("\000\001\001\003\003\003\001\001\002\003\003\001\002\001\001\001\001\002\002\003\002\002\003\005\001\001\001\003\003\003\003\002\003\004\005").toCharArray ();
		private static char[] ecs = ("\000\001\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\002\003\004\005\006\007\010\011\012\013\014\015\016\017\020\021\022\023\024\025\000\026\027\030").toCharArray ();
		private static char[] base = ("\031\060\017\001\003\000\007\u00d4\111\112\124\125\137\140\152\153\165\012\000\005\u00d5\010\166\032\u0080\004\060\u00ca\017\000\u0081\u008b\003\u008c\067\u0096\002\000\000\u0097\u00a1\u00a2\u00ac\035\064\u00ad\u00b4\u00b5\u00bc\060\u00c4\103\u00b7\u00d8\000\u00cc\u00bf\u00e2\u00f0\u00fc\u00f5\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00f6\u00fc\u00fc\u00f7\u00f8\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00fc\u00f9\u00fc\u00fc\u00fc").toCharArray ();
		private static char[] next = ("\ufffe\000\035\052\000\062\055\000\037\uffe1\uffff\043\000\uffe1\040\033\034\044\053\054\030\056\057\000\032\063\000\001\000\002\036\003\uffe5\050\045\004\005\051\uffe5\006\007\010\011\012\013\014\015\016\017\020\000\026\000\uffe0\000\uffe4\046\uffe0\000\000\060\uffe4\000\000\000\000\000\000\000\000\000\027\000\ufffa\ufff9\066\ufffa\ufff9\ufffa\ufff9\ufffa\ufff9\ufffa\ufff9\ufff3\ufff2\000\ufff3\ufff2\ufff3\ufff2\ufff3\ufff2\ufff3\ufff2\ufff1\ufff0\000\ufff1\ufff0\ufff1\ufff0\ufff1\ufff0\ufff1\ufff0\ufff5\uffe8\000\ufff5\uffe8\ufff5\uffe8\ufff5\uffe8\ufff5\uffe8\uffe7\uffec\000\uffe7\uffec\uffe7\uffec\uffe7\uffec\uffe7\uffec\uffef\ufff4\000\uffef\ufff4\uffef\ufff4\uffef\ufff4\uffef\ufff4\ufff8\uffee\000\ufff8\uffee\ufff8\uffee\ufff8\uffee\ufff8\uffee\uffeb\ufffb\000\uffeb\ufffb\uffeb\ufffb\uffeb\ufffb\uffeb\ufffb\ufffc\ufffd\000\ufffc\ufffd\ufffc\ufffd\ufffc\ufffd\ufffc\ufffd\ufff6\ufff7\000\ufff6\ufff7\ufff6\ufff7\ufff6\ufff7\ufff6\ufff7\uffe3\uffe2\000\uffdf\000\uffed\uffe3\uffe2\uffed\uffdf\uffed\uffde\uffed\uffea\uffed\000\uffea\uffde\uffea\000\uffea\uffe9\uffea\000\uffe9\000\uffe9\000\uffe9\uffe6\uffe9\000\uffe6\000\uffe6\041\uffe6\047\uffe6\042\067\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\021\022\023\024\025\000\031\061\064\065\070\000\000\000\000\000\000").toCharArray ();
		private static char[] check = ("\022\071\005\035\003\044\040\023\023\031\021\025\021\031\023\004\004\025\035\035\002\040\040\005\003\044\004\000\027\000\006\000\053\034\027\000\000\034\053\000\000\000\000\000\000\000\000\000\000\000\001\001\001\061\001\054\032\061\001\001\042\054\001\001\001\001\001\001\001\001\001\001\001\010\011\063\010\011\010\011\010\011\010\011\012\013\071\012\013\012\013\012\013\012\013\014\015\071\014\015\014\015\014\015\014\015\016\017\071\016\017\016\017\016\017\016\017\020\026\071\020\026\020\026\020\026\020\026\030\036\071\030\036\030\036\030\036\030\036\037\041\071\037\041\037\041\037\041\037\041\043\047\071\043\047\043\047\043\047\043\047\050\051\071\050\051\050\051\050\051\050\051\052\055\071\052\055\052\055\052\055\052\055\056\057\071\064\071\060\056\057\060\064\060\070\060\062\060\071\062\070\062\071\062\067\062\071\067\071\067\071\067\007\067\007\007\024\007\024\007\033\007\024\065\065\033\071\071\071\071\071\071\071\071\071\071\071\071\071\071\071\072\072\072\072\072\074\074\134\137\140\160\163\163\163\163\163\163").toCharArray ();
		private static char[] defaults = ("\071\000\000\001\003\003\003\005\007\007\007\007\007\007\007\007\007\005\021\005\023\023\007\005\007\024\027\003\033\033\007\007\033\007\000\007\001\000\000\007\007\007\007\023\023\007\023\023\007\024\007\027\023\027\000\007\023\071").toCharArray ();
		private static char[] gotoDefault = ("\163\163\072\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\074\163\163\074\074\163\163\163\163\163\163\163\163\163\163\163\163\163\163\163\074\163\163\163").toCharArray ();
		private static char[] lhs = ("\000\031\032\033\033\033\033\033\033\033\033\033\033\033\033\033\033\033\033\033\033\033\033\033\033\033\033\034\034\034\034\035\035\036\036").toCharArray ();
	}

	private final static class YYParserState	// internal tracking tool
	{
		int token;			// the current token type
		Object value;		// the current value associated with token
		int state;			// the current scan state

		YYParserState ()	// EOF token construction
		{
			this (0, null, 0);
		}
		YYParserState (int token)
		{
			this (token, null, 0);
		}
		YYParserState (int token, Object value)
		{
			this (token, value, 0);
		}
		YYParserState (int token, Object value, int state)
		{
			this.token = token;
			this.value = value;
			this.state = state;
		}
	}

	// lookahead stack for the parser
	private final LinkedList _yyLookaheadStack = new LinkedList ();
	// state stack for the parser
	private final Vector _yyStateStack = new Vector (512, 512);
	// flag that indicates error
	private boolean _yyInError;
	// internal track of the argument start
	private int _yyArgStart;
	// for passing value from lexer to parser
	private Object _yyValue;

	private InputStream _yyIs = System.in;
	private byte[] _yyBuffer;
	private int _yyBufferSize = 4096;
	private int _yyMatchStart;
	private int _yyBufferEnd;

	private int _yyBaseState;

	private int _yyTextStart;
	private int _yyLength;

	private Stack _yyLexerStack;
	private Stack _yyInputStack;


	/**
	 * Set the current input.
	 *
	 * @param	is
	 *			the new input.
	 */
	public void setInput (InputStream is)
	{
		_yyIs = is;
	}

	/**
	 * Obtain the current input.
	 *
	 * @return	the current input
	 */
	public InputStream getInput ()
	{
		return _yyIs;
	}

	/**
	 * Switch the current input to the new input.  The old input and already
	 * buffered characters are pushed onto the stack.
	 *
	 * @param	is
	 * 			the new input
	 */
	public void yyPushInput (InputStream is)
	{
		int len = _yyBufferEnd - _yyMatchStart;
		byte[] leftOver = new byte[len];
		System.arraycopy (_yyBuffer, _yyMatchStart, leftOver, 0, len);

		Object[] states = new Object[4];
		states[0] = _yyIs;
		states[1] = leftOver;

		if (_yyInputStack == null)
			_yyInputStack = new Stack ();
		_yyInputStack.push (states);

		_yyIs = is;
		_yyMatchStart = 0;
		_yyBufferEnd = 0;
	}

	/**
	 * Switch the current input to the old input on stack.  The currently
	 * buffered characters are inserted infront of the old buffered characters.
	 */
	public void yyPopInput ()
	{
		Object[] states = (Object[])_yyInputStack.pop ();
		_yyIs = (InputStream)states[0];
		byte[] leftOver = (byte[])states[1];

		int curLen = _yyBufferEnd - _yyMatchStart;

		if ((leftOver.length + curLen) > _yyBuffer.length)
		{
			byte[] newBuffer = new byte[leftOver.length + curLen];
			System.arraycopy (_yyBuffer, _yyMatchStart, newBuffer, 0, curLen);
			System.arraycopy (leftOver, 0, newBuffer, curLen, leftOver.length);
			_yyBuffer = newBuffer;
			_yyMatchStart = 0;
			_yyBufferEnd = leftOver.length + curLen;
		}
		else
		{
			int start = _yyMatchStart;
			int end = _yyBufferEnd;
			byte[] buffer = _yyBuffer;

			for (int i = 0; start < end; ++i, ++start)
				buffer[i] = buffer[start];
			System.arraycopy (leftOver, 0, buffer, curLen, leftOver.length);
			_yyMatchStart = 0;
			_yyBufferEnd = leftOver.length + curLen;
		}
	}

	/**
	 * Obtain the number of input objects on the stack.
	 *
	 * @return	the number of input objects on the stack.
	 */
	public int yyInputStackSize ()
	{
		return _yyInputStack == null ? 0 : _yyInputStack.size ();
	}


	/**
	 * Get the current token text.
	 * <p>
	 * Avoid calling this function unless it is absolutely necessary since it creates
	 * a copy of the token string.  The string length can be found by reading _yyLength
	 * or calling yyLength () function.
	 *
	 * @return	the current text token.
	 */
	public String yyText ()
	{
		if (_yyMatchStart == _yyTextStart)		// this is the case when we have EOF
			return null;
		return new String (_yyBuffer, _yyTextStart, _yyMatchStart - _yyTextStart);
	}

	/**
	 * Get the current text token's length.  Actions specified in the CookCC file
	 * can directly access the variable _yyLength.
	 *
	 * @return	the string token length
	 */
	public int yyLength ()
	{
		return _yyLength;
	}

	/**
	 * Print the current string token to the standard output.
	 */
	public void echo ()
	{
		System.out.print (yyText ());
	}

	/**
	 * Put all but n characters back to the input stream.  Be aware that calling
	 * yyLess (0) is allowed, but be sure to change the state some how to avoid
	 * an endless loop.
	 *
	 * @param	n
	 * 			The number of characters.
	 */
	protected void yyLess (int n)
	{
		if (n < 0)
			throw new IllegalArgumentException ("yyLess function requires a non-zero value.");
		if (n > (_yyMatchStart - _yyTextStart))
			throw new IndexOutOfBoundsException ("yyLess function called with a too large index value " + n + ".");
		_yyMatchStart = _yyTextStart + n;
	}

	/**
	 * Set the lexer's current state.
	 *
	 * @param	baseState
	 *			the base state index
	 */
	protected void begin (int baseState)
	{
		_yyBaseState = baseState;
	}

	/**
	 * Push the current state onto lexer state onto stack and
	 * begin the new state specified by the user.
	 *
	 * @param	newState
	 *			the new state.
	 */
	protected void yyPushLexerState (int newState)
	{
		if (_yyLexerStack == null)
			_yyLexerStack = new Stack ();
		_yyLexerStack.push (new Integer (_yyBaseState));
		begin (newState);
	}

	/**
	 * Restore the previous lexer state.
	 */
	protected void yyPopLexerState ()
	{
		begin (((Integer)_yyLexerStack.pop ()).intValue ());
	}


	// read more data from the input
	protected boolean yyRefreshBuffer () throws IOException
	{
		if (_yyBuffer == null)
			_yyBuffer = new byte[_yyBufferSize];
		if (_yyMatchStart > 0)
		{
			if (_yyBufferEnd > _yyMatchStart)
			{
				System.arraycopy (_yyBuffer, _yyMatchStart, _yyBuffer, 0, _yyBufferEnd - _yyMatchStart);
				_yyBufferEnd -= _yyMatchStart;
				_yyMatchStart = 0;
			}
			else
			{
				_yyMatchStart = 0;
				_yyBufferEnd = 0;
			}
		}
		else if (_yyBufferEnd == _yyBuffer.length)
		{
			byte[] newBuffer = new byte[_yyBuffer.length + _yyBuffer.length / 2];
			System.arraycopy (_yyBuffer, 0, newBuffer, 0, _yyBufferEnd);
			_yyBuffer = newBuffer;
		}

		int readSize = _yyIs.read (_yyBuffer, _yyBufferEnd, _yyBuffer.length - _yyBufferEnd);
		if (readSize > 0)
			_yyBufferEnd += readSize;
		else if (readSize < 0 && !yyWrap ())		// since we are at EOF, call yyWrap ().  If the return value of yyWrap is false, refresh buffer again
			return yyRefreshBuffer ();
		return readSize >= 0;
	}

	/**
	 * Reset the internal buffer.
	 */
	public void yyResetBuffer ()
	{
		_yyMatchStart = 0;
		_yyBufferEnd = 0;
	}

	/**
	 * Set the internal buffer size.  This action can only be performed
	 * when the buffer is empty.  Having a large buffer is useful to read
	 * a whole file in to increase the performance sometimes.
	 *
	 * @param	bufferSize
	 *			the new buffer size.
	 */
	public void setBufferSize (int bufferSize)
	{
		if (_yyBufferEnd > _yyMatchStart)
			throw new IllegalArgumentException ("Cannot change lexer buffer size at this moment.");
		_yyBufferSize = bufferSize;
		_yyMatchStart = 0;
		_yyBufferEnd = 0;
		if (_yyBuffer != null && bufferSize != _yyBuffer.length)
			_yyBuffer = new byte[bufferSize];
	}

	/**
	 * Call this function to start the scanning of the input.
	 *
	 * @return	a token or status value.
	 * @throws	IOException
	 *			in case of I/O error.
	 */
	protected int yyLex () throws IOException
	{

		char[] cc_ecs = cc_lexer.ecs;
		char[] cc_next = cc_lexer.next;
		char[] cc_check = cc_lexer.check;
		char[] cc_base = cc_lexer.base;
		char[] cc_default = cc_lexer.defaults;
		char[] cc_meta = cc_lexer.meta;
		char[] cc_accept = cc_lexer.accept;

		byte[] buffer = _yyBuffer;

		while (true)
		{
			// initiate variables necessary for lookup
			int cc_matchedState = _yyBaseState;

			int matchedLength = 0;

			int internalBufferEnd = _yyBufferEnd;
			int lookahead = _yyMatchStart;

			int cc_backupMatchedState = cc_matchedState;
			int cc_backupMatchedLength = 0;

			// the DFA lookup
			while (true)
			{
				// check buffer status
				if (lookahead < internalBufferEnd)
				{
					// now okay to process the character
					int cc_toState;
					int symbol = cc_ecs[buffer[lookahead] & 0xff];
					cc_toState = cc_matchedState;
					while (cc_check[symbol + cc_base[cc_toState]] != cc_toState)
					{
						cc_toState = cc_default[cc_toState];
						if (cc_toState >= 163)
							symbol = cc_meta[symbol];
					}
					cc_toState = cc_next[symbol + cc_base[cc_toState]];

					if (cc_toState == 0)
					{
						cc_matchedState = cc_backupMatchedState;
						matchedLength = cc_backupMatchedLength;
						break;
					}

					cc_matchedState = cc_toState;
					++lookahead;
					++matchedLength;

					if (cc_accept[cc_matchedState] > 0)
					{
						cc_backupMatchedState = cc_toState;
						cc_backupMatchedLength = matchedLength;
					}
				}
				else
				{
					int lookPos = lookahead - _yyMatchStart;
					boolean refresh = yyRefreshBuffer ();
					buffer = _yyBuffer;
					internalBufferEnd = _yyBufferEnd;
					lookahead = _yyMatchStart + lookPos;
					if (! refresh)
					{
						// <<EOF>>
						int cc_toState;
						int symbol = cc_ecs[256];
						cc_toState = cc_matchedState;
						while (cc_check[symbol + cc_base[cc_toState]] != cc_toState)
						{
							cc_toState = cc_default[cc_toState];
							if (cc_toState >= 163)
								symbol = cc_meta[symbol];
						}
						cc_toState = cc_next[symbol + cc_base[cc_toState]];

						if (cc_toState != 0)
							cc_matchedState = cc_toState;
						else
						{
							cc_matchedState = cc_backupMatchedState;
							matchedLength = cc_backupMatchedLength;
						}
						break;
					}
				}
			}

			_yyTextStart = _yyMatchStart;
			_yyMatchStart += matchedLength;
			_yyLength = matchedLength;


			switch (cc_accept[cc_matchedState])
			{
				case 1:	// \{
				{
					_yyValue = m_this.parseToken (); return OPEN_BRACE;
				}
				case 31: break;
				case 2:	// \}
				{
					_yyValue = m_this.parseToken (); return CLOSE_BRACE;
				}
				case 32: break;
				case 3:	// \[
				{
					_yyValue = m_this.parseToken (); return OPEN_BRACKET;
				}
				case 33: break;
				case 4:	// \]
				{
					_yyValue = m_this.parseToken (); return CLOSE_BRACKET;
				}
				case 34: break;
				case 5:	// \(
				{
					_yyValue = m_this.parseToken (); return OPEN_PAREN;
				}
				case 35: break;
				case 6:	// \)
				{
					_yyValue = m_this.parseToken (); return CLOSE_PAREN;
				}
				case 36: break;
				case 7:	// ,
				{
					_yyValue = m_this.parseToken (); return COMMA;
				}
				case 37: break;
				case 8:	// =>
				{
					_yyValue = m_this.parseToken (); return ARROW;
				}
				case 38: break;
				case 9:	// true
				{
					_yyValue = m_this.parseToken (); return TRUE;
				}
				case 39: break;
				case 10:	// false
				{
					_yyValue = m_this.parseToken (); return FALSE;
				}
				case 40: break;
				case 11:	// big
				{
					_yyValue = m_this.parseToken (); return BIG;
				}
				case 41: break;
				case 12:	// bytes
				{
					_yyValue = m_this.parseToken (); return BYTES;
				}
				case 42: break;
				case 13:	// decimal
				{
					_yyValue = m_this.parseToken (); return DECIMAL;
				}
				case 43: break;
				case 14:	// integer
				{
					_yyValue = m_this.parseToken (); return INTEGER;
				}
				case 44: break;
				case 15:	// expression
				{
					_yyValue = m_this.parseToken (); return EXPRESSION;
				}
				case 45: break;
				case 16:	// undefined
				{
					_yyValue = m_this.parseToken (); return UNDEFINED;
				}
				case 46: break;
				case 17:	// [+-]?[0-9]+L
				{
					_yyValue = m_this.parsePlainValue (); return LONG_VAL;
				}
				case 47: break;
				case 18:	// [+-]?0x[0-9a-fA-F]+L
				{
					_yyValue = m_this.parsePlainValue (); return LONG_HEX_VAL;
				}
				case 48: break;
				case 19:	// [+-]?[0-9]+
				{
					_yyValue = m_this.parsePlainValue (); return INT_VAL;
				}
				case 49: break;
				case 20:	// [+-]?0x[0-9a-fA-F]+
				{
					_yyValue = m_this.parsePlainValue (); return INT_HEX_VAL;
				}
				case 50: break;
				case 21:	// [+-]?(NaN|Infinity)
				{
					_yyValue = m_this.parsePlainValue (); return DOUBLE_SPECIAL_VAL;
				}
				case 51: break;
				case 22:	// [+-]?([0-9]+\.[0-9]+([eE][+-]?[0-9]+)?)
				{
					_yyValue = m_this.parsePlainValue (); return DEC_VAL;
				}
				case 52: break;
				case 23:	// BIG_DECIMAL|BIG_INTEGER|BOOLEAN|BYTES|DOUBLE|EXPRESSION|INT|LIST|LONG|OBJECT|PROPERTY|STRING|TYPE|UNDEFINED
				{
					_yyValue = m_this.parsePlainValue (); return TYPE_VAL;
				}
				case 53: break;
				case 24:	// \"([^"\\]+|\\.)*\"
				{
					_yyValue = m_this.parseStringValue (); return STR_VAL;
				}
				case 54: break;
				case 25:	// [ \t\r\n]+
				{
					m_this.ignored ();
				}
				case 55: break;
				case 26:	// .
				{
					m_this.invalid ();
				}
				case 56: break;
				case 27:	// <<EOF>>
				{
					m_this.parseEOF (); return 0;
				}
				case 57: break;
				case 28:	// .|\n
				{
					echo ();			// default character action
				}
				case 58: break;
				case 29:	// <<EOF>>
				{
					return 0;			// default EOF action
				}
				case 59: break;
				default:
					throw new IOException ("Internal error in Parser lexer.");
			}

		}
	}


	/**
	 * Call this function to start parsing.
	 *
	 * @return	0 if everything is okay, or 1 if an error occurred.
	 * @throws	IOException
	 *			in case of error
	 */
	public int yyParse () throws IOException
	{
		char[] cc_ecs = cc_parser.ecs;
		char[] cc_next = cc_parser.next;
		char[] cc_check = cc_parser.check;
		char[] cc_base = cc_parser.base;
		char[] cc_default = cc_parser.defaults;
		char[] cc_gotoDefault = cc_parser.gotoDefault;
		char[] cc_rule = cc_parser.rule;
		char[] cc_lhs = cc_parser.lhs;

		LinkedList cc_lookaheadStack = _yyLookaheadStack;
		Vector cc_stateStack = _yyStateStack;

		if (cc_stateStack.size () == 0)
			cc_stateStack.add (new YYParserState ());

		int cc_toState = 0;

		for (;;)
		{
			YYParserState cc_lookahead;

			int cc_fromState;
			char cc_ch;

			//
			// check if there are any lookahead tokens on stack
			// if not, then call yyLex ()
			//
			if (cc_lookaheadStack.size () == 0)
			{
				_yyValue = null;
				int val = yyLex ();
				cc_lookahead = new YYParserState (val, _yyValue);
				cc_lookaheadStack.add (cc_lookahead);
			}
			else
				cc_lookahead = (YYParserState)cc_lookaheadStack.getLast ();

			cc_ch = cc_ecs[cc_lookahead.token];
			cc_fromState = ((YYParserState)cc_stateStack.get (cc_stateStack.size () - 1)).state;
			int cc_symbol = cc_ch;
			cc_toState = cc_fromState;
			while (cc_check[cc_symbol + cc_base[cc_toState]] != cc_toState)
			{
				cc_toState = cc_default[cc_toState];
				if (cc_toState >= 57)
					cc_symbol = 0;
			}
			cc_toState = (short)cc_next[cc_symbol + cc_base[cc_toState]];


			//
			// check the value of toState and determine what to do
			// with it
			//
			if (cc_toState > 0)
			{
				// shift
				cc_lookahead.state = cc_toState;
				cc_stateStack.add (cc_lookahead);
				cc_lookaheadStack.removeLast ();
				continue;
			}
			else if (cc_toState == 0)
			{
				// error
				if (_yyInError)
				{
					// first check if the error is at the lookahead
					if (cc_ch == 1)
					{
						// so we need to reduce the stack until a state with reduceable
						// action is found
						if (_yyStateStack.size () > 1)
							_yyStateStack.setSize (_yyStateStack.size () - 1);
						else
							return 1;	// can't do much we exit the parser
					}
					else
					{
						// this means that we need to dump the lookahead.
						if (cc_ch == 0)		// can't do much with EOF;
							return 1;
						cc_lookaheadStack.removeLast ();
					}
					continue;
				}
				else
				{
					if (yyParseError (cc_lookahead.token))
						return 1;
					_yyLookaheadStack.add (new YYParserState (1, _yyValue));
					_yyInError = true;
					continue;
				}
			}
			_yyInError = false;
			// now the reduce action
			int cc_ruleState = -cc_toState;

			_yyArgStart = cc_stateStack.size () - cc_rule[cc_ruleState] - 1;
			//
			// find the state that said need this non-terminal
			//
			cc_fromState = ((YYParserState)cc_stateStack.get (_yyArgStart)).state;

			//
			// find the state to goto after shifting the non-terminal
			// onto the stack.
			//
			if (cc_ruleState == 1)
				cc_toState = 0;			// reset the parser
			else
			{
				cc_toState = cc_fromState + 58;
				int cc_tmpCh = cc_lhs[cc_ruleState] - 25;
				while (cc_check[cc_tmpCh + cc_base[cc_toState]] != cc_toState)
					cc_toState = cc_gotoDefault[cc_toState - 58];
				cc_toState = cc_next[cc_tmpCh + cc_base[cc_toState]];
			}

			_yyValue = null;

			switch (cc_ruleState)
			{
				case 1:					// accept
					return 0;
				case 2:	// complete : node
				{
					return m_this.parse ((org.jboss.dmr.ModelNode)yyGetValue (1));
				}
				case 37: break;
				case 3:	// node : BIG DECIMAL DEC_VAL
				{
					_yyValue = m_this.parseBigDecimal ((java.lang.String)yyGetValue (3));
				}
				case 38: break;
				case 4:	// node : BIG DECIMAL INT_VAL
				{
					_yyValue = m_this.parseBigDecimal ((java.lang.String)yyGetValue (3));
				}
				case 39: break;
				case 5:	// node : BIG INTEGER INT_VAL
				{
					_yyValue = m_this.parseBigInteger ((java.lang.String)yyGetValue (3));
				}
				case 40: break;
				case 6:	// node : TRUE
				{
					_yyValue = m_this.parseTrue ();
				}
				case 41: break;
				case 7:	// node : FALSE
				{
					_yyValue = m_this.parseFalse ();
				}
				case 42: break;
				case 8:	// node : bytes CLOSE_BRACE
				{
					_yyValue = m_this.finishBytes ((java.io.ByteArrayOutputStream)yyGetValue (1));
				}
				case 43: break;
				case 9:	// node : bytes COMMA CLOSE_BRACE
				{
					_yyValue = m_this.finishBytes ((java.io.ByteArrayOutputStream)yyGetValue (1));
				}
				case 44: break;
				case 10:	// node : BYTES OPEN_BRACE CLOSE_BRACE
				{
					_yyValue = m_this.emptyBytes ();
				}
				case 45: break;
				case 11:	// node : DEC_VAL
				{
					_yyValue = m_this.parseDouble ((java.lang.String)yyGetValue (1));
				}
				case 46: break;
				case 12:	// node : EXPRESSION STR_VAL
				{
					_yyValue = m_this.parseExpression ((java.lang.String)yyGetValue (2));
				}
				case 47: break;
				case 13:	// node : INT_VAL
				{
					_yyValue = m_this.parseInt ((java.lang.String)yyGetValue (1));
				}
				case 48: break;
				case 14:	// node : INT_HEX_VAL
				{
					_yyValue = m_this.parseIntHex ((java.lang.String)yyGetValue (1));
				}
				case 49: break;
				case 15:	// node : LONG_VAL
				{
					_yyValue = m_this.parseLong ((java.lang.String)yyGetValue (1));
				}
				case 50: break;
				case 16:	// node : LONG_HEX_VAL
				{
					_yyValue = m_this.parseLongHex ((java.lang.String)yyGetValue (1));
				}
				case 51: break;
				case 17:	// node : OPEN_BRACKET CLOSE_BRACKET
				{
					_yyValue = m_this.parseEmptyList ();
				}
				case 52: break;
				case 18:	// node : list CLOSE_BRACKET
				{
					_yyValue = m_this.finishList ((org.jboss.dmr.ModelNode)yyGetValue (1));
				}
				case 53: break;
				case 19:	// node : list COMMA CLOSE_BRACKET
				{
					_yyValue = m_this.finishList ((org.jboss.dmr.ModelNode)yyGetValue (1));
				}
				case 54: break;
				case 20:	// node : OPEN_BRACE CLOSE_BRACE
				{
					_yyValue = m_this.parseEmptyObject ();
				}
				case 55: break;
				case 21:	// node : object CLOSE_BRACE
				{
					_yyValue = m_this.finishObject ((org.jboss.dmr.ModelNode)yyGetValue (1));
				}
				case 56: break;
				case 22:	// node : object COMMA CLOSE_BRACE
				{
					_yyValue = m_this.finishObject ((org.jboss.dmr.ModelNode)yyGetValue (1));
				}
				case 57: break;
				case 23:	// node : OPEN_PAREN STR_VAL ARROW node CLOSE_PAREN
				{
					_yyValue = m_this.parseProperty ((java.lang.String)yyGetValue (2), (org.jboss.dmr.ModelNode)yyGetValue (4));
				}
				case 58: break;
				case 24:	// node : STR_VAL
				{
					_yyValue = m_this.parseString ((java.lang.String)yyGetValue (1));
				}
				case 59: break;
				case 25:	// node : TYPE_VAL
				{
					_yyValue = m_this.parseType ((java.lang.String)yyGetValue (1));
				}
				case 60: break;
				case 26:	// node : UNDEFINED
				{
					_yyValue = m_this.parseUndefined ();
				}
				case 61: break;
				case 27:	// bytes : BYTES OPEN_BRACE INT_VAL
				{
					_yyValue = m_this.startBytesInt ((java.lang.String)yyGetValue (3));
				}
				case 62: break;
				case 28:	// bytes : BYTES OPEN_BRACE INT_HEX_VAL
				{
					_yyValue = m_this.startBytesHex ((java.lang.String)yyGetValue (3));
				}
				case 63: break;
				case 29:	// bytes : bytes COMMA INT_VAL
				{
					_yyValue = m_this.nextByteInt ((java.io.ByteArrayOutputStream)yyGetValue (1), (java.lang.String)yyGetValue (3));
				}
				case 64: break;
				case 30:	// bytes : bytes COMMA INT_HEX_VAL
				{
					_yyValue = m_this.nextByteHex ((java.io.ByteArrayOutputStream)yyGetValue (1), (java.lang.String)yyGetValue (3));
				}
				case 65: break;
				case 31:	// list : OPEN_BRACKET node
				{
					_yyValue = m_this.parseStartList ((org.jboss.dmr.ModelNode)yyGetValue (2));
				}
				case 66: break;
				case 32:	// list : list COMMA node
				{
					_yyValue = m_this.parseListItem ((org.jboss.dmr.ModelNode)yyGetValue (1), (org.jboss.dmr.ModelNode)yyGetValue (3));
				}
				case 67: break;
				case 33:	// object : OPEN_BRACE STR_VAL ARROW node
				{
					_yyValue = m_this.parseStartObject ((java.lang.String)yyGetValue (2), (org.jboss.dmr.ModelNode)yyGetValue (4));
				}
				case 68: break;
				case 34:	// object : object COMMA STR_VAL ARROW node
				{
					_yyValue = m_this.parseObjectItem ((org.jboss.dmr.ModelNode)yyGetValue (1), (java.lang.String)yyGetValue (3), (org.jboss.dmr.ModelNode)yyGetValue (5));
				}
				case 69: break;
				default:
					throw new IOException ("Internal error in Parser parser.");
			}

			YYParserState cc_reduced = new YYParserState (-cc_ruleState, _yyValue, cc_toState);
			_yyValue = null;
			cc_stateStack.setSize (_yyArgStart + 1);
			cc_stateStack.add (cc_reduced);
		}
	}

	/**
	 * This function is used by the error handling grammars to check the immediate
	 * lookahead token on the stack.
	 *
	 * @return	the top of lookahead stack.
	 */
	protected YYParserState yyPeekLookahead ()
	{
		return (YYParserState)_yyLookaheadStack.getLast ();
	}

	/**
	 * This function is used by the error handling grammars to pop an unwantted
	 * token from the lookahead stack.
	 */
	protected void yyPopLookahead ()
	{
		_yyLookaheadStack.removeLast ();
	}

	/**
	 * Clear the error flag.  If this flag is present and the parser again sees
	 * another error transition, it would immediately calls yyParseError, which
	 * would by default exit the parser.
	 * <p>
	 * This function is used in error recovery.
	 */
	protected void yyClearError ()
	{
		_yyInError = false;
	}

	/**
	 * This function reports error and return true if critical error occurred, or
	 * false if the error has been successfully recovered.  IOException is an optional
	 * choice of reporting error.
	 *
	 * @param	terminal
	 *			the terminal that caused the error.
	 * @return	true if irrecoverable error occurred.  Or simply throw an IOException.
	 *			false if the parsing can be continued to check for specific
	 *			error tokens.
	 * @throws	IOException
	 *			in case of error.
	 */
	protected boolean yyParseError (int terminal) throws IOException
	{
		return false;
	}

	/**
	 * Gets the object value associated with the symbol at the argument's position.
	 *
	 * @param	arg
	 *			the symbol position starting from 1.
	 * @return	the object value associated with symbol.
	 */
	protected Object yyGetValue (int arg)
	{
		return ((YYParserState)_yyStateStack.get (_yyArgStart + arg)).value;
	}

	/**
	 * Set the object value for the current non-terminal being reduced.
	 *
	 * @param	value
	 * 			the object value for the current non-terminal.
	 */
	protected void yySetValue (Object value)
	{
		_yyValue = value;
	}




	private final org.jboss.dmr.ModelNodeParser m_this = (org.jboss.dmr.ModelNodeParser)this;

	/**
	 * This function is used to change the initial state for the lexer.
	 *
	 * @param	state
	 *			the name of the state
	 */
	protected void begin (String state)
	{
		if ("INITIAL".equals (state))
		{
			begin (INITIAL);
			return;
		}
		throw new IllegalArgumentException ("Unknown lexer state: " + state);
	}

	/**
	 * Push the current state onto lexer state onto stack and
	 * begin the new state specified by the user.
	 *
	 * @param	state
	 *			the new state.
	 */
	protected void yyPushLexerState (String state)
	{
		if ("INITIAL".equals (state))
		{
			yyPushLexerState (INITIAL);
			return;
		}
		throw new IllegalArgumentException ("Unknown lexer state: " + state);
	}

	/**
	 * Check if there are more inputs.  This function is called when EOF is
	 * encountered.
	 *
	 * @return	true to indicate no more inputs.
	 * @throws	IOException
	 * 			in case of an IO error
	 */
	protected boolean yyWrap () throws IOException
	{
		return true;
	}


/*
 * lexer properties:
 * unicode = false
 * bol = false
 * backup = true
 * cases = 29
 * table = compressed
 * ecs = 59
 * states = 163
 * max symbol value = 256
 *
 * memory usage:
 * full table = 41891
 * ecs table = 9874
 * next = 268
 * check = 268
 * default = 165
 * meta = 59
 * compressed table = 1017
 *
 * parser properties:
 * symbols = 31
 * max terminal = 279
 * used terminals = 25
 * non-terminals = 6
 * rules = 34
 * shift/reduce conflicts = 0
 * reduct/reduce conflicts = 0
 *
 * memory usage:
 * ecs table = 2047
 * compressed table = 1028
 */
}
