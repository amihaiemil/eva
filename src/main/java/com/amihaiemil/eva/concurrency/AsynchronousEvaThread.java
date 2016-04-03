package com.amihaiemil.eva.concurrency;

import com.amihaiemil.eva.Eva;

/**
 * Asynchronous thread for running an evolutionary algorithm.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public final class AsynchronousEvaThread implements Runnable {
    private SolutionCallback callback;
    private Eva algorithm;
    private String threadName;
    private int runs;
    private Thread tr;
    /**
     * Constructor.
     * @param algorithm The evolutionary algorithm to be run.
     * @param callback The callback logic (what to do with the found solution?).
     * @param name The name of this runnable (it will be suffixed with _nrOfRuns).
     */
    public AsynchronousEvaThread(Eva algorithm, SolutionCallback callback, String name) {
        this.algorithm = algorithm;
        this.callback = callback;
        this.threadName = name;
    }

    public void run() {
        callback.execute(algorithm.calculate());
    }

    /**
     * Start the execution of a new thread.
     */
    public void start() {
        runs++;
        tr = new Thread(this, threadName + "_" + runs);
        tr.start();

    }
}
