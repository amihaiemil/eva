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
package com.amihaiemil.eva.util;

import com.amihaiemil.eva.Solution;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link BinaryArraySolution}.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class BinaryArraySolutionTestCase {
    /**
     * BinaryArraySolution can mutate.
     */
    @Test
    public void solutionMutates() {
        Random mockChance = Mockito.mock(Random.class);
        Mockito.when(mockChance.nextDouble()).thenReturn(0.3);

        Solution testSolution = new BinaryArraySolution(mockChance);
        NumericalRepresentation binaryRepresentation = new NumericalRepresentation();
        binaryRepresentation = binaryRepresentation.addNumber(0);
        binaryRepresentation = binaryRepresentation.addNumber(1);
        binaryRepresentation = binaryRepresentation.addNumber(1);
        testSolution.setRepresentation(binaryRepresentation);

        testSolution.mutate(0.5);
        binaryRepresentation = (NumericalRepresentation) testSolution.getRepresentation();
        assertTrue(binaryRepresentation.get(0) == 1);
        assertTrue(binaryRepresentation.get(1) == 0);
        assertTrue(binaryRepresentation.get(2) == 0);
    }

    /**
     * Two solutions can crossover.
     */
    @Test
    public void solutionsCrossover() {
        Random mockChance = Mockito.mock(Random.class);
        Mockito.when(mockChance.nextInt(4)).thenReturn(2);
        Mockito.when(mockChance.nextDouble()).thenReturn(0.25);

        Solution mother= new BinaryArraySolution(mockChance);
        Solution father= new BinaryArraySolution(mockChance);
        mother.setRepresentation(new NumericalRepresentation(Arrays.asList(0,1,0,0)));
        father.setRepresentation(new NumericalRepresentation(Arrays.asList(0,0,1,1)));

        Solution offspring = mother.crossover(father, 0.5);
        NumericalRepresentation offspringRepresentation = (NumericalRepresentation) offspring.getRepresentation();
        assertTrue(offspringRepresentation.get(0) == 0);
        assertTrue(offspringRepresentation.get(1) == 1);
        assertTrue(offspringRepresentation.get(2) == 1);
        assertTrue(offspringRepresentation.get(3) == 1);
    }
}






