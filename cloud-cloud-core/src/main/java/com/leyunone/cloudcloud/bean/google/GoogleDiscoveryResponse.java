package com.leyunone.cloudcloud.bean.google;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/2/23 10:38
 */
@Getter
@Setter
public class GoogleDiscoveryResponse {

    private String requestId;

    private Payload payload;
    
    public GoogleDiscoveryResponse(String requestId,Payload payload){
        this.requestId = requestId;
        this.payload = payload;
    }
    
    @Builder
    public static class Payload {
        
        private String agentUserId;
        
        private List<GoogleDevice> devices;
    }
}
