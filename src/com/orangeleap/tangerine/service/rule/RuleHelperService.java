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

package com.orangeleap.tangerine.service.rule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.communication.EmailService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.service.exception.DuplicateConstituentException;
import com.orangeleap.tangerine.service.rollup.RollupHelperService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.web.common.SortInfo;

public class RuleHelperService {

	protected final static Log logger = OLLogger.getLog(RuleHelperService.class);
	private static TangerineUserHelper userHelper;
	private static ConstituentService constituentService;
	private static EmailService emailService;
	private static PicklistItemService picklistItemService;
	private static RollupHelperService rollupHelperService;


	public RollupHelperService getRollupHelperService() {
		return rollupHelperService;
	}

	public void setRollupHelperService(RollupHelperService rollupHelperService) {
		this.rollupHelperService = rollupHelperService;
	}

    public PicklistItemService getPicklistItemService() {
		return picklistItemService;
	}

	public void setPicklistItemService(
			PicklistItemService picklistItemService) {
		this.picklistItemService = picklistItemService;
	}

	public  EmailService getEmailService() {
		return emailService;
	}

	public  void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public ConstituentService getConstituentService() {
		return constituentService;
	}

	public void setConstituentService(ConstituentService constituentService) {
		this.constituentService = constituentService;
	}

	public TangerineUserHelper getUserHelper() {
		return userHelper;
	}

	public void setUserHelper(TangerineUserHelper userHelper) {
		this.userHelper = userHelper;
	}

	public static String trimToNull(String str) {
		return StringUtils.trimToNull(str);
	}

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
            args.deleteCharAt(args.length() - 1);
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

        for (Object g : giftObjects) {
            if (g instanceof Gift) {
                gift = (Gift) g;
                gifts.add(gift);
            }
        }

        // Cycle through the last x months
        for (int i = 0; i < numberOfMonths; i++) {
            // For each gift
            for (Gift g : gifts) {
                // If there is a gift in month x, add to number of matches
                if ((StringUtils.equals(g.getGiftStatus(),Gift.STATUS_PAID) && (g.getDonationDate().after(getBeginningOfMonthDate(i))) && (g.getDonationDate().before(getEndOfMonthDate(i))))) {
                    numMatches += 1;
                    if (numberOfMatches == numMatches) {
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
        final Log logger = OLLogger.getLog(RuleHelperService.class);
        return logger;
    }

    public static Date getBeginDate(int number, String timeUnit, int fiscalYearStartingMonth ) {
    	Calendar cal = Calendar.getInstance();
    	StringBuilder args = new StringBuilder(timeUnit.toUpperCase());
    	if (args.toString().equals("DAYS") || args.toString().equals("WEEKS") || args.toString().equals("MONTHS") || args.toString().equals("YEARS") || args.toString().equals("FISCALYEARS")) {
    		args.deleteCharAt(args.length()-1);
    	}
    	if (args.toString().equals("DAY")) {
    		cal.add(Calendar.DATE, -(number));
    	}
    	else if (args.toString().equals("WEEK")) {
    		cal.add(Calendar.WEEK_OF_YEAR, -(number));
    	}
    	else if (args.toString().equals("MONTH")) {
    		cal.add(Calendar.MONTH, -(number));
    	}
    	else if (args.toString().equals("YEAR")) {
    		cal.add(Calendar.YEAR, -(number));
    	}
    	else if (args.toString().equals("FISCALYEAR")) {
    	    Calendar fiscalYearEnd = Calendar.getInstance();
    	    //subtract 1 from fiscalYearStartingMonth because Jan = 0
    	    fiscalYearEnd.set(Calendar.MONTH, fiscalYearStartingMonth - 1);
    	    fiscalYearEnd.set(Calendar.DAY_OF_MONTH,1);
    	    fiscalYearEnd.set(Calendar.HOUR_OF_DAY, 0);
            fiscalYearEnd.set(Calendar.MILLISECOND, 0);
            fiscalYearEnd.set(Calendar.SECOND, 0);
            fiscalYearEnd.set(Calendar.MINUTE, 0);

    	    if (fiscalYearEnd.compareTo(cal) == 1) {
    	        // Fiscal Year end is past today
    	        fiscalYearEnd.add(Calendar.YEAR,-(number));
    	       //System.out.println("Begin Date = " + fiscalYearEnd.getTime() );
    	        return fiscalYearEnd.getTime();
    	    } else {
    	        // Fiscal Year end is before today
    	        fiscalYearEnd.add(Calendar.YEAR,-(number - 1));
    	        //System.out.println("Begin Date = " + fiscalYearEnd.getTime() );
    	        return fiscalYearEnd.getTime();
            }
    	}


    	return cal.getTime();
    }


    /**
     * Takes a constituent and returns the number of gifts that constituent has given in the past timeAmount timeUnits
     * (i.e. past 6 MONTHS or 1 YEAR or 2 FISCALYEARS)
     * If the timeAmount and timeUnit are -1/null then it will return the total number of gifts a constituent has given.
     * @param constituent
     * @param timeAmount
     * @param timeUnits
     * @param fiscalYearStartingMonth
     * @return
     *
     */
    public static int numberOfDonationsMadePerTimeFrame(Constituent constituent, int timeAmount, String timeUnit ) {
    	int numberOfMatches = 0;
        int fiscalYearStartingMonth = -1;
    	List<Gift> gifts = constituent.getGifts();

        if ( (timeAmount == -1) || (timeUnit == null)){
            // Cycle through the gifts
            for (Gift g : gifts) {
                // If the gift status is paid add it to the number of matches.
                if ((StringUtils.equals(g.getGiftStatus(),Gift.STATUS_PAID))) {
                	numberOfMatches++;
                }
            }
            return numberOfMatches;
        }

    	if(timeUnit.equalsIgnoreCase("FISCALYEAR") || timeUnit.equalsIgnoreCase("FISCALYEARS")){
    		fiscalYearStartingMonth = Integer.parseInt(userHelper.getSiteOptionByName("fiscal.year.starting.month"));
    	}

        // Cycle through the gifts
        for (Gift g : gifts) {
            // If there is a gift given after the beginning date  and it has a gift status of paid add it to the number of matches.
            if ((StringUtils.equals(g.getGiftStatus(),Gift.STATUS_PAID) && (g.getDonationDate().equals(getBeginDate(timeAmount, timeUnit, fiscalYearStartingMonth)) || g.getDonationDate().after(getBeginDate(timeAmount, timeUnit, fiscalYearStartingMonth))))) {
            	numberOfMatches++;
            }
        }
        return numberOfMatches;
    }


    public static boolean analyzeMonthlyDonor(Constituent constituent, int numberOfMatches, int numberOfMonths) {

    	int numMatches = 0;

    	List<Gift> gifts = constituent.getGifts();

    	// Cycle through the last x months
    	for (int i = 0; i < numberOfMonths; i++) {
    		// For each gift
    		for (Gift g : gifts) {
    			// If there is a gift with a status of paid in month x, add to number of matches
    			if((StringUtils.equals(g.getGiftStatus(),Gift.STATUS_PAID) && (g.getDonationDate().after(getBeginningOfMonthDate(i))) && (g.getDonationDate().before(getEndOfMonthDate(i))))) {
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


    public static Boolean isDuplicate(Constituent p, String dupDetectCriteria) {
   		Map<String,Object> params = new HashMap<String, Object>();
    	List<Long> ignoreIds = new ArrayList<Long>();
    	long ignoreId = p.getId();
    	ignoreIds.add(ignoreId);

		//System.out.println ("criteria: " + dupDetectCriteria);

		//parse the string and put each into the params map
    	String[] duplicateDetectCriteriaArray = dupDetectCriteria.split(",");
        int paramCount = 0;

        for(int i = 0;i < duplicateDetectCriteriaArray.length;i++){
            String[] duplicateDetectCriteria = duplicateDetectCriteriaArray[i].split(":");
            String criteria = duplicateDetectCriteria[0];
            int numCharactersToMatch = Integer.parseInt(duplicateDetectCriteria[1]);


        //If it is a constituent then match on first and last name if org match on orgname
        if (p.isIndividual()){
           if ( criteria.equalsIgnoreCase("firstName") && p.getFirstName() != null){
           		params.put(criteria, p.getFirstName().substring(0, ((numCharactersToMatch > p.getFirstName().length()) ? p.getFirstName().length() : numCharactersToMatch)));
           		paramCount++;
           }
           if ( criteria.equalsIgnoreCase("lastName") && p.getLastName() != null){
           		params.put(criteria, p.getLastName().substring(0, ((numCharactersToMatch > p.getLastName().length()) ? p.getLastName().length() : numCharactersToMatch)));
           		paramCount++;
           }
        }else if (p.isOrganization()){
           if ( criteria.equalsIgnoreCase("organizationName") && p.getOrganizationName() != null){
           		params.put(criteria, p.getOrganizationName().substring(0, ((numCharactersToMatch > p.getOrganizationName().length()) ? p.getOrganizationName().length() : numCharactersToMatch)));
           		paramCount++;
           }

        }
		/* TODO: Implement match on account number
          if ( criteria.equalsIgnoreCase("accountNumber"))
           		params.put(criteria, p.getAccountNumber().ToString().substring(0,numCharactersToMatch));
		*/
			//Primary Email Info
           if ( criteria.equalsIgnoreCase("emailAddress") && p.getPrimaryEmail() != null && p.getPrimaryEmail().getEmailAddress() != null){
           		params.put("primaryEmail." + criteria, p.getPrimaryEmail().getEmailAddress().substring(0, ((numCharactersToMatch > p.getPrimaryEmail().getEmailAddress().length()) ? p.getPrimaryEmail().getEmailAddress().length() : numCharactersToMatch)));
         		paramCount++;
         	}

			//Primary Address Info
           if  (p.getPrimaryAddress() != null){
               if ( criteria.equalsIgnoreCase("addressLine1") && p.getPrimaryAddress().getAddressLine1() != null){
              		params.put("primaryAddress." + criteria, p.getPrimaryAddress().getAddressLine1().substring(0, ((numCharactersToMatch > p.getPrimaryAddress().getAddressLine1().length()) ? p.getPrimaryAddress().getAddressLine1().length() : numCharactersToMatch)));
              		paramCount++;
              	}
               if ( criteria.equalsIgnoreCase("stateProvince") && p.getPrimaryAddress().getStateProvince() != null){
              		params.put("primaryAddress." + criteria, p.getPrimaryAddress().getStateProvince().substring(0, ((numCharactersToMatch > p.getPrimaryAddress().getStateProvince().length()) ? p.getPrimaryAddress().getStateProvince().length() : numCharactersToMatch)));
              		paramCount++;
              	}
               if ( criteria.equalsIgnoreCase("city") && p.getPrimaryAddress().getCity() != null){
              		params.put("primaryAddress." + criteria, p.getPrimaryAddress().getCity().substring(0, ((numCharactersToMatch > p.getPrimaryAddress().getCity().length()) ? p.getPrimaryAddress().getCity().length() : numCharactersToMatch)));
              		paramCount++;
              	}
               if ( criteria.equalsIgnoreCase("postalCode") && p.getPrimaryAddress().getPostalCode() != null){
              		params.put("primaryAddress." + criteria, p.getPrimaryAddress().getPostalCode().substring(0, ((numCharactersToMatch > p.getPrimaryAddress().getPostalCode().length()) ? p.getPrimaryAddress().getPostalCode().length() : numCharactersToMatch)));
              		paramCount++;
              	}
           }
		}

		List<Constituent> constituents = null;
		if (paramCount > 0){
			params.put("byPassDuplicateDetection",0);
			SortInfo sortInfo = new SortInfo();
			sortInfo.setSort("accountNumber");
			sortInfo.setStart(0);

	    	constituents = constituentService.searchConstituents(params, true, sortInfo, Locale.US );
	    }


		if (constituents != null && constituents.size() > 1) return true;
    	return false;
}


    /**
     * Used by the groovy rule consequences to call the email sending service.
     * @param addresses
     * @param constituent
     * @param gift
     * @param subject
     * @param templateName
     * @param selectedEmails
     */
    public static void sendMail(String addresses, Constituent constituent, Gift gift, String subject, String body, String templateName, List<Email> selectedEmails) {
    	Map<String, String> map = new HashMap<String, String>();
    	if (body != null) map.put(EmailService.EMAIL_BODY, body);
    	emailService.sendMail(addresses,constituent,gift,subject,map,templateName, null);
    }

    /**
     * Used by the groovy rule consequences to call the email sending service.
     * @param constituent
     * @param gift
     * @param subject
     * @param templateName
     */
    public static void sendMail(Constituent p, Gift g, String subject, String body, String templateName, Boolean primaryOnly) {
    	Map<String, String> map = new HashMap<String, String>();
    	if (body != null) map.put(EmailService.EMAIL_BODY, body);
    	emailService.sendMail(p,  g,  null, null, map, subject,  templateName, primaryOnly);
    }


    /**
     * Takes a constituent and a threshold amount and evaluates if the constituents total donations are greater than
     * or equal to the threshold amount.
     * (i.e. Gave $8000+ )
     * @param constituent
     * @param threshold
     * @return
     *
     */
    public static Boolean evaluateTotalDonations(Constituent constituent, BigDecimal threshold) {
    	if (totalDonationsPerTimeFrame(constituent, -1, null ).compareTo(threshold) >= 0)
    		return true;
    	else
    		return false;
    }

    /**
     * Takes a constituent, threshold amount, and time interval and evaluates if the constituents total donations are greater than
     * or equal to the threshold amount.
     * (i.e. Gave $8000+ over past 6 MONTHS/1 YEAR/2 FISCALYEARS etc.)
     * @param constituent
     * @param threshold
     * @param timeAmount
     * @param timeUnits
     * @return
     *
     */
    public static Boolean evaluateTotalDonations(Constituent constituent, BigDecimal threshold, int timeAmount, String timeUnit  ) {
    	if (totalDonationsPerTimeFrame(constituent, timeAmount, timeUnit ).compareTo(threshold) >= 0)
    		return true;
    	else
    		return false;
    }


    /**
     * Takes a constituent and a range and evaluates if the constituents total donations are within that range.
     * (i.e. Gave between $1000 and $2000)
     * @param constituent
     * @param minRange
     * @param maxRange
     * @return
     *
     */
    public static Boolean evaluateTotalDonations(Constituent constituent, BigDecimal minRange, BigDecimal maxRange) {
    	BigDecimal totalDonations = totalDonationsPerTimeFrame(constituent, -1, null );
    	if (totalDonations.compareTo(minRange) >= 0 && totalDonations.compareTo(maxRange) <= 0)
    		return true;
    	else
    		return false;
    }

    /**
     * Takes a constituent, threshold amount, and time interval and evaluates if the constituents total donations are within that range.
     * (i.e. Gave between $1000 and $2000 over past 6 MONTHS/1 YEAR/2 FISCALYEARS etc.)
     * @param constituent
     * @param threshold
     * @param timeAmount
     * @param timeUnits
     * @return
     *
     */
    public static Boolean evaluateTotalDonations(Constituent constituent, BigDecimal minRange, BigDecimal maxRange, int timeAmount, String timeUnit ) {
    	BigDecimal totalDonations = totalDonationsPerTimeFrame(constituent, timeAmount, timeUnit );
    	if (totalDonations.compareTo(minRange) >= 0 && totalDonations.compareTo(maxRange) <= 0)
    		return true;
    	else
    		return false;
    }

    /**
     * Takes a constituent and returns the total donations made over a specific time interval.
     * (i.e. Gave $8000+ over past 6 MONTHS/1 YEAR/2 FISCALYEARS etc.)
     * If the timeAmount and timeUnit are -1/null then it will return the total for all gifts a constituent has given.
     * @param constituent
     * @param timeAmount
     * @param timeUnits
     * @return
     *
     */
    public static BigDecimal totalDonationsPerTimeFrame(Constituent constituent, int timeAmount, String timeUnit ) {
    	BigDecimal totalDonations = BigDecimal.valueOf(0);
        int fiscalYearStartingMonth = -1;
    	List<Gift> gifts = constituent.getGifts();

        if ( (timeAmount == -1) || (timeUnit == null)){
        	Iterator<Gift> itGifts = gifts.iterator();
        	while (itGifts.hasNext()) {
    			Gift gift = (Gift)itGifts.next();
    			if (StringUtils.equals(gift.getGiftStatus(),Gift.STATUS_PAID))
    				totalDonations = totalDonations.add(gift.getAmount());
    		}
        	return totalDonations;
        }

    	if(timeUnit.equalsIgnoreCase("FISCALYEAR") || timeUnit.equalsIgnoreCase("FISCALYEARS")){
    		fiscalYearStartingMonth = Integer.parseInt(userHelper.getSiteOptionByName("fiscal.year.starting.month"));
    	}

        // Cycle through the gifts
        for (Gift g : gifts) {
            // If there is a gift given after the beginning date add it to the running total.
            if (g.getDonationDate().equals(getBeginDate(timeAmount, timeUnit, fiscalYearStartingMonth)) || g.getDonationDate().after(getBeginDate(timeAmount, timeUnit, fiscalYearStartingMonth))) {
    			if (StringUtils.equals(g.getGiftStatus(),Gift.STATUS_PAID))
    				totalDonations = totalDonations.add(g.getAmount());
            }
        }
        return totalDonations;
    }

    /**
     * Takes a constituent and returns true if a they have made a one time donation amount in the specified
     * range over a specific time interval.
     * (i.e. Gave between $1000 and $2000 over past 6 MONTHS/1 YEAR/2 FISCALYEARS etc.)
     * If the timeAmount and timeUnit are -1/-1/null then it will evaluate all gifts a constituent has given.
     * If the maxAmount is -1 then it will evaluate all gifts a constituent has given.
     * @param constituent
     * @param minAmount
     * @param maxAmount
     * @param timeAmount
     * @param timeUnits
     * @return
     *
     */
    public static Boolean oneTimeDonationPerTimeFrame(Constituent constituent, BigDecimal minAmount, BigDecimal maxAmount, int timeAmount, String timeUnit ) {
    	Boolean oneTimeDonationMade = false;
        int fiscalYearStartingMonth = -1;
    	List<Gift> gifts = constituent.getGifts();

    	//one time donation made is greater than x (gift >= minAmount)
        if ( maxAmount == null && ((timeAmount == -1) || (timeUnit == null))){
        	Iterator<Gift> itGifts = gifts.iterator();
        	while (itGifts.hasNext()) {
    			Gift gift = (Gift)itGifts.next();
    			if (StringUtils.equals(gift.getGiftStatus(),Gift.STATUS_PAID) && gift.getAmount().compareTo(minAmount) >= 0)
    				oneTimeDonationMade = true;
    		}
        	return oneTimeDonationMade;
        //one time donation made is between x and y (minAmount <= gift >= maxAmount)
        }else if ( timeAmount == -1 || timeUnit == null){
        	Iterator<Gift> itGifts = gifts.iterator();
        	while (itGifts.hasNext()) {
    			Gift gift = (Gift)itGifts.next();
    			if (StringUtils.equals(gift.getGiftStatus(),Gift.STATUS_PAID) && (gift.getAmount().compareTo(minAmount) >= 0 && gift.getAmount().compareTo(maxAmount) <= 0))
    				oneTimeDonationMade = true;
    		}
        	return oneTimeDonationMade;
        //one time donation made is greater than x dollars and within the specific time frame (minAmount <= gift )
        }else if (maxAmount == null && ( timeAmount != -1 && timeUnit != null)){
        	if(timeUnit.equalsIgnoreCase("FISCALYEAR") || timeUnit.equalsIgnoreCase("FISCALYEARS")){
        		fiscalYearStartingMonth = Integer.parseInt(userHelper.getSiteOptionByName("fiscal.year.starting.month"));
        	}

            // Cycle through the gifts
            for (Gift g : gifts) {
                // If there is a gift given after the beginning date add it to the running total.
                if (((g.getDonationDate().equals(getBeginDate(timeAmount, timeUnit, fiscalYearStartingMonth)) || g.getDonationDate().after(getBeginDate(timeAmount, timeUnit, fiscalYearStartingMonth))))) {
        			if (StringUtils.equals(g.getGiftStatus(),Gift.STATUS_PAID) && g.getAmount().compareTo(minAmount) >= 0)
        				oneTimeDonationMade = true;
                }
            }
            return oneTimeDonationMade;
        //one time donation made is between x and y dollars and within the specific time frame (minAmount <= gift >= maxAmount)
        }else if (maxAmount != null && timeAmount != -1 && timeUnit != null){
        	if(timeUnit.equalsIgnoreCase("FISCALYEAR") || timeUnit.equalsIgnoreCase("FISCALYEARS")){
        		fiscalYearStartingMonth = Integer.parseInt(userHelper.getSiteOptionByName("fiscal.year.starting.month"));
        	}

            // Cycle through the gifts
            for (Gift g : gifts) {
                // If there is a gift given after the beginning date add it to the running total.
                if (((g.getDonationDate().equals(getBeginDate(timeAmount, timeUnit, fiscalYearStartingMonth)) || g.getDonationDate().after(getBeginDate(timeAmount, timeUnit, fiscalYearStartingMonth))))) {
        			if (StringUtils.equals(g.getGiftStatus(),Gift.STATUS_PAID) && (g.getAmount().compareTo(minAmount) >= 0 && g.getAmount().compareTo(maxAmount) <= 0))
        				oneTimeDonationMade = true;               }
            }
            return oneTimeDonationMade;
        }

        return false;

    }

    // Date d1 before d2
    public static BigDecimal age(Date d1, Date d2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);

        BigDecimal result = new BigDecimal(cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR));

        // Add fractional part.
        cal1.set(Calendar.YEAR, cal2.get(Calendar.YEAR)); // same day of year should result in 0 difference, so leap years must match later year.
        BigDecimal daydelta = new BigDecimal(cal2.get(Calendar.DAY_OF_YEAR) - cal1.get(Calendar.DAY_OF_YEAR));
        daydelta = daydelta.setScale(3);
        BigDecimal frac = daydelta.divide(new BigDecimal("365.25"), RoundingMode.FLOOR); // Do not round up or will increment age before anniversary date!
        result = result.add(frac);

        return result;
    }

//    public static void main(String[] args) {
//    	System.out.print(age(new Date("02/29/1966"), new Date("03/01/1967")));
//    }

    private static String OPERATOR_EQUALS = "=";
    private static String OPERATOR_LESS_THAN = "<";
    private static String OPERATOR_GREATER_THAN = ">";
    private static String OPERATOR_LESS_THAN_OR_EQUAL_TO = "<=";
    private static String OPERATOR_GREATER_THAN_OR_EQUAL_TO = ">=";

    private static boolean compareOperator(String operator, int compare) {
    	if (OPERATOR_EQUALS.equals(operator)) {
    		return compare == 0;
    	}
    	if (OPERATOR_LESS_THAN.equals(operator)) {
    		return compare < 0;
    	}
    	if (OPERATOR_GREATER_THAN.equals(operator)) {
    		return compare > 0;
    	}
    	if (OPERATOR_LESS_THAN_OR_EQUAL_TO.equals(operator)) {
    		return compare <= 0;
    	}
    	if (OPERATOR_GREATER_THAN_OR_EQUAL_TO.equals(operator)) {
    		return compare >= 0;
    	}
    	return false;
    }

    private static boolean isNumberConstant(String s) {
    	char c = s.charAt(0);
    	return Character.isDigit(c) || c == '-';
    }

    public static boolean getCustomFieldNumericCompare(AbstractCustomizableEntity entity, String fieldname, String operator, String compareTo) {

    	try {

	    	BigDecimal fieldValue = entity.getCustomFieldAsBigDecimal(fieldname);

	    	BigDecimal compareValue;
	    	if (isNumberConstant(compareTo)) {
	    		compareValue = new BigDecimal(compareTo);
	    	} else {
	    		compareValue = entity.getCustomFieldAsBigDecimal(compareTo);
	    	}

	    	if (fieldValue == null || compareValue == null) return false;
	    	return compareOperator(operator, fieldValue.compareTo(compareValue));

    	} catch (Exception e) {
    		return false;
    	}

    }

    private static final String TODAY = "TODAY";

    public static boolean getCustomFieldDateCompare(AbstractCustomizableEntity entity, String fieldname, String operator, String compareTo) {

    	try {

	    	Date fieldValue = entity.getCustomFieldAsDate(fieldname);

	    	Date compareValue;
	    	if (compareTo.equalsIgnoreCase(TODAY)) {
	    		compareValue = new java.util.Date();
	    		compareValue = DateUtils.truncate(compareValue, Calendar.DATE);
	    	} else if (isNumberConstant(compareTo)) {
	    		SimpleDateFormat sdf = new SimpleDateFormat(AbstractCustomizableEntity.FMT);
	    		compareValue = sdf.parse(compareTo);
	    	} else {
	    		compareValue = entity.getCustomFieldAsDate(compareTo);
	    	}

	    	if (fieldValue == null || compareValue == null) return false;
	    	return compareOperator(operator, fieldValue.compareTo(compareValue));

    	} catch (Exception e) {
    		return false;
    	}

    }

    public static boolean getCustomFieldDateAgeCompare(AbstractCustomizableEntity entity, String fieldname, String operator, String compareTo) {

    	try {

    		Date now = new Date();

	    	BigDecimal fieldValue = age(entity.getCustomFieldAsDate(fieldname), now);

	    	BigDecimal compareValue;
	    	if (isNumberConstant(compareTo)) {
	    		compareValue = new BigDecimal(compareTo);
	    	} else {
	    		compareValue = age(entity.getCustomFieldAsDate(compareTo), now);
	    	}

	    	if (fieldValue == null || compareValue == null) return false;
	    	return compareOperator(operator, fieldValue.compareTo(compareValue));

    	} catch (Exception e) {
    		return false;
    	}

    }

    public static Boolean isDesignationCodeContainedInDistroLines(Gift g, String designationCode) {
		List<DistributionLine> distroLines = g.getDistributionLines();
		for (DistributionLine distroLine : distroLines ){
			if (distroLine.getProjectCode() != null && distroLine.getProjectCode().compareToIgnoreCase(designationCode) == 0)
				return true;
		}
	    return false;
	}

    public static Boolean isMotivationCodeInFirstDistroLine(Gift g, String motivationCode) {
    		DistributionLine distroLine = g.getDistributionLines().get(0);
    		if (distroLine.getMotivationCode() != null && distroLine.getMotivationCode().compareToIgnoreCase(motivationCode) == 0)
    				return true;
    	    return false;
    	}

    public static Boolean isMotivationCodeContainedInDistroLines(Gift g, String motivationCode) {
		List<DistributionLine> distroLines = g.getDistributionLines();
		for (DistributionLine distroLine : distroLines ){
			if (distroLine.getMotivationCode() != null && distroLine.getMotivationCode().compareToIgnoreCase(motivationCode) == 0)
				return true;
		}
	    return false;
	}

    public static Boolean isDesignationCodeInFirstDistroLine(Gift g, String designationCode) {
    		DistributionLine distroLine = g.getDistributionLines().get(0);
    		if (distroLine.getProjectCode() != null && distroLine.getProjectCode().compareToIgnoreCase(designationCode) == 0)
    				return true;
    	    return false;
    	}

    public static Boolean distroLinesCustomFieldHasAnyValue(Gift g, String customField) {
		List<DistributionLine> distroLines = g.getDistributionLines();
		for (DistributionLine distroLine : distroLines ){
			if (distroLine.getCustomFieldValue(customField) != null)
				return true;
		}
	    return false;
	}

    public static Boolean isCustomFieldValueContainedInDistroLines(Gift g, String customField, String value) {
		List<DistributionLine> distroLines = g.getDistributionLines();
		for (DistributionLine distroLine : distroLines ){
			if (distroLine.getCustomFieldValue(customField) != null && distroLine.getCustomFieldValue(customField).contains(value))
				return true;
		}
	    return false;
	}

    public static void addDistroLineCustomFieldConstituentValueToPickList(Gift g, String customField, String picklist) {
		List<DistributionLine> distroLines = g.getDistributionLines();
		Picklist pl = picklistItemService.getPicklist(picklist);

		if (pl != null){
			for (DistributionLine distroLine : distroLines ){
				if (distroLine.getCustomFieldValue(customField) != null){
					Constituent relatedConstituent = constituentService.readConstituentById(Long.parseLong(distroLine.getCustomFieldValue(customField)));
					if (relatedConstituent != null){
						PicklistItem pli = new PicklistItem();
						pli.setPicklistId(picklistItemService.getPicklist(picklist).getId());
						pli.setItemName(relatedConstituent.getAccountNumber() + "-" + relatedConstituent.getFullName().replaceAll(" ", "").toLowerCase());
						pli.setItemOrder(pl.getPicklistItems().size() + 1);
						pli.setInactive(false);
						pli.setLongDescription(null);
						pli.setDefaultDisplayValue(relatedConstituent.getAccountNumber() + " - " + relatedConstituent.getFullName());
						pli.setDetail(null);
						pl.getPicklistItems().add(pli);
						picklistItemService.maintainPicklist(pl);
					}
				}
			}
		}else
			logger.error(picklist + " is not a valid picklist id name");

	}

    public static boolean hasProfileType(Constituent constituent, String profileType) {

    	if (constituent.isOrganization()){
    		if (constituent.getConstituentOrganizationRoles() != null && constituent.getConstituentOrganizationRoles().contains(profileType)){
    			return true;
    		}else
    			return false;
    	}else{
    		if (constituent.getConstituentIndividualRoles() != null && constituent.getConstituentIndividualRoles().contains(profileType)){
        		return true;
    		}else
    			return false;
    	}

	}

    public static void addProfileType(Constituent constituent, String profileType) {

    	if (constituent.isOrganization()){
    		if (constituent.getConstituentOrganizationRoles() == null || constituent.getConstituentOrganizationRoles().indexOf(profileType) == -1){
    			constituent.addConstituentOrganizationRoles(profileType);
    			try {
    				constituentService.maintainConstituent(constituent);
    			} catch (ConstituentValidationException e) {
    				logger.error(e);
    				e.printStackTrace();
    			} catch (BindException e) {
    				logger.error(e);
    				e.printStackTrace();
    			}
    		}
    	}else{
    		if (constituent.getConstituentIndividualRoles() == null || constituent.getConstituentIndividualRoles().indexOf(profileType) == -1 ){
        		constituent.addConstituentIndividualRoles(profileType);
        		try {
    				constituentService.maintainConstituent(constituent);
    			} catch (ConstituentValidationException e) {
    				logger.error(e);
    				e.printStackTrace();
    			} catch (BindException e) {
    				logger.error(e);
    				e.printStackTrace();
    			}

    		}
    	}

	}

    public static void removeProfileType(Constituent constituent, String profileType) {

    	if (constituent.isOrganization()){
    		if (constituent.getConstituentOrganizationRoles() != null && constituent.getConstituentOrganizationRoles().indexOf(profileType) != -1){
    			constituent.removeConstituentOrganizationRoles(profileType);
    			try {
    				constituentService.maintainConstituent(constituent);
    			} catch (ConstituentValidationException e) {
    				logger.error(e);
    				e.printStackTrace();
    			} catch (BindException e) {
    				logger.error(e);
    				e.printStackTrace();
    			}
    		}
    	}else{
    		if (constituent.getConstituentIndividualRoles() != null && constituent.getConstituentIndividualRoles().indexOf(profileType) != -1){
        		constituent.removeConstituentIndividualRoles(profileType);
        		try {
    				constituentService.maintainConstituent(constituent);
    			} catch (ConstituentValidationException e) {
    				logger.error(e);
    				e.printStackTrace();
    			} catch (BindException e) {
    				logger.error(e);
    				e.printStackTrace();
    			}

    		}
    	}

	}

    public static boolean hasDonorProfile(Constituent constituent, String donorProfile) {

		if (constituent.getCustomFieldValue(com.orangeleap.tangerine.domain.Constituent.DONOR_PROFILES) != null && constituent.getCustomFieldValue(com.orangeleap.tangerine.domain.Constituent.DONOR_PROFILES).contains(donorProfile)){
			return true;
		}else
			return false;

	}

    public static void addDonorProfile(Constituent constituent, String donorProfile) {

		if (constituent.getCustomFieldValue(com.orangeleap.tangerine.domain.Constituent.DONOR_PROFILES) == null || !constituent.getCustomFieldValue(com.orangeleap.tangerine.domain.Constituent.DONOR_PROFILES).contains(donorProfile) ){
			constituent.addCustomFieldValue(com.orangeleap.tangerine.domain.Constituent.DONOR_PROFILES,donorProfile);
    		try {
				constituentService.maintainConstituent(constituent);
			} catch (ConstituentValidationException e) {
				logger.error(e);
				e.printStackTrace();
			} catch (BindException e) {
				logger.error(e);
				e.printStackTrace();
			}

		}
	}

    public static void removeDonorProfile(Constituent constituent, String donorProfile) {

		if (constituent.getCustomFieldValue(com.orangeleap.tangerine.domain.Constituent.DONOR_PROFILES) != null && constituent.getCustomFieldValue(com.orangeleap.tangerine.domain.Constituent.DONOR_PROFILES).contains(donorProfile)){
			constituent.removeCustomFieldValue(com.orangeleap.tangerine.domain.Constituent.DONOR_PROFILES,donorProfile);
    		try {
				constituentService.maintainConstituent(constituent);
			} catch (ConstituentValidationException e) {
				logger.error(e);
				e.printStackTrace();
			} catch (BindException e) {
				logger.error(e);
				e.printStackTrace();
			}
    	}

	}



    public Constituent updateConstituentDependencies(Constituent constituent) {
    	try {
    		constituent = constituentService.maintainConstituent(constituentService.readConstituentById(constituent.getId()));
    		getRollupHelperService().refreshByConstituent(constituent.getId());
    	} catch (Exception e) {
    		logger.error(e);
    	}
    	return constituent;
    }

    public static void throwConstituentValidationException(String message) {
    	ConstituentValidationException cve = new ConstituentValidationException(message);
    	cve.addValidationResult(message);
    	throw new OrangeLeapConsequenceRuntimeException(cve);
    }

    public static void throwDuplicateConstituentException() {
    	DuplicateConstituentException cve = new DuplicateConstituentException();
    	throw new OrangeLeapConsequenceRuntimeException(cve);
    }

}
