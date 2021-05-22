package com.arexh.magicsquare.algorithm;

public class HelperFunction {

    public static int evaluateMagicSquare(int[][] matrix) {
        int dimension = matrix.length;
        int result = 0;
        int magicConstant = magicConstant(dimension);
        int diagonal = -magicConstant;
        int antiDiagonal = -magicConstant;

        for(int i = 0; i < dimension; ++i) {
            int row = -magicConstant;
            int col = -magicConstant;

            for(int j = 0; j < dimension; ++j) {
                row += matrix[i][j];
                col += matrix[j][i];
            }

            result += Math.abs(row) + Math.abs(col);
            diagonal += matrix[i][i];
            antiDiagonal += matrix[i][dimension - i - 1];
        }

        result += Math.abs(diagonal) + Math.abs(antiDiagonal);
        return result;
    }

    public static int magicConstant(int dimension) {
        return (dimension * (dimension * dimension + 1)) / 2;
    }
}
