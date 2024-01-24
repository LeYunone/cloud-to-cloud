package com.leyunone.cloudcloud.handler.protocol;

import com.leyunone.cloudcloud.bean.info.ActionContext;

/**
 * :)
 *  标注策略组件
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
public interface CloudProtocolHandler<R>  {

    /**
     * 处理
     * @param request 请求
     * @param context 上下文
     * @return response 响应
     */
    R action(String request, ActionContext context);
}
