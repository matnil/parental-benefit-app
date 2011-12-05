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

public class TransferDaysBetweenParentsMove implements Move {

    private MonthPlan leftMonthPlan;
    private MonthPlan rightMonthPlan;
    private int daysOnSicknessBenefit;
    private int daysOnMinimumBenefit;
    private int daysOnLeaveWithoutPay;
    
    public TransferDaysBetweenParentsMove(MonthPlan leftMonthPlan, MonthPlan rightMonthPlan, int daysOnSicknessBenefit, int daysOnMinimumBenefit, int daysOnLeaveWithoutPay) {
        this.leftMonthPlan = leftMonthPlan;
        this.rightMonthPlan = rightMonthPlan;
        this.daysOnSicknessBenefit = daysOnSicknessBenefit;
        this.daysOnMinimumBenefit = daysOnMinimumBenefit;
        this.daysOnLeaveWithoutPay = daysOnLeaveWithoutPay;
    }

    public boolean isMoveDoable(WorkingMemory workingMemory) {
        return leftMonthPlan.getMonth().equals(rightMonthPlan.getMonth()) &&
        	!leftMonthPlan.getParent().equals(rightMonthPlan.getParent()) &&
        	leftMonthPlan.getDaysOnSicknessBenefit() >= daysOnSicknessBenefit &&
        	leftMonthPlan.getDaysOnMinimumBenefit() >= daysOnMinimumBenefit &&
        	leftMonthPlan.getDaysOnLeaveWithoutPay() >= daysOnLeaveWithoutPay;
    }

    public Move createUndoMove(WorkingMemory workingMemory) {
        return new TransferDaysBetweenParentsMove(rightMonthPlan, leftMonthPlan, daysOnSicknessBenefit, daysOnMinimumBenefit, daysOnLeaveWithoutPay);
    }

    public void doMove(WorkingMemory workingMemory) {
        FactHandle leftFactHandle = workingMemory.getFactHandle(leftMonthPlan);
        FactHandle rightFactHandle = workingMemory.getFactHandle(rightMonthPlan);
        leftMonthPlan.setDaysOnSicknessBenefit(leftMonthPlan.getDaysOnSicknessBenefit() - daysOnSicknessBenefit);
        leftMonthPlan.setDaysOnMinimumBenefit(leftMonthPlan.getDaysOnMinimumBenefit() - daysOnMinimumBenefit);
        leftMonthPlan.setDaysOnLeaveWithoutPay(leftMonthPlan.getDaysOnLeaveWithoutPay() - daysOnLeaveWithoutPay);
        leftMonthPlan.setDaysAtWork(leftMonthPlan.getDaysAtWork() + daysOnSicknessBenefit + daysOnMinimumBenefit + daysOnLeaveWithoutPay);
        rightMonthPlan.setDaysOnSicknessBenefit(rightMonthPlan.getDaysOnSicknessBenefit() + daysOnSicknessBenefit);
        rightMonthPlan.setDaysOnMinimumBenefit(rightMonthPlan.getDaysOnMinimumBenefit() + daysOnMinimumBenefit);
        rightMonthPlan.setDaysOnLeaveWithoutPay(rightMonthPlan.getDaysOnLeaveWithoutPay() + daysOnLeaveWithoutPay);
        rightMonthPlan.setDaysAtWork(rightMonthPlan.getDaysAtWork() - (daysOnSicknessBenefit + daysOnMinimumBenefit + daysOnLeaveWithoutPay));
    	workingMemory.update(leftFactHandle, leftMonthPlan);
    	workingMemory.update(rightFactHandle, rightMonthPlan);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof TransferDaysBetweenParentsMove) {
            TransferDaysBetweenParentsMove other = (TransferDaysBetweenParentsMove) o;
            return new EqualsBuilder()
                    .append(leftMonthPlan, other.leftMonthPlan)
                    .append(rightMonthPlan, other.rightMonthPlan)
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
                .append(leftMonthPlan)
                .append(rightMonthPlan)
                .append(daysOnSicknessBenefit)
                .append(daysOnMinimumBenefit)
                .append(daysOnLeaveWithoutPay)
                .toHashCode();
    }

    public String toString() {
        return leftMonthPlan + " <=> " + rightMonthPlan;
    }

}
