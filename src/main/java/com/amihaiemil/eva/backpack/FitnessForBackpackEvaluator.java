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
package com.amihaiemil.eva.backpack;

import com.amihaiemil.eva.Fitness;
import com.amihaiemil.eva.FitnessEvaluator;
import com.amihaiemil.eva.Solution;
import com.amihaiemil.eva.util.NumericalRepresentation;

/**
 * Each solution for the 'Backpack problem' has to have its fitness evaluated.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public final class FitnessForBackpackEvaluator implements FitnessEvaluator {

	/**
	 * The backpack's capacity defaults to 100.
	 */
    private long capacity = 100;
    private long[] objectsWeights;
    /**
     * Constructor.
     * @param weights The weights of all the objects that could go in the backpack.
     * Default backpack capacity is 100 units.
     */
    public FitnessForBackpackEvaluator(long[] weights) {
        if(weights == null || weights.length ==0 ) {
            throw new IllegalStateException("No weight specified!");
        }
        this.objectsWeights = weights;
    }

    /**
     * Constructor.
     * @param weights The weights of all the objects that could go in the backpack.
     * @param capacity The capacity of the backpack.
     */
    public FitnessForBackpackEvaluator(long[] weights, long capacity) {
        this(weights);
        this.capacity = capacity;
    }

    public Fitness calculateFitnessForSolution(Solution solution) {
        NumericalRepresentation representation = (NumericalRepresentation) solution.getRepresentation();
        long solutionWeight = 0;
        for (int i=0;i<representation.getSize();i++) {
            if(representation.get(i) == 1) {
                solutionWeight += this.objectsWeights[i];
            }
        }
        return new FitnessForBackpack(solutionWeight, this.capacity);
    }
}
