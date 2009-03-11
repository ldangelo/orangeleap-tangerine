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
