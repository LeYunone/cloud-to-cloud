package com.leyunone.cloudcloud.web.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.web.bean.dto.ProductActionDTO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import com.leyunone.cloudcloud.web.bean.vo.ProductActionMappingVO;
import com.leyunone.cloudcloud.web.bean.vo.ProductActionVO;

import java.util.List;

/**
 * :)
 *
 * @Author LeyunOne
 * @Date 2024/3/26 15:29
 */
public interface ProductActionService {

    ProductActionMappingVO getDetail(String productId, ThirdPartyCloudEnum cloud);

    Page<ProductActionVO> listByCon(ProductTypeQuery query);

    void save(ProductActionDTO dto);

    List<String> thirdFunction(String thirdPartyCloud);

    void delete(ProductActionDTO dto);
}
