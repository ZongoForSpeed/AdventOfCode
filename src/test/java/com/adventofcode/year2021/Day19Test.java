package com.adventofcode.year2021;

import com.adventofcode.map.Point3D;
import com.adventofcode.matrix.Matrix3D;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day19Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day19Test.class);

    private static final List<Matrix3D> ROTATIONS = buildOrientation();

    private static List<Matrix3D> buildOrientation() {
        List<Point3D> pivots = List.of(
                Point3D.of(1, 0, 0),
                Point3D.of(0, 1, 0),
                Point3D.of(0, 0, 1)
        );

        pivots = pivots.stream().flatMap(p -> Stream.of(p, Point3D.minus(p))).toList();
        LOGGER.trace("Pivots: {}", pivots);

        List<Matrix3D> rotations = new ArrayList<>();
        for (Point3D pivot1 : pivots) {
            for (Point3D pivot2 : pivots) {
                for (Point3D pivot3 : pivots) {
                    Matrix3D m = new Matrix3D(pivot1, pivot2, pivot3);
                    int determinant = m.determinant();
                    if (determinant == 1) {
                        LOGGER.trace("Determinant {} = {}", m, determinant);
                        rotations.add(m);
                    }
                }
            }
        }

        return List.copyOf(rotations);
    }

    private static Set<Point3D> beaconScannerPartOne(Scanner scanner) {
        return beaconScanner(scanner).getLeft();
    }

    private static Integer beaconScannerPartTwo(Scanner scanner) {
        return beaconScanner(scanner).getRight();
    }

    private static Pair<Set<Point3D>, Integer> beaconScanner(Scanner scanner) {
        Pattern pattern = Pattern.compile("--- scanner (\\d+) ---");

        Map<String, List<Probe>> scanners = new HashMap<>();

        String scannerName = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (StringUtils.isBlank(line)) {
                continue;
            }
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                scannerName = matcher.group(1);
            } else {
                int[] coord = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
                scanners.computeIfAbsent(scannerName, ignore -> new ArrayList<>()).add(Probe.of(Point3D.of(coord[0], coord[1], coord[2])));
            }
        }

        for (Map.Entry<String, List<Probe>> entry : scanners.entrySet()) {
            LOGGER.trace("Scanner {}: \n{}", entry.getKey(), entry.getValue());
        }

        for (List<Probe> probes : scanners.values()) {
            for (Probe probe1 : probes) {
                for (Probe probe2 : probes) {
                    probe1.distances().add(Point3D.distance(probe1.point(), probe2.point()));
                }
            }
        }

        for (Map.Entry<String, List<Probe>> entry : scanners.entrySet()) {
            LOGGER.trace("Scanner {}: \n{}", entry.getKey(), entry.getValue());
        }

        Map<Pair<String, String>, Set<Pair<Point3D, Point3D>>> beaconmap = new HashMap<>();
        {
            List<Map.Entry<String, List<Probe>>> entries = List.copyOf(scanners.entrySet());
            for (int i = 0; i < entries.size(); i++) {
                Map.Entry<String, List<Probe>> entryI = entries.get(i);
                for (int j = i + 1; j < entries.size(); j++) {
                    Map.Entry<String, List<Probe>> entryJ = entries.get(j);
                    for (Probe probe1 : entryI.getValue()) {
                        for (Probe probe2 : entryJ.getValue()) {
                            SetUtils.SetView<Double> intersection = SetUtils.intersection(probe1.distances(), probe2.distances());
                            if (intersection.size() >= 12) {
                                beaconmap.computeIfAbsent(Pair.of(entryI.getKey(), entryJ.getKey()), ignore -> new HashSet<>())
                                        .add(Pair.of(probe1.point(), probe2.point()));
                            }
                        }
                    }
                }
            }
        }

        for (Map.Entry<Pair<String, String>, Set<Pair<Point3D, Point3D>>> entry : beaconmap.entrySet()) {
            LOGGER.trace("Beaconmap {}: \n{}", entry.getKey(), entry.getValue());
        }

        Map<Pair<String, String>, List<Transformation>> transforms = new HashMap<>();
        for (Map.Entry<Pair<String, String>, Set<Pair<Point3D, Point3D>>> entry : beaconmap.entrySet()) {
            Pair<String, String> pair = entry.getKey();
            Set<Pair<Point3D, Point3D>> values = entry.getValue();
            for (Matrix3D o : ROTATIONS) {
                Map<Double, Pair<Point3D, Point3D>> set = new HashMap<>();
                for (Pair<Point3D, Point3D> value : values) {
                    set.put(Point3D.distance(value.getLeft(), o.apply(value.getRight())), value);
                }
                if (set.size() == 1) {
                    Pair<Point3D, Point3D> value = values.iterator().next();
                    Point3D A = value.getLeft();
                    Point3D B = o.apply(value.getRight());
                    LOGGER.trace("Pair: {} {} -> {}, {}", o, pair, A, B);
                    Transformation transformation = Transformation.of(Point3D.minus(A, B), o);
                    LOGGER.trace("Operation {}: {}", pair, transformation);

                    transforms.computeIfAbsent(pair, ignore -> new ArrayList<>()).add(transformation);
                    transforms.computeIfAbsent(Pair.of(pair.getRight(), pair.getLeft()), ignore -> new ArrayList<>()).add(transformation.inverse());
                    break;
                }
            }
        }

        for (Map.Entry<Pair<String, String>, List<Transformation>> entry : transforms.entrySet()) {
            LOGGER.trace("Transforms {}: \n{}", entry.getKey(), entry.getValue());
        }

        while (transforms.keySet().stream().map(Pair::getLeft).filter("0"::equals).count() < scanners.size() - 1) {
            for (String b : scanners.keySet()) {
                for (Map.Entry<String, List<Probe>> entry1 : scanners.entrySet()) {
                    String i = entry1.getKey();
                    for (Map.Entry<String, List<Probe>> entry2 : scanners.entrySet()) {
                        String j = entry2.getKey();
                        if (b.equals(j) || i.equals(j)) {
                            continue;
                        }
                        List<Transformation> rel1 = transforms.get(Pair.of(b, i));
                        List<Transformation> rel2 = transforms.get(Pair.of(i, j));
                        if (rel1 != null && rel2 != null && !transforms.containsKey(Pair.of(b, j))) {
                            List<Transformation> transform = new ArrayList<>();
                            transform.addAll(rel2);
                            transform.addAll(rel1);
                            transforms.put(Pair.of(b, j), transform);
                        }
                        rel2 = transforms.get(Pair.of(j, i));
                        if (rel1 != null && rel2 != null && !transforms.containsKey(Pair.of(b, j))) {
                            List<Transformation> transform = new ArrayList<>();
                            rel2 = new ArrayList<>(rel2);
                            Collections.reverse(rel2);
                            for (Transformation t : rel2) {
                                transform.add(t.inverse());
                            }
                            transform.addAll(rel1);
                            transforms.put(Pair.of(b, j), transform);
                        }
                    }
                }
            }
        }

        for (Map.Entry<Pair<String, String>, List<Transformation>> entry : transforms.entrySet()) {
            LOGGER.trace("Transforms {}: \n{}", entry.getKey(), entry.getValue());
        }

        Map<String, Point3D> positions = new HashMap<>();
        positions.put("0", Point3D.ORIGIN);
        Set<Point3D> points = new HashSet<>();
        scanners.get("0").forEach(probe -> points.add(probe.point()));

        for (Map.Entry<String, List<Probe>> entry : scanners.entrySet()) {
            String key = entry.getKey();
            if ("0".equals(key)) {
                continue;
            }
            for (Probe probe : entry.getValue()) {
                Point3D scannerPosition = Point3D.ORIGIN;
                Point3D point = probe.point();
                for (Transformation transform : transforms.get(Pair.of("0", key))) {
                    scannerPosition = transform.apply(scannerPosition);
                    point = transform.apply(point);
                }

                points.add(point);
                positions.put(key, scannerPosition);
            }
        }
        LOGGER.info("Points: {}\n{}", points.size(), points);

        LOGGER.info("Position: {}", positions);
        int maxDistance = 0;
        for (Point3D position1 : positions.values()) {
            for (Point3D position2 : positions.values()) {
                int distance = Point3D.manhattanDistance(position1, position2);
                maxDistance = Math.max(maxDistance, distance);
            }
        }

        LOGGER.info("Max distance: {}", maxDistance);

        return Pair.of(points, maxDistance);
    }

    @Test
    void inputExample() {
        String input = """
                --- scanner 0 ---
                404,-588,-901
                528,-643,409
                -838,591,734
                390,-675,-793
                -537,-823,-458
                -485,-357,347
                -345,-311,381
                -661,-816,-575
                -876,649,763
                -618,-824,-621
                553,345,-567
                474,580,667
                -447,-329,318
                -584,868,-557
                544,-627,-890
                564,392,-477
                455,729,728
                -892,524,684
                -689,845,-530
                423,-701,434
                7,-33,-71
                630,319,-379
                443,580,662
                -789,900,-551
                459,-707,401

                --- scanner 1 ---
                686,422,578
                605,423,415
                515,917,-361
                -336,658,858
                95,138,22
                -476,619,847
                -340,-569,-846
                567,-361,727
                -460,603,-452
                669,-402,600
                729,430,532
                -500,-761,534
                -322,571,750
                -466,-666,-811
                -429,-592,574
                -355,545,-477
                703,-491,-529
                -328,-685,520
                413,935,-424
                -391,539,-444
                586,-435,557
                -364,-763,-893
                807,-499,-711
                755,-354,-619
                553,889,-390

                --- scanner 2 ---
                649,640,665
                682,-795,504
                -784,533,-524
                -644,584,-595
                -588,-843,648
                -30,6,44
                -674,560,763
                500,723,-460
                609,671,-379
                -555,-800,653
                -675,-892,-343
                697,-426,-610
                578,704,681
                493,664,-388
                -671,-858,530
                -667,343,800
                571,-461,-707
                -138,-166,112
                -889,563,-600
                646,-828,498
                640,759,510
                -630,509,768
                -681,-892,-333
                673,-379,-804
                -742,-814,-386
                577,-820,562

                --- scanner 3 ---
                -589,542,597
                605,-692,669
                -500,565,-823
                -660,373,557
                -458,-679,-417
                -488,449,543
                -626,468,-788
                338,-750,-386
                528,-832,-391
                562,-778,733
                -938,-730,414
                543,643,-506
                -524,371,-870
                407,773,750
                -104,29,83
                378,-903,-323
                -778,-728,485
                426,699,580
                -438,-605,-362
                -469,-447,-387
                509,732,623
                647,635,-688
                -868,-804,481
                614,-800,639
                595,780,-596

                --- scanner 4 ---
                727,592,562
                -293,-554,779
                441,611,-461
                -714,465,-776
                -743,427,-804
                -660,-479,-426
                832,-632,460
                927,-485,-438
                408,393,-506
                466,436,-512
                110,16,151
                -258,-428,682
                -393,719,612
                -211,-452,876
                808,-476,-593
                -575,615,604
                -485,667,467
                -680,325,-822
                -627,-443,-432
                872,-547,-609
                833,512,582
                807,604,487
                839,-516,451
                891,-625,532
                -652,-548,-490
                30,-46,-14""";

        Pair<Set<Point3D>, Integer> beaconScanner = beaconScanner(new Scanner(input));

        assertThat(beaconScanner.getLeft()).hasSize(79);
        assertThat(beaconScanner.getRight()).isEqualTo(3621);

    }

    /**
     * --- Day 19: Beacon Scanner ---
     *
     * As your probe drifted down through this area, it released an assortment of
     * beacons and scanners into the water. It's difficult to navigate in the
     * pitch black open waters of the ocean trench, but if you can build a map of the
     * trench using data from the scanners, you should be able to safely reach
     * the bottom.
     *
     * The beacons and scanners float motionless in the water; they're designed to
     * maintain the same position for long periods of time. Each scanner is
     * capable of detecting all beacons in a large cube centered on the scanner;
     * beacons that are at most 1000 units away from the scanner in each of the
     * three axes (x, y, and z) have their precise position determined relative to
     * the scanner. However, scanners cannot detect other scanners. The submarine
     * has automatically summarized the relative positions of beacons detected by
     * each scanner (your puzzle input).
     *
     * For example, if a scanner is at x,y,z coordinates 500,0,-500 and there are
     * beacons at -500,1000,-1500 and 1501,0,-500, the scanner could report that
     * the first beacon is at -1000,1000,-1000 (relative to the scanner) but would
     * not detect the second beacon at all.
     *
     * Unfortunately, while each scanner can report the positions of all detected
     * beacons relative to itself, the scanners do not know their own position.
     * You'll need to determine the positions of the beacons and scanners
     * yourself.
     *
     * The scanners and beacons map a single contiguous 3d region. This region can
     * be reconstructed by finding pairs of scanners that have overlapping
     * detection regions such that there are at least 12 beacons that both
     * scanners detect within the overlap. By establishing 12 common beacons, you
     * can precisely determine where the scanners are relative to each other,
     * allowing you to reconstruct the beacon map one scanner at a time.
     *
     * For a moment, consider only two dimensions. Suppose you have the following
     * scanner reports:
     *
     * --- scanner 0 ---
     * 0,2
     * 4,1
     * 3,3
     *
     * --- scanner 1 ---
     * -1,-1
     * -5,0
     * -2,1
     *
     * Drawing x increasing rightward, y increasing upward, scanners as S, and
     * beacons as B, scanner 0 detects this:
     *
     * ...B.
     * B....
     * ....B
     * S....
     *
     * Scanner 1 detects this:
     *
     * ...B..
     * B....S
     * ....B.
     *
     * For this example, assume scanners only need 3 overlapping beacons. Then,
     * the beacons visible to both scanners overlap to produce the following
     * complete map:
     *
     * ...B..
     * B....S
     * ....B.
     * S.....
     *
     * Unfortunately, there's a second problem: the scanners also don't know their
     * rotation or facing direction. Due to magnetic alignment, each scanner is
     * rotated some integer number of 90-degree turns around all of the x, y, and
     * z axes. That is, one scanner might call a direction positive x, while
     * another scanner might call that direction negative y. Or, two scanners
     * might agree on which direction is positive x, but one scanner might be
     * upside-down from the perspective of the other scanner. In total, each
     * scanner could be in any of 24 different orientations: facing positive or
     * negative x, y, or z, and considering any of four directions "up" from that
     * facing.
     *
     * For example, here is an arrangement of beacons as seen from a scanner in
     * the same position but in different orientations:
     *
     * --- scanner 0 ---
     * -1,-1,1
     * -2,-2,2
     * -3,-3,3
     * -2,-3,1
     * 5,6,-4
     * 8,0,7
     *
     * --- scanner 0 ---
     * 1,-1,1
     * 2,-2,2
     * 3,-3,3
     * 2,-1,3
     * -5,4,-6
     * -8,-7,0
     *
     * --- scanner 0 ---
     * -1,-1,-1
     * -2,-2,-2
     * -3,-3,-3
     * -1,-3,-2
     * 4,6,5
     * -7,0,8
     *
     * --- scanner 0 ---
     * 1,1,-1
     * 2,2,-2
     * 3,3,-3
     * 1,3,-2
     * -4,-6,5
     * 7,0,8
     *
     * --- scanner 0 ---
     * 1,1,1
     * 2,2,2
     * 3,3,3
     * 3,1,2
     * -6,-4,-5
     * 0,7,-8
     *
     * By finding pairs of scanners that both see at least 12 of the same beacons,
     * you can assemble the entire map. For example, consider the following
     * report:
     *
     * --- scanner 0 ---
     * 404,-588,-901
     * 528,-643,409
     * -838,591,734
     * 390,-675,-793
     * -537,-823,-458
     * -485,-357,347
     * -345,-311,381
     * -661,-816,-575
     * -876,649,763
     * -618,-824,-621
     * 553,345,-567
     * 474,580,667
     * -447,-329,318
     * -584,868,-557
     * 544,-627,-890
     * 564,392,-477
     * 455,729,728
     * -892,524,684
     * -689,845,-530
     * 423,-701,434
     * 7,-33,-71
     * 630,319,-379
     * 443,580,662
     * -789,900,-551
     * 459,-707,401
     *
     * --- scanner 1 ---
     * 686,422,578
     * 605,423,415
     * 515,917,-361
     * -336,658,858
     * 95,138,22
     * -476,619,847
     * -340,-569,-846
     * 567,-361,727
     * -460,603,-452
     * 669,-402,600
     * 729,430,532
     * -500,-761,534
     * -322,571,750
     * -466,-666,-811
     * -429,-592,574
     * -355,545,-477
     * 703,-491,-529
     * -328,-685,520
     * 413,935,-424
     * -391,539,-444
     * 586,-435,557
     * -364,-763,-893
     * 807,-499,-711
     * 755,-354,-619
     * 553,889,-390
     *
     * --- scanner 2 ---
     * 649,640,665
     * 682,-795,504
     * -784,533,-524
     * -644,584,-595
     * -588,-843,648
     * -30,6,44
     * -674,560,763
     * 500,723,-460
     * 609,671,-379
     * -555,-800,653
     * -675,-892,-343
     * 697,-426,-610
     * 578,704,681
     * 493,664,-388
     * -671,-858,530
     * -667,343,800
     * 571,-461,-707
     * -138,-166,112
     * -889,563,-600
     * 646,-828,498
     * 640,759,510
     * -630,509,768
     * -681,-892,-333
     * 673,-379,-804
     * -742,-814,-386
     * 577,-820,562
     *
     * --- scanner 3 ---
     * -589,542,597
     * 605,-692,669
     * -500,565,-823
     * -660,373,557
     * -458,-679,-417
     * -488,449,543
     * -626,468,-788
     * 338,-750,-386
     * 528,-832,-391
     * 562,-778,733
     * -938,-730,414
     * 543,643,-506
     * -524,371,-870
     * 407,773,750
     * -104,29,83
     * 378,-903,-323
     * -778,-728,485
     * 426,699,580
     * -438,-605,-362
     * -469,-447,-387
     * 509,732,623
     * 647,635,-688
     * -868,-804,481
     * 614,-800,639
     * 595,780,-596
     *
     * --- scanner 4 ---
     * 727,592,562
     * -293,-554,779
     * 441,611,-461
     * -714,465,-776
     * -743,427,-804
     * -660,-479,-426
     * 832,-632,460
     * 927,-485,-438
     * 408,393,-506
     * 466,436,-512
     * 110,16,151
     * -258,-428,682
     * -393,719,612
     * -211,-452,876
     * 808,-476,-593
     * -575,615,604
     * -485,667,467
     * -680,325,-822
     * -627,-443,-432
     * 872,-547,-609
     * 833,512,582
     * 807,604,487
     * 839,-516,451
     * 891,-625,532
     * -652,-548,-490
     * 30,-46,-14
     *
     * Because all coordinates are relative, in this example, all "absolute"
     * positions will be expressed relative to scanner 0 (using the orientation of
     * scanner 0 and as if scanner 0 is at coordinates 0,0,0).
     *
     * Scanners 0 and 1 have overlapping detection cubes; the 12 beacons they both
     * detect (relative to scanner 0) are at the following coordinates:
     *
     * -618,-824,-621
     * -537,-823,-458
     * -447,-329,318
     * 404,-588,-901
     * 544,-627,-890
     * 528,-643,409
     * -661,-816,-575
     * 390,-675,-793
     * 423,-701,434
     * -345,-311,381
     * 459,-707,401
     * -485,-357,347
     *
     * These same 12 beacons (in the same order) but from the perspective of
     * scanner 1 are:
     *
     * 686,422,578
     * 605,423,415
     * 515,917,-361
     * -336,658,858
     * -476,619,847
     * -460,603,-452
     * 729,430,532
     * -322,571,750
     * -355,545,-477
     * 413,935,-424
     * -391,539,-444
     * 553,889,-390
     *
     * Because of this, scanner 1 must be at 68,-1246,-43 (relative to scanner 0).
     *
     * Scanner 4 overlaps with scanner 1; the 12 beacons they both detect
     * (relative to scanner 0) are:
     *
     * 459,-707,401
     * -739,-1745,668
     * -485,-357,347
     * 432,-2009,850
     * 528,-643,409
     * 423,-701,434
     * -345,-311,381
     * 408,-1815,803
     * 534,-1912,768
     * -687,-1600,576
     * -447,-329,318
     * -635,-1737,486
     *
     * So, scanner 4 is at -20,-1133,1061 (relative to scanner 0).
     *
     * Following this process, scanner 2 must be at 1105,-1205,1229 (relative to
     * scanner 0) and scanner 3 must be at -92,-2380,-20 (relative to scanner 0).
     *
     * The full list of beacons (relative to scanner 0) is:
     *
     * -892,524,684
     * -876,649,763
     * -838,591,734
     * -789,900,-551
     * -739,-1745,668
     * -706,-3180,-659
     * -697,-3072,-689
     * -689,845,-530
     * -687,-1600,576
     * -661,-816,-575
     * -654,-3158,-753
     * -635,-1737,486
     * -631,-672,1502
     * -624,-1620,1868
     * -620,-3212,371
     * -618,-824,-621
     * -612,-1695,1788
     * -601,-1648,-643
     * -584,868,-557
     * -537,-823,-458
     * -532,-1715,1894
     * -518,-1681,-600
     * -499,-1607,-770
     * -485,-357,347
     * -470,-3283,303
     * -456,-621,1527
     * -447,-329,318
     * -430,-3130,366
     * -413,-627,1469
     * -345,-311,381
     * -36,-1284,1171
     * -27,-1108,-65
     * 7,-33,-71
     * 12,-2351,-103
     * 26,-1119,1091
     * 346,-2985,342
     * 366,-3059,397
     * 377,-2827,367
     * 390,-675,-793
     * 396,-1931,-563
     * 404,-588,-901
     * 408,-1815,803
     * 423,-701,434
     * 432,-2009,850
     * 443,580,662
     * 455,729,728
     * 456,-540,1869
     * 459,-707,401
     * 465,-695,1988
     * 474,580,667
     * 496,-1584,1900
     * 497,-1838,-617
     * 527,-524,1933
     * 528,-643,409
     * 534,-1912,768
     * 544,-627,-890
     * 553,345,-567
     * 564,392,-477
     * 568,-2007,-577
     * 605,-1665,1952
     * 612,-1593,1893
     * 630,319,-379
     * 686,-3108,-505
     * 776,-3184,-501
     * 846,-3110,-434
     * 1135,-1161,1235
     * 1243,-1093,1063
     * 1660,-552,429
     * 1693,-557,386
     * 1735,-437,1738
     * 1749,-1800,1813
     * 1772,-405,1572
     * 1776,-675,371
     * 1779,-442,1789
     * 1780,-1548,337
     * 1786,-1538,337
     * 1847,-1591,415
     * 1889,-1729,1762
     * 1994,-1805,1792
     *
     * In total, there are 79 beacons.
     *
     * Assemble the full map of beacons. How many beacons are there?
     *
     * Your puzzle answer was 353.
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day19Test.class.getResourceAsStream("/2021/day/19/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(beaconScannerPartOne(scanner)).hasSize(353);
        }
    }

    /**
     * --- Part Two ---
     *
     * Sometimes, it's a good idea to appreciate just how big the ocean is. Using
     * the Manhattan distance, how far apart do the scanners get?
     *
     * In the above example, scanners 2 (1105,-1205,1229) and 3 (-92,-2380,-20)
     * are the largest Manhattan distance apart. In total, they are
     * 1197 + 1175 + 1249 = 3621 units apart.
     *
     * What is the largest Manhattan distance between any two scanners?
     *
     * Your puzzle answer was 10832.
     */
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day19Test.class.getResourceAsStream("/2021/day/19/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(beaconScannerPartTwo(scanner)).isEqualTo(10832);
        }
    }

    record Probe(Point3D point, Set<Double> distances) {
        public static Probe of(Point3D point) {
            return new Probe(point, new HashSet<>());
        }
    }

    record Transformation(Point3D base, Matrix3D operation) {
        public static Transformation of(Point3D base, Matrix3D m) {
            return new Transformation(base, m);
        }

        public Transformation inverse() {
            Matrix3D newOperation = operation.inverse();
            Point3D newBase = newOperation.apply(Point3D.minus(base));
            return of(newBase, newOperation);
        }

        public Point3D apply(Point3D point) {
            return Point3D.add(base, operation.apply(point));
        }
    }

}
