package algorithm;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class PoorMultiThreadSolver {
    public static final Random random = new Random();
    public static ExecutorService executorService;
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

    private static class TaskResultBundle {
        private final int[][] matrix;
        private final int result;
        private TaskResultBundle(int[][] matrix, int result) {
            this.matrix = matrix;
            this.result = result;
        }
    }

    public static FutureTask<TaskResultBundle> createTask(int[][] matrix, double probability, int step) {
        return new FutureTask<>(() -> {
            int[][] copy = deepCopy(matrix);
            mutate(copy, probability, step);
            return new TaskResultBundle(copy, evaluate(copy));
        });
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int dimension = 20;
        int THREAD_NUM = 1;
        int TASK_NUM = 10;
        int size = dimension * dimension;
        int[][] matrix = new int[dimension][dimension];
        do {
            double temperature = 100;
            final double alpha = 0.99;
            int count = 100000;
            List<FutureTask<TaskResultBundle>> taskList = new LinkedList<>();
            magicConstant = magicConstant(dimension);
            executorService = Executors.newFixedThreadPool(8);
            initMatrix(matrix);
            a: do {
                int curResult = evaluate(matrix);
                while (taskList.size() < TASK_NUM) {
                    FutureTask<TaskResultBundle> task = createTask(matrix, 1, size);
                    executorService.execute(task);
                    taskList.add(task);
                }
                while (taskList.size() != 0) {
                    FutureTask<TaskResultBundle> task = taskList.get(0);
                    if (task.isDone()) {
                        TaskResultBundle taskResult = null;
                        try {
                            taskResult = task.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        if (taskResult != null) {
                            int diff = taskResult.result - curResult;
                            if (diff < 0 || randomInt(0, 1) < Math.exp(-diff / temperature)) {
                                matrix = taskResult.matrix;
                            } else {
                                count--;
                            }
                            if (count == 0) break a;
                            temperature *= alpha;
                            taskList.remove(task);
                        }
                    }
                }
            } while(evaluate(matrix) != 0);
            System.out.println(evaluate(matrix));
            executorService.shutdown();
        } while(evaluate(matrix) != 0);
        printMatrix(matrix);
        System.out.println(evaluate(matrix));
        System.out.println(System.currentTimeMillis() - startTime);
    }
}