# Loop Invariant Code Motion

Loop invariant code motion is an algorithm that recognizes invariants inside a loop and moves those out.

## Recognizing Invariants

It recognizes invariants with the following rules for all instructions inside the loop:

1. If it's an arithmetic instruction and both operands are invariants, then it's an invariant.
2. If it's a compare instruction and both operands are invariants, then it's an invariant.
3. If it's a getelementptr instruction and all operands are invariants, then it's an invariant.
4. If it's a load instruction and the address is an invariant, this condition is not enough. We should check whether the
   value in the address is modified in the loop. If it's not modified, then it's an invariant. This condition is
   achieved with the help of equal set.

## Moving Invariants

This step is quite easy. Just create a pre-header before the loop and move all invariants to the pre-header.