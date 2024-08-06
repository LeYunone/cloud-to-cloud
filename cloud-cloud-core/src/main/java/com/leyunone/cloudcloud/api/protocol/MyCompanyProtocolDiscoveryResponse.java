package com.leyunone.cloudcloud.api.protocol;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/31 10:55
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class MyCompanyProtocolDiscoveryResponse {

    private Payload payload;

    private MyCompanyHeader header;
    
    public MyCompanyProtocolDiscoveryResponse(){
        
    }

    public MyCompanyProtocolDiscoveryResponse(MyCompanyHeader header, Payload payload) {
        this.header = header;
        this.payload = payload;
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class Payload {

        private List<MyCompanyProtocolDeviceInfo> devices;

        private List<DeviceGroupInfo> discoveredGroups = new ArrayList<>();
    }
}
