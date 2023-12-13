package com.adventofcode.year2020;

import it.unimi.dsi.fastutil.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day21Test {

    @Test
    void testAllergenAssessment() {
        String input = """
                mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
                trh fvjkl sbzzf mxmxvkd (contains dairy)
                sqjhc fvjkl (contains soy)
                sqjhc mxmxvkd sbzzf (contains fish)""";

        Scanner scanner = new Scanner(input);

        Pair<Long, String> result = Day21.findAllergenAssessment(scanner);

        assertThat(result.left()).isEqualTo(5);
        assertThat(result.right()).isEqualTo("mxmxvkd,sqjhc,fvjkl");
    }

    @Test
    void inputAllergenAssessment() throws IOException {
        try (InputStream inputStream = Day21Test.class.getResourceAsStream("/2020/day/21/input"); Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            Pair<Long, String> result = Day21.findAllergenAssessment(scanner);
            assertThat(result.left()).isEqualTo(2170L);
            assertThat(result.right()).isEqualTo("nfnfk,nbgklf,clvr,fttbhdr,qjxxpr,hdsm,sjhds,xchzh");
        }
    }
}
