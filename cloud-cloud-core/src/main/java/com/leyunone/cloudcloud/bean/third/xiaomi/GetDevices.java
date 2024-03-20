package com.leyunone.cloudcloud.bean.third.xiaomi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @author LeYunone
 * @date 2023-12-21 15:42:17
**/
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetDevices extends XiaomiCommonPayload {

    private List<XiaomiDevice> devices;

}
