grammar MeteorParser;

import MeteorLexer;

// program
prog: stmt*;

// statements
stmt:
	jumpStmt
	| exprStmt
	| condStmt
	| forStmt
	| whileStmt
	| def
	| decl
	| funcSuite;

// declarations
decl: varDecl | funcDecl;
// varDecl: varType Id Assign expr ';'; funcDecl: returnType Id LeftParen paramDeclList ';';

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
initExpr:
	New varType ('[' expr? ']')* '[]'*; // it make sure that all brackets with int is in the head

// 7.3.3. built-in methods, or generally, method calls methodCall: (instName = Id) '.' (methodName =
// Id) paramInputList?;

// 7.3.4 multi-dimensional arrays

// 8. class, namely non-primitive type
nonPrimitiveType: Id;
classSuite: '{' (decl | classCtor)* '}';
classDef: Class className = Id classSuite;

// 8.3. access class members and call class methods (in 7.3.3.) memberAccess: className=Id '.'
// classMember=Id;

// 8.4 class constructor
classCtor: classId = Id paramDefList? '{' stmt* '}';

// 9. function
returnType: primitiveType | nonPrimitiveType | voidType;
funcSuite: '{' stmt* '}';
funcDef: returnType funcId = Id paramDefList funcSuite;
funcDecl: returnType funcId = Id paramDefList funcSuite;
funcCall: (funcId = Id) paramInputList;

// 9.1. function definition
paramDefList: '(' (varType Id (',' varType Id)*)? ')';
paramInputList: '(' (expr (',' expr)*)? ')';
// TODO: does it has only one way to locate params?

// 9.4. lambda
lambdaDef:
	'[' (op = '&')? ']' paramInputList '->' '{' stmt* '}';
lambdaCall: lambdaDef paramInputList;

// 10.1. basic expressions
basicExpr:
	IntegerLiteral
	| StringLiteral
	| Id
	| This
	| funcCall
	| initExpr
	| True
	| False
	| Null;

// 10.2. arithmetic expressions and assign expressions leftValue: Id | memberAccess | arrayAccess;
// TODO: the formal parameters of function, see in the doc
exprStmt: expr ';';
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
	| expr '.' (methodName = Id) paramInputList #methodAccess
	| expr '.' (classMember = Id) #memberAccess
	| expr ('[' expr ']')+ #arrayAccess
	| expr (Increment | Decrement) #suffixExpr
	| prefixOps expr #prefixExpr
	| expr op = (Mul | Div | Mod) expr #binaryExpr
	| expr op = (Add | Sub) expr #binaryExpr
	| expr op = (LeftShift | RightShift) expr #binaryExpr
	| expr op = (Less | LessEqual | Greater | GreaterEqual) expr #binaryExpr
	| expr op = (Equal | NotEqual) #binaryExpr
	| expr op = BitwiseAnd expr #binaryExpr
	| expr op = BitwiseXor expr #binaryExpr
	| expr op = BitwiseOr expr #binaryExpr
	| expr op = LogicalAnd expr #binaryExpr
	| expr op = LogicalOr expr #binaryExpr
	| expr Assign expr #assignExpr
	;

// 11.1. variable declarations
assignUnit: Id (Assign expr)?;
varDecl: varType assignUnit (',' assignUnit)* ';';

// 11.2. conditional stmtements
blockSuite: '{' stmt* '}' | stmt;
// TODO: detail the stmt
jumpStmt: (Return expr? | Break | Continue) ';';
condStmt: If '(' expr ')' blockSuite (Else blockSuite)?;

// 11.3. loops
whileStmt: While '(' expr ')' blockSuite;
forInitUnit: (varDecl | expr ';') | ';';
forCondUnit: expr? ';';
forStepUnit: expr?;
forStmt:
	For '(' forInitUnit forCondUnit forStepUnit ')' blockSuite;
