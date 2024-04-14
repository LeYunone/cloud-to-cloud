package com.leyunone.cloudcloud.service;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.cloudcloud.bean.enums.ActionTypeEnum;
import com.leyunone.cloudcloud.dao.ThirdPartyActionRepository;
import com.leyunone.cloudcloud.dao.entity.ThirdPartyActionDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 * 技能标识配置表
 *
 * @Author LeYunone
 * @Date 2024/1/31 9:46
 */
@Service
public class ThirdPartyLoadConfigServiceImpl implements ThirdPartyLoadConfigService {

    private final ThirdPartyActionRepository thirdPartyActionRepository;

    public Map<ThirdPartyCloudEnum, Map<Integer, List<String>>> actionKeys = new HashMap<>();

    public ThirdPartyLoadConfigServiceImpl(ThirdPartyActionRepository thirdPartyActionRepository) {
        this.thirdPartyActionRepository = thirdPartyActionRepository;
    }

    /**
     * 得到各平台技能api标识
     */
    @Override
    public void afterPropertiesSet() {
        List<ThirdPartyActionDO> thirdPartyActionDOS = thirdPartyActionRepository.selectByCon(null);
        Map<ThirdPartyCloudEnum, List<ThirdPartyActionDO>> thirdActions = thirdPartyActionDOS.stream().collect(Collectors.groupingBy(ThirdPartyActionDO::getThirdPartyCloud));
        thirdActions.keySet().forEach(cloud -> actionKeys.put(cloud,
                thirdActions.get(cloud).stream()
                        .collect(Collectors.groupingBy(ThirdPartyActionDO::getActionType,
                                Collectors.mapping(ThirdPartyActionDO::getActionIdentify
                                        , Collectors.toList()
                                )
                                )
                        )
        ));
    }

    @Override
    public List<String> getKeys(ThirdPartyCloudEnum cloud, ActionTypeEnum actionType) {
        Map<Integer, List<String>> cloudActions = actionKeys.get(cloud);
        List<String> keys = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(cloudActions) && cloudActions.containsKey(actionType.getType())) {
            keys = cloudActions.get(actionType.getType());
        }
        return keys;
    }
}
