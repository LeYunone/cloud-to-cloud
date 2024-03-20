package com.leyunone.cloudcloud.bean.third.xiaomi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @author LeYunone
 * @date 2023-12-21 15:33:19
**/
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SetProperties extends XiaomiCommonPayload{

    private List<XiaomiProperties> properties;

}
