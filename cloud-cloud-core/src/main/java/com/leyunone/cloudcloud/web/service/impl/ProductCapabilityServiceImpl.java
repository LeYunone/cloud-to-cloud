package com.leyunone.cloudcloud.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.dao.DeviceCapabilityRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceCapabilityDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.web.bean.dto.ProductCapabilityDTO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import com.leyunone.cloudcloud.web.bean.vo.ProductCapabilityVO;
import com.leyunone.cloudcloud.web.service.ProductCapabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeyunOne
 * @Date 2024/3/26 15:29
 */
@Service
public class ProductCapabilityServiceImpl implements ProductCapabilityService {

    @Autowired
    private DeviceCapabilityRepository deviceCapabilityRepository;

    @Override
    public ProductCapabilityVO getDetail(Integer id) {
        DeviceCapabilityDO deviceCapabilityDO = deviceCapabilityRepository.selectById(id);
        ProductCapabilityVO productCapabilityVO = new ProductCapabilityVO();
        BeanUtil.copyProperties(productCapabilityVO, deviceCapabilityDO);
        return productCapabilityVO;
    }

    @Override
    public Page<ProductCapabilityVO> listByCon(ProductTypeQuery query) {
        Page<DeviceCapabilityDO> deviceCapabilityDOPage = deviceCapabilityRepository.selectPage(query);

        Page<ProductCapabilityVO> page = new Page<>();
        page.setTotal(deviceCapabilityDOPage.getTotal());
        page.setRecords(deviceCapabilityDOPage.getRecords().stream().map(c -> {
            ProductCapabilityVO productCapabilityVO = new ProductCapabilityVO();
            BeanUtil.copyProperties(c, productCapabilityVO);
            return productCapabilityVO;
        }).collect(Collectors.toList()));
        return page;
    }

    @Override
    public void save(ProductCapabilityDTO dto) {
        DeviceCapabilityDO deviceCapabilityDO = new DeviceCapabilityDO();
        BeanUtil.copyProperties(dto, deviceCapabilityDO);
        deviceCapabilityRepository.insertOrUpdate(deviceCapabilityDO);
    }

    @Override
    public List<String> thirdCapability(ThirdPartyCloudEnum thirdPartyCloud) {
        List<DeviceCapabilityDO> deviceCapabilityDOS = deviceCapabilityRepository.selectByCloud(thirdPartyCloud);
        return deviceCapabilityDOS.stream().map(DeviceCapabilityDO::getDescription).collect(Collectors.toList());
    }

    @Override
    public void delete(ProductCapabilityDTO dto) {
        //删除整个产品映射关系
        deviceCapabilityRepository.deleteById(dto.getId());
    }
}
