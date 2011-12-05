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

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.drools.planner.api.domain.entity.PlanningEntity;
import org.drools.planner.api.domain.variable.PlanningVariable;
import org.drools.planner.api.domain.variable.ValueRangeUndefined;
import org.drools.planner.examples.common.domain.AbstractPersistable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@PlanningEntity() //difficultyComparatorClass = ShiftAssignmentDifficultyComparator.class)
@XStreamAlias("MonthPlan")
@SuppressWarnings("serial")
public class MonthPlan extends AbstractPersistable implements Comparable<MonthPlan> {

    private Month month;
    private Parent parent;

    // number of days in different activities during this month
    private int daysAtWork;
    private int daysOnSicknessBenefit;
    private int daysOnMinimumBenefit;
    private int daysOnLeaveWithoutPay;
    
    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

	public Parent getParent() {
		return parent;
	}
	
	public void setParent(Parent parent) {
		this.parent = parent;
	}

    @PlanningVariable()
    @ValueRangeUndefined()
    public int getDaysAtWork() {
		return daysAtWork;
	}

	public void setDaysAtWork(int daysAtWork) {
		this.daysAtWork = daysAtWork;
	}

    @PlanningVariable()
    @ValueRangeUndefined()
	public int getDaysOnSicknessBenefit() {
		return daysOnSicknessBenefit;
	}

	public void setDaysOnSicknessBenefit(int daysOnSicknessBenefit) {
		this.daysOnSicknessBenefit = daysOnSicknessBenefit;
	}

    @PlanningVariable()
    @ValueRangeUndefined()
	public int getDaysOnMinimumBenefit() {
		return daysOnMinimumBenefit;
	}

	public void setDaysOnMinimumBenefit(int daysOnMinimumBenefit) {
		this.daysOnMinimumBenefit = daysOnMinimumBenefit;
	}

    @PlanningVariable()
    @ValueRangeUndefined()
	public int getDaysOnLeaveWithoutPay() {
		return daysOnLeaveWithoutPay;
	}

	public void setDaysOnLeaveWithoutPay(int daysOnLeaveWithoutPay) {
		this.daysOnLeaveWithoutPay = daysOnLeaveWithoutPay;
	}

	public boolean isAtHomeDuringMonth() {
		return daysOnLeaveWithoutPay > 0 || daysOnSicknessBenefit > 0 || daysOnMinimumBenefit > 0;
	}

	public int getMinimumLevelBenefit() {
		int minimumLevelBenefit = parent.getMinimumLevelDailyAmount() * daysOnMinimumBenefit;
		return minimumLevelBenefit;
	}

	public int getSicknessLevelBenefit() {
		int sicknessLevelBenefit = parent.getSicknessBenefitDailyAmount() * daysOnSicknessBenefit;
		return sicknessLevelBenefit;
	}

	public int getTotalBenefit() {
		return getSicknessLevelBenefit() + getMinimumLevelBenefit();
	}

	public int getTotalPay() {
		int salary = parent.getMonthlySalary() * daysAtWork / month.getNumberOfDays();
		return salary + getTotalBenefit();
	}

	public int getTotalPayedDays() {
		return daysOnSicknessBenefit + daysOnMinimumBenefit + daysAtWork;
	}

	public int getTotalDaysOnBenefit() {
		return daysOnSicknessBenefit + daysOnMinimumBenefit;
	}

	public int getTotalDaysOnLeave() {
		return daysOnSicknessBenefit + daysOnMinimumBenefit + daysOnLeaveWithoutPay;
	}

	public int compareTo(MonthPlan other) {
        return new CompareToBuilder()
                .append(month, other.month)
                .append(parent, other.parent)
                .toComparison();
    }

    public MonthPlan clone() {
        MonthPlan clone = new MonthPlan();
        clone.id = id;
        clone.month = month;
        clone.parent = parent;
        clone.daysAtWork = daysAtWork;
        clone.daysOnLeaveWithoutPay = daysOnLeaveWithoutPay;
        clone.daysOnMinimumBenefit = daysOnMinimumBenefit;
        clone.daysOnSicknessBenefit = daysOnSicknessBenefit;
        return clone;
    }

    /**
     * The normal methods {@link #equals(Object)} and {@link #hashCode()} cannot be used because the rule engine already
     * requires them (for performance in their original state).
     * @see #solutionHashCode()
     */
    public boolean solutionEquals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof MonthPlan) {
            MonthPlan other = (MonthPlan) o;
            return new EqualsBuilder()
                    .append(id, other.id)
                    .append(month, other.month)
                    .append(parent, other.parent)
                    .append(daysAtWork, other.daysAtWork)
                    .append(daysOnLeaveWithoutPay, other.daysOnLeaveWithoutPay)
                    .append(daysOnMinimumBenefit, other.daysOnMinimumBenefit)
                    .append(daysOnSicknessBenefit, other.daysOnSicknessBenefit)
                    .isEquals();
        } else {
            return false;
        }
    }

    /**
     * The normal methods {@link #equals(Object)} and {@link #hashCode()} cannot be used because the rule engine already
     * requires them (for performance in their original state).
     * @see #solutionEquals(Object)
     */
    public int solutionHashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(month)
                .append(parent)
                .append(daysAtWork)
                .append(daysOnLeaveWithoutPay)
                .append(daysOnMinimumBenefit)
                .append(daysOnSicknessBenefit)
                .toHashCode();
    }

    @Override
    public String toString() {
        return month + " : " + parent;
    }

}
