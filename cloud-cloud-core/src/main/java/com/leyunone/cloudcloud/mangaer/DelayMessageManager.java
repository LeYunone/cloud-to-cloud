package com.leyunone.cloudcloud.mangaer;


import com.leyunone.cloudcloud.bean.message.DelayMessage;

public interface DelayMessageManager {

    /**
     * 发布延时消息
     * @param delayMessage 延时消息
     * @param delay 延时时间 单位毫秒
     * @return
     */
    void pushMessage(DelayMessage delayMessage, int delay);
}
