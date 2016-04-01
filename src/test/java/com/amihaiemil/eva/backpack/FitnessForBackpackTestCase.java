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
import com.amihaiemil.eva.backpack.FitnessForBackpack;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link FitnessForBackpack}
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class FitnessForBackpackTestCase {
    /**
     * The Fitness can tell if it's ok or not.
     */
    @Test
    public void isFitnessOkOrNot() {
        Fitness fitness = new FitnessForBackpack(56, 100);
        assertTrue(fitness.isOk());
        fitness = new FitnessForBackpack(150, 100);
        assertFalse(fitness.isOk());
    }

    /**
     * A Fitness can compare itself to another Fitness.
     */
    @Test
    public void comparesItself() {
        Fitness fitness1 = new FitnessForBackpack(56, 100);
        Fitness fitness2 = new FitnessForBackpack(90, 100);
        Fitness fitness3 = new FitnessForBackpack(101, 100);
        Fitness fitness4 = new FitnessForBackpack(56, 100);

        assertTrue(fitness1.compareTo(fitness2) == 1);
        assertTrue(fitness2.compareTo(fitness1) == -1);
        assertTrue(fitness2.compareTo(fitness3) == 1);
        assertTrue(fitness1.compareTo(fitness4) == 0);
    }
}
