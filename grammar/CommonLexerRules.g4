lexer grammar CommonLexerRules;

// identities TODO: length that is greater than 64 is undefined
Id: [a-zA-Z] [_0-9a-zA-Z]*;

// arithmetic operators
Add: '+';
Sub: '-';
Mul: '*';
Div: '/';
Mod: '%';

// relational operators
Greater: '>';
Less: '<';
GreaterEqual: '>=';
LessEqual: '<=';
Equal: '==';
NotEqual: '!=';

// logic operators
RightShift: '>>';
LeftShift: '<<';
BitwiseAnd: '&';
BitwiseOr: '|';
BitwiseXor: '^';
BitwiseNot: '~';

// assignment operators
Assign: '=';

// increment or decrement operators
Increment: '++';
Decrement: '--';

// postfix operators
MemberAccess: '.';
Brackets: '[]';
LeftBracket: '[';
RightBracket: ']';
LeftParen: '(';
RightParen: ')';
LeftBrace: '{';
RightBrace: '}';

//TODO: for some special characters

// comments TODO: make sure that it should be skipped
LineComment: '//' .*? '\r'? '\n' -> skip;
BlockComment: '/*' .*? '*/' -> skip;

// keywords
Void: 'void';
Bool: 'bool';
Int: 'int';
String: 'string';
New: 'new';
Class: 'class';
Null: 'null';
True: 'true';
False: 'false';
This: 'this';
If: 'if';
Else: 'else';
For: 'for';
While: 'while';
Break: 'break';
Continue: 'continue';
Return: 'return';

// literals
IntegerLiteral: '0' | [1-9][0-9]*;
StringLiteral: '"' (Escape | .)*? '"';
Escape: '\\"' | '\\\\' | '\\n';