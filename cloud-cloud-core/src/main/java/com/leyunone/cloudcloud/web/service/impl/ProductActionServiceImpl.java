package com.leyunone.cloudcloud.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.bean.enums.ConvertFunctionEnum;
import com.leyunone.cloudcloud.dao.ActionMappingRepository;
import com.leyunone.cloudcloud.dao.ProductTypeMappingRepository;
import com.leyunone.cloudcloud.dao.entity.ActionMappingDO;
import com.leyunone.cloudcloud.dao.entity.ProductTypeMappingDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import com.leyunone.cloudcloud.util.DeepSearchUtils;
import com.leyunone.cloudcloud.web.bean.dto.ProductActionDTO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import com.leyunone.cloudcloud.web.bean.vo.ProductActionMappingVO;
import com.leyunone.cloudcloud.web.bean.vo.ProductActionVO;
import com.leyunone.cloudcloud.web.bean.vo.ProductFunctionVO;
import com.leyunone.cloudcloud.web.service.ProductActionService;
import com.leyunone.cloudcloud.util.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/26 15:29
 */
@Service
public class ProductActionServiceImpl implements ProductActionService {

    @Autowired
    private ActionMappingRepository actionMappingRepository;
    @Autowired
    private ProductTypeMappingRepository productTypeMappingRepository;

    @Override
    public ProductActionMappingVO getDetail(String productId, ThirdPartyCloudEnum cloud) {
        List<ProductTypeMappingDO> productTypeMappingDOS = productTypeMappingRepository.selectByProductId(productId, cloud.name());
        List<ActionMappingDO> actionMappingDOS = actionMappingRepository.selectByProductId(productId, cloud.name());

        ProductActionMappingVO productActionMappingVO = new ProductActionMappingVO();
        productActionMappingVO.setProductId(productId);
        productActionMappingVO.setProductActions(actionMappingDOS.stream().map(actionMapping -> {
            ProductActionMappingVO.ActionMapping function = new ProductActionMappingVO.ActionMapping();
            function.setProductId(actionMapping.getProductId());
            function.setThirdPartyCloud(actionMapping.getThirdPartyCloud());
            function.setId(actionMapping.getId());
            function.setSignCode(actionMapping.getSignCode());
            function.setThirdSignCode(actionMapping.getThirdSignCode());
            function.setThirdActionCode(actionMapping.getThirdActionCode());
            function.setValueOf(actionMapping.getValueOf());
            function.setConvertFunction(actionMapping.getConvertFunction());
            if (StrUtil.isNotBlank(actionMapping.getValueMapping())) {
                Map<String, Object> innerMap = JSON.parseObject(actionMapping.getValueMapping()).getInnerMap();
                function.setValueMapping(innerMap.keySet().stream().map(key -> {
                    ProductActionMappingVO.ValueMap valueMap = new ProductActionMappingVO.ValueMap();
                    valueMap.setKey(key);
                    valueMap.setValue(innerMap.get(key));
                    return valueMap;
                }).collect(Collectors.toList()));
            } else {
                function.setValueMapping(new ArrayList<>());
            }
            return function;
        }).collect(Collectors.toList()));
        productActionMappingVO.setThirdPartyCloud(cloud);
        productActionMappingVO.setThirdProductIds(productTypeMappingDOS.stream().map(ProductTypeMappingDO::getThirdProductId).collect(Collectors.toList()));
        return productActionMappingVO;
    }

    @Override
    public Page<ProductActionVO> listByCon(ProductTypeQuery query) {
        Page<ActionMappingDO> actionMappingDOPage = actionMappingRepository.selectPageOrder(query);
        List<String> pids = actionMappingDOPage.getRecords().stream().map(ActionMappingDO::getProductId).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(pids)) return new Page<>();
        List<ProductTypeMappingDO> productTypeMappingDOS = productTypeMappingRepository.selectByProductIds(pids, query.getThirdPartyCloud().name());

        List<ActionMappingDO> actionMappingDOS = actionMappingRepository.selectByProductIds(pids, query.getThirdPartyCloud().name());
        Map<String, List<ActionMappingDO>> functionMap = CollectionFunctionUtils.groupTo(actionMappingDOS, ActionMappingDO::getProductId);
        Map<String, List<ProductTypeMappingDO>> productMap = CollectionFunctionUtils.groupTo(productTypeMappingDOS, ProductTypeMappingDO::getProductId);

        Page<ProductActionVO> page = new Page<>();
        page.setTotal(actionMappingDOPage.getTotal());
        page.setRecords(actionMappingDOPage.getRecords().stream().map(a -> {
            ProductActionVO productActionVO = new ProductActionVO();
            List<ActionMappingDO> fs = functionMap.get(a.getProductId());
            List<ProductTypeMappingDO> ps = productMap.get(a.getProductId());
            productActionVO.setProductId(a.getProductId());
            productActionVO.setUpdateTime(a.getUpdateTime());
            productActionVO.setThirdPartyCloud(query.getThirdPartyCloud());
            if (CollectionUtil.isNotEmpty(fs)) {
                productActionVO.setSignCodes(fs.stream().map(ActionMappingDO::getSignCode).collect(Collectors.toSet()));
                productActionVO.setThirdCodes(fs.stream().map(m -> {
                    ProductActionVO.Mapping pm = new ProductActionVO.Mapping();
                    pm.setThirdActionCode(m.getThirdActionCode());
                    pm.setThirdSignCode(m.getThirdSignCode());
                    return pm;
                }).collect(Collectors.toList()));
            }
            if (CollectionUtil.isNotEmpty(ps)) {
                productActionVO.setThirdProductIds(ps.stream().map(ProductTypeMappingDO::getThirdProductId).collect(Collectors.toList()));
            }
            return productActionVO;
        }).collect(Collectors.toList()));
        return page;
    }


    @Override
    public void save(ProductActionDTO dto) {
        List<ProductActionDTO.FunctionMapping> actionMappings = dto.getProductActions();
        List<ActionMappingDO> newFunctionMapping = actionMappings.stream().map(f -> {
            ActionMappingDO actionMappingDO = new ActionMappingDO();
            actionMappingDO.setId(f.getId());
            actionMappingDO.setProductId(dto.getProductId());
            actionMappingDO.setSignCode(f.getSignCode());
            try {
                //找到最终key
                actionMappingDO.setThirdSignCode(DeepSearchUtils.findDeepestKey(JSONObject.parseObject(f.getThirdSignCode())));
            } catch (Exception e) {
                actionMappingDO.setThirdSignCode(f.getThirdSignCode());
            }
            actionMappingDO.setThirdActionCode(f.getThirdActionCode());
            actionMappingDO.setThirdPartyCloud(dto.getThirdPartyCloud());
            actionMappingDO.setValueOf(f.isValueOf());
            if (CollectionUtil.isNotEmpty(f.getValueMapping())) {
                Map<String, Object> o = new HashMap<>();
                f.getValueMapping().forEach(fm -> {
                    o.put(fm.getKey(), fm.getValue());
                });
                actionMappingDO.setValueMapping(JSONObject.toJSONString(o));
            }
//            functionMappingDO.setLegalValue();
            if (StrUtil.isNotBlank(f.getConvertFunction())) {
                actionMappingDO.setConvertFunction(ConvertFunctionEnum.valueOf(f.getConvertFunction()));
            }

            return actionMappingDO;
        }).collect(Collectors.toList());
        List<ActionMappingDO> actionMappingDOS = actionMappingRepository.selectByProductId(dto.getProductId(),
                dto.getThirdPartyCloud().name());
        actionMappingRepository.updateNull(CollectionUtil.newArrayList(dto.getProductId()));
        DaoUtils.comparisonDb(actionMappingRepository, ActionMappingDO::getId, actionMappingDOS, newFunctionMapping, ActionMappingDO.class);
    }

    @Override
    public List<String> thirdFunction(String thirdPartyCloud) {
        return null;
    }

    @Override
    public void delete(ProductActionDTO dto) {
        if (StrUtil.isNotBlank(dto.getProductId())) {
            //删除整个产品映射关系
            actionMappingRepository.deleteByProductId(dto.getProductId(), dto.getThirdPartyCloud());
        }
    }
}
