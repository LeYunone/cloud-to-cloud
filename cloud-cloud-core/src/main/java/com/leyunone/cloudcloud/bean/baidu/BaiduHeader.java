package com.leyunone.cloudcloud.bean.baidu;

import lombok.*;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaiduHeader {

    String namespace;

    String name;

    String messageId;

    String payloadVersion;

}
