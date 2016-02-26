package com.amihaiemil.eva;

import com.amihaiemil.eva.ex.BinaryArraySolutionsGenerator;
import com.amihaiemil.eva.ex.FitnessForBackpack;
import com.amihaiemil.eva.ex.FitnessForBackpackEvaluator;
import com.amihaiemil.eva.ex.NumericalRepresentation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class EvolvingAlgorithmTest {
    private Random r = new Random();

    @Test
    public void firstTest() throws Exception {
        List<Integer> weights = new ArrayList<Integer>();
        for(int i=0;i<20;i++) {
            weights.add(r.nextInt(100));
        }
        BinaryArraySolutionsGenerator generator = new BinaryArraySolutionsGenerator(weights.size());
        FitnessForBackpackEvaluator evaluator = new FitnessForBackpackEvaluator(weights);
        EvolvingAlgorithm eva = new EvolvingAlgorithm();
        Solution solution = eva.with(generator).with(evaluator).run();
        NumericalRepresentation rep = (NumericalRepresentation) solution.getRepresentation();
        for(int i=0;i<rep.getSize();i++) {
            System.out.print(weights.get(i) + " ");
        }
        System.out.println(" ");
        System.out.println("SUM: " + ((FitnessForBackpack) solution.getFitness()).getWeight());

        for(int i=0;i<rep.getSize();i++) {
            System.out.print(rep.get(i) + " ");
        }

    }
}
