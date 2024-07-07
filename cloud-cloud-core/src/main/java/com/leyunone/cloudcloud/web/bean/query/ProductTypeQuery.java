package com.leyunone.cloudcloud.web.bean.query;

import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2024/3/25 16:47
 */
@Getter
@Setter
public class ProductTypeQuery extends CommonPage {

    private ThirdPartyCloudEnum thirdPartyCloud;

    private String productId;
}
