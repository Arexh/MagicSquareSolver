package algorithm;

import algorithm.base.EvaluationFunction;
import algorithm.base.Individual;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RankSelection;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

import java.util.ArrayList;
import java.util.List;

public class TestWatchmaker {
    public static void main(String[] args) {
        int dimension = 4;
        int populationSize = 20;
        int crossoverPoints = 12;

        CandidateFactory<Individual> factory = new IndividualFactory(dimension);

        List<EvolutionaryOperator<Individual>> operators = new ArrayList<>(dimension);

        operators.add(new MagicSquareMutation());

        EvolutionaryOperator<Individual> pipeline = new EvolutionPipeline<>(operators);

        GenerationalEvolutionEngine<Individual> engine = new GenerationalEvolutionEngine<>(factory,
                pipeline,
                new AbsoluteFitness((int) EvaluationFunction.calculateMagicConstant(dimension)),
                new RankSelection(),
                new MersenneTwisterRNG());

        engine.setSingleThreaded(true);

        engine.addEvolutionObserver(data -> System.out.printf("Generation %d: %f\n",
                data.getGenerationNumber(),
                data.getBestCandidateFitness()));

        Individual best = engine.evolve(populationSize,
                5,
                new TargetFitness(0, false));

        System.out.println(best);
        System.out.println(best.getFitness());

    }
}
