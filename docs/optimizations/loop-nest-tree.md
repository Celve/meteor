# Loop Nest Tree

Loop nest tree is a data structure that depicts loops.

## Construction

If we want to identify a loop inside the control flow graph, every time a back edge is found then a loop is found.

A back edge is an edge from a node to a node that dominates it. Once we found the back edge, the header block, which
must be the destination of the back edge, could also be found.

After that, a silly breadth-first search is used to find other blocks in the loop. The exiting blocks could be
identified by checking which blocks have the edge pointing to blocks that are not inside the loop.

A loop must be contained inside another loop, which means that it finally builds a tree. 
