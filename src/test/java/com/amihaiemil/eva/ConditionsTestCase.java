package com.amihaiemil.eva;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class ConditionsTestCase {
    @Test
    public void multipleConditionsEvaluated() {
        Solution solution = Mockito.mock(Solution.class);
        Condition initial = Mockito.mock(Condition.class);
        Mockito.when(initial.passed(solution)).thenReturn(true);

        Condition condition1 = Mockito.mock(Condition.class);
        Mockito.when(condition1.passed(solution)).thenReturn(true);

        Condition condition2 = Mockito.mock(Condition.class);
        Mockito.when(condition2.passed(solution)).thenReturn(false);

        Condition condition3 = Mockito.mock(Condition.class);
        Mockito.when(condition3.passed(solution)).thenReturn(true);

        Conditions conditions = new Conditions(initial);
        boolean resolution = conditions.and(condition1).or(condition2).and(condition3).passed(solution);
        assertTrue(resolution);
        resolution = conditions.and(condition2).passed(solution);
        assertFalse(resolution);
    }
}
