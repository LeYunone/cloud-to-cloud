package com.leyunone.cloudcloud.bean.mapping;

import com.leyunone.cloudcloud.bean.enums.ConvertFunctionEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024-02-07
 */
@Getter
@Setter
public class StatusMapping {

    private boolean valueOf;
    
    private Map<String,Object> valueMapping;
    
    private String signCode;

    private Integer functionId;
    
    private String thirdSignCode;

    private String thirdActionCode;

    private String legalValue;

    private ConvertFunctionEnum convertFunction;;
}
