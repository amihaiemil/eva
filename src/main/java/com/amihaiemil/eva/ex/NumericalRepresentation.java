package com.amihaiemil.eva.ex;

import com.amihaiemil.eva.Representation;

import java.util.ArrayList;
import java.util.List;

/**
 * Numerical representation of a Solution.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class NumericalRepresentation implements Representation {
    private List<Integer> numbers = new ArrayList<Integer>();

    public void addNumber(int number) {
        this.numbers.add(number);
    }

    public void removeAt(int index) {
        if(this.numbers.size() > index) {
            this.numbers.remove(index);
        }
    }

    public int get(int index) {
        return this.numbers.get(index);
    }

    public void addAt(int number, int index) {
        if(this.numbers.size() > index) {
            this.numbers.add(index, number);
        }
    }

    public void replaceAt(int index, int number) {
        numbers.set(index, number);
    }

    public int getSize() {
        return this.numbers.size();
    }
}
