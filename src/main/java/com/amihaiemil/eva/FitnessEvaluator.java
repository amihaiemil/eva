package com.amihaiemil.eva;

import java.util.List;

/**
 * Each solution needs to have its fitness evaluated.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public interface FitnessEvaluator {
    /**
     * Decide the Fitness of a solution.
     * @param solution The solution for which the fitness is calculated.
     * @return The fitness of the given solution.
     */
    public Fitness calculateFitnessForSolution(Solution solution);
}
