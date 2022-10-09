## Basic Structure

```mermaid
graph TD
A[Value]
B[User]
C[Argument]
D[BasicBlock]
E[Instruction]
F[Constant]
G[ConstantData]
H[BasicBlockAddr]
I[GlobalValue]
J[Function]
K[GlobalVariable]
L[Module]
M[Use]
N[Type]
A --> B
A --> C
A --> D
B --> E
B --> F
F --> G 
F --> H
F --> I
I --> J
I --> K
B --> M
L -->> J
L -->> K
L -->> G
```

