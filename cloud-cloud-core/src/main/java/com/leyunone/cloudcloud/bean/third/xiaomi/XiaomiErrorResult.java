package com.leyunone.cloudcloud.bean.third.xiaomi;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author LeYunone
 * @date 2023-12-21 17:11:40
**/
@Builder
@Data
public class XiaomiErrorResult {

    private Integer code;

    private String description;

}
