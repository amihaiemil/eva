package com.amihaiemil.eva.ex;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class NumericalRepresentationTestCase {
    /**
     * A number can be added to a NumericalRepresentation.
     */
    @Test
    public void addsNumber() {
        NumericalRepresentation representation = new NumericalRepresentation();
        assertTrue(representation.getSize() == 0);
        representation.addNumber(5);
        assertTrue(representation.getSize() == 1);
    }

    /**
     * A number can be replaced in a NumericalRepresentation.
     */
    @Test
    public void replacesNumberAt() {
        NumericalRepresentation representation = new NumericalRepresentation(Arrays.asList(1, 2, 3));
        assertTrue(representation.getSize() == 3);
        assertTrue(representation.get(1) == 2);
        representation.replaceAt(2, 4);
        assertTrue(representation.getSize() == 3);
        assertTrue(representation.get(2) == 4);
    }

}
