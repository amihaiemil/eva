package com.amihaiemil.eva;

/**
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public interface Fitness extends Comparable<Fitness>{
    /**
     * Indicates whether this fitness is OK.
     * If it is not OK, then it's not even worth comparing it to others, since
     * we know that a solution with this fitness is not good.
     * @return true if this fitness is considered OK, or false otherwise.
     */
    boolean isOk();
}
