package com.adventofcode.common.point;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Position2DTest {

    @Test
    void testOf() {
        Point2D p = Point2D.of(1, 2);
        Position2D pos = Position2D.of(p, Direction.UP);
        assertThat(pos.p()).isEqualTo(p);
        assertThat(pos.direction()).isEqualTo(Direction.UP);
        assertThat(pos.x()).isEqualTo(1);
        assertThat(pos.y()).isEqualTo(2);

        Position2D pos2 = Position2D.of(5, 6, Direction.DOWN);
        assertThat(pos2.p()).isEqualTo(Point2D.of(5, 6));
        assertThat(pos2.direction()).isEqualTo(Direction.DOWN);
    }

    @Test
    void testLeftRight() {
        Position2D pos = Position2D.of(0, 0, Direction.UP);
        assertThat(pos.left()).isEqualTo(Position2D.of(0, 0, Direction.LEFT));
        assertThat(pos.right()).isEqualTo(Position2D.of(0, 0, Direction.RIGHT));
    }

    @Test
    void testMove() {
        Position2D pos = Position2D.of(5, 5, Direction.UP);
        assertThat(pos.move()).isEqualTo(Position2D.of(5, 4, Direction.UP));
    }

    @Test
    void testMoveLoop() {
        Position2D pos = Position2D.of(0, 0, Direction.UP);
        assertThat(pos.moveLoop(2, 2)).isEqualTo(Position2D.of(0, 2, Direction.UP));
    }
}
