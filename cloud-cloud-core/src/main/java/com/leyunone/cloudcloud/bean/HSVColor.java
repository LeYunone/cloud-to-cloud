package com.leyunone.cloudcloud.bean;

import lombok.Builder;
import lombok.Data;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/29 16:38
 */
@Data
@Builder
public class HSVColor {

    private float hue;
    
    private float saturation;
    
    private float value;
}
