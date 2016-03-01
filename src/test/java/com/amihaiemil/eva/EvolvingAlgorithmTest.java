package com.amihaiemil.eva;

import com.amihaiemil.eva.ex.BinaryArraySolutionsGenerator;
import com.amihaiemil.eva.ex.FitnessForBackpack;
import com.amihaiemil.eva.ex.FitnessForBackpackEvaluator;
import com.amihaiemil.eva.ex.NumericalRepresentation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

/**
 * Test cases for {@link Eva} implementations.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class EvolvingAlgorithmTest {
    private Random r = new Random();

    /**
     * {@link SimpleEvolvingAlgorithm} can find an acceptable solution.
     * @throws Exception If something goes wrong.
     */
    @Test
    public void simpleEvaFindsASolution() throws Exception {
        List<Integer> weights = new ArrayList<Integer>();
        for(int i=0;i<20;i++) {
            weights.add(r.nextInt(100));
        }
        BinaryArraySolutionsGenerator generator = new BinaryArraySolutionsGenerator(weights.size());
        FitnessForBackpackEvaluator evaluator = new FitnessForBackpackEvaluator(weights);
        Eva algorithm = new SimpleEvolvingAlgorithm();
        Solution solution = algorithm.with(generator).with(evaluator).calculate();
        int solutionWeight = ((FitnessForBackpack) solution.getFitness()).getWeight();
        assertTrue(
                "Expected solution with weight <= 100, got weight: " + solutionWeight,
                solutionWeight <= 100
        );
    }
}
