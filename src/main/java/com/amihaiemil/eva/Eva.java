package com.amihaiemil.eva;

/**
 * Interface of an evolutionary algorithm implementation.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public interface Eva {
    Solution calculate() throws IllegalStateException;
    Eva with(FitnessEvaluator evaluator);
    Eva with(SolutionsGenerator generator);
    Eva with(Condition additionalStoppingConditions);
}
