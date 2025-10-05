package com.adventofcode.year2016;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.chars.Char2IntMap;
import it.unimi.dsi.fastutil.chars.Char2IntOpenHashMap;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day04 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day04.class);
    private static final Pattern PATTERN = Pattern.compile("([a-z\\-]+)-(\\d+)\\[(\\w+)]");

    private Day04() {
        // No-Op
    }

    @Nullable
    private static Pair<String, Integer> checkRoomName(String name) {
        Matcher matcher = PATTERN.matcher(name);
        if (!matcher.matches()) {
            return null;
        }

        String letters = matcher.group(1);
        int sectorID = Integer.parseInt(matcher.group(2));
        String checksum = matcher.group(3);
        Char2IntMap counts = new Char2IntOpenHashMap();
        for (int i = 0; i < letters.length(); i++) {
            char c = letters.charAt(i);
            if (c != '-') {
                counts.mergeInt(c, 1, Integer::sum);
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

        if (!Strings.CS.equals(checksum, hash)) {
            return null;
        }

        return Pair.of(letters, sectorID);
    }

    private static char decryptRoomName(char c, int sectorID) {
        if (c == '-') {
            return ' ';
        }

        sectorID %= 26;
        c += (char) sectorID;
        if (c > 'z') {
            c = (char) (c - 26);
        }

        return c;
    }

    static String decryptRoomName(String room, int sectorID) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < room.length(); i++) {
            char c = room.charAt(i);
            sb.append(decryptRoomName(c, sectorID));
        }
        return sb.toString();
    }

    @Nullable
    private static Pair<String, Integer> decryptRoomName(String input) {
        Pair<String, Integer> pair = checkRoomName(input);
        if (pair == null) {
            return null;
        }

        String name = pair.left();
        int sectorID = pair.right();
        return Pair.of(decryptRoomName(name, sectorID), sectorID);
    }

    /**
     * --- Day 4: Security Through Obscurity ---
     * <p>
     * Finally, you come across an information kiosk with a list of rooms. Of
     * course, the list is encrypted and full of decoy data, but the instructions
     * to decode the list are barely hidden nearby. Better remove the decoy data
     * first.
     * <p>
     * Each room consists of an encrypted name (lowercase letters separated by
     * dashes) followed by a dash, a sector ID, and a checksum in square brackets.
     * <p>
     * A room is real (not a decoy) if the checksum is the five most common
     * letters in the encrypted name, in order, with ties broken by
     * alphabetization. For example:
     * <p>
     * - aaaaa-bbb-z-y-x-123[abxyz] is a real room because the most common
     * letters are a (5), b (3), and then a tie between x, y, and z, which
     * are listed alphabetically.
     * - a-b-c-d-e-f-g-h-987[abcde] is a real room because although the letters
     * are all tied (1 of each), the first five are listed alphabetically.
     * - not-a-real-room-404[oarel] is a real room.
     * - totally-real-room-200[decoy] is not.
     * Of the real rooms from the list above, the sum of their sector IDs is 1514.
     * <p>
     * What is the sum of the sector IDs of the real rooms?
     * <p>
     * Your puzzle answer was 245102.
     */
    static int checkRoomName(Scanner scanner) {
        int sectorIDs = 0;
        while (scanner.hasNextLine()) {
            Pair<String, Integer> pair = checkRoomName(scanner.nextLine());
            if (pair != null) {
                sectorIDs += pair.right();
            }
        }
        return sectorIDs;
    }

    /**
     * --- Part Two ---
     * <p>
     * With all the decoy data out of the way, it's time to decrypt this list and
     * get moving.
     * <p>
     * The room names are encrypted by a state-of-the-art shift cipher, which is
     * nearly unbreakable without the right software. However, the information kiosk
     * designers at Easter Bunny HQ were not expecting to deal with a master
     * cryptographer like yourself.
     * <p>
     * To decrypt a room name, rotate each letter forward through the alphabet a
     * number of times equal to the room's sector ID. A becomes B, B becomes C, Z
     * becomes A, and so on. Dashes become spaces.
     * <p>
     * For example, the real name for qzmt-zixmtkozy-ivhz-343 is
     * very encrypted name.
     * <p>
     * What is the sector ID of the room where North Pole objects are stored?
     * <p>
     * Your puzzle answer was 324.
     */
    static int decryptRoomName(Scanner scanner) {
        while (scanner.hasNextLine()) {
            Pair<String, Integer> pair = decryptRoomName(scanner.nextLine());
            if (pair != null && "northpole object storage".equals(pair.left())) {
                LOGGER.info("{}", pair);
                return pair.right();
            }
        }
        return -1;
    }
}
