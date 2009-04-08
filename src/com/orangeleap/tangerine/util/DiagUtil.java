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
