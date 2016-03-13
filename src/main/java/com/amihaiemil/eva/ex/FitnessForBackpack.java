package com.amihaiemil.eva.ex;

import com.amihaiemil.eva.Fitness;

/**
 * Fitness of a solution to the 'Backpack Problem'
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class FitnessForBackpack implements Fitness{
    private int backpackCapacity;
    private int solutionWeight;
    public FitnessForBackpack(int weight) {
        this.backpackCapacity = 100;
        this.solutionWeight = weight;
    }

    public int getWeight() {
        return this.solutionWeight;
    }
    public int getSolutionWeight() {
        return this.solutionWeight;
    }

    public boolean isOk() {
        return solutionWeight <= backpackCapacity;
    }

    public int compareTo(Fitness o) {
        FitnessForBackpack other = (FitnessForBackpack) o;
        if(this.solutionWeight > other.getSolutionWeight()) {
            return -1;
        }
        if(this.solutionWeight == other.getSolutionWeight()) {
            return 0;
        }
        return 1;
    }
}
