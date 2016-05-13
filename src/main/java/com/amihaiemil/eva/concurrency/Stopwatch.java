package com.amihaiemil.eva.concurrency;


/**
 * A stopwatch that runs on a separate thread.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 *
 */
public class Stopwatch implements Runnable {
	private Thread watch;
	private int millis;
	
	public Stopwatch(int miliseconds, String name) {
		this.millis = miliseconds;
		this.watch = new Thread(this, name);
	}
	
	/**
     * Start the execution of a new thread.
     */
    public void start() {
    	watch.start();
    }

	public void run() {
		try {
			Thread.sleep(this.millis);
			String message = String.format(
				"Timeout exception, because %s did not finish execution in %d miliseconds",
				watch.getName(),
				this.millis
			);
			throw new StopwatchException(message);
		} catch (InterruptedException e) {
			throw new StopwatchException("Stop watch interrupted!", e);
		}
	}
	
	public void stop() throws StopwatchException {
		watch.interrupt();
	}
}
