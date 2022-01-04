package com.adventofcode.year2016;

import it.unimi.dsi.fastutil.chars.Char2IntMap;
import it.unimi.dsi.fastutil.chars.Char2IntOpenHashMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


class Day04Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day04Test.class);
    private static final Pattern PATTERN = Pattern.compile("([a-z\\-]+)-(\\d+)\\[(\\w+)]");

    private static Pair<String, Integer> checkRoomName(String name) {
        Matcher matcher = PATTERN.matcher(name);
        if (!matcher.matches()) {
            return null;
        }

        String letters = matcher.group(1);
        int sectorID = Integer.parseInt(matcher.group(2));
        String checksum = matcher.group(3);
        Char2IntMap counts = new Char2IntOpenHashMap();
        for (char c : letters.toCharArray()) {
            if (c != '-') {
                counts.compute(c, (ignore, value) -> value == null ? 1 : value + 1);
            }
        }

        String hash = counts.char2IntEntrySet()
                .stream()
                .sorted(Comparator.comparingInt(Char2IntMap.Entry::getIntValue).reversed().thenComparingInt(Char2IntMap.Entry::getCharKey))
                .limit(5)
                .map(Char2IntMap.Entry::getCharKey)
                .map(Object::toString)
                .collect(Collectors.joining());
        LOGGER.trace("Counts: {}", hash);

        if (!StringUtils.equals(checksum, hash)) {
            return null;
        }

        return Pair.of(letters, sectorID);
    }

    private static char decryptRoomName(char c, int sectorID) {
        if (c == '-') {
            return ' ';
        }

        sectorID %= 26;
        c += sectorID;
        if (c > 'z') {
            c -= 26;
        }

        return c;
    }

    private static String decryptRoomName(String room, int sectorID) {
        StringBuilder sb = new StringBuilder();
        for (char c : room.toCharArray()) {
            sb.append(decryptRoomName(c, sectorID));
        }
        return sb.toString();
    }

    private static Pair<String, Integer> decryptRoomName(String input) {
        Pair<String, Integer> pair = checkRoomName(input);
        if (pair == null) {
            return null;
        }

        String name = pair.getLeft();
        int sectorID = pair.getRight();
        return Pair.of(decryptRoomName(name, sectorID), sectorID);
    }

    private static int checkRoomName(Scanner scanner) {
        int sectorIDs = 0;
        while (scanner.hasNextLine()) {
            Pair<String, Integer> pair = checkRoomName(scanner.nextLine());
            if (pair != null) {
                sectorIDs += pair.getRight();
            }
        }
        return sectorIDs;
    }

    private static int decryptRoomName(Scanner scanner) {
        while (scanner.hasNextLine()) {
            Pair<String, Integer> pair = decryptRoomName(scanner.nextLine());
            if (pair != null && "northpole object storage".equals(pair.getLeft())) {
                LOGGER.info("{}", pair);
                return pair.getRight();
            }
        }
        return -1;
    }

    @Test
    void inputExample() {
        String input = """
                aaaaa-bbb-z-y-x-123[abxyz]
                a-b-c-d-e-f-g-h-987[abcde]
                not-a-real-room-404[oarel]
                totally-real-room-200[decoy]""";

        Scanner scanner = new Scanner(input);

        assertThat(checkRoomName(scanner)).isEqualTo(1514);

        assertThat(decryptRoomName("qzmt-zixmtkozy-ivhz", 343)).isEqualTo("very encrypted name");

    }

    /**
     * --- Day 4: Security Through Obscurity ---
     *
     * Finally, you come across an information kiosk with a list of rooms. Of
     * course, the list is encrypted and full of decoy data, but the instructions
     * to decode the list are barely hidden nearby. Better remove the decoy data
     * first.
     *
     * Each room consists of an encrypted name (lowercase letters separated by
     * dashes) followed by a dash, a sector ID, and a checksum in square brackets.
     *
     * A room is real (not a decoy) if the checksum is the five most common
     * letters in the encrypted name, in order, with ties broken by
     * alphabetization. For example:
     *
     *   - aaaaa-bbb-z-y-x-123[abxyz] is a real room because the most common
     *     letters are a (5), b (3), and then a tie between x, y, and z, which
     *     are listed alphabetically.
     *   - a-b-c-d-e-f-g-h-987[abcde] is a real room because although the letters
     *     are all tied (1 of each), the first five are listed alphabetically.
     *   - not-a-real-room-404[oarel] is a real room.
     *   - totally-real-room-200[decoy] is not.
     * Of the real rooms from the list above, the sum of their sector IDs is 1514.
     *
     * What is the sum of the sector IDs of the real rooms?
     *
     * Your puzzle answer was 245102.
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day04Test.class.getResourceAsStream("/2016/day/4/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(checkRoomName(scanner)).isEqualTo(245102);
        }
    }

    /**
     * --- Part Two ---
     *
     * With all the decoy data out of the way, it's time to decrypt this list and
     * get moving.
     *
     * The room names are encrypted by a state-of-the-art shift cipher, which is
     * nearly unbreakable without the right software. However, the information kiosk
     * designers at Easter Bunny HQ were not expecting to deal with a master
     * cryptographer like yourself.
     *
     * To decrypt a room name, rotate each letter forward through the alphabet a
     * number of times equal to the room's sector ID. A becomes B, B becomes C, Z
     * becomes A, and so on. Dashes become spaces.
     *
     * For example, the real name for qzmt-zixmtkozy-ivhz-343 is
     * very encrypted name.
     *
     * What is the sector ID of the room where North Pole objects are stored?
     *
     * Your puzzle answer was 324.
     */
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day04Test.class.getResourceAsStream("/2016/day/4/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(decryptRoomName(scanner)).isEqualTo(324);
        }
    }

}
