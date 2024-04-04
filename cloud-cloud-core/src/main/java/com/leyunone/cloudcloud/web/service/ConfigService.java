package com.leyunone.cloudcloud.web.service;


import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.web.bean.dto.ThirdClientConfigDTO;
import com.leyunone.cloudcloud.web.bean.vo.ThirdClientConfigVO;

/**
 * :)
 *
 * @Author LeyunOne
 * @Date 2024/4/2 16:44
 */
public interface ConfigService {

    void thirdClientConfigSave(ThirdClientConfigDTO dto);

    ThirdClientConfigVO clientConfigDetail(ThirdPartyCloudEnum cloud);
}
