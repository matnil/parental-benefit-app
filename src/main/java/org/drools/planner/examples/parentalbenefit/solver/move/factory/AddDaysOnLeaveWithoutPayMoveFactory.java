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
import org.drools.planner.examples.parentalbenefit.solver.move.UpdateDaysOnLeaveWithoutPayMove;

public class AddDaysOnLeaveWithoutPayMoveFactory extends AbstractMoveFactory {

    public List<Move> createMoveList(@SuppressWarnings("rawtypes") Solution solution) {
    	ParentalBenefitPlan parentalBenefitPlan = (ParentalBenefitPlan) solution;
        List<MonthPlan> monthPlanList = parentalBenefitPlan.getMonthPlanList();
        List<Move> moveList = new ArrayList<Move>();
        for (ListIterator<MonthPlan> iter = monthPlanList.listIterator(); iter.hasNext();) {
            MonthPlan monthPlan = iter.next();
            if (monthPlan.getDaysAtWork() > 0 &&
               	!parentalBenefitPlan.getPlanForOtherParent(monthPlan).isAtHomeDuringMonth()) {
            	// replace one day at work with one day on leave without pay
            	moveList.add(new UpdateDaysOnLeaveWithoutPayMove(parentalBenefitPlan, monthPlan, 1));
            }
        }
        return moveList;
    }

}
