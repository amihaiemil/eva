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
 * Simple evolutionary algorithm implementation.
 * No threads, special stopping conditions or error range.
 * This class is immutable and thread safe.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public final class SimpleEvolutionaryAlgorithm implements Eva{
    private static final Logger logger = LoggerFactory.getLogger(SimpleEvolutionaryAlgorithm.class);
    
    private EvaCounters cntrs;
    private SolutionsGenerator solutionsGenerator;
    private FitnessEvaluator solutionsEvaluator;

    /**
     * Additional stopping conditions.
     */
    private Condition additionalCondition = null;
    
    /**
     * The way an individual solution selected from its population.
     */
    private Selection selection = null;
    
    /**
     * The way the best individual is found in its population.
     */
    private BestSelection bestSelection = null;
    
    /**
     * Default constructor with default values for population size
     * (1000) and number of generations (150).<br><br>
     * <b>Important</b><br>
     * You have to specify a SolutionsGenerator and a FitnessEvaluator using
     * <b>with(...)</b> methods!
     */
    public SimpleEvolutionaryAlgorithm() {
        this(1000, 150);
    }

    /**
     * Constructor with parameters for population size and number of generations.<br><br>
     * <b>Important</b><br>
     * You have to specify a SolutionsGenerator and a FitnessEvaluator using
     * <b>with(...)</b> methods!
     * @param population The number of initial solutions.
     * @param generations The number of minimum iterations this algorithm does.
     */
    public SimpleEvolutionaryAlgorithm(int population, int generations) {
        this(population, generations, null, null);
    }
    
    /**
     * Constructor.
     * @param population The number of initial solutions.
     * @param generations The number of minimum iterations this algorithm does.
     */
    public SimpleEvolutionaryAlgorithm(
        int population, int generations, SolutionsGenerator sgen, FitnessEvaluator fev
    ) {
        this(
            new EvaCountersWithChecks(population, generations), sgen, fev,
            new NoConditions(), new DefaultSelection(), new DefaultBestSelection()
        );
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
        EvaCounters cntrs,
        SolutionsGenerator generator,
        FitnessEvaluator evaluator,
        Condition conditions,
        Selection selection,
        BestSelection bestSelection
    ) {
        this.cntrs = cntrs;
        this.solutionsGenerator = generator;
        this.solutionsEvaluator = evaluator;
        this.additionalCondition = conditions;
        this.selection = selection;
        this.bestSelection = bestSelection;
    }

    public Eva with(SolutionsGenerator generator) {
        return new SimpleEvolutionaryAlgorithm(
            this.cntrs, generator, this.solutionsEvaluator, this.additionalCondition,
            this.selection, this.bestSelection);
    }

    public Eva with(FitnessEvaluator evaluator) {
        return new SimpleEvolutionaryAlgorithm(
            this.cntrs, this.solutionsGenerator, evaluator, this.additionalCondition,
            this.selection, this.bestSelection);
    }

    public Eva with(Condition additionalCondition) {
        return new SimpleEvolutionaryAlgorithm(
            this.cntrs, this.solutionsGenerator, this.solutionsEvaluator, additionalCondition,
            this.selection, this.bestSelection);
    }
    
    public Condition conditions() {
        return this.additionalCondition;
    }

    public Eva with(Selection selection) {
        return new SimpleEvolutionaryAlgorithm(
            this.cntrs, this.solutionsGenerator, this.solutionsEvaluator,
            this.additionalCondition, selection, this.bestSelection);
    }

    public Eva with(BestSelection bestSelection) {
        return new SimpleEvolutionaryAlgorithm(
            this.cntrs, this.solutionsGenerator, this.solutionsEvaluator,
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
                this.selection, this.bestSelection, this.cntrs.population());

        initialPopulation.evaluateIndividuals();
        Population newPopulation;
        for (int i = 0; i < this.cntrs.generations(); i++) {
            newPopulation = new Population(this.solutionsEvaluator, this.selection, this.bestSelection);
            for (int j = 0; j < this.cntrs.population(); j++) {
                
                Solution child = initialPopulation.selectIndividual().crossover(
                                     initialPopulation.selectIndividual(),
                                     this.cntrs.crossover()
                                 );
                child.mutate(this.cntrs.mutation());
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
