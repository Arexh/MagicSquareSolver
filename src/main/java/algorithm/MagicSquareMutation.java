package algorithm;

import algorithm.base.EvaluationFunction;
import algorithm.base.Individual;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import util.RandomUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MagicSquareMutation implements EvolutionaryOperator<Individual> {

    @Override
    public List<Individual> apply(List<Individual> selectedCandidates, Random rng) {
        List<Individual> newCandidates = new ArrayList<>(selectedCandidates.size());
        newCandidates.addAll(selectedCandidates);
        selectedCandidates.sort(Comparator.comparingLong(Individual::getFitness));
        int populationSize = selectedCandidates.size();
        int size = selectedCandidates.get(0).getSize();
        int dimension = selectedCandidates.get(0).getDimension();
        for (int j = 0; j < populationSize; j++) {
            Individual bestIndividual = selectedCandidates.get(j);
            Individual childIndividual = new Individual(bestIndividual, EvaluationFunction::absoluteFitnessFunction);
            for (int x = 0; x < dimension; x++) {
                for (int y = 0; y < dimension; y++) {
                    if (RandomUtil.randomBoolean(0.2)) {
                        childIndividual.randomSwapWithNeighbor(x, y, size);
                    }
                }
            }
            int compareIndex = RandomUtil.randomInt(0, populationSize);
            newCandidates.set(compareIndex, childIndividual);
            if (RandomUtil.randomBoolean(0.01) ||
                    childIndividual.getFitness() < newCandidates.get(compareIndex).getFitness()) {
                newCandidates.set(compareIndex, childIndividual);
            }
        }
        return newCandidates;
    }
}
