package com.leyunone.cloudcloud.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.cloudcloud.dao.ThirdPartyClientRepository;
import com.leyunone.cloudcloud.dao.entity.ThirdPartyClientDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.web.bean.dto.ThirdClientConfigDTO;
import com.leyunone.cloudcloud.web.bean.vo.ThirdClientConfigVO;
import com.leyunone.cloudcloud.web.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * :)
 *
 * @Author LeyunOne
 * @Date 2024/4/2 16:44
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ThirdPartyClientRepository thirdPartyClientRepository;

    public void thirdClientConfigSave(ThirdClientConfigDTO dto) {
        ThirdPartyClientDO partyClientDO = thirdPartyClientRepository.selectByCloud(dto.getThirdPartyCloud());
        if (ObjectUtil.isNotNull(partyClientDO) && !partyClientDO.getClientId().equals(dto.getClientId())) {
            thirdPartyClientRepository.deleteByCloud(dto.getThirdPartyCloud());
        }

        ThirdPartyClientDO thirdPartyClientDO = new ThirdPartyClientDO();
        thirdPartyClientDO.setClientId(dto.getClientId());
        thirdPartyClientDO.setClientSecret(dto.getClientSecret());
        thirdPartyClientDO.setMainUrl(dto.getMainUrl());
        thirdPartyClientDO.setIcon(dto.getIcon());
        thirdPartyClientDO.setAdditionalInformation(dto.getAdditionalInformation());
        thirdPartyClientDO.setAppSecret("b998qT663T6J9KYAUEUvsUBEE6j8Mnec");
        thirdPartyClientDO.setReportUrl(dto.getReportUrl());
        thirdPartyClientDO.setThirdPartyCloud(dto.getThirdPartyCloud());
        thirdPartyClientDO.setThirdClientId(dto.getThirdClientId());
        thirdPartyClientDO.setThirdClientSecret(dto.getThirdClientSecret());
        thirdPartyClientRepository.insertOrUpdate(thirdPartyClientDO);
    }

    public ThirdClientConfigVO clientConfigDetail(ThirdPartyCloudEnum cloud) {
        ThirdPartyClientDO thirdPartyClientDO = thirdPartyClientRepository.selectByCloud(cloud);
        ThirdClientConfigVO vo = new ThirdClientConfigVO();
        BeanUtil.copyProperties(thirdPartyClientDO, vo);
        return vo;
    }
}
