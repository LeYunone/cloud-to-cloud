package com.leyunone.cloudcloud.bean;

import lombok.Data;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-09
 */
@Data
public class RGBColor {

    private int r;

    private int g;

    private int b;

    public RGBColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
