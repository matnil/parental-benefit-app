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
import org.drools.planner.examples.common.domain.AbstractPersistable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Parent")
@SuppressWarnings("serial")
public class Parent extends AbstractPersistable implements Comparable<Parent> {

    private String name;
    private int monthlySalary;
    private int sicknessBenefitDailyAmount;
    private int minimumLevelDailyAmount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int compareTo(Parent other) {
        return new CompareToBuilder()
                .append(name, other.name)
                .toComparison();
    }

    @Override
    public String toString() {
        return name;
    }

    // Månadslön
    public int getMonthlySalary() {
		return monthlySalary;
	}

	public int getSicknessBenefitDailyAmount() {
		return sicknessBenefitDailyAmount;
	}

	public void setSicknessBenefitDailyAmount(int sicknessBenefitDailyAmount) {
		this.sicknessBenefitDailyAmount = sicknessBenefitDailyAmount;
	}

	public int getMinimumLevelDailyAmount() {
		return minimumLevelDailyAmount;
	}

	public void setMinimumLevelDailyAmount(int minimumLevelDailyAmount) {
		this.minimumLevelDailyAmount = minimumLevelDailyAmount;
	}

}
