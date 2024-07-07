package com.leyunone.cloudcloud.bean.third.baidu;

import lombok.*;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Getter
@Setter
public class BaiduErrorResponse {

    private BaiduHeader header;

    private Payload payload;


    @NoArgsConstructor
    public static class Payload {
    }

    @Getter
    @Setter
    public static class ValueOutOfRange extends Payload{

        private String minimumValue;

        private String maximumValue;

    }

    @Getter
    @Setter
    public static class CommonErrorPayload extends Payload{

        private ErrorInfo errorInfo;

    }


    public static class ErrorInfo {

    }

    @Getter
    @Setter
    public static class CodeErrorInfo extends ErrorInfo{

        private String code;

        private String description;

    }

    @Getter
    @Setter
    public static class NotSupportedInCurrentModeError extends ErrorInfo{

        private String currentDeviceMode;

    }

    public enum BaiduErrorName {

        /**
         * 值范围错误
         */
        VALUE_OUT_OF_RANGE_ERROR("ValueOutOfRangeError"),

        /**
         * 设备离线
         */
        TARGET_OFFLINE_ERROR("TargetOfflineError"),

        /**
         * 当技能无法在目标设备上获取指定值时，会发送该消息给DuerOS。DuerOS根据errorInfo.code值判断不同的故障。
         */
        UNABLE_TO_GET_VALUE_ERROR("UnableToGetValueError"),

        UNABLE_TO_SET_VALUE_ERROR("UnableToSetValueError"),

        /**
         * 当技能获取到目标设备不接受某项功能的参数设置
         */
        UNWILLING_TO_SET_VALUE_ERROR("UnwillingToSetValueError"),

        /**
         * 不支持的模式值
         */
        NOT_SUPPORTED_IN_CURRENT_MODE_ERROR("NotSupportedInCurrentModeError"),

        /**
         * token 失效
         */
        Expired_Access_Token_Error("ExpiredAccessTokenError"),

        /**
         * 不支持的操作
         */
        UNSUPPORTED_OPERATION_ERROR("UnsupportedOperationError"),

        DEPENDENT_SERVICE_UNAVAILABLE_ERROR("DependentServiceUnavailableError")

        ;

        private final String name;


        BaiduErrorName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }


    public enum BaiduErrorCode{
        /**
         * 由于设备没打开，无法获取指定的状态
         */
        DEVICE_AJAR,

        /**
         * 设备正忙
         */
        DEVICE_BUSY,
        DEVICE_JAMMED,
        DEVICE_OVERHEATED,
        HARDWARE_FAILURE,
        LOW_BATTERY,
        NOT_CALIBRATED,
        DEVICE_MODEL_TOO_OLD,
        MODE_NOT_SUPPORT_BOOKING,
        MODE_NOT_SUPPORT_CANCEL,
        UNMODE_NOT_SUPPORT_CANCEL,
        WORKING_STATE_NOT_SUPPORT_RESET,
        OPEN_STATE_NOT_SUPPORT_RESET,
        CLOSE_STATE_NOT_SUPPORT_RESET,
        BUSY_STATE_NOT_SUPPORT_RESET,
        NOT_WORKING,
        UNABLE_TO_SET_MODE,
        UNABLE_TO_MOVE_UP,
        UNABLE_TO_MOVE_DOWN,
        UNABLE_TO_REDUCE_FAN_SPEED,
        UNABLE_TO_INCREASE_FAN_SPEED,
        UNABLE_TO_SET_FAN_SPEED,
        UNABLE_TO_SET_COLOR,
        ALREADY_STOPPED,
        UNABLE_TO_CONTROL_IN_MOTION,

        ThermostatIsOff,
        ;
    }





}
