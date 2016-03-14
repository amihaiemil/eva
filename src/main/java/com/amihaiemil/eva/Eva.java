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

/**
 * Interface of an evolutionary algorithm implementation.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public interface Eva {
    /**
     * Run the algorithm, calculate the solution.
     * @return The found solution.
     */
    Solution calculate();

    /**
     * It is <b>mandatory</b> to specify a fitness evaluator.
     * @param evaluator The evaluator that this algorithm works with.
     * @return This algorithm instance (for fluent usage of with(...) methods).
     */
    Eva with(FitnessEvaluator evaluator);

    /**
     * It is <b>mandatory</b> to specify a solution generator.
     * @param generator The generator that this algorithm works with.
     * @return This algorithm instance (for fluent usage of with(...) methods).
     */
    Eva with(SolutionsGenerator generator);

    /**
     * Optionally, you can specify additional stopping conditions that the found solution has
     * to meet before the algorithm stops.
     * @param additionalStoppingConditions The added condition(s).
     * @return This algorithm instance (for fluent usage of with(...) methods).
     */
    Eva with(Condition additionalStoppingConditions);
}
