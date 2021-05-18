package algorithm;

import algorithm.base.EvaluationFunction;
import algorithm.base.GeneticAlgorithm;
import algorithm.base.Individual;
import algorithm.base.Population;
import algorithm.base.operator.FitnessFunction;
import util.RandomUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MagicSquareGeneticSolver extends GeneticAlgorithm {

    private final static FitnessFunction fitnessFunction = EvaluationFunction::absoluteFitnessFunction;
    private final static int ELITE_SIZE = 10;
    private final static int CHILD_SIZE = 5;

    public MagicSquareGeneticSolver(int populationSize, int dimension, int logPeriod) {
        super(populationSize, dimension, logPeriod);
    }

    @Override
    public void step() {
        super.step();
    }

    @Override
    protected Population initPopulation() {
        return new Population(this.populationSize, this.dimension, fitnessFunction);
    }

    @Override
    protected void mutate() {
        this.population.sortAccordingFitness();
        for (int j = 0; j < this.dimension; j++) {
            Individual bestIndividual = this.population.getIndividual(j);
            Individual childIndividual = new Individual(bestIndividual, fitnessFunction);
//            for (int i = 0; i < MagicSquareGeneticSolver.this.dimension; i++) {
//                if (RandomUtil.randomBoolean(0.7)) {
//                    childIndividual.randomSwapTwoNumberInCol(i);
//                }
//                if (RandomUtil.randomBoolean(0.7)) {
//                    childIndividual.randomSwapTwoNumberInRow(i);
//                }
//                if (RandomUtil.randomBoolean(0.5)) {
//                    childIndividual.randomSwapTwoNumberInAntiDiagonal();
//                }
//                if (RandomUtil.randomBoolean(0.5)) {
//                    childIndividual.randomSwapTwoNumberInMainDiagonal();
//                }
//            }
            for (int x = 0; x < this.dimension; x++) {
                for (int y = 0; y < this.dimension; y++) {
                    if (RandomUtil.randomBoolean(0.1)) {
                        childIndividual.randomSwapWithNeighbor(x, y, this.size);
                    }
                }
            }
            int compareIndex = RandomUtil.randomInt(0, this.populationSize);
            if (RandomUtil.randomBoolean(0.01) ||
                    childIndividual.getFitness() < population.getIndividual(compareIndex).getFitness()) {
                population.setIndividual(compareIndex, childIndividual);
            }
        }
//        if (RandomUtil.randomBoolean(0.0001)) {
//            Individual randomIndividual = new Individual(this.dimension, fitnessFunction);
//            randomIndividual.shuffleMatrix();
//            population.setIndividual(RandomUtil.randomInt(0, this.populationSize), randomIndividual);
//        }
    }

    @Override
    protected void crossOver() {
        List<Individual> children = new ArrayList<>(CHILD_SIZE);
        for (int i = 0; i < CHILD_SIZE; i++) {
            int firstParentIndex = RandomUtil.randomInt(0, ELITE_SIZE);
            int secondParentIndex = (firstParentIndex + RandomUtil.randomInt(1, ELITE_SIZE)) % ELITE_SIZE;
            Individual firstParentIndividual = this.population.getIndividual(firstParentIndex);
            Individual secondParentIndividual = this.population.getIndividual(secondParentIndex);
            children.add(orderCrossover(firstParentIndividual, secondParentIndividual, this.size / 2));
        }
        this.population.tournamentSelection(children);
    }

    private Individual orderCrossover(Individual individualOne, Individual individualTwo, int length) {
        int randomIndex = RandomUtil.randomInt(0, this.size);
        Individual childIndividual = new Individual(individualOne, fitnessFunction);
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < length; i++) {
            set.add(individualOne.getValue((randomIndex + i) % this.size));
        }
        int rightMostIndex = (randomIndex + length) % this.size;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                int val = individualTwo.getValue(i, j);
                if (!set.contains(val)) {
                    childIndividual.setValue(rightMostIndex % this.size, val);
                    rightMostIndex++;
                }
            }
        }
        return childIndividual;
    }

    public void solve() {
        do {
            this.step();
        } while (this.population.getBestIndividual().getFitness() != 0);
    }

    public static void main(String[] args) {
        MagicSquareGeneticSolver magicSquareGeneticSolver =
                new MagicSquareGeneticSolver(20, 8, 10000);
        magicSquareGeneticSolver.solve();
        System.out.println("Result: ");
        System.out.println(magicSquareGeneticSolver.population.getBestIndividual());

//        Individual individual = new Individual(10, fitnessFunction);
//        Individual individualTwo = new Individual(10, fitnessFunction);
//        individualTwo.shuffleMatrix();
//        System.out.println(individual);
//        System.out.println();
//        System.out.println(individualTwo);
//        System.out.println();
//        System.out.println(magicSquareGeneticSolver.orderCrossover(individual, individualTwo, 10));
    }
}
