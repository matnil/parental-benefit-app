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

@XStreamAlias("Month")
@SuppressWarnings("serial")
public class Month extends AbstractPersistable implements Comparable<Month> {

    private int monthIndex;

	public int getMonthIndex() {
		return monthIndex;
	}

	public void setMonthIndex(int monthIndex) {
		this.monthIndex = monthIndex;
	}

    
    public int getYear() {
		return monthIndex / 100;
	}

    public int getMonthOfYear() {
		return monthIndex % 100;
	}

    public String getMonthString() {
    	switch (getMonthOfYear()) {
    		case 1:
    			return "JAN";
    		case 2:
    			return "FEB";
    		case 3:
    			return "MAR";
    		case 4:
    			return "APR";
    		case 5:
    			return "MAY";
    		case 6:
    			return "JUN";
    		case 7:
    			return "JUL";
    		case 8:
    			return "AUG";
    		case 9:
    			return "SEP";
    		case 10:
    			return "OCT";
    		case 11:
    			return "NOV";
    		case 12:
    			return "DEC";
    	}
    	throw new RuntimeException("Shall never happen...");
    }

	public int getNumberOfDays() {
    	switch (getMonthOfYear()) {
			case 1:
				return 31;
			case 2:
				return 28;
			case 3:
				return 31;
			case 4:
				return 30;
			case 5:
				return 31;
			case 6:
				return 30;
			case 7:
				return 31;
			case 8:
				return 31;
			case 9:
				return 30;
			case 10:
				return 31;
			case 11:
				return 30;
			case 12:
				return 31;
		}
		throw new RuntimeException("Shall never happen...");
	}

	public int compareTo(Month other) {
        return new CompareToBuilder()
                .append(monthIndex, other.monthIndex)
                .toComparison();
    }

    @Override
    public String toString() {
        return monthIndex + "(" + getNumberOfDays() + " days)";
    }

}
