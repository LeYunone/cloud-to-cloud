package com.leyunone.cloudcloud.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.leyunone.cloudcloud.bean.CallBackParams;
import com.leyunone.cloudcloud.bean.ClientAccessTokenModel;
import com.leyunone.cloudcloud.bean.enums.YingshiAPIEnum;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.bean.third.yingshi.YingshiProtocolParam;
import com.leyunone.cloudcloud.bean.third.yingshi.YingshiTVAddressInfoResponse;
import com.leyunone.cloudcloud.bean.third.yingshi.YingshiTobeDevice;
import com.leyunone.cloudcloud.dao.DeviceRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceDO;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import com.leyunone.cloudcloud.mangaer.protocol.YingshiProtocolHttpManagerImpl;
import com.leyunone.cloudcloud.service.token.YingshiTokenHandler;
import com.leyunone.cloudcloud.util.ApplicationContextProvider;
import com.leyunone.cloudcloud.util.ClientTokenUtils;
import lombok.Data;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/8/30 13:57
 */
@Service
public class YingshiServiceImpl implements YingshiService {

    private final CacheManager cacheManager;
    private final DeviceRepository deviceRepository;
    private final YingshiTokenHandler yingshiTokenHandler;
    private final ThirdPartyConfigService thirdPartyCloudService;
    private final MyDeviceService myDeviceService;

    /**
     * 萤石云设备存储区
     */
    private Cache<String, YingshiTobeDevice> ysDeviceLocal = CacheBuilder.newBuilder()
            .maximumSize(800)
            .removalListener((RemovalListener<String, YingshiTobeDevice>) removalNotification -> {
                if (ObjectUtil.isNotNull(removalNotification.getValue()) && CollectionUtil.isNotEmpty(removalNotification.getValue().getDeviceIds())) {
                    /**
                     * 因为萤石云设备授权为第三方页面全权操作,因此会出现授权了,但是未在我方APP中确认的情况
                     * 因此在萤石云设备授权后,3分钟之后未确认的将取消授权
                     */
                    YingshiTobeDevice value = removalNotification.getValue();
                    this.cancelAuth(value.getBusinessId(), value.getAppId(), value.getClientId(), CollectionUtil.newArrayList(value.getDeviceIds()));
                }
            })
            .expireAfterWrite(1, TimeUnit.MINUTES).build();
    private static final String YS_DEVICES = "YS_DEVICES";

    public YingshiServiceImpl(CacheManager cacheManager, DeviceRepository deviceRepository, YingshiTokenHandler yingshiTokenHandler, ThirdPartyConfigService thirdPartyCloudService, MyDeviceService myDeviceService) {
        this.cacheManager = cacheManager;
        this.deviceRepository = deviceRepository;
        this.yingshiTokenHandler = yingshiTokenHandler;
        this.thirdPartyCloudService = thirdPartyCloudService;
        this.myDeviceService = myDeviceService;
    }


    @Override
    public void cleanCache() {
        ysDeviceLocal.cleanUp();
    }

    /**
     * 弹出该业务id下的设备
     *
     * @param businessId
     * @return
     */
    @Override
    public List<String> popBusinessDevices(String businessId, Integer appId, String clientId, boolean invalidate) {
        if (StringUtils.isBlank(businessId)) {
            return new ArrayList<>();
        }
        String deviceKey = generatorDeviceKey(businessId, appId, clientId);
        YingshiTobeDevice ifPresent = ysDeviceLocal.getIfPresent(deviceKey);
        List<String> result = null;
        if (ObjectUtil.isNotNull(ifPresent)) {
            result = new ArrayList<>(ifPresent.getDeviceIds());
            ifPresent.setDeviceIds(null);
            ysDeviceLocal.put(deviceKey, ifPresent);
        }
        if (invalidate) {
            /**
             * 由于获取设备id后,设备信息接口http时长不稳定
             * 所以获取阶段先扫掉空闲空间
             */
            ysDeviceLocal.invalidate(deviceKey);
        }
        return result;
    }

    @Override
    public void pushBusinessDevices(String businessId, Integer appId, String clientId, List<String> deviceIds) {
        if (StringUtils.isBlank(businessId)) {
            return;
        }
        String deviceKey = generatorDeviceKey(businessId, appId, clientId);
        YingshiTobeDevice ifPresent = ysDeviceLocal.getIfPresent(deviceKey);
        if (ObjectUtil.isNull(ifPresent)) {
            ifPresent = new YingshiTobeDevice();
            ifPresent.setAppId(appId);
            ifPresent.setBusinessId(businessId);
            ifPresent.setClientId(clientId);
            ifPresent.setDeviceIds(CollectionUtil.newHashSet(deviceIds));
        } else {
            ifPresent.getDeviceIds().addAll(deviceIds);
        }
        ysDeviceLocal.put(deviceKey, ifPresent);
    }

    /**
     * 设备入库前的待存储指令
     *
     * @param deviceIds      萤石设备id
     * @param callBackParams 回调参数
     */
    @Override
    public void deviceInStoragePack(List<String> deviceIds, CallBackParams callBackParams) {
        if (CollectionUtil.isEmpty(deviceIds)) {
            return;
        }
        String key = generatorDeviceKey(callBackParams.getBusinessId(), callBackParams.getAppId(), callBackParams.getClientId());
        Optional<YingshiTobeDevice> optional = Optional.ofNullable(ysDeviceLocal.getIfPresent(key));
        optional.ifPresent((ys) -> {
            ys.getDeviceIds().addAll(CollectionUtil.newHashSet(deviceIds));
        });
        YingshiTobeDevice yingshiTobeDevice = optional.orElseGet(() -> {
            YingshiTobeDevice newTobe = new YingshiTobeDevice();
            newTobe.setClientId(callBackParams.getClientId());
            newTobe.setAppId(callBackParams.getAppId());
            newTobe.setBusinessId(callBackParams.getBusinessId());
            newTobe.setDeviceIds(CollectionUtil.newHashSet(deviceIds));
            return newTobe;
        });
        ysDeviceLocal.put(key, yingshiTobeDevice);

        /**
         * 使用缓存入库兜底 兜底时间为5分钟内一定要恢复应用 极限时间为 5+3分钟授权设备才能回源到智家中
         */
        cacheManager.addListValue(key, deviceIds, 5L, TimeUnit.MINUTES);
        cacheManager.addListValue(YS_DEVICES, key, 5L, TimeUnit.MINUTES);
    }

    /**
     * 在萤石云页面上取消设备授权
     *
     * @param deviceIds      萤石设备id
     * @param callBackParams 回调参数
     */
    @Override
    public void cancelDevice(List<String> deviceIds, CallBackParams callBackParams) {
        if (CollectionUtil.isEmpty(deviceIds)) {
            return;
        }
        String key = generatorDeviceKey(callBackParams.getBusinessId(), callBackParams.getAppId(), callBackParams.getClientId());
        YingshiTobeDevice yingshiTobeDevice = ysDeviceLocal.getIfPresent(key);
        if (ObjectUtil.isNotNull(yingshiTobeDevice)) {
            //删除缓存中已勾选设备
            HashSet<String> deviceIdsSet = CollectionUtil.newHashSet(deviceIds);
            yingshiTobeDevice.getDeviceIds().removeIf(deviceIdsSet::contains);
            ysDeviceLocal.put(key, yingshiTobeDevice);
        }
        //通知app应用进行解绑操作
        List<DeviceDO> deviceDOS = deviceRepository.selectByThirdDeviceIdsClient(callBackParams.getClientId(), deviceIds);
        if (CollectionUtil.isNotEmpty(deviceDOS)) {
            /**
             * 解绑流程:
             * 1\先通知对接应用
             * 2\对接应用下发解绑后,云云平台进行解绑
             */
            myDeviceService.deleteDevice(deviceDOS.stream().map(DeviceDO::getDeviceId).collect(Collectors.toList()));
        }
    }

    /**
     * 取消本次链路中已授权设备
     *
     * @param deviceIds
     */
    @Override
    public void cancelAuth(String businessId, Integer appId, String clientId, List<String> deviceIds) {
        String tokenKey = ClientTokenUtils.generateClientAccessToken(clientId, businessId, appId);
        String token = cacheManager.getData(tokenKey, String.class);
        if (StringUtils.isBlank(token)) {
            return;
        }
//        AssertUtil.isFalse(StringUtils.isBlank(token), TocResultCode.UNAUTHORIZED);
        List<String> deviceSerials = deviceIds.stream().map(deviceId -> deviceId + ":" + this.getCameraNo(deviceId)).collect(Collectors.toList());
        CancelDevice cancelDevice = new CancelDevice();
        cancelDevice.setAccessToken(token);
        cancelDevice.setDeviceSerials(CollectionUtil.join(deviceSerials, ","));

        ThirdPartyCloudConfigInfo config = thirdPartyCloudService.getConfig(clientId);
        if (ObjectUtil.isNull(config)) {
            return;
        }
        HttpUtil.createPost(config.getWebServiceUrl() + YingshiAPIEnum.CANCEL_AUTH.getUrl())
                .form(BeanUtil.beanToMap(cancelDevice))
                .execute().body();
    }

    @Override
    public void cancelAuth(List<Long> deviceIds) {
        List<DeviceDO> deviceDOS = deviceRepository.selectByIds(deviceIds);
        if (CollectionUtil.isEmpty(deviceDOS)) {
            return;
        }
        DeviceDO deviceDO = CollectionUtil.getFirst(deviceDOS);
        String token = cacheManager.getData(deviceDO.getTokenKey(), String.class);
        if (StringUtils.isBlank(token)) {
            return;
        }
        String additional = deviceDO.getAdditional();

        CancelDevice cancelDevice = new CancelDevice();
        cancelDevice.setAccessToken(token);
        cancelDevice.setDeviceSerials(CollectionUtil.join(deviceDOS.stream().map(device -> {
            JSONObject jsonObject = JSONObject.parseObject(additional);
            return device.getThirdDeviceId() + ":" + jsonObject.getString("channel_no");
        }).collect(Collectors.toList()), ","));

        ThirdPartyCloudConfigInfo config = thirdPartyCloudService.getConfig(deviceDO.getClientId());
        if (ObjectUtil.isNull(config)) {
            return;
        }
        HttpUtil.createPost(config.getWebServiceUrl() + YingshiAPIEnum.CANCEL_AUTH.getUrl())
                .form(BeanUtil.beanToMap(cancelDevice))
                .execute().body();
    }

    @Data
    public static class CancelDevice {

        private String accessToken;

        private String deviceSerials;
    }

    /**
     * 回调loading一次托管令牌
     *
     * @param callBackParams
     */
    @Override
    public void loadToken(CallBackParams callBackParams) {
        String tokenKey = ClientTokenUtils.generateClientAccessToken(callBackParams.getClientId(), callBackParams.getBusinessId(), callBackParams.getAppId());
        String token = cacheManager.getData(tokenKey, String.class);

        if (StringUtils.isBlank(token)) {
            ClientAccessTokenModel clientAccessTokenModel = this.yingshiToken(tokenKey, callBackParams.getClientId());
        }
    }

    @Override
    public void saveCameraNo(List<String> deviceSerials) {
        for (String deviceSerial : deviceSerials) {
            cacheManager.addData(YS_DEVICES + deviceSerial.split(":")[0], deviceSerial.split(":")[1], 30L, TimeUnit.MINUTES);
        }
    }

    @Override
    public int getCameraNo(String deviceId) {
        Integer data = cacheManager.getData(YS_DEVICES + deviceId, int.class);
        data = ObjectUtil.isNull(data) ? 1 : data;
        return data;
    }

    /**
     * 同上{@link #loadToken(CallBackParams)} 根据设备id获取萤石云token
     *
     * @param deviceId 我方设备id
     * @return
     */
    @Override
    public String getDeviceToken(String deviceId) {
        DeviceDO deviceDO = deviceRepository.selectById(deviceId);
        if (ObjectUtil.isNull(deviceDO)) {
            return null;
        }
        String tokenKey = deviceDO.getTokenKey();
        String token = cacheManager.getData(tokenKey, String.class);

        if (StringUtils.isBlank(token)) {
            ClientAccessTokenModel tokenTemp = this.yingshiToken(tokenKey, deviceDO.getClientId());
        }
        return token;
    }

    private ClientAccessTokenModel yingshiToken(String tokenKey, String clientId) {
        ClientAccessTokenModel tokenTemp = Optional.ofNullable(YingshiTokenHandler.TOKEN_TEMP)
                .orElseGet(() -> {
                    ClientAccessTokenModel clientAccessTokenModel = yingshiTokenHandler.get(clientId);
                    if (ObjectUtil.isNull(clientAccessTokenModel)) {
                        throw new RuntimeException("accessToken is empty");
                    }
                    return clientAccessTokenModel;
                });
        yingshiTokenHandler.deliveryLoop(clientId, tokenTemp.getExpiresIn());
        cacheManager.addData(tokenKey, tokenTemp.getAccessToken(), tokenTemp.getExpiresIn(), TimeUnit.MILLISECONDS);
        return tokenTemp;
    }

    /**
     * 萤石云设备获取监控地址url
     * 到这一步一定一定是已经将设备给到并且注册进我方
     *
     * @param myDeviceId
     * @return
     */
    @Override
    public YingshiTVAddressInfoResponse getTVURl(String myDeviceId) {
        DeviceDO deviceDO = deviceRepository.selectById(myDeviceId);
        if (ObjectUtil.isNull(deviceDO)) {
            return null;
        }
        //萤石令牌
        ClientAccessTokenModel clientAccessTokenModel = this.yingshiToken(deviceDO.getTokenKey(), deviceDO.getClientId());
        if (ObjectUtil.isNull(clientAccessTokenModel)) {
            throw new RuntimeException("accessToken is empty");
        }
        YingshiProtocolParam yingshiProtocolParam = new YingshiProtocolParam();
        yingshiProtocolParam.setAccessToken(clientAccessTokenModel.getAccessToken());
        yingshiProtocolParam.setDeviceSerial(deviceDO.getThirdDeviceId());
        ThirdPartyCloudConfigInfo config = thirdPartyCloudService.getConfig(deviceDO.getClientId());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        BeanUtil.beanToMap(yingshiProtocolParam).forEach((key, value) -> {
            if (ObjectUtil.isNotNull(value)) {
                body.add(key, value);
            }
        });

        YingshiTVAddressInfoResponse yingshiTVAddressInfoResponse = ApplicationContextProvider.getBean(YingshiProtocolHttpManagerImpl.class)
                .http(config.getWebServiceUrl() + YingshiAPIEnum.GET_TV_URL.getUrl(), HttpMethod.POST, new HttpEntity<>(body
                        , httpHeaders), YingshiTVAddressInfoResponse.class, new JSONObject());
        return yingshiTVAddressInfoResponse;
    }

    /**
     * 初始化本地内存 见 {@link #deviceInStoragePack} 缓存兜底
     */
    @PostConstruct
    public void initYsDeviceLocal() {
        Set<Object> keys = cacheManager.getSetValue(YS_DEVICES);
        if (CollectionUtil.isEmpty(keys)) {
            return;
        }
        //恢复本地缓存
        for (Object key : keys) {
            Set<Object> ysDeviceIds = cacheManager.getSetValue(key.toString());
            if (CollectionUtil.isEmpty(ysDeviceIds)) {
                return;
            }
            String[] split = key.toString().split(":");
            YingshiTobeDevice yingshiTobeDevice = new YingshiTobeDevice();
            yingshiTobeDevice.setBusinessId(split[0]);
            yingshiTobeDevice.setAppId(Integer.valueOf(split[1]));
            yingshiTobeDevice.setClientId(split[2]);
            yingshiTobeDevice.setDeviceIds(ysDeviceIds.stream().map(Object::toString).collect(Collectors.toSet()));

            ysDeviceLocal.put(key.toString(), yingshiTobeDevice);
        }
    }

    public String generatorDeviceKey(String businessId, Integer appId, String clientId) {
        return String.join(":", YS_DEVICES, businessId, appId.toString(), clientId);
    }

}
