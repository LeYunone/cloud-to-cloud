package com.leyunone.cloudcloud.dao.base.iservice;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 *  * 「使用方法」：
 *  * 1、XXDao extends BaseDao<XXDO>
 *  * 2、XXDaoImpl extends BaseDaoImpl<XXMapper,XXDO> Implement XXDao     
 *  * 3、Dao层内  this.baseMapper.selectById
 *  * 4、Service层内 xxDao.selectById
 *  
 * @author leyunone
 * @create 2022-03-28 16:54
 * 基础服务接口 T规定  入参
 */
public interface IBaseRepository<DO> extends IService<DO>,IOperationService<DO>,IQueryService<DO> {
}
