package com.leyunone.cloudcloud.api;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 设备控制
 * @author Yelandu
 * @date 2020/11/24
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

        private boolean isScene;

        /**
         * 值
         */
        private String value;
    }



}
