#
# The following ENBF grammar defines JBoss DMR 'JSON' format.
# index.html file contains visual representation of this grammar.
#

value      ::= list | object | property | string | number | bytes | expression | type | boolean | 'null'
list       ::= '[' ']' | '[' ( value ',' )* value ']'
object     ::= '{' '}' | '{' ( string '=>' value ',' )* string '=>' value '}'
property   ::= '{' string '=>' value '}'
string     ::= '"' ( [^"\] | '\' '"' | '\' '\' | '\' 'b' | '\' 'f' | '\' 't' | '\' 'r' | '\' 'n' | '\' 'u' hexdigit hexdigit hexdigit hexdigit ) * '"'
number     ::= int | double
expression ::= '{' '"EXPRESSION_VALUE"' ':' string '}'
bytes      ::= '{' '"BYTES_VALUE"' ':' base64string '}'
boolean    ::= 'false' | 'true'
type       ::= '{' '"TYPE_MODEL_VALUE"' ':' ( '"BIG_DECIMAL"' | '"BIG_INTEGER"' | '"BOOLEAN"' | '"BYTES"' | '"DOUBLE"' | '"EXPRESSION"' | '"LIST"' | '"LONG"' | '"INT"' | '"OBJECT"' | '"PROPERTY"' | '"STRING"' | '"TYPE"' | '"UNDEFINED"' ) '}'
int        ::= octinteger | decinteger | hexinteger
double     ::= [+-]? ( "Infinity" | "NaN" ) | ( decinteger "." ( decdigit )* ( exponent )? )  | [+-]? ( ( "." ( decdigit )* exponent )  | ( "." ( decdigit )+ (exponent)? ) | ( "." ( decdigit )+ exponent ) )
octinteger ::= [+-]? '0' octdigit+
decinteger ::= [+-]? ( '0' | [1-9] decdigit* )
hexinteger ::= [+-]? '0x' hexdigit+
base64string  ::= '"' ( base64quartet | base64newline )* base64padding? base64newline* '"'
base64quartet ::= base64char base64char base64char base64char
base64padding ::= base64char base64char ( base64char base64char | base64char '=' | '==' )
base64char    ::= [a-zA-Z0-9] | '+' | '/'
base64newline ::= '\r' | '\n'
octdigit      ::= [0-7]
decdigit      ::= [0-9]
hexdigit      ::= [0-9a-fA-F]
exponent      ::= ('e' | 'E') [+-]? decdigit+

