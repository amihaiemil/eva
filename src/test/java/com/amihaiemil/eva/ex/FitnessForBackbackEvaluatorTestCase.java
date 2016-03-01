package com.amihaiemil.eva.ex;

import com.amihaiemil.eva.Fitness;
import com.amihaiemil.eva.FitnessEvaluator;
import com.amihaiemil.eva.Solution;
import org.junit.Test;

import java.util.Arrays;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class FitnessForBackbackEvaluatorTestCase {
    /**
     * {@link FitnessForBackpackEvaluator} can evaluate the Fitness of a Solution.
     */
    @Test
    public void evaluatesFitnessCorrectly() {
        FitnessEvaluator evaluator = new FitnessForBackpackEvaluator(
                                        Arrays.asList(1,2,100)
                                     );
        NumericalRepresentation rep1 = new NumericalRepresentation();
        rep1.addNumber(1);
        rep1.addNumber(0);
        rep1.addNumber(1);
        Solution solution = new IntegersArraySolution();
        solution.setRepresentation(rep1);
        Fitness fitness1 = evaluator.calculateFitnessForSolution(solution);
        assertFalse(fitness1.isOk());

        NumericalRepresentation rep2 = new NumericalRepresentation();
        rep2.addNumber(1);
        rep2.addNumber(1);
        rep2.addNumber(0);
        solution.setRepresentation(rep2);
        Fitness fitness2 = evaluator.calculateFitnessForSolution(solution);
        assertTrue(fitness2.isOk());
    }
}
