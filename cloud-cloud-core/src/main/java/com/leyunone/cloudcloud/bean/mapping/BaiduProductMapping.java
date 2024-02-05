package com.leyunone.cloudcloud.bean.mapping;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-07
 */
@Getter
@Setter
public class BaiduProductMapping extends ProductMapping{

    private List<ActionMapping> actionMappings;
}
