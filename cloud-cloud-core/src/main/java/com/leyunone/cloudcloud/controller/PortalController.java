package com.leyunone.cloudcloud.controller;

import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.service.ThirdPartyPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/20 15:51
 */
@RestController
@RequestMapping("/portal/")
public class PortalController {

    @Autowired
    private ThirdPartyPortalService thirdPartyPortalService;

    @PostMapping("google")
    public String google(@RequestBody String payload) {
        return thirdPartyPortalService.portal(payload, ThirdPartyCloudEnum.GOOGLE);
    }

    @PostMapping("alexa")
    public String alexa(@RequestBody String payload) {
        return thirdPartyPortalService.portal(payload, ThirdPartyCloudEnum.ALEXA);
    }

    @PostMapping("baidu")
    public String baidu(@RequestBody String payload) {
        return thirdPartyPortalService.portal(payload, ThirdPartyCloudEnum.BAIDU);
    }
    
    @PostMapping("xiaomi")
    public String xiaomi(@RequestBody String payload) {
        return thirdPartyPortalService.portal(payload, ThirdPartyCloudEnum.XIAOMI);
    }

}
