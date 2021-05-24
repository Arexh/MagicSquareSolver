package com.arexh.magicsquare.algorithm;

import edu.cmu.cs.xfxie.MagicSquareSolver.AlgorithmCallBack;

public class MagicSquareSolver extends AlgorithmSolver {
    private final int[][] constrainMatrix;
    private final int constrainRow;
    private final int constrainCol;
    private final int dimension;
    private final int earlyStop = 1000;

    public MagicSquareSolver(int dimension, int[][] constrainMatrix,
                             int constrainRow, int constrainCol) {
        this.dimension = dimension;
        this.constrainMatrix = constrainMatrix;
        this.constrainRow = constrainRow;
        this.constrainCol = constrainCol;
    }

    public MagicSquareSolver(int dimension) {
        this(dimension, null, -1, -1);
    }

    @Override
    protected void solve(SolverCallBack callBack) {
        AlgorithmCallBack algorithmCallBack = new AlgorithmCallBack() {
            @Override
            public void onAlgorithmReheat() {
                callBack.onReheat();
            }

            @Override
            public void onSquareChanged(int[][] square) {
                callBack.onSquareChanged(square);
            }
        };
        if (constrainMatrix == null) {
            edu.cmu.cs.xfxie.MagicSquareSolver.solve(dimension, earlyStop, algorithmCallBack);
        } else {
            edu.cmu.cs.xfxie.MagicSquareSolver.solve(dimension, constrainMatrix, constrainRow,
                    earlyStop, constrainCol, algorithmCallBack);
        }
    }

    public static void main(String[] args) {
        MagicSquareSolver magicSquareSolver = new MagicSquareSolver(20);
        magicSquareSolver.run(null);
    }
}
