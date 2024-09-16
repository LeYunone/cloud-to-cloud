package com.leyunone.cloudcloud.bean.message;


import com.leyunone.cloudcloud.bean.enums.DelayMessageTypeEnum;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/6/18 11:06
 */
public class DelayMessage {

    private DelayMessageTypeEnum delayMessageType;

    public DelayMessage(DelayMessageTypeEnum delayMessageType) {
        this.delayMessageType = delayMessageType;
    }

    public DelayMessageTypeEnum getDelayMessageType() {
        return delayMessageType;
    }
    
    public DelayMessage(){
        
    }

    public DelayMessage setDelayMessageType(DelayMessageTypeEnum delayMessageType) {
        this.delayMessageType = delayMessageType;
        return this;
    }
}
