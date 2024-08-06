package com.leyunone.cloudcloud.api.protocol;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/5/10 13:49
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MyCompanyHeader {

    private String namespace;

//    private String name;

    private String messageId;

    private Long timestamp;
}
