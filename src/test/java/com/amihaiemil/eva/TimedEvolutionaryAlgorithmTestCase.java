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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link TimedEvolutionaryAlgorithm}
 * @author Mihai Andronache (amihaiemil@gmail.com)
 *
 */
public class TimedEvolutionaryAlgorithmTestCase {

	/**
	 * TimedEvolutionaryAlgorithm throws {@link StopwatchException} because the time runs out.
	 * @throws Exception
	 */
	@Test(expected = StopwatchException.class)
	public void timeRunsOut() throws Exception {
		Eva algorithm = Mockito.mock(Eva.class);
		Mockito.when(algorithm.calculate()).then(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Thread.sleep(500);
				return Mockito.mock(Solution.class);
			}
		});
		new TimedEvolutionaryAlgorithm(algorithm, 250).calculate();
	}
	
	/**
	 * TimedEvolutionaryAlgorithm can return a solution before the time runs out.
	 * @throws Exception
	 */
	@Test
	public void findsSolutionBeforeTimeout() throws Exception {
		Eva algorithm = Mockito.mock(Eva.class);
		Mockito.when(algorithm.calculate()).then(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Thread.sleep(250);
				return Mockito.mock(Solution.class);
			}
		});
		Solution sol = new TimedEvolutionaryAlgorithm(algorithm, 500).calculate();
		assertTrue("Found solution is null!", sol != null);
	}
	
}
