package com.leyunone.cloudcloud.bean.third.xiaomi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小米
 * @author LeYunone
 * @date 2023-12-21 14:57:33
**/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class XiaomiCommonPayload {

    private String requestId;

    private String intent;

}
