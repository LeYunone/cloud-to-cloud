package com.leyunone.cloudcloud.bean.third.tmall;

import lombok.*;

/**
 * :)
 *
 * @Author Leyunone
 * @Date 2024/1/16 17:00
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TmallHeader {

    private String namespace;
    
    private String name;
    
    private String messageId;
    
    private Integer payLoadVersion;
}
