package com.treasurehunting.java.math;

public class Matrix {

    public static float[][] multiply(float[][] aMatrix, float[][] bMatrix) {
        if (aMatrix[0].length != bMatrix.length) {
            System.out.println("ERROR: A matrix columns does not equal B matrix rows");
            return identity(aMatrix);
        }

        float[][] outMatrix = new float[aMatrix.length][bMatrix[0].length];

        float temp;
        for (int i = 0; i < aMatrix.length; i++) {
            for (int j = 0; j < bMatrix[0].length; j++) {
                temp = 0;
                for (int k = 0; k < aMatrix[0].length; k++) {
                    temp += aMatrix[i][k] * bMatrix[k][j];
                }
                outMatrix[i][j] = temp;
            }
        }

        return outMatrix;
    }

    public static float[][] add(float[][] aMatrix, float[][] bMatrix) {
        if (aMatrix.length != bMatrix.length || aMatrix[0].length != bMatrix[0].length ) {
            System.out.println("ERROR: A matrix dimensions does not equal B matrix dimensions");
            return identity(aMatrix);
        }

        float[][] outMatrix = new float[aMatrix.length][bMatrix[0].length];

        for (int i = 0; i < aMatrix.length; i++) {
            for (int j = 0; j < bMatrix[0].length; j++) {
                outMatrix[i][j] = aMatrix[i][j] + bMatrix[i][j];
            }
        }

        return outMatrix;
    }

    public static float[][] add(float[][] matrix, float value) {
        float[][] outMatrix = new float[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                outMatrix[i][j] = value + matrix[i][j];
            }
        }

        return outMatrix;
    }

    private static float[][] identity(float[][] matrix) {
        float[][] outMatrix = new float[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            outMatrix[i][i] = 1;
        }

        return outMatrix;
    }

}
