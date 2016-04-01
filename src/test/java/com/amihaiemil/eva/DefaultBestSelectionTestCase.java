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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test cases for the selection of the best individual.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 *
 */
public class DefaultBestSelectionTestCase {
	/**
	 * The default best selection does not accept an empty collection of solutions.
	 */
	@Test(expected = IllegalStateException.class)
	public void defaultBestSelectionEmptyCollection() {
		BestSelection defaultSelection = new DefaultBestSelection();
    	defaultSelection.selectBest(new ArrayList<Solution>());
	}

	/**
	 * The default best  selection does not accept a null collection of solutions.
	 */
	@Test(expected = IllegalStateException.class)
	public void defaultBestSelectionNullCollection() {
		new DefaultBestSelection().selectBest(null);
	}
	
	/**
     * The default best selection can return the best solution from a collection of solutions.
     */
	@Test
	public void defaultBestSelectionWorks() {
		BestSelection defaultSelection = new DefaultBestSelection();
    	List<Solution> solutions = new ArrayList<Solution>();
        solutions.add(mockSolution(false));
        
        Solution best = mockSolution(true);
        Mockito.when(best.getFitness().compareTo(Mockito.any(Fitness.class))).thenReturn(1);
        
        solutions.add(best);
        solutions.add(mockSolution(false));

    	Solution selected = defaultSelection.selectBest(solutions);
    	assertTrue("Selected solution is null!", selected != null);
    	assertTrue("Selected solution is not in the initial collection!", solutions.contains(selected));
    	assertTrue("Selected solution is not the best!", solutions.get(1) == selected);
    }
	
	/**
	 * The default best selection works when there is only one solution in the collection
	 */
	@Test
	public void defaultBestSelectionOneSolution() {
		BestSelection defaultSelection = new DefaultBestSelection();
    	List<Solution> solutions = new ArrayList<Solution>();
    	solutions.add(mockSolution(false));
    	
    	Solution selected = defaultSelection.selectBest(solutions);
    	assertTrue("Selected solution is null!", selected != null);
    	assertTrue("Selected solution is not in the initial collection!", solutions.contains(selected));
	}
	
	/**
	 * Mock a solution.
	 * @param isFitnessOk Is the solution's fitness ok or not?
	 * @return The mocked solution.
	 */
	private Solution mockSolution(boolean isFitnessOk) {
		Solution mockSolution = Mockito.mock(Solution.class);
		Fitness mockFitness = Mockito.mock(Fitness.class);
		Mockito.when(mockFitness.isOk()).thenReturn(isFitnessOk);
		Mockito.when(mockSolution.getFitness()).thenReturn(mockFitness);
		return mockSolution;
	}
}
