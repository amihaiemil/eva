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
public class DefaultSelectionTestCase {
    /**
     * The default selection can return a solution from a collection of solutions.
     */
	@Test
	public void defaultSelectionWorks() {
		Selection defaultSelection = new DefaultSelection();
    	List<Solution> solutions = new ArrayList<Solution>();
    	for(int i=0;i<5;i++) {
        	solutions.add(mockSolution());
    	}
    	Solution selected = defaultSelection.select(solutions);
    	assertTrue("Selected solution is null!", selected != null);
    	assertTrue("Selected solution is not in the initial collection!", solutions.contains(selected));
    }
	
	@Test
	public void defaultSelectionOneSolution() {
		Selection defaultSelection = new DefaultSelection();
    	List<Solution> solutions = new ArrayList<Solution>();
    	solutions.add(mockSolution());
    	
    	Solution selected = defaultSelection.select(solutions);
    	assertTrue("Selected solution is null!", selected != null);
    	assertTrue("Selected solution is not in the initial collection!", solutions.contains(selected));
	}
	
	/*
	 * The default selection does not accept an empty collection of solutions.
	 */
	@Test(expected = IllegalStateException.class)
	public void defaultSelectionEmptyCollection() {
		Selection defaultSelection = new DefaultSelection();
    	defaultSelection.select(new ArrayList<Solution>());
	}

	/**
	 * The default selection foes not accept a null collection of solutions.
	 */
	@Test(expected = IllegalStateException.class)
	public void defaultSelectionNullCollection() {
		new DefaultSelection().select(null);
	}
	
	/**
	 * Mock a solution.
	 * @return The mocked solution.
	 */
	private Solution mockSolution() {
		Solution mockSolution = Mockito.mock(Solution.class);
		Fitness mockFitness = Mockito.mock(Fitness.class);
		Mockito.when(mockFitness.compareTo(Mockito.any(Fitness.class))).thenReturn(new Random().nextInt(10)%2);
		Mockito.when(mockSolution.getFitness()).thenReturn(mockFitness);
		return mockSolution;
	}
}
