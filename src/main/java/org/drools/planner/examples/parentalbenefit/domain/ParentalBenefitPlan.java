/*
 * Copyright (c) 2011 Mattias Nilsson Grip <mattias at mhome.se>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * GNU General Public License is available at <http ://www.gnu.org/licenses/>.
 */

package org.drools.planner.examples.parentalbenefit.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.drools.planner.api.domain.solution.PlanningEntityCollectionProperty;
import org.drools.planner.core.score.HardAndSoftScore;
import org.drools.planner.core.solution.Solution;
import org.drools.planner.examples.common.domain.AbstractPersistable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ParentalBenefitPlan")
@SuppressWarnings("serial")
public class ParentalBenefitPlan extends AbstractPersistable implements Solution<HardAndSoftScore> {

    private String code;

    private Settings settings;
    private List<Parent> parentList;
    private List<Month> monthList;

    private List<MonthPlan> monthPlanList;

    private HardAndSoftScore score;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Settings getSettings() {
		return settings;
	}
    
    public void setSettings(Settings settings) {
		this.settings = settings;
	}
    
    public List<Parent> getParentList() {
        return parentList;
    }

    public void setParentList(List<Parent> parentList) {
        this.parentList = parentList;
    }

    public List<Month> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<Month> monthList) {
        this.monthList = monthList;
    }

    @PlanningEntityCollectionProperty
    public List<MonthPlan> getMonthPlanList() {
        return monthPlanList;
    }

    public void setMonthPlanList(List<MonthPlan> monthPlanList) {
        this.monthPlanList = monthPlanList;
    }
    
    public HardAndSoftScore getScore() {
        return score;
    }

    public void setScore(HardAndSoftScore score) {
        this.score = score;
    }

    public boolean isInitialized() {
        return (monthPlanList != null);
    }

    public Collection<? extends Object> getProblemFacts() {
        List<Object> facts = new ArrayList<Object>();
        facts.add(settings);
        facts.addAll(parentList);
        facts.addAll(monthList);
        // Do not add the planning entity's (monthPlanList) because that will be done automatically
        return facts;
    }
    
    public MonthPlan getPlanForOtherParent(MonthPlan plan) {
    	for (MonthPlan otherPlan : monthPlanList) {
    		if (plan.getMonth().equals(otherPlan.getMonth()) &&
    			!plan.getParent().equals(otherPlan.getParent())) {
    			return otherPlan;
    		}
    	}
    	throw new RuntimeException("Found no month plan for other parent!");
    }

    /**
     * Clone will only deep copy the {@link #monthPlanList}.
     */
    public ParentalBenefitPlan cloneSolution() {
        ParentalBenefitPlan clone = new ParentalBenefitPlan();
        clone.id = id;
        clone.code = code;
        clone.parentList = parentList;
        clone.monthList = monthList;
        List<MonthPlan> clonedMonthPlanList = new ArrayList<MonthPlan>(
                monthPlanList.size());
        for (MonthPlan monthPlan : monthPlanList) {
            MonthPlan clonedMonthPlan = monthPlan.clone();
            clonedMonthPlanList.add(clonedMonthPlan);
        }
        clone.monthPlanList = clonedMonthPlanList;
        clone.score = score;
        return clone;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (id == null || !(o instanceof ParentalBenefitPlan)) {
            return false;
        } else {
            ParentalBenefitPlan other = (ParentalBenefitPlan) o;
            if (monthPlanList.size() != other.monthPlanList.size()) {
                return false;
            }
            for (Iterator<MonthPlan> it = monthPlanList.iterator(), otherIt = other.monthPlanList.iterator(); it.hasNext();) {
                MonthPlan monthPlan = it.next();
                MonthPlan otherMonthPlan = otherIt.next();
                // Notice: we don't use equals()
                if (!monthPlan.solutionEquals(otherMonthPlan)) {
                    return false;
                }
            }
            return true;
        }
    }

    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        for (MonthPlan assignment : monthPlanList) {
            // Notice: we don't use hashCode()
            hashCodeBuilder.append(assignment.solutionHashCode());
        }
        return hashCodeBuilder.toHashCode();
    }

}
