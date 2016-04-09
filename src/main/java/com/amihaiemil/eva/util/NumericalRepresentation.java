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
package com.amihaiemil.eva.util;

import com.amihaiemil.eva.Representation;

import java.util.ArrayList;
import java.util.List;

/**
 * Numerical representation of a Solution.
 * This class is immutable.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class NumericalRepresentation implements Representation {
    private List<Integer> numbers;

    public NumericalRepresentation() {
        this(new ArrayList<Integer>());
    }
    public NumericalRepresentation(List<Integer> rep) {
        this.numbers = new ArrayList<Integer>();
        for(int i=0;i<rep.size();i++) {
            this.numbers.add(rep.get(i));
        }
    }

    /**
     * Returns a new NumericalRepresentation with the added number.
     * @param number The number to be added to the numerical representation.
     * @return NumericalRepresentation instance.
     */
    public NumericalRepresentation addNumber(int number) {
        List<Integer> withNumber = new ArrayList<Integer>(this.numbers);
        withNumber.add(number);
        return new NumericalRepresentation(withNumber);
    }

    public int get(int index) {
        return this.numbers.get(index);
    }

    /**
     * Returns a new NumericalRepresentation with the replaced index.
     * @param index The index of the value to be replaced by number.
     * @param number The number to replace the value found at index.
     * @return NumericalRepresentation instance.
     */
    public NumericalRepresentation replaceAt(int index, int number) {
        List<Integer> withNumberReplaced = new ArrayList<Integer>(this.numbers);
        withNumberReplaced.set(index, number);
        return new NumericalRepresentation(withNumberReplaced);
    }

    public int getSize() {
        return this.numbers.size();
    }
}
