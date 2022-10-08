grammar Meteor;

import Stardust;

// program
prog: progBlock;
progBlock: (short | def | suite | decl | jump)*;

// suite
suite: forSuite | whileSuite | condSuite | fieldSuite;
fieldSuite: '{' funcBlock '}';

// declarations
decl: varDecl;

// definitions
def: classDef | funcDef;

// 7. type
varType: classType ('[' ']')*;
classType: (primitiveType | nonPrimitiveType);

// 7.1. primitives
primitiveType: Bool | Int | String;
voidType: Void;

// 7.3.1. array type's declaration and access (in 11.1.)

// 7.3.2 array creation

// 7.3.3. built-in methods, or generally, method calls (in 10.2.)

// 7.3.4 multi-dimensional arrays (in 11.1.)

// 8. class, namely non-primitive type
nonPrimitiveType: Id;
classBlock: (decl | funcDef | classCtor)*;
classDef: Class className = Id '{' classBlock '}' ';';

// 8.3. access class members and call class methods (in 7.3.3.)

// 8.4 class constructor
classCtor: classId = Id paramDeclList? '{' funcBlock '}';

// 9. function
returnType: varType | voidType;
funcBlock: (short | suite | decl | jump)*;
funcDef: returnType funcName = Id paramDeclList '{' funcBlock '}';

// 9.1. function definition
paramDecl: varType Id;
paramDeclList: '(' (paramDecl(',' paramDecl)*)? ')';
paramInputList: '(' (expr (',' expr)*)? ')';

// 9.4. lambda
lambdaDef: '[' (op = '&')? ']' paramDeclList '->' '{' funcBlock '}';

// 10.1. basic expressions
atom:
	IntegerLiteral
	| StringLiteral
	| Id
	| This
	| True
	| False
	| Null;

// 10.2. arithmetic expressions and assign expressions leftValue: Id | memberAccess | arrayAccess;
short: expr? ';';
bracketedExpr: '[' expr? ']';
lambdaCall: lambdaDef paramInputList;
funcCall: (funcName = Id) paramInputList;
priorExpr: '(' expr ')';
primaryExpr: priorExpr | atom | funcCall | lambdaCall;
suffixExpr: suffixExpr '.' (methodName = Id) paramInputList #methodCall
  | suffixExpr '.' (memberName = Id) #memberAccess
  | suffixExpr '[' expr ']' #arrayAccess
  | suffixExpr op = (Increment | Decrement) #suffixIncrement
  | primaryExpr #primaryExprRelay
  ;
prefixExpr: op = (Increment | Decrement | Add | Sub | LogicalNot | BitwiseNot) prefixExpr #prefixIncrement
  | New classType bracketedExpr* ('(' ')')? #initExpr
  | suffixExpr #suffixExprRelay
  ;
mulExpr: prefixExpr (op = (Mul | Div | Mod) prefixExpr)*;
addExpr: mulExpr (op = (Add | Sub) mulExpr)*;
shiftExpr: addExpr (op = (LeftShift | RightShift) addExpr)*;
cmpExpr: shiftExpr (op = (Less | LessEqual | Greater | GreaterEqual) shiftExpr)*;
equalExpr: cmpExpr (op = (Equal | NotEqual) cmpExpr)*;
bitwiseAndExpr: equalExpr (op = BitwiseAnd equalExpr)*;
bitwiseXorExpr: bitwiseAndExpr (op = BitwiseXor bitwiseAndExpr)*;
bitwiseOrExpr: bitwiseXorExpr (op = BitwiseOr bitwiseXorExpr)*;
logicalAndExpr:  bitwiseOrExpr (op = LogicalAnd bitwiseOrExpr)*;
logicalOrExpr: logicalAndExpr (op = LogicalOr logicalAndExpr)*;
assignExpr: logicalOrExpr (op = Assign assignExpr)?;
expr: assignExpr;

//
//expr:
//	'(' expr ')' #priorExpr
//	| basicExpr #atom
//	| New classType bracketedExpr* ('(' ')')? #initExpr // constructor with parameters is undefined behavior
//	| lambdaDef paramInputList #lambdaCall
//  | (funcName = Id) paramInputList #funcCall
//	| expr '.' (methodName = Id) paramInputList #methodAccess
//	| expr '.' (classMember = Id) #memberAccess
//	| expr '[' expr ']' #arrayAccess
//	| expr op = (Increment | Decrement) #suffixExpr
//	| <assoc=right> prefixOps expr #prefixExpr
//	| expr op = (Mul | Div | Mod) expr #binaryExpr
//	| expr op = (Add | Sub) expr #binaryExpr
//	| expr op = (LeftShift | RightShift) expr #binaryExpr
//	| expr op = (Less | LessEqual | Greater | GreaterEqual) expr #binaryExpr
//	| expr op = (Equal | NotEqual) expr #binaryExpr
//	| expr op = BitwiseAnd expr #binaryExpr
//	| expr op = BitwiseXor expr #binaryExpr
//	| expr op = BitwiseOr expr #binaryExpr
//	| expr op = LogicalAnd expr #binaryExpr
//	| expr op = LogicalOr expr #binaryExpr
//	| <assoc=right> expr Assign expr #assignExpr // left value cannot be judged here, due to indirect left recursion
//	;

// 11.1. variable declarations
assignUnit: Id (Assign expr)?;
varDecl: varType assignUnit (',' assignUnit)* ';';

// 11.2. conditional stmtements
jump: (op = Return expr? | op = Break | op = Continue) ';';
simpleBlock: short | jump | decl;
extendedSuite: simpleBlock | suite;
condSuite: If '(' expr ')' extendedSuite (Else extendedSuite)?;

// 11.3. loops
whileSuite: While '(' expr ')' extendedSuite;
forInitUnit: (varDecl | expr ';') | ';';
forCondUnit: expr? ';';
forStepUnit: expr?;
forSuite: For '(' forInitUnit forCondUnit forStepUnit ')' extendedSuite;