package com.everybody.codes.event.year2024;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Quests01 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Quests01.class);

    static long countPotionsPartOne(Scanner scanner) {
        String line = scanner.nextLine();
        long potions = 0;
        for (int i = 0; i < line.length(); i++) {
            potions += countPotion(line.charAt(i));
        }
        return potions;
    }

    private static long countPotion(char monster) {
        return switch (monster) {
            case 'A' -> 0L;
            case 'B' -> 1L;
            case 'C' -> 3L;
            case 'D' -> 5L;
            default -> throw new IllegalStateException("Unknown monsters '" + monster + "'");
        };
    }

    private static long countPotion(char m1, char m2) {
        long potions = 0;
        if (m1 != 'x') {
            potions += countPotion(m1);
        }
        if (m2 != 'x') {
            potions += countPotion(m2);
        }
        if (m1 != 'x' && m2 != 'x') {
            potions += 2;
        }
        LOGGER.info("{}{} -> {}", m1, m2, potions);
        return potions;
    }

    private static long countPotion(char m1, char m2, char m3) {
        long potions = 0;
        int monsters = 0;
        if (m1 != 'x') {
            monsters++;
            potions += countPotion(m1);
        }
        if (m2 != 'x') {
            monsters++;
            potions += countPotion(m2);
        }
        if (m3 != 'x') {
            monsters++;
            potions += countPotion(m3);
        }
        if (monsters == 2) {
            potions += 2;
        } else if (monsters == 3) {
            potions += 6;
        }
        LOGGER.info("{}{}{} -> {}", m1, m2, m3, potions);
        return potions;
    }

    static long countPotionsPartTwo(Scanner scanner) {
        String line = scanner.nextLine();
        long potions = 0;
        for (int i = 0; i < line.length(); i += 2) {
            char m1 = line.charAt(i);
            char m2 = line.charAt(i + 1);
            potions += countPotion(m1, m2);
        }
        return potions;
    }

    static long countPotionsPartThree(Scanner scanner) {
        String line = scanner.nextLine();
        long potions = 0;
        for (int i = 0; i < line.length(); i += 3) {

            char m1 = line.charAt(i);
            char m2 = line.charAt(i + 1);
            char m3 = line.charAt(i + 2);
            potions+= countPotion(m1, m2, m3);
        }
        return potions;
    }
}
