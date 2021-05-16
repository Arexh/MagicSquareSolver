package algorithm.base;

import algorithm.base.operator.FitnessFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Population {
    protected static final Logger logger = LogManager.getLogger(Population.class);
    private final Individual[] individualArray;
    private boolean isNeedSort;
    private Set<Individual> individualSet;

    public Population(int populationSize, int dimension, FitnessFunction fitnessFunction) {
        this.individualArray = new Individual[populationSize];
        this.individualSet = new HashSet<>();
        for (int i = 0; i < populationSize; i++) {
            this.individualArray[i] = new Individual(dimension, fitnessFunction);
            this.individualArray[i].shuffleMatrix();
            this.individualSet.add(this.individualArray[i]);
        }
        this.isNeedSort = true;
    }

    public void setIndividual(int index, Individual individual) {
        if (this.individualSet.contains(individual)) return;
        this.individualSet.remove(this.individualArray[index]);
        this.individualArray[index] = individual;
        isNeedSort = true;
        this.individualSet.add(individual);
    }

    public int getSize() {
        return this.individualArray.length;
    }

    public Individual getIndividual(int index) {
        return this.individualArray[index];
    }

    public Individual getBestIndividual() {
        sortAccordingFitness();
        return this.individualArray[0];
    }

    public Individual getWorstIndividual() {
        sortAccordingFitness();
        return this.individualArray[getSize() - 1];
    }

    public void evaluateIndividuals() {
        for (Individual individual : this.individualArray) {
            individual.getFitness();
        }
    }

    public void sortAccordingFitness() {
        if (!isNeedSort) return;
        logger.debug("Sorting population...");
//        logger.debug(toString());
        Arrays.sort(this.individualArray, Comparator.comparingLong(Individual::getFitness));
//        logger.debug(toString());
//        for (Individual individual : this.individualArray) {
//            System.out.println(individual);
//            System.out.println();
//        }
        this.isNeedSort = false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fitness: [");
        for (int i = 0; i < getSize() - 1; i++) {
            sb.append(this.individualArray[i].getFitness()).append(", ");
        }
        sb.append(this.individualArray[getSize() - 1].getFitness()).append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Population population = new Population(4, 3, EvaluationFunction::absoluteFitnessFunction);
        System.out.println(population);
        System.out.println("----------------");
        System.out.println("Test sort: ");
        population.sortAccordingFitness();
        System.out.println(population);
        System.out.println("----------------");
        System.out.println("Test sort again: ");
        population.sortAccordingFitness();
        System.out.println(population);
        System.out.println("----------------");
        System.out.println("Test set new individual: ");
        population.setIndividual(2, new Individual(4, EvaluationFunction::absoluteFitnessFunction));
        System.out.println(population);
        System.out.println("----------------");
        System.out.println("Test sort: ");
        population.sortAccordingFitness();
        System.out.println(population);
    }
}