package com.adventofcode.common.point;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

class Point3DTest {

    @Test
    void testOf() {
        Point3D p = Point3D.of(1, 2, 3);
        assertThat(p.x()).isEqualTo(1);
        assertThat(p.y()).isEqualTo(2);
        assertThat(p.z()).isEqualTo(3);
    }

    @Test
    void testConstructorFromPoint2D() {
        Point2D p2d = Point2D.of(5, 10);
        Point3D p3d = new Point3D(p2d, 15);
        assertThat(p3d).isEqualTo(Point3D.of(5, 10, 15));
    }

    @Test
    void testDistance() {
        Point3D p1 = Point3D.of(0, 0, 0);
        Point3D p2 = Point3D.of(1, 1, 1);
        // sqrt(1^2 + 1^2 + 1^2) = sqrt(3) approx 1.73205
        assertThat(Point3D.distance(p1, p2)).isCloseTo(1.73205, offset(0.00001));
    }

    @Test
    void testAdd() {
        Point3D p1 = Point3D.of(1, 2, 3);
        Point3D p2 = Point3D.of(4, 5, 6);
        assertThat(Point3D.add(p1, p2)).isEqualTo(Point3D.of(5, 7, 9));
    }

    @Test
    void testMinus() {
        Point3D p1 = Point3D.of(10, 10, 10);
        Point3D p2 = Point3D.of(1, 2, 3);
        assertThat(Point3D.minus(p1, p2)).isEqualTo(Point3D.of(9, 8, 7));
        assertThat(Point3D.minus(p1)).isEqualTo(Point3D.of(-10, -10, -10));
    }

    @Test
    void testManhattanDistance() {
        Point3D p1 = Point3D.of(1, 1, 1);
        Point3D p2 = Point3D.of(4, 5, 6);
        // |1-4| + |1-5| + |1-6| = 3 + 4 + 5 = 12
        assertThat(Point3D.manhattanDistance(p1, p2)).isEqualTo(12);
    }

    @Test
    void testProject() {
        Point3D p = Point3D.of(1, 2, 3);
        assertThat(p.project()).isEqualTo(Point2D.of(1, 2));
    }

    @Test
    void testToString() {
        Point3D p = Point3D.of(1, 2, 3);
        assertThat(p.toString()).isEqualTo("(1, 2, 3)");
    }

    @Test
    void testOrigin() {
        assertThat(Point3D.ORIGIN).isEqualTo(Point3D.of(0, 0, 0));
    }
}
