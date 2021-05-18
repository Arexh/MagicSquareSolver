package algorithm;

import algorithm.base.Individual;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;

public class AbsoluteFitness implements FitnessEvaluator<Individual> {

    private final int magicConstant;

    public AbsoluteFitness(int magicConstant) {
        this.magicConstant = magicConstant;
    }

    @Override
    public double getFitness(Individual candidate, List<? extends Individual> population) {
        long result = 0;
        for (int i = 0; i < candidate.getDimension(); i++) {
            long tempRowSum = -magicConstant;
            long tempColSum = -magicConstant;
            for (int j = 0; j < candidate.getDimension(); j++) {
                tempRowSum += candidate.getValue(i, j);
                tempColSum += candidate.getValue(j, i);
            }
            result += Math.abs(tempColSum) + Math.abs(tempRowSum);
        }
        long mainDiagonal = -magicConstant;
        long secondaryDiagonal = -magicConstant;
        for (int i = 0; i < candidate.getDimension(); i++) {
            mainDiagonal += candidate.getValue(i, i);
            secondaryDiagonal += candidate.getValue(i, candidate.getDimension() - i - 1);
        }
        result += Math.abs(mainDiagonal) + Math.abs(secondaryDiagonal);
        assert result < Double.MAX_VALUE;
        return result;
    }

    @Override
    public boolean isNatural() {
        return false;
    }
}
