package algorithm;

import algorithm.base.EvaluationFunction;
import algorithm.base.GeneticAlgorithm;
import algorithm.base.Individual;
import algorithm.base.Population;
import algorithm.base.operator.FitnessFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.RandomUtil;

public class MagicSquareSolver {

    protected static final Logger logger = LogManager.getLogger(MagicSquareSolver.class);

    private final GeneticAlgorithm geneticAlgorithm;
    private final Population population;
    private final FitnessFunction fitnessFunction;
    private final int populationSize;
    private final int dimension;

    public MagicSquareSolver(int populationSize, int dimension) {
        this.populationSize = populationSize;
        this.dimension = dimension;
        this.fitnessFunction = EvaluationFunction::absoluteFitnessFunction;
        this.population = new Population(this.populationSize, this.dimension, this.fitnessFunction);
        this.geneticAlgorithm = new GeneticAlgorithm(population -> {
            population.sortAccordingFitness();
            for (int k = 0; k < 1; k++) {
                Individual bestIndividual = population.getIndividual(RandomUtil.randomInt(0, this.populationSize / 2));
                Individual childIndividual = new Individual(bestIndividual, MagicSquareSolver.this.fitnessFunction);
                for (int i = 0; i < MagicSquareSolver.this.dimension; i++) {
                    if (RandomUtil.randomBoolean(0.7)) {
                        childIndividual.randomSwapTwoNumberInCol(i);
                    }
                    if (RandomUtil.randomBoolean(0.7)) {
                        childIndividual.randomSwapTwoNumberInRow(i);
                    }
                    if (RandomUtil.randomBoolean(0.5)) {
                        childIndividual.randomSwapTwoNumberInAntiDiagonal();
                    }
                    if (RandomUtil.randomBoolean(0.5)) {
                        childIndividual.randomSwapTwoNumberInMainDiagonal();
                    }
                }
                int compareIndex = RandomUtil.randomInt(this.populationSize / 2, this.populationSize);
                if (RandomUtil.randomBoolean(0.4) &&
                        childIndividual.getFitness() < population.getIndividual(compareIndex).getFitness()) {
                    population.setIndividual(population.getSize() - 1, childIndividual);
                }
            }
        }, this.population);
    }

    public void solve() {
        do {
            this.geneticAlgorithm.step();
        } while (this.population.getBestIndividual().getFitness() != 0);
//        logger.debug(this.population.getBestIndividual());
    }

    public static void main(String[] args) {
        MagicSquareSolver magicSquareSolver = new MagicSquareSolver(20, 3);
        magicSquareSolver.solve();
        System.out.println("Result: ");
        System.out.println(magicSquareSolver.population.getBestIndividual());
    }
}
