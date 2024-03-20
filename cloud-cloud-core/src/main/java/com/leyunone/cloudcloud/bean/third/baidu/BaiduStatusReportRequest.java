package com.leyunone.cloudcloud.bean.third.baidu;

import lombok.*;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BaiduStatusReportRequest {

    private BaiduHeader header;
 
    private Payload payload;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Payload{
        
        private String botId;
        
        private String openUid;
    
        private Appliance appliance;
    }
    
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Appliance {
        
        private String applianceId;
        
        private String attributeName;
    }
}
