package com.leyunone.cloudcloud.api.protocol;

import com.leyunone.cloudcloud.api.MyCompanyProtocolShadowModel;
import lombok.Data;

import java.util.List;


/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/29 16:10
 */
@Data
public class MyCompanyProtocolQueryResponse {

    private Payload payload;

    private MyCompanyHeader header;

    @Data
    public static class Payload {

        private List<MyCompanyProtocolShadowModel> devices;
    }
}
