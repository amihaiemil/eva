package com.amihaiemil.eva.ex;

import com.amihaiemil.eva.Solution;

import java.util.Random;

/**
 * A Solution represented on an array of 0 and 1 (NumericalRepresentation is used).
 * @author Mihai Andronache (amihaiemil@gmail.com).
 */
public class BinaryArraySolution extends Solution {
    private Random chance = new Random();

    public BinaryArraySolution(Random chance) {
        this.chance = chance;
    }

    @Override
    public void mutate(double pm) {
        NumericalRepresentation representation = (NumericalRepresentation) this.getRepresentation();
        for(int i=0;i<representation.getSize();i++) {
            if(chance.nextDouble() < pm) {
                representation.replaceAt(i, 1-representation.get(i));
            }
        }
    }

    @Override
    public Solution crossover(Solution partner) {
        NumericalRepresentation representation = (NumericalRepresentation) this.getRepresentation();
        NumericalRepresentation partnerRepresentation = (NumericalRepresentation) partner.getRepresentation();

        Solution offspring = new BinaryArraySolution(new Random());
        NumericalRepresentation offSpringRepresentation = new NumericalRepresentation();
        int point = chance.nextInt(representation.getSize());
        for(int i=0;i<point;i++) {
            offSpringRepresentation.addNumber(representation.get(i));
        }
        for(int i=point;i<representation.getSize();i++) {
            offSpringRepresentation.addNumber(partnerRepresentation.get(i));
        }
        offspring.setRepresentation(offSpringRepresentation);
        return offspring;
    }
}
