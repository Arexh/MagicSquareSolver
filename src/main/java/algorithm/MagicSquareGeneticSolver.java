package algorithm;

import algorithm.base.EvaluationFunction;
import algorithm.base.GeneticAlgorithm;
import algorithm.base.Individual;
import algorithm.base.Population;
import algorithm.base.operator.FitnessFunction;
import util.RandomUtil;

public class MagicSquareGeneticSolver extends GeneticAlgorithm {

    private final static FitnessFunction fitnessFunction = EvaluationFunction::absoluteFitnessFunction;

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
                    if (RandomUtil.randomBoolean(0.2)) {
                        childIndividual.randomSwapWithNeighbor(x, y, this.dimension * this.dimension);
                    }
                }
            }
            int compareIndex = RandomUtil.randomInt(0, this.populationSize);
            if (RandomUtil.randomBoolean(0.01) ||
                    childIndividual.getFitness() < population.getIndividual(compareIndex).getFitness()) {
                population.setIndividual(compareIndex, childIndividual);
            }
        }
        if (RandomUtil.randomBoolean(0.0001)) {
            Individual randomIndividual = new Individual(this.dimension, fitnessFunction);
            randomIndividual.shuffleMatrix();
            population.setIndividual(RandomUtil.randomInt(0, this.populationSize), randomIndividual);
        }
    }

    @Override
    protected void crossOver() {

    }

    public void solve() {
        do {
            this.step();
        } while (this.population.getBestIndividual().getFitness() != 0);
    }

    public static void main(String[] args) {
        MagicSquareGeneticSolver magicSquareGeneticSolver =
                new MagicSquareGeneticSolver(24, 5, 10000);
        magicSquareGeneticSolver.solve();
        System.out.println("Result: ");
        System.out.println(magicSquareGeneticSolver.population.getBestIndividual());
    }
}
