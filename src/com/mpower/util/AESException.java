package com.mpower.util;

public class AESException extends RuntimeException {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 1L;

    public AESException(String msg) {
        super(msg);
    }

    public AESException(String msg, Throwable root) {
        super(msg, root);
    }

}
