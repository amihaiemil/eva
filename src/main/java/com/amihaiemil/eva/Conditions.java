package com.amihaiemil.eva;

import java.util.ArrayList;
import java.util.List;

/**
 * Specify multiple stopping conditions for the algorithm.
 * @author Mihai Andronache (amihaiemil@gmail.com).
 */
public final class Conditions implements Condition {

    private boolean resolution;
    private Condition initialCondition;
    private List<AddedCondition> addedConditions = new ArrayList<AddedCondition>();
    public Conditions (Condition initialCondition) {
        this.initialCondition = initialCondition;
    }

    /**
     * Add a condition to the list with an 'and' clause.
     * @param condition Added condition.
     * @return this instance of conditions.
     */
    public Conditions and(Condition condition) {
        this.addedConditions.add(new AddedCondition("&&", condition));
        return this;
    }

    /**
     * Add a condition to the list with an 'or' clause.
     * @param condition Added condition.
     * @return this instance of conditions.
     */
    public Conditions or(Condition condition) {
        this.addedConditions.add(new AddedCondition("||", condition));
        return this;
    }

    public boolean passed(Solution s) {
        this.resolution = initialCondition.passed(s);
        for(int i=0;i<addedConditions.size();i++) {
            AddedCondition condition = addedConditions.get(i);
            if("||".equals(condition.getOperator())) {
                this.resolution = this.resolution || condition.getCondition().passed(s);
            } else {
                this.resolution = this.resolution && condition.getCondition().passed(s);
            }
        }
        return this.resolution;
    }

    private static final class AddedCondition {
        private String operator;
        private Condition condition;
        public AddedCondition(String op, Condition condition) {
            this.operator = op;
            this.condition = condition;
        }
        public Condition getCondition() {
            return this.condition;
        }

        public String getOperator() {
            return this.operator;
        }
    }
}
