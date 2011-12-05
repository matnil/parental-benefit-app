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
import org.drools.planner.examples.parentalbenefit.domain.ParentalBenefitPlan;
import org.drools.runtime.rule.FactHandle;

public class UpdateDaysOnSicknessBenefitMove implements Move {

	private ParentalBenefitPlan parentalBenefitPlan;
    private MonthPlan monthPlan;
    private int increaseInNumberOfDaysOnSicknessBenefit;

    public UpdateDaysOnSicknessBenefitMove(ParentalBenefitPlan parentalBenefitPlan, MonthPlan monthPlan, int increaseInNumberOfDaysOnSicknessBenefit) {
        this.parentalBenefitPlan = parentalBenefitPlan;
    	this.monthPlan = monthPlan;
        this.increaseInNumberOfDaysOnSicknessBenefit = increaseInNumberOfDaysOnSicknessBenefit;
    }

    public boolean isMoveDoable(WorkingMemory workingMemory) {
    	if (increaseInNumberOfDaysOnSicknessBenefit > 0){
            return (monthPlan.getDaysAtWork() >= increaseInNumberOfDaysOnSicknessBenefit &&
            		!parentalBenefitPlan.getPlanForOtherParent(monthPlan).isAtHomeDuringMonth());
    	}
    	else {
            return (monthPlan.getDaysOnSicknessBenefit() >= -increaseInNumberOfDaysOnSicknessBenefit);
    	}
    }

    public Move createUndoMove(WorkingMemory workingMemory) {
        return new UpdateDaysOnSicknessBenefitMove(parentalBenefitPlan, monthPlan, -increaseInNumberOfDaysOnSicknessBenefit);
    }

    public void doMove(WorkingMemory workingMemory) {
    	int newDaysAtWork = monthPlan.getDaysAtWork() - increaseInNumberOfDaysOnSicknessBenefit;
    	int newDaysOnSicknessBenefit = monthPlan.getDaysOnSicknessBenefit() + increaseInNumberOfDaysOnSicknessBenefit;
        FactHandle factHandleMonthPlan = workingMemory.getFactHandle(monthPlan);
    	monthPlan.setDaysAtWork(newDaysAtWork);
    	monthPlan.setDaysOnSicknessBenefit(newDaysOnSicknessBenefit);
        workingMemory.update(factHandleMonthPlan, monthPlan);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof UpdateDaysOnSicknessBenefitMove) {
            UpdateDaysOnSicknessBenefitMove other = (UpdateDaysOnSicknessBenefitMove) o;
            return new EqualsBuilder()
                    .append(monthPlan, other.monthPlan)
                    .append(increaseInNumberOfDaysOnSicknessBenefit, other.increaseInNumberOfDaysOnSicknessBenefit)
                    .isEquals();
        } else {
            return false;
        }
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(monthPlan)
                .append(increaseInNumberOfDaysOnSicknessBenefit)
                .toHashCode();
    }

    public String toString() {
        return monthPlan + ": add " + increaseInNumberOfDaysOnSicknessBenefit + " day on sickness benefit";
    }

}
