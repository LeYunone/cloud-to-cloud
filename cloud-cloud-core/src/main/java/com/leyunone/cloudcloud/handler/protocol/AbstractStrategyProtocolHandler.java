package com.leyunone.cloudcloud.handler.protocol;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceCloudInfo;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.info.DeviceMappingInfo;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.StrategyFactory;
import com.leyunone.cloudcloud.mangaer.DeviceRelationManager;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.leyunone.cloudcloud.strategy.AbstractStrategyAutoRegisterComponent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
public abstract class AbstractStrategyProtocolHandler<R,P> extends AbstractStrategyAutoRegisterComponent implements CloudProtocolHandler<R>, InitializingBean {

    protected final DeviceRelationManager deviceRelationManager;

    private Class<P> pClass;

    protected AbstractStrategyProtocolHandler(StrategyFactory factory, DeviceRelationManager deviceManager) {
        super(factory);
        this.deviceRelationManager = deviceManager;
        Class<?> clazzz = getClass();
        Type generic = clazzz.getGenericSuperclass();
        if (generic instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) generic).getActualTypeArguments();
            //DO
            pClass = (Class<P>) actualTypeArguments[1];
        }
    }

    /**
     * 实际处理器执行三步动作：
     * 1、请求前的模型转换
     * 2、请求
     * 3、请求后的结果集转换
     *
     * @param request 请求
     * @param context 上下文
     * @return 已分支结果集
     */
    @Override
    public R action(String request, ActionContext context) {
        P requestParam = JSONObject.parseObject(request, pClass);
        R r = null;
        try {
            r = action1(requestParam, context);
        } catch (Exception e) {
            throw e;
        }
        return r;
    }

    protected abstract R action1(P p, ActionContext context);

    protected List<DeviceMappingInfo> convert(List<DeviceInfo> deviceInfos, String clientId, String userId, ThirdPartyCloudEnum cloud) {
        return deviceInfos
                .stream()
                .map(d -> new DeviceMappingInfo()
                        .setDeviceId(d.getDeviceId())
                        .setProductId(d.getProductId())
                        .setUserId(userId)
                        .setThirdPartyCloud(cloud)
                        .setClientId(clientId))
                .collect(Collectors.toList());
    }

    /**
     * @param deviceInfos 设备信息
     * @param userId             用户id
     * @param clientId           客户端id
     * @param thirdFunction      三方云的值关系绑定函数
     */
    protected void doRelationStore(List<DeviceInfo> deviceInfos, String userId, String clientId, ThirdPartyCloudEnum cloud, Consumer<DeviceMappingInfo> thirdFunction) {
        if (CollectionUtils.isEmpty(deviceInfos)) {
            deviceRelationManager.deleteDeviceMappingByUserIdAndCloudId(userId, cloud);
            //删除关系数据。
        } else {
            List<DeviceMappingInfo> deviceEntities = convert(deviceInfos, clientId, userId, cloud);
            deviceEntities = deviceEntities.stream()
                    .peek(thirdFunction)
                    .collect(Collectors.toList());
            deviceRelationManager.saveDeviceAndMapping(deviceEntities);
        }
    }


}
