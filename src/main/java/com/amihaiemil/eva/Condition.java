package com.amihaiemil.eva;

/**
 * Condition that has to be met in order for the algorithm to stop.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public interface Condition {

    /**
     * Does the solution meet this condition or not?
     * @return true if it is met or false otherwise.
     */
    boolean passed(Solution solution);
}
