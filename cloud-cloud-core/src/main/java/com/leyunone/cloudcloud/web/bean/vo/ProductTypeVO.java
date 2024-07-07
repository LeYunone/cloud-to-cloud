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
 * @Date 2024/3/26 17:12
 */
@Getter
@Setter
public class ProductTypeVO {

    private List<String> thirdProductIds;

    private String productId;

    private ThirdPartyCloudEnum thirdPartyCloud;

    private LocalDateTime updateTime;
}
