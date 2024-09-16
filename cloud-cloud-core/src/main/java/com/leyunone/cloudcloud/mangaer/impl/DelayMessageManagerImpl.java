package com.leyunone.cloudcloud.mangaer.impl;

import com.alibaba.fastjson.JSON;
import com.leyunone.cloudcloud.bean.message.DelayMessage;
import com.leyunone.cloudcloud.mangaer.DelayMessageManager;
import com.leyunone.cloudcloud.system.config.DelayedQueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DelayMessageManagerImpl implements DelayMessageManager {

    private static final Logger log = LoggerFactory.getLogger(DelayMessageManager.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发布延时消息
     * @param delayMessage 延时消息
     * @param delay 延时时间 单位毫秒
     * @return
     */
    @Override
    public void pushMessage(DelayMessage delayMessage, int delay) {
        if (delayMessage == null){
            return;
        }
        String msgId = UUID.randomUUID().toString();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setMessageId(msgId);
        messageProperties.setCorrelationId(msgId);
        messageProperties.setDelay(delay);
        Message message = new Message(JSON.toJSONString(delayMessage).getBytes(), messageProperties);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(msgId);
        log.info("消息ID:{}", msgId);
        log.info("内容：{}", JSON.toJSONString(delayMessage));
        rabbitTemplate.convertAndSend(DelayedQueueConfig.EX_DELAYED, DelayedQueueConfig.R_DELAYED, message, correlationData);
    }
}
