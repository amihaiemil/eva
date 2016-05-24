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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reruns the algorithm if the specified conditions aren't met (SimpleEvolutionaryAlgorithm can still
 * find a solution that doesn't meet the conditions). 
 * 
 * <b>Note: </b> It makes no sense to use this if you haven't specified any additional conditions using <b>Eva.with(Condition)</b>.
 * 
 * @author Mihai Andronache (amihaiemil@gmail.com)
 *
 */
public final class RetryEvolutionaryAlgorithm implements Eva {
	private Eva algorithm;
	private int totalRuns;
    private static final Logger logger = LoggerFactory.getLogger(RetryEvolutionaryAlgorithm.class);

	
	public Solution calculate() {
		Condition conditions = this.algorithm.conditions();
		Solution sol;
		sol = this.algorithm.calculate();
		int runsPerformed = 1;
		if(! (conditions instanceof NoConditions)) {
			while(runsPerformed <= this.totalRuns && !conditions.passed(sol)) {
				logger.warn("Found solution doesn't meet the specified conditions. Rerun #" + runsPerformed);
				runsPerformed++;
				sol = this.algorithm.calculate();
				
			}
		}
		
		logger.info("Algorithm runs: " + runsPerformed);
		if(conditions.passed(sol)) {
			logger.info("Found solution meets the conditions!");
		} else {
			logger.warn("Found solution DOES NOT meet the conditions!");
		}

		return sol;
	}
	
	/**
	 * Constructor. Default number of reruns is 3.
	 * @param alg The algoritm to run.
	 */
	public RetryEvolutionaryAlgorithm(Eva alg) {
		this(alg, 3);
	}
	
	/**
	 * Constructor.
	 * @param alg The algorithm to run.
	 * @param runs Max number of reruns.
	 */
	public RetryEvolutionaryAlgorithm(Eva alg, int runs) {
		this.algorithm = alg;
		this.totalRuns = runs;
	}
	
	public Eva with(FitnessEvaluator evaluator) {
		return new RetryEvolutionaryAlgorithm(
			this.algorithm.with(evaluator)
		);
	}

	public Eva with(SolutionsGenerator generator) {
		return new RetryEvolutionaryAlgorithm(
			this.algorithm.with(generator)
		);
	}

	public Eva with(Condition additionalStoppingConditions) {
		return new RetryEvolutionaryAlgorithm(
			this.algorithm.with(additionalStoppingConditions)
		);
	}
	
	public Condition conditions() {
		return this.algorithm.conditions();
	}

	public Eva with(Selection selection) {
		return new RetryEvolutionaryAlgorithm(
			this.algorithm.with(selection)
		);
	}

	public Eva with(BestSelection bestSelection) {
		return new RetryEvolutionaryAlgorithm(
			this.algorithm.with(bestSelection)
		);
	}
}
