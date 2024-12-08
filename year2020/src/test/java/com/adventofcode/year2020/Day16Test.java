package com.adventofcode.year2020;

import com.adventofcode.common.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class Day16Test {

    @Test
    void testInvalidNearbyTickets() {
        List<String> notes = List.of("class: 1-3 or 5-7",
                "row: 6-11 or 33-44",
                "seat: 13-40 or 45-50",
                "",
                "your ticket:",
                "7,1,14",
                "",
                "nearby tickets:",
                "7,3,47",
                "40,4,50",
                "55,2,20",
                "38,6,12");

        assertThat(Day16.invalidNearbyTickets(notes)).isEqualTo(71);
    }

    @Test
    void testTicketTranslation() {
        List<String> notes = List.of("class: 0-1 or 4-19",
                "row: 0-5 or 8-19",
                "seat: 0-13 or 16-19",
                "",
                "your ticket:",
                "11,12,13",
                "",
                "nearby tickets:",
                "3,9,18",
                "15,1,5",
                "5,14,9");

        Map<String, Integer> ticketTranslation = Day16.ticketTranslation(notes);
        assertThat(ticketTranslation)
                .containsExactly(
                        Map.entry("seat", 13),
                        Map.entry("row", 11),
                        Map.entry("class", 12)
                );
    }

    @Test
    void inputInvalidNearbyTickets() throws IOException {
        List<String> notes = FileUtils.readLines("/2020/day/16/input");

        assertThat(Day16.invalidNearbyTickets(notes)).isEqualTo(21081);

        Map<String, Integer> ticketTranslation = Day16.ticketTranslation(notes);
        assertThat(ticketTranslation)
                .containsExactly(
                        Map.entry("arrival location", 71),
                        Map.entry("wagon", 127),
                        Map.entry("arrival station", 149),
                        Map.entry("type", 97),
                        Map.entry("arrival track", 101),
                        Map.entry("duration", 53),
                        Map.entry("seat", 137),
                        Map.entry("departure track", 59),
                        Map.entry("route", 73),
                        Map.entry("departure time", 61),
                        Map.entry("zone", 109),
                        Map.entry("arrival platform", 89),
                        Map.entry("price", 107),
                        Map.entry("departure location", 67),
                        Map.entry("departure station", 139),
                        Map.entry("departure platform", 113),
                        Map.entry("row", 131),
                        Map.entry("departure date", 83),
                        Map.entry("class", 103),
                        Map.entry("train", 79)
                );

        long departure = ticketTranslation.entrySet().stream().filter(e -> e.getKey().startsWith("departure"))
                .mapToLong(Map.Entry::getValue)
                .reduce(1, (a, b) -> a * b);
        assertThat(departure).isEqualTo(314360510573L);
    }

}
