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

@XStreamAlias("Settings")
@SuppressWarnings("serial")
public class Settings extends AbstractPersistable implements Comparable<Settings> {

    private int minimumMonthlyIncome;

    // minsta acceptabla inkomsten per månad för hushållet
    public int getMinimumMonthlyIncome() {
		return minimumMonthlyIncome;
	}
    
    public void setMinimumMonthlyIncome(int minimumMonthlyIncome) {
		this.minimumMonthlyIncome = minimumMonthlyIncome;
	}
    
    public int compareTo(Settings other) {
        return new CompareToBuilder()
                .append(id, other.id)
                .toComparison();
    }

    @Override
    public String toString() {
        return "settings: minimumMonthlyIncome=" +  minimumMonthlyIncome;
    }

}
