package com.leyunone.cloudcloud.bean.tmall;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * :)
 *
 * @Author Leyunone
 * @Date 2024/1/16 16:58
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TmallStandardRequest implements Serializable {
    
    private TmallHeader header;
    
    private StandardPayload payload;

    @Getter
    @Setter
    public static class StandardPayload {

        private String accessToken;
    }
}
