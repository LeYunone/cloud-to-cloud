package com.leyunone.cloudcloud.handler.protocol;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/11 11:38
 */
public interface MyCloudProtocolHandler<R> {

    R action(String request);
}
