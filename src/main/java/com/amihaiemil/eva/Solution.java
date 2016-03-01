package com.amihaiemil.eva;

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
    private Representation representation;

    /**
     * Each solution should have a comparable fitness that makes it better or worse than other solutions.
     * @return The Fitness of this solution.
     */
    public Fitness getFitness() {
        return this.fitness;
    }

    /**
     * Each solution should have a representation.
     * @return The representation of this solution.
     */
    public Representation getRepresentation() { return this.representation;}

    public void setRepresentation(Representation rep) { this.representation = rep;}

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

    /**
     * Crossover this solution with the given partner.
     * In other words, create a new solution with data taken from both this solution and the partner.
     * @param partner Another solution.
     */
    public abstract Solution crossover(Solution partner);

    /**
     * A solution is acceptable if its fitness is ok.
     * @return true if acceptable, false otherwise.
     */
    public boolean isAcceptable() {
        return this.fitness.isOk();
    }
}
