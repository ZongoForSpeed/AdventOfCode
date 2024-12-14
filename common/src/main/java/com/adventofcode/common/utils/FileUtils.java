package com.adventofcode.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class FileUtils {

    private FileUtils() {
        // No-Op
    }

    public static List<String> readLines(Scanner scanner) {
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        return lines;
    }
}
