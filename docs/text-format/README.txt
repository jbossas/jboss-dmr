#
# The following ENBF grammar defines JBoss DMR 'Text' format.
# index.html file contains visual representation of this grammar.
#

value      ::= list | object | property | string | number | bytes | expression | type | boolean | 'undefined'
list       ::= '[' ']' | '[' ( value ',' )* value ( ',' )? ']'
object     ::= '{' '}' | '{' ( string '=>' value ',' )* string '=>' value ( ',' )? '}'
property   ::= '(' string '=>' value ')'
string     ::= '"' ( [^"\] | '\' '"' | '\' '\' | '\' 'u' hexdigit hexdigit hexdigit hexdigit ) * '"'
number     ::= int | long | double | biginteger | bigdecimal
expression ::= 'expression' string
bytes      ::= 'bytes' ( '{' '}' | '{' ( ( decbyte | hexbyte ) ',' )* ( decbyte | hexbyte ) ( ',' )? '}' )
boolean    ::= 'false' | 'true'
type       ::= 'BIG_DECIMAL' | 'BIG_INTEGER' | 'BOOLEAN' | 'BYTES' | 'DOUBLE' | 'EXPRESSION' | 'LIST' | 'LONG' | 'INT' | 'OBJECT' | 'PROPERTY' | 'STRING'
int        ::= decinteger | hexinteger
long       ::= int 'L'
double     ::= [+-]? ( "Infinity" | "NaN" ) | ( decinteger "." ( decdigit )* ( exponent )? )  | [+-]? ( ( "." ( decdigit )* exponent )  | ( "." ( decdigit )+ (exponent)? ) | ( "." ( decdigit )+ exponent ) )
biginteger ::= "big" "integer" decinteger
bigdecimal ::= "big" "decimal" decinteger "." decdigit* exponent?
decinteger ::= [+-]? ( '0' | [1-9] decdigit* )
hexinteger ::= '0x' hexdigit+
decbyte    ::= [+-]? ( '1' [0-2] [0-8] | [1-9] [0-9] | [0-9] )
hexbyte    ::= '0x' hexdigit hexdigit?
decdigit   ::= [0-9]
hexdigit   ::= [0-9a-fA-F]
exponent   ::= ('e' | 'E') [+-]? decdigit+

