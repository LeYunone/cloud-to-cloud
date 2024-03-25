package com.leyunone.cloudcloud.web.service;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.web.bean.dto.ProductTypeMappingDTO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import com.leyunone.cloudcloud.web.bean.vo.ProductTypeMappingVO;
import com.leyunone.cloudcloud.web.bean.vo.ProductTypeVO;

import java.util.List;

/**
 * 产品类型映射关系(MProductTypeMapping)service
 *
 * @author leyunone
 * @since 2024-03-25 17:54:29
 */
public interface ProductTypeMappingService {

    ProductTypeMappingVO getDetail(String productId, ThirdPartyCloudEnum thirdPartyCloud);

    Page<ProductTypeVO> listByCon(ProductTypeQuery query);
    
    void save(ProductTypeMappingDTO dto);

    List<String> thirdProducts(String thirdPartyCloud);
    
    void delete(ProductTypeMappingDTO productTypeMappingDTO);
}

