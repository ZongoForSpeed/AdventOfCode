package com.adventofcode.common.point;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Point2DTest {

    @Test
    void testOf() {
        Point2D p = Point2D.of(5, 10);
        assertThat(p.x()).isEqualTo(5);
        assertThat(p.y()).isEqualTo(10);
    }

    @Test
    void testManhattanDistance() {
        Point2D p1 = Point2D.of(1, 1);
        Point2D p2 = Point2D.of(4, 5);
        assertThat(Point2D.manhattanDistance(p1, p2)).isEqualTo(7);
        assertThat(Point2D.manhattanDistance(p2, p1)).isEqualTo(7);
    }

    @Test
    void testMoveDirection() {
        Point2D p = Point2D.of(5, 5);
        assertThat(p.move(Direction.UP)).isEqualTo(Point2D.of(5, 4));
        assertThat(p.move(Direction.DOWN)).isEqualTo(Point2D.of(5, 6));
        assertThat(p.move(Direction.LEFT)).isEqualTo(Point2D.of(4, 5));
        assertThat(p.move(Direction.RIGHT)).isEqualTo(Point2D.of(6, 5));
    }

    @Test
    void testMoveDirectionDistance() {
        Point2D p = Point2D.of(5, 5);
        assertThat(p.move(Direction.UP, 3)).isEqualTo(Point2D.of(5, 2));
        assertThat(p.move(Direction.DOWN, 3)).isEqualTo(Point2D.of(5, 8));
        assertThat(p.move(Direction.LEFT, 3)).isEqualTo(Point2D.of(2, 5));
        assertThat(p.move(Direction.RIGHT, 3)).isEqualTo(Point2D.of(8, 5));
    }

    @Test
    void testMoveRange() {
        Point2D p = Point2D.of(1, 1);
        var xMax = 2;
        var yMax = 2;
        
        assertThat(p.moveRange(Direction.UP, xMax, yMax)).isEqualTo(Point2D.of(1, 0));
        assertThat(p.moveRange(Direction.UP, xMax, yMax).moveRange(Direction.UP, xMax, yMax)).isEqualTo(Point2D.of(1, 0));
        
        assertThat(p.moveRange(Direction.DOWN, xMax, yMax)).isEqualTo(Point2D.of(1, 2));
        assertThat(p.moveRange(Direction.DOWN, xMax, yMax).moveRange(Direction.DOWN, xMax, yMax)).isEqualTo(Point2D.of(1, 2));

        assertThat(p.moveRange(Direction.LEFT, xMax, yMax)).isEqualTo(Point2D.of(0, 1));
        assertThat(p.moveRange(Direction.LEFT, xMax, yMax).moveRange(Direction.LEFT, xMax, yMax)).isEqualTo(Point2D.of(0, 1));

        assertThat(p.moveRange(Direction.RIGHT, xMax, yMax)).isEqualTo(Point2D.of(2, 1));
        assertThat(p.moveRange(Direction.RIGHT, xMax, yMax).moveRange(Direction.RIGHT, xMax, yMax)).isEqualTo(Point2D.of(2, 1));
    }

    @Test
    void testMoveLoop() {
        Point2D p = Point2D.of(0, 0);
        var xMax = 2;
        var yMax = 2;

        assertThat(p.moveLoop(Direction.UP, xMax, yMax)).isEqualTo(Point2D.of(0, 2));
        assertThat(p.moveLoop(Direction.LEFT, xMax, yMax)).isEqualTo(Point2D.of(2, 0));
        
        Point2D pEnd = Point2D.of(2, 2);
        assertThat(pEnd.moveLoop(Direction.DOWN, xMax, yMax)).isEqualTo(Point2D.of(2, 0));
        assertThat(pEnd.moveLoop(Direction.RIGHT, xMax, yMax)).isEqualTo(Point2D.of(0, 2));
    }

    @Test
    void testMovePoint() {
        Point2D p = Point2D.of(5, 5);
        assertThat(p.move(Point2D.of(1, 2))).isEqualTo(Point2D.of(6, 7));
        assertThat(p.move(Point2D.of(-1, -2))).isEqualTo(Point2D.of(4, 3));
    }

    @Test
    void testMovePointLoop() {
        Point2D p = Point2D.of(1, 1);
        var xMax = 5;
        var yMax = 5;
        
        assertThat(p.moveLoop(Point2D.of(5, 0), xMax, yMax)).isEqualTo(Point2D.of(1, 1));
        assertThat(p.moveLoop(Point2D.of(0, 5), xMax, yMax)).isEqualTo(Point2D.of(1, 1));
        assertThat(p.moveLoop(Point2D.of(4, 4), xMax, yMax)).isEqualTo(Point2D.of(0, 0));
        assertThat(p.moveLoop(Point2D.of(-2, -2), xMax, yMax)).isEqualTo(Point2D.of(4, 4));
    }

    @Test
    void testCompareTo() {
        Point2D p1 = Point2D.of(1, 1);
        Point2D p2 = Point2D.of(2, 1);
        Point2D p3 = Point2D.of(1, 2);

        assertThat(p1.compareTo(p2)).isNegative();
        assertThat(p2.compareTo(p1)).isPositive();
        assertThat(p1.compareTo(p3)).isNegative();
        assertThat(p3.compareTo(p1)).isPositive();
        assertThat(p1).isEqualByComparingTo(p1);
    }

    @Test
    void testAdjacent() {
        assertThat(Point2D.ADJACENT).hasSize(8)
                .containsExactlyInAnyOrder(
                        Point2D.of(-1, -1), Point2D.of(-1, 0), Point2D.of(-1, 1),
                        Point2D.of(0, -1),                     Point2D.of(0, 1),
                        Point2D.of(1, -1),  Point2D.of(1, 0),  Point2D.of(1, 1)
                );
    }
}
