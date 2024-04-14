package com.leyunone.cloudcloud.dao.base.iservice;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author leyunone
 * @date 2022-04-05
 */
public interface IQueryPageService<DO>{

    /**
     * 万能eq分页查询
     * @return
     */
    Page<DO> selectByConPage(Object o, Page page);

    Page<DO> selectByConPage(Object o, Integer index, Integer size);
}
