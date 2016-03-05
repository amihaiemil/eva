package com.amihaiemil.eva.ex;

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
        binaryRepresentation.addNumber(0);
        binaryRepresentation.addNumber(1);
        binaryRepresentation.addNumber(1);
        testSolution.setRepresentation(binaryRepresentation);

        testSolution.mutate(0.5);
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

        Solution mother= new BinaryArraySolution(mockChance);
        Solution father= new BinaryArraySolution(mockChance);
        mother.setRepresentation(new NumericalRepresentation(Arrays.asList(0,1,0,0)));
        father.setRepresentation(new NumericalRepresentation(Arrays.asList(0,0,1,1)));

        Solution offspring = mother.crossover(father);
        NumericalRepresentation offspringRepresentation = (NumericalRepresentation) offspring.getRepresentation();
        assertTrue(offspringRepresentation.get(0) == 0);
        assertTrue(offspringRepresentation.get(1) == 1);
        assertTrue(offspringRepresentation.get(2) == 1);
        assertTrue(offspringRepresentation.get(3) == 1);
    }
}






