package com.amihaiemil.eva;

import java.util.List;
import java.util.Random;

/**
 * Simple evolutionary algorithm implementation.
 * No threads, no error range or complicated stopping conditions.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public final class SimpleEvolutionaryAlgorithm implements Eva{
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
     * Calculates the solution. Runs until the <b>numberOfGenerations</b> was exceeded or
     * until it finds a Solution with an acceptable Fitness (iterations might be exceeded
     * before finding an acceptable solution).
     * @return The best solution found (the solution with the best ok Fitness).
     * @throws IllegalStateException If no generator of solutions was specified.
     */
    public Solution calculate() throws IllegalStateException {
        if(this.solutionsGenerator == null) {
            throw new IllegalStateException("A generator of solutions must be specified!");
        }
        if(this.solutionsEvaluator == null) {
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
