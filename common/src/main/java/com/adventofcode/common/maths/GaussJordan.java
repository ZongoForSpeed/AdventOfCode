package com.adventofcode.common.maths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.StringJoiner;

public final class GaussJordan {

    private static final Logger LOGGER = LoggerFactory.getLogger(GaussJordan.class);

    private final double[][] a;
    private final int n;

    public GaussJordan(double[][] a, int n) {
        this.a = a;
        this.n = n;
    }

    public GaussJordan(double[][] matrixA, double[] vectorB) {
        this.a = new double[matrixA.length][matrixA.length + 1];
        this.n = matrixA.length;

        for (int i = 0; i < matrixA.length; i++) {
            System.arraycopy(matrixA[i], 0, a[i], 0, matrixA[i].length);
            a[i][matrixA.length] = vectorB[i];
        }
    }

    // Function to print the matrix
    void printMatrix() {
        StringJoiner joiner = new StringJoiner(",\n", "[", "]");

        for (int i = 0; i < n; i++) {
            joiner.add(Arrays.toString(a[i]));
        }

        LOGGER.info("printMatrix:\n{}", joiner);
    }

    // function to reduce matrix to reduced
// row echelon form.
    int performOperation() {
        int flag = 0;

        // Performing elementary operations
        for (int i = 0; i < n; i++) {
            if (a[i][i] == 0) {
                int c = 1;
                while ((i + c) < n && a[i + c][i] == 0)
                    c++;
                if ((i + c) == n) {
                    flag = 1;
                    break;
                }
                for (int k = 0; k <= n; k++) {
                    double temp = a[i][k];
                    a[i][k] = a[i + c][k];
                    a[i + c][k] = temp;
                }
            }

            for (int j = 0; j < n; j++) {

                // Excluding all i == j
                if (i != j) {

                    // Converting Matrix to reduced row
                    // echelon form(diagonal matrix)
                    double p = a[j][i] / a[i][i];

                    for (int k = 0; k <= n; k++){
                        a[j][k] = a[j][k] - a[i][k] * p;
                    }
                }
            }
        }
        return flag;
    }

    // Function to print the desired result
    // if unique solutions exists, otherwise
    // prints no solution or infinite solutions
    // depending upon the input given.
    double[] getResult(int flag) {
        switch (flag) {
            case 2 -> throw new IllegalStateException("Infinite Solutions Exists");
            case 3 -> throw new IllegalStateException("No Solution Exists");
            default -> {
                // Printing the solution by dividing constants by
                // their respective diagonal elements
                double[] solution = new double[n];
                for (int i = 0; i < n; i++)
                    solution[i] = a[i][n] / a[i][i];
                return solution;
            }
        }
    }

    // To check whether infinite solutions
    // exists or no solution exists
    int checkConsistency() {

        // flag == 2 for infinite solution
        // flag == 3 for No solution
        int flag = 3;
        for (int i = 0; i < n; i++) {
            double sum = 0;
            int j;
            for (j = 0; j < n; j++)
                sum = sum + a[i][j];
            if (sum == a[i][j]) {
                flag = 2;
                break;
            }
        }
        return flag;
    }

    public double[] solve() {
        int flag;
        // Performing Matrix transformation
        flag = performOperation();

        if (flag == 1)
            flag = checkConsistency();

        // Printing Final Matrix
        printMatrix();

        // Get Solutions(if exist)
        return getResult(flag);
    }
}

