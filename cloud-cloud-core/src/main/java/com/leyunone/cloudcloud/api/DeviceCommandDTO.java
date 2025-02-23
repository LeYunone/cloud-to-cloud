package com.leyunone.cloudcloud.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 设备控制
 * @Author LeYunone
 * @Date 2024/1/18 10:53
**/
@Data
public class DeviceCommandDTO implements Serializable {

    /**
     * 设备Id
     */
    private String deviceId;


    /**
     * 运算 详见#{@link FunctionOperation}
     */
    private String operation;


    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class FunctionCodeCommand extends DeviceCommandDTO{

        /**
         *功能标识码，需要与productId确保唯一
         */

        private String signCode;

        /**
         * 值
         */
        private String value;
    }



}
