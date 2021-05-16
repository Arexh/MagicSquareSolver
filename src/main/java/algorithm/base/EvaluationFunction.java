package algorithm.base;

public class EvaluationFunction {
    public static long absoluteFitnessFunction(Individual individual) {
        long result = 0;
        long magicConstant = calculateMagicConstant(individual.getDimension());
        for (int i = 0; i < individual.getDimension(); i++) {
            long tempRowSum = -magicConstant;
            long tempColSum = -magicConstant;
            for (int j = 0; j < individual.getDimension(); j++) {
                tempRowSum += individual.getValue(i, j);
                tempColSum += individual.getValue(j, i);
            }
            result += Math.abs(tempColSum) + Math.abs(tempRowSum);
        }
        long mainDiagonal = -magicConstant;
        long secondaryDiagonal = -magicConstant;
        for (int i = 0; i < individual.getDimension(); i++) {
            mainDiagonal += individual.getValue(i, i);
            secondaryDiagonal += individual.getValue(i, individual.getDimension() - i - 1);
        }
        result += Math.abs(mainDiagonal) + Math.abs(secondaryDiagonal);
        return result;
    }

    private static long calculateMagicConstant(long dimension) {
        return (dimension * (dimension * dimension + 1)) / 2;
    }
}
