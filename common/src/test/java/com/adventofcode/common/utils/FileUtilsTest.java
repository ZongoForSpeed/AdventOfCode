package com.adventofcode.common.utils;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class FileUtilsTest {

    @Test
    void testReadLines() {
        String input = "line1\nline2\nline3";
        Scanner scanner = new Scanner(input);
        List<String> lines = FileUtils.readLines(scanner);
        assertThat(lines).containsExactly("line1", "line2", "line3");
    }

    @Test
    void testReadEmpty() {
        Scanner scanner = new Scanner("");
        List<String> lines = FileUtils.readLines(scanner);
        assertThat(lines).isEmpty();
    }
}
