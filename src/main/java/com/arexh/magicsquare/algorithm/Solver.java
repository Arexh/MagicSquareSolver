//package com.arexh.magicsquare.algorithm;
//
//import edu.cmu.cs.xfxie.MagicSquareSolver;
//
//import java.util.*;
//
//public class Solver {
//    public static final Random random = new Random();
//    private static int magicConstant;
//    public static void initMatrix(int[][] matrix) {
//        int dimension = matrix.length;
//        int size = dimension * dimension;
//        List<Integer> list = new ArrayList<>(size);
//        for (int i = 1; i <= size; i++) {
//            list.add(i);
//        }
//        Collections.shuffle(list);
//        int count = 0;
//        for (int i = 0; i < dimension; i++) {
//            for (int j = 0; j < dimension; j++) {
//                matrix[i][j] = list.get(count++);
//            }
//        }
//    }
//
//    public static void mutate(int[][] matrix, double scale, int step) {
//        int dimension = matrix.length;
//        int size = dimension * dimension;
//        double[][] probabilities = getNormalizedMatrix(matrix);
//        for (int i = 0; i < dimension; i++) {
//            for (int j = 0; j < dimension; j++) {
//                if (randomBoolean(probabilities[i][j] * scale)) {
//                    int index = i * dimension + j;
//                    int randomIndex = (index + randomInt(-step, step + 1) + size) % size;
//                    swap(matrix, index, randomIndex);
//                }
//            }
//        }
//    }
//
//    public static double[][] getNormalizedMatrix(int[][] matrix) {
//        int dimension = matrix.length;
//        double[][] probabilities = new double[dimension][dimension];
//        int[] rowCost = new int[dimension];
//        int[] colCost = new int[dimension];
//        int diagonalCost = -magicConstant;
//        int antiDiagonalCost = -magicConstant;
//        for (int i = 0; i < dimension; i++) {
//            rowCost[i] = -magicConstant;
//            colCost[i] = -magicConstant;
//        }
//        for (int i = 0; i < dimension; i++) {
//            for (int j = 0; j < dimension; j++) {
//                rowCost[i] += matrix[i][j];
//                colCost[i] += matrix[j][i];
//            }
//            rowCost[i] = Math.abs(rowCost[i]);
//            colCost[i] = Math.abs(colCost[i]);
//        }
//        for (int i = 0; i < dimension; i++) {
//            diagonalCost += matrix[i][i];
//            antiDiagonalCost += matrix[i][dimension - 1 - i];
//        }
//        diagonalCost = Math.abs(diagonalCost);
//        antiDiagonalCost = Math.abs(antiDiagonalCost);
//        for (int i = 0; i < dimension; i++) {
//            for (int j = 0; j < dimension; j++) {
//                probabilities[i][j] = rowCost[i] + colCost[j];
//            }
//            probabilities[i][i] += diagonalCost;
//            probabilities[i][dimension - 1 - i] += antiDiagonalCost;
//        }
//        int sum = 0;
//        for (int i = 0; i < dimension; i++) {
//            for (int j = 0; j < dimension; j++) {
//                sum += probabilities[i][j];
//            }
//        }
//        for (int i = 0; i < dimension; i++) {
//            for (int j = 0; j < dimension; j++) {
//                probabilities[i][j] /= sum;
//            }
//        }
//        return probabilities;
//    }
//
//    public static int evaluate(int[][] matrix) {
//        int dimension = matrix.length;
//        int result = 0;
//        magicConstant = magicConstant(dimension);
//        int diagonal = -magicConstant;
//        int antiDiagonal = -magicConstant;
//        for (int i = 0; i < dimension; i++) {
//            int row = -magicConstant;
//            int col = -magicConstant;
//            for (int j = 0; j < dimension; j++) {
//                row += matrix[i][j];
//                col += matrix[j][i];
//            }
//            result += Math.abs(row) + Math.abs(col);
//            diagonal += matrix[i][i];
//            antiDiagonal += matrix[i][dimension - i - 1];
//        }
//        result += Math.abs(diagonal) + Math.abs(antiDiagonal);
//        return result;
//    }
//
//    public static void swap(int[][] matrix, int indexOne, int indexTwo) {
//        int dimension = matrix.length;
//        swap(matrix, indexOne / dimension, indexOne % dimension,
//                indexTwo / dimension, indexTwo % dimension);
//    }
//
//    public static void swap(int[][] matrix, int rowOne, int colOne, int rowTwo, int colTwo) {
//        int temp = matrix[rowOne][colOne];
//        matrix[rowOne][colOne] = matrix[rowTwo][colTwo];
//        matrix[rowTwo][colTwo] = temp;
//    }
//
//    public static int randomInt(int start, int end) {
//        return start + random.nextInt(end - start);
//    }
//
//    public static boolean randomBoolean(double probability) {
//        return random.nextDouble() < probability;
//    }
//
//    public static void printMatrix(int[][] matrix) {
//        int dimension = matrix.length;
//        for (int[] ints : matrix) {
//            for (int j = 0; j < dimension - 1; j++) {
//                System.out.print(ints[j] + "\t");
//            }
//            System.out.println(ints[dimension - 1]);
//        }
//    }
//
//    public static int[][] deepCopy(int[][] matrix) {
//        return Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
//    }
//
//    public static int magicConstant(int dimension) {
//        return (dimension * (dimension * dimension + 1)) / 2;
//    }
//
//    public static final int DIMENSION = 7;
//    public static final int EVAL_TIMES = 30;
//    public static final double TEMPERATURE = 485760;
//    public static final double ALPHA = 0.872847396394329;
//    public static final int EARLY_STOP = 1201;
//    public static final double SCALE = 0.973968144434559;
//    public static int[][] matrix = new int[][]{{188,79,38,9,330,85,276,187,310,59,314,327,309,334,183,36,31,200,336,379},
//            {94,242,258,271,313,41,302,389,328,234,42,300,34,154,3,303,47,263,280,112},
//            {186,267,341,197,157,338,220,305,125,207,249,190,284,113,304,93,374,35,10,15},
//            {132,241,346,60,33,70,225,147,89,279,224,306,278,23,172,362,50,326,394,253},
//            {90,344,384,91,73,308,95,151,71,124,339,165,377,245,375,133,387,27,58,173},
//            {274,363,367,396,48,30,13,45,359,299,251,214,218,208,43,102,158,20,316,286},
//            {201,138,361,223,196,354,192,368,176,257,117,335,119,61,252,163,392,75,14,16},
//            {312,229,323,142,216,320,291,319,99,261,149,231,189,240,227,57,373,19,6,7},
//            {101,72,24,122,297,161,63,177,264,92,369,210,171,32,318,366,398,349,46,378},
//            {322,232,164,365,64,385,212,246,100,88,4,162,230,268,262,179,174,140,272,141},
//            {353,255,228,17,390,62,25,2,135,351,357,86,281,145,219,364,18,98,233,391},
//            {83,217,307,129,356,215,168,175,395,298,106,37,152,209,103,84,144,205,340,287},
//            {213,269,130,270,160,66,204,221,266,53,256,289,247,180,128,195,51,372,146,294},
//            {296,203,78,211,292,191,331,69,193,178,105,39,5,393,273,321,44,350,52,386},
//            {111,49,155,198,343,82,121,347,108,282,153,159,150,383,54,290,260,380,317,68},
//            {134,148,156,265,127,202,222,332,182,237,115,285,109,184,131,181,250,399,311,40},
//            {315,244,1,382,96,381,301,28,12,355,329,65,118,81,342,277,77,243,80,283},
//            {137,56,97,116,170,360,254,110,275,107,169,324,348,388,55,120,167,358,325,74},
//            {123,295,226,352,235,337,236,21,126,11,29,87,185,293,166,248,239,143,370,288},
//            {345,67,26,194,114,22,259,371,397,238,333,199,206,76,400,136,376,8,104,139}};
//
//    // PARAMS: DIMENSION, EVAL_TIMES, TEMPERATURE, ALPHA, EARLY_STOP, SCALE
//    public static void main(String[] args) {
//
////        long startTime = System.currentTimeMillis();
////        for (int i = 0; i < EVAL_TIMES; i++) {
////            int size = DIMENSION * DIMENSION;
////            int[][] matrix = new int[DIMENSION][DIMENSION];
////            do {
////                double temperature = TEMPERATURE;
////                int count = EARLY_STOP;
////                magicConstant = magicConstant(DIMENSION);
////                initMatrix(matrix);
////                do {
////                    int[][] explore = deepCopy(matrix);
////                    mutate(explore, SCALE, size);
////                    int diff = evaluate(explore) - evaluate(matrix);
////                    if (diff < 0 || randomInt(0, 1) < Math.exp(-diff / temperature)) {
////                        matrix = explore;
////                    } else {
////                        count--;
////                    }
////                    if (count == 0) break;
////                    temperature *= ALPHA;
////                } while(evaluate(matrix) != 0);
//////                System.out.println(evaluate(matrix));
////            } while(evaluate(matrix) != 0);
//////            printMatrix(matrix);
//////            System.out.println(evaluate(matrix));
////        }
////        System.out.println(System.currentTimeMillis() - startTime);
////        System.out.println((System.currentTimeMillis() - startTime) / (double) EVAL_TIMES);
////        System.out.println(evaluate(matrix));
//        int[][] result = MagicSquareSolver.solve(5, 1000, null);
//        printMatrix(result);
//        System.out.println(evaluate(result));
//    }
//}