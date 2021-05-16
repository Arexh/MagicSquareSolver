//package algorithm.base;
//
//import util.TimeUtil;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.Random;
//
//public abstract class GeneticAlgorithm {
//    protected static final Logger logger = LogManager.getLogger(GeneticAlgorithm.class);
//    private final Individual[] individuals;
//    private final int populationSize;
//    private final int magicConstant;
//    private long maximumFitness = -1;
//    private int step = 0;
//    private final int n;
//
//    public GeneticAlgorithm(int n, int populationSize) {
//        assert n > 1;
//        assert populationSize > 1;
//        this.n = n;
//        this.populationSize = populationSize;
//        this.magicConstant = calculateMagicConstant();
//        this.individuals = new Individual[populationSize];
//        initIndividualList();
//        maximumFitness = getBestSolution().getFitness();
//    }
//
//    private int calculateMagicConstant() {
//        return (this.n * (this.n * this.n + 1)) / 2;
//    }
//
//    private void initIndividualList() {
////        algorithm.abstrct.Individual defaultIndividual = algorithm.abstrct.Individual.initDefaultIndividual(n, this.magicConstant);
////        this.individuals[0] = defaultIndividual;
//        for (int i = 0; i < this.populationSize; i++) {
//            Individual randomIndividual = Individual.initRandomIndividual(n, this.magicConstant);
//            this.individuals[i] = randomIndividual;
//        }
//    }
//
//    private void evaluate() {
//        for (Individual individual : this.individuals) {
//            individual.evaluateFitness();
//        }
//        Arrays.sort(individuals, Comparator.comparingLong(Individual::getFitness));
//    }
//
//    private void mutation() {
//        Individual bestIndividual = this.individuals[new Random().nextInt(this.individuals.length)];
//        Individual childIndividual = bestIndividual.generateChild(Math.log10(bestIndividual.getFitness()) / Math.log10(maximumFitness));
//        if (childIndividual.getFitness() <= this.individuals[this.populationSize - 1].getFitness()) {
//            this.individuals[this.populationSize - 1] = childIndividual;
//        }
//    }
//
//    private void crossOver() {
//
//    }
//
//    private void showFitness() {
//        for (int i = 0; i < this.populationSize - 1; i++) {
//            logger.debug(this.individuals[i].getFitness() + ", ");
//        }
//        logger.debug(this.individuals[this.populationSize - 1].getFitness());
//    }
//
//    private void step() {
//        evaluate();
//        mutation();
//        crossOver();
//        if (this.step % 10 == 0)
//            showFitness();
//        this.step++;
//    }
//
//    public Individual getBestSolution() {
//        return individuals[0];
//    }
//
//    public static void main(String[] args) {
//        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(200,  1);
//        Individual bestIndividual;
//        int count = 0;
//        long startTime = TimeUtil.getCurrentTime();
//        do {
//            geneticAlgorithm.step();
//            bestIndividual = geneticAlgorithm.getBestSolution();
//            count++;
////            logger.debug(String.valueOf(bestIndividual.getFitness()));
//        } while(bestIndividual.getFitness() != 0);
//        logger.debug(TimeUtil.getCurrentTime() - startTime);
//        logger.debug(bestIndividual);
//        logger.debug(count);
//    }
//}
