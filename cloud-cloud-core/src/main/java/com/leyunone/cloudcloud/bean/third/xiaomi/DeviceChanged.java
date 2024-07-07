package com.leyunone.cloudcloud.bean.third.xiaomi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @author LeYunone
 * @date 2023-12-21 16:19:41
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceChanged {

    private String requestId;

    private String topic;

    private List<XiaomiDevice> devices;
}
