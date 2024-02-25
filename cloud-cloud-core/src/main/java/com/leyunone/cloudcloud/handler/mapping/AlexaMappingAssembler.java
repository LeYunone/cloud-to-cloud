package com.leyunone.cloudcloud.handler.mapping;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.cloudcloud.bean.mapping.AlexaFunctionMapping;
import com.leyunone.cloudcloud.bean.mapping.AlexaProductMapping;
import com.leyunone.cloudcloud.bean.mapping.StatusMapping;
import com.leyunone.cloudcloud.constant.AlexaActionConstants;
import com.leyunone.cloudcloud.dao.ActionMappingRepository;
import com.leyunone.cloudcloud.dao.DeviceCapabilityRepository;
import com.leyunone.cloudcloud.dao.FunctionMappingRepository;
import com.leyunone.cloudcloud.dao.ProductTypeMappingRepository;
import com.leyunone.cloudcloud.dao.entity.ActionMappingDO;
import com.leyunone.cloudcloud.dao.entity.DeviceCapabilityDO;
import com.leyunone.cloudcloud.dao.entity.FunctionMappingDO;
import com.leyunone.cloudcloud.dao.entity.ProductTypeMappingDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.handler.factory.MappingAssemblerFactory;
import com.leyunone.cloudcloud.mangaer.CacheManager;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/31 11:28
 */
@Service
public class AlexaMappingAssembler extends AbstractStrategyMappingAssembler<AlexaProductMapping> {

    private final FunctionMappingRepository functionMappingRepository;
    private final ActionMappingRepository actionMappingRepository;
    private final ProductTypeMappingRepository productTypeMappingRepository;
    private final DeviceCapabilityRepository deviceCapabilityRepository;

    protected AlexaMappingAssembler(MappingAssemblerFactory factory, FunctionMappingRepository functionMappingRepository, CacheManager cacheManager, FunctionMappingRepository functionMappingRepository1, ActionMappingRepository actionMappingRepository, ProductTypeMappingRepository productTypeMappingRepository, DeviceCapabilityRepository deviceCapabilityRepository) {
        super(factory, functionMappingRepository, cacheManager);
        this.functionMappingRepository = functionMappingRepository1;
        this.actionMappingRepository = actionMappingRepository;
        this.productTypeMappingRepository = productTypeMappingRepository;
        this.deviceCapabilityRepository = deviceCapabilityRepository;
    }

    @Override
    public String getKey() {
        return ThirdPartyCloudEnum.ALEXA.name();
    }

    /**
     * Alexa需进行如下映射：
     * 1、属性映射
     * 2、接口能力映射
     * 3、能力语义配置
     *
     * @param pids
     * @return
     */
    @Override
    protected List<AlexaProductMapping> dataGet(List<String> pids) {
        List<FunctionMappingDO> functionMappingDOS = functionMappingRepository.selectByProductIdsAndThirdPartyCloud(pids, ThirdPartyCloudEnum.ALEXA.name());
        List<ActionMappingDO> actionMappingDOS = actionMappingRepository.selectByProductIds(pids, ThirdPartyCloudEnum.ALEXA.name());
        List<ProductTypeMappingDO> productTypeMappingDOS = productTypeMappingRepository.selectByProductIds(pids, ThirdPartyCloudEnum.ALEXA.name());
        List<DeviceCapabilityDO> deviceCapabilityDOS = deviceCapabilityRepository.selectByCloud(ThirdPartyCloudEnum.ALEXA);

        Map<Integer, DeviceCapabilityDO> capabilityMap = CollectionFunctionUtils.mapTo(deviceCapabilityDOS, DeviceCapabilityDO::getId);

        Map<String, List<FunctionMappingDO>> functionMappingMap = CollectionFunctionUtils.groupTo(functionMappingDOS, FunctionMappingDO::getProductId);
        Map<String, List<ActionMappingDO>> actionMappingMap = CollectionFunctionUtils.groupTo(actionMappingDOS, ActionMappingDO::getProductId);
        Map<String, List<ProductTypeMappingDO>> productTypeMappingMap = CollectionFunctionUtils.groupTo(productTypeMappingDOS, ProductTypeMappingDO::getProductId);

        /**
         * 必须配置功能映射值
         */
        return pids.stream().map(pid -> {
            List<FunctionMappingDO> functionMappings = functionMappingMap.get(pid);
            List<ActionMappingDO> actionMappings = actionMappingMap.get(pid);
            /**
             * 一定需要一个属性映射
             */
            if (CollectionUtil.isEmpty(functionMappings)) {
                return null;
            }

            List<ProductTypeMappingDO> productTypeMappings = productTypeMappingMap.get(pid);
            AlexaProductMapping alexaProductMapping = new AlexaProductMapping();
            alexaProductMapping.setProductId(pid);
            alexaProductMapping.setAlexaFunctionMappings(this.convert(functionMappings, capabilityMap));
            alexaProductMapping.setCapabilityList(this.convertAction(alexaProductMapping.getAlexaFunctionMappings(), actionMappings, capabilityMap));
            alexaProductMapping.setThirdPartyCloud(ThirdPartyCloudEnum.ALEXA);
            alexaProductMapping.setThirdProductIds(CollectionUtil.isNotEmpty(productTypeMappings)
                    ? productTypeMappings.stream().map(ProductTypeMappingDO::getThirdProductId).collect(Collectors.toList()) : null);
            return alexaProductMapping;
        }).filter(ObjectUtil::isNotNull).collect(Collectors.toList());
    }

    /**
     * Alexa 映射配置规则：
     *
     * @param functionMappings functionMapping决定：1、技能表及语义配置 2、我方云映射对方属性值
     * @param actionMappings   actionMapping决定： 1、对方云映射我方属性值
     * @param capabilityMap    capabilityMap决定： 语义配置
     * @return
     */
    private List<AlexaProductMapping.Capability> convertAction(List<AlexaFunctionMapping> functionMappings, List<ActionMappingDO> actionMappings, Map<Integer, DeviceCapabilityDO> capabilityMap) {
        if (CollectionUtil.isEmpty(functionMappings)) return new ArrayList<>();

        Map<String, List<AlexaFunctionMapping>> functionMap = CollectionFunctionUtils.groupTo(functionMappings, AlexaFunctionMapping::getInterfaceStr);
        /**
         * 我方一个code-对方多个code
         */
        Map<String, List<ActionMappingDO>> actionMaps = CollectionFunctionUtils.groupTo(actionMappings, ActionMappingDO::getSignCode);


        return functionMap.keySet().stream().map(key -> {
            /**
             * 如均衡控制器 存在一个操作支持多个属性
             */
            List<AlexaFunctionMapping> functionMapping = functionMap.get(key);
            AlexaFunctionMapping function = CollectionUtil.getFirst(functionMapping);

            AlexaProductMapping.Capability capability = new AlexaProductMapping.Capability();
            capability.setThirdActionCode(function.getInterfaceStr());
            capability.setSignCode(function.getSignCode());
            capability.setFunctionId(function.getFunctionId());

            capability.setSupportAttr(functionMapping.stream().map(AlexaFunctionMapping::getThirdSignCode).collect(Collectors.toList()));
            //TODO 技能的三方值 而非属性
            if (actionMaps.containsKey(function.getSignCode())) {

                List<AlexaProductMapping.CapabilityMapping> capabilityMappings = actionMaps.get(function.getSignCode()).stream().map(actionMappingDO -> AlexaProductMapping.CapabilityMapping.builder()
                        .defaultValue(actionMappingDO.getDefaultValue())
                        .valueMapping(StrUtil.isNotBlank(actionMappingDO.getValueMapping()) ? JSONObject.parseObject(actionMappingDO.getValueMapping()).getInnerMap() : null)
                        .operation(actionMappingDO.getOperation())
                        .thirdSignCode(actionMappingDO.getThirdPartyCode())
                        .valueOf(actionMappingDO.getValueOf())
                        .build()).collect(Collectors.toList());
                capability.setCapabilityMapping(CollectionFunctionUtils.mapTo(capabilityMappings, AlexaProductMapping.CapabilityMapping::getThirdSignCode));
            }

            DeviceCapabilityDO deviceCapabilityDO = capabilityMap.get(function.getCapabilityConfigId());
            if (ObjectUtil.isNotNull(deviceCapabilityDO)) {
                capability.setCapabilityResources(JSONObject.parseObject(deviceCapabilityDO.getCapabilitySemantics()));
                capability.setConfiguration(JSONObject.parseObject(deviceCapabilityDO.getCapabilityConfiguration()));
                capability.setInstance(deviceCapabilityDO.getInstanceName());
                capability.setSemantics(JSONObject.parseObject(deviceCapabilityDO.getValueSemantics()));
            }
            return capability;
        }).collect(Collectors.toList());
    }

    protected List<AlexaFunctionMapping> convert(List<FunctionMappingDO> functionMappingDos, Map<Integer, DeviceCapabilityDO> capabilityMap) {
        if (CollectionUtil.isEmpty(functionMappingDos)) {
            return new ArrayList<>();
        }
        return functionMappingDos
                .stream()
                .map(fm -> {
                    StatusMapping functionMapping = StatusMapping.Converter.INSTANCE.convert(fm);
                    AlexaFunctionMapping alexaFunctionMapping = new AlexaFunctionMapping();
                    BeanUtil.copyProperties(functionMapping, alexaFunctionMapping);
                    String valueMapping = fm.getValueMapping();
                    Map<String, Object> map = new HashMap<>();
                    if (StrUtil.isNotBlank(valueMapping)) {
                        JSONObject jsonObject = JSON.parseObject(valueMapping);
                        map = jsonObject.getInnerMap();
                    }
                    //取出thirdPartyCode中的接口信息
                    String[] thirdCodes = functionMapping.getThirdSignCode().split("_");
                    alexaFunctionMapping.setThirdSignCode(thirdCodes[0]);
                    alexaFunctionMapping.setInterfaceStr(thirdCodes[1]);
                    alexaFunctionMapping.setCapabilityConfigId(fm.getCapabilityConfigId());

                    alexaFunctionMapping.setValueMapping(map);
                    if (capabilityMap.containsKey(fm.getCapabilityConfigId())) {
                        alexaFunctionMapping.setInstance(capabilityMap.get(fm.getCapabilityConfigId()).getInstanceName());
                    }
                    return alexaFunctionMapping;
                })
                .collect(Collectors.toList());
    }
}
