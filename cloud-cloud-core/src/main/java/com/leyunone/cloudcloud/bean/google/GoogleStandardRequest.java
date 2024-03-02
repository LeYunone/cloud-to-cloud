package com.leyunone.cloudcloud.bean.google;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/23 10:25
 */
@Getter
@Setter
public class GoogleStandardRequest implements Serializable {

    private String requestId;

    private List<Input> inputs;

    @Getter
    @Setter
    public static class Input {
        
        private String intent;
    }
}
