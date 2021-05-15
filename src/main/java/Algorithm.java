
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Algorithm {

    public static final Logger logger = LogManager.getLogger(Algorithm.class);

    private final int n;
    private final int populationSize;
    private final int magicConstant;
    private final Individual[] individuals;
    private long maximumFitness = -1;

    public Algorithm(int n, int populationSize) {
        assert n > 1;
        assert populationSize > 1;
        this.n = n;
        this.populationSize = populationSize;
        this.magicConstant = calculateMagicConstant();
        this.individuals = new Individual[populationSize];
        initIndividualList();
        maximumFitness = getBestSolution().getFitness();
    }

    private int calculateMagicConstant() {
        return (this.n * (this.n * this.n + 1)) / 2;
    }

    private void initIndividualList() {
        Individual defaultIndividual = Individual.initDefaultIndividual(n, this.magicConstant);
        this.individuals[0] = defaultIndividual;
        for (int i = 1; i < this.populationSize; i++) {
            Individual randomIndividual = Individual.initRandomIndividual(n, this.magicConstant);
            this.individuals[i] = randomIndividual;
        }
    }

    private void evaluate() {
        for (Individual individual : this.individuals) {
            individual.evaluateFitness();
        }
        Arrays.sort(individuals, Comparator.comparingLong(Individual::getFitness));
    }

    private void mutation() {
        Individual bestIndividual = this.individuals[new Random().nextInt(this.individuals.length)];
        Individual childIndividual = bestIndividual.generateChild(Math.exp(bestIndividual.getFitness() / (double) maximumFitness));
        if (childIndividual.getFitness() <= this.individuals[this.populationSize - 1].getFitness()) {
            this.individuals[this.populationSize - 1] = childIndividual;
        }
    }

    private void crossOver() {

    }

    private void showFitness() {
        for (int i = 0; i < this.populationSize - 1; i++) {
            logger.debug(this.individuals[i].getFitness() + ", ");
        }
        logger.debug(this.individuals[this.populationSize - 1].getFitness());
    }

    private void step() {
        evaluate();
        mutation();
        crossOver();
        showFitness();
    }

    public Individual getBestSolution() {
        return individuals[0];
    }

    public static void main(String[] args) {
        Algorithm algorithm = new Algorithm(200,  1);
        Individual bestIndividual;
        int count = 0;
        long startTime = TimeUtil.getCurrentTime();
        do {
            algorithm.step();
            bestIndividual = algorithm.getBestSolution();
            count++;
//            logger.debug(String.valueOf(bestIndividual.getFitness()));
        } while(bestIndividual.getFitness() != 0);
        logger.debug(TimeUtil.getCurrentTime() - startTime);
        logger.debug(bestIndividual);
        logger.debug(count);
    }
}
