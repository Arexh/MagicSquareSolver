package algorithm;

import algorithm.base.EvaluationFunction;
import algorithm.base.Individual;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IndividualFactory extends AbstractCandidateFactory<Individual> implements
        CandidateFactory<Individual> {

    private final int dimension;

    public IndividualFactory(int dimension) {
        super();
        this.dimension = dimension;
    }

    @Override
    public List<Individual> generateInitialPopulation(int populationSize,
                                                    Random rng) {
        List<Individual> population = new ArrayList<>();

        for (int i = 1; i <= populationSize; i++)
            population.add(generateRandomCandidate(rng));

        return population;
    }

    @Override
    public Individual generateRandomCandidate(Random rng) {
        Individual individual = new Individual(dimension, EvaluationFunction::absoluteFitnessFunction);
        individual.shuffleMatrix();
        return individual;
    }
}