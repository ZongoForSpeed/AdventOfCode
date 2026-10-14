package com.adventofcode.year2020;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2020, day = 21)
class Day21Test {
    @Test
    void allergenAssessment() {
        var input = """
                mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
                trh fvjkl sbzzf mxmxvkd (contains dairy)
                sqjhc fvjkl (contains soy)
                sqjhc mxmxvkd sbzzf (contains fish)""";

        var scanner = new Scanner(input);

        var result = Day21.findAllergenAssessment(scanner);

        assertThat(result.left()).isEqualTo(5);
        assertThat(result.right()).isEqualTo("mxmxvkd,sqjhc,fvjkl");
    }

    @Test
    void partOne(Scanner scanner) {
        var result = Day21.findAllergenAssessment(scanner);
        assertThat(result.left()).isEqualTo(2170L);
        assertThat(result.right()).isEqualTo("nfnfk,nbgklf,clvr,fttbhdr,qjxxpr,hdsm,sjhds,xchzh");
    }

    @Test
    void partTwo(Scanner scanner) {
        // No-Op
    }
}
