package com.amihaiemil.eva.abstractions;

import com.amihaiemil.eva.Fitness;

import java.util.List;

/**
 * Each solution needs to have its fitness evaluated.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public abstract class FitnessEvaluator {

    private List<Solution> solutions;

    public FitnessEvaluator(List<Solution> solutions) {
        this.solutions = solutions;
    }

    /**
     * Decide the Fitness of a solution.
     * @param solution The solution for which the fitness is calculated.
     */
    public abstract Fitness calculateFitnessForSolution(Solution solution);

    void evaluateSolutions() {
        for(int i=0;i<solutions.size();i++) {
            solutions.get(i).setFitness(this.calculateFitnessForSolution(solutions.get(i)));
            if(solutions.get(i).getFitness() == null) {
                throw new IllegalStateException("Fitness of the solution should have been set by now!");
            }
        }
    }
}
