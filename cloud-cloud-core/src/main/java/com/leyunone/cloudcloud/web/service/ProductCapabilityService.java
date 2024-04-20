package com.leyunone.cloudcloud.web.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.dao.entity.DeviceCapabilityDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.web.bean.dto.ProductCapabilityDTO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import com.leyunone.cloudcloud.web.bean.vo.ProductCapabilityVO;

import java.util.List;

/**
 * :)
 *
 * @Author LeyunOne
 * @Date 2024/3/26 15:29
 */
public interface ProductCapabilityService {

    ProductCapabilityVO getDetail(Integer id);

    Page<ProductCapabilityVO> listByCon(ProductTypeQuery query);

    void save(ProductCapabilityDTO dto);

    List<DeviceCapabilityDO> thirdCapability(ThirdPartyCloudEnum thirdPartyCloud);

    void delete(ProductCapabilityDTO dto);
}
