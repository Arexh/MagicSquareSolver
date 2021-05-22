package algorithm;

import java.util.LinkedList;

public abstract class AlgorithmSolver {

    public AlgorithmSolver() {

    }

    public static interface AlgorithmCallBack {
        void onAlgorithmReheated();
        void onSquareChanged(int[][] square);
    }
}
