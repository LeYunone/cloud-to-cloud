package com.leyunone.cloudcloud.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.bean.enums.ConvertFunctionEnum;
import com.leyunone.cloudcloud.dao.FunctionMappingRepository;
import com.leyunone.cloudcloud.dao.ProductTypeMappingRepository;
import com.leyunone.cloudcloud.dao.entity.FunctionMappingDO;
import com.leyunone.cloudcloud.dao.entity.ProductTypeMappingDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import com.leyunone.cloudcloud.util.DaoUtils;
import com.leyunone.cloudcloud.util.DeepSearchUtils;
import com.leyunone.cloudcloud.web.bean.dto.ProductFunctionDTO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import com.leyunone.cloudcloud.web.bean.vo.ProductFunctionMappingVO;
import com.leyunone.cloudcloud.web.bean.vo.ProductFunctionVO;
import com.leyunone.cloudcloud.web.service.ProductFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Date 2024/3/26 15:29
 */
@Service
public class ProductFunctionServiceImpl implements ProductFunctionService {

    @Autowired
    private FunctionMappingRepository productMappingRepository;
    @Autowired
    private ProductTypeMappingRepository productTypeMappingRepository;

    @Override
    public ProductFunctionMappingVO getDetail(String productId, ThirdPartyCloudEnum cloud) {
        List<ProductTypeMappingDO> productTypeMappingDOS = productTypeMappingRepository.selectByProductId(productId, cloud.name());
        List<FunctionMappingDO> functionMappingDOS = productMappingRepository.selectByProductIdsAndThirdPartyCloud(productId, cloud.name());

        ProductFunctionMappingVO productFunctionMappingVO = new ProductFunctionMappingVO();
        productFunctionMappingVO.setProductId(productId);
        productFunctionMappingVO.setProductFunctions(functionMappingDOS.stream().map(functionMapping -> {
            ProductFunctionMappingVO.FunctionMapping function = new ProductFunctionMappingVO.FunctionMapping();
            function.setProductId(functionMapping.getProductId());
            function.setThirdPartyCloud(functionMapping.getThirdPartyCloud());
            function.setId(functionMapping.getId());
            function.setSignCode(functionMapping.getSignCode());
            function.setThirdSignCode(functionMapping.getThirdSignCode());
            function.setThirdActionCode(functionMapping.getThirdActionCode());
            function.setValueOf(functionMapping.getValueOf());
            function.setConvertFunction(functionMapping.getConvertFunction());
            if (StrUtil.isNotBlank(functionMapping.getCapabilityConfigId())) {
                function.setCapabilityConfigIds(CollectionUtil.newArrayList(functionMapping.getCapabilityConfigId().split(",")));
                function.setCapabilityConfigId(functionMapping.getCapabilityConfigId());
            }
            function.setRemark(functionMapping.getRemark());
            if (StrUtil.isNotBlank(functionMapping.getValueMapping())) {
                Map<String, Object> innerMap = JSON.parseObject(functionMapping.getValueMapping()).getInnerMap();
                function.setValueMapping(innerMap.keySet().stream().map(key -> {
                    ProductFunctionMappingVO.ValueMap valueMap = new ProductFunctionMappingVO.ValueMap();
                    valueMap.setKey(key);
                    valueMap.setValue(innerMap.get(key));
                    return valueMap;
                }).collect(Collectors.toList()));
            } else {
                function.setValueMapping(new ArrayList<>());
            }
            return function;
        }).collect(Collectors.toList()));
        productFunctionMappingVO.setThirdPartyCloud(cloud);
        productFunctionMappingVO.setThirdProductIds(productTypeMappingDOS.stream().map(ProductTypeMappingDO::getThirdProductId).collect(Collectors.toList()));

        return productFunctionMappingVO;
    }

    @Override
    public Page<ProductFunctionVO> listByCon(ProductTypeQuery query) {
        Page<FunctionMappingDO> functionMappingDOPage = productMappingRepository.selectPageOrder(query);
        List<String> pids = functionMappingDOPage.getRecords().stream().map(FunctionMappingDO::getProductId).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(pids)) return new Page<>();

        List<ProductTypeMappingDO> productTypeMappingDOS = productTypeMappingRepository.selectByProductIds(pids, query.getThirdPartyCloud().name());

        List<FunctionMappingDO> functionMappingDOS = productMappingRepository.selectByProductIdsAndThirdPartyCloud(pids, query.getThirdPartyCloud().name());
        Map<String, List<FunctionMappingDO>> functionMap = CollectionFunctionUtils.groupTo(functionMappingDOS, FunctionMappingDO::getProductId);
        Map<String, List<ProductTypeMappingDO>> productMap = CollectionFunctionUtils.groupTo(productTypeMappingDOS, ProductTypeMappingDO::getProductId);

        Page<ProductFunctionVO> page = new Page<>();
        page.setTotal(functionMappingDOPage.getTotal());
        page.setRecords(functionMappingDOPage.getRecords().stream().map(f -> {
            ProductFunctionVO productFunctionVO = new ProductFunctionVO();
            List<FunctionMappingDO> fs = functionMap.get(f.getProductId());
            List<ProductTypeMappingDO> ps = productMap.get(f.getProductId());
            productFunctionVO.setProductId(f.getProductId());
            productFunctionVO.setUpdateTime(f.getUpdateTime());
            productFunctionVO.setThirdPartyCloud(query.getThirdPartyCloud());
            if (CollectionUtil.isNotEmpty(fs)) {
                productFunctionVO.setThirdCodes(fs.stream().map(m -> {
                    ProductFunctionVO.Mapping pm = new ProductFunctionVO.Mapping();
                    pm.setThirdActionCode(m.getThirdActionCode());
                    pm.setThirdSignCode(m.getThirdSignCode());
                    return pm;
                }).collect(Collectors.toList()));
            }
            if (CollectionUtil.isNotEmpty(ps)) {
                productFunctionVO.setThirdProductIds(ps.stream().map(ProductTypeMappingDO::getThirdProductId).collect(Collectors.toList()));
            }
            return productFunctionVO;
        }).collect(Collectors.toList()));
        return page;
    }

    @Override
    public void save(ProductFunctionDTO dto) {
        List<ProductFunctionDTO.FunctionMapping> functionMappings = dto.getProductFunctions();
        List<FunctionMappingDO> newFunctionMapping = functionMappings.stream().map(f -> {
            FunctionMappingDO functionMappingDO = new FunctionMappingDO();
            functionMappingDO.setId(f.getId());
            functionMappingDO.setProductId(dto.getProductId());
            functionMappingDO.setSignCode(f.getSignCode());
            try {
                //找到最终key
                functionMappingDO.setThirdSignCode(DeepSearchUtils.findDeepestKey(JSONObject.parseObject(f.getThirdSignCode())));
            } catch (Exception e) {
                functionMappingDO.setThirdSignCode(f.getThirdSignCode());
            }
            functionMappingDO.setThirdActionCode(f.getThirdActionCode());
            functionMappingDO.setThirdPartyCloud(dto.getThirdPartyCloud());
            functionMappingDO.setValueOf(f.isValueOf());
            if (CollectionUtil.isNotEmpty(f.getValueMapping())) {
                Map<String, Object> o = new HashMap<>();
                f.getValueMapping().forEach(fm -> {
                    o.put(fm.getKey(), fm.getValue());
                });
                functionMappingDO.setValueMapping(JSONObject.toJSONString(o));
            }
            functionMappingDO.setRemark(f.getRemark());
//            functionMappingDO.setLegalValue();
            if (StrUtil.isNotBlank(f.getConvertFunction())) {
                functionMappingDO.setConvertFunction(ConvertFunctionEnum.valueOf(f.getConvertFunction()));
            }
            if (CollectionUtil.isNotEmpty(f.getCapabilityConfigIds())) {
                functionMappingDO.setCapabilityConfigId(CollectionUtil.join(f.getCapabilityConfigIds(), ","));
            }
            if (StrUtil.isNotBlank(f.getCapabilityConfigId())) {
                functionMappingDO.setCapabilityConfigId(f.getCapabilityConfigId());
            }
            return functionMappingDO;
        }).collect(Collectors.toList());
        List<FunctionMappingDO> functionMappingDOS = productMappingRepository.selectByProductIdsAndThirdPartyCloud(CollectionUtil.newArrayList(dto.getProductId()),
                dto.getThirdPartyCloud().name());
        productMappingRepository.updateNull(CollectionUtil.newArrayList(dto.getProductId()));
        DaoUtils.comparisonDb(productMappingRepository, FunctionMappingDO::getId, functionMappingDOS, newFunctionMapping, FunctionMappingDO.class);
    }

    @Override
    public List<String> thirdFunction(String thirdPartyCloud) {
        return null;
    }

    @Override
    public void delete(ProductFunctionDTO dto) {
        if (StrUtil.isNotBlank(dto.getProductId())) {
            //删除整个产品映射关系
            productMappingRepository.deleteByProductId(dto.getProductId(), dto.getThirdPartyCloud());
        }
    }
}
