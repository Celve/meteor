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
--unroll # enable loop unrolling optimization
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

## Scripts

### test

```shell
./test [--result=<dir>] [--file=<file>] [--dir=<dir>] [buildOptions]
```

Use `--file` to specify the file that should be tested. And use `--dir` to specify the directory that contains the files
that should be tested.

When only the file is specified, the default directory is `codegen/`. When only the directory is specified, all files in
the directory will be tested. Either file or directory should be specified.

Besides, the directory specified in the option should be the relative path to the `src/main/resources` directory.

Use `--result` to specify the directory that contains the result files. The default directory is `result/` relative
to `debug/`. For example, with `--result=licm`, this script would generate two directory `debug/licm/code/`
and `debug/licm/benchmark/`, which contains the result files of code and benchmark respectively.

### analyze

```shell
./analyze licm adce
```

The `analyze` script would utilize the result of `test` script to analyze the performance of the optimizations.

For example, if there are two tests with `./test --licm ...` and `./test --adce ...`, then with `./analyze licm adce`
would use the result of LICM as the baseline to judge the ADCE's performance. 
