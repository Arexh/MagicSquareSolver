package algorithm;

import algorithm.base.EvaluationFunction;
import algorithm.base.Individual;
import org.uncommons.watchmaker.framework.operators.AbstractCrossover;
import util.RandomUtil;

import java.util.*;

public class OrderCrossover extends AbstractCrossover<Individual> {

    protected OrderCrossover(int crossoverPoints) {
        super(crossoverPoints);
    }

    @Override
    protected List<Individual> mate(Individual parent1, Individual parent2, int numberOfCrossoverPoints, Random rng) {
        int randomIndex = RandomUtil.randomInt(0, numberOfCrossoverPoints);
        Individual childIndividual = new Individual(parent1, EvaluationFunction::absoluteFitnessFunction);
        Set<Integer> set = new HashSet<>();
        int size = parent1.getSize();
        int dimension = parent1.getDimension();
        for (int i = 0; i < numberOfCrossoverPoints; i++) {
            set.add(parent1.getValue((randomIndex + i) % size));
        }
        int rightMostIndex = (randomIndex + numberOfCrossoverPoints) % size;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int val = parent2.getValue(i, j);
                if (!set.contains(val)) {
                    childIndividual.setValue(rightMostIndex % size, val);
                    rightMostIndex++;
                }
            }
        }
        return new ArrayList<>() {{ add(childIndividual); }};
    }
}
