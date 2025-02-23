package com.leyunone.cloudcloud.controller;

import com.leyunone.cloudcloud.bean.DataResponse;
import com.leyunone.cloudcloud.bean.info.ThirdPartyCloudConfigInfo;
import com.leyunone.cloudcloud.service.ThirdPartyConfigService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/20 16:12
 */
@RestController
@RequestMapping("/thirdPartyCloud/")
public class ThirdClientConfigController {


    private final ThirdPartyConfigService thirdPartyCloudService;

    public ThirdClientConfigController(ThirdPartyConfigService thirdPartyCloudService) {
        this.thirdPartyCloudService = thirdPartyCloudService;
    }

    @PostMapping("/getConfig")
    public DataResponse<ThirdPartyCloudConfigInfo> getConfig(@RequestParam("clientId") String clientId){
        ThirdPartyCloudConfigInfo config = thirdPartyCloudService.getConfig(clientId);
        return DataResponse.of(config);
    }
}
