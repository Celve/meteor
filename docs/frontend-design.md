The `meteor.g4` underlies the whole design.
I have no idea that why there is a distinct boundary between **concrete syntax tree** and **abstract syntax tree**.

No matter what name it should be, there is a hierarchy inside the tree.
Due to the introduction of the lambda expression, which enables the nested function, the hierarchy could be somewhat
obscure.
Therefore, I take out the lambda first, and introduce it later.

## Structure

### Block

The suite is the first layer, because it's a container holding different individual parts.

There are basically four suites inside the hierarchy: `ProgBlock`, `ClassBlock`, `FuncBlock`, and `SimpleBlock`.

Its order also implicates the order inside suites: `ProgBlock` could contain the next four, and `ClassBlock` could
contain the next three, etc..

Strictly speaking, `SimpleBlock` could not be regarded as a block.
It's just a convenient representation of single statement in loops.
However, this is the only layer it fits in, because it creates a new scope.

The most important feature of block layer is that every block creates a new scope.
For example, `ProgBlock` creates a global scope, `ClassBlock` creates a class scope, and `FuncBlock` creates a function
scope.
There are a few tiny difference between these scopes, which would be mentioned later in [Scope](#Scope).

The basic idea of block is that it contains several parts that make up the whole,
which fortunately has a corresponding idea in LLVM IR called `BasicBlock`.
In a unit that makes up a block, there should be only some kinds of statements.
For example, a `FuncBlock` should not include class definition,
while a `SimpleBlock` could contain only one statement.
The constraints here are the statements it could contain,
while in the LLVM IR is that it should and only could have one terminator at the end.

### Definition

The Mx* has two types of definition: `ClassDef` and `FuncDef`.
Similarly, `ClassDef` can contain the `FuncDef`, and the `FuncDef` cannot contain the `ClassDef`.
They all contain their own suite to indicate local scope, which is actually indicated by the `Block` layer, because two
suites both contain a block.

### Suite

The third layer is suite. It has the ability to create a new scope, isolate control statements from the outside.
The number of scope it should create is determined by the number of blocks it owns.
Because suite and block always appear together, it's acceptable to say that both suite and block invoke a new scope.

Both the definition and the suite would contain at least one block, therefore the two layers are somewhat close enough.
The reason to distinguish them is that classes and functions are really significant things in programming language.

So many structures could be included in this layer: `ForBlock`, `WhileBlock`, `CondBlock`, and `FieldBlock`.
This time, the `ForBlock` can no longer contain `WhileBlock`.
The hierarchy inside the layer is obscure.

The `FieldBlock` is a compromise, which represents `{ ... }`.

### Short & Declaration & Jump

These are all belong to the fourth layer. They are just one sentence, and they could not be nested.

For `Short`, it only contains `Expr` because everything inside Mx* is expression. Short means short statement, by the
way.
`Expr` consists of various expressions, such as binary expressions `a + b`, prefix expressions `++a`, and so on.

For `Decl`, it also only contains `VarDecl`, because `FuncDecl` is eliminated due to the forward reference.
`VarDecl` undoubtedly update key-value pair in scope. It's the only way to create a new variable.

For `Jump`, it contains several jumps like: `Return`, `Break`, and `Continue`.
The reason to distinguish them with short is that they present the control flow in the program.
A for loop might end because of the presentation of `Break`.
In order to churn out IR code more easily, it's necessary to distinguish them.

## Scope

There are mainly six kinds of scope: `GlobalScope`, `ClassScope`, `FuncScope`, `LoopScope`, `CondScope`, and `InitScope`
.
The first three are the most important ones, and the last three are similar ones, which are completely the same as
function scope.
They are specified for the convenience of IR code generation (`InitScope`) and in-class function judgement (`LoopScope`
and `CondScope`).
But after finishing the task, I reckon that this is only my wishful thinking, because class and function need much more
information than the scope could provide.

`GlobalScope` can register classes and functions, and it's the only scope that can register classes.
`ClassScope` can register functions, and it's the only two scope that can register functions.
`FuncScope` can register variables, every scope could register variable, that's why the latter two are the same, except
for their name. 