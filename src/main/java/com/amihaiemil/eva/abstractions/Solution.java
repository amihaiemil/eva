package com.amihaiemil.eva.abstractions;

import com.amihaiemil.eva.Fitness;
import com.amihaiemil.eva.Representation;

/**
 * Interface to be implemented by a possible solution of the algorithm.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public abstract class Solution {

    /**
     * Fitness of this solution. How good it is (how fit) in comparison to other solutions.
     */
    private Fitness fitness;

    /**
     * Representation of this solution.
     */
    protected Representation representation;

    /**
     * Each solution should have a comparable fitness that makes it better or worse than other solutions.
     * @return The Fitness of this solution.
     */
    public Fitness getFitness() {
        return this.fitness;
    }

    /**
     * Set the fitness of this solution.
     * @param fitness The set fitness of this solution.
     */
    void setFitness(Fitness fitness) {
        this.fitness = fitness;
    }

    /**
     * Mutate (change) this solution's representation with regards to pm - possibility of mutation.
     * @param pm A random chosen double (0 < pm < 1) that should give the random element in this solution's mutation.
     */
    public abstract void mutate(double pm);
}
