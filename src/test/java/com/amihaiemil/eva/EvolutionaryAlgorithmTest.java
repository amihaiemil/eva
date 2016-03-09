package com.amihaiemil.eva;

import com.amihaiemil.eva.ex.BinaryArraySolutionsGenerator;
import com.amihaiemil.eva.ex.FitnessForBackpack;
import com.amihaiemil.eva.ex.FitnessForBackpackEvaluator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

/**
 * Test cases for {@link Eva} implementations.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class EvolutionaryAlgorithmTest {
    private Random r = new Random();

    /**
     * {@link SimpleEvolutionaryAlgorithm} can find an acceptable solution.
     * This test might take a while to finish, since there is no time limit specified.
     * @throws Exception If something goes wrong.
     */
    @Test
    public void simpleEvaFindsASolution() throws Exception {
        List<Integer> weights = new ArrayList<Integer>();
        for(int i=0;i<10;i++) {
            weights.add(r.nextInt(100));
        }
        BinaryArraySolutionsGenerator generator = new BinaryArraySolutionsGenerator(weights.size());
        FitnessForBackpackEvaluator evaluator = new FitnessForBackpackEvaluator(weights);

         // The algorithm should stop only when the found solution is acceptable.
        Condition solutionIsAcceptable = new Condition() {
            public boolean passed(Solution solution) {
                return solution.isAcceptable();
            }
        };

        Eva algorithm = new SimpleEvolutionaryAlgorithm();
        Solution solution = algorithm.with(generator).with(evaluator).with(solutionIsAcceptable).calculate();
        int solutionWeight = ((FitnessForBackpack) solution.getFitness()).getWeight();
        assertTrue(
                "Expected solution with weight <= 100, got weight: " + solutionWeight,
                solutionWeight <= 100
        );
    }
}
