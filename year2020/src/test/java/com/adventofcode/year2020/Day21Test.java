package com.adventofcode.year2020;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day21Test extends AbstractTest {
    Day21Test() {
        super(2020, 21);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        var result = Day21.findAllergenAssessment(scanner);
        assertThat(result.left()).isEqualTo(2170L);
        assertThat(result.right()).isEqualTo("nfnfk,nbgklf,clvr,fttbhdr,qjxxpr,hdsm,sjhds,xchzh");
    }

    @Override
    public void partTwo(Scanner scanner) {
        // No-Op
    }
}
