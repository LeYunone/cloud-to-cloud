package com.leyunone.cloudcloud.bean.baidu;

import lombok.*;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BaiduAttributes {

    private String name;

    private String value;

    /**
     * 单位
     */
    private String scale;

    /**
     * 时间戳
     */
    private long timestampOfSample;

    /**
     * 属性值取样的时间误差，单位是ms。如果设备使用的是轮询时间间隔的取样方式，
     * 那么uncertaintyInMilliseconds就等于时间间隔。如温度传感器每1秒取样1次，
     * 那么uncertaintyInMilliseconds的值就是1000。
     */
    private long uncertaintyInMilliseconds;

    /**
     * 属性取值的合法范围，是字符串类型。字符串中包含的值，可以是单个值："INTEGER"，表示合法值是整数；"DOUBLE"，表示合法值是浮点数；"STRING"，表示合法值是字符串；
     * "BOOLEAN"，表示合法值是布尔值；"OBJECT"，表示合法值是json对象；
     * 可以是集合： "(A1, B1, C1, D1)"，表示值可以取这些字符串；
     * 也可以是数字范围："[from: to]"，表示合法值是处于对应的数值范围内。
     */
    private String legalValue;

}
