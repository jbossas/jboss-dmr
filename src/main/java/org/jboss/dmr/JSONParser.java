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

@Deprecated
public abstract class JSONParser
{
	protected final static int OPEN_BRACE = 256;
	protected final static int CLOSE_BRACE = 257;
	protected final static int OPEN_BRACKET = 258;
	protected final static int CLOSE_BRACKET = 259;
	protected final static int COLON = 260;
	protected final static int COMMA = 261;
	protected final static int BIG = 262;
	protected final static int INTEGER = 263;
	protected final static int DECIMAL = 264;
	protected final static int UNDEFINED = 265;
	protected final static int TRUE = 266;
	protected final static int FALSE = 267;
	protected final static int ZERO_VAL = 268;
	protected final static int OCTAL_INT_VAL = 269;
	protected final static int HEX_INT_VAL = 270;
	protected final static int SIGNED_HEX_INT_VAL = 271;
	protected final static int DEC_INT_VAL = 272;
	protected final static int NAN_VAL = 273;
	protected final static int INF_VAL = 274;
	protected final static int DEC_VAL = 275;
	protected final static int STR_VAL = 276;

	protected final static int INITIAL = 0;

	// an internal class for lazy initiation
	private final static class cc_lexer
	{
		private static char[] accept = ("\000\027\026\027\005\027\015\021\006\027\027\003\004\027\027\027\027\027\027\001\002\030\000\025\000\015\000\000\000\015\016\000\000\000\000\000\000\000\000\000\000\024\017\000\022\011\000\000\000\000\000\020\000\000\000\000\000\014\007\000\024\000\000\010\000\000\000\000\000\012\013\023").toCharArray ();
		private static char[] ecs = ("\000\000\000\000\000\000\000\000\000\001\002\000\000\001\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\001\000\003\000\000\000\000\000\000\000\000\000\004\005\006\000\007\010\010\010\010\010\010\010\010\010\011\000\000\000\000\000\000\012\012\012\012\013\012\000\000\014\000\000\000\000\015\000\000\000\000\000\000\000\000\000\000\000\000\016\017\020\000\000\000\021\022\023\024\025\026\027\000\030\000\000\031\032\033\000\000\000\034\035\036\037\000\000\040\041\000\042\000\043\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\044").toCharArray ();
		private static char[] base = ("\065\000\003\000\000\047\000\014\000\002\005\000\000\014\022\037\026\023\076\000\000\000\000\000\000\073\000\000\002\074\005\006\107\121\110\115\110\104\112\105\027\000\000\115\000\000\116\112\123\120\125\000\036\120\122\130\127\000\000\152\000\130\140\000\135\125\133\131\125\000\000\000\167\000\171").toCharArray ();
		private static char[] next = ("\026\000\026\027\002\002\034\035\036\051\051\064\036\052\052\030\052\052\034\007\007\064\041\052\052\052\052\052\052\040\063\063\037\063\063\073\042\074\074\043\063\063\063\063\063\063\031\007\044\045\046\032\033\001\002\002\003\004\005\001\006\007\010\001\001\011\012\013\001\014\001\015\001\016\001\017\001\020\001\001\021\001\001\022\001\001\001\023\024\025\047\050\000\053\054\055\056\057\060\061\062\065\066\067\070\071\072\075\076\077\100\000\101\102\103\104\105\106\107\026\026\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000").toCharArray ();
		private static char[] check = ("\111\112\111\003\002\002\006\006\006\034\034\051\036\037\037\003\037\037\007\007\007\051\012\037\037\037\037\037\037\011\050\050\006\050\050\064\015\064\064\016\050\050\050\050\050\050\005\005\017\020\021\005\005\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\022\031\035\040\041\042\043\044\045\046\047\053\056\057\060\061\062\065\066\067\070\073\075\076\100\101\102\103\104\110\110\112\112\112\112\112\112\112\112\112\112\112\112\112\112\112\112\112\112\112\112\112\112").toCharArray ();
		private static char[] defaults = ("\112\112\001\110\112\001\001\001\112\001\001\112\112\001\001\001\001\001\001\112\112\112\003\112\111\006\011\012\001\006\035\001\001\001\001\001\001\001\001\001\001\034\037\001\112\112\001\001\001\001\001\050\001\001\001\001\001\112\112\064\073\001\001\112\001\001\001\001\001\112\112\112\112\112\112").toCharArray ();
		private static char[] meta = ("\000\000\001\002\000\000\000\000\000\000\000\000\000\000\000\002\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\003").toCharArray ();
	}

	// an internal class for lazy initiation
	private final static class cc_parser
	{
		private static char[] rule = ("\000\001\001\001\001\001\001\001\001\001\001\001\001\002\002\003\002\002\003\001\001\002\003\004\005").toCharArray ();
		private static char[] ecs = ("\000\001\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\002\003\004\005\006\007\000\000\000\010\011\012\013\014\015\016\017\020\021\022\023").toCharArray ();
		private static char[] base = ("\016\160\006\000\042\043\053\054\064\065\075\076\106\107\117\002\014\001\151\120\011\130\160\131\020\141\001\000\142\177\152\036\007\000\012\u0087\u0092\u009c\u0096\u009c\u009c\u009c\u009c\u009c\u009c\u009c\u009c\u009c\u009c\u009c\u009c\u009c\u009c\u009c\u009c\u009c\u009c\u009c\u009c\u009c\u0097\u009c\u009c\u0098\u009c\u009c\u009c\u009c\u009c\u0099\u009c\u009c").toCharArray ();
		private static char[] next = ("\uffec\000\uffff\uffec\036\uffec\027\uffec\030\000\uffe9\025\ufffe\uffe8\uffe9\033\001\uffe8\002\000\037\034\003\004\005\006\007\010\011\012\013\014\015\016\ufff5\ufff4\041\ufff5\ufff4\ufff5\ufff4\ufff5\ufff4\ufffd\ufff8\000\ufffd\ufff8\ufffd\ufff8\ufffd\ufff8\ufff7\ufff6\000\ufff7\ufff6\ufff7\ufff6\ufff7\ufff6\ufff9\ufffb\000\ufff9\ufffb\ufff9\ufffb\ufff9\ufffb\ufffa\ufffc\000\ufffa\ufffc\ufffa\ufffc\ufffa\ufffc\uffed\ufff0\000\uffed\ufff0\uffed\ufff0\uffed\ufff0\ufff3\ufff2\000\ufff3\ufff2\ufff3\ufff2\ufff3\ufff2\uffef\ufff1\000\uffef\ufff1\uffef\ufff1\uffef\ufff1\uffee\000\031\uffee\000\uffee\032\uffee\000\023\000\uffeb\000\uffeb\000\000\000\000\000\000\000\000\000\000\000\024\uffea\000\uffea\000\000\000\000\000\000\000\000\000\000\000\000\017\020\021\022\000\026\035\040\042\000\000\000\000\000").toCharArray ();
		private static char[] check = ("\003\021\017\003\032\003\021\003\021\024\040\002\020\042\040\024\000\042\000\003\032\030\000\000\000\000\000\000\000\000\000\000\000\000\004\005\037\004\005\004\005\004\005\006\007\043\006\007\006\007\006\007\010\011\043\010\011\010\011\010\011\012\013\043\012\013\012\013\012\013\014\015\043\014\015\014\015\014\015\016\023\043\016\023\016\023\016\023\025\027\043\025\027\025\027\025\027\031\034\043\031\034\031\034\031\034\036\043\022\036\022\036\022\036\001\001\001\026\043\026\001\001\001\001\001\001\001\001\001\001\001\001\035\043\035\043\043\043\043\043\043\043\043\043\043\043\043\044\044\044\044\046\046\074\077\105\107\107\107\107\107").toCharArray ();
		private static char[] defaults = ("\043\000\000\001\003\003\003\003\003\003\003\003\003\003\003\043\017\017\021\003\017\003\021\003\000\003\001\000\003\021\003\024\022\000\022\043").toCharArray ();
		private static char[] gotoDefault = ("\107\107\044\107\107\107\107\107\107\107\107\107\107\107\107\107\107\107\107\107\107\107\107\107\046\107\107\046\107\107\107\107\107\046\107\107").toCharArray ();
		private static char[] lhs = ("\000\024\025\026\026\026\026\026\026\026\026\026\026\026\026\026\026\026\026\026\026\027\027\030\030").toCharArray ();
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
						if (cc_toState >= 72)
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
							if (cc_toState >= 72)
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
				case 28: break;
				case 2:	// \}
				{
					_yyValue = m_this.parseToken (); return CLOSE_BRACE;
				}
				case 29: break;
				case 3:	// \[
				{
					_yyValue = m_this.parseToken (); return OPEN_BRACKET;
				}
				case 30: break;
				case 4:	// \]
				{
					_yyValue = m_this.parseToken (); return CLOSE_BRACKET;
				}
				case 31: break;
				case 5:	// ,
				{
					_yyValue = m_this.parseToken (); return COMMA;
				}
				case 32: break;
				case 6:	// :
				{
					_yyValue = m_this.parseToken (); return COLON;
				}
				case 33: break;
				case 7:	// true
				{
					_yyValue = m_this.parseToken (); return TRUE;
				}
				case 34: break;
				case 8:	// false
				{
					_yyValue = m_this.parseToken (); return FALSE;
				}
				case 35: break;
				case 9:	// big
				{
					_yyValue = m_this.parseToken (); return BIG;
				}
				case 36: break;
				case 10:	// decimal
				{
					_yyValue = m_this.parseToken (); return DECIMAL;
				}
				case 37: break;
				case 11:	// integer
				{
					_yyValue = m_this.parseToken (); return INTEGER;
				}
				case 38: break;
				case 12:	// null
				{
					_yyValue = m_this.parseToken (); return UNDEFINED;
				}
				case 39: break;
				case 13:	// [+-]?0+
				{
					_yyValue = m_this.parsePlainValue (); return ZERO_VAL;
				}
				case 40: break;
				case 14:	// [+-]?0[0-9]+
				{
					_yyValue = m_this.parsePlainValue (); return OCTAL_INT_VAL;
				}
				case 41: break;
				case 15:	// 0x[0-9a-fA-F]+
				{
					_yyValue = m_this.parsePlainValue (); return HEX_INT_VAL;
				}
				case 42: break;
				case 16:	// [+-]0x[0-9a-fA-F]+
				{
					_yyValue = m_this.parsePlainValue (); return SIGNED_HEX_INT_VAL;
				}
				case 43: break;
				case 17:	// [+-]?[1-9][0-9]*
				{
					_yyValue = m_this.parsePlainValue (); return DEC_INT_VAL;
				}
				case 44: break;
				case 18:	// [+-]?NaN
				{
					_yyValue = m_this.parsePlainValue (); return NAN_VAL;
				}
				case 45: break;
				case 19:	// [+-]?Infinity
				{
					_yyValue = m_this.parsePlainValue (); return INF_VAL;
				}
				case 46: break;
				case 20:	// [+-]?([0-9]+\.[0-9]+([eE][+-]?[0-9]+)?)
				{
					_yyValue = m_this.parsePlainValue (); return DEC_VAL;
				}
				case 47: break;
				case 21:	// \"([^"\\]+|\\.)*\"
				{
					_yyValue = m_this.parseStringValue (); return STR_VAL;
				}
				case 48: break;
				case 22:	// [ \t\r\n]+
				{
					m_this.ignored ();
				}
				case 49: break;
				case 23:	// .
				{
					m_this.invalid ();
				}
				case 50: break;
				case 24:	// <<EOF>>
				{
					m_this.parseEOF (); return 0;
				}
				case 51: break;
				case 25:	// .|\n
				{
					echo ();			// default character action
				}
				case 52: break;
				case 26:	// <<EOF>>
				{
					return 0;			// default EOF action
				}
				case 53: break;
				default:
					throw new IOException ("Internal error in JSONParser lexer.");
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
				if (cc_toState >= 35)
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
				cc_toState = cc_fromState + 36;
				int cc_tmpCh = cc_lhs[cc_ruleState] - 20;
				while (cc_check[cc_tmpCh + cc_base[cc_toState]] != cc_toState)
					cc_toState = cc_gotoDefault[cc_toState - 36];
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
				case 27: break;
				case 3:	// node : ZERO_VAL
				{
					_yyValue = m_this.parseZero ();
				}
				case 28: break;
				case 4:	// node : DEC_VAL
				{
					_yyValue = m_this.parseBigDecimal ((java.lang.String)yyGetValue (1));
				}
				case 29: break;
				case 5:	// node : NAN_VAL
				{
					_yyValue = m_this.parseNaN ();
				}
				case 30: break;
				case 6:	// node : INF_VAL
				{
					_yyValue = m_this.parseInf ((java.lang.String)yyGetValue (1));
				}
				case 31: break;
				case 7:	// node : DEC_INT_VAL
				{
					_yyValue = m_this.parseDecInt ((java.lang.String)yyGetValue (1));
				}
				case 32: break;
				case 8:	// node : OCTAL_INT_VAL
				{
					_yyValue = m_this.parseOctal ((java.lang.String)yyGetValue (1));
				}
				case 33: break;
				case 9:	// node : HEX_INT_VAL
				{
					_yyValue = m_this.parseHex ((java.lang.String)yyGetValue (1));
				}
				case 34: break;
				case 10:	// node : SIGNED_HEX_INT_VAL
				{
					_yyValue = m_this.parseHexSigned ((java.lang.String)yyGetValue (1));
				}
				case 35: break;
				case 11:	// node : TRUE
				{
					_yyValue = m_this.parseTrue ();
				}
				case 36: break;
				case 12:	// node : FALSE
				{
					_yyValue = m_this.parseFalse ();
				}
				case 37: break;
				case 13:	// node : OPEN_BRACKET CLOSE_BRACKET
				{
					_yyValue = m_this.parseEmptyList ();
				}
				case 38: break;
				case 14:	// node : list CLOSE_BRACKET
				{
					_yyValue = m_this.finishList ((org.jboss.dmr.ModelNode)yyGetValue (1));
				}
				case 39: break;
				case 15:	// node : list COMMA CLOSE_BRACKET
				{
					_yyValue = m_this.finishList ((org.jboss.dmr.ModelNode)yyGetValue (1));
				}
				case 40: break;
				case 16:	// node : OPEN_BRACE CLOSE_BRACE
				{
					_yyValue = m_this.parseEmptyObject ();
				}
				case 41: break;
				case 17:	// node : object CLOSE_BRACE
				{
					_yyValue = m_this.finishObject ((org.jboss.dmr.ModelNode)yyGetValue (1));
				}
				case 42: break;
				case 18:	// node : object COMMA CLOSE_BRACE
				{
					_yyValue = m_this.finishObject ((org.jboss.dmr.ModelNode)yyGetValue (1));
				}
				case 43: break;
				case 19:	// node : STR_VAL
				{
					_yyValue = m_this.parseString ((java.lang.String)yyGetValue (1));
				}
				case 44: break;
				case 20:	// node : UNDEFINED
				{
					_yyValue = m_this.parseUndefined ();
				}
				case 45: break;
				case 21:	// list : OPEN_BRACKET node
				{
					_yyValue = m_this.parseStartList ((org.jboss.dmr.ModelNode)yyGetValue (2));
				}
				case 46: break;
				case 22:	// list : list COMMA node
				{
					_yyValue = m_this.parseListItem ((org.jboss.dmr.ModelNode)yyGetValue (1), (org.jboss.dmr.ModelNode)yyGetValue (3));
				}
				case 47: break;
				case 23:	// object : OPEN_BRACE STR_VAL COLON node
				{
					_yyValue = m_this.parseStartObject ((java.lang.String)yyGetValue (2), (org.jboss.dmr.ModelNode)yyGetValue (4));
				}
				case 48: break;
				case 24:	// object : object COMMA STR_VAL COLON node
				{
					_yyValue = m_this.parseObjectItem ((org.jboss.dmr.ModelNode)yyGetValue (1), (java.lang.String)yyGetValue (3), (org.jboss.dmr.ModelNode)yyGetValue (5));
				}
				case 49: break;
				default:
					throw new IOException ("Internal error in JSONParser parser.");
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




	private final org.jboss.dmr.JSONParserImpl m_this = (org.jboss.dmr.JSONParserImpl)this;

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
 * cases = 26
 * table = compressed
 * ecs = 37
 * states = 72
 * max symbol value = 256
 *
 * memory usage:
 * full table = 18504
 * ecs table = 2921
 * next = 143
 * check = 143
 * default = 75
 * meta = 37
 * compressed table = 655
 *
 * parser properties:
 * symbols = 25
 * max terminal = 276
 * used terminals = 20
 * non-terminals = 5
 * rules = 24
 * shift/reduce conflicts = 0
 * reduct/reduce conflicts = 0
 *
 * memory usage:
 * ecs table = 1152
 * compressed table = 743
 */
}
