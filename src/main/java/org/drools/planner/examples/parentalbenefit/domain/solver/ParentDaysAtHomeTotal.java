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

package org.drools.planner.examples.parentalbenefit.domain.solver;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.drools.planner.examples.parentalbenefit.domain.Parent;

@SuppressWarnings("serial")
public class ParentDaysAtHomeTotal implements Comparable<ParentDaysAtHomeTotal>, Serializable {

    private Parent parent;
    private int daysOnSicknessBenefit;
    private int daysOnMinimumBenefit;
    private int daysWithoutPay;

    public ParentDaysAtHomeTotal(Parent parent, int daysOnSicknessBenefit, int daysOnMinimumBenefit, int daysWithoutPay) {
        this.parent = parent;
        this.daysOnSicknessBenefit = daysOnSicknessBenefit;
        this.daysOnMinimumBenefit = daysOnMinimumBenefit;
        this.daysWithoutPay = daysWithoutPay;
    }

    public Parent getParent() {
		return parent;
	}
    
    public void setParent(Parent parent) {
		this.parent = parent;
	}

    public int getDaysOnMinimumBenefit() {
		return daysOnMinimumBenefit;
	}
    
    public int getDaysOnSicknessBenefit() {
		return daysOnSicknessBenefit;
	}
    
    public int getTotalDaysOnBenefit() {
		return daysOnSicknessBenefit + daysOnMinimumBenefit;
	}
    
    public int getDaysWithoutPay() {
		return daysWithoutPay;
	}
    
    public int getTotalDaysAtHome() {
        return getTotalDaysOnBenefit() + daysWithoutPay;
    }
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof ParentDaysAtHomeTotal) {
            ParentDaysAtHomeTotal other = (ParentDaysAtHomeTotal) o;
            return new EqualsBuilder()
                    .append(parent, other.parent)
                    .append(daysOnSicknessBenefit, other.daysOnSicknessBenefit)
                    .append(daysOnMinimumBenefit, other.daysOnMinimumBenefit)
                    .append(daysWithoutPay, other.daysWithoutPay)
                    .isEquals();
        } else {
            return false;
        }
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(parent)
                .append(daysOnSicknessBenefit)
                .append(daysOnMinimumBenefit)
                .append(daysWithoutPay)
                .toHashCode();
    }

    public int compareTo(ParentDaysAtHomeTotal other) {
        return new CompareToBuilder()
                .append(parent, other.parent)
                .append(daysOnSicknessBenefit, other.daysOnSicknessBenefit)
                .append(daysOnMinimumBenefit, other.daysOnMinimumBenefit)
                .append(daysWithoutPay, other.daysWithoutPay)
                .toComparison();
    }

    @Override
    public String toString() {
        return parent + " = " + getTotalDaysOnBenefit() + " (+" + daysWithoutPay + ")";
    }

}
