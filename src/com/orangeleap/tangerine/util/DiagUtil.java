/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.util;

public class DiagUtil {
	
	public static String getMemoryStats() {

		StringBuilder sb = new StringBuilder();
		Runtime r = Runtime.getRuntime();
		
		long total = r.totalMemory();
		long free = r.freeMemory();
		long max = r.maxMemory();
		float pct = ((total*1.0f - free) / max)*100;

		sb.append("Total Memory: "+total+"\r\n");    
		sb.append("Free Memory:  "+free+"\r\n");
		sb.append("Max Memory:   "+max+"\r\n");
		sb.append("% full:       " + pct + "\r\n");

		return sb.toString();

	}

}
