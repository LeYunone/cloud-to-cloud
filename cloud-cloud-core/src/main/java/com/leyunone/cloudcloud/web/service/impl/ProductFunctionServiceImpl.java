package com.leyunone.cloudcloud.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.dao.FunctionMappingRepository;
import com.leyunone.cloudcloud.dao.ProductTypeMappingRepository;
import com.leyunone.cloudcloud.dao.entity.ProductTypeMappingDO;
import com.leyunone.cloudcloud.dao.entity.FunctionMappingDO;
import com.leyunone.cloudcloud.web.bean.dto.ProductFunctionDTO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import com.leyunone.cloudcloud.web.bean.vo.ProductFunctionVO;
import com.leyunone.cloudcloud.web.service.ProductFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2024/3/26 15:29
 */
@Service
public class ProductFunctionServiceImpl implements ProductFunctionService {

    @Autowired
    private FunctionMappingRepository functionMappingRepository;
    @Autowired
    private ProductTypeMappingRepository productTypeMappingRepository;

    @Override
    public ProductFunctionVO getDetail(Integer id) {
        return null;
    }

    @Override
    public Page<ProductFunctionVO> listByCon(ProductTypeQuery query) {
        Page<ProductTypeMappingDO> productType = productTypeMappingRepository.selectPage(query);
        return null;
    }

    @Override
    public void save(ProductFunctionDTO dto) {
        List<ProductFunctionDTO.FunctionMapping> functionMappings = dto.getFunctionMappings();
        functionMappings = functionMappings.stream().peek(f -> {
            f.setProductId(dto.getProductId());
            f.setThirdPartyCloud(dto.getThirdPartyCloud());
        }).collect(Collectors.toList());
        List<FunctionMappingDO> functionMappingDOS = functionMappingRepository.selectByProductIdsAndThirdPartyCloud(CollectionUtil.newArrayList(dto.getProductId()),
                dto.getThirdPartyCloud().name());
        DaoUtils.comparisonDb(functionMappingRepository, FunctionMappingDO::getId, functionMappingDOS, functionMappings, FunctionMappingDO.class);
    }

    @Override
    public List<String> thirdFunction(String thirdPartyCloud) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
