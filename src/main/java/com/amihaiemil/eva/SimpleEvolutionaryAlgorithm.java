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
 * Simple evolutionary algorithm implementation.
 * No threads, special stopping conditions or error range.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public final class SimpleEvolutionaryAlgorithm implements Eva{
    private static final Logger logger = LoggerFactory.getLogger(SimpleEvolutionaryAlgorithm.class);
    private int populationSize;
    private int numberOfGenerations;
    private Random random = new Random();
    private double crossoverProbability = random.nextDouble();
    private double mutationProbability = random.nextDouble();

    private SolutionsGenerator solutionsGenerator;
    private FitnessEvaluator solutionsEvaluator;

    /**
     * Additional stopping conditions.
     */
    private Condition additionalCondition = new Condition() {
        public boolean passed(Solution s) {
            return false;
        }
    };
    
    /**
     * The way an individual solution selected from its population.
     */
    private Selection selection = new DefaultSelection();

    private Population initialPopulation;
    /**
     * Default constructor with default values for population size and mutation probability.
     */
    public SimpleEvolutionaryAlgorithm() {
        this(1000, 150);
    }

    /**
     * Constructor with parameters for population size and number of generations.
     * @param population The number of initial solutions.
     * @param generations The number of minimum iterations this algorithm does.
     */
    public SimpleEvolutionaryAlgorithm(int population, int generations) {
        this.populationSize = population;
        this.numberOfGenerations = generations;
        logger.info("Initialized evolutionary algorithm with population size " + population +
                " number of generations: " + generations);
    }

    public Eva with(SolutionsGenerator generator) {
        this.solutionsGenerator = generator;
        return this;
    }

    public Eva with(FitnessEvaluator evaluator) {
        this.solutionsEvaluator = evaluator;
        return this;
    }

    public Eva with(Condition additionalCondition) {
        this.additionalCondition = additionalCondition;
        return this;
    }

    public Eva with(Selection selection) {
    	this.selection = selection;
    	return this;
    }

    /**
     * Calculates the solution. Does <b>numberOfGenerations</b> x <b>populationSize</b>
     * iterations or more, until the additional stopping conditions are met (if any are specified).
     * @return A solution.
     */
    public Solution calculate() {
        logger.info("Running the evolutionary algorithm...");
        if(this.solutionsGenerator == null) {
            logger.error("No generator of solutions was specified!");
            throw new IllegalStateException("A generator of solutions must be specified!");
        }
        if(this.solutionsEvaluator == null) {
            logger.error("No fitness evaluator was specified!");
            throw new IllegalStateException("An evaluator of solutions must be specified!");
        }
        this.initialPopulation = new Population(
        		this.solutionsEvaluator, this.solutionsGenerator,
        		this.selection, this.populationSize);
        
        this.initialPopulation.evaluateIndividuals();
        Population newPopulation;
        for (int i = 0; i < numberOfGenerations; i++) {
            newPopulation = new Population(this.solutionsEvaluator, this.selection);
            for (int j = 0; j < populationSize; j++) {
            	
                Solution child = initialPopulation.selectIndividual().crossover(
                		             initialPopulation.selectIndividual(),
                		             this.crossoverProbability
                		         );
                child.mutate(mutationProbability);
                child.setFitness(solutionsEvaluator.calculateFitnessForSolution(child));
                if(additionalCondition.passed(child)) {
                    logger.info("Evolutionary algorithm run finished successfully! The found solution meets the specified conditions.");
                    return child;
                }
                newPopulation.addIndividual(child);
            }
            initialPopulation = newPopulation;
        }
        logger.info("Evolutionary algorithm run finished successfully!");
        return initialPopulation.bestIndividual();
    }

}
