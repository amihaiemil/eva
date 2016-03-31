package com.amihaiemil.eva;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

/**
 * Test cases for individual's selection.
 * @author Mihai Andronache (mihai.andronache@urss.ro)
 *
 */
public class SelectionTestCase {
    /**
     * The default selection can return a solution from a collection of solutions.
     */
	@Test
	public void defaultSelectionWorks() {
		Selection defaultSelection = new DefaultSelection();
    	List<Solution> solutions = new ArrayList<Solution>();
    	for(int i=0;i<5;i++) {
    		Solution mockSolution = Mockito.mock(Solution.class);
    		Fitness mockFitness = Mockito.mock(Fitness.class);
    		Mockito.when(mockFitness.compareTo(Mockito.any(Fitness.class))).thenReturn(new Random().nextInt(10)%2);
    		Mockito.when(mockSolution.getFitness()).thenReturn(mockFitness);
    		
        	solutions.add(mockSolution);
    	}
    	Solution selected = defaultSelection.select(solutions);
    	assertTrue("Selected solution is null!", selected != null);
    	assertTrue("Selected solution is not in the initial collection!", solutions.contains(selected));
    }
	
	@Test(expected = IllegalStateException.class)
	public void defautlSelectionEmptyCollection() {
		Selection defaultSelection = new DefaultSelection();
    	defaultSelection.select(new ArrayList<Solution>());
	}
	
	@Test(expected = IllegalStateException.class)
	public void defautlSelectionNullCollection() {
		new DefaultSelection().select(null);
	}
}
