package com.adventofcode.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public final class FileUtils {

    private FileUtils() {
        // No-Op
    }

    public static List<String> readLines(String file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (InputStream is = FileUtils.class.getResourceAsStream(file)) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        }
        return lines;
    }

    public static String readLine(String file) throws IOException {
        try (InputStream is = FileUtils.class.getResourceAsStream(file)) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            return scanner.nextLine();
        }
    }
}
