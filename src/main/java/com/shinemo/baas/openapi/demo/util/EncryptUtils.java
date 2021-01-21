package com.shinemo.baas.openapi.demo.util;

import com.shinemo.baas.openapi.demo.common.ApiException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 加密工具类
 *
 * @date: 2021-01-21
 * @author: luchao
 * @email: luc@shinemo.com
 */
public class EncryptUtils {

    private final static char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 32位 MD5加密
     *
     * @param s
     * @return
     */
    public static String md5Hex(String s) {
        byte[] result = digest("MD5", s.getBytes(UTF_8));
        return hex(result);
    }

    /**
     * 32 位 sha256加密
     *
     * @param s
     * @return
     */
    public static String sha256(String s) {
        byte[] result = digest("SHA-256", s.getBytes(UTF_8));
        return hex(result);
    }

    private static byte[] digest(String algorithm, byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(data, 0, data.length);
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new ApiException(algorithm + " error", e);
        }
    }

    private static String hex(byte[] data) {
        char[] result = new char[data.length * 2];
        int c = 0;
        for (byte b : data) {
            result[c++] = HEX_DIGITS[(b >> 4) & 0xf];
            result[c++] = HEX_DIGITS[b & 0xf];
        }
        return new String(result);
    }
}
