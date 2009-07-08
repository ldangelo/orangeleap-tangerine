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

package com.orangeleap.tangerine.domain.checkservice;

/**
 * Represents each detail line in the inner XML document
 * returned by Paperless Payments
 * @version 1.0
 */
public class Response {

    private int transactionNumber;
    private String transactionConfirmation;
    private String message;

    public Response(int transactionNumber, String transactionConfirmation, String message) {
        this.transactionNumber = transactionNumber;
        this.transactionConfirmation = transactionConfirmation;
        this.message = message.trim();
    }

    /**
     * Get the Transaction Number of the request that this response
     * corresponds to.
     * @return the Transaction Number as an integer
     */
    public int getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * Gets the confirmation number for this transaction
     * @return the Transaction Confirmation Number as a String
     */
    public String getTransactionConfirmation() {
        return transactionConfirmation;
    }

    /**
     * Gets the message sent back in response to the transaction
     * @return the message String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Convenience method that returns true if the Batch request was accepted
     * for the transaction number. Based on a simple String comparison which checks
     * that the response message starts with the word "Accepted".
     * @return
     */
    public boolean isAccepted() {
        return message.startsWith("Accepted");
    }

    /**
     * Convenience method for testing purposes. If a Test message is sent by setting
     * Batch#isTest(true), this method will return true if the response message contains
     * the String <code>*In Test Mode*</code> which indicates the server acknowledged
     * it is a test message.
     * @return
     */
    public boolean isTestResponse() {
        return message.contains("*In Test Mode*");
    }


    @Override
    public String toString() {
        return "Transaction # " + transactionNumber + ", Confirmation: " + transactionConfirmation +
                ", Message: " + message;
    }



}
