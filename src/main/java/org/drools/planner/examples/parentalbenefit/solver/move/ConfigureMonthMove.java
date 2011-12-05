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

package org.drools.planner.examples.parentalbenefit.solver.move;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.drools.WorkingMemory;
import org.drools.planner.core.move.Move;
import org.drools.planner.examples.parentalbenefit.domain.MonthPlan;
import org.drools.runtime.rule.FactHandle;

public class ConfigureMonthMove implements Move {

    private MonthPlan monthPlan;
    private int daysOnSicknessBenefit;
    private int daysOnMinimumBenefit;
    private int daysOnLeaveWithoutPay;

    public ConfigureMonthMove(MonthPlan monthPlan, int daysOnSicknessBenefit, int daysOnMinimumBenefit, int daysOnLeaveWithoutPay) {
    	this.monthPlan = monthPlan;
    	this.daysOnSicknessBenefit = daysOnSicknessBenefit;
    	this.daysOnMinimumBenefit = daysOnMinimumBenefit;
    	this.daysOnLeaveWithoutPay = daysOnLeaveWithoutPay;
    }

    public boolean isMoveDoable(WorkingMemory workingMemory) {
    	return true;
    }

    public Move createUndoMove(WorkingMemory workingMemory) {
        return new ConfigureMonthMove(monthPlan, 0, 0, 0);
    }

    public void doMove(WorkingMemory workingMemory) {
    	int newDaysAtWork = monthPlan.getMonth().getNumberOfDays() - daysOnSicknessBenefit - daysOnMinimumBenefit - daysOnLeaveWithoutPay;
        FactHandle factHandleMonthPlan = workingMemory.getFactHandle(monthPlan);
    	monthPlan.setDaysAtWork(newDaysAtWork);
    	monthPlan.setDaysOnSicknessBenefit(daysOnSicknessBenefit);
    	monthPlan.setDaysOnMinimumBenefit(daysOnMinimumBenefit);
    	monthPlan.setDaysOnLeaveWithoutPay(daysOnLeaveWithoutPay);
        workingMemory.update(factHandleMonthPlan, monthPlan);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof ConfigureMonthMove) {
            ConfigureMonthMove other = (ConfigureMonthMove) o;
            return new EqualsBuilder()
                    .append(monthPlan, other.monthPlan)
                    .append(daysOnSicknessBenefit, other.daysOnSicknessBenefit)
                    .append(daysOnMinimumBenefit, other.daysOnMinimumBenefit)
                    .append(daysOnLeaveWithoutPay, other.daysOnLeaveWithoutPay)
                    .isEquals();
        } else {
            return false;
        }
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(monthPlan)
                .append(daysOnSicknessBenefit)
                .append(daysOnMinimumBenefit)
                .append(daysOnLeaveWithoutPay)
                .toHashCode();
    }

    public String toString() {
        return monthPlan + ": configure " + daysOnSicknessBenefit + ", " + daysOnMinimumBenefit + ", " + daysOnLeaveWithoutPay;
    }

}
