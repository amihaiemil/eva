package com.amihaiemil.eva;

import com.amihaiemil.eva.abstractions.FitnessEvaluator;
import com.amihaiemil.eva.abstractions.Solution;

import java.util.Random;

/**
 * Evolving algorithm implementation
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public final class EvolvingAlgorithm {
    private int populationSize;
    private int numberOfGenerations;
    private double crossoverProbability;
    private double mutationProbability;
    private Random random = new Random();
    private SolutionsGenerator solutionsGenerator;
    private FitnessEvaluator solutionsEvaluator;

    private Population initialPopulation;
    /**
     * Default constructor with default values for population size and mutation probability.
     */
    public EvolvingAlgorithm() {
        this.populationSize = 100;
        this.numberOfGenerations = 1000;
        this.crossoverProbability = random.nextDouble();
        this.mutationProbability = random.nextDouble();
    }

    /**
     * Constructor with parameters for population size and number of generations.
     */
    public EvolvingAlgorithm(int population, int generations) {
        this.populationSize = population;
        this.numberOfGenerations = generations;
        this.crossoverProbability = random.nextDouble();
        this.mutationProbability = random.nextDouble();
    }

    /**
     * Specify the solutions generator used by this algorithm.
     * @param generator An object that generates a list of solutions.
     * @return This algorithm.
     */
    public EvolvingAlgorithm with(SolutionsGenerator generator) {
        this.solutionsGenerator = generator;
        return this;
    }

    /**
     * Specify the fitness evaluator used by this algorithm.
     * @param evaluator An object that knows to evaluate the fitness of a solution.
     * @return This algorithm.
     */
    public EvolvingAlgorithm with(FitnessEvaluator evaluator) {
        this.solutionsEvaluator = evaluator;
        return this;
    }

    /**
     * Runs this evolving algorithm.
     * @return The best solution found (the solution with the best Fitness)
     * @throws IllegalStateException If no generator of solutions was specified.
     */
    public Solution run() throws IllegalStateException {
        if(this.solutionsGenerator == null) {
            throw new IllegalStateException("A generator of solutions must be specified!");
        }
        if(this.solutionsEvaluator == null) {
            throw new IllegalStateException("An evaluator of solutions must be specified!");
        }
        this.initialPopulation = new Population(solutionsGenerator, populationSize);

        return null;
    }
}
