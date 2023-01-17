# Equal Set

Equal set is an enhancement on the value number. While the value number focus on the equality of values, the equal set
focus on the possibility of equality of values. If two values are inside the same equal set, they are possible to be
equal. If two values are not inside the same equal set, they are impossible to be equal.

## Construction

The construction of equal set is a kind of induction. Suppose we already have a set of equal sets, we could expand the
equal set according to the instructions that use them. The expansion is based on the following rules:

1. If two load instructions load the addresses that are inside the same equal set, then the two values loaded are
   probably the same.
2. If two getelementptr instructions get the addresses that are inside the same equal set, then the two values
   calculated are probably the same.
3. If two store instructions store the addresses that are inside the same equal set, then the two values stored are
   probably the same.

And we could build the original equal sets by considering mv instructions and phi instructions. We repeat the given
rules until it reaches a fix point.

