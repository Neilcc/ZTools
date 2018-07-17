package com.zcc.ztools.security;

import android.util.Base64;

import java.security.MessageDigest;

public class CommonUtils {
    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new String(Base64.encode(md.digest(), Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
