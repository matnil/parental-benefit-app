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

package org.drools.planner.examples.parentalbenefit.swingui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.drools.planner.core.solution.Solution;
import org.drools.planner.examples.common.swingui.SolutionPanel;
import org.drools.planner.examples.parentalbenefit.domain.Month;
import org.drools.planner.examples.parentalbenefit.domain.MonthPlan;
import org.drools.planner.examples.parentalbenefit.domain.Parent;
import org.drools.planner.examples.parentalbenefit.domain.ParentalBenefitPlan;

/**
 * TODO this code is highly unoptimized
 */
@SuppressWarnings("serial")
public class ParentalBenefitPanel extends SolutionPanel {

    private static final Color HEADER_COLOR = new Color(255, 255, 170); // yellow
    private static final Color AT_HOME_COLOR = new Color(170, 255, 170); // green

    private BorderLayout borderLayout;
    private JPanel gridPanel;
    private GridLayout gridLayout;

    public ParentalBenefitPanel() {
    	borderLayout = new BorderLayout();
        setLayout(borderLayout);
    	gridPanel = new JPanel();
        gridLayout = new GridLayout(0, 1);
    	gridPanel.setLayout(gridLayout);
    	add(gridPanel, BorderLayout.CENTER);
    }

	@Override
	public void resetPanel(@SuppressWarnings("rawtypes") Solution solution) {
        gridPanel.removeAll();
        removeAll();
        ParentalBenefitPlan parentalBenefitPlan = (ParentalBenefitPlan)solution;
        add(new PlanOverviewPanel(parentalBenefitPlan), BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        
        gridLayout.setColumns(parentalBenefitPlan.getParentList().size() + 1);
        JLabel headerCornerLabel = new JLabel("Månad \\ Förälder");
        headerCornerLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        headerCornerLabel.setBackground(HEADER_COLOR);
        headerCornerLabel.setOpaque(true);
        gridPanel.add(headerCornerLabel);
        for (Parent parent : parentalBenefitPlan.getParentList()) {
            JLabel parentLabel = new JLabel(parent.toString());
            parentLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.DARK_GRAY),
                    BorderFactory.createEmptyBorder(2, 2, 2, 2)));
            parentLabel.setBackground(HEADER_COLOR);
            parentLabel.setOpaque(true);
            gridPanel.add(parentLabel);
        }
        Map<Month, Map<Parent, ParentMonthPanel>> monthParentPanelMap = new HashMap<Month, Map<Parent, ParentMonthPanel>>();
        for (Month month : parentalBenefitPlan.getMonthList()) {
            JLabel monthLabel = new JLabel("" + month.getMonthIndex());
            monthLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.DARK_GRAY),
                    BorderFactory.createEmptyBorder(2, 2, 2, 2)));
            monthLabel.setBackground(HEADER_COLOR);
            monthLabel.setOpaque(true);
            gridPanel.add(monthLabel);
            Map<Parent, ParentMonthPanel> parentPanelMap = new HashMap<Parent, ParentMonthPanel>();
            monthParentPanelMap.put(month, parentPanelMap);
            for (Parent parent : parentalBenefitPlan.getParentList()) {
                ParentMonthPanel parentMonthPanel = new ParentMonthPanel();
                /*
                if (parent.getContract().getWeekendDefinition().isWeekend(shiftDate.getDayOfWeek())) {
                    parentShiftDatePanel.setBackground(Color.LIGHT_GRAY);
                }
                */
                parentMonthPanel.setToolTipText("Planering för förälder " + parent.getName()
                        + " i månad " + month.getMonthIndex());
                gridPanel.add(parentMonthPanel);
                parentPanelMap.put(parent, parentMonthPanel);
            }
        }
        if (parentalBenefitPlan.isInitialized()) {
            for (MonthPlan monthPlan : parentalBenefitPlan.getMonthPlanList()) {
                Month month = monthPlan.getMonth();
                ParentMonthPanel parentMonthPanel = monthParentPanelMap.get(month).get(monthPlan.getParent());
                parentMonthPanel.addMonthPlan(monthPlan);
            }
        }
    }

    private class PlanOverviewPanel extends JPanel {

        public PlanOverviewPanel(ParentalBenefitPlan plan) {
            super(new GridLayout(0, 1));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.DARK_GRAY),
                    BorderFactory.createEmptyBorder(2, 2, 2, 2)));
            int totalDaysOnSicknessBenefit = 0;
            int totalDaysOnMinimumBenefit = 0;
            int totalDaysOnLeaveWithoutPay = 0;
            int totalBenefit = 0;
            int totalIncome = 0;
            int totalDaysAtHomeParent1 = 0;
            int totalDaysAtHomeParent2 = 0;
            Parent parent1 = plan.getParentList().get(0);
            Parent parent2 = plan.getParentList().get(1);
            if (plan.isInitialized()) {
	            for (MonthPlan monthPlan : plan.getMonthPlanList()) {
	            	totalDaysOnSicknessBenefit += monthPlan.getDaysOnSicknessBenefit();
	            	totalDaysOnMinimumBenefit += monthPlan.getDaysOnMinimumBenefit();
	            	totalDaysOnLeaveWithoutPay += monthPlan.getDaysOnLeaveWithoutPay();
	            	totalBenefit += monthPlan.getTotalBenefit();
	            	totalIncome += monthPlan.getTotalPay();
	            	if (monthPlan.getParent() == parent1) {
	            		totalDaysAtHomeParent1 += monthPlan.getTotalDaysOnLeave();
	            	}
	            	else {
	            		totalDaysAtHomeParent2 += monthPlan.getTotalDaysOnLeave();
	            	}
	            }
	            //add(new JLabel("Resultat: " + plan.getScore()));
	            add(new JLabel("Totalt antal dagar på sjukpenning-nivå: " + totalDaysOnSicknessBenefit));
	            add(new JLabel("Totalt antal dagar på lägsta-nivå: " + totalDaysOnMinimumBenefit));
	            add(new JLabel("Totalt antal dagar ledigt utan lön: " + totalDaysOnLeaveWithoutPay));
	            add(new JLabel("Totalt antal dagar hemma: " + (totalDaysAtHomeParent1 + totalDaysAtHomeParent2)));
	            add(new JLabel("Antal dagar hemma för förälder " + parent1.toString() + ": " + totalDaysAtHomeParent1));
	            add(new JLabel("Antal dagar hemma för förälder " + parent2.toString() + ": " + totalDaysAtHomeParent2));
	            add(new JLabel("Total föräldrapenning: " + totalBenefit + " kr"));
	            add(new JLabel("Total inkomst: " + totalIncome + " kr"));
            }
        }

    }

    private class ParentMonthPanel extends JPanel {

        public ParentMonthPanel() {
            super(new GridLayout(0, 1));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.DARK_GRAY),
                    BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        }

        public void addMonthPlan(MonthPlan monthPlan) {
        	if (monthPlan.isAtHomeDuringMonth()) {
        		this.setBackground(AT_HOME_COLOR);
        	}
            add(new JLabel("Arbetar: " + monthPlan.getDaysAtWork()));
            add(new JLabel("Sjukpenning-nivå: " + monthPlan.getDaysOnSicknessBenefit()));
            add(new JLabel("Lägsta-nivå: " + monthPlan.getDaysOnMinimumBenefit()));
            add(new JLabel("Ledig utan lön: " + monthPlan.getDaysOnLeaveWithoutPay()));
            add(new JLabel("Total inkomst: " + monthPlan.getTotalPay() + " kr"));
        }

    }

}
