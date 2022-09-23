grammar Meteor;

import Stardust;

// program
prog: suite;
suite: (stmt | def | block | decl | jump)*;

// block
block: for | while | cond | field;
field: '{' funcSuite '}';

// declarations
decl: varDecl | funcDecl;

// definitions
def: classDef | funcDef | lambdaDef;

// 7. type
varType: (primitiveType | nonPrimitiveType) Brackets*;

// 7.1. primitives
primitiveType: Bool | Int | String;
voidType: Void;

// 7.3.1. array type's declaration and access arrayAccess: expr ('[' expr ']')+;

// TODO: array objects support assign by reference

// 7.3.2 array creation 

// 7.3.3. built-in methods, or generally, method calls

// 7.3.4 multi-dimensional arrays

// 8. class, namely non-primitive type
nonPrimitiveType: Id;
classSuite: (decl | def | classCtor)*;
classDef: Class className = Id '{' classSuite '}';

// 8.3. access class members and call class methods (in 7.3.3.)

// 8.4 class constructor
classCtor: classId = Id paramDefList? '{' funcSuite '}';

// 9. function
returnType: primitiveType | nonPrimitiveType | voidType;
funcSuite: (stmt | block | decl | jump)*;
funcDef: returnType funcId = Id paramDefList '{' funcSuite '}';
funcDecl: returnType funcId = Id paramDefList '{' funcSuite '}';

// 9.1. function definition
paramDefList: '(' (varType Id (',' varType Id)*)? ')';
paramInputList: '(' (expr (',' expr)*)? ')';
// TODO: does it has only one way to locate params?

// 9.4. lambda
lambdaDef:
	'[' (op = '&')? ']' paramInputList '->' '{' stmt* '}';

// 10.1. basic expressions
basicExpr:
	IntegerLiteral
	| StringLiteral
	| Id
	| This
	| True
	| False
	| Null;

// 10.2. arithmetic expressions and assign expressions leftValue: Id | memberAccess | arrayAccess;
// TODO: the formal parameters of function, see in the doc
stmt: expr? ';';
prefixOps:
	Increment
	| Decrement
	| Add
	| Sub
	| LogicalNot
	| BitwiseNot;
// TODO: resolve some associativity
expr:
	'(' expr ')' #priorExpr
	| basicExpr #atom
	| New varType ('[' expr? ']')* '[]'* #initExpr // it make sure that all brackets with int is in the head
	| lambdaDef paramInputList #lambdaCall
  | (funcName = Id) paramInputList #funcCall
	| expr '.' (methodName = Id) paramInputList #methodAccess
	| expr '.' (classMember = Id) #memberAccess
	| expr ('[' expr ']')+ #arrayAccess
	| expr op = (Increment | Decrement) #suffixExpr
	| <assoc=right> prefixOps expr #prefixExpr
	| expr op = (Mul | Div | Mod) expr #binaryExpr
	| expr op = (Add | Sub) expr #binaryExpr
	| expr op = (LeftShift | RightShift) expr #binaryExpr
	| expr op = (Less | LessEqual | Greater | GreaterEqual) expr #binaryExpr
	| expr op = (Equal | NotEqual) expr #binaryExpr
	| expr op = BitwiseAnd expr #binaryExpr
	| expr op = BitwiseXor expr #binaryExpr
	| expr op = BitwiseOr expr #binaryExpr
	| expr op = LogicalAnd expr #binaryExpr
	| expr op = LogicalOr expr #binaryExpr
	| <assoc=right> expr Assign expr #assignExpr
	;

// 11.1. variable declarations
assignUnit: Id (Assign expr)?;
varDecl: varType assignUnit (',' assignUnit)* ';';

// 11.2. conditional stmtements
jump: (op = Return expr? | op = Break | op = Continue) ';';
extendedBlock:  stmt | jump | block;
// TODO: detail the stmt
cond: If '(' expr ')' extendedBlock (Else extendedBlock)?;

// 11.3. loops
while: While '(' expr ')' extendedBlock;
forInitUnit: (varDecl | expr ';') | ';';
forCondUnit: expr? ';';
forStepUnit: expr?;
for:
	For '(' forInitUnit forCondUnit forStepUnit ')' extendedBlock;
