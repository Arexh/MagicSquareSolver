package algorithm.base.operator;

import algorithm.base.Individual;

public interface FitnessFunction {
    long evaluateFitness(Individual individual);
}