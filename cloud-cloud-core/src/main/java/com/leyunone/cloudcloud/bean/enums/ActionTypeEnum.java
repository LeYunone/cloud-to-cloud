package com.leyunone.cloudcloud.bean.enums;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/31 10:08
 */
public enum ActionTypeEnum {

    QUERY(0),

    CONTROL(1);

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    ActionTypeEnum(Integer type) {
        this.type = type;
    }
}

