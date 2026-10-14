package com.adventofcode.test;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

/// Checks that every `com.adventofcode` package on the classpath is annotated with `@NullMarked` and
/// `@CheckReturnValue`.
///
/// This class is picked up by every module through the Surefire `dependenciesToScan` setting.
public class PackageInfoTest {
    private static final String ROOT = "com.adventofcode";
    private static final String PACKAGE_INFO = "package-info";
    private static final String GENERATED_BY_JMH = ".jmh_generated";

    @Test
    void everyPackageIsNullMarkedAndCheckReturnValue() {
        var reflections = new Reflections(ROOT, Scanners.SubTypes.filterResultsBy(_ -> true));

        Map<String, String> classByPackage = reflections.getAll(Scanners.SubTypes).stream()
                .filter(name -> name.startsWith(ROOT + "."))
                .filter(name -> !name.endsWith(PACKAGE_INFO))
                .filter(name -> !name.contains(GENERATED_BY_JMH))
                .collect(Collectors.toMap(PackageInfoTest::packageName, name -> name, (a, b) -> a));

        var invalid = new TreeSet<String>();
        classByPackage.forEach((packageName, className) -> {
            var annotatedPackage = load(className).getPackage();
            if (!annotatedPackage.isAnnotationPresent(NullMarked.class)
                || !annotatedPackage.isAnnotationPresent(CheckReturnValue.class)) {
                invalid.add(packageName);
            }
        });

        assertThat(classByPackage).as("scanned packages").isNotEmpty();
        assertThat(invalid).as("packages without @NullMarked and @CheckReturnValue").isEmpty();
    }

    private static String packageName(String className) {
        return className.substring(0, className.lastIndexOf('.'));
    }

    private static Class<?> load(String className) {
        try {
            return Class.forName(className, false, PackageInfoTest.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot load " + className, e);
        }
    }
}
