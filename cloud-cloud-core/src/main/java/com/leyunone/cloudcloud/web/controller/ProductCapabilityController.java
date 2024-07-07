package com.leyunone.cloudcloud.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.bean.DataResponse;
import com.leyunone.cloudcloud.web.bean.dto.ProductCapabilityDTO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import com.leyunone.cloudcloud.web.bean.vo.ProductCapabilityVO;
import com.leyunone.cloudcloud.web.service.ProductCapabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * :)
 *
 * @Author LeyunOne
 * @Date 2024/4/2 9:57
 */
@RequestMapping("/web/productCapability")
@RestController
public class ProductCapabilityController {

    @Autowired
    private ProductCapabilityService productCapabilityService;

    @GetMapping("/list")
    public DataResponse<Page<ProductCapabilityVO>> list(ProductTypeQuery query) {
        Page<ProductCapabilityVO> productCapabilityVOPage = productCapabilityService.listByCon(query);
        return DataResponse.of(productCapabilityVOPage);
    }

    @GetMapping("/detail")
    public DataResponse<ProductCapabilityVO> getDetail(Integer id) {
        ProductCapabilityVO detail = productCapabilityService.getDetail(id);
        return DataResponse.of(detail);
    }


    @PostMapping("/save")
    public DataResponse<?> save(@RequestBody ProductCapabilityDTO dto) {
        productCapabilityService.save(dto);
        return DataResponse.of();
    }

    @PostMapping("/delete")
    public DataResponse<?> delete(@RequestBody ProductCapabilityDTO dto) {
        productCapabilityService.delete(dto);
        return DataResponse.of();
    }
}
