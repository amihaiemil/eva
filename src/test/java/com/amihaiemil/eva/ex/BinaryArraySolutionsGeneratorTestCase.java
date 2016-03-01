package com.amihaiemil.eva.ex;

import com.amihaiemil.eva.Solution;
import com.amihaiemil.eva.SolutionsGenerator;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class BinaryArraySolutionsGeneratorTestCase {
    /**
     * A solution cen be created using a {@link BinaryArraySolutionsGenerator}.
     */
    @Test
    public void generatesSolution() {
        SolutionsGenerator generator = new BinaryArraySolutionsGenerator(5);
        Solution generatedSolution = generator.generateRandomSolution();
        assertTrue("Generated solution is null!", generatedSolution != null);
        assertTrue("Wrong solution type!", generatedSolution instanceof IntegersArraySolution);
        assertTrue("Wrong representation type!", generatedSolution.getRepresentation() instanceof NumericalRepresentation);
        NumericalRepresentation representation = (NumericalRepresentation) generatedSolution.getRepresentation();
        for(int i=0;i<representation.getSize();i++){
            assertTrue("Expected a binary representation!", representation.get(i) == 0 || representation.get(i) == 1);
        }
    }
}
