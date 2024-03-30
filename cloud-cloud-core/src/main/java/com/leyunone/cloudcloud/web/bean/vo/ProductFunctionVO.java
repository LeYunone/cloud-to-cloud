package com.leyunone.cloudcloud.web.bean.vo;

import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2024/3/26 15:31
 */
@Getter
@Setter
public class ProductFunctionVO {

    private String productId;

    private List<String> thirdProductIds;

    private List<String> thirdSignCodes;

    private LocalDateTime updateTime;

    private ThirdPartyCloudEnum thirdPartyCloud;
    
}
