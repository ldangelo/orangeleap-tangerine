package com.orangeleap.tangerine.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;

public class DistanceUtil {
	
	// Used to determine distance between zip codes, given census data for zips in zcta.txt
	// http://www.census.gov/geo/www/gazetteer/places2k.html#zcta
	
    protected static final Log logger = OLLogger.getLog(DistanceUtil.class);

	public final static class Zip {
		public String zip;
		public double latitude;
		public double longitude;
	}
	
	private static Map<String, Zip> loadData() throws IOException {
		Map<String, Zip> map = new HashMap<String, Zip>();
		InputStream fin = DistanceUtil.class.getClassLoader().getResourceAsStream("zcta.txt"); 
		BufferedReader bin = new BufferedReader(new InputStreamReader(fin));
		try {
			while (true) {
				String line = bin.readLine();
				if (line == null || line.trim().length() == 0) break;
				try {
					String zipcode = line.substring(3-1, 3+5-1);
					Zip zip = new Zip();
					zip.zip = zipcode;
					zip.latitude = Double.parseDouble(line.substring(137-1, 146-1).trim());
					zip.longitude = Double.parseDouble(line.substring(147-1, 157-1).trim());
					map.put(zipcode, zip);
				} catch (Exception e) {
					logger.debug("zcta parsing - "+e.getMessage()+": "+line);  // disregard lines missing geocodes
				}
			}
		} finally {
			fin.close();
		}
		return map;
	}
	
	public static void main(String[] args) {}

	private static Map<String, Zip> ZIPS;
	static {
		try {
			ZIPS = loadData();
		} catch (Exception e) {
			ZIPS = new HashMap<String, Zip>();
			logger.fatal("Unable to load zip code data from zcta.txt: " + e);
		}
	}

	public static List<String> getZipsWithinMilesOfZip(String zipcode, double miles) {
		List<String> result = new ArrayList<String>();
		Zip source = ZIPS.get(zipcode);
		if (source != null) {
			for (Zip zip: ZIPS.values()) {
				if (distance(source, zip) < miles) result.add(zip.zip);
			}
		} else {
			logger.debug("Zip code not in zcta data: "+zipcode);
		}
		return result;
	}

	private static double distance(Zip zip1, Zip zip2) {
		return distance(zip1.latitude, zip1.longitude, zip2.latitude, zip2.latitude);
	}

	private static final double EARTH_RADIUS_MILES = 3956.087107103049;
	
	// Haversine formula returns distance in miles
	public static double distance(double lat1, double long1, double lat2, double long2) {
		
		lat1 = (lat1 / 180d) * Math.PI;
		long1 = (long1 / 180d) * Math.PI;
		lat2 = (lat2 / 180d) * Math.PI;
		long2 = (long2 / 180d) * Math.PI;
		
		double distance = EARTH_RADIUS_MILES * 2 * Math.asin(
			Math.sqrt(
				Math.pow(Math.sin((lat1-lat2) / 2d), 2)	+
				Math.cos(lat1) * Math.cos(lat2) *
				Math.pow(Math.sin((long1-long2) / 2d), 2)
			)
		);
		
		return distance;
	}
	
	/*
	 
	  The ZCTA file contains data for all 5 digit ZCTAs in the 50 states, District of Columbia and Puerto Rico as of Census 2000. The file is plain ASCII text, one line per record.
	  
	¥	Columns 1-2: United States Postal Service State Abbreviation
	¥	Columns 3-66: Name (e.g. 35004 5-Digit ZCTA - there are no post office names)
	¥	Columns 67-75: Total Population (2000)
	¥	Columns 76-84: Total Housing Units (2000)
	¥	Columns 85-98: Land Area (square meters) - Created for statistical purposes only.
	¥	Columns 99-112: Water Area (square meters) - Created for statistical purposes only.
	¥	Columns 113-124: Land Area (square miles) - Created for statistical purposes only.
	¥	Columns 125-136: Water Area (square miles) - Created for statistical purposes only.
	¥	Columns 137-146: Latitude (decimal degrees) First character is blank or "-" denoting North or South latitude respectively
	¥	Columns 147-157: Longitude (decimal degrees) First character is blank or "-" denoting East or West longitude respectively

    */

	
}
