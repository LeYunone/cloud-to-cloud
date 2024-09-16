package com.leyunone.cloudcloud.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.dao.ThirdPartyClientRepository;
import com.leyunone.cloudcloud.dao.entity.ThirdPartyClientDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import org.springframework.stereotype.Service;
import org.springframework.validation.ValidationUtils;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/18
 */
@Service
public class ThirdPartyConfigServiceImpl implements ThirdPartyConfigService {

    private final ThirdPartyClientRepository thirdPartyClientRepository;

    public ThirdPartyConfigServiceImpl(ThirdPartyClientRepository thirdPartyClientRepository) {
        this.thirdPartyClientRepository = thirdPartyClientRepository;
    }


    @Override
    public ThirdPartyCloudConfigInfo getConfig(String clientId) {
        ThirdPartyClientDO thirdPartyClientDO = thirdPartyClientRepository.selectByClientId(clientId);
        ThirdPartyCloudConfigInfo thirdPartyCloudConfigInfo = new ThirdPartyCloudConfigInfo();
        if (ObjectUtil.isNotNull(thirdPartyClientDO)) {
            BeanUtil.copyProperties(thirdPartyClientDO, thirdPartyCloudConfigInfo);
        }
        return thirdPartyCloudConfigInfo;
    }

    @Override
    public List<ThirdPartyCloudConfigInfo> getConfig(List<String> clientIds) {
        List<ThirdPartyClientDO> thirdPartyClientDOS = thirdPartyClientRepository.selectByIds(clientIds);
        //查询数据并做转换
        return JSONObject.parseArray(JSONObject.toJSONString(thirdPartyClientDOS), ThirdPartyCloudConfigInfo.class);
    }
}
