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
import com.amihaiemil.eva.backpack.FitnessForBackpackEvaluator;
import com.amihaiemil.eva.util.BinaryArraySolution;
import com.amihaiemil.eva.util.NumericalRepresentation;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class FitnessForBackbackEvaluatorTestCase {
    /**
     * {@link FitnessForBackpackEvaluator} can evaluate the Fitness of a Solution.
     */
    @Test
    public void evaluatesFitnessCorrectly() {
        FitnessEvaluator evaluator = new FitnessForBackpackEvaluator(new long[]{1,2,100});
        NumericalRepresentation rep1 = new NumericalRepresentation();
        rep1 = rep1.addNumber(1);
        rep1 = rep1.addNumber(0);
        rep1 = rep1.addNumber(1);
        Solution solution = new BinaryArraySolution(new Random());
        solution.setRepresentation(rep1);
        Fitness fitness1 = evaluator.calculateFitnessForSolution(solution);
        assertFalse(fitness1.isOk());

        NumericalRepresentation rep2 = new NumericalRepresentation();
        rep2 = rep2.addNumber(1);
        rep2 = rep2.addNumber(1);
        rep2 = rep2.addNumber(0);
        solution.setRepresentation(rep2);
        Fitness fitness2 = evaluator.calculateFitnessForSolution(solution);
        assertTrue(fitness2.isOk());
    }
}
