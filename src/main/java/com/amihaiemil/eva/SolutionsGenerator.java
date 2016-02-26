package com.amihaiemil.eva;

/**
 * Implemented by a generator of random solutions.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public interface SolutionsGenerator {

    /**
     * Generate a possible solution. Make sure this method does return a random solution every time.
     * @return A possible solution to the problem.
     */
    Solution generateRandomSolution();
}
