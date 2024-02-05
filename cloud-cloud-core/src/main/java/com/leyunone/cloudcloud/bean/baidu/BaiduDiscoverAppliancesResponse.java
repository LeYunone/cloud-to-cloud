package com.leyunone.cloudcloud.bean.baidu;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BaiduDiscoverAppliancesResponse {


    private BaiduHeader header;

    private Payload payload;

    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Payload {

        private List<BaiduDevice> discoveredAppliances;

        private List<DiscoveredGroup> discoveredGroups;

    }

    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class DiscoveredGroup {

        private String groupName;

        private List<String> applianceIds;

        private String groupNotes;

        private Map<String,String> additionalGroupDetails;

    }



}
