package com.mpower.service.rule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.model.paymentInfo.Gift;

public class RuleHelperService {

	// Returns a Date object with a value of the time at the beginning of the year
	public static Date getBeginningOfYearDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 0, 0, 0, 0, 0);
		return cal.getTime();
	}

	// Returns a Date object as the current date/time
	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	// Returns a Date object from a time you specify, e.g. 2 months ago
	public static Date getBeginDate(int number, String timeUnit) {
		Calendar cal = Calendar.getInstance();
		StringBuilder args = new StringBuilder(timeUnit.toUpperCase());
		if (args.toString().equals("MONTHS") || args.toString().equals("YEARS")) {
			args.deleteCharAt(args.length()-1);
		}
		if (args.toString().equals("MONTH")) {
			cal.add(Calendar.MONTH, -(number));
		}
		if (args.toString().equals("YEAR")) {
			cal.add(Calendar.YEAR, -(number));
		}
		return cal.getTime();
	}

	// Returns an integer as the month of the transaction date
	public static int getMonthOfTransaction(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

	// Returns a Date object with the value of the time at the beginning of the month
	public static Date getBeginningOfMonthDate(int number) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -(number));
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return cal.getTime();
	}

	// Returns a Date object with the value of the time at the end of the month
	public static Date getEndOfMonthDate(int number) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -(number));
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getMaximum(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return cal.getTime();
	}

	// Returns a boolean value of whether or not one is a monthly donor
	public static boolean analyzeMonthlyDonor(ArrayList<Object> giftObjects, int numberOfMatches, int numberOfMonths) {

		int numMatches = 0;

		ArrayList<Gift> gifts = new ArrayList<Gift>();

		Gift gift = null;

		for(Object g : giftObjects) {
			if(g instanceof Gift) {
				gift = (Gift)g;
				gifts.add(gift);
			}
		}

		// Cycle through the last x months
		for (int i = 0; i < numberOfMonths; i++) {
			// For each gift
			for (Gift g : gifts) {
				// If there is a gift in month x, add to number of matches
				if((g.getTransactionDate().after(getBeginningOfMonthDate(i))) && (g.getTransactionDate().before(getEndOfMonthDate(i)))) {
					numMatches += 1;
					if(numberOfMatches == numMatches) {
						return true;
					}
					break;
				}

			}

		}
		return false;

	}

	// For the logger
	public static Log getLogger() {
		final Log logger = LogFactory.getLog("DROOLS: ");
		return logger;
	}


}
