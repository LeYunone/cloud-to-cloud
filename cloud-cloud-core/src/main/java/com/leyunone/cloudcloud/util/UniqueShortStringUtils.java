package com.leyunone.cloudcloud.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * :)
 * 生成唯一短串
 *
 * @Author leyunone
 * @Date 2023/12/14 17:27
 */
public class UniqueShortStringUtils {

    public static String generate(String str) {
        String shortStr = str;
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            byte[] digest = instance.digest(str.getBytes());
            shortStr = Base64.getEncoder().encodeToString(digest);
            shortStr = shortStr.substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return shortStr;
    }
}
