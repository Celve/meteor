grammar CommonParserRules;
import CommonLexerRules;

// TODO: fulfill it
stmt: decl | def;

// declarations
decl: varDecl | funcDef;
// varDecl: varType Id Assign expr ';'; funcDecl: returnType Id LeftParen paramDeclList ';';

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
	(instName = Id) '.' (methodName = Id) paramInputList?;

// 7.3.4 multi-dimensional arrays

// 8. class, namely non-primitive type
nonPrimitiveType: Id;
classSuite: '{' (decl | classCtor)* '}';
classDef: Class classSuite;

// 8.3. access class members and call class methods (in 7.3.3.)
memberAccess: (className = Id) '.' (classMember = Id);

// 8.4 class constructor
classCtor: (classId = Id) paramDeclList? '{' stmt* '}';

// 9. function
returnType: primitiveType | nonPrimitiveType | voidType;
funcSuite: '{' stmt* '}';
funcDef: returnType (funcId = Id) paramDeclList? funcSuite;
funcCall: (funcId = Id) paramInputList?;

// 9.1. function definition
paramDeclList: '(' varType Id (',' varType Id)* ')';
paramInputList: '(' expr (',' expr)* ')';
// TODO: does it has only one way to locate params?

// 9.4. lambda
lambdaDef:
	'[' (op = '&')? ']' paramInputList? '->' '{' stmt* '}';
lambdaCall: lambdaDef paramInputList;

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

// 11.1. variable declarations
assignUnit: Id (Assign expr)?;
varDecl: varType Brackets* assignUnit (',' assignUnit)* ';';

// 11.2. conditional stmtements
blockSuite: '{' stmt* '}' | stmt; // TODO: detail the stmt
condStmt: If '(' expr ')' blockSuite (Else blockSuite)?;

// 11.3. loops
whileLoop: While '(' expr ')' blockSuite;
forInitUnit: (varDecl | expr ';') | ';';
forCondUnit: expr? ';';
forStepUnit: expr?;
forLoop:
	For '(' forInitUnit forCondUnit forStepUnit ')' blockSuite;

