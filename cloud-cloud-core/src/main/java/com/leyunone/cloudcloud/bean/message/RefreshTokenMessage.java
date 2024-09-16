package com.leyunone.cloudcloud.bean.message;

import com.leyunone.cloudcloud.bean.enums.DelayMessageTypeEnum;
import lombok.Data;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/18 11:07
 */
@Data
public class RefreshTokenMessage extends DelayMessage {

    //消息id
    private String messageId;
    
    private String refreshToken;
    
    private String clientId;

    /**
     * 绑定的业务id
     */
    private String businessId;

    private Integer appId;

    /**
     * 定时时间 如果大于int字节 说明对方token为年限制
     */
    private Integer timingTime;
    
    private boolean month;

    /**
     * 超过限定的时间
     */
    private Long overTime;
    
    public RefreshTokenMessage(){}
    
    public RefreshTokenMessage(DelayMessageTypeEnum delayMessageTypeEnum) {
        super(delayMessageTypeEnum);
    }
}
