package com.leyunone.cloudcloud.support;

import com.leyunone.cloudcloud.service.report.DeviceMessageReportShuntHandler;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * :)
 *  仅处理设备[属性]上报
 * @Author leyunone
 * @Date 2023/12/15 10:44
 */
@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "toc.device.shadow", durable = "true"),
        exchange = @Exchange(value = "device.shadow.topic", type = ExchangeTypes.TOPIC),
        key = {"device.shadow.toc"}
))
public class DeviceMessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(DeviceMessageConsumer.class);

    private final DeviceMessageReportShuntHandler shuntHandler;

    public DeviceMessageConsumer(DeviceMessageReportShuntHandler shuntHandler) {
        this.shuntHandler = shuntHandler;
    }

    @RabbitHandler
    public void receiveDeviceLog(@Payload String msg, Channel channel, Message message) throws IOException {
        try {
            log.info("receive device notice message,msg: {}", msg);
            shuntHandler.messageShunt(msg);
        } catch (Exception e) {
            log.error("handle device message failure,message: {" + msg + "}", e);
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
