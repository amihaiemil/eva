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
import java.util.Random;

/**
 * Default-used selection.
 * @author Mihai Andronache (mihai.andronache@urss.ro)
 *
 */
final class DefaultSelection implements Selection {
	private Random chance = new Random();
	/**
	 * The best out of 2 randomly chosen solutions is returned.
	 */
	public Solution select(Collection<Solution> solutions) {
		if(solutions == null || solutions.size() == 0) {
			throw new IllegalStateException ("Empty or null collection of solutions!");
		}
		int size = solutions.size();
		Solution[] solArray = new Solution[size];
		solutions.toArray(solArray);
		Solution candidate1 = solArray[chance.nextInt(size)];
        Solution candidate2 = solArray[chance.nextInt(size)];
        if(candidate1.getFitness().compareTo(candidate2.getFitness()) == 1) {
            return candidate1;
        } else {
            return candidate2;
        }
	}

}
