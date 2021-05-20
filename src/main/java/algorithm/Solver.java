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

    public static void mutate(int[][] matrix, double probability, int step) {
        int dimension = matrix.length;
        int size = dimension * dimension;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (randomBoolean(probability / size)) {
                    int index = i * dimension + j;
                    int randomIndex = (index + randomInt(-step, step + 1) + size) % size;
                    swap(matrix, index, randomIndex);
                }
            }
        }
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

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int dimension = 20;
        int size = dimension * dimension;
        int[][] matrix = new int[dimension][dimension];
        do {
            double temperature = 100;
            final double alpha = 0.99;
            int count = 1000000;
            magicConstant = magicConstant(dimension);
            initMatrix(matrix);
            do {
                int[][] explore = deepCopy(matrix);
                mutate(explore, 1, size);
                int diff = evaluate(explore) - evaluate(matrix);
                if (diff < 0 || randomInt(0, 1) < Math.exp(-diff / temperature)) {
                    matrix = explore;
                } else {
                    count--;
                }
                if (count == 0) break;
                temperature *= alpha;
            } while(evaluate(matrix) != 0);
            System.out.println(evaluate(matrix));
        } while(evaluate(matrix) != 0);
        printMatrix(matrix);
        System.out.println(evaluate(matrix));
        System.out.println(System.currentTimeMillis() - startTime);
    }
}