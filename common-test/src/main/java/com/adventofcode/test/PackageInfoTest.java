package com.adventofcode.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

/// Checks that every package of the module under test is `@NullMarked` and `@CheckReturnValue`.
///
/// This class is picked up by every module through the Surefire `dependenciesToScan` setting,
/// and inspects the `src/main/java` directory of the module it runs in.
public class PackageInfoTest {
    private static final Path SOURCES = Path.of("src", "main", "java");

    @Test
    void everyPackageHasPackageInfo() throws IOException {
        if (!Files.isDirectory(SOURCES)) {
            return;
        }

        try (Stream<Path> files = Files.walk(SOURCES)) {
            var missing = files.filter(PackageInfoTest::isSourceFile)
                    .map(Path::getParent)
                    .distinct()
                    .filter(directory -> !Files.exists(directory.resolve("package-info.java")))
                    .map(SOURCES::relativize)
                    .map(Path::toString)
                    .sorted()
                    .toList();

            assertThat(missing).as("packages without package-info.java").isEmpty();
        }
    }

    @Test
    void everyPackageInfoIsNullMarked() throws IOException {
        if (!Files.isDirectory(SOURCES)) {
            return;
        }

        try (Stream<Path> files = Files.walk(SOURCES)) {
            var invalid = files.filter(path -> path.getFileName().toString().equals("package-info.java"))
                    .filter(PackageInfoTest::missingAnnotations)
                    .map(SOURCES::relativize)
                    .map(Path::toString)
                    .sorted()
                    .toList();

            assertThat(invalid).as("package-info.java without @NullMarked and @CheckReturnValue").isEmpty();
        }
    }

    private static boolean isSourceFile(Path path) {
        var name = path.getFileName().toString();
        return name.endsWith(".java") && !name.equals("package-info.java");
    }

    private static boolean missingAnnotations(Path packageInfo) {
        try {
            var content = Files.readString(packageInfo);
            return !content.contains("@NullMarked") || !content.contains("@CheckReturnValue");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
