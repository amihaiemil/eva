package com.amihaiemil.eva.ex;

import com.amihaiemil.eva.Fitness;
import com.amihaiemil.eva.FitnessEvaluator;
import com.amihaiemil.eva.Solution;

import java.util.List;

/**
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class FitnessForBackpackEvaluator implements FitnessEvaluator {

    private List<Integer> objectsWeights;
    public FitnessForBackpackEvaluator(List<Integer> weights) {
        this.objectsWeights = weights;
    }

    public Fitness calculateFitnessForSolution(Solution solution) {
        NumericalRepresentation representation = (NumericalRepresentation) solution.getRepresentation();
        int solutionWeight = 0;
        for (int i=0;i<representation.getSize();i++) {
            if(representation.get(i) == 1) {
                solutionWeight += objectsWeights.get(i);
            }
        }
        return new FitnessForBackpack(solutionWeight);
    }
}
