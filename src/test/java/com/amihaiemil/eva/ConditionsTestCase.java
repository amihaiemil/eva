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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class ConditionsTestCase {
    @Test
    public void multipleConditionsEvaluated() {
        Solution solution = Mockito.mock(Solution.class);
        Condition initial = Mockito.mock(Condition.class);
        Mockito.when(initial.passed(solution)).thenReturn(true);

        Condition condition1 = Mockito.mock(Condition.class);
        Mockito.when(condition1.passed(solution)).thenReturn(true);

        Condition condition2 = Mockito.mock(Condition.class);
        Mockito.when(condition2.passed(solution)).thenReturn(false);

        Condition condition3 = Mockito.mock(Condition.class);
        Mockito.when(condition3.passed(solution)).thenReturn(true);

        Conditions conditions = new Conditions(initial);
        boolean resolution = conditions.and(condition1).or(condition2).and(condition3).passed(solution);
        assertTrue(resolution);
        resolution = conditions.and(condition2).passed(solution);
        assertFalse(resolution);
    }
}
