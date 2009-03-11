package com.orangeleap.tangerine.domain.checkservice;

/**
 * This class is used internalling for different
 * padding and formatting options used by the Paperless
 * Payment API. Typically used for left-padding with zeros,
 * which is something their API seems infatuated with.
 * @version 1.0
 */
class Padding {

    /**
     * Left pad the passed positive integer with zeros to fill out a
     * String to the specified length. If the integer is already
     * longer than the max Sring length, an IllegalArgumentException
     * will the thrown. An exception will also be thrown if the value
     * is negative.
     * @param val the value to pad into a String
     * @param length the total length of the String, including zeros
     * @return the padded Sring
     */
    public static String leftPadZero(int val, int length) {

        if(val < 0) {
            throw new IllegalArgumentException("Cannot left pad a negative number (" + val + ")");
        }

        String valString = Integer.toString(val);
        if(valString.length() > length) {
            throw new IllegalArgumentException("Cannot truncate " + val + " to a length of " +
                length + " without data loss");
        }

        StringBuilder builder = new StringBuilder(length);
        int padding = length - valString.length();
        for(int i =0; i<padding; i++) {
            builder.append("0");
        }

        builder.append(valString);
        return builder.toString();
    }

    

    /**
     * The Paperless Transaction API makes heavy use of integer/long values, but they are tightly
     * contrained with respect to length. This method will check a long to ensure it is
     * >= zero and meets the size contraints when converted to a String.
     * Will throw and IllegalArgumentException if the value is < 0 or does not conform to the
     * specified bounds when stringified.
     * @param elementName the name of the element; used for building the Exception
     * @param val the current value
     * @param minLen the minimum length for the stringified number; zero means unbounded
     * @param maxLen the maximum length for the stringfied number; zero means unbounded
     */
    public static void validateNumber(String elementName, long val, int minLen, int maxLen) {

        if(val <= 0) {
            throw new IllegalArgumentException(String.format("%s cannot have a value <= 0 (currently %d)",
                    elementName, val) );
        }

        String valString = Long.toString(val);

        if(minLen > 0 && valString.length() < minLen) {
            throw new IllegalArgumentException(String.format("%s must have a minimum length of %d (currently %d)",
                    elementName, minLen, val) );
        }

        if(maxLen > 0 && valString.length() > maxLen) {
            throw new IllegalArgumentException(String.format("%s must have a maximum length of %d (currently %d)",
                    elementName, maxLen, val) );
        }
    }



    /**
     * Similar to the int version above, this method will ensure a String  has
     * a length bounded by the specified min and max. It will also perform a trim of the String
     * before testing the bounds. Will thrown and IllegalArgumentException if the String does
     * not conform to the specified bounds.
     * @param elementName the name of the element, used for building the Exception
     * @param val the String value to test
     * @param minLen the minimum length of the String; zero means unbounded
     * @param maxLen the maximum length of the String; zero means unbounded
     * @return the result of trimming the String, assuming it passes the validation requirements
     */
    public static String validateString(String elementName, String val, int minLen, int maxLen) {

        // shortcut and assume null was intentional
        if(val == null) return null;

        String trimmedVal = val.trim();

        if(minLen > 0 && trimmedVal.length() < minLen) {
            throw new IllegalArgumentException(String.format("%s must have a minimum length of %d (currently %s)",
                    elementName, minLen, trimmedVal) );
        }

        if(maxLen > 0 && trimmedVal.length() > maxLen) {
            throw new IllegalArgumentException(String.format("%s must have a maximum length of %d (currently %s)",
                    elementName, maxLen, trimmedVal) );
        }

        return trimmedVal;


    }


}
