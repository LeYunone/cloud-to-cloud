package com.leyunone.cloudcloud.bean.enums;


import com.leyunone.cloudcloud.bean.third.yingshi.YingshiCommonInfo;
import com.leyunone.cloudcloud.bean.third.yingshi.YingshiVolumeInfoResponse;

/**
 * :)
 * 萤石云接口
 *
 * @Author LeYunone
 * @Date 2024/8/22 16:33
 */
public enum YingshiAPIEnum {

    /**
     * 萤石接口前缀 https://open.ys7.com/api/
     */
    //获取设备信息
    GET_DEVICE("lapp/device/info", false, null),
    /**
     * 音量接口
     * GET-查询
     * POST-修改
     */
    VOLUME("v3/device/talkSpeakerVolume", false, YingshiVolumeInfoResponse.class),

    /**
     * 镜像翻转控制
     */
    MIRROR("lapp/device/ptz/mirror", false, null),

    /**
     * 获取托管token
     */
    GET_ACCESS_TOKEN("lapp/trust/device/v2/token/get", false, null),

    CANCEL_AUTH("lapp/trust/cancel", false, null),

    GET_TV_URL("lapp/v2/live/address/get", false, null),

    CAPACITY("lapp/device/capacity", false, null);

    YingshiAPIEnum(String url, boolean sync, Class<? extends YingshiCommonInfo> responseClass) {
        this.url = url;
        this.sync = sync;
        this.responseClass = responseClass;
    }

    private String url;

    private boolean sync;

    private Class<? extends YingshiCommonInfo> responseClass;

    public Class<? extends YingshiCommonInfo> getResponseClass() {
        return responseClass;
    }

    public String getUrl() {
        return url;
    }

    public boolean isSync() {
        return sync;
    }
}
