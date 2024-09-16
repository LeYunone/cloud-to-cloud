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
public class TimingRefreshTokenMessage extends DelayMessage {

    private String clientId;

    private String messageId;

    public TimingRefreshTokenMessage() {
    }

    public TimingRefreshTokenMessage(DelayMessageTypeEnum delayMessageTypeEnum) {
        super(delayMessageTypeEnum);
    }
}
