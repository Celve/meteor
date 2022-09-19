grammar CommonParserRules;
import CommonLexerRules;

// TODO: fulfill it
stat: decl | def;

// declarations
decl: varDecl | funcDecl;
varDecl: varType Id Assign expr ';';
// funcDecl: returnType Id LeftParen paramDeclList ';';

// definitions
def: classDef;

// 7. type
varType: (primitiveType | nonPrimitiveType) Brackets+;

// 7.1. primitives
primitiveType: Bool | Int | String;
voidType: Void;

// 7.3.1. array type's declaration and access 
arrayAccess: Id ('[' expr ']')+;

// TODO: array objects support assign by reference

// 7.3.2 array creation 
initExpr: New varType ('[' expr? ']')*;

// 7.3.3. built-in methods, or generally, method calls
methodCall:
	(instName = Id) '.' (methodName = Id) '(' paramInputList? ')';

// 7.3.4 multi-dimensional arrays

// 8. class, namely non-primitive type
nonPrimitiveType: Id;
classDef: Class LeftBrace (decl | classCtor)* RightBrace;

// 8.3. access class members and call class methods (in 7.3.3.)
memberAccess: (className = Id) '.' (classMember = Id);

// 8.4 class constructor
classCtor: (classId = Id) '(' paramDeclList? ')' '{' stat* '}';

// 9. function
returnType: primitiveType | nonPrimitiveType | voidType;
funcDecl:
	returnType (funcId = Id) '(' paramDeclList? ')' '{' stat* '}';
funcCall: (funcId = Id) '(' paramInputList? ')';

// 9.1. function definition
paramDeclList: varType Id (',' varType Id)*;
paramInputList:
	expr (',' expr)*; // TODO: does it has only one way to locate params?

// 9.4. lambda
lambda:
	'[' (op = '&')? ']' '(' paramInputList? ')' '->' '{' stat* '}';
lambdaCall: lambda '(' paramInputList ')';

// 10.1. basic expressions
basicExpr:
	IntegerLiteral
	| StringLiteral
	| Id
	| This
	| funcCall
	| memberAccess
	| arrayAccess
	| initExpr;

// 10.2. arithmetic expressions and assign expressions
leftValue: Id | memberAccess | arrayAccess;
// TODO: the formal parameters of function, see in the doc

expr:
	'(' expr ')'
	| basicExpr
	| memberAccess
	| arrayAccess
	| methodCall
	| funcCall
	| expr (Increment | Decrement)
	| PrefixOps expr
	| expr op = (Mul | Div | Mod) expr
	| expr op = (Add | Sub) expr
	| expr op = (LeftShift | RightShift) expr
	| expr op = (Less | LessEqual | Greater | GreaterEqual) expr
	| expr op = (Equal | NotEqual) expr
	| expr BitwiseAnd expr
	| expr BitwiseNot expr
	| expr BitwiseOr expr
	| expr LogicalAnd expr
	| expr LogicalOr expr
	| leftValue Assign expr;