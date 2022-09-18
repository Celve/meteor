grammar CommonParserRules;
import CommonLexerRules;

// TODO: fulfill it
stat: varDecl | arrayDecl;

// declarations
varDecl: varType Id Assign expr;
arrayDecl: varType Brackets Id Assign initExpr;

// type
varType: primitiveType | nonPrimitiveType;
primitiveType: Bool | Int | String;
nonPrimitiveType: Id;

// TODO: determine the mod precedence and determine whether there are other terminal nodes
expr:
	expr op = (Add | Sub) expr
	| expr op = (Mul | Div | Mod) expr
	| Id
	| Int
	| LeftParen expr RightParen;

// TODO: fulfill it
initExpr:;