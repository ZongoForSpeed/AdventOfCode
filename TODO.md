# TODO — `common` module

Findings from a review of `common/src/main/java/com/adventofcode/common` (2026-10-11).
Paths below are relative to that directory. Items marked _(verified)_ were reproduced by running the code.

## 1. Bugs

- [x] **`graph/AStar`: heuristic is ignored** _(verified)_ — the priority queue compares `NodeHeuristic::cost` instead of
  `NodeHeuristic::heuristic` (g + h), so it behaves like Dijkstra. With an exact heuristic on a 0→1000 line it expanded
  1,999 nodes instead of ~1,000. 11 subclasses in the year modules; re-run their tests after the fix.
- [x] **`maths/Prime.sieve` drops the last number** _(verified)_ — `test.set(0, size - 1, true)` excludes `size - 1`:
  `sieve(8)` = `[2, 3, 5]` (no 7), `sieve(14)` has no 13. Used in 4 days.
- [x] **`utils/Bits.convertBitSet` fails when bit 63 is set** _(verified)_ — the early exit `mask > bitset` is wrong for
  negative values: `convertBitSet(1L << 63 | 1)` = `[]`. Iterate with `Long.numberOfTrailingZeros` instead.
- [x] **`utils/Bits.toBitSet` sums instead of OR-ing** _(verified)_ — duplicates corrupt the result:
  `toBitSet([1, 1])` = 4 instead of 2. Also simplify `remove` to `bitset & ~mask`.
- [x] **`maths/Arithmetic.lcm` overflows early** _(verified)_ — `a * b / gcd(a, b)` overflows even when the result fits:
  `lcm(65_536, 32_768)` = -65536. Use `a / gcd(a, b) * b` (`int` and `long` versions).
- [x] **`point/range/Range.difference` returns invalid ranges** _(verified)_ —
  `difference([0..10], [0..5])` = `[[0..-1], [6..10]]`. Filter with `Range::valid`.
  _Fixed:_ also returned the wrong span for disjoint ranges (`[0..2] - [5..7]` gave `[0..4]`); now computed directly.
- [x] **`maths/Combinations.generate` exposes its cache** _(verified)_ — the cached `List<int[]>` is returned as is, so a
  caller writing to an array corrupts later results (`[99, 1]` came back after a mutation). Return copies or an
  immutable structure.
- [x] **`point/map/CharMap.insertLine` at the end throws NPE** _(verified)_ — `insertLine(map.length, c)` reads
  `map[line].length` on a `null` row.
- [x] **`maths/Digits` edge cases** _(verified)_ — `digits(-12)` returns `[]`; `numberDigits(0)` returns 0 (should be 1).
  _Fixed:_ negative input now throws `IllegalArgumentException`.
- [x] **`CharMap.subMap` wrong size** — allocated `xMax - xMin - 1` × `yMax - yMin - 1` but filled
  `xMax - xMin` × `yMax - yMin` (relied on auto-grow). _`BooleanMap.subMap` was actually correct: its constructor
  takes max indexes, not sizes._
- [x] **`point/map/BooleanMap` size bookkeeping is inconsistent** — `sizeX`/`sizeY` are a max index in `set`/`reset`,
  a count in the constructor and `trim()`, and iterated with `<=` in `toString`/`rotate`/`flipX`/`flipY` (off-by-one).
  Also: `trim()` throws on an empty map; `equals` treats trailing empty `BitSet` rows as different.
  _Fixed:_ max index is the contract (callers already relied on it); only `trim()` deviated. 2016 Day18 no longer
  trims and uses the known grid size instead.

## 2. Design

- [ ] **One generic sparse grid** — `Map2D`, `EnumMap2D`, `InfiniteCharMap`, `InfiniteBooleanMap` each hand-delegate
  `Map<Point2D, X>` (~60 lines) and duplicate `print()`. Replace with `SparseGrid<V> extends AbstractMap<Point2D, V>`
  with a single `render(Function<V, Character>)`.
- [ ] **Merge `IntegerMap` and `LongMap`** — the two 195-line files are identical modulo the element type.
- [ ] **Drop the `memory` package** (`Memory`, `IntMemory`, `LongMemory`, `ObjectMemory`, 4 usages) in favour of
  fastutil `Int2IntOpenHashMap` / `Int2ObjectOpenHashMap` (already a dependency). Note `IntMemory.containsKey(-1)`
  throws instead of returning `false`, and `clear()` resets capacity to 10.
- [ ] **`graph/Dijkstra` is O(V²)** — linear scan for the minimum, boxed `Pair<E, Integer>` edges, `int` distances.
  Use a priority queue, or implement it as `AStar` with a zero heuristic once AStar is fixed.
- [ ] **`point/Direction`: store `dx`/`dy` on each constant** — makes `Point2D.move` a one-liner and removes the
  per-constant `left`/`right`/`reverse` overrides boilerplate.
- [ ] **`point/Point2D` wrap-around semantics differ** — `moveLoop(Direction, xMax, yMax)` treats `xMax` as the last
  valid index, `moveLoop(Point2D, xMax, yMax)` as the grid size. Pick one.
- [ ] **Pair records duplicate fastutil** — `IntegerPair`, `LongPair`, `CharPair`, `BooleanPair` (44 usages) vs
  `IntIntPair` & co, which the code already mixes in.

## 3. Cleanup

- [ ] **`maths/Arithmetic`**
  - [ ] `modularInverse` (via φ, needs primes) and `inverseModulaire` (Bézout) do the same thing — keep Bézout,
        English name.
  - [ ] `power` silently truncates via `BigInteger.longValue()`; use `Math.multiplyExact` so overflow fails loudly.
  - [ ] `chineseRemainderTheorem`: `r * inverse * reminder` can overflow; use `Math.multiplyHigh`/`BigInteger`.
        Rename `reminders` → `remainders`.
  - [ ] Duplicated `int`/`long` `gcd` loops.
- [ ] **`maths/Permutations`** — three copy-pasted `permutationHelper`s, O(n²) per permutation via `List.remove`,
  allocation-heavy string version, ambiguous `List.remove` (Error Prone `ListRemoveAmbiguous`). Use an iterative
  next-permutation.
- [ ] **`graph/Clique`** — comment cites the pivot variant of Bron–Kerbosch, code has no pivot; unused in year
  modules. Delete, or add pivoting and a `maximalCliques(graph)` entry point.
- [ ] **`matrix/Matrix3D`** — `int` determinant and integer-division inverse are only correct for det = ±1
  (rotations). Document or rename; consider a record.
- [ ] **`maths/IntegerPartition`** — logs at INFO then throws; actually computes ordered compositions, not partitions;
  no memoization.
- [ ] **`maths/Prime.sieve2`** — unused; check correctness and make it the only sieve, or delete it.
- [ ] **`CharMap.xMax()` / `yMax()`** — full grid scan on every call; cache or track on `set`.
- [ ] **`graph/DisjointSet.find`** — assignment in a return expression (Error Prone `AssignmentExpression`).

## Suggested order

1. ~~Section 1, one regression test per bug; run the full suite.~~ Done — no year result changed.
2. Deduplicate: `IntegerMap`/`LongMap`, sparse grids, `memory` → fastutil.
3. API cleanup: `Direction`, `Point2D`, `Arithmetic`.
