package com.adventofcode.common.utils;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class FileUtilsTest {

    @Test
    void testReadLines() {
        var input = "line1\nline2\nline3";
        var scanner = new Scanner(input);
        var lines = FileUtils.readLines(scanner);
        assertThat(lines).containsExactly("line1", "line2", "line3");
    }

    @Test
    void testReadEmpty() {
        var scanner = new Scanner("");
        var lines = FileUtils.readLines(scanner);
        assertThat(lines).isEmpty();
    }
}
