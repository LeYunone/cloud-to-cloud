package com.leyunone.cloudcloud.api.protocol;

import lombok.Data;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/28 17:33
 */
@Data
public class DeviceGroupInfo {

    private String groupName;

    private List<String> deviceIds;
    
    private String thirdGroupId;
}
