package com.amihaiemil.eva;

/**
 * Interface of an evolutionary algorithm implementation.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public interface Eva {
    /**
     * Run the algorithm, calculate the solution.
     * @return The found solution.
     */
    Solution calculate();

    /**
     * It is <b>mandatory</b> to specify a fitness evaluator.
     * @param evaluator The evaluator that this algorithm works with.
     * @return This algorithm instance (for fluent usage of with(...) methods).
     */
    Eva with(FitnessEvaluator evaluator);

    /**
     * It is <b>mandatory</b> to specify a solution generator.
     * @param generator The generator that this algorithm works with.
     * @return This algorithm instance (for fluent usage of with(...) methods).
     */
    Eva with(SolutionsGenerator generator);

    /**
     * Optionally, you can specify additional stopping conditions that the found solution has
     * to meet before the algorithm stops.
     * @param additionalStoppingConditions The added condition(s).
     * @return This algorithm instance (for fluend usage of with(...) methods).
     */
    Eva with(Condition additionalStoppingConditions);
}
