package algorithm.base;

public class EvaluationFunction {
    public static long absoluteFitnessFunction(Individual individual) {
        long result = 0;
        for (int i = 0; i < individual.getDimension(); i++) {
            long tempRowSum = -15;
            long tempColSum = -15;
            for (int j = 0; j < individual.getDimension(); j++) {
                tempRowSum += individual.getValue(i, j);
                tempColSum += individual.getValue(j, i);
            }
            result += Math.abs(tempColSum) + Math.abs(tempRowSum);
        }
        long mainDiagonal = -15;
        long secondaryDiagonal = -15;
        for (int i = 0; i < individual.getDimension(); i++) {
            mainDiagonal += individual.getValue(i, i);
            secondaryDiagonal += individual.getValue(i, individual.getDimension() - i - 1);
        }
        result += Math.abs(mainDiagonal) + Math.abs(secondaryDiagonal);
        return result;
    }
}
