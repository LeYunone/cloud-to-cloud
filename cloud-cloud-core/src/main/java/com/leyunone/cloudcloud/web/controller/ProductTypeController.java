package com.leyunone.cloudcloud.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.bean.DataResponse;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.web.bean.dto.ProductTypeMappingDTO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import com.leyunone.cloudcloud.web.bean.vo.ProductTypeMappingVO;
import com.leyunone.cloudcloud.web.bean.vo.ProductTypeVO;
import com.leyunone.cloudcloud.web.service.ProductTypeMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * :)
 * 产品类型CRUD
 *
 * @Author leyunone
 * @Date 2024/3/25 16:45
 */
@RequestMapping("/web/productType")
@RestController
public class ProductTypeController {

    @Autowired
    private ProductTypeMappingService productTypeMappingService;

    @GetMapping("/list")
    public DataResponse<Page<ProductTypeVO>> getList(ProductTypeQuery query) {
        Page<ProductTypeVO> productTypeMappingVOPage = productTypeMappingService.listByCon(query);
        return DataResponse.of(productTypeMappingVOPage);
    }

    @GetMapping("/detail")
    public DataResponse<ProductTypeMappingVO> getDetail(String productId, ThirdPartyCloudEnum thirdPartyCloud) {
        ProductTypeMappingVO detail = productTypeMappingService.getDetail(productId,thirdPartyCloud);
        return DataResponse.of(detail);
    }

    @PostMapping("/save")
    public DataResponse<?> save(@RequestBody ProductTypeMappingDTO productTypeMappingDTO) {
        productTypeMappingService.save(productTypeMappingDTO);
        return DataResponse.of();
    }

    @GetMapping("/thirdProducts")
    public DataResponse<List<String>> thirdProducts(String thirdPartyCloud) {
        List<String> pids = productTypeMappingService.thirdProducts(thirdPartyCloud);
        return DataResponse.of(pids);
    }

    @PostMapping("/delete")
    public DataResponse<?> delete(@RequestBody ProductTypeMappingDTO productTypeMappingDTO) {
        productTypeMappingService.delete(productTypeMappingDTO);
        return DataResponse.of();
    }
}
