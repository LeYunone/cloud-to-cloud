package com.leyunone.cloudcloud.bean.mapping;

import com.leyunone.cloudcloud.bean.enums.ConvertFunctionEnum;
import com.leyunone.cloudcloud.dao.entity.ActionMappingDO;
import com.leyunone.cloudcloud.enums.OperationEnum;
import lombok.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionMapping {

    /**
     * 三方参数
     */
    private String thirdSignCode;

    /**
     * 三方行为标识
     */
    private String thirdActionCode;

    private Integer functionId;

    private String signCode;

    /**
     * 不为空的时候采用默认值
     */
    private String defaultValue;

    private boolean valueOf;

    /**
     * 
     * 示例 {"1":"on","0":"off"} 
     */
    private Map<String, Object> valueMapping;
    
    private OperationEnum operation;

    private ConvertFunctionEnum convertFunction;

    @Mapper
    public interface Converter {

        Converter INSTANCE  = Mappers.getMapper(Converter.class);

        @Mappings({
                @Mapping(target = "valueMapping",ignore = true),
        })
        ActionMapping convert(ActionMappingDO actionMappingDO);

    }
}
