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

import com.amihaiemil.eva.concurrency.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This decorator times the execution, in order to avoid too long runs.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public final class TimedEvolutionaryAlgorithm implements Eva {

	private Eva algorithm;
	private int miliseconds;
	
	/**
	 * Default constructor.
	 * A {@link SimpleEvolutionaryAlgorithm} is used.
	 * @param miliseconds For how many miliseconds (1/1000 * 1 second ) should this algorithm run, at most?
	 */
	public TimedEvolutionaryAlgorithm(int miliseconds) {
		this(new SimpleEvolutionaryAlgorithm(), miliseconds);
	}
	
	/**
	 * Constructor.
	 * @param alg The timed algorithm.
	 * @param miliseconds For how many miliseconds (1/1000 * 1 second ) should this algorithm run, at most?
	 */
	public TimedEvolutionaryAlgorithm(Eva alg, int miliseconds) {
		this.algorithm = alg;
		this.miliseconds = miliseconds;
	}
	
	/**
	 * @throws IllegalStateException if the solutions generator or the solutions evaluator is missing.
	 * @throws StopwatchException if the specified miliseconds passed and the algorithm didn't finish.
	 */
	public Solution calculate() {
        String algorithmName = this.algorithm.getClass().getName();
		Stopwatch timer = new Stopwatch(this.miliseconds);
        ExecutorService timerExecutorService = Executors.newSingleThreadExecutor();
        StopwatchSolutionCallback callback = new StopwatchSolutionCallback(timerExecutorService);
        AsynchronousEvaThread asyncEva = new AsynchronousEvaThread(
            this.algorithm,
            callback,
            algorithmName
        );

        Future timerFuture = timerExecutorService.submit(timer);
        asyncEva.start();
        try {
            timerFuture.get();
        } catch (InterruptedException e) {
            asyncEva.stop();
            return callback.getFound();
        } catch (ExecutionException e) {
            if(e.getMessage().contains("interrupted")) {
                return callback.getFound();
            } else {
                asyncEva.stop();
                throw new StopwatchException("Stopwatch exception!", e);
            }
        }
        return callback.getFound();
	}

	public Eva with(FitnessEvaluator evaluator) {
		return new TimedEvolutionaryAlgorithm(
			this.algorithm.with(evaluator),
			this.miliseconds
		);
	}

	public Eva with(SolutionsGenerator generator) {
		return new TimedEvolutionaryAlgorithm(
			this.algorithm.with(generator),
			this.miliseconds
		);
	}

	public Eva with(Condition additionalStoppingConditions) {
		return new TimedEvolutionaryAlgorithm(
			this.algorithm.with(additionalStoppingConditions),
			this.miliseconds
		);
	}
	
	public Condition conditions() {
		return this.algorithm.conditions();
	}

	public Eva with(Selection selection) {
		return new TimedEvolutionaryAlgorithm(
			this.algorithm.with(selection),
			this.miliseconds
		);
	}

	public Eva with(BestSelection bestSelection) {
		return new TimedEvolutionaryAlgorithm(
			this.algorithm.with(bestSelection),
			this.miliseconds
		);
	}

}
