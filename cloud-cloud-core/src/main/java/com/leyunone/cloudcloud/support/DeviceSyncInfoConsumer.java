package com.leyunone.cloudcloud.support;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.service.report.ReportMessageReportShuntHandler;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * :)
 * 仅处理设备[属性]上报
 *
 * @Author LeYunone
 * @Date 2023/12/15 10:44
 */
@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "toc.device.status", durable = "true"),
        exchange = @Exchange(value = "device.report.topic", type = ExchangeTypes.TOPIC),
        key = {"toc.device.sync"}
))
public class DeviceSyncInfoConsumer {

    private static final Logger log = LoggerFactory.getLogger(DeviceMessageConsumer.class);
    private final ReportMessageReportShuntHandler shuntHandler;

    public DeviceSyncInfoConsumer(ReportMessageReportShuntHandler shuntHandler) {
        this.shuntHandler = shuntHandler;
    }

    @RabbitHandler
    public void receiveDeviceLog(@Payload String msg, Channel channel, Message message) throws IOException {
        try {
            log.info("receive device notice message,msg: {}", msg);
            List<String> userIds = JSONObject.parseArray(msg, String.class);
            /**
             * 分流平台
             */
            shuntHandler.messageShunt(userIds);
        } catch (Exception e) {
            log.error("handle device message failure,message: {" + msg + "}", e);
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}

