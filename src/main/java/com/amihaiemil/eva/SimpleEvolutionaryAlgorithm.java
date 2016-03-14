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

import java.util.List;
import java.util.Random;

/**
 * Simple evolutionary algorithm implementation.
 * No threads, no error range or complicated stopping conditions.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public final class SimpleEvolutionaryAlgorithm implements Eva{
    private static final Logger logger = LoggerFactory.getLogger(SimpleEvolutionaryAlgorithm.class);
    private int populationSize;
    private int numberOfGenerations;
    private double crossoverProbability;
    private double mutationProbability;
    private Random random = new Random();
    private SolutionsGenerator solutionsGenerator;
    private FitnessEvaluator solutionsEvaluator;

    /**
     * Additional stopping conditions.
     */
    private Condition additionalCondition;

    private Population initialPopulation;
    /**
     * Default constructor with default values for population size and mutation probability.
     */
    public SimpleEvolutionaryAlgorithm() {
        this.populationSize = 1000;
        this.numberOfGenerations = 150;
        this.crossoverProbability = random.nextDouble();
        this.mutationProbability = random.nextDouble();
        this.additionalCondition = new Condition() {
            public boolean passed(Solution s) {
                return true;
            }
        };
        logger.debug("Initialized evolutionary algorithm with default parameters");
    }

    /**
     * Constructor with parameters for population size and number of generations.
     * @param population The number of initial solutions.
     * @param generations The number of minimum iterations this algorithm does.
     */
    public SimpleEvolutionaryAlgorithm(int population, int generations) {
        this.populationSize = population;
        this.numberOfGenerations = generations;
        this.crossoverProbability = random.nextDouble();
        this.mutationProbability = random.nextDouble();
        this.additionalCondition = new Condition() {
            public boolean passed(Solution s) {
                return true;
            }
        };
        logger.debug("Initialized evolutionary algorithm with population size " + population +
                " number of generations: " + generations);
    }

    /**
     * Specify the solutions generator used by this algorithm.
     * @param generator An object that generates a list of solutions.
     * @return This algorithm.
     */
    public Eva with(SolutionsGenerator generator) {
        this.solutionsGenerator = generator;
        return this;
    }

    /**
     * Specify the fitness evaluator used by this algorithm.
     * @param evaluator An object that knows to evaluate the fitness of a solution.
     * @return This algorithm.
     */
    public Eva with(FitnessEvaluator evaluator) {
        this.solutionsEvaluator = evaluator;
        return this;
    }

    /**
     * Specify additional conditions that have to be met by the chosen solution.
     * @param additionalCondition The specified condition(s).
     * @return This algorithm.
     */
    public Eva with(Condition additionalCondition) {
        this.additionalCondition = additionalCondition;
        return this;
    }

    /**
     * Calculates the solution. Does <b>numberOfGenerations</b> x <b>populationSize</b>
     * iterations or more, until the additional stopping conditions are met (if any are specified).
     * @return A solution.
     */
    public Solution calculate() {
        logger.debug("Running the evolutionary algorithm...");
        if(this.solutionsGenerator == null) {
            logger.error("No generator of solutions was specified!");
            throw new IllegalStateException("A generator of solutions must be specified!");
        }
        if(this.solutionsEvaluator == null) {
            logger.error("No fitness evaluator was specified!");
            throw new IllegalStateException("An evaluator of solutions must be specified!");
        }
        this.initialPopulation = new Population(solutionsGenerator, populationSize);
        this.evaluateSolutions(initialPopulation.getIndividuals());
        Population newPopulation;
        Solution bestSolutionFound;
        do {
            for (int i = 0; i < numberOfGenerations; i++) {
                newPopulation = new Population();
                for (int j = 0; j < populationSize; j++) {
                    Solution child = this.mate(initialPopulation.selectIndividual(), initialPopulation.selectIndividual());
                    child.mutate(mutationProbability);
                    child.setFitness(solutionsEvaluator.calculateFitnessForSolution(child));
                    newPopulation.addIndividual(child);
                }
                initialPopulation = newPopulation;
            }
            bestSolutionFound = initialPopulation.bestIndividual();
        } while (!additionalCondition.passed(bestSolutionFound));
        logger.debug("Evolutionary algorithm run finished! Solution found: " + bestSolutionFound);
        return bestSolutionFound;
    }

    /**
     * Create a child solution based on 2 other solutions, having in regards the crossover probability.
     * @param mother The "mother" of the created solution.
     * @param father The "father" of the created solution.
     * @return A child solution or the best one of the mother and the father, if they "cannot have children".
     */
    private Solution mate(Solution mother, Solution father) {
        if(random.nextDouble() < crossoverProbability) { //parents have an offspring
            return mother.crossover(father);
        } else { //no offspring, return best parrent.
            if(mother.getFitness().compareTo(father.getFitness()) == 1) {
                return mother;
            }
            return father;
        }
    }

    /**
     * Evaluates a list of possible solutions (their fitness is evaluated and set).
     * @param solutions The list of evaluated possible solutions.
     */
    private void evaluateSolutions(List<Solution> solutions) {
        for(int i=0;i<solutions.size();i++) {
            solutions.get(i).setFitness(solutionsEvaluator.calculateFitnessForSolution(solutions.get(i)));
            if(solutions.get(i).getFitness() == null) {
                throw new IllegalStateException("Fitness of the solution should have been set by now!");
            }
        }
    }

}
