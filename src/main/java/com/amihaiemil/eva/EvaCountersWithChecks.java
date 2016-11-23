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

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EvaCounters which check for preconditions, like
 * population and nr of generation should not be negative.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 1.2.0
 */
final class EvaCountersWithChecks implements EvaCounters {

	/**
	 * Logger.
	 */
    private static final Logger logger = LoggerFactory.getLogger(EvaCountersWithChecks.class);

    /**
     * To generate crossover and mutation probs.
     */
    private static final Random random = new Random();

    /**
	 * Population size.
	 */
    private int populationSize;

    /**
     * Nr of generations.
     */
    private int numberOfGenerations;

    /**
     * Crossover prob.
     */
    private double crossoverProbability = random.nextDouble();

    /**
     * Mutation prob.
     */
    private double mutationProbability = random.nextDouble();

    /**
     * Default ctor.
     */
    EvaCountersWithChecks() {
    	this(1000, 150);
    }

    /**
     * Constructor with parameters for population size and number of generations.
     * @param population The number of initial solutions.
     * @param generations The number of minimum iterations this algorithm does.
     */
    EvaCountersWithChecks(int population, int generations) {
        this.populationSize = population;
        this.numberOfGenerations = generations;
    }

	@Override
	public int population() {
		if(this.populationSize <= 0) {
			logger.error("Population size is negative or 0, can't continue!");
			throw new IllegalArgumentException("The population size has to be a positive integer!");
		}

		return this.populationSize;
	}

	@Override
	public int generations() {
		if(this.numberOfGenerations <= 0) {
			logger.error("Nr of generations is negative or 0, can't continue!");
			throw new IllegalArgumentException("The number of generations has to be a positive integer!");
		}
		return this.numberOfGenerations;
	}

	@Override
	public double crossover() {
		return this.crossoverProbability;
	}

	@Override
	public double mutation() {
		return this.mutationProbability;
	}

}
