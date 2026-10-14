package com.adventofcode.test;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.InputStream;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

/// Resolves `Scanner` test parameters from the puzzle input of the test class `@AdventOfCode(year, day)`.
public final class PuzzleInputExtension implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.getParameter().getType() == Scanner.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        var puzzle = AnnotationSupport.findAnnotation(extensionContext.getRequiredTestClass(), AdventOfCode.class)
                .orElseThrow(() -> new ParameterResolutionException(
                        "%s must be annotated with @AdventOfCode".formatted(extensionContext.getRequiredTestClass().getName())));

        var resource = String.format(Locale.ROOT, "/%d/day/%02d/input", puzzle.year(), puzzle.day());
        var scanner = new Scanner(openResource(resource), UTF_8);
        extensionContext.getStore(ExtensionContext.Namespace.create(PuzzleInputExtension.class, extensionContext.getUniqueId()))
                .put(resource, scanner);
        return scanner;
    }

    private static InputStream openResource(String resource) {
        var stream = PuzzleInputExtension.class.getResourceAsStream(resource);
        return Objects.requireNonNull(stream, () -> "Missing puzzle input " + resource);
    }
}
