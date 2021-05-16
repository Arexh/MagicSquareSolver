package algorithm.base;

import algorithm.base.operator.FitnessFunction;

import java.util.*;

public class Individual {
    private final int[][] matrix;
    private final FitnessFunction fitnessFunction;
    private long fitness = -1;

    public Individual(int n, FitnessFunction fitnessFunction) {
        this.matrix = new int[n][n];
        this.fitnessFunction = fitnessFunction;
        initMatrix();
    }

    public Individual(Individual individual, FitnessFunction fitnessFunction) {
        this.matrix = deepCopy(individual.matrix);
        this.fitnessFunction = fitnessFunction;
    }

    public int getValue(int row, int col) {
        return this.matrix[row][col];
    }

    public int getDimension() {
        return this.matrix.length;
    }

    public int getSize() {
        return getDimension() * getDimension();
    }

    public long getFitness() {
        if (fitness == -1) {
            this.fitness = fitnessFunction.evaluateFitness(this);
        }
        return this.fitness;
    }

    public void shuffleMatrix() {
        List<Integer> list = new ArrayList<>(getSize());
        for (int i = 0; i < getDimension(); i++) {
            for (int j = 0; j < getDimension(); j++) {
                list.add(this.matrix[i][j]);
            }
        }
        Collections.shuffle(list);
        int index = 0;
        for (int i = 0; i < getDimension(); i++) {
            for (int j = 0; j < getDimension(); j++) {
                this.matrix[i][j] = list.get(index++);
            }
        }
    }

    public void shuffleRow(int row) {
        List<Integer> list = new ArrayList<>(getDimension());
        for (int i = 0; i < getDimension(); i++) {
            list.add(this.matrix[row][i]);
        }
        Collections.shuffle(list);
        for (int i = 0; i < getDimension(); i++) {
            this.matrix[row][i] = list.get(i);
        }
    }

    public void shuffleColumn(int col) {
        List<Integer> list = new ArrayList<>(getDimension());
        for (int i = 0; i < getDimension(); i++) {
            list.add(this.matrix[i][col]);
        }
        Collections.shuffle(list);
        for (int i = 0; i < getDimension(); i++) {
            this.matrix[i][col] = list.get(i);
        }
    }

    public void shuffleMainDiagonal() {
        List<Integer> list = new ArrayList<>(getDimension());
        for (int i = 0; i < getDimension(); i++) {
            list.add(this.matrix[i][i]);
        }
        Collections.shuffle(list);
        for (int i = 0; i < getDimension(); i++) {
            this.matrix[i][i] = list.get(i);
        }
    }

    public void shuffleAntiDiagonal() {
        List<Integer> list = new ArrayList<>(getDimension());
        for (int i = 0; i < getDimension(); i++) {
            list.add(this.matrix[i][getDimension() - 1 - i]);
        }
        Collections.shuffle(list);
        for (int i = 0; i < getDimension(); i++) {
            this.matrix[i][getDimension() - 1 - i] = list.get(i);
        }
    }

    public void randomSwapTwoNumberInRow(int row) {
        List<Integer> sequence = getRandomSequence(getDimension());
        swapTwoNumber(row, sequence.get(0), row, sequence.get(getDimension() - 1));
    }

    public void randomSwapTwoNumberInCol(int col) {
        List<Integer> sequence = getRandomSequence(getDimension());
        swapTwoNumber(sequence.get(0), col, sequence.get(getDimension() - 1), col);
    }

    public void randomSwapTwoNumberInMainDiagonal() {
        List<Integer> sequence = getRandomSequence(getDimension());
        swapTwoNumber(sequence.get(0), sequence.get(0), sequence.get(getDimension() - 1), sequence.get(getDimension() - 1));
    }

    public void randomSwapTwoNumberInAntiDiagonal() {
        List<Integer> sequence = getRandomSequence(getDimension());
        swapTwoNumber(sequence.get(0), getDimension() - sequence.get(0) - 1,
                sequence.get(getDimension() - 1), getDimension() - sequence.get(getDimension() - 1) - 1);
    }

    public void swapTwoNumber(int rowOne, int colOne, int rowTwo, int colTwo) {
        int temp = this.matrix[rowOne][colOne];
        this.matrix[rowOne][colOne] = this.matrix[rowTwo][colTwo];
        this.matrix[rowTwo][colTwo] = temp;
    }

    public void swapRow(int rowOne, int rowTwo) {
        int[] tempArray = new int[getDimension()];
        System.arraycopy(this.matrix[rowOne], 0, tempArray, 0, getDimension());
        System.arraycopy(this.matrix[rowTwo], 0, this.matrix[rowOne], 0, getDimension());
        System.arraycopy(tempArray, 0, this.matrix[rowTwo], 0, getDimension());
    }

    public void swapCol(int colOne, int colTwo) {
        int[] tempArray = new int[getDimension()];
        for (int i = 0; i < getDimension(); i++) {
            tempArray[i] = this.matrix[i][colOne];
        }
        for (int i = 0; i < getDimension(); i++) {
            this.matrix[i][colOne] = this.matrix[i][colTwo];
        }
        for (int i = 0; i < getDimension(); i++) {
            this.matrix[i][colTwo] = tempArray[i];
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getDimension(); i++) {
            for (int j = 0; j < getDimension() - 1; j++) {
                sb.append(this.matrix[i][j]).append("\t");
            }
            sb.append(this.matrix[i][getDimension() - 1]).append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private void initMatrix() {
        int count = 0;
        for (int i = 0; i < getDimension(); i++) {
            for (int j = 0; j < getDimension(); j++) {
                this.matrix[i][j] = ++count;
            }
        }
    }

    private int[][] deepCopy(int[][] matrix) {
        return Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
    }

    private List<Integer> getRandomSequence(int length) {
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
        System.out.println("Dimension: " + one.getDimension());
        System.out.println("Size: " + one.getSize());
        System.out.println("Fitness: " + one.getFitness());
        System.out.println("-----------------------");
        System.out.println("Test shuffleMatrix(): ");
        one.shuffleMatrix();
        System.out.println(one);
        System.out.println("-----------------------");
        System.out.println("Test shuffleRow(lastRow): ");
        one.shuffleRow(one.getDimension() - 1);
        System.out.println(one);
        System.out.println("-----------------------");
        System.out.println("Test shuffleCol(lastCol): ");
        one.shuffleColumn(one.getDimension() - 1);
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
        one.swapRow(0, one.getDimension() - 1);
        System.out.println(one);
        System.out.println("-----------------------");
        System.out.println("Test swapCol(0, lastCol): ");
        one.swapCol(0, one.getDimension() - 1);
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