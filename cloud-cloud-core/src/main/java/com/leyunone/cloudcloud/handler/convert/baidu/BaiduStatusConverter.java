package com.leyunone.cloudcloud.handler.convert.baidu;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.cloudcloud.bean.third.baidu.BaiduAttributes;
import com.leyunone.cloudcloud.bean.info.DeviceInfo;
import com.leyunone.cloudcloud.bean.mapping.ProductMapping;
import com.leyunone.cloudcloud.enums.ThirdPartyCloudEnum;
import com.leyunone.cloudcloud.service.mapping.ProductMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/1/25
 */
@Component
public class BaiduStatusConverter extends AbstractBaiduDataConverterTemplate<List<BaiduAttributes>, DeviceInfo> {

    private final Logger logger = LoggerFactory.getLogger(BaiduStatusConverter.class);

    protected BaiduStatusConverter( ProductMappingService productMappingService) {
        super( productMappingService);
    }

    /**
     * 百度重写改方法用于特殊处理HSB转RGB。
     * 百度hue范围值为0-360
     *
     * @param value
     * @param mapping
     * @return
     */

    /**
     * 存在一个产品 属于多种产品类型 比如 即是空调又有色温功能
     * @param r
     * @return
     */
    @Override
    public List<BaiduAttributes> convert(DeviceInfo r) {
        if(ObjectUtil.isNull(r)) return new ArrayList<>();
        String productId = r.getProductId();
        List<ProductMapping> mappings = productMappingService.getMapping(productId, ThirdPartyCloudEnum.BAIDU);
        List<BaiduAttributes> baiduAttributes = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(mappings)) {
            mappings.forEach(mapping -> {
                baiduAttributes.addAll(convert(mapping.getStatusMappings(), r.getDeviceFunctions()));
            });
        }
        return baiduAttributes;
    }
}
