//package com.arexh.magicsquare.algorithm;
//
//import edu.cmu.cs.xfxie.MagicSquareSolver.AlgorithmCallBack;
//
//public class MagicSquareSolver extends AlgorithmSolver {
//    private final AlgorithmCallBack callBack;
//    private final int[][] constrainMatrix;
//    private final int constrainRow;
//    private final int constrainCol;
//    private final int dimension;
//    private final int earlyStop = 1000000;
//
//    public MagicSquareSolver(int dimension, int[][] constrainMatrix,
//                             int constrainRow, int constrainCol,
//                             AlgorithmCallBack callBack) {
//        this.callBack = callBack;
//        this.dimension = dimension;
//        this.constrainMatrix = constrainMatrix;
//        this.constrainRow = constrainRow;
//        this.constrainCol = constrainCol;
//    }
//
//    public MagicSquareSolver(int dimension, AlgorithmCallBack callback) {
//        this(dimension, null, -1, -1, callback);
//    }
//
//    @Override
//    protected void solve() {
//        if (constrainMatrix == null) {
//            edu.cmu.cs.xfxie.MagicSquareSolver.solve(dimension, earlyStop, callBack);
//        } else {
//            edu.cmu.cs.xfxie.MagicSquareSolver.solve(dimension, constrainMatrix, constrainRow,
//                    earlyStop, constrainCol, callBack);
//        }
//    }
//
//    private static void printMatrix(int[][] matrix) {
//        int dimension = matrix.length;
//        for(int i = 0; i < dimension; ++i) {
//            for(int j = 0; j < dimension - 1; ++j) {
//                System.out.print(matrix[i][j] + ", ");
//            }
//            System.out.println(matrix[i][dimension - 1]);
//        }
//
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//        MagicSquareSolver magicSquareSolver = new MagicSquareSolver(100, new AlgorithmCallBack() {
//            @Override
//            public void onAlgorithmReheat() {
//                System.out.println("Reheated");
//            }
//
//            @Override
//            public void onSquareChanged(int[][] square) {
//                printMatrix(square);
////                System.out.println(edu.cmu.cs.xfxie.MagicSquareSolver.(square));
//            }
//        });
////        magicSquareSolver.run();
//    }
//}
