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
 * This class is immutable and thread safe.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public final class SimpleEvolutionaryAlgorithm implements Eva{
    private static final Logger logger = LoggerFactory.getLogger(SimpleEvolutionaryAlgorithm.class);
    private static final Random random = new Random();

    private int populationSize;
    private int numberOfGenerations;
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
    
    /**
     * The way the best individual is found in its population.
     */
    private BestSelection bestSelection = new DefaultBestSelection();
    
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

    /**
     * Constructor (used internally to provide immutability).
     * @param population Size of the initial population.
     * @param generations Number of generations.
     * @param crossoverProbability Crossover probability.
     * @param mutationProbability Mutation probability.
     * @param generator Generator of solutions.
     * @param evaluator Evaluator of solutions.
     * @param conditions Additional stopping conditions.
     * @param selection Selection method.
     * @param bestSelection Final result (best solution) selection method.
     */
    private SimpleEvolutionaryAlgorithm(
        int population, int generations,
        double crossoverProbability, double mutationProbability,
        SolutionsGenerator generator,
        FitnessEvaluator evaluator,
        Condition conditions,
        Selection selection,
        BestSelection bestSelection) {
        this.populationSize = population;
        this.numberOfGenerations = generations;
        this.crossoverProbability = crossoverProbability;
        this.mutationProbability = mutationProbability;
        this.solutionsGenerator = generator;
        this.solutionsEvaluator = evaluator;
        this.additionalCondition = conditions;
        this.selection = selection;
        this.bestSelection = bestSelection;
    }

    public Eva with(SolutionsGenerator generator) {
        return new SimpleEvolutionaryAlgorithm(
            this.populationSize, this.numberOfGenerations, this.crossoverProbability,
            this.mutationProbability, generator, this.solutionsEvaluator, this.additionalCondition,
            this.selection, this.bestSelection);
    }

    public Eva with(FitnessEvaluator evaluator) {
        return new SimpleEvolutionaryAlgorithm(
            this.populationSize, this.numberOfGenerations, this.crossoverProbability,
            this.mutationProbability, this.solutionsGenerator, evaluator, this.additionalCondition,
            this.selection, this.bestSelection);
    }

    public Eva with(Condition additionalCondition) {
        return new SimpleEvolutionaryAlgorithm(
            this.populationSize, this.numberOfGenerations, this.crossoverProbability,
            this.mutationProbability, this.solutionsGenerator, this.solutionsEvaluator, additionalCondition,
            this.selection, this.bestSelection);
    }

    public Eva with(Selection selection) {
        return new SimpleEvolutionaryAlgorithm(
            this.populationSize, this.numberOfGenerations, this.crossoverProbability,
            this.mutationProbability, this.solutionsGenerator, this.solutionsEvaluator,
            this.additionalCondition, selection, this.bestSelection);
    }

    public Eva with(BestSelection bestSelection) {
        return new SimpleEvolutionaryAlgorithm(
            this.populationSize, this.numberOfGenerations, this.crossoverProbability,
            this.mutationProbability, this.solutionsGenerator, this.solutionsEvaluator,
            this.additionalCondition, this.selection, bestSelection);
    }
    
    /**
     * Calculates the solution. Does <b>numberOfGenerations</b> x <b>populationSize</b>
     * iterations or less, if the stopping conditions are met.
     * @return A solution.
     * @throws IllegalStateException if the solutions generator or the solutions evaluator is missing.
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
        Population initialPopulation = new Population(
        		this.solutionsEvaluator, this.solutionsGenerator,
        		this.selection, this.bestSelection, this.populationSize);

        initialPopulation.evaluateIndividuals();
        Population newPopulation;
        for (int i = 0; i < numberOfGenerations; i++) {
            newPopulation = new Population(this.solutionsEvaluator, this.selection, this.bestSelection);
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
