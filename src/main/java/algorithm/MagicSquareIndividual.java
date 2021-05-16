//package algorithm.base;
//
//import java.util.Random;
//
//public abstract class Individual {
//
//    private final int n;
//    private final int[] array;
//    private final Random random;
//    private final int magicConstant;
//    private long fitness = -1;
//
//    private Individual(int n, int magicConstant) {
//        this.n = n;
//        this.array = new int[n * n];
//        this.magicConstant = magicConstant;
//        this.random = new Random();
//    }
//
//    private Individual(Individual individual) {
//        this.n = individual.n;
//        this.array = new int[this.n * this.n];
//        this.magicConstant = individual.magicConstant;
//        this.random = new Random();
//        this.fitness = -1;
//        System.arraycopy(individual.array, 0, this.array, 0, this.array.length);
//    }
//
//    public static Individual initDefaultIndividual(int n, int magicConstant) {
//        Individual individual = new Individual(n, magicConstant);
//        individual.initDefaultNumbers();
//        return individual;
//    }
//
//    public static Individual initRandomIndividual(int n, int magicConstant) {
//        Individual individual = new Individual(n, magicConstant);
//        individual.initRandomNumbers();
//        return individual;
//    }
//
//    public void setValue(int row, int col, int value) {
//        int index = row * this.n + col;
//        if (this.array[index] != value) {
//            this.array[index] = value;
//            clearFitness();
//        }
//    }
//
//    private void clearFitness() {
//        this.fitness = -1;
//    }
//
//    public int getValue(int row, int col) {
//        return this.array[row * this.n + col];
//    }
//
//    public void initDefaultNumbers() {
//        for (int i = 0; i < this.array.length; i++) {
//            this.array[i] = i + 1;
//        }
//    }
//
//    private void randomSwap(double ratio) {
////        Algorithm.logger.debug(ratio);
//        int val = this.n;
////        int val = (int) Math.exp(ratio + 0.5) * this.n + this.n / 20;
//        GeneticAlgorithm.logger.debug(val);
//        int indexOne = random.nextInt(this.array.length);
//        int indexTwo = Math.min(random.nextInt(val) + indexOne, this.array.length - 1);
//        swap(indexOne, indexTwo);
//    }
//
//    private void swap(int indexOne, int indexTwo) {
//        if (this.array[indexOne] != this.array[indexTwo]) {
//            int temp = this.array[indexTwo];
//            this.array[indexTwo] = this.array[indexOne];
//            this.array[indexOne] = temp;
//            clearFitness();
//        }
//    }
//
//
//
//    public void initRandomNumbers() {
//        initDefaultNumbers();
//        // from: https://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
//        for (int i = this.array.length - 1; i > 0; i--) {
//            int index = this.random.nextInt(i + 1);
//            swap(index, i);
//        }
//    }
//
//    public boolean isFitnessEvaluated() {
//        return fitness != -1;
//    }
//
//    public long getFitness() {
//        if (fitness == -1) evaluateFitness();
//        return fitness;
//    }
//
//    public void evaluateFitness() {
//        if (isFitnessEvaluated()) return;
//        long result = 0;
//        for (int i = 0; i < this.n; i++) {
//            long tempRowSum = -this.magicConstant;
//            long tempColSum = -this.magicConstant;
//            for (int j = 0; j < this.n; j++) {
//                tempRowSum += getValue(i, j);
//                tempColSum += getValue(j, i);
//            }
////            result += Math.abs(tempColSum) + Math.abs(tempRowSum);
//            result += Math.min(Math.abs(tempColSum), Math.abs(tempRowSum));
//        }
//        long mainDiagonal = -this.magicConstant;
//        long secondaryDiagonal = -this.magicConstant;
//        for (int i = 0; i < n; i++) {
//            mainDiagonal += getValue(i, i);
//            secondaryDiagonal += getValue(i, n - i - 1);
//        }
////        result += Math.abs(mainDiagonal) + Math.abs(secondaryDiagonal);
//        this.fitness = result;
//    }
//
//    public Individual generateChild(double ratio) {
//        Individual childIndividual = new Individual(this);
//        for (int i = 0; i < 1; i++) {
//            childIndividual.randomSwap(ratio);
//        }
//        return childIndividual;
//    }
//
//    @Override
//    public String toString() {
//        int count = 0;
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < this.n; i++) {
//            for (int j = 0; j < this.n - 1; j++) {
//                sb.append(this.array[count++]).append(", ");
//            }
//            sb.append(this.array[count++]).append("\n");
//        }
//        sb.append("Fitness : ").append(getFitness());
//        return sb.toString();
//    }
//}
