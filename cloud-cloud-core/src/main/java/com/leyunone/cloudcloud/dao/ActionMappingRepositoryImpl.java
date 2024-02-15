package com.leyunone.cloudcloud.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.leyunone.cloudcloud.dao.base.repository.BaseRepository;
import com.leyunone.cloudcloud.dao.entity.ActionMappingDO;
import com.leyunone.cloudcloud.dao.mapper.ActionMappingMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-14
 */
@Repository
public class ActionMappingRepositoryImpl extends BaseRepository<ActionMappingMapper,ActionMappingDO> implements ActionMappingRepository{

    @Resource
    ActionMappingMapper actionMappingMapper;

    @Override
    public List<ActionMappingDO> selectByProductIds(List<String> productIds, String cloud) {
        if(CollectionUtils.isEmpty(productIds)){
            return new ArrayList<>();
        }
        return actionMappingMapper
                    .selectList(new LambdaQueryWrapper<ActionMappingDO>()
                            .eq(ActionMappingDO::getThirdPartyCloud, cloud)
                            .in(ActionMappingDO::getProductId, productIds)
                    );
    }


}
