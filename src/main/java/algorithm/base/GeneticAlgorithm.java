package algorithm.base;

import algorithm.base.operator.CrossOverOperator;
import algorithm.base.operator.MutationOperator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GeneticAlgorithm {
    private final static int LOG_PER_GENERATIONS = 10;

    protected static final Logger logger = LogManager.getLogger(GeneticAlgorithm.class);
    private final MutationOperator mutationOperator;
    private final CrossOverOperator crossOverOperator;
    private final Population population;

    private int generation;

    public GeneticAlgorithm(MutationOperator mutationOperator,
                            CrossOverOperator crossOverOperator,
                            Population population) {
        this.mutationOperator = mutationOperator;
        this.crossOverOperator = crossOverOperator;
        this.population = population;
        this.generation = 0;
    }

    public GeneticAlgorithm(MutationOperator mutationOperator, Population population) {
        this(mutationOperator, null, population);
    }

    public void step() {
        evaluate();
        mutate();
        crossOver();
        logResult();
        this.generation++;
    }

    private void evaluate() {
        population.evaluateIndividuals();
    }

    private void mutate() {
        if (this.mutationOperator != null) {
            this.mutationOperator.mutate(this.population);
        }
    }

    private void crossOver() {
        if (this.crossOverOperator != null) {
            this.crossOverOperator.crossOver(this.population);
        }
    }

    private void logResult() {
        if (this.generation % LOG_PER_GENERATIONS == 0) {
            logger.debug(this.population);
        }
    }
}
