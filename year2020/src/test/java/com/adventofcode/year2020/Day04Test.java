package com.adventofcode.year2020;

import com.adventofcode.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day04Test {

    @Test
    void testPassportProcessing() {
        List<String> batch = List.of(
                "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd",
                "byr:1937 iyr:2017 cid:147 hgt:183cm",
                "",
                "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884",
                "hcl:#cfa07d byr:1929",
                "",
                "hcl:#ae17e1 iyr:2013",
                "eyr:2024",
                "ecl:brn pid:760753108 byr:1931",
                "hgt:179cm",
                "",
                "hcl:#cfa07d eyr:2025 pid:166559648",
                "iyr:2011 ecl:brn hgt:59in"
        );

        assertThat(Day04.passportProcessing(batch)).isEqualTo(2);
    }

    @Test
    void testInvalidPassports() {
        List<String> batch = List.of("eyr:1972 cid:100",
                "hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926",
                "",
                "iyr:2019",
                "hcl:#602927 eyr:1967 hgt:170cm",
                "ecl:grn pid:012533040 byr:1946",
                "",
                "hcl:dab227 iyr:2012",
                "ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277",
                "",
                "hgt:59cm ecl:zzz",
                "eyr:2038 hcl:74454a iyr:2023",
                "pid:3556412378 byr:2007");

        assertThat(Day04.readBatchFile(batch).stream()
                .map(Day04::readPassport)
                .noneMatch(p -> Day04.checkMandatoryFields(p) && Day04.checkPassportFields(p)))
                .isTrue();
    }

    @Test
    void testValidPassports() {
        List<String> batch = List.of("pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980",
                "hcl:#623a2f",
                "",
                "eyr:2029 ecl:blu cid:129 byr:1989",
                "iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm",
                "",
                "hcl:#888785",
                "hgt:164cm byr:2001 iyr:2015 cid:88",
                "pid:545766238 ecl:hzl",
                "eyr:2022",
                "",
                "iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719");

        assertThat(Day04.readBatchFile(batch).stream()
                .map(Day04::readPassport)
                .allMatch(p -> Day04.checkMandatoryFields(p) && Day04.checkPassportFields(p)))
                .isTrue();
    }

    @Test
    void testPassportFields() {
        assertThat(Day04.PassportFields.byr.validField("2002")).isTrue();
        assertThat(Day04.PassportFields.byr.validField("2003")).isFalse();

        assertThat(Day04.PassportFields.hgt.validField("60in")).isTrue();
        assertThat(Day04.PassportFields.hgt.validField("190cm")).isTrue();
        assertThat(Day04.PassportFields.hgt.validField("190in")).isFalse();
        assertThat(Day04.PassportFields.hgt.validField("190")).isFalse();

        assertThat(Day04.PassportFields.hcl.validField("#123abc")).isTrue();
        assertThat(Day04.PassportFields.hcl.validField("#123abz")).isFalse();
        assertThat(Day04.PassportFields.hcl.validField("123abc")).isFalse();

        assertThat(Day04.PassportFields.ecl.validField("brn")).isTrue();
        assertThat(Day04.PassportFields.ecl.validField("wat")).isFalse();

        assertThat(Day04.PassportFields.pid.validField("000000001")).isTrue();
        assertThat(Day04.PassportFields.pid.validField("0123456789")).isFalse();

    }

    @Test
    void inputPassportProcessing() throws IOException {
        List<String> batch = FileUtils.readLines("/2020/day/4/input");

        assertThat(Day04.passportProcessing(batch)).isEqualTo(210);
        assertThat(Day04.runBatch(batch)).isEqualTo(131);
    }

}
