# 🌟Meteor

A compiler that convert instructions from Mx* language into rv32i assembly.

## Usage

### Jar

```bash
gradle shadowJar
java -jar build/libs/meteor-all.jar <buildOptions>
```

### Gradle

```bash
gradle run --args="<buildOptions>"
```

### Build Options

The following segments are valid build options:

```shell
--asm # generate assembly code
--ir # generate llvm ir code
--custom # generate llvm ir code with custom instructions

--inline # enable inline optimization
--svn # enable super value numbering optimization
--sccp # enable sparse conditional constant propagation optimization
--adce # enable aggressive dead code elimination optimization
--licm # enable loop invariant code motion optimization
--dvnt # enable dominant based value numbering optimization
--localize # enable localizing global variables optimization
--peephole # enable peephole optimization
--gcp # enable global code placement optimization
--dmnt # enable dominator based memory numbering optimization
--sr # enable strength reduction optimization
--reorder # enable operand reordering optimization
```

### Demo

For Jar:

```bash
gradle shadowJar
java -jar build/libs/meteor-all.jar --ir --inline --adce # print ir code, and enable inline and aggressive dead code elimination optimization
```

For Gradle:

```bash
gradle run --args="--asm --sccp" # print asm code, and enable sparse conditional constant propagation optimization
```