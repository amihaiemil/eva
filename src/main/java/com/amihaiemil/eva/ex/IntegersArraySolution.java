package com.amihaiemil.eva.ex;

import com.amihaiemil.eva.Solution;

import java.util.Random;

/**
 * A Solution represented on an array of integers (NumericalRepresentation is used).
 * @author Mihai Andronache (amihaiemil@gmail.com).
 */
public class IntegersArraySolution extends Solution {
    private Random r = new Random();

    @Override
    public void mutate(double pm) {
        NumericalRepresentation representation = (NumericalRepresentation) this.representation;

        for(int i=0;i<representation.getSize();i++) {
            if(r.nextDouble() < pm) {
                representation.replaceAt(i, 1-representation.get(i));
            }
        }

        this.representation = representation;
    }

    @Override
    public Solution crossover(Solution partner) {
        NumericalRepresentation representation = (NumericalRepresentation) this.representation;
        NumericalRepresentation partnerRepresentation = (NumericalRepresentation) partner.getRepresentation();

        Solution offspring = new IntegersArraySolution();
        NumericalRepresentation offSpringRepresentation = new NumericalRepresentation();
        int point = r.nextInt(representation.getSize());
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
