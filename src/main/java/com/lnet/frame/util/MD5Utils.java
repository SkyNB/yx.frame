package com.lnet.frame.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public final class MD5Utils {

    private final static char HEX_CHARS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String md5ForBase64(String str) {
        return encodeBase64(md5(str));
    }

    public static String md5ForHex(String str) {
        return encodeHex(md5(str));
    }

    public static byte[] md5(String encryptStr) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(encryptStr.getBytes("utf8"));
            return md5.digest();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encodeBase64(byte[] b) {
        return Base64.getEncoder().encodeToString(b);
    }

    public static String encodeHex(byte[] md) {
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (byte b : md) {
            str[k++] = HEX_CHARS[b >>> 4 & 0xf];
            str[k++] = HEX_CHARS[b & 0xf];
        }
        return new String(str);
    }
}

