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
 * Reruns the algorithm if the specified conditions aren't met (SimpleEvolutionaryAlgorithm can still
 * find a solution that doesn't meet the conditions). It is advisable to use this class together with
 * {@link TimedEvolutionaryAlgorithm} since it might happen that a solution to meet the conditions cannot be found
 * for the given input. <br><br>
 * <b>Note: </b> It makes no sense to use this if you haven't specified any additional conditions using <b>Eva.with(Condition)</b>.
 * 
 * @author Mihai Andronache (amihaiemil@gmail.com)
 *
 */
public final class RetryEvolutionaryAlgorithm implements Eva {
	private Eva algorithm;
	
	public Solution calculate() {
		Condition conditions = this.algorithm.conditions();
		Solution sol;
		sol = this.algorithm.calculate();
		if(! (conditions instanceof NoConditions)) {
			while(!conditions.passed(sol)) {
				sol = this.algorithm.calculate();
			}
		}
		return sol;
	}
	
	public RetryEvolutionaryAlgorithm(Eva alg) {
		this.algorithm = alg;
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
