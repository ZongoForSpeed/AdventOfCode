package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class Day23Test extends AbstractTest {

    Day23Test() {
        super(2024, 23);
    }

    @Test
    void inputExample() {
        String input = """
                kh-tc
                qp-kh
                de-cg
                ka-co
                yn-aq
                qp-ub
                cg-tb
                vc-aq
                tb-ka
                wh-tc
                yn-cg
                kh-ub
                ta-co
                de-co
                tc-td
                tb-wq
                wh-td
                ta-ka
                td-qp
                aq-cg
                wq-ub
                ub-vc
                de-ta
                wq-aq
                wq-vc
                wh-yn
                ka-de
                kh-ta
                co-tc
                wh-qp
                tb-vc
                td-yn""";

        try (Scanner scanner = new Scanner(input)) {
            Set<Set<String>> triplets = Day23.partOne(scanner);

            assertThat(triplets).containsExactlyInAnyOrder(
                    Set.of("co", "de", "ta"),
                    Set.of("co", "ka", "ta"),
                    Set.of("de", "ka", "ta"),
                    Set.of("qp", "td", "wh"),
                    Set.of("tb", "vc", "wq"),
                    Set.of("tc", "td", "wh"),
                    Set.of("td", "wh", "yn")
            );
        }

        try (Scanner scanner = new Scanner(input)) {
            String clique = Day23.partTwo(scanner);
            assertThat(clique).isEqualTo("co,de,ka,ta");
        }
    }


    @Override
    public void partOne(Scanner scanner) throws Exception {
        Set<Set<String>> triplets = Day23.partOne(scanner);
        assertThat(triplets).hasSize(893);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        String clique = Day23.partTwo(scanner);
        assertThat(clique).isEqualTo("cw,dy,ef,iw,ji,jv,ka,ob,qv,ry,ua,wt,xz");
    }


}
