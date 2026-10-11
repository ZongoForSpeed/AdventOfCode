# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Java solutions to [Advent of Code](https://adventofcode.com) puzzles, 2015–2025. It's a multi-module Maven build targeting **Java 25** (`maven.compiler.release=25`). There is no Maven wrapper, and `mvn` may not be on the shell PATH (the IntelliJ-bundled Maven may be the one in use).

## Commands

```bash
mvn clean install                                   # build everything and run all tests
mvn -pl year2025 -am test                           # one year (plus the modules it depends on)
mvn -pl year2025 -am test -Dtest=Day07Test -Dsurefire.failIfNoSpecifiedTests=false
mvn -pl year2025 -am test -Dtest='Day07Test#inputPartTwo' -Dsurefire.failIfNoSpecifiedTests=false
mvn -P fixErrorProne compile                        # apply Error Prone suggested fixes in place
mvn rewrite:run                                     # OpenRewrite JavaBestPractices recipe
```

## Validating changes

**Always finish a change with a full `mvn clean install` from the repo root**, even if the change only touches one module, and only report the work as done once it ends in `BUILD SUCCESS`. Year modules depend on `common`, so a change there can break any year. Error Prone also runs at compile time, so a single-module or IDE build isn't enough. If the build fails, report the failing modules and tests; never skip tests (`-DskipTests`) or disable them to get a green build.

`mvn` is usually not on the shell PATH. Use IntelliJ's bundled Maven with the local JDK:

```bash
export JAVA_HOME=/Users/zongo/Library/Java/JavaVirtualMachines/openjdk-26.0.1/Contents/Home
"/Users/zongo/Applications/IntelliJ IDEA.app/Contents/plugins/maven-plugin/lib/maven3/bin/mvn" clean install
```

The full build takes about 2–3 minutes. Run it in the background and filter the log for `Tests run:`, `<<< FAILURE`, `COMPILATION ERROR` and the reactor summary.

## Static analysis is part of compilation

The `errorProne` profile is **active by default**. It runs Error Prone and NullAway (JSpecify mode, `AnnotatedPackages=com.adventofcode`), and most checks are set to `ERROR`, so violations fail the build. Some checks that commonly break new code: `UnusedVariable`, `UnusedMethod`, `MethodCanBeStatic`, `FieldCanBeFinal`, `PrivateConstructorForUtilityClass`, `RemoveUnusedImports`, `WildcardImport`, `UnnecessaryParentheses`, `ReferenceEquality`, `MissingOverride`, and `OperatorPrecedence`. `.mvn/jvm.config` holds the `--add-exports`/`--add-opens` flags that Error Prone needs.

Every package has a `package-info.java` annotated with `@NullMarked` (JSpecify) and `@CheckReturnValue` (Error Prone). That means:
- Anything that can be null must be marked `@Nullable`.
- Ignoring a return value is a compile error unless the method is annotated `@CanIgnoreReturnValue`.
- A new package needs its own `package-info.java` with the same annotations.

## Architecture

- **`common`**: shared algorithms and data structures used by the year modules. Packages: `graph` (AStar, Dijkstra, DisjointSet, Clique, Connectivity), `maths` (primes, GaussJordan, permutations/combinations), `matrix`, `memory` (Intcode-style memory), `point` (Point2D/3D/4D, Direction, `map` grids such as `CharMap`/`IntegerMap`/`InfiniteBooleanMap`, `range` types such as `Range`/`Rectangle`/`Cuboid`), and `utils`. Check here before writing a new grid, pathfinding, or math helper. JMH benchmarks are in `common/src/test` (e.g. `PermutationsBenchmark`).
- **`common-test`**: provides `AbstractTest`. Its constructor takes `(year, day)`, and it defines two `@Test` methods, `inputPartOne` and `inputPartTwo`. Each one opens the classpath resource `/<year>/day/<DD>/input` as a `Scanner` and passes it to the abstract `partOne(Scanner)` / `partTwo(Scanner)`.
- **`yearYYYY`**: one module per year. Each depends on `common` and `common-test` (test scope), and may also use libraries such as fastutil, commons-lang3, and Guava.

### Per-day conventions (`yearYYYY`)

- `src/main/java/com/adventofcode/yearYYYY/DayNN.java`: a `final` utility class of static solver methods. The puzzle text is copied in as `///` Markdown doc comments, one block per part.
- `src/test/java/com/adventofcode/yearYYYY/DayNNTest.java`: extends `AbstractTest` and calls `super(YYYY, NN)`. It usually adds an `inputExample()` test that runs the solver on the puzzle's sample input from a text block. `partOne`/`partTwo` assert the known answers for the real input.
- `src/test/resources/YYYY/day/NN/input`: the real puzzle input, committed to git.
- `src/test/resources/junit-platform.properties` enables parallel test execution (classes run concurrently), so solvers must not rely on shared mutable static state.

## Style

- Recent commits favour `var` for local variables and SLF4J loggers (`LOGGER`) over `System.out`/`IO.println`.
