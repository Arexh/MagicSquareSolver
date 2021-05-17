package algorithm.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class GeneticAlgorithm {
    protected static final Logger logger = LogManager.getLogger(GeneticAlgorithm.class);
    protected final Population population;
    protected final int dimension;
    protected final int populationSize;
    protected final int logPeriod;

    private int generation;

    public GeneticAlgorithm(int populationSize, int dimension, int logPeriod) {
        this.populationSize = populationSize;
        this.dimension = dimension;
        this.generation = 0;
        this.logPeriod = logPeriod;
        this.population = initPopulation();
    }

    public void step() {
        evaluate();
        mutate();
        crossOver();
        logResult();
        this.generation++;
    }

    protected abstract Population initPopulation();

    protected abstract void mutate();

    protected abstract void crossOver();

    protected void evaluate() {
        this.population.evaluateIndividuals();
    }

    private void logResult() {
        if (this.generation % this.logPeriod == 0) {
            logger.debug("Gen: " + this.generation + ", " + this.population);
            logger.debug("Individuals Per Millisecond: " + Individual.individualCount / (System.currentTimeMillis() - Individual.FIRST_CREATE_TIME));
        }
    }
}
