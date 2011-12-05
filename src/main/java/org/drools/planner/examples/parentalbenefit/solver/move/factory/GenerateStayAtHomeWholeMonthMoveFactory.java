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

package org.drools.planner.examples.parentalbenefit.solver.move.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.drools.planner.core.move.Move;
import org.drools.planner.core.move.factory.AbstractMoveFactory;
import org.drools.planner.core.solution.Solution;
import org.drools.planner.examples.parentalbenefit.domain.MonthPlan;
import org.drools.planner.examples.parentalbenefit.domain.ParentalBenefitPlan;
import org.drools.planner.examples.parentalbenefit.solver.move.ConfigureMonthMove;

public class GenerateStayAtHomeWholeMonthMoveFactory extends AbstractMoveFactory {

    public List<Move> createMoveList(@SuppressWarnings("rawtypes") Solution solution) {
    	ParentalBenefitPlan parentalBenefitPlan = (ParentalBenefitPlan) solution;
        List<MonthPlan> monthPlanList = parentalBenefitPlan.getMonthPlanList();
        List<Move> moveList = new ArrayList<Move>();
        int plannedMonths = 0;
        for (ListIterator<MonthPlan> iter = monthPlanList.listIterator(); iter.hasNext();) {
            MonthPlan monthPlan = iter.next();
            // only consider generating stay at home whole month moves for months where no parent is currently at home
            if (!monthPlan.isAtHomeDuringMonth() && !parentalBenefitPlan.getPlanForOtherParent(monthPlan).isAtHomeDuringMonth()) {
            	int daysInMonth = monthPlan.getMonth().getNumberOfDays();
            	for (int daysOnSicknessBenefit=daysInMonth; daysOnSicknessBenefit>=0; daysOnSicknessBenefit--) {
                    if (monthPlan.getMonth().getId() < 6) {
                    	// not allowed to use minimum benefit days first six months
                    	int daysOnMinimumBenefit=0;
                    	int daysOnLeaveWithoutPay=daysInMonth-daysOnSicknessBenefit;
                		moveList.add(new ConfigureMonthMove(monthPlan, daysOnSicknessBenefit, daysOnMinimumBenefit, daysOnLeaveWithoutPay));
                    }
                    else {
                    	for (int daysOnMinimumBenefit=daysInMonth-daysOnSicknessBenefit; daysOnMinimumBenefit>=0; daysOnMinimumBenefit--) {
                    		int daysOnLeaveWithoutPay=daysInMonth-daysOnSicknessBenefit-daysOnMinimumBenefit;
                    		moveList.add(new ConfigureMonthMove(monthPlan, daysOnSicknessBenefit, daysOnMinimumBenefit, daysOnLeaveWithoutPay));
	                    }                    	
                	}
            	}
            	if (plannedMonths++ > 1) {
	            	// only generate these moves for the first unplanned month
                    return moveList;
            	}
            }
        }
        return moveList;
    }

}
