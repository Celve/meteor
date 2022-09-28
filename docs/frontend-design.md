The `meteor.g4` underlies the whole design.
I have no idea that why there is a distinct boundary between **concrete syntax tree** and **abstract syntax tree**.

No matter what name it should be, there is a hierarchy inside the tree.
Due to the introduction of the lambda expression, which enables the nested function, the hierarchy could be somewhat
obscure.
Therefore, I take out the lambda first, and introduce it later.

## Suite

The suite is the first layer, because it's a container holding different individual parts.

There are basically four suites inside the hierarchy: `ProgSuite`, `ClassSuite`, `FuncSuite`, and `SimpleSuite`.

Its order also implicates the order inside suites: `ProgSuite` could contain the next four, and `ClassSuite` could
contain the next three, etc..

Strictly speaking, `SimpleSuite` could not be regarded as a suite.
It's just a convenient representation of single statement in loops.
However, this is the only layer it fits in.

## Definition

The Mx* has two types of definition: `ClassDef` and `FuncDef`.
Similarly, `ClassDef` can contain the `FuncDef`, and the `FuncDef` cannot contain the `ClassDef`. They all contain
their own suite to indicate local scope.

## Block

The third layer is block. It has the ability to create a new scope, isolate control statements from the
outside.
In Mx*, `exit()` doesn't exist.

So many structures could be included in this layer: `ForBlock`, `WhileBlock`, `CondBlock`, and `FieldBlock`.
This time, the `ForBlock` can no longer contain `WhileBlock`.

The `FieldBlock` is a compromise, which represents `{ ... }`.

## Short & Declaration & Jump

These are all belong to the fourth layer. They are just one sentence, and they could not be nested.

For short, it only contains `Expr` because everything inside Mx* is expression. Short means short statement, by the way.

For declaration, it also only contains `VarDecl`, because `FuncDecl` is eliminated due to the forward reference.

For jump, it contains several jumps like: `Return`, `Break`, and `Continue`. 
