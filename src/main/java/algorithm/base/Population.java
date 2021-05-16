package algorithm.base;

import algorithm.base.operator.FitnessFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Comparator;

public class Population {
    protected static final Logger logger = LogManager.getLogger(Population.class);
    private final Individual[] individualArray;
    private boolean isNeedSort;

    public Population(int size, int dimension, FitnessFunction fitnessFunction) {
        this.individualArray = new Individual[size];
        for (int i = 0; i < size; i++) {
            this.individualArray[i] = new Individual(dimension, fitnessFunction);
            this.individualArray[i].shuffleMatrix();
        }
        this.isNeedSort = true;
    }

    public void setIndividual(int index, Individual individual) {
        this.individualArray[index] = individual;
        isNeedSort = true;
    }

    public int getSize() {
        return this.individualArray.length;
    }

    public Individual getBestIndividual() {
        sortAccordingFitness();
        return individualArray[0];
    }

    public void evaluateIndividuals() {
        for (Individual individual : this.individualArray) {
            individual.getFitness();
        }
    }

    private void sortAccordingFitness() {
        if (!isNeedSort) return;
        logger.debug("Sorting population...");
        logger.debug(toString());
        Arrays.sort(this.individualArray, Comparator.comparingLong(Individual::getFitness));
        logger.debug(toString());
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