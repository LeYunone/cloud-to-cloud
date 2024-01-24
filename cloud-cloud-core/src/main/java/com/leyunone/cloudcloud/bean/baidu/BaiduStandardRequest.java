package com.leyunone.cloudcloud.bean.baidu;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Getter
@Setter
public class BaiduStandardRequest implements Serializable {

    private BaiduHeader header;

    private StandardPayload payload;

    @Getter
    @Setter
    public static class StandardPayload {
        
        private String accessToken;
    }
}
