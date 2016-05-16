/*
 Copyright (c) 2016, Mihai-Emil Andronache
 All rights reserved.

 Redistribution and use in source and binary forms, with or without modification,
 are permitted provided that the following conditions are met:
 1. Redistributions of source code must retain the above copyright notice,
 this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.
 3. Neither the name of the copyright holder nor the names of its contributors
 may be used to endorse or promote products derived from this software
 without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 ARE DISCLAIMED.
 IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.amihaiemil.eva;


/**
 * A stopwatch that runs on a separate thread.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 *
 */
class Stopwatch implements Runnable {
	private Thread watch;
	private int millis;
	
	public Stopwatch(int miliseconds) {
		this.millis = miliseconds;
		this.watch = new Thread(this);
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
				"Timeout exception because the algorithm did not finish execution in %d miliseconds",
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
