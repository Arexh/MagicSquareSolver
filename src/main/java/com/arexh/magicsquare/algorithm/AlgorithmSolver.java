package com.arexh.magicsquare.algorithm;

public abstract class AlgorithmSolver {

    private Thread thread;
    private volatile boolean isRunning;

    public AlgorithmSolver() {
    }

    public void run() {
        isRunning = true;
        thread = new Thread(this::solve);
        thread.start();
    }

    public void pause() {
        isRunning = false;
    }

    public void resume() {
        isRunning = true;
    }

    public void interrupt() {
        if (thread != null) thread.interrupt();
    }

    protected abstract void solve();
}
