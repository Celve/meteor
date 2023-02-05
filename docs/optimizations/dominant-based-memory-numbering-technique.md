# Dominant Based Memory Numbering Technique

This is a technique to reduce the number of memory accesses in a program by eliminating useless load instructions and
getelementptr instructions.

## Introduction

It is based on the mechanism that Mx* provided, which ensures that all operations on pointers could be deduced because
there is no direct operations on pointers. Therefore, this technique trace the addresses, the values of the addresses,
the addresses of the addresses, and so on, to check whether the two values or addresses are the same.

The basic idea is, if there is a load after a load to the same address, and between those two the value of the address
is not changed, the second load could be replaced by the value of the first load. So the most important thing is to
determine it hasn't been modified between the two load instructions.

This target could be easily achieved by using the equal set. As long as the equal set is determined, we could infer what
values are possible to be equal. If there is a store on the possibly equal addresses, then the subsequent load cannot
be eliminated but to be retained. Otherwise, if there is no store on the possibly equal addresses, the subsequent load
should be eliminated. 

