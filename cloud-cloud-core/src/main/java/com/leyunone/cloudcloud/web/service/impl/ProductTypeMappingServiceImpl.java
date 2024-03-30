package com.leyunone.cloudcloud.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.dao.ProductTypeMappingRepository;
import com.leyunone.cloudcloud.dao.entity.ProductTypeMappingDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.util.CollectionFunctionUtils;
import com.leyunone.cloudcloud.web.bean.dto.ProductTypeMappingDTO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import com.leyunone.cloudcloud.web.bean.vo.ProductTypeMappingVO;
import com.leyunone.cloudcloud.web.bean.vo.ProductTypeVO;
import com.leyunone.cloudcloud.web.service.ProductTypeMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 产品类型映射关系(MProductTypeMapping)服务
 *
 * @author leyunone
 * @since 2024-03-25 17:55:22
 */
@Service
public class ProductTypeMappingServiceImpl implements ProductTypeMappingService {

    @Autowired
    private ProductTypeMappingRepository productTypeMappingRepository;

    @Override
    public ProductTypeMappingVO getDetail(String productId, ThirdPartyCloudEnum thirdPartyCloud) {
        List<ProductTypeMappingDO> productTypeMappingDOS = productTypeMappingRepository.selectByProductIds(CollectionUtil.newArrayList(productId), thirdPartyCloud.name());
        ProductTypeMappingVO productTypeMappingVO = new ProductTypeMappingVO();
        productTypeMappingVO.setProductId(productId);
        productTypeMappingVO.setThirdPartyCloud(thirdPartyCloud);
        productTypeMappingVO.setProductTypes(JSONObject.parseArray(JSONObject.toJSONString(productTypeMappingDOS), ProductTypeMappingVO.ProductType.class));
        return productTypeMappingVO;
    }

    @Override
    public Page<ProductTypeVO> listByCon(ProductTypeQuery query) {
        Page<ProductTypeMappingDO> productTypeMappingDOPage = productTypeMappingRepository.selectPageOrder(query);
        List<String> pids = productTypeMappingDOPage.getRecords().stream().map(ProductTypeMappingDO::getProductId).collect(Collectors.toList());
        List<ProductTypeMappingDO> productTypeMappingDOS = productTypeMappingRepository.selectByProductIds(pids, query.getThirdPartyCloud().name());
        Map<String, List<ProductTypeMappingDO>> productMap = CollectionFunctionUtils.groupTo(productTypeMappingDOS, ProductTypeMappingDO::getProductId);

        Page<ProductTypeVO> page = new Page<>();
        page.setRecords(productTypeMappingDOPage.getRecords().stream().map(p -> {
            ProductTypeVO productTypeVO = new ProductTypeVO();
            productTypeVO.setProductId(p.getProductId());
            productTypeVO.setUpdateTime(p.getUpdateTime());
            List<ProductTypeMappingDO> productTypeMappings = productMap.get(p.getProductId());
            productTypeVO.setThirdProductIds(productTypeMappings.stream().map(ProductTypeMappingDO::getThirdProductId).collect(Collectors.toList()));
            productTypeVO.setThirdPartyCloud(CollectionUtil.getFirst(productTypeMappings).getThirdPartyCloud());
            return productTypeVO;
        }).collect(Collectors.toList()));
        page.setTotal(productTypeMappingDOPage.getTotal());
        return page;
    }

    @Override
    public void save(ProductTypeMappingDTO dto) {
        List<ProductTypeMappingDTO.ProductType> productTypes = dto.getProductTypes();
        productTypes = productTypes.stream().peek(p -> {
            p.setProductId(dto.getProductId());
            p.setThirdPartyCloud(dto.getThirdPartyCloud());
        }).collect(Collectors.toList());
        List<ProductTypeMappingDO> productTypeMappingDOS = productTypeMappingRepository.selectByProductIds(CollectionUtil.newArrayList(dto.getProductId()), dto.getThirdPartyCloud().name());
        DaoUtils.comparisonDb(productTypeMappingRepository, ProductTypeMappingDO::getId, productTypeMappingDOS, productTypes, ProductTypeMappingDO.class);
    }

    @Override
    public List<String> thirdProducts(String thirdPartyCloud) {
        List<ProductTypeMappingDO> productTypeMappingDOS = productTypeMappingRepository.selectByCloud(thirdPartyCloud);
        return productTypeMappingDOS.stream().map(ProductTypeMappingDO::getThirdProductId).collect(Collectors.toList());
    }

    @Override
    public void delete(ProductTypeMappingDTO productTypeMappingDTO) {
        if (StrUtil.isNotBlank(productTypeMappingDTO.getProductId())) {
            //删除整个产品映射关系
            productTypeMappingRepository.deleteByProductId(productTypeMappingDTO.getProductId(), productTypeMappingDTO.getThirdPartyCloud());
        }
    }
} 

