package test;

import java.util.*;

import org.uncommons.maths.random.*;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;
import org.uncommons.watchmaker.framework.operators.DoubleArrayCrossover;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.Stagnation;

public class FunctionOptimizer {

    public static void main(String[] args) {
        new FunctionOptimizer().work();
    }

    private void work() {
        int dimension = 10;
        double max = 5.0;
        double min = -max;
        int populationSize = 50;
        int crossoverPoints = 2;
        double mutationProbability = 0.02;

        CandidateFactory<double[]> factory = new ArrayFactory(dimension, min, max);

        List<EvolutionaryOperator<double[]>> operators = new ArrayList<EvolutionaryOperator<double[]>>(dimension);

        operators.add(new DoubleArrayCrossover(crossoverPoints));

        operators.add(new DoubleArrayMutation(new Probability(mutationProbability)));

        EvolutionaryOperator<double[]> pipeline = new EvolutionPipeline<double[]>(operators);

        GenerationalEvolutionEngine<double[]> engine = new GenerationalEvolutionEngine<double[]>(factory,
                pipeline,
                new SphereFunction(dimension),
                new RouletteWheelSelection(),
                new MersenneTwisterRNG());

        engine.setSingleThreaded(true);

        engine.addEvolutionObserver(new EvolutionLogger());

        double[] best = engine.evolve(populationSize,
                5,
                new Stagnation(100000, false));

        System.out.printf("(%f, %f)\n", best[0], best[1]);
    }

    private static class DoubleArrayMutation implements EvolutionaryOperator<double[]> {
        private Probability p;

        public DoubleArrayMutation(Probability p) {
            this.p = p;
        }

        @Override
        public List<double[]> apply(List<double[]> selectedCandidates, Random rng) {
            List<double[]> newCandidates = new ArrayList<double[]>(selectedCandidates.size());
            newCandidates.addAll(selectedCandidates);

            for(int i = 0; i < newCandidates.size(); i++) {
                if(p.nextEvent(rng)) {
                    double[] mutated = newCandidates.get(i);
                    int dimension = rng.nextInt(mutated.length);

                    mutated[dimension] += rng.nextGaussian();

                    newCandidates.set(i, mutated);
                }
            }

            return newCandidates;
        }

    }

    private static class SphereFunction implements FitnessEvaluator<double[]> {
        private int dimension;

        public SphereFunction(int dimension) {
            this.dimension = dimension;
        }

        @Override
        public double getFitness(double[] candidate,
                                 List<? extends double[]> population) {
            double sum = 0.0;

            for(int i = 0; i < dimension; i++)
                sum += candidate[i]*candidate[i];

            return sum;
        }

        @Override
        public boolean isNatural() {
            return false;
        }

    }

    private static class ArrayFactory extends AbstractCandidateFactory<double[]> implements
            CandidateFactory<double[]> {

        private int dimension;
        private double min;
        private double max;

        public ArrayFactory(int dimension, double min, double max) {
            super();
            this.dimension = dimension;
            this.min = min;
            this.max = max;
        }

        @Override
        public List<double[]> generateInitialPopulation(int populationSize,
                                                        Random rng) {
            List<double[]> population = new ArrayList<double[]>();

            for(int i = 1; i <= populationSize; i++)
                population.add(generateRandomCandidate(rng));

            return population;
        }

        @Override
        public List<double[]> generateInitialPopulation(int populationSize,
                                                        Collection<double[]> seedCandidates, Random rng) {
            List<double[]> population = new ArrayList<double[]>();

            population.addAll(seedCandidates);

            for(int i = 1; i <= populationSize - seedCandidates.size(); i++)
                population.add(generateRandomCandidate(rng));

            return population;
        }

        @Override
        public double[] generateRandomCandidate(Random rng) {
            double[] candidate = new double[dimension];

            for(int i = 0; i < dimension; i++)
                candidate[i] = rng.nextDouble()*(max-min)+min;

            return candidate;
        }

    }

    private static class EvolutionLogger implements EvolutionObserver<double[]>
    {
        public void populationUpdate(PopulationData<? extends double[]> data)
        {
            System.out.printf("Generation %d: %f\n",
                    data.getGenerationNumber(),
                    data.getBestCandidateFitness());
        }
    }
}