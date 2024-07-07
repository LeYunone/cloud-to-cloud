package com.leyunone.cloudcloud.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.dao.base.iservice.IBaseRepository;
import com.leyunone.cloudcloud.dao.entity.DeviceCapabilityDO;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
public interface DeviceCapabilityRepository extends IBaseRepository<DeviceCapabilityDO> {

    List<DeviceCapabilityDO> selectByCloud(ThirdPartyCloudEnum cloud);

    Page<DeviceCapabilityDO> selectPage(ProductTypeQuery query);
}
