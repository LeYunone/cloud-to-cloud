package com.leyunone.cloudcloud.web.controller;

import com.leyunone.cloudcloud.bean.DataResponse;
import com.leyunone.cloudcloud.bean.enums.ConvertFunctionEnum;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.web.bean.dto.ThirdClientConfigDTO;
import com.leyunone.cloudcloud.web.bean.vo.ThirdClientConfigVO;
import com.leyunone.cloudcloud.web.service.ConfigService;
import com.leyunone.cloudcloud.web.service.ProductCapabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * :)
 *
 * @Author LeyunOne
 * @Date 2024/3/28 11:17
 */
@RestController
@RequestMapping("/web/config")
public class ConfigController {

    @Autowired
    private ProductCapabilityService productCapabilityService;
    @Autowired
    private ConfigService configService;

    @GetMapping("/convertFunctions")
    public DataResponse<?> getConvertFunctions() {
        ConvertFunctionEnum[] values = ConvertFunctionEnum.values();
        return DataResponse.of(values);
    }

    @GetMapping("/capability")
    public DataResponse<?> getCapability(ThirdPartyCloudEnum cloud) {
        List<String> capabilities = productCapabilityService.thirdCapability(cloud);
        return DataResponse.of(capabilities);
    }

    @PostMapping("/saveClientConfig")
    public DataResponse<?> saveClientConfig(@RequestBody ThirdClientConfigDTO thirdClientConfigDTO) {
        configService.thirdClientConfigSave(thirdClientConfigDTO);
        return DataResponse.of();
    }

    @GetMapping("/clientConfig")
    public DataResponse<?> clientConfigDetail(ThirdPartyCloudEnum cloud) {
        ThirdClientConfigVO thirdClientConfigVO = configService.clientConfigDetail(cloud);
        return DataResponse.of(thirdClientConfigVO);
    }
}
