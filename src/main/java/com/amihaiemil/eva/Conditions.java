/*
 Copyright (c) 2016, Mihai-Emil Andronache
 All rights reserved.

 Redistribution and use in source and binary forms, with or without modification,
 are permitted provided that the following conditions are met:
 1. Redistributions of source code must retain the above copyright notice,
 this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.
 3. Neither the name of the copyright holder nor the names of its contributors
 may be used to endorse or promote products derived from this software
 without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 ARE DISCLAIMED.
 IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
