package com.amihaiemil.eva.ex;

import com.amihaiemil.eva.Representation;

import java.util.ArrayList;
import java.util.List;

/**
 * Numerical representation of a Solution.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class NumericalRepresentation implements Representation {
    private List<Integer> numbers;

    public NumericalRepresentation() {
        numbers = new ArrayList<Integer>();
    }
    public NumericalRepresentation(List<Integer> rep) {
        this.numbers = rep;
    }

    public void addNumber(int number) {
        this.numbers.add(number);
    }

    public int get(int index) {
        return this.numbers.get(index);
    }

    public void replaceAt(int index, int number) {
        numbers.set(index, number);
    }

    public int getSize() {
        return this.numbers.size();
    }
}
