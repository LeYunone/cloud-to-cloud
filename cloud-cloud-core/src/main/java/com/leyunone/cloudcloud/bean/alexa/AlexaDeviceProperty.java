package com.leyunone.cloudcloud.bean.alexa;

import lombok.*;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/2/26
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlexaDeviceProperty {

    private String namespace;

    private String name;

    private Object value;

    private String timeOfSample;

    /**
     * 语义实例名
     */
    private String instance;

    private Long uncertaintyInMilliseconds;
}
