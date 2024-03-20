package com.leyunone.cloudcloud.bean.third.alexa;

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
@EqualsAndHashCode
public class AlexaHeader {

    private String namespace;
    
    private String name;
    
    private String messageId;
    
    private String payloadVersion;
    
    private String correlationToken;
}
