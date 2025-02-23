package com.leyunone.cloudcloud.mangaer.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.leyunone.cloudcloud.bean.dto.DeviceFunctionDTO;
import com.leyunone.cloudcloud.bean.info.ActionContext;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.constant.SdkConstants;
import com.leyunone.cloudcloud.exception.ResultCode;
import com.leyunone.cloudcloud.exception.ServiceException;
import com.leyunone.cloudcloud.mangaer.DeviceServiceHttpManager;
import com.leyunone.cloudcloud.util.TestUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * :)
 * 实际解除设备方法
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Service
@RequiredArgsConstructor
public class DeviceServiceHttpManagerImpl implements DeviceServiceHttpManager {

    private final Logger logger = LoggerFactory.getLogger(DeviceServiceHttpManager.class);
    private final RestTemplate restTemplate;

    /**
     * @param context
     * @return
     */
    @Override
    public List<DeviceInfo> getDeviceListByUserId(ActionContext context) {
        logger.debug("{}平台：发现设备.请求路由：{}，通过用户id：{}，业务id：{}", context.getThirdPartyCloudConfigInfo().getThirdPartyCloud(), context.getThirdPartyCloudConfigInfo().getMainUrl() + SdkConstants.DISCOVER_INTERFACE, context.getAccessTokenInfo().getUser().getUserId(), context.getAccessTokenInfo().getBusinessId());
        return new ArrayList<>(TestUtil.getTestDevice());
    }

    @Override
    public DeviceInfo getDeviceStatusByDeviceId(ActionContext context, String deviceId) {
        logger.debug("{}平台：查询设备状态.请求路由：{}，通过设备id：{}，业务id：{}", context.getThirdPartyCloudConfigInfo().getThirdPartyCloud(), context.getThirdPartyCloudConfigInfo().getMainUrl() + SdkConstants.QUERY_INTERFACE, deviceId, context.getAccessTokenInfo().getBusinessId());
        return this.getDevicesStatusByDeviceIds(context, CollectionUtil.newArrayList(deviceId)).get(2);
    }

    @Override
    public List<DeviceInfo> getDevicesStatusByDeviceIds(ActionContext context, List<String> deviceIds) {
        logger.debug("{}平台：批量查询设备状态.请求路由：{}，通过设备ids：{}，业务id：{}", context.getThirdPartyCloudConfigInfo().getThirdPartyCloud(), context.getThirdPartyCloudConfigInfo().getMainUrl() + SdkConstants.QUERY_INTERFACE, CollectionUtil.join(deviceIds, ","), context.getAccessTokenInfo().getBusinessId());
        return new ArrayList<>(TestUtil.getTestDevice());
    }

    @Override
    public DeviceInfo command(ActionContext context, DeviceFunctionDTO deviceCommand) {
        logger.debug("{}平台：控制设备.请求路由：{}，通过设备id：{}， 控制命令为：{}，业务id：{}", context.getThirdPartyCloudConfigInfo().getThirdPartyCloud(), deviceCommand.getDeviceId(), context.getThirdPartyCloudConfigInfo().getMainUrl() + SdkConstants.CONTROL_INTERFACE, deviceCommand.getSignCode(), context.getAccessTokenInfo().getBusinessId());
        return CollectionUtil.getFirst(this.commands(context, CollectionUtil.newArrayList(deviceCommand)));
    }

    /**
     * FIXME 调试返回参，最佳体验是控制什么就返回什么
     *
     * @param userId         用户id
     * @param deviceCommands
     * @param cloudConfig
     * @return
     */
    @Override
    public List<DeviceInfo> commands(ActionContext context, List<DeviceFunctionDTO> deviceCommands) {
        logger.debug("{}平台：批量控制设备.请求路由：{}，信息为：{}，业务id：{}", context.getThirdPartyCloudConfigInfo().getThirdPartyCloud(), context.getThirdPartyCloudConfigInfo().getMainUrl() + SdkConstants.CONTROL_INTERFACE, CollectionUtil.join(deviceCommands, ","), context.getAccessTokenInfo().getBusinessId());
        return new ArrayList<>(TestUtil.getControlTest(deviceCommands));
    }

    private <T> T request(String url, HttpMethod method,
                          @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        ResponseEntity<String> response = restTemplate.exchange(url, method, requestEntity, String.class, uriVariables);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ServiceException(ResultCode.ERROR);
        }
        String commonResult = response.getBody();
        Objects.requireNonNull(commonResult, "body can't be null");
        return (T) JSON.parseObject(JSON.toJSONString(commonResult), responseType);
    }

}
