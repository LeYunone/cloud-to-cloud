package com.leyunone.cloudcloud.util;


import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/18 11:34
 */
public class TimeTranslationUtils {

    /**
     * 过期时间转换为int毫秒
     *
     * @param expiresInTime
     * @param thirdPartyCloud
     * @return
     */
    public static int expiresInTimeToInt(Long expiresInTime, ThirdPartyCloudEnum thirdPartyCloud) {
        int time = 0;
        switch (thirdPartyCloud) {
            case YINGSHI:
                //萤石传过来是毫秒
            default:
                time = expiresInTime.intValue();
        }
        return time;
    }
}
