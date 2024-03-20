package com.leyunone.cloudcloud.bean.third.xiaomi;

import lombok.*;

/**
 *
 * @author LeYunone
 * @date 2023-12-21 15:21:44
**/
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class XiaomiProperties extends XiaomiResult {

    //did 即 DeviceID，是设备的唯一标识符，必须是字符串，不能包含点字符，不能超过 40 个字符，该 did 由开发者自行设置
    private String did;

    // 设备中的服务实例 ID
    private Integer siid;

    // 服务实例中的属性实例 ID
    private Integer piid;

    private String value;

}
