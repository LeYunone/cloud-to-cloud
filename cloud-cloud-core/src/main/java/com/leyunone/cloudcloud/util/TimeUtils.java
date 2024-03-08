package com.leyunone.cloudcloud.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/30 15:57
 */
public class TimeUtils {

    /**
     * 获取UTC时区当前时间的 yyyyMMddTHHmmssSSSZ格式
     * @return
     */
    public static String getUTCyyyyMMddTHHmmssSSSZ() {
        Date currentTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(currentTime);
    }
}
