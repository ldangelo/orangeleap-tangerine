package com.mpower.test.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mpower.domain.util.AES;

public class AESTest {
    public static Logger logger = Logger.getLogger(AES.class.getName());

    /**
     * @param args
     */
    public static void main(String[] args) {
        logger.log(Level.INFO, "starting with string = 'this is a test'");
        logger.log(Level.INFO, "encrypted string = '" + AES.encrypt("this is a test") + "'");
        logger.log(Level.INFO, "descrypted string = '" + AES.decrypt("BgfmJP+odWGZzxLHLYka/A==") + "'");
    }
}
