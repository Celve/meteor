lexer grammar CommonLexerRules;


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

// logical operators
LogicalAnd: '&&';
LogicalOr: '||';
LogicalNot: '!';

// bit operators
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

// postfix operators MemberAccess: '.'; TODO: to determine whether it maintain it
Brackets: '[]';
LeftBracket: '[';
RightBracket: ']';
LeftParen: '(';
RightParen: ')';
LeftBrace: '{';
RightBrace: '}';

//TODO: for some special characters 

//Semicolon: ';'; I don't want to include semicolon now, because it's meaningless, but have to be

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

// omission
WhiteSpace: [ \t]+ -> skip;
NewLine: ('\r\n' | '\r' | '\n') -> skip;

// extra
Access: '.';

// identities TODO: length that is greater than 64 is undefined
Id: [a-zA-Z] [_0-9a-zA-Z]*; // Id should be put as low as it could, because it's the final choice
