package com.leyunone.cloudcloud.util;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/14 16:40
 */
public class ClientTokenUtils {


    private static final String CLIENT_ACCESS_TOKEN = "CLIENT_ACCESS_TOKEN";
    private static final String CLIENT_REFRESH_TOKEN = "CLIENT_REFRESH_TOKEN";

    public static String generateClientAccessToken(String clientId, String businessId, Integer appId) {
        return String.join("_", CLIENT_ACCESS_TOKEN, clientId, businessId, appId.toString());
    }

    public static String generateClientRefreshToken(String clientId, String businessId, Integer appId) {
        return String.join("_", CLIENT_REFRESH_TOKEN, clientId, businessId, appId.toString());
    }
}
