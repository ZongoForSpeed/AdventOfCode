package com.adventofcode.matrix;

import com.adventofcode.point.Point3D;

import java.util.Arrays;

public class Matrix3D {
    private final int[][] m;

    public Matrix3D() {
        this.m = new int[3][3];
    }

    public Matrix3D(Point3D a, Point3D b, Point3D c) {
        this();
        this.m[0][0] = a.x();
        this.m[0][1] = b.x();
        this.m[0][2] = c.x();
        this.m[1][0] = a.y();
        this.m[1][1] = b.y();
        this.m[1][2] = c.y();
        this.m[2][0] = a.z();
        this.m[2][1] = b.z();
        this.m[2][2] = c.z();
    }

    public int determinant() {
        int det = 0;
        for (int i = 0; i < 3; ++i) {
            det += (m[0][i] * (m[1][(i + 1) % 3] * m[2][(i + 2) % 3] - m[1][(i + 2) % 3] * m[2][(i + 1) % 3]));
        }
        return det;
    }

    public Matrix3D inverse() {
        int det = determinant();
        Matrix3D inverse = new Matrix3D();
        inverse.m[0][0] = ((m[1][1] * m[2][2]) - (m[1][2] * m[2][1])) / det;
        inverse.m[0][1] = (-((m[0][1] * m[2][2]) - (m[0][2] * m[2][1]))) / det;
        inverse.m[0][2] = ((m[0][1] * m[1][2]) - (m[0][2] * m[1][1])) / det;
        inverse.m[1][0] = (-((m[1][0] * m[2][2]) - (m[1][2] * m[2][0]))) / det;
        inverse.m[1][1] = ((m[0][0] * m[2][2]) - (m[0][2] * m[2][0])) / det;
        inverse.m[1][2] = (-((m[0][0] * m[1][2]) - (m[0][2] * m[1][0]))) / det;
        inverse.m[2][0] = ((m[1][0] * m[2][1]) - (m[1][1] * m[2][0])) / det;
        inverse.m[2][1] = (-((m[0][0] * m[2][1]) - (m[0][1] * m[2][0]))) / det;
        inverse.m[2][2] = ((m[0][0] * m[1][1]) - (m[0][1] * m[1][0])) / det;
        return inverse;
    }

    public Point3D apply(Point3D p) {
        return Point3D.of(p.x() * m[0][0] + p.y() * m[0][1] + p.z() * m[0][2],
                p.x() * m[1][0] + p.y() * m[1][1] + p.z() * m[1][2],
                p.x() * m[2][0] + p.y() * m[2][1] + p.z() * m[2][2]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Matrix3D");
        sb.append('[');
        for (int[] ints : m) {
            sb.append(Arrays.toString(ints));
        }
        sb.append(']');
        return sb.toString();
    }
}
