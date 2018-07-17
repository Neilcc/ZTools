package com.zcc.ztools.security;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DesUtil {
    private static final byte[] SEED ="zccdesseedsample".getBytes();

    // Base64加密
    private static String base64Encode(byte[] data) {
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    // Base64解密
    private static byte[] base64Decode(String s) {
        return Base64.decode(s, Base64.DEFAULT);
    }


    /**
     * @param clearText : the clear text that will be encrypted.
     * @return the encrypted text.
     * @throws Exception : the exception.
     */
    public static String encryptText(String clearText)
            throws DESEception {
        try {
            byte[] rawKey = getRawKey(SEED);
            byte[] result = encrypt(rawKey, clearText.getBytes());
            return base64Encode(result);
        } catch (Exception e) {
            DESEception exception = new DESEception();
            exception.initCause(e);
            throw exception;
        }
    }


    /**
     * @param encrypted : the encrypted text.
     * @return the clear text.
     * @throws Exception : the exception.
     */
    public static String decryptText(String encrypted)
            throws DESEception {
        try {
            byte[] rawKey = getRawKey(SEED);
            byte[] enc = base64Decode(encrypted);
            byte[] result = decrypt(rawKey, enc);
            return new String(result);
        } catch (Exception e) {
            DESEception exception = new DESEception();
            exception.initCause(e);
            throw exception;
        }
    }

    /**
     * @param seed : the SEED will be used to produce raw key.
     * @return the key bytes
     * @throws Exception : the exception
     */
    private static byte[] getRawKey(byte[] seed) throws Exception {
        SecretKeySpec key = new SecretKeySpec(seed, "AES");
        byte[] raw = key.getEncoded();
        return raw;
    }

    /**
     * @param raw   : the raw key.
     * @param clear : the clear bytes.
     * @return the encrypted bytes.
     * @throws Exception : the exception.
     */
    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    /**
     * @param raw       : the raw key.
     * @param encrypted : the encrypted bytes.
     * @return the clear bytes.
     * @throws Exception : the exception.
     */
    private static byte[] decrypt(byte[] raw, byte[] encrypted)
            throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static class DESEception extends Exception {
    }
}
