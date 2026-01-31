package com.adventofcode.common.point;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DirectionTest {

    @Test
    void testReverse() {
        assertThat(Direction.UP.reverse()).isEqualTo(Direction.DOWN);
        assertThat(Direction.DOWN.reverse()).isEqualTo(Direction.UP);
        assertThat(Direction.LEFT.reverse()).isEqualTo(Direction.RIGHT);
        assertThat(Direction.RIGHT.reverse()).isEqualTo(Direction.LEFT);
    }

    @Test
    void testLeft() {
        assertThat(Direction.UP.left()).isEqualTo(Direction.LEFT);
        assertThat(Direction.LEFT.left()).isEqualTo(Direction.DOWN);
        assertThat(Direction.DOWN.left()).isEqualTo(Direction.RIGHT);
        assertThat(Direction.RIGHT.left()).isEqualTo(Direction.UP);
    }

    @Test
    void testRight() {
        assertThat(Direction.UP.right()).isEqualTo(Direction.RIGHT);
        assertThat(Direction.RIGHT.right()).isEqualTo(Direction.DOWN);
        assertThat(Direction.DOWN.right()).isEqualTo(Direction.LEFT);
        assertThat(Direction.LEFT.right()).isEqualTo(Direction.UP);
    }
}
