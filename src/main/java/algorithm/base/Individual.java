package algorithm.base;

import algorithm.base.operator.FitnessFunction;
import util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Individual {
    public final static long FIRST_CREATE_TIME = System.currentTimeMillis();
    public static long individualCount = 0;

    private final int[][] matrix;
    private final FitnessFunction fitnessFunction;
    private final int dimension;
    private final int size;
    private long fitness = -1;

    public Individual(int dimension, FitnessFunction fitnessFunction) {
        this.matrix = new int[dimension][dimension];
        this.size = dimension * dimension;
        this.dimension = dimension;
        this.fitnessFunction = fitnessFunction;
        initMatrix();
        individualCount++;
    }

    public Individual(Individual individual, FitnessFunction fitnessFunction) {
        this.matrix = deepCopy(individual.matrix);
        this.fitnessFunction = fitnessFunction;
        this.dimension = this.matrix.length;
        this.size = this.dimension * this.dimension;
        individualCount++;
    }

    public void setValue(int index, int val) {
        this.matrix[index / this.dimension][index % this.dimension] = val;
    }

    public void setValue(int row, int col, int val) {
        this.matrix[row][col] = val;
    }

    public int getValue(int index) {
        return this.matrix[index / this.dimension][index % this.dimension];
    }

    public int getValue(int row, int col) {
        return this.matrix[row][col];
    }

    public int getDimension() {
        return this.dimension;
    }

    public int getSize() {
        return this.dimension * this.dimension;
    }

    public long getFitness() {
        if (fitness == -1) {
            this.fitness = fitnessFunction.evaluateFitness(this);
        }
        return this.fitness;
    }

    public void shuffleMatrix() {
        List<Integer> list = new ArrayList<>(this.size);
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                list.add(this.matrix[i][j]);
            }
        }
        Collections.shuffle(list);
        int index = 0;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.matrix[i][j] = list.get(index++);
            }
        }
    }

    public void shuffleRow(int row) {
        List<Integer> list = new ArrayList<>(this.dimension);
        for (int i = 0; i < this.dimension; i++) {
            list.add(this.matrix[row][i]);
        }
        Collections.shuffle(list);
        for (int i = 0; i < this.dimension; i++) {
            this.matrix[row][i] = list.get(i);
        }
    }

    public void shuffleColumn(int col) {
        List<Integer> list = new ArrayList<>(this.dimension);
        for (int i = 0; i < this.dimension; i++) {
            list.add(this.matrix[i][col]);
        }
        Collections.shuffle(list);
        for (int i = 0; i < this.dimension; i++) {
            this.matrix[i][col] = list.get(i);
        }
    }

    public void shuffleMainDiagonal() {
        List<Integer> list = new ArrayList<>(this.dimension);
        for (int i = 0; i < this.dimension; i++) {
            list.add(this.matrix[i][i]);
        }
        Collections.shuffle(list);
        for (int i = 0; i < this.dimension; i++) {
            this.matrix[i][i] = list.get(i);
        }
    }

    public void shuffleAntiDiagonal() {
        List<Integer> list = new ArrayList<>(this.dimension);
        for (int i = 0; i < this.dimension; i++) {
            list.add(this.matrix[i][this.dimension - 1 - i]);
        }
        Collections.shuffle(list);
        for (int i = 0; i < this.dimension; i++) {
            this.matrix[i][this.dimension - 1 - i] = list.get(i);
        }
    }

    public void randomSwapTwoNumberInRow(int row) {
        int[] indexes = generateTwoRandomIndex(this.dimension, this.dimension);
        swapTwoNumber(row, indexes[0], row, indexes[1]);
    }

    public void randomSwapTwoNumberInCol(int col) {
        int[] indexes = generateTwoRandomIndex(this.dimension, this.dimension);
        swapTwoNumber(indexes[0], col, indexes[1], col);
    }

    public void randomSwapTwoNumberInMainDiagonal() {
        int[] indexes = generateTwoRandomIndex(this.dimension, this.dimension);
        swapTwoNumber(indexes[0], indexes[0], indexes[1], indexes[1]);
    }

    public void randomSwapTwoNumberInAntiDiagonal() {
        int[] indexes = generateTwoRandomIndex(this.dimension, this.dimension);
        swapTwoNumber(indexes[0], this.dimension - indexes[0] - 1,
                indexes[1], this.dimension - indexes[1] - 1);
    }

//    public void randomSwapTwoNumberInMatrix() {
//        int[] indexes = generateTwoRandomIndex(this.size, this.size);
//        swapTwoNumber(indexes[0] / this.dimension, indexes[0] % this.dimension,
//                indexes[1] / this.dimension, indexes[1] % this.dimension);
//    }
//
//    public void randomSwapTwoNumberInMatrix(int step) {
//        int[] indexes = generateTwoRandomIndex(step, this.size);
//        swapTwoNumber(indexes[0] / this.dimension, indexes[0] % this.dimension,
//                indexes[1] / this.dimension, indexes[1] % this.dimension);
//    }

    public void randomSwapWithNeighbor(int row, int col, int step) {
        assert step <= this.size;
        int firstIndex = row * this.dimension + col;
        int secondIndex = (firstIndex + RandomUtil.randomInt(-step, step + 1) + this.size) % this.size;
        swapTwoNumber(firstIndex, secondIndex);
    }

    public void swapTwoNumber(int indexOne, int indexTwo) {
        swapTwoNumber(indexOne / this.dimension, indexOne % this.dimension,
                indexTwo / this.dimension, indexTwo % this.dimension);
    }

    public void swapTwoNumber(int rowOne, int colOne, int rowTwo, int colTwo) {
        int temp = this.matrix[rowOne][colOne];
        this.matrix[rowOne][colOne] = this.matrix[rowTwo][colTwo];
        this.matrix[rowTwo][colTwo] = temp;
    }

    public void swapRow(int rowOne, int rowTwo) {
        int[] tempArray = new int[this.dimension];
        System.arraycopy(this.matrix[rowOne], 0, tempArray, 0, this.dimension);
        System.arraycopy(this.matrix[rowTwo], 0, this.matrix[rowOne], 0, this.dimension);
        System.arraycopy(tempArray, 0, this.matrix[rowTwo], 0, this.dimension);
    }

    public void swapCol(int colOne, int colTwo) {
        int[] tempArray = new int[this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            tempArray[i] = this.matrix[i][colOne];
        }
        for (int i = 0; i < this.dimension; i++) {
            this.matrix[i][colOne] = this.matrix[i][colTwo];
        }
        for (int i = 0; i < this.dimension; i++) {
            this.matrix[i][colTwo] = tempArray[i];
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension - 1; j++) {
                sb.append(this.matrix[i][j]).append("\t");
            }
            sb.append(this.matrix[i][this.dimension - 1]).append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private void initMatrix() {
        int count = 0;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.matrix[i][j] = ++count;
            }
        }
    }

    private int[][] deepCopy(int[][] matrix) {
        return Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
    }

    private int[] generateTwoRandomIndex(int range, int clip) {
        int[] indexes = new int[2];
        indexes[0] = RandomUtil.randomInt(1, range);
        indexes[1] = (indexes[0] + RandomUtil.randomInt(1, this.dimension)) % clip;
        return indexes;
    }

    private List<Integer> generateRandomSequence(int length) {
        List<Integer> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Individual) {
            Individual objIndividual = (Individual) obj;
            return objIndividual.getFitness() == getFitness();
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;

        for (int[] a : this.matrix)
            for (int b : a)
            result = 31 * result + b;

        return result;
    }

    public static void main(String[] args) {
        Individual one = new Individual(3, EvaluationFunction::absoluteFitnessFunction);
        System.out.println("An new individual: ");
        System.out.println(one);
        System.out.println("Dimension: " + one.dimension);
        System.out.println("Size: " + one.size);
        System.out.println("Fitness: " + one.getFitness());
        System.out.println("-----------------------");
        System.out.println("Test shuffleMatrix(): ");
        one.shuffleMatrix();
        System.out.println(one);
        System.out.println("-----------------------");
        System.out.println("Test shuffleRow(lastRow): ");
        one.shuffleRow(one.dimension - 1);
        System.out.println(one);
        System.out.println("-----------------------");
        System.out.println("Test shuffleCol(lastCol): ");
        one.shuffleColumn(one.dimension - 1);
        System.out.println(one);
        System.out.println("-----------------------");
        System.out.println("Test shuffleMainDiagonal(): ");
        one.shuffleMainDiagonal();
        System.out.println(one);
        System.out.println("-----------------------");
        System.out.println("Test shuffleAntiDiagonal(): ");
        one.shuffleAntiDiagonal();
        System.out.println(one);
        System.out.println("-----------------------");
        System.out.println("Test randomSwapTwoNumberInRow(0): ");
        one.randomSwapTwoNumberInRow(0);
        System.out.println(one);
        System.out.println("-----------------------");
        System.out.println("Test randomSwapTwoNumberInCol(0): ");
        one.randomSwapTwoNumberInCol(0);
        System.out.println(one);
        System.out.println("-----------------------");
        System.out.println("Test randomSwapTwoNumberInMainDiagonal(): ");
        one.randomSwapTwoNumberInMainDiagonal();
        System.out.println(one);
        System.out.println("-----------------------");
        System.out.println("Test randomSwapTwoNumberInAntiDiagonal(): ");
        one.randomSwapTwoNumberInAntiDiagonal();
        System.out.println(one);
        System.out.println("-----------------------");
        System.out.println("Test swapRow(0, lastRow): ");
        one.swapRow(0, one.dimension - 1);
        System.out.println(one);
        System.out.println("-----------------------");
        System.out.println("Test swapCol(0, lastCol): ");
        one.swapCol(0, one.dimension - 1);
        System.out.println(one);
        System.out.println("-----------------------");
        System.out.println("Test copy constructor");
        Individual two = new Individual(one, individual -> 0);
        System.out.println("Two: ");
        System.out.println(two);
        System.out.println("-----------------------");
        System.out.println("Test shuffleMatrix():");
        two.shuffleMatrix();
        System.out.println("One: ");
        System.out.println(one);
        System.out.println("Two: ");
        System.out.println(two);
    }
}