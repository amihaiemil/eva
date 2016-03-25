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

/**
 * Abstract class to be extended by a possible solution of the algorithm.
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
     * Set the fitness of this solution.
     * @param fitness The set fitness of this solution.
     */
    public void setFitness(Fitness fitness) {
        this.fitness = fitness;
    }

    /**
     * Each solution should have a representation.
     * @return The representation of this solution.
     */
    public Representation getRepresentation() {
    	return this.representation;
    }

    public void setRepresentation(Representation rep) {
    	this.representation = rep;
    }

    /**
     * Mutate (change) this solution's representation with regards to pm - possibility of mutation.
     * @param pm A random chosen double (0 &lt; pm &lt; 1) that should give the random element in this solution's mutation.
     */
    public abstract void mutate(double pm);

    /**
     * Crossover this solution with the given partner.
     * In other words, create a new solution with data taken from both this solution and the partner.
     * @param partner Another solution.
     * @param cp Crossover probability.
     * @return The newly created solution.
     */
    public abstract Solution crossover(Solution partner, double cp);

    /**
     * A solution is acceptable if its fitness is ok.
     * @return true if acceptable, false otherwise.
     */
    public boolean isAcceptable() {
        return this.fitness.isOk();
    }
}
