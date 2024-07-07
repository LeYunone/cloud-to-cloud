package com.leyunone.cloudcloud.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.cloudcloud.bean.DataResponse;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.web.bean.dto.ProductActionDTO;
import com.leyunone.cloudcloud.web.bean.query.ProductTypeQuery;
import com.leyunone.cloudcloud.web.bean.vo.ProductActionMappingVO;
import com.leyunone.cloudcloud.web.bean.vo.ProductActionVO;
import com.leyunone.cloudcloud.web.service.ProductActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * :)
 *
 * @Author LeyunOne
 * @Date 2024/3/28 17:32
 */
@RestController
@RequestMapping("/web/action")
public class ProductActionController {

    @Autowired
    private ProductActionService productActionService;

    @GetMapping("/list")
    public DataResponse<Page<ProductActionVO>> list(ProductTypeQuery query) {
        Page<ProductActionVO> productFunctionVOPage = productActionService.listByCon(query);
        return DataResponse.of(productFunctionVOPage);
    }

    @GetMapping("/detail")
    public DataResponse<ProductActionMappingVO> getDetail(String productId, ThirdPartyCloudEnum thirdPartyCloud) {
        ProductActionMappingVO detail = productActionService.getDetail(productId, thirdPartyCloud);
        return DataResponse.of(detail);
    }


    @PostMapping("/save")
    public DataResponse<?> save(@RequestBody ProductActionDTO productActionDTO) {
        productActionService.save(productActionDTO);
        return DataResponse.of();
    }

    @PostMapping("/delete")
    public DataResponse<?> delete(@RequestBody ProductActionDTO productActionDTO) {
        productActionService.delete(productActionDTO);
        return DataResponse.of();
    }
}
