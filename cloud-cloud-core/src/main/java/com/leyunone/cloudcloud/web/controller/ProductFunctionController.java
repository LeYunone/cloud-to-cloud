package com.leyunone.cloudcloud.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.bean.DataResponse;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.web.bean.dto.ProductFunctionDTO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import com.leyunone.cloudcloud.web.bean.vo.ProductFunctionMappingVO;
import com.leyunone.cloudcloud.web.bean.vo.ProductFunctionVO;
import com.leyunone.cloudcloud.web.service.ProductFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * :)
 *
 * @Author leyunone
 * @Date 2024/3/26 15:11
 */
@RequestMapping("/web/productFunction")
@RestController
public class ProductFunctionController {
    
    @Autowired
    private ProductFunctionService productFunctionService;

    @GetMapping("/list")
    public DataResponse<?> list(ProductTypeQuery query) {
        Page<ProductFunctionVO> productFunctionVOPage = productFunctionService.listByCon(query);
        return DataResponse.of(productFunctionVOPage);
    }

    @GetMapping("/detail")
    public DataResponse<?> getDetail(String productId, ThirdPartyCloudEnum thirdPartyCloud) {
        ProductFunctionMappingVO detail = productFunctionService.getDetail(productId, thirdPartyCloud);
        return DataResponse.of(detail);
    }


    @PostMapping("/save")
    public DataResponse<?> save(@RequestBody ProductFunctionDTO productFunctionDTO) {
        productFunctionService.save(productFunctionDTO);
        return DataResponse.of();
    }

    @PostMapping("/delete")
    public DataResponse<?> delete(@RequestBody ProductFunctionDTO productFunctionDTO) {
        productFunctionService.delete(productFunctionDTO);
        return DataResponse.of();
    }
}
