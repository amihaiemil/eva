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

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.amihaiemil.eva.util.BinaryArraySolution;

/**
 * Test cases for {@link Solutions}
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class SolutionsTestCase {
    /**
     * Population under test.
     */
    private Solutions popUt;

    @Before
    public void setup() {
        SolutionsGenerator generator = Mockito.mock(SolutionsGenerator.class);
        FitnessEvaluator evaluator = Mockito.mock(FitnessEvaluator.class);

        Solution solution = Mockito.spy(new BinaryArraySolution());
        Fitness fitness = Mockito.mock(Fitness.class);
        Selection selection = Mockito.mock(Selection.class);
        BestSelection bestSelection = Mockito.mock(BestSelection.class);

        Mockito.when(generator.generateRandomSolution()).thenReturn(solution);
        Mockito.when(evaluator.calculateFitnessForSolution(solution)).thenReturn(fitness);
        Mockito.when(selection.select(Mockito.anyCollectionOf(Solution.class))).thenReturn(solution);
        Mockito.when(bestSelection.selectBest(Mockito.anyCollectionOf(Solution.class))).thenReturn(solution);

        popUt = new Solutions(evaluator, generator, selection, bestSelection, 5);
    }

    /**
     * A Population of solutions can tell its size.
     */
    @Test
    public void getsSize() {
        assertTrue(popUt.size() == 5);
    }

    /**
     * A new individual can be added to the population.
     */
    @Test
    public void addIndividual() {
        int initialSize = popUt.size();
        popUt.addIndividual(Mockito.mock(Solution.class));
        assertTrue(popUt.size() == initialSize + 1);
    }

    /**
     * An individual cen be removed from the population.
     */
    @Test
    public void removeIndividual() {
        int initialSize = popUt.size();
        Solution ind = popUt.selectIndividual();
        popUt.removeIndividual(ind);
        assertTrue(popUt.size() == initialSize - 1);
    }

    /**
     * An individual can be selected from the population.
     */
    @Test
    public void selectsIndividual() {
        assertTrue(popUt.selectIndividual() != null);
    }

    /**
     * The best individual can be selected from the population.
     */
    @Test
    public void selectsBestIndividual() {
        assertTrue(popUt.bestIndividual() != null);
    }

    /**
     * A population can return its list of individuals.
     */
    @Test
    public void getsListOfIndividualt() {
        assertTrue(popUt.getIndividuals() != null);
        assertTrue(popUt.getIndividuals().size() == 5);
    }

    /**
     * Individuals of a population can be evaluated.
     */
    @Test
    public void evaluatesIndividuals() {
        Collection<Solution> individuals = popUt.getIndividuals();
        for(Solution ind : individuals) {
            assertTrue(ind.getFitness() == null);
        }
        popUt.evaluateIndividuals();
        for(Solution ind : individuals) {
            assertTrue(ind.getFitness() != null);
        }
    }
}
