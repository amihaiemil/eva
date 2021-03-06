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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * A generation of possible solutions.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
final class Solutions implements Generation {
    private List<Solution> individuals;
    private FitnessEvaluator evaluator;
    private Selection selection;
    private BestSelection bestSelection;

    /**
     * Create a population of individuals, with a given size.
     * @param evaluator The fitness evaluator.
     * @param generator The generator of random solutions.
     * @param selection The individual Selection algorithm.
     * @param size The size of the population.
     */
    Solutions(
    	FitnessEvaluator evaluator,
    	SolutionsGenerator generator,
    	Selection selection,
    	BestSelection bestSelection,
    	int size
    ) {
        this.individuals = new LinkedList<Solution>();
        for(int i=0;i<size;i++) {
            individuals.add(generator.generateRandomSolution());
        }
        this.evaluator = evaluator;
        this.selection = selection;
        this.bestSelection = bestSelection;
    }

    /**
     * Create a population with no individuals.
     * @param evaluator The fitness evaluator.
     * @param selection The individual Selection algorithm.
     * @param bestSelection Selection of the best algorithm.
     */
    Solutions(
    	FitnessEvaluator evaluator,
    	Selection selection,
    	BestSelection bestSelection
    ) {
        this(evaluator, null, selection, bestSelection, 0);
    }

    public Collection<Solution> getIndividuals() {
        return this.individuals;
    }

    public Solution selectIndividual() {
    	return this.selection.select(this.individuals);
    }

    public Solution bestIndividual() {
        return this.bestSelection.selectBest(this.individuals);
    }

    public void addIndividual(Solution individual) {
        this.individuals.add(individual);
    }

    public void removeIndividual(Solution individual) {
        this.individuals.remove(individual);
    }

    public int size() {
        return this.individuals.size();
    }

    public void evaluateIndividuals() {
    	for(int i=0;i<individuals.size();i++) {
    		individuals.get(i).setFitness(evaluator.calculateFitnessForSolution(individuals.get(i)));
            if(individuals.get(i).getFitness() == null) {
                throw new IllegalStateException("Fitness of the solution should have been set by now!");
            }
        }
    }
}
