package com.arexh.magicsquare.algorithm;

public abstract class AlgorithmSolver {

    private Thread thread;

    public AlgorithmSolver() {
    }

    public void run(SolverCallBack callBack) {
        thread = new Thread(() -> {
            solve(callBack);
            if (callBack != null) callBack.onFinish();
        });
        thread.start();
    }

    public void pause() {
        if (thread != null) thread.suspend();
    }

    public void resume() {
        if (thread != null) thread.resume();
    }

    public void stop() {
        if (thread != null) thread.stop();
    }

    protected abstract void solve(SolverCallBack callback);

    public interface SolverCallBack {
        void onReheat();
        void onSquareChanged(int[][] square);
        void onFinish();
    }
}
