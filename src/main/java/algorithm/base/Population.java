package algorithm.base;

import algorithm.base.operator.FitnessFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Population {
    protected static final Logger logger = LogManager.getLogger(Population.class);
    private final List<Individual> individualList;
    private final Set<Individual> individualSet;
    private final int populationSize;
    private boolean isNeedSort;

    public Population(int populationSize, int dimension, FitnessFunction fitnessFunction) {
        this.populationSize = populationSize;
        this.individualList = new ArrayList<>(populationSize);
        this.individualSet = new HashSet<>();
        for (int i = 0; i < populationSize; i++) {
            Individual individual = new Individual(dimension, fitnessFunction);
            individual.shuffleMatrix();
            this.individualList.add(individual);
            this.individualSet.add(individual);
        }
        this.isNeedSort = true;
    }

    public void setIndividual(int index, Individual individual) {
        if (this.individualSet.contains(individual)) return;
        this.individualSet.remove(this.individualList.get(index));
        this.individualList.set(index, individual);
        this.individualSet.add(individual);
        isNeedSort = true;
    }

    public void tournamentSelection(List<Individual> children) {
        for (Individual individual : children) {
            if (!this.individualSet.contains(individual)) {
                this.individualList.add(individual);
                this.individualSet.add(individual);
            }
        }
        sortAccordingFitness();
        for (int i = this.individualList.size() - 1; i >= this.populationSize; i--) {
            this.individualSet.remove(this.individualList.get(i));
            this.individualList.remove(i);
        }
    }

    public int getSize() {
        return this.individualList.size();
    }

    public Individual getIndividual(int index) {
        return this.individualList.get(index);
    }

    public Individual getBestIndividual() {
        sortAccordingFitness();
        return this.individualList.get(0);
    }

    public Individual getWorstIndividual() {
        sortAccordingFitness();
        return this.individualList.get(getSize() - 1);
    }

    public void evaluateIndividuals() {
        for (Individual individual : this.individualList) {
            individual.getFitness();
        }
    }

    public void sortAccordingFitness() {
        if (!isNeedSort) return;
        this.individualList.sort(Comparator.comparingLong(Individual::getFitness));
        this.isNeedSort = false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fitness: [");
        for (int i = 0; i < getSize() - 1; i++) {
            sb.append(this.individualList.get(i).getFitness()).append(", ");
        }
        sb.append(this.individualList.get(getSize() - 1).getFitness()).append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
//        Population population = new Population(4, 3, EvaluationFunction::absoluteFitnessFunction);
//        System.out.println(population);
//        System.out.println("----------------");
//        System.out.println("Test sort: ");
//        population.sortAccordingFitness();
//        System.out.println(population);
//        System.out.println("----------------");
//        System.out.println("Test sort again: ");
//        population.sortAccordingFitness();
//        System.out.println(population);
//        System.out.println("----------------");
//        System.out.println("Test set new individual: ");
//        population.setIndividual(2, new Individual(4, EvaluationFunction::absoluteFitnessFunction));
//        System.out.println(population);
//        System.out.println("----------------");
//        System.out.println("Test sort: ");
//        population.sortAccordingFitness();
//        System.out.println(population);

        Population a = new Population(4, 3, EvaluationFunction::absoluteFitnessFunction);
        Population b = new Population(4, 3, EvaluationFunction::absoluteFitnessFunction);
        System.out.println(a);
        System.out.println(b);
        a.tournamentSelection(b.individualList);
        System.out.println(a);
    }
}