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
    private Long deviceId;


    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class FunctionIdCommand extends DeviceCommandDTO {
        /**
         * 值
         */
        private String value;

        /**
         * 功能Id
         */
        private Integer functionId;

        /**
         * 运算 详见#{@link FunctionOperation}
         */
        private String operation;
    }


    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class FunctionCodeCommand extends FunctionIdCommand{

        /**
         *功能标识码，需要与productId确保唯一
         */

        private String signCode;

        private boolean isScene;
    }



}
