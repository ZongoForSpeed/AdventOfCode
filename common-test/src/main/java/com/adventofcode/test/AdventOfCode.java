package com.adventofcode.test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;

/// Declares the puzzle a test class is about, and lets its test methods take the puzzle input
/// as a `Scanner` parameter. The scanner reads the classpath resource `/<year>/day/<DD>/input`
/// and is closed after the test.
///
/// ```java
/// @AdventOfCode(year = 2024, day = 7)
/// class Day07Test {
///     @Test
///     void partOne(Scanner scanner) {
///         assertThat(Day07.partOne(scanner)).isEqualTo(3351424677624L);
///     }
/// }
/// ```
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(PuzzleInputExtension.class)
public @interface AdventOfCode {
    int year();

    int day();
}
