package com.amihaiemil.eva.ex;

import com.amihaiemil.eva.Fitness;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link FitnessForBackpack}
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class FitnessForBackpackTestCase {
    /**
     * The Fitness can tell if it's ok or not.
     */
    @Test
    public void isFitnessOkOrNot() {
        Fitness fitness = new FitnessForBackpack(56);
        assertTrue(fitness.isOk());
        fitness = new FitnessForBackpack(150);
        assertFalse(fitness.isOk());
    }

    /**
     * A Fitness can compare itself to another Fitness.
     */
    @Test
    public void comparesItself() {
        Fitness fitness1 = new FitnessForBackpack(56);
        Fitness fitness2 = new FitnessForBackpack(90);
        Fitness fitness3 = new FitnessForBackpack(101);
        Fitness fitness4 = new FitnessForBackpack(56);

        assertTrue(fitness1.compareTo(fitness2) == 1);
        assertTrue(fitness2.compareTo(fitness1) == -1);
        assertTrue(fitness2.compareTo(fitness3) == 1);
        assertTrue(fitness1.compareTo(fitness4) == 0);
    }
}
