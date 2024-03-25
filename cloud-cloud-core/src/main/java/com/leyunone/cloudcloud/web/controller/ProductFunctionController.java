package com.leyunone.cloudcloud.web.controller;

import com.leyunone.cloudcloud.bean.DataResponse;
import com.leyunone.cloudcloud.web.bean.dto.ProductFunctionDTO;
import com.leyunone.cloudcloud.web.bean.query.ProductFunctionQuery;
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
    public DataResponse<?> list(ProductFunctionQuery query) {
        return DataResponse.of();
    }

    @GetMapping("/detail")
    public DataResponse<?> getDetail(String productId) {
        return DataResponse.of();
    }


    @PostMapping("/save")
    public DataResponse<?> save(@RequestBody ProductFunctionDTO productFunctionDTO) {
        return DataResponse.of();
    }

    @PostMapping("/delete")
    public DataResponse<?> delete(@RequestBody ProductFunctionDTO productFunctionDTO) {
        return DataResponse.of();
    }
}
