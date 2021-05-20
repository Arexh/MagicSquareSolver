package algorithm.base;

import util.RandomUtil;

import java.util.*;

public class SimulatedAnnealing {
    private static int[][] testCaseOne() {
        return new int[][]{
                {0, 2, 4, 0, 0, 7, 0, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 8, 0, 4, 1, 5},
                {4, 3, 1, 0, 0, 5, 0, 0, 0},
                {5, 0, 0, 0, 0, 0, 0, 3, 2},
                {7, 9, 0, 0, 0, 0, 0, 6, 0},
                {2, 0, 9, 7, 1, 0, 8, 0, 0},
                {0, 4, 0, 0, 9, 3, 0, 0, 0},
                {3, 1, 0, 0, 0, 4, 7, 5, 0}};
    }

    private static int[][] sample() {
        return new int[][] {
                {1, 2, 4, 1, 5, 7, 6, 9, 2},
                {6, 5, 9, 3, 4, 2, 3, 8, 7},
                {8, 7, 3, 6, 8, 9, 4, 1, 5},
                {4, 3, 1, 6, 8, 5, 4, 7, 9},
                {5, 2, 6, 7, 1, 9, 1, 3, 2},
                {7, 9, 8, 4, 3, 2, 8, 6, 5},
                {2, 7, 9, 7, 1, 8, 8, 2, 1},
                {8, 4, 6, 2, 9, 3, 9, 3, 4},
                {3, 1, 5, 5, 6, 4, 7, 5, 6}
        };
    }

    private static int[][] testCaseTwo() {
        return new int[][]{
                {0, 0, 0, 0, 0, 0, 2, 15, 0, 0, 0, 1, 0, 4, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 16, 8, 0, 0, 0, 2},
                {7, 0, 0, 0, 16, 12, 0, 0, 0, 0, 0, 0, 1, 3, 0, 0},
                {0, 0, 6, 1, 0, 0, 8, 9, 14, 11, 0, 0, 13, 0, 0, 7},
                {0, 0, 0, 0, 0, 0, 0, 0, 11, 14, 0, 10, 2, 0, 0, 13},
                {3, 0, 2, 8, 0, 5, 12, 4, 0, 0, 0, 13, 0, 11, 0, 6},
                {0, 6, 11, 0, 15, 1, 14, 10, 0, 0, 0, 0, 0, 0, 8, 16},
                {0, 0, 1, 0, 0, 0, 0, 16, 0, 5, 12, 0, 0, 0, 14, 0},
                {1, 0, 0, 10, 0, 0, 0, 0, 0, 16, 2, 0, 3, 0, 0, 0},
                {0, 9, 0, 0, 0, 14, 0, 2, 15, 8, 0, 12, 0, 13, 0, 0},
                {0, 0, 7, 0, 0, 0, 0, 0, 1, 0, 0, 0, 10, 16, 0, 9},
                {0, 16, 0, 0, 0, 8, 0, 0, 7, 0, 0, 0, 0, 0, 0, 12},
                {0, 0, 0, 0, 0, 2, 15, 0, 4, 12, 0, 0, 0, 0, 0, 0},
                {0, 11, 8, 0, 13, 0, 0, 0, 0, 0, 0, 0, 6, 0, 2, 4},
                {0, 0, 5, 0, 0, 16, 0, 8, 0, 9, 0, 15, 0, 0, 0, 0},
                {9, 13, 0, 15, 0, 0, 0, 11, 3, 0, 7, 2, 14, 0, 0, 1}};
    }

    private static int[][] perfect() {
        return new int[][]{
                {1,2,4,9,5,7,3,8,6},
            {6,8,5,3,4,1,2,9,7},
            {9,7,3,6,8,2,4,1,5},
            {4,3,1,2,6,5,9,7,8},
            {5,6,8,4,7,9,1,3,2},
            {7,9,2,1,3,8,5,6,4},
            {2,5,9,7,1,6,8,4,3},
            {8,4,7,5,9,3,6,2,1},
            {3,1,6,8,2,4,7,5,9}};
    }

    private static int costFunction(int[][] matrix) {
        int n = matrix.length;
        int result = 0;
        for (int i = 0; i < n; i++) {
            Set<Integer> row = new HashSet<>(n);
            Set<Integer> col = new HashSet<>(n);
            for (int j = 0; j < n; j++) {
                if (row.contains(matrix[i][j])) {
                    result++;
                } else {
                    row.add(matrix[i][j]);
                }
                if (col.contains(matrix[j][i])) {
                    result++;
                } else {
                    col.add(matrix[j][i]);
                }
            }
        }
        return result;
    }

//    private static int[][] mutate(int[][] matrix, int[][] origin) {
//        int n = (int) Math.sqrt(matrix.length);
//        int randomIndex = RandomUtil.randomInt(0, matrix.length);
//        int row = (randomIndex / n) * n;
//        int col = (randomIndex % n) * n;
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                int nextRow = row + i;
//                int nextCol = col + j;
//                if (origin[nextRow][nextCol] == 0) {
//                    list.add(nextRow * matrix.length + nextCol);
//                }
//            }
//        }
//        Collections.shuffle(list);
//        if (list.size() >= 2) {
//            swap(matrix, list.get(0), list.get(1));
//        }
//        return matrix;
//    }

    private static int[][] mutate(int[][] matrix, int[][] origin) {
        int n = (int) Math.sqrt(matrix.length);
        for (int randomIndex = 0; randomIndex < matrix.length; randomIndex++) {
            if (!RandomUtil.randomBoolean(1.0 / matrix.length)) continue;
            int row = (randomIndex / n) * n;
            int col = (randomIndex % n) * n;
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int nextRow = row + i;
                    int nextCol = col + j;
                    if (origin[nextRow][nextCol] == 0) {
                        list.add(nextRow * matrix.length + nextCol);
                    }
                }
            }
            int indexOne = RandomUtil.randomInt(0, list.size());
            int indexTwo = RandomUtil.randomInt(0, list.size());
            swap(matrix, list.get(indexOne), list.get(indexTwo));
        }
        return matrix;
    }

    private static void swap(int[][] matrix, int indexOne, int indexTwo) {
        int n = matrix.length;
        swap(matrix, indexOne / n, indexOne % n, indexTwo / n, indexTwo % n);
    }

    private static void swap(int[][] matrix, int rowOne, int colOne, int rowTwo, int colTwo) {
        int temp = matrix[rowOne][colOne];
        matrix[rowOne][colOne] = matrix[rowTwo][colTwo];
        matrix[rowTwo][colTwo] = temp;
    }

    private static int[][] initMatrix(int[][] matrix) {
        int n = (int) Math.sqrt(matrix.length);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Set<Integer> set = new HashSet<>();
                for (int x = 0; x < n; x++) {
                    for (int y = 0; y < n; y++) {
                        int r = i * n + x;
                        int c = j * n + y;
                        if (matrix[r][c] != 0) {
                            set.add(matrix[r][c]);
                        }
                    }
                }
                List<Integer> list = new LinkedList<>();
                for (int x = 1; x <= matrix.length; x++) {
                    if (!set.contains(x)) {
                        list.add(x);
                    }
                }
                Collections.shuffle(list);
                int index = 0;
                for (int x = 0; x < n; x++) {
                    for (int y = 0; y < n; y++) {
                        int r = i * n + x;
                        int c = j * n + y;
                        if (matrix[r][c] == 0) {
                            matrix[r][c] = list.get(index++);
                        }
                    }
                }
            }
        }
        return matrix;
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix[0].length - 1; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println(ints[matrix[0].length - 1]);
        }
    }

    private static int[][] deepCopy(int[][] matrix) {
        return Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
    }

    public static void main(String[] args) {
        int[][] init = testCaseOne();
        int[][] matrix = initMatrix(deepCopy(init));
        long startTime = System.currentTimeMillis();
        while (costFunction(matrix) != 0) {
            matrix = initMatrix(deepCopy(init));
            final double alpha = 0.99;
            double temperature = 1000;
            int count = 10000;
            do {
                int[][] next = deepCopy(matrix);
                mutate(next, init);
                mutate(next, init);
                int diff = costFunction(next) - costFunction(matrix);
                double probability = Math.exp(-diff / temperature);
                if (diff < 0 || RandomUtil.randomInt(0, 1) < probability) {
                    matrix = next;
                } else {
                    count--;
                }
                if (count == 0) break;
                temperature *= alpha;
            } while (costFunction(matrix) != 0);
            System.out.println(costFunction(matrix));
        }
        printMatrix(matrix);
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
