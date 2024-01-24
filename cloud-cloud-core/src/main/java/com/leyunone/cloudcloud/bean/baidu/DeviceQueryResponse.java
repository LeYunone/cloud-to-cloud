package com.leyunone.cloudcloud.bean.baidu;

import lombok.*;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Getter
@Setter
public class DeviceQueryResponse {

    private BaiduHeader header;
    
    private Payload payload;

    @Getter
    @Setter
    public static class Payload{

        private List<BaiduAttributes> attributes;
    }
}
