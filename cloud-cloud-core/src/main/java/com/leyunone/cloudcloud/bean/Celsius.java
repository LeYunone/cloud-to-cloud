package com.leyunone.cloudcloud.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * :)
 * 摄氏度
 *
 * @Author leyunone
 * @Date 2024/2/3 11:06
 */
@Getter
@Setter
public class Celsius {

    private float value;

    private String scale = "CELSIUS";

    public Celsius(float value) {
        this.value = value;
    }
}
