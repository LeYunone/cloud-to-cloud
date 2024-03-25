package com.leyunone.cloudcloud.web.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.web.bean.dto.ProductFunctionDTO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import com.leyunone.cloudcloud.web.bean.vo.ProductFunctionVO;

import java.util.List;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2024/3/26 15:29
 */
public interface ProductFunctionService {

    ProductFunctionVO getDetail(Integer id);

    Page<ProductFunctionVO> listByCon(ProductTypeQuery query);

    void save(ProductFunctionDTO dto);

    List<String> thirdFunction(String thirdPartyCloud);
    
    void delete(Integer id);
}
