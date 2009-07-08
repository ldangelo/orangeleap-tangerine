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

package com.orangeleap.tangerine.controller.importexport;

import java.util.ArrayList;
import java.util.List;


public class ProperCaseAddressUtil {
	
	public static String convertToProperCase(String s) {
		
		s = " " + s.toLowerCase() + " ";
		
		// Proper case
		StringBuilder sb = new StringBuilder();
		boolean start = true;
		boolean reserved = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (SEPARATORS.indexOf(c) > -1) {
				start = true;
				String word = getNextWord(s, i);
				reserved = RESERVED.contains(word.toUpperCase()) || hasNum(word);
			} else {
				if (start || reserved) {
					c = Character.toUpperCase(c);
				} 
				start = false;
			}
			sb.append(c);
		}

		return sb.toString().trim();
	}
	
	private static String getNextWord(String s, int i) {
		String word = s.substring(i+1);
		int sp = word.indexOf(" ");
		if (sp > -1) word = word.substring(0, sp);
		return word;
	}
	
	private static boolean hasNum(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (Character.isDigit(s.charAt(i))) return true;
		}
		return false;
	}
	
	private static String SEPARATORS = " ,.-/\'";
	
	private static List<String> RESERVED = new ArrayList<String>();
	static {
		RESERVED.add("CR");
		RESERVED.add("SR");
		RESERVED.add("US");
		RESERVED.add("FM");
		RESERVED.add("GA");
		RESERVED.add("RR");
		RESERVED.add("PO");
		RESERVED.add("NW");
		RESERVED.add("NE");
		RESERVED.add("SW");
		RESERVED.add("SE");
		RESERVED.add("N");
		RESERVED.add("S");
		RESERVED.add("E");
		RESERVED.add("W");
	}

	 

}
