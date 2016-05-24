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

import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link RetryEvolutionaryAlgorithm}
 * @author Mihai Andronache (amihaiemil@gmail.com)
 *
 */
public class RetryEvolutionaryAlgorithmTestCase {
	/**
	 * {@link RetryEvolutionaryAlgorithm} finds a good solution after one rerun.
	 */
	@Test
    public void rerunsAlgorithmOneTime() {
    	Solution found = Mockito.mock(Solution.class);
    	Condition conditions = Mockito.mock(Condition.class);
    	Mockito.when(conditions.passed(found)).thenReturn(false).thenReturn(true);
    	
    	Eva algorithm = Mockito.mock(Eva.class);
    	Mockito.when(algorithm.calculate()).thenReturn(found);
    	Mockito.when(algorithm.conditions()).thenReturn(conditions);
    	
    	Eva retryEva = new RetryEvolutionaryAlgorithm(algorithm);
    	
    	assertTrue("Found solution should meet the conditions!", conditions.passed(retryEva.calculate()));
    }
	
	/**
	 * {@link RetryEvolutionaryAlgorithm} cannot find a good solution at all (all reruns are performed but the solution still
	 * does not meet the conditions).
	 */
	@Test
	public void rerunsAlgorithmWithNoSuccess() {
		Solution found = Mockito.mock(Solution.class);
    	Condition conditions = Mockito.mock(Condition.class);
    	Mockito.when(conditions.passed(found)).thenReturn(false);
    	
    	Eva algorithm = Mockito.mock(Eva.class);
    	Mockito.when(algorithm.calculate()).thenReturn(found);
    	Mockito.when(algorithm.conditions()).thenReturn(conditions);
    	
    	Eva retryEva = new RetryEvolutionaryAlgorithm(algorithm);
    	
    	assertFalse("Found solution should NOT meet the conditions.", conditions.passed(retryEva.calculate()));

	}
}
