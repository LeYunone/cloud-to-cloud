package com.leyunone.cloudcloud.web.bean.vo;

import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * :)
 *
 * @Author LeyunOne
 * @Date 2024/3/26 15:31
 */
@Getter
@Setter
public class ProductActionVO {

    private String productId;

    private List<String> thirdProductIds = new ArrayList<>();
    
    private Set<String> signCodes = new HashSet<>();
    
    private List<Mapping> thirdCodes = new ArrayList<>();

    private LocalDateTime updateTime;

    private ThirdPartyCloudEnum thirdPartyCloud;
    
    @Getter
    @Setter
    public static class Mapping{
        
        private String thirdSignCode;
        
        private String thirdActionCode;
    }
}
