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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Specify multiple stopping conditions for the algorithm.
 * @author Mihai Andronache (amihaiemil@gmail.com).
 */
public final class Conditions implements Condition {

    private static final Logger logger = LoggerFactory.getLogger(Conditions.class);
    private boolean resolution;
    private Condition initialCondition;
    private List<AddedCondition> addedConditions = new ArrayList<AddedCondition>();
    public Conditions (Condition initialCondition) {
        logger.debug("Adding multiple stopping conditions. Initial condition: " + initialCondition.toString());
        this.initialCondition = initialCondition;
    }

    /**
     * Constructor to provide immutability.
     * @param initialCondition The initial condition.
     * @param conditions The conditions we have so far.
     * @param nextCondition The added condition.
     */
    private Conditions(Condition initialCondition, List<AddedCondition> conditions, AddedCondition nextCondition) {
       this.initialCondition = initialCondition;
        this.addedConditions = new ArrayList<AddedCondition>();
        for(AddedCondition c : conditions) {
            this.addedConditions.add(c);
        }
        this.addedConditions.add(nextCondition);
    }
    /**
     * Add a condition to the list with an 'and' operator.
     * @param condition Added condition.
     * @return A new, copied, instance of this, with the specified condition added.
     */
    public Conditions and(Condition condition) {
        logger.info("Adding stopping condition with AND operator: AND " + condition.toString());
        return new Conditions(this.initialCondition, this.addedConditions, new AddedCondition("&&", condition));
    }

    /**
     * Add a condition to the list with an 'or' operator.
     * @param condition Added condition.
     * @return A new, copied, instance of this, with the specified condition added.
     */
    public Conditions or(Condition condition) {
        logger.info("Adding stopping condition with OR operator: OR " + condition.toString());
        return new Conditions(this.initialCondition, this.addedConditions, new AddedCondition("||", condition));
    }

    public boolean passed(Solution s) {
        logger.info("Resolving multiple stopping conditions...");
        this.resolution = initialCondition.passed(s);
        for(int i=0;i<addedConditions.size();i++) {
            AddedCondition condition = addedConditions.get(i);
            if("||".equals(condition.getOperator())) {
                this.resolution = this.resolution || condition.getCondition().passed(s);
            } else {
                this.resolution = this.resolution && condition.getCondition().passed(s);
            }
        }
        logger.info("Stopping conditions resolved. Resolution: " + this.resolution);
        return this.resolution;
    }

    /**
     * Wrapper for an added condition, to also specify the operator.
     */
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
