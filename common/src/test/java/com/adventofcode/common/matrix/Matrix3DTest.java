package com.adventofcode.common.matrix;

import com.adventofcode.common.point.Point3D;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Matrix3DTest {

    @Test
    void testDeterminant() {
        // | 1 2 3 |
        // | 0 1 4 | = 1*(1*1 - 4*1) - 2*(0*1 - 4*2) + 3*(0*1 - 1*2)
        // | 2 1 1 | = 1*(-3) - 2*(-8) + 3*(-2) = -3 + 16 - 6 = 7
        var m = new Matrix3D(
                Point3D.of(1, 0, 2),
                Point3D.of(2, 1, 1),
                Point3D.of(3, 4, 1)
        );
        assertThat(m.determinant()).isEqualTo(7);
    }

    @Test
    void testInverse() {
        // Matrix:
        // [ 1 2 1 ]
        // [ 0 1 0 ]
        // [ 1 0 1 ]
        // Determinant: 1(1-0) - 2(0-0) + 1(0-1) = 1 - 1 = 0 -> Not invertible
        
        var mNonInv = new Matrix3D(
                Point3D.of(1, 0, 1),
                Point3D.of(2, 1, 0),
                Point3D.of(1, 0, 1)
        );
        assertThat(mNonInv.determinant()).isEqualTo(0);
        assertThatThrownBy(mNonInv::inverse)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Matrix is not invertible");

        // Invertible Matrix:
        // [ 1 0 0 ]
        // [ 0 2 0 ]
        // [ 0 0 1 ]
        // Det = 2
        // Inverse should have [1 0 0], [0 0.5 0], [0 0 1] -> but it's int based!
        // The implementation uses integer division! 
        // inverse.m[1][1] = ((m[0][0] * m[2][2]) - (m[0][2] * m[2][0])) / det;
        // inverse.m[1][1] = (1*1 - 0*0) / 2 = 1 / 2 = 0
        // Wait, if it's integer based, it's only useful for matrices with det = 1 or -1, 
        // or where elements are divisible by det.

        var m = new Matrix3D(
                Point3D.of(1, 0, 0),
                Point3D.of(0, 1, 0),
                Point3D.of(0, 0, 1)
        );
        Matrix3D inv = m.inverse();
        assertThat(inv).isEqualTo(m);
    }

    @Test
    void testApply() {
        // [ 1 2 3 ]   [ 1 ]   [ 1*1 + 2*2 + 3*3 ]   [ 14 ]
        // [ 4 5 6 ] * [ 2 ] = [ 4*1 + 5*2 + 6*3 ] = [ 32 ]
        // [ 7 8 9 ]   [ 3 ]   [ 7*1 + 8*2 + 9*3 ]   [ 50 ]
        var m = new Matrix3D(
                Point3D.of(1, 4, 7),
                Point3D.of(2, 5, 8),
                Point3D.of(3, 6, 9)
        );
        Point3D p = Point3D.of(1, 2, 3);
        Point3D result = m.apply(p);
        assertThat(result).isEqualTo(Point3D.of(14, 32, 50));
    }

    @Test
    void testToString() {
        var m = new Matrix3D(
                Point3D.of(1, 0, 0),
                Point3D.of(0, 1, 0),
                Point3D.of(0, 0, 1)
        );
        assertThat(m.toString()).isEqualTo("Matrix3D[[1, 0, 0][0, 1, 0][0, 0, 1]]");
    }
}
