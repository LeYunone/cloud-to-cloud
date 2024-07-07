package com.leyunone.cloudcloud.web.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.web.bean.dto.ProductFunctionDTO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import com.leyunone.cloudcloud.web.bean.vo.ProductFunctionMappingVO;
import com.leyunone.cloudcloud.web.bean.vo.ProductFunctionVO;

import java.util.List;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2024/3/26 15:29
 */
public interface ProductFunctionService {

    ProductFunctionMappingVO getDetail(String productId, ThirdPartyCloudEnum cloud);

    Page<ProductFunctionVO> listByCon(ProductTypeQuery query);

    void save(ProductFunctionDTO dto);

    List<String> thirdFunction(String thirdPartyCloud);

    void delete(ProductFunctionDTO dto);
}
