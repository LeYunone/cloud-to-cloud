package com.leyunone.cloudcloud.bean.mapping;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author LeYunone
 * @date 2023-12-26 15:05:02
**/
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString
@Data
public class XiaomiStatusMapping extends StatusMapping{

    private Integer siid;

    private Integer piid;

}