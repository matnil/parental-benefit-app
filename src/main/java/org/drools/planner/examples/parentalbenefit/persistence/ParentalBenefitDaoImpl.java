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

package org.drools.planner.examples.parentalbenefit.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.drools.planner.core.solution.Solution;
import org.drools.planner.examples.common.persistence.XstreamSolutionDaoImpl;
import org.drools.planner.examples.parentalbenefit.domain.Month;
import org.drools.planner.examples.parentalbenefit.domain.MonthPlan;
import org.drools.planner.examples.parentalbenefit.domain.Parent;
import org.drools.planner.examples.parentalbenefit.domain.ParentalBenefitPlan;

@SuppressWarnings("rawtypes")
public class ParentalBenefitDaoImpl extends XstreamSolutionDaoImpl {

    public ParentalBenefitDaoImpl() {
        super("parentalbenefit", ParentalBenefitPlan.class);
    }

	@Override
    public Solution readSolution(File file) {
    	Solution solution = super.readSolution(file);
    	postRead(solution);
    	return solution;
    }
    
    @Override
    protected void postRead(Solution solution) {
    	super.postRead(solution);
    	ParentalBenefitPlan plan = (ParentalBenefitPlan)solution;
    	if (plan.getMonthPlanList() == null || plan.getMonthPlanList().size() == 0) {
    		plan.setMonthPlanList(createMonthPlanList(plan));
    	}
    }
    
	public List<MonthPlan> createMonthPlanList(
			ParentalBenefitPlan parentalBenefitPlan) {
		List<Month> monthList = parentalBenefitPlan.getMonthList();

		List<MonthPlan> monthPlanList = new ArrayList<MonthPlan>(
				monthList.size() * 2);
		long monthPlanId = 0;
		for (Month month : monthList) {
			for (Parent parent : parentalBenefitPlan.getParentList()) {
				MonthPlan monthPlan = new MonthPlan();
				monthPlan.setId(monthPlanId++);
				monthPlan.setMonth(month);
				monthPlan.setParent(parent);
				monthPlan.setDaysAtWork(month.getNumberOfDays());
				monthPlanList.add(monthPlan);
			}
		}
		return monthPlanList;
	}

}
