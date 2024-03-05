package com.leyunone.cloudcloud.bean.mapping;

import com.leyunone.cloudcloud.bean.enums.ConvertFunctionEnum;
import com.leyunone.cloudcloud.dao.entity.FunctionMappingDO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Mapper;
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
public class StatusMapping {

    private boolean valueOf;
    
    private Map<String,Object> valueMapping;
    
    private String signCode;

    private Integer functionId;
    
    private String thirdSignCode;

    private String legalValue;

    private ConvertFunctionEnum convertFunction;;


    @Mapper
    public interface Converter {

        Converter INSTANCE  = Mappers.getMapper(Converter.class);

        @Mappings({
                @Mapping(target = "valueMapping",ignore = true)
        })
        StatusMapping convert(FunctionMappingDO functionMappingDO);

    }
}
