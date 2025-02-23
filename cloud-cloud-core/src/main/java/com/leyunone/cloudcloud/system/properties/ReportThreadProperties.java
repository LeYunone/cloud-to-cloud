package com.leyunone.cloudcloud.system.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/18 14:48
 */
@ConfigurationProperties(prefix = "thread.report")
public class ReportThreadProperties {


    /**
     * 核心线程
     */
    private int corePoolSize = 4;

    /**
     * 最大线程数
     */
    private int maxPoolSize = 8;

    /**
     * 空闲线程存活时间 单位：s
     */
    private int keepAliveSeconds = 30;

    /**
     * 最大等待任务数
     */
    private int queueCapacity = 200;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }
}
