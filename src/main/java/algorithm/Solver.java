package algorithm;

import java.util.*;

public class Solver {
    public static final Random random = new Random();
    private static int magicConstant;
    public static void initMatrix(int[][] matrix) {
        int dimension = matrix.length;
        int size = dimension * dimension;
        List<Integer> list = new ArrayList<>(size);
        for (int i = 1; i <= size; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        int count = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                matrix[i][j] = list.get(count++);
            }
        }
    }

    public static void mutate(int[][] matrix, double scale, int step) {
        int dimension = matrix.length;
        int size = dimension * dimension;
        double[][] probabilities = getNormalizedMatrix(matrix);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (randomBoolean(probabilities[i][j] * scale)) {
                    int index = i * dimension + j;
                    int randomIndex = (index + randomInt(-step, step + 1) + size) % size;
                    swap(matrix, index, randomIndex);
                }
            }
        }
    }

    public static double[][] getNormalizedMatrix(int[][] matrix) {
        int dimension = matrix.length;
        double[][] probabilities = new double[dimension][dimension];
        int[] rowCost = new int[dimension];
        int[] colCost = new int[dimension];
        int diagonalCost = -magicConstant;
        int antiDiagonalCost = -magicConstant;
        for (int i = 0; i < dimension; i++) {
            rowCost[i] = -magicConstant;
            colCost[i] = -magicConstant;
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                rowCost[i] += matrix[i][j];
                colCost[i] += matrix[j][i];
            }
            rowCost[i] = Math.abs(rowCost[i]);
            colCost[i] = Math.abs(colCost[i]);
        }
        for (int i = 0; i < dimension; i++) {
            diagonalCost += matrix[i][i];
            antiDiagonalCost += matrix[i][dimension - 1 - i];
        }
        diagonalCost = Math.abs(diagonalCost);
        antiDiagonalCost = Math.abs(antiDiagonalCost);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                probabilities[i][j] = rowCost[i] + colCost[j];
            }
            probabilities[i][i] += diagonalCost;
            probabilities[i][dimension - 1 - i] += antiDiagonalCost;
        }
        int sum = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sum += probabilities[i][j];
            }
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                probabilities[i][j] /= sum;
            }
        }
        return probabilities;
    }

    public static int evaluate(int[][] matrix) {
        int dimension = matrix.length;
        int result = 0;
        int diagonal = -magicConstant;
        int antiDiagonal = -magicConstant;
        for (int i = 0; i < dimension; i++) {
            int row = -magicConstant;
            int col = -magicConstant;
            for (int j = 0; j < dimension; j++) {
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

    public static void swap(int[][] matrix, int indexOne, int indexTwo) {
        int dimension = matrix.length;
        swap(matrix, indexOne / dimension, indexOne % dimension,
                indexTwo / dimension, indexTwo % dimension);
    }

    public static void swap(int[][] matrix, int rowOne, int colOne, int rowTwo, int colTwo) {
        int temp = matrix[rowOne][colOne];
        matrix[rowOne][colOne] = matrix[rowTwo][colTwo];
        matrix[rowTwo][colTwo] = temp;
    }

    public static int randomInt(int start, int end) {
        return start + random.nextInt(end - start);
    }

    public static boolean randomBoolean(double probability) {
        return random.nextDouble() < probability;
    }

    public static void printMatrix(int[][] matrix) {
        int dimension = matrix.length;
        for (int[] ints : matrix) {
            for (int j = 0; j < dimension - 1; j++) {
                System.out.print(ints[j] + "\t");
            }
            System.out.println(ints[dimension - 1]);
        }
    }

    public static int[][] deepCopy(int[][] matrix) {
        return Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
    }

    public static int magicConstant(int dimension) {
        return (dimension * (dimension * dimension + 1)) / 2;
    }

    public static final int DIMENSION = 5;
    public static final int EVAL_TIMES = 30;
    public static final double TEMPERATURE = 485760;
    public static final double ALPHA = 0.872847396394329;
    public static final int EARLY_STOP = 1201;
    public static final double SCALE = 0.973968144434559;

    // PARAMS: DIMENSION, EVAL_TIMES, TEMPERATURE, ALPHA, EARLY_STOP, SCALE
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < EVAL_TIMES; i++) {
            int size = DIMENSION * DIMENSION;
            int[][] matrix = new int[DIMENSION][DIMENSION];
            do {
                double temperature = TEMPERATURE;
                int count = EARLY_STOP;
                magicConstant = magicConstant(DIMENSION);
                initMatrix(matrix);
                do {
                    int[][] explore = deepCopy(matrix);
                    mutate(explore, SCALE, size);
                    int diff = evaluate(explore) - evaluate(matrix);
                    if (diff < 0 || randomInt(0, 1) < Math.exp(-diff / temperature)) {
                        matrix = explore;
                    } else {
                        count--;
                    }
                    if (count == 0) break;
                    temperature *= ALPHA;
                } while(evaluate(matrix) != 0);
//                System.out.println(evaluate(matrix));
            } while(evaluate(matrix) != 0);
//            printMatrix(matrix);
//            System.out.println(evaluate(matrix));
        }
//        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println((System.currentTimeMillis() - startTime) / (double) EVAL_TIMES);
    }
}