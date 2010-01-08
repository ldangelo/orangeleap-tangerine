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

package com.orangeleap.tangerine.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.PropertyResourceBundle;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

public class AES {
	
    public static Logger logger = Logger.getLogger(AES.class.getName());

    /* The file containing the secret key */
    private static final String KEY_FILE_PATH_DEFAULT = "/key.txt";
    private static final String KEY_FILE_PATH_OVERRIDE = System.getProperty("key.file.path");
    private static final String KEY_FILE_PATH = (KEY_FILE_PATH_OVERRIDE != null)? KEY_FILE_PATH_OVERRIDE : KEY_FILE_PATH_DEFAULT;
    static {
    	logger.info("Using key file path "+KEY_FILE_PATH);
    }

    /* The key of the key/value pair in the secret key file */
    private static final String SECRET_KEY_KEY = "SECRET_KEY";

    /* The algorithm used */
    private static final String ALGORITHM = "AES";

    /**
     * Turns array of bytes into string
     * @param buf Array of bytes to convert to hex string
     * @return Generated hex string
     */
    private static String asHex(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;

        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }

        return strbuf.toString();
    }

    /**
     * Encrypt the bytes using a key in the <code>KEY_FILE_PATH</code>,
     * which is loaded from the CLASSPATH. If it can not find the key file
     * on the classpath, it will attempt to load it from the machine's
     * root directory.
     * @param clearString the clear text to encrypt
     * @return the encrypted hex string
     * @throws AESException
     */
    public static String encrypt(String clearString) throws AESException {
        InputStream keyInputStream = null;
        try {
            keyInputStream = AES.class.getResourceAsStream(KEY_FILE_PATH);
            if(keyInputStream == null) {
                // fallback to trying to load from the old location (root "/" directory)
                keyInputStream = new FileInputStream(KEY_FILE_PATH);
            }

            Properties prop = new Properties();
            prop.load(keyInputStream);
            String key = prop.getProperty(SECRET_KEY_KEY);
            return new String(Base64.encodeBase64(encrypt(clearString.getBytes(), new BigInteger(key, 16).toByteArray())));
        } catch (FileNotFoundException e) {
            throw new AESException("No key file found at " + KEY_FILE_PATH, e);
        } catch (IOException e) {
            throw new AESException("IO exception", e);
        } finally {
            IOUtils.closeQuietly(keyInputStream);
        }
    }

    /**
     * Encrypt the bytes using the specified key
     * @param clearBytes the clear text to encrypt
     * @param key the key used to encrypt
     * @return the encrypted <code>byte</code>[]
     * @throws AESException
     */
    private static byte[] encrypt(byte[] clearBytes, byte[] key) throws AESException {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            return cipher.doFinal(clearBytes);
        } catch (Exception e) {
            throw new AESException("Exception while encrypting", e);
        }
    }

    /**
     * Decrypt the bytes using a key in the <code>KEY_FILE_PATH</code>
     * @param encryptedString the encrypted text to decrypt
     * @return the decrypted <code>byte</code>[]
     * @throws AESException
     */
    public static String decrypt(String encryptedString) throws AESException {
        FileInputStream keyInputStream = null;
        try {
            keyInputStream = new FileInputStream(KEY_FILE_PATH);
            PropertyResourceBundle bundle = new PropertyResourceBundle(keyInputStream);
            String key = bundle.getString(SECRET_KEY_KEY);
            return new String(decrypt(Base64.decodeBase64(encryptedString.getBytes()), new BigInteger(key, 16).toByteArray()));
        } catch (FileNotFoundException e) {
            throw new AESException("No key file found at " + KEY_FILE_PATH, e);
        } catch (IOException e) {
            throw new AESException("IO exception", e);
        } finally {
            IOUtils.closeQuietly(keyInputStream);
        }
    }

    /**
     * Decrypt the bytes using the specified key
     * @param encryptedBytes the encrypted text to decrypt
     * @param key the key used to decrypt
     * @return the decrypted <code>byte</code>[]
     * @throws AESException
     */
    private static byte[] decrypt(byte[] encryptedBytes, byte[] key) throws AESException {
        byte[] clearBytes;
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            clearBytes = cipher.doFinal(encryptedBytes);
        } catch (Exception e) {
            throw new AESException("Exception while decrypting", e);
        }
        return clearBytes;
    }

    public static String findLastFourDigits(String number) {
        return number == null ? "" : (number.length() > 4 ? number.substring(number.length() - 4, number.length()) : number);
    }

    public static String decryptAndMask(String encryptedString) {
        String clear = null;
        if (encryptedString != null) {
            clear = decrypt(encryptedString);
            clear = mask(clear);
        }
        return clear;
    }

    public static String mask(String clear) {
        if (clear != null && clear.length() >= 4) {
            return new StringBuilder(StringConstants.MASK_START).append(clear.substring(clear.length() - 4)).toString();
        }
        return clear;
    }

    public static void main(String[] args) throws Exception {
        /* This generates a key to use */
        KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
        kgen.init(128);
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        String rawString = asHex(raw);
        logger.log(Level.INFO, "generated key = " + rawString);

        String beginningString = "this is a test";
        logger.log(Level.INFO, "beginning string = " + beginningString);
        byte[] encryptedBytes = AES.encrypt("this is a test".getBytes(), raw);
        logger.log(Level.INFO, "encrypted string (as hex) = " + asHex(encryptedBytes));
        byte[] clearBytes = AES.decrypt(encryptedBytes, raw);
        String endingString = new String(clearBytes);
        logger.log(Level.INFO, "ending string = " + endingString);
    }
}