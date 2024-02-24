package com.leyunone.cloudcloud.service;

import com.leyunone.cloudcloud.bean.enums.ActionTypeEnum;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * :)
 * 三方配置加载
 *
 * @Author LeYunone
 * @Date 2024/1/31 9:45
 */
public interface ThirdPartyLoadConfigService extends InitializingBean {

    List<String> getKeys(ThirdPartyCloudEnum cloud, ActionTypeEnum actionType);
}
